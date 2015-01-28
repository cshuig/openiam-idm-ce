package org.openiam.ui.security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openiam.ui.security.http.OpeniamHttpServletRequest;
import org.openiam.ui.util.OpenIAMConstants;
import org.openiam.ui.util.URIUtils;

public abstract class AbstractPreLoginHandler {
	
	private static Logger LOG = Logger.getLogger(AbstractPreLoginHandler.class);

	private String loginURL;
	private Set<String> postURIsToConvertToGet;
	
	public void setLoginURL(final String loginUrl) {
		this.loginURL = loginUrl;
	}
	
	public void setPostURIsToConvertToGet(Set<String> postURIsToConvertToGet) {
		this.postURIsToConvertToGet = postURIsToConvertToGet;
	}

	/* gets the URL to redirect to, in case the user has not logged in */
	protected String getRedirectURL(final HttpServletRequest request, String postbackURL) throws UnsupportedEncodingException {
		
		/* construct original request URI */
		String originalRequestURI = request.getRequestURI();
		//originalRequestURI = (originalRequestURI.endsWith("/")) ? originalRequestURI.substring(0, originalRequestURI.length() - 1) : originalRequestURI;
		final StringBuilder requestURI = new StringBuilder(originalRequestURI);
		if(StringUtils.equals("GET", request.getMethod())) {
			if(request.getQueryString() != null) {
				requestURI.append("?").append(request.getQueryString());
			}
		} else { /* POST */
			
			/* if this is one of the URLs to convert to GET, do it.  Useful for SAML */
			if(postURIsToConvertToGet != null && postURIsToConvertToGet.contains(request.getRequestURI())) {
				if(MapUtils.isNotEmpty(request.getParameterMap())) {
					requestURI.append("?");
					final int numOfParams = request.getParameterMap().size();
					int i = 0;
					for(final Object key : request.getParameterMap().keySet()) {
						final String keyStr = (String)key;
						final String value = request.getParameter(keyStr);
						requestURI.append(String.format("%s=%s", keyStr, URLEncoder.encode(value, OpenIAMConstants.UTF_8)));
						if(i++ < numOfParams - 1) {
							requestURI.append("&");
						}
					}
					if(numOfParams > 0) {
						requestURI.append("&");
					}
					requestURI.append("X_OPENIAM_POST=true");
				}
			}
		}
		
		/* the 'final' URL to redirect to, in case of login success */
		postbackURL = (postbackURL != null) ? postbackURL : requestURI.toString();
		
		/* session setter URL */
		return getLoginURIWithPostback(postbackURL);
	}
	
	protected String getLoginURIWithPostback(String postbackURL) throws UnsupportedEncodingException {
		postbackURL = URLEncoder.encode(postbackURL, OpenIAMConstants.UTF_8);
		final StringBuilder url = new StringBuilder(loginURL);
		url.append((loginURL.contains("?") ? "&" : "?"));
		url.append(OpenIAMConstants.POSTBACK_URL).append("=").append(postbackURL);
		return url.toString();
	}
	
	public void sendRedirect(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		final String redirectURL = getRedirectURL(request, null);
		response.sendRedirect(redirectURL);
	}
	
	public void sendRedirect(final HttpServletRequest request, final HttpServletResponse response, final String postBackURL) throws IOException {
		final String redirectURL = getRedirectURL(request, postBackURL);
		response.sendRedirect(redirectURL);
	}
}
