package org.openiam.ui.web.mvc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.srvc.auth.dto.AuthenticationRequest;
import org.openiam.idm.srvc.auth.dto.Subject;
import org.openiam.idm.srvc.auth.service.AuthenticationConstants;
import org.openiam.idm.srvc.auth.ws.AuthenticationResponse;
import org.openiam.idm.srvc.policy.dto.Policy;
import org.openiam.idm.srvc.policy.dto.PolicyAttribute;
import org.openiam.idm.srvc.policy.service.PolicyDataService;
import org.openiam.idm.srvc.pswd.ws.PasswordWebService;
import org.openiam.ui.login.DefaultLoginPageDisplayHandler;
import org.openiam.ui.login.LoginActionToken;
import org.openiam.ui.util.CookieUtils;
import org.openiam.ui.util.OpenIAMConstants;
import org.openiam.ui.util.URIUtils;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.web.model.ChangePasswordToken;
import org.openiam.ui.web.util.LoginProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class AbstractLoginController extends AbstractController {

    @Value("${org.openiam.ui.password.change.url}")
    protected String changePasswordURL;

    @Value("${org.openiam.self.register.enabled}")
    protected boolean isSelfRegistrationEnabled;

    @Value("${org.openiam.forgot.username.enabled}")
    protected boolean forgotUsernameEnabled;

    @Value("${org.openiam.ui.password.unlock.enabled}")
    protected boolean passwordUnlockEnabled;

    @Value("${org.openiam.self.register.url}")
    protected String selfRegistrationURL;

    @Value("${org.openiam.auth.cookie.domain}")
    private String cookieDomain;

    @Value("${org.openiam.auth.policy.id}")
    private String authentificationPolicyId;

    @Resource(name = "policyServiceClient")
    private PolicyDataService policyServiceClient;

    @Resource(name = "passwordServiceClient")
    protected PasswordWebService passwordService;

    @Autowired
    private LoginProvider loginProvider;

    @Value("${org.openiam.ui.login.page.additional.hyperlink.script}")
    private String hyperlinkScript;

    @Value("${org.openiam.ui.login.placeholder}")
    private String loginPlaceholder;
    @Value("${org.openiam.ui.password.placeholder}")
    private String passwordPlaceholder;
    @Value("${org.openiam.ui.forgot.password.label}")
    private String forgotPasswordLabel;
    @Value("${org.openiam.ui.forgot.username.label}")
    private String forgotUsernameLabel;

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


    private static final String TMP_LOGIN_COOKIE = "LOGIN_PLACEHOLDER";

    protected String getTemporaryLoginCookie(final HttpServletRequest request) {
        final Cookie cookie = CookieUtils.getCookie(request, TMP_LOGIN_COOKIE);
        return (cookie != null) ? cookie.getValue() : null;
    }

    /**
     * This sets a 'very' temporary cookie that is used <b>only</b> for cases when the user has logged in, but his password needs to be changed.
     * We need this to prefill his login in the change password form
     *
     * @param request
     * @param response
     * @param login
     */
    protected void setTemporaryLoginCookie(final HttpServletRequest request, final HttpServletResponse response, final String login) {
        if (login != null) {
            final Cookie cookie = new Cookie(TMP_LOGIN_COOKIE, login);
            cookie.setMaxAge(60);
            cookie.setDomain(getCookieDomain(request));
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }

    protected LoginActionToken getLoginActionToken(final HttpServletRequest request, final HttpServletResponse response, final String login, final String password) throws Exception {
        return loginProvider.getLoginActionToken(request, response, login, password);
    }

    /* generates the URL required by the 'changePassword' screen */
    protected String getChangePasswordURL(final ChangePasswordToken token) {
        String encodedPostabckURL = "";
        try {
            encodedPostabckURL = URLEncoder.encode(token.getPostbackURL(), OpenIAMConstants.UTF_8);
        } catch (Throwable e) {

        }

        final int changeReason = token.getChangeReason();
        final String managedSysId = token.getManagedSysId();
        final StringBuilder url = new StringBuilder(
                String.format("%s?postbackURL=%s&changeReason=%s&managedSysId=%s&",
                        changePasswordURL,
                        encodedPostabckURL,
                        changeReason,
                        managedSysId));
        if (token.getNumOfDaysUntilPasswordExpiration() != null) {
            url.append("&numOfDaysUntilPasswordExpiration=").append(token.getNumOfDaysUntilPasswordExpiration().intValue());
        }
        return url.toString();
    }

    /* sets all request attributes as 'hidden', with the exception of login and password */
    protected void setCommonRequestAttributes(final HttpServletRequest request, final String postbackURL) {
        final Map<String, String> hiddenAttributes = new HashMap<String, String>();
        if (request.getParameterMap() != null) {
            for (final Object key : request.getParameterMap().keySet()) {
                final String stringKey = (String) key;
                if (!StringUtils.equals("login", stringKey) &&
                        !StringUtils.equals("password", stringKey) &&
                        !StringUtils.equals("openiamPrincipal", stringKey) &&
                        !StringUtils.equals("loginErrorFromProxy", stringKey) &&
                        !StringUtils.equals("postbackURL", stringKey)) {
                    hiddenAttributes.put(stringKey, request.getParameter(stringKey));
                }
            }
        }

        hiddenAttributes.put("postbackURL", postbackURL);

        DefaultLoginPageDisplayHandler displayHandler = null;
        try {
            if (hyperlinkScript != null) {
                displayHandler = (DefaultLoginPageDisplayHandler) scriptRunner.instantiateClass(null, hyperlinkScript);
            }
        } catch (Throwable e) {
            displayHandler = new DefaultLoginPageDisplayHandler();
        }

        final Map<String, Object> bindingMap = new HashMap<>();
        bindingMap.put("REQUEST", request);
        bindingMap.put("LANGUAGE", getCurrentLanguage());
        displayHandler.init(bindingMap);
        request.setAttribute("displayHandler", displayHandler);
        request.setAttribute("frameBustPostbackURL", postbackURL);
        request.setAttribute("hiddenAttributes", hiddenAttributes);
        request.setAttribute("isSelfRegistrationEnabled", isSelfRegistrationEnabled);
        request.setAttribute("forgotUsernameEnabled", forgotUsernameEnabled);
        request.setAttribute("passwordUnlockEnabled", passwordUnlockEnabled);
        request.setAttribute("selfRegistrationURL", selfRegistrationURL);
        request.setAttribute("loginPlaceholder", loginPlaceholder);
        request.setAttribute("passwordPlaceholder", passwordPlaceholder);
        request.setAttribute("forgotPasswordLabel", forgotPasswordLabel);
        request.setAttribute("forgotUsernameLabel", forgotUsernameLabel);
    }

    protected Errors getErrorFromCode(final int errCode) {
        Errors error = null;
        switch (errCode) {
            case AuthenticationConstants.RESULT_INVALID_DOMAIN:
                error = Errors.INVALID_DOMAIN;
                break;
            case AuthenticationConstants.RESULT_INVALID_USER_STATUS:
                error = Errors.INVALID_USER_STATUS;
                break;
            case AuthenticationConstants.RESULT_LOGIN_DISABLED:
                error = Errors.ACCOUNT_DISABLED;
                break;
            case AuthenticationConstants.RESULT_LOGIN_LOCKED:
                error = Errors.ACCOUNT_LOCKED;
                break;
            case AuthenticationConstants.RESULT_SERVICE_NOT_FOUND:
                error = Errors.INVALID_DOMAIN;
                break;
            case AuthenticationConstants.RESULT_INVALID_CONFIGURATION:
                error = Errors.INVALID_CONFIGURATION;
                break;
            case AuthenticationConstants.RESULT_PASSWORD_EXPIRED:
                error = Errors.PASSWORD_EXPIRED;
                break;
            case AuthenticationConstants.RESULT_SUCCESS_PASSWORD_EXP:
                error = Errors.PASSWORD_WILL_EXPIRE;
                break;
            case AuthenticationConstants.RESULT_PASSWORD_CHANGE_AFTER_RESET:
                error = Errors.PASSWORD_RESETED;
                break;
            default:
                error = Errors.INVALID_LOGIN;
                break;
        }
        return error;
    }

    protected void doLogout(final HttpServletRequest request, final HttpServletResponse response, final boolean doSAMLLogout) throws IOException {
        loginProvider.doLogout(request, response, doSAMLLogout);

    }


    protected Policy getAuthentificationPolicy() {
        return policyServiceClient.getPolicy(authentificationPolicyId);
    }

    protected String getAuthParamValue(Policy policy, String name) {
        if (policy == null || StringUtils.isBlank(name)) {
            return null;
        }
        if (policy.getAttribute(name) == null) {
            return null;
        }
        return policy.getAttribute(name).getValue1();
    }

    protected String getAuthentificationManagedSystem(Policy policy) {
        if (policy == null || StringUtils.isBlank(this.getAuthParamValue(policy, "MANAGED_SYS_ID"))) {
            return defaultManagedSysId;
        } else {
            return this.getAuthParamValue(policy, "MANAGED_SYS_ID");
        }
    }


    protected String buildLoginAccordingAuthPolicy(Policy policy, String login) {
        String freshLogin = login;
        if (policy == null || StringUtils.isBlank(this.getAuthParamValue(policy, "KEY_ATTRIBUTE"))
                || defaultManagedSysId.equals(this.getAuthentificationManagedSystem(policy))) {
            return freshLogin;
        } else {
            String format = this.getAuthParamValue(policy, "KEY_ATTRIBUTE");
            String[] splitFormat = format.split("\\?");
            if (splitFormat != null) {
                for (String sp : splitFormat) {
                    if (!login.contains(sp)) {
                        freshLogin = format.replace("?", login);
                        break;
                    }
                }
            }
        }
        return freshLogin;
    }

    protected String buildExternalPassword(Policy policy, String login) {
        String freshLogin = login;
        if (policy == null || StringUtils.isBlank(this.getAuthParamValue(policy, "KEY_ATTRIBUTE"))
                || defaultManagedSysId.equals(this.getAuthentificationManagedSystem(policy))) {
            return freshLogin;
        } else {
            String format = this.getAuthParamValue(policy, "KEY_ATTRIBUTE");
            String[] splitFormat = format.split("\\?");
            if (splitFormat != null) {
                boolean isToTrim = true;
                for (String sp : splitFormat) {
                    freshLogin = freshLogin.replace(sp, "");
                }
            }
        }
        return freshLogin;
    }
}
