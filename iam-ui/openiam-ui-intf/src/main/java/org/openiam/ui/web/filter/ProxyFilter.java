package org.openiam.ui.web.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openiam.ui.util.HeaderUtils;

public class ProxyFilter implements Filter {
	
	private static final Logger LOG = Logger.getLogger(ProxyFilter.class);
	
	private static final String PROXY_HOST = "x-openiam-proxy-host";

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain filter) throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest)req;
		request.setAttribute(PROXY_HOST, StringUtils.isNotBlank(HeaderUtils.getCaseInsensitiveHeader(request, PROXY_HOST)));
		filter.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
	
	public static boolean isProxyRequest(final HttpServletRequest request) {
		return ((Boolean)request.getAttribute(PROXY_HOST)).booleanValue();
	}
}
