package org.openiam.ui.security;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openiam.authmanager.service.AuthorizationManagerMenuWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OpenIAMPostAuthFilter extends GenericFilterBean {

    private static final Log log = LogFactory.getLog(OpenIAMPostAuthFilter.class);

    @Autowired
    private OpenIAMCookieProvider cookieProvider;

    @Resource(name="authorizationManagerMenuServiceClient")
    private AuthorizationManagerMenuWebService authorizationManagerMenuServiceClient;

    @Value("${org.openiam.ui.challenge.response.required}")
    private boolean isChallengeResponseRequired;

    @Value("${org.openiam.ui.challenge.response.url}")
    private String challengResponseBaseURL;

    @Value("${org.openiam.ui.use.policy.required}")
    private boolean isUsePolicyRequired;

    @Value("${org.openiam.ui.use.policy.url}")
    private String usePolicyBaseURL;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        final SecurityContext ctx = SecurityContextHolder.getContext();
        final HttpServletRequest httpRequest = (HttpServletRequest)request;
        final HttpServletResponse httpResponse = (HttpServletResponse)response;

        boolean skipMenuCheck = false;
        String redirectURL = null;

        String userId = cookieProvider.getUserId(httpRequest);
        String principal = cookieProvider.getPrincipal(httpRequest);
        String requestURI = httpRequest.getRequestURI();

        final String challengeResponseURL = new StringBuilder(httpRequest.getContextPath()).append(challengResponseBaseURL).toString();
        final String usePolicyConfirmURL = new StringBuilder(httpRequest.getContextPath()).append(usePolicyBaseURL).toString();

        if(isChallengeResponseRequired && !StringUtils.equalsIgnoreCase(usePolicyConfirmURL, requestURI)
                && !StringUtils.equalsIgnoreCase(challengeResponseURL, requestURI)) {
            log.debug(String.format("Challenge Response Required: %s", isChallengeResponseRequired));
            if(ctx != null && ctx.getAuthentication() != null && ctx.getAuthentication().getDetails() != null) {
                log.debug(String.format("Request URI after checking context: %s", requestURI));

                if(!StringUtils.equalsIgnoreCase(challengeResponseURL, requestURI)) {

                    final OpeniamWebAuthenticationDetails details = (OpeniamWebAuthenticationDetails)ctx.getAuthentication().getDetails();
                    final boolean isSecurityQuestionsAnswered = details.isIdentityQuestionsAnswered();
                    log.debug(String.format("Details: %s, questions answered: %s", details, isSecurityQuestionsAnswered));
                    if(!isSecurityQuestionsAnswered) {
                        redirectURL = challengeResponseURL + (challengeResponseURL.contains("?")? challengeResponseURL+"&": "?") + "postbackUrl=" + requestURI + "&auth=true";
                        log.debug(String.format("Security questions for user '%s:%s' not answered - redirecting to questions page: %s", userId, principal, redirectURL));
                    }
                } else {
                    skipMenuCheck = true; // don't check menus for challenge response URL
                }
            } else {
                log.debug(String.format("Context, authentication, or details are null: %s", ctx));
            }
        }

        if(isUsePolicyRequired && redirectURL == null && !StringUtils.equalsIgnoreCase(usePolicyConfirmURL, requestURI)
                && !StringUtils.equalsIgnoreCase(challengeResponseURL, requestURI)) {
            log.debug(String.format("Use Policy Required: %s", isUsePolicyRequired));
            if(ctx != null && ctx.getAuthentication() != null && ctx.getAuthentication().getDetails() != null) {
                log.debug(String.format("Request URI after checking context: %s", requestURI));

                if(!StringUtils.equalsIgnoreCase(usePolicyConfirmURL, requestURI)) {

                    final OpeniamWebAuthenticationDetails details = (OpeniamWebAuthenticationDetails)ctx.getAuthentication().getDetails();
                    final boolean isUsePolicyConfirmed = details.isUsePolicyConfirmed();
                    log.debug(String.format("Details: %s, use policy confirmed: %s", details, isUsePolicyConfirmed));
                    if(!isUsePolicyConfirmed) {
                        redirectURL = usePolicyConfirmURL;
                        log.debug(String.format("Use Policy for user '%s:%s' are not confirmed - redirecting to use policy page: %s", userId, principal, redirectURL));
                    }
                } else {
                    skipMenuCheck = true; // don't check menus for challenge response URL
                }
            } else {
                log.debug(String.format("Context, authentication, or details are null: %s", ctx));
            }
        }

        if(redirectURL != null) {
            httpResponse.sendRedirect(redirectURL);
        } else {
            if(userId != null && !skipMenuCheck) {
                final String menuId = httpRequest.getParameter("OPENIAM_MENU_ID");
                if(!authorizationManagerMenuServiceClient.isUserAuthenticatedToMenuWithURL(userId, requestURI, menuId, true)) {
                    log.debug(String.format("User with ID '%s' is not entitled to menu with URL '%s'", userId, requestURI));
                    httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
            }
            chain.doFilter(request, response);
        }
    }

}
