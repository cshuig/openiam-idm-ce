package org.openiam.ui.idp.web.mvc;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openiam.ui.idp.saml.model.SAMLIDPMetadataResponse;
import org.openiam.ui.idp.saml.model.SAMLLogoutResponseToken;
import org.openiam.ui.idp.saml.model.SAMLResponseToken;
import org.openiam.ui.idp.saml.service.SAMLService;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.web.mvc.AbstractLoginController;
import org.openiam.ui.web.util.LoginProvider;
import org.opensaml.xml.util.XMLHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class SAMLController extends AbstractLoginController {
	
	private static Logger log = Logger.getLogger(SAMLController.class);
	
	@Autowired
	private SAMLService samlProvider;
	

	@Autowired
	private LoginProvider loginProvider;
	
	@RequestMapping(value="/SAMLMetadata", method=RequestMethod.GET, produces="text/xml")
	public @ResponseBody String SAMLMetadata(final HttpServletRequest request,
							   final HttpServletResponse response) throws IOException {
		final SAMLIDPMetadataResponse token = samlProvider.getSAMLIDPMetadata(request);
		if(token.isError()) {
			//request.setAttribute("error", new ErrorToken(token.getError()));
			//return "auth/authError";
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, token.getError().toString());
			return null;
		} else {
			return XMLHelper.prettyPrintXML(token.getEntityDescriptorElement());
		}
	}
	
	/*
	@RequestMapping(value="/SAMLLogout", method=RequestMethod.GET)
	public String samlLogout(final HttpServletRequest request,
							 final HttpServletResponse response) throws IOException {
		final SAMLLogoutResponseToken token = samlProvider.processLogoutRequest(request, response);
		if(token.isError()) {
			request.setAttribute("error", new ErrorToken(token.getError()));
			return "auth/authError";
		} else {
			response.sendRedirect("/idp/logout.html");
			return null;
		}
	}
	*/
	
	/* 
	 * this URL is NOT protected by Spring Security, because we need to support SAML POST requests
	 * Standard SAML URL parameters are expected (SAMLRequest, RelayState)
	 */
	@RequestMapping(value="/SAMLLogin", method=RequestMethod.GET)
	public String samlLoginGet(final HttpServletRequest request, 
							   final HttpServletResponse response,
							   final @RequestParam(required=false, value="X_OPENIAM_POST") String wasPost,
							   final @RequestParam(required=true, value="SAMLRequest") String samlRequest,
							   final @RequestParam(required=false, value="RelayState") String relayState) throws IOException, ServletException {
		log.info(String.format("SAMLLogin: %s", request.getMethod()));
		log.info(String.format("SAMLRequest: %s", samlRequest));
		log.info(String.format("RelayState: %s", relayState));
		if(StringUtils.equalsIgnoreCase("true", wasPost)) {
			request.setAttribute("SAMLRequest", samlRequest);
			request.setAttribute("RelayState", relayState);
			return "auth/doSAMLPostBack";
		} else {
			return doSamlLogin(request, response);
		}
	}
	
	@RequestMapping(value="/SAMLLogin", method=RequestMethod.POST)
	public String samlLoginPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletException {
		return doSamlLogin(request, response);
	}
	
	public String doSamlLogin(final HttpServletRequest request, final HttpServletResponse response) {
		final SAMLResponseToken token = samlProvider.samlLogin(request);
		if(token.isError()) {
			request.setAttribute("error", new ErrorToken(token.getError()));
			return "auth/authError";
		} else {
			request.setAttribute("SAMLResponse", token.getEncodedResponse());
			request.setAttribute("acs", token.getAssertionConsumerURL());
			request.setAttribute("RelayState", token.getRelayState());
			return "auth/samlPost";
		}
	}
}
