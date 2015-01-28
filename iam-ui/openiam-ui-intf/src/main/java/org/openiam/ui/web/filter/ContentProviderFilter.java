package org.openiam.ui.web.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openiam.am.srvc.uriauth.dto.URIFederationResponse;
import org.openiam.am.srvc.ws.URIFederationWebService;
import org.openiam.ui.util.HeaderUtils;
import org.openiam.ui.util.SpringContextProvider;
import org.openiam.ui.util.URIUtils;
import org.openiam.ui.web.util.ContentProviderCacheProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class ContentProviderFilter implements Filter {
	
	private static final String PATTERN_HEADER = "x-openiam-uri-pattern-id";
	private static final String CP_HEADER = "x-openiam-cp-id";
	
	private static final String OVERRIDE_CSS_ATTR = "contentProviderOverrideCSS";
	private static final String PATTERN_REQUIRES_AUTHENTIATION = "PatternRequiresAuthentication";
	
	private static final Logger LOG = Logger.getLogger(ContentProviderFilter.class);
	
	@Autowired
	private ContentProviderCacheProvider contentProviderCacheProvider;
	
	@Autowired
	private URIFederationWebService uriFederationServiceClient;
	
	/*
	 * These URL patterns are special.  They ALWAYS require the user to go through Spring Security, period.
	 * In case the COntent Provider is set up so that these patterns are public (for example, SAML IDP
	 * is configured this way, b/c we have to save POST requests, which the proxy doesn't do), these urls still
	 * require authentication
	 */
	@Value("${org.openiam.ui.url.pattenrs.always.require.auth}")
	private String urlPatternsRequiringAuthentication;
	
	private Set<String> urlsAlwaysRequiringAuthenticationSet = new HashSet<>();

	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
		SpringContextProvider.autowire(this);
		SpringContextProvider.resolveProperties(this);
		for(final String url : StringUtils.split(urlPatternsRequiringAuthentication, ",")) {
			urlsAlwaysRequiringAuthenticationSet.add(StringUtils.trimToEmpty(url).toLowerCase());
		}
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest httpRequest = (HttpServletRequest)request;
		boolean isRequestFromProxy = true;
		String contentProviderId = HeaderUtils.getCaseInsensitiveHeader(httpRequest, CP_HEADER);
		String patternId = HeaderUtils.getCaseInsensitiveHeader(httpRequest, PATTERN_HEADER);
		if(patternId == null) {
			isRequestFromProxy = false;
			//final AuthLevel passwordLevel = contentProviderCacheProvider.getCachedAuthLevel(passwordAuthLevelId);
			final String currentURI = URIUtils.getRequestURL(httpRequest);
			
			if(LOG.isDebugEnabled()) {
				LOG.debug(String.format("Proxy did not send Pattern header - retreiving via Federation Service. Current URI: '%s'", currentURI));
			}
			
			//final int authLevel = (passwordLevel != null) ? passwordLevel.getLevel() : 0;
			final URIFederationResponse federationResponse = uriFederationServiceClient.getMetadata(currentURI);
			if(federationResponse != null) {
				patternId = federationResponse.getPatternId();
				contentProviderId = federationResponse.getCpId();
			}
		}
		
		if(LOG.isDebugEnabled()) {
			LOG.debug(String.format("URI Pattern ID: %s, Content Provider ID: %s, is From Proxy: %s.  User ID: %s", patternId,  contentProviderId, isRequestFromProxy, null));
		}
		
		final String overrideCSS = contentProviderCacheProvider.getOverrideStylesheetForPatternId(patternId);
		
		boolean patternRequiresAuthentication = contentProviderCacheProvider.patternRequiresAuthentiation(patternId);
		if(urlsAlwaysRequiringAuthenticationSet.contains(httpRequest.getRequestURI().toLowerCase())) {
			patternRequiresAuthentication = true;
		}
		
		request.setAttribute(PATTERN_REQUIRES_AUTHENTIATION, patternRequiresAuthentication);
		request.setAttribute(OVERRIDE_CSS_ATTR, overrideCSS);
		request.setAttribute(CP_HEADER, contentProviderId);
		request.setAttribute(PATTERN_HEADER, patternId);
		
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * A user can say that a URI isn't protected in selfservice.  If so, this returns 'true' if the pattern matches.
	 * @param request
	 * @return
	 */
	public static boolean currentRequestRequiresAuthentication(final HttpServletRequest request) {
		return (request != null) ? (Boolean)request.getAttribute(PATTERN_REQUIRES_AUTHENTIATION) : true;
	}

	public static String getURIPatternForRequest(final HttpServletRequest request) {
		return (String)request.getAttribute(PATTERN_HEADER);
	}
	
	public static String getContentProviderForRequest(final HttpServletRequest request) {
		return (String)request.getAttribute(CP_HEADER);
	}
	
	public static String getOverrideStylesheet(final HttpServletRequest request) {
		return (String)request.getAttribute(OVERRIDE_CSS_ATTR);
	}
}
