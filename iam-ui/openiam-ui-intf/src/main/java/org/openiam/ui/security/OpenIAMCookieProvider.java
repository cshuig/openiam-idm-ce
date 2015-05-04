package org.openiam.ui.security;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.srvc.auth.dto.SSOToken;
import org.openiam.idm.srvc.auth.dto.Subject;
import org.openiam.idm.srvc.auth.service.AuthenticationService;
import org.openiam.idm.srvc.auth.ws.AuthenticationResponse;
import org.openiam.idm.srvc.policy.dto.Policy;
import org.openiam.ui.rest.api.model.LoginAjaxResponse;
import org.openiam.ui.security.cryptor.CookieCryptor;
import org.openiam.ui.security.model.OpenIAMAuthCookie;
import org.openiam.ui.util.CookieUtils;
import org.openiam.ui.util.URIUtils;
import org.openiam.ui.web.filter.ProxyFilter;
import org.openiam.ui.web.model.AuthTokenInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.net.URL;
import java.util.Date;
import java.util.Locale;

@Component
public class OpenIAMCookieProvider {

    private static Logger LOG = Logger.getLogger(OpenIAMCookieProvider.class);

    @Resource(name = "authServiceClient")
    private AuthenticationService authServiceClient;

    @Autowired
    private CookieCryptor cookieCryptor;

    public static final String AUTH_COOKIE_INFO = "AUTH_COOKIE_INFO";
    private static final String COOKIE_NAME = "OPENIAM_AUTH_TOKEN";
    private static final String COOKIE_RENEWED_ATTR = "AUTH_COOKIE_RENEWED";
    private static final String COOKIE_RENEW_FAILED = "AUTH_COOKIE_RENEW_FAILED";
    private static final String COOKIE_LOCALE = "OPENIAM_LOCALE";

    @Value("${org.openiam.auth.cookie.domain}")
    private String cookieDomain;

    @Value("${org.openiam.ui.auth.cookie.encryption.enabled}")
    private boolean cookieEncryptionEnabled;

    //@Value("${org.openiam.ui.auth.cookie.include.timestamp}")
    //private boolean includeTimestamp;
    
    public AuthTokenInfo generateNewToken(final String oldToken) {
    	AuthTokenInfo retVal = null;
        final OpenIAMAuthCookie oldCookie = decrypt(oldToken);
        if (oldCookie != null) {
            final String oldTokenType = oldCookie.getTokenType();
            final Response authResponse = authServiceClient.renewToken(oldCookie.getPrincipal(), oldCookie.getToken(), oldTokenType);
            //if(includeTimestamp) {
            if (oldCookie.isExpired()) {
                authResponse.fail();
            }
            //}
            if (authResponse != null && authResponse.isSuccess()) {
                if (authResponse.getResponseValue() instanceof SSOToken) {
                    final SSOToken ssoToken = (SSOToken) authResponse.getResponseValue();
                    if (ssoToken != null) {
                        final String token = ssoToken.getToken();
                        final String tokenType = ssoToken.getTokenType();
                        final String userId = ssoToken.getUserId();
                        final String principal = ssoToken.getPrincipal();
                        try {
                            //retVal = new OpenIAMAuthCookie(userId, principal, token, tokenType, (includeTimestamp) ? ssoToken.getExpirationTime() : null);
                            final OpenIAMAuthCookie newCookie = new OpenIAMAuthCookie(userId, principal, token, tokenType, ssoToken.getExpirationTime());
                            retVal = getAuthToken(newCookie, ssoToken);
                        } catch (Throwable e) {
                            LOG.warn(String.format("Can't encrypt cookie", ssoToken), e);
                        }
                    }
                }
            }
        }
        return retVal;
    }

    /**
     * Called to renew the current Authentication Token
     *
     * @param request - the current HttpServletRequest
     * @return - the new OpenIAMAuthCookie, representing the identity of the
     * User
     */
    public OpenIAMAuthCookie renew(final HttpServletRequest request, final HttpServletResponse response) {
        OpenIAMAuthCookie retVal = null;
        final OpenIAMAuthCookie oldCookie = decrypt(request);
        if (!ProxyFilter.isProxyRequest(request)) {
            LOG.debug("is not a proxy request - renewing cookie");
            if (oldCookie != null) {
                final String oldTokenType = oldCookie.getTokenType();
                final Response authResponse = authServiceClient.renewToken(oldCookie.getPrincipal(), oldCookie.getToken(), oldTokenType);
                //if(includeTimestamp) {
                if (oldCookie.isExpired()) {
                    authResponse.fail();
                }
                //}
                if (authResponse != null && authResponse.isSuccess()) {
                    if (authResponse.getResponseValue() instanceof SSOToken) {
                        final SSOToken ssoToken = (SSOToken) authResponse.getResponseValue();
                        if (ssoToken != null) {
                            final String token = ssoToken.getToken();
                            final String tokenType = ssoToken.getTokenType();
                            final String userId = ssoToken.getUserId();
                            final String principal = ssoToken.getPrincipal();
                            try {
                                //retVal = new OpenIAMAuthCookie(userId, principal, token, tokenType, (includeTimestamp) ? ssoToken.getExpirationTime() : null);
                                retVal = new OpenIAMAuthCookie(userId, principal, token, tokenType, ssoToken.getExpirationTime());
                                final AuthTokenInfo authTokenInfo = encryptAndAdd(request, response, retVal, ssoToken);
                                request.setAttribute(COOKIE_RENEWED_ATTR, retVal);
                                request.setAttribute(AUTH_COOKIE_INFO, authTokenInfo);
                            } catch (Throwable e) {
                                LOG.warn(String.format("Can't encrypt cookie", ssoToken), e);
                            }
                        }
                    }
                } else {
                    request.setAttribute(COOKIE_RENEW_FAILED, true);
                }
            }
        } else {
            LOG.debug("Is proxy request - using cookie from proxy");
            retVal = oldCookie;
            request.setAttribute(COOKIE_RENEWED_ATTR, retVal);
        }
        return retVal;
    }

    public boolean cookieRenewFailedForThisRequest(final HttpServletRequest request) {
        return Boolean.TRUE.equals(request.getAttribute(COOKIE_RENEW_FAILED));
    }

    public OpenIAMAuthCookie getTokenForCurrentRequest(final HttpServletRequest request) {
        return (OpenIAMAuthCookie) request.getAttribute(COOKIE_RENEWED_ATTR);
    }

    /**
     * Removes the AuthCookie from your request
     *
     * @param response - HttpServletResponse of this request
     */
    public void invalidate(final HttpServletRequest request, final HttpServletResponse response) {
        final Cookie cookie = CookieUtils.getCookie(request, COOKIE_NAME);
        if (cookie != null) {
            cookie.setDomain(getCookieDomain(request));
            cookie.setValue(null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }
    
    public void setSAMLPostbackURL(final HttpServletRequest request,
    							   final HttpServletResponse response,
    							   final String name, 
    							   final String value) {
    	final Cookie cookie = new Cookie(name, value);
    	cookie.setPath("/");
        cookie.setSecure(StringUtils.equalsIgnoreCase("https", request.getScheme()));
        cookie.setDomain(getCookieDomain(request));
        cookie.setHttpOnly(true);
        cookie.setMaxAge(30 * 60); /* 30 secs */
        response.addCookie(cookie);
    }
    
    public String getAndRemoveSAMLPostbackURL(final HttpServletRequest request, 
    									      final HttpServletResponse response,
    									      final String name) {
    	String retVal = null;
    	final Cookie cookie = CookieUtils.getCookie(request, name);
		if(cookie != null) {
			retVal = cookie.getValue();
			cookie.setPath("/");
	        cookie.setSecure(StringUtils.equalsIgnoreCase("https", request.getScheme()));
	        cookie.setDomain(getCookieDomain(request));
	        cookie.setHttpOnly(true);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
		return retVal;
    }

    public String getUserId(final HttpServletRequest request) {
        String userId = null;
        final SecurityContext ctx = SecurityContextHolder.getContext();
        if (ctx != null) {
            if (ctx.getAuthentication() != null && ctx.getAuthentication().getCredentials() != null) {
                userId = (String) ctx.getAuthentication().getCredentials();
            }
        }

        /*
         * for urls not protected by spring security, the context will be null,
         * so use the session
         */
        /*
         * if(userId == null) { final OpenIAMAuthCookie token =
         * decrypt(request); if(token != null) { userId = token.getUserId(); } }
         */

        return userId;
    }

    public String getUserIdFromCookie(final HttpServletRequest request) {
        String userId = null;
        final OpenIAMAuthCookie token = decrypt(request);
        if (token != null) {
            userId = token.getUserId();
        }
        return userId;
    }

    public String getUserName(final HttpServletRequest request) {
        String userName = null;
        final SecurityContext ctx = SecurityContextHolder.getContext();
        if (ctx != null) {
            if (ctx.getAuthentication() != null && ctx.getAuthentication().getName() != null) {
                userName = (String) ctx.getAuthentication().getName();
            }
        }
        return userName;
    }

    public String getPrincipal(final HttpServletRequest request) {
        String principal = null;
        final OpenIAMAuthCookie cookie = this.decrypt(request);
        if (cookie != null) {
            principal = cookie.getPrincipal();
        }
        return principal;
    }

    /**
     * @param request - the current HttpServletRequest
     * @return - the current OpenIAMAuthCookie - does NOT renew the token
     */
    private OpenIAMAuthCookie decrypt(final HttpServletRequest request) {
        OpenIAMAuthCookie retVal = null;
        final Cookie cookie = CookieUtils.getCookie(request, COOKIE_NAME);

        if (cookie != null && cookie.getMaxAge() != 0) {
            retVal = decrypt(cookie.getValue());
        }

        return retVal;
    }
    
    private OpenIAMAuthCookie decrypt(final String value) {
    	 try {
    		 final String cookieValue = (cookieEncryptionEnabled) ? cookieCryptor.decode(value) : value;
    		 return OpenIAMAuthCookie.getToken(cookieValue);
    	 } catch (Throwable e) {
             LOG.warn(String.format("Can't decrypt cookie", value), e);
             return null;
         }
    }
    
    public AuthTokenInfo setAuthInfo(final HttpServletRequest request, 
									 final HttpServletResponse response, 
									 final String principal,
									 final AuthenticationResponse authResponse,
									 final boolean addCookieToResponse) throws Exception {
    	final Subject subject = authResponse.getSubject();
        final String userId = subject.getUserId();

        final SSOToken ssoToken = authResponse.getSubject().getSsoToken();
        final String tokenType = ssoToken.getTokenType();
        //final OpenIAMAuthCookie token = new OpenIAMAuthCookie(userId, principal, ssoToken.getToken(), tokenType, (includeTimestamp) ? ssoToken.getExpirationTime() : null);
        final OpenIAMAuthCookie token = new OpenIAMAuthCookie(userId, principal, ssoToken.getToken(), tokenType, ssoToken.getExpirationTime());
        if(addCookieToResponse) {
        	return encryptAndAdd(request, response, token, ssoToken);
        } else {
        	return getAuthToken(token, ssoToken);
        }
    }

    public AuthTokenInfo setAuthInfo(final HttpServletRequest request, 
    						final HttpServletResponse response, 
    						final String principal,
                            final AuthenticationResponse authResponse) throws Exception {

        return setAuthInfo(request, response, principal, authResponse, true);
    }
    
    private String encrypt(final OpenIAMAuthCookie token) throws Exception {
    	final String cookieValue = (cookieEncryptionEnabled) ? cookieCryptor.encode(token.tokenizeValue()) : token.tokenizeValue();
    	return cookieValue;
    }
    
    private int getExpirationTimeInSeconds(final SSOToken ssoToken) {
    	final int seconds = Long.valueOf((ssoToken.getExpirationTime().getTime() - new Date().getTime()) / 1000).intValue();
    	return seconds;
    }
    
    private AuthTokenInfo getAuthToken(final OpenIAMAuthCookie token,
            						   final SSOToken ssoToken) throws Exception {
        final int seconds = getExpirationTimeInSeconds(ssoToken);
        final String encryptedString = encrypt(token);
        final AuthTokenInfo authTokenInfo = new AuthTokenInfo();
        authTokenInfo.setAuthToken(encryptedString);
        authTokenInfo.setTimeToLiveSeconds(seconds);
        return authTokenInfo;
    }

    private AuthTokenInfo encryptAndAdd(final HttpServletRequest request, 
    						   final HttpServletResponse response, 
    						   final OpenIAMAuthCookie token,
                               final SSOToken ssoToken) throws Exception {
        final int seconds = getExpirationTimeInSeconds(ssoToken);

        final String cookieValue = encrypt(token);
        final Cookie cookie = new Cookie(COOKIE_NAME, cookieValue);
        int cookieMaxAge = -1;
        if (!ssoToken.isExpireOnBrowserClose()) {
            cookieMaxAge = seconds;
        }
        cookie.setMaxAge(cookieMaxAge);
        cookie.setPath("/");
        cookie.setSecure(StringUtils.equalsIgnoreCase("https", request.getScheme()));
        cookie.setDomain(getCookieDomain(request));
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        final AuthTokenInfo authTokenInfo = new AuthTokenInfo();
        authTokenInfo.setAuthToken(cookieValue);
        authTokenInfo.setTimeToLiveSeconds(cookieMaxAge);
        return authTokenInfo;
    }

    private String getCookieDomain(final HttpServletRequest request) {
        String domain = null;
        if (StringUtils.isNotBlank(cookieDomain)) {
            domain = cookieDomain;
        } else {
            try {
                domain = new URL(URIUtils.getRequestURL(request)).getHost();
            } catch (Throwable e) {

            }
        }
        return domain;
    }

    public static Locale getLocale(final HttpServletRequest request) {
        final Cookie cookie = CookieUtils.getCookie(request, COOKIE_LOCALE);
        String locale = null;
        if (cookie != null) {
            locale = cookie.getValue();
        }
        return (locale != null) ? new Locale(locale) : null;
    }

    public void setLocale(final HttpServletRequest request, final HttpServletResponse response, Locale locale) {
        final Cookie cookie = new Cookie(COOKIE_LOCALE, locale.getLanguage());
        cookie.setMaxAge(604800); // 7 days
        cookie.setPath("/");
        cookie.setDomain(getCookieDomain(request));
        response.addCookie(cookie);
    }
}
