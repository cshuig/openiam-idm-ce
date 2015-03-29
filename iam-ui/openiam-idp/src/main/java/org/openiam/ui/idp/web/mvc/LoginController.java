package org.openiam.ui.idp.web.mvc;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.srvc.auth.dto.AuthenticationRequest;
import org.openiam.idm.srvc.auth.dto.Subject;
import org.openiam.idm.srvc.auth.service.AuthenticationConstants;
import org.openiam.idm.srvc.auth.service.AuthenticationService;
import org.openiam.idm.srvc.auth.ws.AuthenticationResponse;
import org.openiam.idm.srvc.policy.dto.Policy;
import org.openiam.ui.login.LoginActionToken;
import org.openiam.ui.security.OpenIAMCookieProvider;
import org.openiam.ui.util.URIUtils;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.web.model.ChangePasswordToken;
import org.openiam.ui.web.mvc.AbstractLoginController;
import org.springframework.aop.framework.ProxyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

@Controller
public class LoginController extends AbstractLoginController {

    private static final Log log = LogFactory.getLog(LoginController.class);

    @Autowired
    private OpenIAMCookieProvider cookieProvider;

    @Value("${org.openiam.ui.password.unlock.url}")
    private String unlockUserURL;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGet(final HttpServletRequest request,
                           final HttpServletResponse response,
                           final @RequestParam(value = "loginErrorFromProxy", required = false) Integer loginErrorFromProxy,
                           final @RequestParam(value = "openiamPrincipal", required = false) String openiamPrincipal,
                           @RequestParam(value = "postbackURL", required = false) String postbackURL,
                           final @RequestHeader(value = "x-requested-with", required = false) String requestedWith) throws Exception {
        postbackURL = (StringUtils.isNotBlank(postbackURL)) ? postbackURL : "/selfservice";
        if (!URIUtils.isValidPostbackURL(postbackURL)) {
            log.warn(String.format("Postback URL '%s' not valid - doesn't start with a '/' - XSS detected.  Returning 401", postbackURL));
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
        Policy policy = this.getAuthentificationPolicy();
        final boolean isAjax = StringUtils.equalsIgnoreCase(requestedWith, "XMLHttpRequest");
        response.setHeader("x-openiam-force-auth", Boolean.valueOf(isAjax).toString());
        response.setHeader("x-openiam-login-uri", request.getRequestURI());

        if (loginErrorFromProxy != null && openiamPrincipal != null) {
            return handlePotentialLoginError(request, response, openiamPrincipal, policy, loginErrorFromProxy.intValue(), postbackURL, null);
        } else {
            setCommonRequestAttributes(request, postbackURL);
        }

        return "core/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost(@RequestParam(value = "login", required = false) String login,
                            @RequestParam(value = "password", required = false) String password,
                            @RequestParam(value = "postbackURL", required = false) String postbackURL,
                            final HttpServletRequest request,
                            final HttpServletResponse response) throws Exception {
    	login = (StringUtils.isNotBlank(login)) ? login : "";
    	password = (StringUtils.isNotBlank(password)) ? password : "";
        postbackURL = (StringUtils.isNotBlank(postbackURL)) ? postbackURL : "/selfservice";
        if (!URIUtils.isValidPostbackURL(postbackURL)) {
            log.warn(String.format("Postback URL '%s' not valid - doesn't start with a '/' - XSS detected.  Returning 401", postbackURL));
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }

        Policy policy = this.getAuthentificationPolicy();
        try {
            //Here should be external login
            String extenalLogin = this.buildExternalPassword(policy, login);
            final LoginActionToken loginActionToken = getLoginActionToken(request, response, extenalLogin, password);
            if (loginActionToken.isSuccess() || loginActionToken.isSuccessWithWarning()) {
                cookieProvider.setAuthInfo(request, response, this.buildLoginAccordingAuthPolicy(policy, login), loginActionToken.getAuthResponse());
            }

            if (loginActionToken.isSuccess()) {
                response.sendRedirect(postbackURL);
                return null;
            } else { /* failure  or expired password or warning */
                if (loginActionToken.getHttpErrorCode() != null) {
                    response.sendError(loginActionToken.getHttpErrorCode().intValue());
                    return null;
                } else {
                    /* at this point, the login was either valid, or it's time to force/warn of a password change */
                    return handlePotentialLoginError(request, response, login, policy, loginActionToken.getErrCode(), postbackURL, loginActionToken.getNumOfDaysUntilPasswordExpiration());
                }
            }

        } catch (Throwable e) {
            log.error("Login failed", e);
            request.setAttribute("error", new ErrorToken(Errors.INTERNAL_ERROR));
            request.setAttribute("login", login);
            setCommonRequestAttributes(request, postbackURL);
            return "core/login";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        final String referer = request.getHeader("referer");
        final String redirectTo = (StringUtils.containsIgnoreCase(referer, "webconsole")) ? "/webconsole" : "/selfservice";
        doLogout(request, response, true);
        request.setAttribute("loginTo", redirectTo);
        return "core/logout";
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public void handleAjaxValidationError(
            MethodArgumentNotValidException e, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private String handlePotentialLoginError(final HttpServletRequest request,
                                             final HttpServletResponse response,
                                             final String login,
                                             final Policy policy,
                                             final int errCode,
                                             final String postbackURL,
                                             final Integer numOfDaysUntilPasswordExpiration) throws IOException, URISyntaxException {

        String internalLogin = this.buildLoginAccordingAuthPolicy(policy, login);
        String managedSysId = this.getAuthentificationManagedSystem(policy);
        String view = null;
        ChangePasswordToken changePasswordToken = null;
        final Errors error = getErrorFromCode(errCode);
        switch (error) {
            case PASSWORD_EXPIRED:
                changePasswordToken = new ChangePasswordToken(postbackURL, AuthenticationConstants.RESULT_PASSWORD_EXPIRED, managedSysId);
                setTemporaryLoginCookie(request, response, internalLogin);
                break;
            case PASSWORD_WILL_EXPIRE:
                changePasswordToken = new ChangePasswordToken(postbackURL,
                        AuthenticationConstants.RESULT_SUCCESS_PASSWORD_EXP,
                        managedSysId,
                        numOfDaysUntilPasswordExpiration);
                setTemporaryLoginCookie(request, response, internalLogin);
                break;
            case PASSWORD_RESETED:
                changePasswordToken = new ChangePasswordToken(postbackURL,
                        AuthenticationConstants.RESULT_PASSWORD_CHANGE_AFTER_RESET,
                        managedSysId,
                        numOfDaysUntilPasswordExpiration);
                setTemporaryLoginCookie(request, response, internalLogin);
                break;
            case ACCOUNT_LOCKED:
                request.setAttribute("unlockURL", new StringBuilder(URIUtils.getBaseURI(request)).append(unlockUserURL).toString());
                break;
            default:
                break;
        }
        if (changePasswordToken != null) {
            response.sendRedirect(getChangePasswordURL(changePasswordToken));
            view = null;
        } else {
            request.setAttribute("error", new ErrorToken(error));
            request.setAttribute("login", login);
            setCommonRequestAttributes(request, postbackURL);
            view = "core/login";
        }
        return view;
    }
}
