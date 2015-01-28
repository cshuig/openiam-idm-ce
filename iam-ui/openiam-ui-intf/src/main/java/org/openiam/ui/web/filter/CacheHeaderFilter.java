package org.openiam.ui.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openiam.ui.util.OpenIAMConstants;

/**
 * @author Lev
 * Should be in web.xml
 * Disable caching of everything in all browsers, since all of our pages are dynamic
 * (IE especially likes to cache dynamic content as static)
 */
public final class CacheHeaderFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest httpRequest = (HttpServletRequest)request;
		final HttpServletResponse httpResponse = (HttpServletResponse)response;
		httpResponse.setHeader("Cache-Control","no-cache"); 
		httpResponse.setHeader("Pragma","no-cache"); 
		httpResponse.setDateHeader ("Expires", -1); 
		httpRequest.setCharacterEncoding(OpenIAMConstants.UTF_8);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
	}

}
