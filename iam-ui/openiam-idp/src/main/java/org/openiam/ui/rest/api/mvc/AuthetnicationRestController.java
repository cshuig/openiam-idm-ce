package org.openiam.ui.rest.api.mvc;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.openiam.idm.srvc.policy.dto.Policy;
import org.openiam.ui.login.LoginActionToken;
import org.openiam.ui.rest.api.model.LoginAjaxResponse;
import org.openiam.ui.security.OpenIAMCookieProvider;
import org.openiam.ui.util.URIUtils;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.web.model.AuthTokenInfo;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.mvc.AbstractLoginController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/auth")
public class AuthetnicationRestController extends AbstractLoginController {

	/* protected, so token will be in request param */
	@RequestMapping(value="/renewToken", method=RequestMethod.GET)
	public @ResponseBody AuthTokenInfo renewToken(final HttpServletRequest request,
												  final HttpServletResponse response) throws IOException {
		final Object o = request.getAttribute(OpenIAMCookieProvider.AUTH_COOKIE_INFO);
		if(o == null) {
			return null;
		} else if(!(o instanceof AuthTokenInfo)) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			log.error("Auth token not of required type");
			return null;
		} else {
			return (AuthTokenInfo)o;
		}
	}
	
	@RequestMapping(value="/renewTokenWithString", method=RequestMethod.GET)
	public @ResponseBody AuthTokenInfo renewTokenWithString(final HttpServletResponse response,
															final @RequestParam(required=true, value="token") String token) throws IOException {
		try {
			return cookieProvider.generateNewToken(token);
		} catch(Throwable e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			log.error("Can't renew token via restful call", e);
			return null;
		}
	}

}
