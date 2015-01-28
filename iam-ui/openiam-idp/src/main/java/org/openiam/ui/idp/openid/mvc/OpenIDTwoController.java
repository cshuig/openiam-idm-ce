package org.openiam.ui.idp.openid.mvc;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.jdom.Namespace;
import org.openiam.am.srvc.dto.ContentProvider;
import org.openiam.idm.srvc.auth.dto.AuthenticationRequest;
import org.openiam.idm.srvc.auth.dto.Login;
import org.openiam.idm.srvc.auth.ws.LoginResponse;
import org.openiam.idm.srvc.continfo.dto.EmailAddress;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.ui.idp.openid.exception.OpenIDMessageException;
import org.openiam.ui.idp.openid.model.XRDCDocument;
import org.openiam.ui.security.OpenIAMAuthenticationEntryPoint;
import org.openiam.ui.util.HeaderUtils;
import org.openiam.ui.util.URIUtils;
import org.openiam.ui.web.mvc.AbstractController;
import org.openiam.ui.web.util.ContentProviderCacheProvider;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.InMemoryConsumerAssociationStore;
import org.openid4java.consumer.InMemoryNonceVerifier;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.xrds.XrdsParserImpl;
import org.openid4java.discovery.xrds.XrdsServiceEndpoint;
import org.openid4java.message.AuthImmediateFailure;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.DirectError;
import org.openid4java.message.IndirectError;
import org.openid4java.message.Message;
import org.openid4java.message.MessageExtension;
import org.openid4java.message.ParameterList;
import org.openid4java.message.ax.AxMessage;
import org.openid4java.message.ax.FetchRequest;
import org.openid4java.message.ax.FetchResponse;
import org.openid4java.message.sreg.SRegMessage;
import org.openid4java.server.InMemoryServerAssociationStore;
import org.openid4java.server.ServerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OpenIDTwoController extends AbstractController {
	
	private static Logger LOG = Logger.getLogger(OpenIDTwoController.class);
	
    @Value("${org.openiam.self.register.enabled}")
    private boolean isSelfRegistrationEnabled;
    
    @Value("${org.openiam.self.register.url}")
    private String selfRegistrationURL;
	
	@Autowired
	private ContentProviderCacheProvider cpCacheProvider;
	
	@Autowired
	private OpenIAMAuthenticationEntryPoint authenticationEntryPoint;
	
	@Value("${org.openiam.openid.url.entrypoint}")
	private String openidURL;
	
	@Value("${org.openiam.openid.url.authenticated}")
	private String authenticatedURL;
	
	private void logRequest(final HttpServletRequest httpReq) {
		List<String> params = new LinkedList<String>();
		if(httpReq.getParameterMap() != null) {
			for(final Object key : httpReq.getParameterMap().keySet()) {
				final String[] value = httpReq.getParameterValues((String)key);
				params.add(String.format("%s=%s", key, ArrayUtils.toString(value)));
			}
		}
		
		final List<String> headers = new LinkedList<String>();
		if(httpReq.getHeaderNames() != null) {
			final Enumeration e = httpReq.getHeaderNames();
			while(e.hasMoreElements()) {
				final String name = (String)e.nextElement();
				headers.add(String.format("%s=%s", name, httpReq.getHeader(name)));
			}
		}
		
		LOG.info(String.format("Parameters: %s, Headers: %s", params, headers));
	}
	
	@RequestMapping(value="/2.0/id")
	public void doClaimedIdentity(final HttpServletRequest request,
								  final HttpServletResponse response,
								  final @RequestParam(required=false, value="id") String id) throws Exception {
		logRequest(request);
		if(id == null) {
			doGet(request, response, null);
		} else {
			sendDiscoveryResponse(request, response, id);
		}
	}

	@RequestMapping(value={"/2.0/ud", "/2.0/ud/"})
	public void doGetWithoutParams(final HttpServletRequest request,
							 	   final HttpServletResponse response) throws Exception {
		doGet(request, response, null);
	}
	
	@RequestMapping(value={"/2.0/ud/{identity}"})
	private void doGet(final HttpServletRequest httpReq, 
					  final HttpServletResponse httpResp,
					  final @PathVariable(value="identity") String identity) throws Exception {
		logRequest(httpReq);
		
		Message response = null;
        String responseText = null;
		try {
			final ContentProvider cp = cpCacheProvider.getCachedContentProvider(httpReq);
			if(cp == null || StringUtils.isBlank(cp.getManagedSysId())) {
				throw new OpenIDMessageException(DirectError.createDirectError("OpenID is not supported for this Content Provider"));
			}
			
			final ServerManager manager = cpCacheProvider.getServerManager(httpReq);
			if(manager == null) {
				throw new OpenIDMessageException(DirectError.createDirectError("OpenID ServerManager is not configured for this Content Provider"));
			}
			
			final ParameterList parameterList = new ParameterList(httpReq.getParameterMap());
			if(isDiscoverRequest(httpReq)) {
				sendDiscoveryResponse(httpReq, httpResp, null);
			} else {
				final String nameSpace = parameterList.getParameterValue("openid.ns");
				if(!StringUtils.equalsIgnoreCase(nameSpace, "http://specs.openid.net/auth/2.0")) {
					throw new OpenIDMessageException(DirectError.createDirectError("openid.ns is either missing, or is not 'http://specs.openid.net/auth/2.0'"));
				}
				
				final String mode = parameterList.getParameterValue("openid.mode");
				if ("associate".equals(mode)) {
					response = manager.associationResponse(parameterList);
					responseText = response.keyValueFormEncoding();
				} else if ("checkid_setup".equals(mode) || "checkid_immediate".equals(mode)) {
					final boolean isImmediate = mode.equals("checkid_immediate");
					
					final String userId = getRequesterIdFromCookie(httpReq);
					if(StringUtils.isBlank(userId)) { /* redirect to login page */
						
						if(isImmediate) {
							final String selfRegistrationPage = (isSelfRegistrationEnabled) ? 
									new StringBuilder(URIUtils.getBaseURI(httpReq)).append(selfRegistrationURL).toString() : null;
							throw new OpenIDMessageException(AuthImmediateFailure.createAuthImmediateFailure(null, selfRegistrationPage, false));
						}
							
						//ERROR/TODO:  XSS problem if openid.redirect_to url is set here
						authenticationEntryPoint.commence(httpReq, httpResp, null);
					} else {
						
						final LoginResponse loginResponse = (identity != null) ? loginServiceClient.getLoginByManagedSys(identity, cp.getManagedSysId()) :
                                loginServiceClient.getPrimaryIdentity(userId);
						if(loginResponse == null || loginResponse.getPrincipal() == null) {
							throw new OpenIDMessageException(DirectError.createDirectError("User Not Found"));
						}
						final Login login = loginResponse.getPrincipal();
						
						final String claimedIdentity = getOpenIDIdentity(httpReq, login);
						response = manager.authResponse(parameterList, claimedIdentity, claimedIdentity, true);
						if (response instanceof DirectError) {
							throw new OpenIDMessageException(response);
						}
						
						if(response instanceof IndirectError) {
							throw new OpenIDMessageException(response);
						}
						
						final AuthRequest authRequest = AuthRequest.createAuthRequest(parameterList, manager.getRealmVerifier());
						
						Map required = null;
				        Map optional = null;
						if(authRequest.hasExtension(AxMessage.OPENID_NS_AX)) {
							final MessageExtension extension = authRequest.getExtension(AxMessage.OPENID_NS_AX);
							if(extension instanceof FetchRequest) {
								final User user = userDataWebService.getUserWithDependent(userId, userId, true);
								if(user == null) {
									throw new OpenIDMessageException(DirectError.createDirectError("user not found"));
								}
								
								//TODO:  propt  the user
								final FetchRequest fetchReq = (FetchRequest) extension;
						        required = fetchReq.getAttributes(true);
						        optional = fetchReq.getAttributes(false);
						        
						        final Map<String, String> userData = new HashMap<String, String>();
						        if(required.containsKey("email")) {
						        	final List<EmailAddress> emailAddresses = userDataWebService.getEmailAddressList(userId);
						        	if(CollectionUtils.isNotEmpty(emailAddresses)) {
						        		EmailAddress email = null;
						        		for(final EmailAddress addr : emailAddresses) {
						        			if(addr.getIsDefault()) {
						        				email = addr;
						        				break;
						        			}
						        		}
						        		
						        		if(email == null) {
						        			email = emailAddresses.get(0);
						        		}
						        		userData.put("email", email.getEmailAddress());
						        	}
						        }
						        if(required.containsKey("firstname")) {
						        	if(StringUtils.isNotBlank(user.getFirstName())) {
						        		userData.put("firstname", user.getFirstName());
						        	}
						        }
						        if(required.containsKey("lastname")) {
						        	if(StringUtils.isNotBlank(user.getLastName())) {
						        		userData.put("lastname", user.getLastName());
						        	}
						        }
								
						        final FetchResponse fetchResp = FetchResponse.createFetchResponse(fetchReq, userData);
						        response.addExtension(fetchResp, "ax");
							}
						}
						
						final String redirectURL = response.getDestinationUrl(true);
						if(isImmediate) {
							httpResp.sendRedirect(redirectURL);
						} else {
							if(MapUtils.isEmpty(required) && MapUtils.isEmpty(optional)) {
								httpResp.sendRedirect(redirectURL);
							} else {
								httpReq.setAttribute("openIdWithPrincipal", claimedIdentity);
								httpReq.setAttribute("openIdUrlWithoutPrincipal", getEndpointURL(httpReq));
								httpReq.setAttribute("openIdTarget", redirectURL);
								httpReq.setAttribute("requiredMap", required);
								httpReq.setAttribute("optionalMap", optional);
								httpReq.getRequestDispatcher("/WEB-INF/jsp/auth/openidpost.jsp").forward(httpReq, httpResp);
							}
						}
					}
				} else if ("check_authentication".equals(mode)) {
					response = manager.verify(parameterList);
					responseText = response.keyValueFormEncoding();
				} else {
					response = DirectError.createDirectError("Unknown request");
					responseText = response.keyValueFormEncoding();
				}
			}
		} catch(OpenIDMessageException e) {
			response = e.getOpenIDMessage();
			responseText = response.keyValueFormEncoding();
		} catch(Throwable e) {
			LOG.error("Unkonwn error", e);
			response = DirectError.createDirectError(e.getMessage());
			responseText = response.keyValueFormEncoding();
		} finally {
			if(responseText != null) {
				httpResp.setContentType("text/plain");
			    final OutputStream os = httpResp.getOutputStream();
			    os.write(responseText.getBytes());
			    os.close();
			}
		}
	}
	
	private boolean isDiscoverRequest(final HttpServletRequest request) {
		return MapUtils.isEmpty(request.getParameterMap());
	}
	
	private void sendDiscoveryResponse(final HttpServletRequest request, final HttpServletResponse response, final String id) throws Exception {
		response.setContentType("application/xrds+xml");
		final OutputStream outputStream = response.getOutputStream();
		
		outputStream.write(createXrdsResponse(request, id).toXmlString().getBytes());
	    outputStream.close();
	}
	
	private String getEndpointURL(final HttpServletRequest request) throws URISyntaxException {
		final String baseURI = URIUtils.getBaseURI(request);
		final String endpoingURL = new StringBuilder(baseURI).append(openidURL).toString();
		return endpoingURL;
	}
	
	private String getOpenIDIdentity(final HttpServletRequest request, final Login login) throws URISyntaxException {
		final String baseURI = URIUtils.getBaseURI(request);
		final String endpoingURL = new StringBuilder(baseURI).append(authenticatedURL).append("?id=").append(login.getUserId()).toString();
		return endpoingURL;
	}
	
	private XRDCDocument createXrdsResponse(final HttpServletRequest request, final String id) throws Exception {
		final String endpoingURL = getEndpointURL(request);
		final XRDCDocument document = new XRDCDocument();
		
		final String specificationURL = (id != null) ? "http://specs.openid.net/auth/2.0/signon" : "http://specs.openid.net/auth/2.0/server";
		document.addServiceElement(new String[] {
				specificationURL,
				"http://openid.net/srv/ax/1.0",
				"http://specs.openid.net/extensions/ui/1.0/mode/popup",
				"http://specs.openid.net/extensions/ui/1.0/icon",
				"http://specs.openid.net/extensions/pape/1.0"
		}, endpoingURL, "0");
		return document;
	}
}
