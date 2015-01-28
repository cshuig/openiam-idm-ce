package org.openiam.ui.util;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class URIUtils {
	
	private static Logger LOG = Logger.getLogger(URIUtils.class);
	
	private static final String PROXY_SCHEME_HEADER = "x-openiam-proxy-scheme";
	private static final String PROXY_HOST_HEADER = "x-openiam-proxy-host";
	private static final String HOST_HEADER_NAME = "host";

	public static String getBaseURI(final HttpServletRequest request) throws URISyntaxException {
		
		final String proxySchemeHeader = getProxySchemeHeader(request);
		final String proxyHostHeader = getProxyHostHeader(request);
		final String localHostHeader = getHostHeader(request);
		final String hostHeader = (StringUtils.isNotBlank(proxyHostHeader)) ? proxyHostHeader : localHostHeader;
		String scheme = (StringUtils.isNotBlank(proxySchemeHeader)) ? proxySchemeHeader : new URI(request.getRequestURL().toString()).getScheme();
		scheme = (scheme != null) ? scheme.toLowerCase() : null;
		
		final StringBuilder baseURI = new StringBuilder(String.format("%s://%s", scheme, hostHeader));
		//final StringBuilder baseURI = new StringBuilder(String.format("%s://%s", uri.getScheme(), uri.getHost()));
		/*
		if(uri.getPort() != -1) {
			baseURI.append(String.format(":%s", uri.getPort()));
		}
		 */
	
		if(LOG.isDebugEnabled()) {
			LOG.debug(String.format("Proxy Scheme Header: %s", proxySchemeHeader));
			LOG.debug(String.format("Scheme: %s", scheme));
			LOG.debug(String.format("Base URI: %s", baseURI));
			LOG.debug(String.format("Local Host header: %s", hostHeader));
			LOG.debug(String.format("Proxy Host Header: %s", proxyHostHeader));
		}
		return baseURI.toString();
	}
	
	public static boolean isValidPostbackURL(String postbackURL) {
		postbackURL = (postbackURL != null) ? StringUtils.trimToNull(postbackURL) : null;
		return (postbackURL != null) ? postbackURL.startsWith("/")/* && !postbackURL.contains("<")*/ : true;
	}
	
	public static String getRequestURL(final HttpServletRequest request) {
		try {
			return new StringBuilder(getBaseURI(request)).append(request.getRequestURI()).toString();
		} catch(URISyntaxException e) {
			LOG.error("URI Syntax Exception", e);
			return null;
		}
	}
	
	public static boolean isSecure(final HttpServletRequest request) {
		return "https".equalsIgnoreCase(getProxySchemeHeader(request));
	}
	
	public static String getProxySchemeHeader(final HttpServletRequest request) {
		return HeaderUtils.getCaseInsensitiveHeader(request, PROXY_SCHEME_HEADER);
	}
	
	private static String getHostHeader(final HttpServletRequest request) {
		return HeaderUtils.getCaseInsensitiveHeader(request, HOST_HEADER_NAME);
	}
	
	private static String getProxyHostHeader(final HttpServletRequest request) {
		return HeaderUtils.getCaseInsensitiveHeader(request, PROXY_HOST_HEADER);
	}
}
