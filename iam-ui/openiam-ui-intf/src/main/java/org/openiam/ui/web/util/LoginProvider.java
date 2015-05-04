package org.openiam.ui.web.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.searchbeans.AuthStateSearchBean;
import org.openiam.idm.srvc.auth.domain.AuthStateEntity;
import org.openiam.idm.srvc.auth.domain.AuthStateId;
import org.openiam.idm.srvc.auth.dto.AuthenticationRequest;
import org.openiam.idm.srvc.auth.dto.Subject;
import org.openiam.idm.srvc.auth.service.AuthenticationConstants;
import org.openiam.idm.srvc.auth.service.AuthenticationService;
import org.openiam.idm.srvc.auth.ws.AuthenticationResponse;
import org.openiam.ui.login.LoginActionToken;
import org.openiam.ui.security.OpenIAMCookieProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LoginProvider {

	@Resource(name="authServiceClient")
	private AuthenticationService authServiceClient;
	
	@Value("${org.openiam.password.expiration.warnDays}")
	private int minNumOfDaysToWarnUserBeforePasswordExpiration;
	

	@Value("${org.openiam.ui.auth.logout.unsetCookieOnLogout}")
	private boolean unsetCookieOnLogout;
	

    @Autowired
    protected OpenIAMCookieProvider cookieProvider;

	public LoginActionToken getLoginActionToken(final HttpServletRequest request, final HttpServletResponse response, final String login, final String password) throws Exception {
		final LoginActionToken token = new LoginActionToken();
		final AuthenticationRequest authenticatedRequest = new AuthenticationRequest();
		authenticatedRequest.setClientIP(request.getRemoteAddr());
		authenticatedRequest.setPassword(password);
		authenticatedRequest.setPrincipal(login);
		try {
			authenticatedRequest.setNodeIP(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			
		}
		final AuthenticationResponse authenticationResponse = authServiceClient.login(authenticatedRequest);
		if(authenticationResponse == null || authenticationResponse.getStatus() == null) {
			token.setHttpErrorCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} else {
			int errCode = authenticationResponse.getAuthErrorCode();
			final Subject subject = authenticationResponse.getSubject();
			final boolean isPasswordExpirationWarning = (subject != null && subject.getResultCode() == AuthenticationConstants.RESULT_SUCCESS_PASSWORD_EXP);
			
			Integer numOfDaysUntilPasswordExpiration = null;
			boolean isSuccessfulLogin = (isPasswordExpirationWarning)|| (ResponseStatus.SUCCESS == authenticationResponse.getStatus());
			if (isSuccessfulLogin) {
				token.setUserId(subject.getUserId());
				
				/* redirect to change password screen in case it's time to reset password, but the login is still valid */
				numOfDaysUntilPasswordExpiration = authenticationResponse.getSubject().getDaysToPwdExp();
				if(numOfDaysUntilPasswordExpiration != null && numOfDaysUntilPasswordExpiration.intValue() <= minNumOfDaysToWarnUserBeforePasswordExpiration) {
					errCode = AuthenticationConstants.RESULT_SUCCESS_PASSWORD_EXP;
					token.setSuccessWithWarning(true);
				} else {
					token.setSuccess(true);
					token.setDoRedirect(true);
				}
				token.setNumOfDaysUntilPasswordExpiration(numOfDaysUntilPasswordExpiration);
				
				/* set login information and session */
				/*
				if(setCookieOnlyOnSuccess) {
					if(token.isSuccess()) {
						cookieProvider.setAuthInfo(request, response, login, authenticationResponse);
					}
				} else {
					cookieProvider.setAuthInfo(request, response, login, authenticationResponse);
				}
				*/
			}
			
			token.setAuthResponse(authenticationResponse);
			token.setErrCode(errCode);
		}
		return token;
	}

    public void doLogout(final HttpServletRequest request, final HttpServletResponse response, final boolean doSAMLLogout) throws IOException {
        final String userId = cookieProvider.getUserIdFromCookie(request);
        if (StringUtils.isNotEmpty(userId)) {
            final AuthStateSearchBean searchBean = new AuthStateSearchBean();
            final AuthStateId id = new AuthStateId();
            id.setUserId(userId);
            id.setTokenType("SAML_SP");
            searchBean.setKey(id);
            searchBean.setOnlyActive(true);
            final List<AuthStateEntity> authStateList = authServiceClient.findBeans(searchBean, 0, Integer.MAX_VALUE);
            if (doSAMLLogout && authStateList != null && CollectionUtils.size(authStateList) == 1) {
                final AuthStateEntity serviceProviderState = authStateList.get(0);
                response.sendRedirect(new StringBuilder("/idp/sp/logout/").append(serviceProviderState.getToken()).toString());
                return;
            } else {
                try {
                    authServiceClient.globalLogout(userId);
                } catch (Throwable e) {
                    //ignore for now
                }

            }
        }
        if (unsetCookieOnLogout) {
            cookieProvider.invalidate(request, response);
        }

        final HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
    }
}
