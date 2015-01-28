package org.openiam.ui.security;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openiam.authmanager.service.AuthorizationManagerMenuWebService;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.idm.srvc.user.ws.UserDataWebService;
import org.openiam.ui.security.model.OpenIAMAuthCookie;
import org.openiam.ui.web.filter.ContentProviderFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OpenIAMPreAuthFilter extends AbstractPreAuthenticatedProcessingFilter {
	
	
	private static final Log log = LogFactory.getLog(OpenIAMPreAuthFilter.class);
	
	@Autowired
	private OpenIAMCookieProvider cookieProvider;

	@Resource(name="userServiceClient")
	private UserDataWebService userServiceClient;

	@Override
	protected Object getPreAuthenticatedCredentials(final HttpServletRequest request) {
		String userId = null;
		final OpenIAMAuthCookie token = cookieProvider.getTokenForCurrentRequest(request);
		if(token != null) {
			userId = token.getUserId();
		}
		if(userId == null) {
			if(!ContentProviderFilter.currentRequestRequiresAuthentication(request)) {
				log.debug(String.format("Current URI marked as 'open' - using -1 as current user"));
				userId = "-1";
			}
		}
		
		return userId;
	}

	@Override
	protected Object getPreAuthenticatedPrincipal(final HttpServletRequest request) {
		String userName = null;
		final OpenIAMAuthCookie token = cookieProvider.getTokenForCurrentRequest(request);
		if(token != null) {
			final String userId = token.getUserId();
			if(StringUtils.isNotBlank(userId)) {
				final User user = userServiceClient.getUserWithDependent(userId,null, false);
				userName = user.getDisplayName();
			}
		}
		if(userName == null) {
			if(!ContentProviderFilter.currentRequestRequiresAuthentication(request)) {
				log.debug(String.format("Current URI marked as 'open' - using Unkonwn as user principal"));
				userName = "Unkonwn";
			}
		}
		return userName;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest httpRequest = (HttpServletRequest)request;
		final HttpServletResponse httpResponse = (HttpServletResponse)response;

		final OpenIAMAuthCookie newCookie = cookieProvider.renew(httpRequest, httpResponse);
		if(cookieProvider.cookieRenewFailedForThisRequest(httpRequest)) {
			cookieProvider.invalidate(httpRequest, httpResponse);
			SecurityContextHolder.clearContext();
		} else if(newCookie == null) { /* if there's no cookie, clear the context */
			//if(ContentProviderFilter.currentRequestRequiresAuthentication(httpRequest)) {
				SecurityContextHolder.clearContext();
			//} else {
			//	log.debug(String.format("Current URI marked as 'open' - not clearing context"));
			//}
		}
        super.doFilter(request, response, chain);
	}
	
	
}
