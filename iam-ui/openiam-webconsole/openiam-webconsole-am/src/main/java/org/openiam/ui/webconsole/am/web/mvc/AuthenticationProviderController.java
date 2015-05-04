package org.openiam.ui.webconsole.am.web.mvc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openiam.am.srvc.constants.SsoAttributeType;
import org.openiam.am.srvc.dto.AuthAttribute;
import org.openiam.am.srvc.dto.AuthProvider;
import org.openiam.am.srvc.dto.AuthProviderType;
import org.openiam.am.srvc.dto.AuthResourceAttributeMap;
import org.openiam.am.srvc.searchbeans.AuthAttributeSearchBean;
import org.openiam.am.srvc.searchbeans.AuthProviderSearchBean;
import org.openiam.am.srvc.ws.AuthProviderWebService;
import org.openiam.am.srvc.ws.AuthResourceAttributeWebService;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.srvc.mngsys.dto.ManagedSysDto;
import org.openiam.idm.srvc.mngsys.ws.ManagedSystemWebService;
import org.openiam.ui.rest.api.model.KeyNameBean;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.model.BeanResponse;
import org.openiam.ui.web.mvc.AbstractController;
import org.openiam.ui.webconsole.am.web.bean.converter.AuthProviderFormRequestConverter;
import org.openiam.ui.webconsole.am.web.model.AuthProviderFormRequest;
import org.openiam.ui.webconsole.am.web.model.X509ModelResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AuthenticationProviderController extends AbstractController {
	
	private static Logger LOG = Logger.getLogger(AuthenticationProviderController.class);
	
	@Resource(name="authAttributeServiceClient")
	private AuthResourceAttributeWebService authAttributeServiceClient;
	
	@Resource(name="authProviderServiceClient")
	private AuthProviderWebService authProviderServiceClient;
	
	@Resource(name="managedSysServiceClient")
	private ManagedSystemWebService managedSysServiceClient;
	
	@Autowired
	private AuthProviderFormRequestConverter authProviderFormRequestConverter;
	
	@Value("${org.openiam.ui.am.provider.root.name}")
	private String rootMenuName;
	
	@Value("${org.openiam.ui.am.provider.edit.name}")
	private String editMenuName;
	
	private static final Map<SsoAttributeType, String> attributeTypeMap = new LinkedHashMap<SsoAttributeType, String>();
	static {
		for(final SsoAttributeType type : SsoAttributeType.values()) {
			attributeTypeMap.put(type, type.getDisplayName());
		}
	}
	
	@RequestMapping("/authprovider/idp/file")
	public @ResponseBody BasicAjaxResponse importIDP(final HttpServletRequest request,
													 final @RequestParam("file") MultipartFile file) {
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		try {
			
		} catch(Throwable e) {
			LOG.error("Can't upload file", e);
		} finally {
			ajaxResponse.process(localeResolver, messageSource, request);
		}
		return ajaxResponse;
	}
	
	@RequestMapping("/authprovider/sp/file")
	public @ResponseBody BasicAjaxResponse importSP(final HttpServletRequest request,
													final @RequestParam("file") MultipartFile file) {
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		try {
			
		} catch(Throwable e) {
			LOG.error("Can't upload file", e);
		} finally {
			ajaxResponse.process(localeResolver, messageSource, request);
		}
		return ajaxResponse;
	}
	
	@RequestMapping("/getAuthenticationProviders.html")
	public @ResponseBody BeanResponse getAuthenticationProviders(final HttpServletRequest reqeust,
																 final @RequestParam(required=true, value="from") int from,
																 final @RequestParam(required=true, value="size") int size) {
		final AuthProviderSearchBean searchBean = new AuthProviderSearchBean();
		searchBean.setDeepCopy(false);
		final List<AuthProvider> results = authProviderServiceClient.findAuthProviderBeans(searchBean, size, from);
		final Integer count = authProviderServiceClient.getNumOfAuthProviderBeans(searchBean);
		final List<KeyNameBean> beans = mapper.mapToList(results, KeyNameBean.class);
		return new BeanResponse(beans, count);
	}

	@RequestMapping("/authenticationProviders.html")
	public String authenticationProviders(final HttpServletRequest request) {
		setMenuTree(request, rootMenuName);
		return "authentication/search";
	}
	
	@RequestMapping(value="/newAuthProvider", method=RequestMethod.GET)
	public String newAuthProvider(final HttpServletRequest request) {
		final List<AuthProviderType> authProviderTypes = authProviderServiceClient.getAuthProviderTypeList();
		request.setAttribute("authProviderList", authProviderTypes);
		setMenuTree(request, rootMenuName);
		return "authentication/newNoAuthProviderType";
	}
	
	@RequestMapping(value="/newAuthProviderWithType", method=RequestMethod.GET)
	public String newAuthProviderWithType(final HttpServletRequest request, 
								  final @RequestParam(value="id", required=true) String providerType) {
		if(StringUtils.isBlank(providerType)) {
			return newAuthProvider(request);
		} else {
			final AuthProvider provider = new AuthProvider();
			provider.setProviderType(providerType);
			populateAuthProviderPage(request, provider);
			return "authentication/edit";
		}
	}
	
	@RequestMapping(value="/editAuthProvider", method=RequestMethod.GET)
	public String editAuthProvider(final HttpServletRequest request, 
								   final HttpServletResponse response,
								  final @RequestParam(value="id", required=true) String providerId) throws IOException {
		final AuthProvider provider = getAuthProvider(providerId);
		if(provider == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, String.format("Authentication provider with ID: '%' - not found", providerId));
			return null;
		}
		set509Cert(provider, request);
		populateAuthProviderPage(request, provider);
		return "authentication/edit";
	}
	
	@RequestMapping(value="/editAuthProvider", method=RequestMethod.POST)
	public String editAuthProviderPOST(final HttpServletRequest request, final AuthProviderFormRequest providerRequest) {
		AuthProvider provider = authProviderFormRequestConverter.convert(providerRequest);
		ErrorToken errorToken = null;
		SuccessToken successToken = null;
		
		//TODO: validate
		try {
			if(errorToken == null) {
				Response response = null;
				if(StringUtils.isNotBlank(provider.getProviderId())) {
					response = authProviderServiceClient.updateAuthProvider(provider, getRequesterId(request));
				} else {
					response = authProviderServiceClient.addAuthProvider(provider, getRequesterId(request));
				}
			
				if(response.getStatus() == ResponseStatus.SUCCESS) {
					provider = getAuthProvider((String)response.getResponseValue());
					successToken = new SuccessToken(SuccessMessage.AUTH_PROVIDER_SAVED);
				} else {
					Errors error = Errors.CANNOT_SAVE_AUTHENTICATION_PROIVDER;
					if(response.getErrorCode() != null) {
						switch(response.getErrorCode()) {
							case AUTH_PROVIDER_TYPE_NOT_SET:
								error = Errors.AUTH_PROVIDER_TYPE_INVALID;
								break;
							case MANAGED_SYS_NOT_SET:
								error = Errors.AUTH_PROVIDER_MANAGED_SYS_INVALID;
								break;
							case RESOURCE_PROP_MISSING:
								error = Errors.AUTH_PROVIDER_RESOURCE_INVALID;
								break;
							case AUTH_PROVIDER_NAME_NOT_SET:
								error = Errors.AUTH_PROVIDER_NAME_INVALID;
								break;
                            case AUTH_PROVIDER_SECUTITY_KEYS_NOT_SET:
                                error = Errors.AUTH_PROVIDER_SECURITY_KEYS_INVALID;
                                break;
                            case AUTH_REQUIRED_PROVIDER_ATTRIBUTE_NOT_SET:
                                error = Errors.AUTH_REQUIRED_PROVIDER_ATTRIBUTE_INVALID;
                                break;
							default:
						}
					}
					errorToken = new ErrorToken(error);
				}
			}
		} catch(Throwable e) {
			errorToken = new ErrorToken(Errors.INTERNAL_ERROR);
			LOG.error("Exception while saving auth provider", e);
		} finally {
			request.setAttribute("errorToken", errorToken);
			request.setAttribute("successToken", successToken);
			
			if(errorToken != null) {
				/* null out the keys, in case of a new provider, and an error occurred */
				if(provider.getProviderId() == null) {
					provider.setPublicKey(null);
					provider.setPrivateKey(null);
				} else { /* null out the keys in case the error occurred, and the provider in the DB has no keys */
					final AuthProvider previousProvider = getAuthProvider(provider.getProviderId());
					if(previousProvider != null) {
						if(previousProvider.getPrivateKey() == null || previousProvider.getPrivateKey().length == 0) {
							provider.setPrivateKey(null);
						}
						if(previousProvider.getPublicKey() == null || previousProvider.getPublicKey().length == 0) {
							provider.setPublicKey(null);
						}
					}
				}
			}
			
			set509Cert(provider, request);
			populateAuthProviderPage(request, provider);
		}
		return "authentication/edit";
	}
	
	@RequestMapping(value="/deleteAuthProvider", method=RequestMethod.POST)
	public String deleteAuthProvider(final HttpServletRequest request, @RequestParam(value="id", required=true) String id) {
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		final Response wsResponse = authProviderServiceClient.deleteAuthProvider(id);
		if(ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
			ajaxResponse.setStatus(200);
			ajaxResponse.setRedirectURL("authenticationProviders.html");
			ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.AUTH_PROVIDER_DELETED));
		} else {
			Errors error = Errors.AUTH_PROVIDER_DELETE_ERROR;
			if(wsResponse.getErrorCode() != null) {
				//TODO: error handler
				switch(wsResponse.getErrorCode()) {
				 	default:
				 		break;
				}
			}
			ajaxResponse.addError(new ErrorToken(error));
			ajaxResponse.setStatus(500);
		}
		request.setAttribute("response", ajaxResponse);
		return "common/basic.ajax.response";
	}
	
	@RequestMapping(value="/authProviderAMAttributes", method=RequestMethod.GET)
	public String authProviderAMAttributes(final HttpServletRequest request,
			   						  	   final HttpServletResponse response,
			   						  	   final @RequestParam(required=true, value="id") String providerId) throws IOException {
		final AuthProvider provider = getAuthProvider(providerId);
		if(provider == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
		setMenuTree(request, editMenuName);
		request.setAttribute("attributeTypeMap", attributeTypeMap);
		request.setAttribute("provider", getAuthProvider(providerId));
		request.setAttribute("amAttributes", authAttributeServiceClient.getAmAttributeList());
		return "authentication/amAttributes"; 
	}
	
	@RequestMapping(value="/deleteAMAttributeForProvider", method=RequestMethod.POST)
	public String deleteAMAttributeForProvider(final HttpServletRequest request,
			   						  final HttpServletResponse response,
			   						  final @RequestParam(required=true, value="id") String attributeMapId) {
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		final Response wsResponse = authAttributeServiceClient.removeAttributeMap(attributeMapId);
		if(ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
			ajaxResponse.setStatus(HttpServletResponse.SC_OK);
			ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.AUTH_ATTRIBUTE_DELETED));
		} else {
			ajaxResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			ajaxResponse.addError(new ErrorToken(Errors.AUTH_PROVIDER_ATTRIBUTE_SAVE_ERROR));
		}
		
		request.setAttribute("response", ajaxResponse);
		return "common/basic.ajax.response";
	}
	
	@RequestMapping(value="/saveAMAttributeForProvider", method=RequestMethod.POST)
	public String saveAMAttributeForProvider(final HttpServletRequest request,
									 final HttpServletResponse response,
									 final @RequestBody AuthResourceAttributeMap attributeMap) {
		//TODO:  needs to be done better
		attributeMap.setAmResAttributeId(StringUtils.trimToNull(attributeMap.getAmResAttributeId()));
		attributeMap.setAmResAttributeName(StringUtils.trimToNull(attributeMap.getAmResAttributeName()));
		attributeMap.setAmPolicyUrl(StringUtils.trimToNull(attributeMap.getAmPolicyUrl()));
		attributeMap.setAttributeValue(StringUtils.trimToNull(attributeMap.getAttributeValue()));
		attributeMap.setTargetAttributeName(StringUtils.trimToNull(attributeMap.getTargetAttributeName()));
		//END
		
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		
		final Response wsResponse = authAttributeServiceClient.saveAttributeMap(attributeMap);
		if(ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
			ajaxResponse.setStatus(HttpServletResponse.SC_OK);
			ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.AUTH_ATTRIBUTE_SAVED));
		} else {
			Errors error = Errors.AUTH_PROVIDER_ATTRIBUTE_SAVE_ERROR;
			if(wsResponse.getErrorCode() != null) {
				switch(wsResponse.getErrorCode()) {
					default:
						break;
				}
			}
			ajaxResponse.addError(new ErrorToken(error));
			ajaxResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		
		request.setAttribute("response", ajaxResponse);
		return "common/basic.ajax.response";
	}
	
	private void populateAuthProviderPage(final HttpServletRequest request, final AuthProvider provider) {
		provider.getProviderAttributeMap();
		request.setAttribute("provider", provider);
		if(provider.getResource() != null && StringUtils.isNotBlank(provider.getResource().getId())) {
			String resourceCoorelatedName = null;
			if(StringUtils.isNotBlank(provider.getResource().getCoorelatedName())) {
				resourceCoorelatedName = provider.getResource().getCoorelatedName();
			} else if(StringUtils.isNotBlank(provider.getResource().getDisplayName())) {
				resourceCoorelatedName = provider.getResource().getDisplayName();
			} else {
				resourceCoorelatedName = provider.getResource().getName();
			}
			request.setAttribute("resourceCoorelatedName", resourceCoorelatedName);
		}
		
		final AuthProviderType authProviderType = authProviderServiceClient.getAuthProviderType(provider.getProviderType());
		
		final AuthAttributeSearchBean searchBean = new AuthAttributeSearchBean();
		searchBean.setProviderType(provider.getProviderType());
		final List<AuthAttribute> attributes = authProviderServiceClient.findAuthAttributeBeans(searchBean, Integer.MAX_VALUE, 0);
		
		final List<ManagedSysDto> managedSystems = managedSysServiceClient.getAllManagedSys();
		
		request.setAttribute("managedSystems", managedSystems);
		request.setAttribute("attributeList", attributes);
		request.setAttribute("authProviderType", authProviderType);
		if(provider.getProviderId() == null) {
			setMenuTree(request, rootMenuName);
		} else {
			setMenuTree(request, editMenuName);
		}
	}
	
	private AuthProvider getAuthProvider(final String providerId) {
		final AuthProviderSearchBean searchBean = new AuthProviderSearchBean();
		searchBean.setDeepCopy(true);
		searchBean.setKey(providerId);
		final List<AuthProvider> authProvider = (providerId != null) ? authProviderServiceClient.findAuthProviderBeans(searchBean, Integer.MAX_VALUE, 0) : null;
		return (CollectionUtils.isNotEmpty(authProvider)) ? authProvider.get(0) : null;
	}
	
	private void set509Cert(final AuthProvider provider, final HttpServletRequest request) { 
		X509ModelResponse cert = null;
		if(provider.getPublicKey() != null && provider.getPublicKey().length > 0) {
			try {
				final InputStream inStream = new ByteArrayInputStream(provider.getPublicKey());
				final CertificateFactory cf = CertificateFactory.getInstance("X.509");
				cert = new X509ModelResponse((X509Certificate)cf.generateCertificate(inStream));
				IOUtils.closeQuietly(inStream);
			} catch(Throwable e) {
				LOG.info("Can't parse uploaded certificate into X509 format", e);
			}
		}
		
		
		request.setAttribute("publicKeyCert", cert);
	}
}
