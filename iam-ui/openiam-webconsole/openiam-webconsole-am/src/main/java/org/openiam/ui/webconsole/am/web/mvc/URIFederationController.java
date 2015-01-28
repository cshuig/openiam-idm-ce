package org.openiam.ui.webconsole.am.web.mvc;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openiam.am.srvc.uriauth.dto.SSOLoginResponse;
import org.openiam.am.srvc.uriauth.dto.URIFederationResponse;
import org.openiam.am.srvc.ws.ContentProviderWebService;
import org.openiam.am.srvc.ws.URIFederationWebService;
import org.openiam.base.ws.Response;
import org.openiam.ui.web.mvc.AbstractController;
import org.openiam.ui.webconsole.am.web.model.URIFederationRequestModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.stereotype.Controller;

@Controller
public class URIFederationController extends AbstractController {

	@Resource(name="uriFederationServiceClient")
	private URIFederationWebService uriFederationWebService;
	
	@RequestMapping(value="/federateURIDebug", method=RequestMethod.GET)
	public String federateURIDebugGET(final HttpServletRequest request) {
		return "content/debug/uriFederation";
	}
	
	@RequestMapping(value="/federateURIDebug", method=RequestMethod.POST)
	public String federateURIDebugPOST(final HttpServletRequest request, final HttpServletResponse response, final URIFederationRequestModel uriFederationModel) {
		request.setAttribute("uriFederationRequestModel", uriFederationModel);
		
		final URIFederationResponse federationResponse = 
				uriFederationWebService.federateProxyURI(uriFederationModel.getUserId(), -1, uriFederationModel.getUrl());
		request.setAttribute("federationResponse", federationResponse);
		return federateURIDebugGET(request);
	}
	
	@RequestMapping(value="/proxyURIPrincipalDebug", method=RequestMethod.GET)
	public String proxyURIPrincipalDebugGET(final HttpServletRequest request) {
		return "content/debug/proxyURIPrincipal";
	}
	
	@RequestMapping(value="/proxyURIPrincipalDebug", method=RequestMethod.POST)
	public String proxyURIPrincipalDebugPOST(final HttpServletRequest request, 
											 final @RequestParam(required=true, value="principal") String principal,
											 final @RequestParam(required=true, value="proxyURI") String proxyURI) {
		
		final SSOLoginResponse response = uriFederationWebService.getCookieFromProxyURIAndPrincipal(proxyURI, principal);
		request.setAttribute("debugResponse", response);
		request.setAttribute("principal", principal);
		request.setAttribute("proxyURI", proxyURI);
		return proxyURIPrincipalDebugGET(request);
	}
}
