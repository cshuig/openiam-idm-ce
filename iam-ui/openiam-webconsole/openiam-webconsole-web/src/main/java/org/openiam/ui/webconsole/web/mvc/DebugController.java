package org.openiam.ui.webconsole.web.mvc;

import javax.servlet.http.HttpServletRequest;

import org.openiam.authmanager.common.model.AuthorizationResource;
import org.openiam.authmanager.ws.request.UserToResourceAccessRequest;
import org.openiam.authmanager.ws.response.AccessResponse;
import org.openiam.ui.web.mvc.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DebugController extends AbstractController {

	@RequestMapping(method=RequestMethod.GET, value="/authorizationDebug")
	public String authorizationDebugGET(final HttpServletRequest request) {
		return "debug/authorizationDebug";
	}
	

	@RequestMapping(method=RequestMethod.POST, value="/authorizationDebug")
	public String authorizationDebugPOST(final HttpServletRequest request,
										 final @RequestParam(required=true, value="userId") String userId,
										 final @RequestParam(required=true, value="resourceName") String resourceName) {
		final AuthorizationResource resource = new AuthorizationResource();
		resource.setName(resourceName);
		final UserToResourceAccessRequest accessRequest = new UserToResourceAccessRequest();
		accessRequest.setUserId(userId);
		accessRequest.setResource(resource);
		final AccessResponse accessResponse = authorizationManager.isUserEntitledTo(accessRequest);
		request.setAttribute("authorizationResponse", accessResponse.getResult());
		
		request.setAttribute("userId", userId);
		request.setAttribute("resourceName", resourceName);
		return authorizationDebugGET(request);
	}
}
