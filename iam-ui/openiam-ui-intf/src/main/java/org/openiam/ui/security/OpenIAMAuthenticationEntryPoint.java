package org.openiam.ui.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

public class OpenIAMAuthenticationEntryPoint extends AbstractPreLoginHandler implements AuthenticationEntryPoint {

	private static final Log log = LogFactory.getLog(OpenIAMAuthenticationEntryPoint.class);
	
	@Override
	public void commence(final HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		sendRedirect(request, response);
	}
}
