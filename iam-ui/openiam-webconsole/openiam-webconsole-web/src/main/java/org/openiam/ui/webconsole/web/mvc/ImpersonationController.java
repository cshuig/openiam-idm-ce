package org.openiam.ui.webconsole.web.mvc;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openiam.authmanager.common.model.AuthorizationResource;
import org.openiam.authmanager.ws.request.UserToResourceAccessRequest;
import org.openiam.base.ws.Response;
import org.openiam.idm.srvc.auth.dto.Login;
import org.openiam.idm.srvc.auth.ws.AuthenticationResponse;
import org.openiam.idm.srvc.auth.ws.LoginResponse;
import org.openiam.ui.login.LoginActionToken;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.mvc.AbstractController;
import org.openiam.ui.web.mvc.AbstractLoginController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ImpersonationController extends AbstractLoginController {
	
	private static final Log LOG = LogFactory.getLog(ImpersonationController.class);
	
	@Value("${org.openiam.ui.impersonate.resource.name}")
	private String authResource;

	@RequestMapping(value="/impersonate",method=RequestMethod.GET)
	public String impersonateGET(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		if(!isEntitledToResource(request)) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return null;
		}
		return "users/impersonate";
	}
	
	@RequestMapping(value="/impersonate",method=RequestMethod.POST)
	public String impersonatePost(final HttpServletRequest request,
								  final HttpServletResponse response,
								  final @RequestParam(required=true, value="id") String id) throws IOException {
		if(!isEntitledToResource(request)) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return null;
		}
		
		ErrorToken error = null;
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		try {
			final LoginResponse loginResponse = loginServiceClient.getPrimaryIdentity(id);
			if(loginResponse == null) {
				throw new NullPointerException("loginResponse was null");
			}
			
			final Login login = loginResponse.getPrincipal();
			if(login == null) {
				throw new NullPointerException("login was null");
			}
			
			final Response passwordResponse = loginServiceClient.decryptPassword(login.getUserId(), login.getPassword());
			if(passwordResponse.isFailure()) {
				throw new Exception("Could not decrypt password");
			}
			
			final String password = (String)passwordResponse.getResponseValue();
			
			final LoginActionToken loginActionToken = getLoginActionToken(request, response, login.getLogin(), password);
			if(loginActionToken.isSuccess()) {
				doLogout(request, response, false);
				cookieProvider.setAuthInfo(request, response, login.getLogin(), loginActionToken.getAuthResponse());
				ajaxResponse.setRedirectURL(request.getContextPath());
			} else { /* failure  or expired password or warning */
				if(loginActionToken.getHttpErrorCode() != null) {
					error = new ErrorToken(Errors.IMPERSIONATION_FAILED);
				} else {
					error = new ErrorToken(getErrorFromCode(loginActionToken.getErrCode()));
				}
			}
		} catch(Throwable e) {
			error = new ErrorToken(Errors.IMPERSIONATION_FAILED);
			LOG.error("Can't impersonate", e);
		} finally {
			if(error != null) {
				ajaxResponse.setStatus(500);
				ajaxResponse.addError(error);
			} else {
				ajaxResponse.setStatus(200);
				ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.IMPERSONATION_SUCCESS));
			}
			request.setAttribute("response", ajaxResponse);
		}
		return "common/basic.ajax.response";
	}
	
	private boolean isEntitledToResource(final HttpServletRequest request) {
		
		final AuthorizationResource resource = new AuthorizationResource();
		resource.setName(authResource);
		
		final UserToResourceAccessRequest accessRequest = new UserToResourceAccessRequest();
		accessRequest.setUserId(getRequesterId(request));
		accessRequest.setResource(resource);
		return authorizationManager.isUserEntitledTo(accessRequest).getResult();
	}
}
