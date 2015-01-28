package org.openiam.ui.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

public class OpenIAMLogoutSuccessHandler extends AbstractPreLoginHandler implements LogoutSuccessHandler {
	
	@Autowired
	private OpenIAMCookieProvider cookieProvider;
	
	@Override
	public void onLogoutSuccess(final HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) throws IOException,
			ServletException {
		cookieProvider.invalidate(request, response);
		sendRedirect(request, response, request.getRequestURI());
		SecurityContextHolder.clearContext();
	}

}
