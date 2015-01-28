package org.openiam.ui.idp.web.sp.mvc;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.openiam.ui.idp.saml.model.SAMLRequestToken;
import org.openiam.ui.idp.saml.model.SAMLResponseToken;
import org.openiam.ui.idp.saml.provider.SAMLServiceProvider;
import org.openiam.ui.idp.saml.service.SAMLService;
import org.openiam.ui.security.OpenIAMCookieProvider;
import org.openiam.ui.util.URIUtils;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.web.filter.ProxyFilter;
import org.openiam.ui.web.util.LoginProvider;
import org.opensaml.DefaultBootstrap;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.BasicSAMLMessageContext;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.binding.encoding.HTTPRedirectDeflateEncoder;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.core.impl.AuthnRequestBuilder;
import org.opensaml.saml2.core.impl.IssuerBuilder;
import org.opensaml.saml2.ecp.Request;
import org.opensaml.saml2.ecp.impl.RequestBuilder;
import org.opensaml.saml2.metadata.Endpoint;
import org.opensaml.ws.message.encoder.MessageEncodingException;
import org.opensaml.ws.transport.http.HTTPOutTransport;
import org.opensaml.ws.transport.http.HttpServletResponseAdapter;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallerFactory;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.util.XMLHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.w3c.dom.Element;

@Controller
public class ServiceProviderController {
	
	private static Logger log = Logger.getLogger(ServiceProviderController.class);

	@Autowired
	private SAMLService samlService;
	
	@Autowired
	private OpenIAMCookieProvider cookieProvider;
	
	@Autowired
	private LoginProvider loginProvider;
	
	@RequestMapping(value={"/logout/{spId}"}, method=RequestMethod.GET)
	public void logout(final HttpServletRequest request,
					   final HttpServletResponse response,
					   final @PathVariable(value="spId") String spId) throws IOException {
		loginProvider.doLogout(request, response, false);
		if(spId != null) {
			final SAMLServiceProvider sp = samlService.getSAMLServiceProvierById(spId);
			if(sp != null && sp.getLogoutURL() != null) {
				response.sendRedirect(sp.getLogoutURL());
			} else {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, String.format("SP with ID '%s' not found", spId));
				//response.sendRedirect("logout.html");
			}
		}
	}
	
	@RequestMapping(value={"/login/{spName}"}, method=RequestMethod.GET)
	public void login(final HttpServletRequest request,
					  final HttpServletResponse response,
					  final @PathVariable(value="spName") String spName) throws IOException, MessageEncodingException {
		final SAMLRequestToken token = samlService.getSAMLRequestForSP(request, spName);
		if(token.isError()) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, token.getError().toString());
		} else {
			//if(cookieProvider.getUserIdFromCookie(request) != null) {
			//	response.sendRedirect("/selfservice");
			//} else {
				final AuthnRequest authnRequest = token.getAuthnRequest();
				final SAMLServiceProvider serviceProvider = token.getServiceProvider();
				final Endpoint endpoint = token.getEndpoint();
				final BasicSAMLMessageContext<SAMLObject, AuthnRequest, SAMLObject> messageContext = new BasicSAMLMessageContext<SAMLObject, AuthnRequest, SAMLObject>();
				final HTTPOutTransport outTransport = new HttpServletResponseAdapter(response, URIUtils.isSecure(request));
				
				messageContext.setOutboundMessageTransport(outTransport);
				messageContext.setOutboundMessage(authnRequest);
				messageContext.setOutboundSAMLMessage(authnRequest);
				//messageContext.setRelayState("relay");
				messageContext.setPeerEntityEndpoint(endpoint);
				new HTTPRedirectDeflateEncoder().encode(messageContext);
			//}
		}
	}
	
	@RequestMapping(value={"/login/{spName}"}, method=RequestMethod.POST)
	public String doPost(final HttpServletRequest request,
					   final HttpServletResponse response) throws IOException {
		final SAMLResponseToken token = samlService.processSAMLResponse(request, response);
		if(token.isError()) {
			request.setAttribute("error", new ErrorToken(token.getError()));
			return "auth/authError";
		} else {
			response.sendRedirect("/selfservice");
			return null;
		}
		//validate SAMLResponse and RelayState
		//decode SAMLResponse
		//set identity and redirect to /selfservice
	}
	
	public static void main(String[] args) {
		try {
			DefaultBootstrap.bootstrap();
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();
		final AuthnRequest request = ((AuthnRequestBuilder)builderFactory.getBuilder(AuthnRequest.DEFAULT_ELEMENT_NAME)).buildObject();
		
		//request.setAssertionConsumerServiceURL(URIUtils.getBaseURI(request));
		//request.setAssertionConsumerServiceIndex(0);
		//request.setAttributeConsumingServiceIndex(1);
		//request.setConsent("consent");
		//request.setDestination("destination");
		//request.setConditions(newConditions);
		//request.setExtensions(newExtensions);
		request.setID(RandomStringUtils.randomAlphanumeric(10));
		request.setIssueInstant(new DateTime());
		//request.setNameIDPolicy(newNameIDPolicy);
		//request.setProtocolBinding(newProtocolBinding);
		//request.setProviderName(newProviderName);
		//request.setRequestedAuthnContext(newRequestedAuthnContext);
		//request.setScoping(newScoping);
		//request.setSubject(newSubject);
		//request.setVersion(newVersion);
		request.setProtocolBinding(SAMLConstants.SAML2_POST_BINDING_URI);
		request.setProviderName("google.com");
		
		final Issuer issuer = ((IssuerBuilder)builderFactory.getBuilder(Issuer.DEFAULT_ELEMENT_NAME)).buildObject();
		issuer.setValue("requestIssuer");
		request.setIssuer(issuer);
		
		final MarshallerFactory marshallerFactory = Configuration.getMarshallerFactory();
		final Marshaller marshaller = marshallerFactory.getMarshaller(request);
		Element responseElmt = null;
		try {
			responseElmt = marshaller.marshall(request);
			final String prettyXML = XMLHelper.prettyPrintXML(responseElmt);
			System.out.println(prettyXML);
		} catch (MarshallingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
