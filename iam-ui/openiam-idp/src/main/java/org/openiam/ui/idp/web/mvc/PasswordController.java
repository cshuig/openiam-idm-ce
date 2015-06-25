package org.openiam.ui.idp.web.mvc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.srvc.auth.dto.AuthenticationRequest;
import org.openiam.idm.srvc.auth.dto.Login;
import org.openiam.idm.srvc.auth.service.AuthenticationConstants;
import org.openiam.idm.srvc.auth.service.AuthenticationService;
import org.openiam.idm.srvc.auth.ws.AuthenticationResponse;
import org.openiam.idm.srvc.auth.ws.LoginResponse;
import org.openiam.idm.srvc.mngsys.dto.ManagedSysDto;
import org.openiam.idm.srvc.mngsys.ws.ManagedSystemWebService;
import org.openiam.idm.srvc.policy.dto.Policy;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.ui.exception.ErrorTokenException;
import org.openiam.ui.idp.web.model.ChangePasswordFormRequest;
import org.openiam.ui.security.OpenIAMCookieProvider;
import org.openiam.ui.util.URIUtils;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.model.SetPasswordToken;
import org.openiam.ui.web.mvc.AbstractPasswordController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Controller
public class PasswordController extends AbstractPasswordController {

    private static Logger log = Logger.getLogger(PasswordController.class);

    @Resource(name = "authServiceClient")
    private AuthenticationService authenticate;

    @Resource(name = "managedSysServiceClient")
    private ManagedSystemWebService managedSystemService;

    @Autowired
    private OpenIAMCookieProvider cookieProvider;

    @Value("${org.openiam.change.password.all}")
    private Boolean showAllManagedSystems;

    @Value("${org.openiam.change.password.success.url}")
    private String changedPasswordSuccess;
    
    @Value("${org.openiam.ui.change.password.uri.oncancel}")
    private String onCancelChangePasswordURL;

    @RequestMapping(value = "/changedPasswordSuccess", method = RequestMethod.GET)
    public String changedPasswordSuccess(final HttpServletRequest request) {
        return "core/changedPasswordSuccess";
    }

    @RequestMapping(value = "/changePasswordManagedSystem", method = RequestMethod.GET)
    public String getChangePasswordManagedSysScreen(final HttpServletRequest request,
                                                    final HttpServletResponse response
    ) throws Exception {
        Policy policy = this.getAuthentificationPolicy();
        String currentLogin = getRequesterPrincipal(request);
        if (currentLogin == null) {
            currentLogin = getTemporaryLoginCookie(request);
        }
//Actualize to internal login
        String internalLogin = this.buildLoginAccordingAuthPolicy(policy, currentLogin);
        String userId = null;
        if (StringUtils.isNotBlank(currentLogin)) {
            User u = userDataWebService.getUserByPrincipal(internalLogin, this.getAuthentificationManagedSystem(policy), true);
            List<Login> logins = u.getPrincipalList();
            List<ManagedSysDto> managedSysDtoList = new ArrayList<ManagedSysDto>();
            if (CollectionUtils.isNotEmpty(logins)) {
                for (Login l : logins) {
                    if (l.getManagedSysId() != null) {
                        ManagedSysDto ms = managedSysServiceClient.getManagedSys(l.getManagedSysId());
                        if (ms.getChangedByEndUser() || showAllManagedSystems) {
                            ms.setUserId(l.getLogin());
                            managedSysDtoList.add(ms);
                        }
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(managedSysDtoList)) {
                request.setAttribute("principalList", managedSysDtoList);
                request.setAttribute("userId", u.getId());
            }
        }


        return "core/changePasswordManagedSys";
    }


    @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
    public String getChangePasswordScreen(final HttpServletRequest request,
                                          final HttpServletResponse response,
                                          final @RequestParam(value = "changeReason", required = false) Integer changeReason,
                                          final @RequestParam(value = "postbackURL", required = false) String postbackURL,
                                          final @RequestParam(value = "numOfDaysUntilPasswordExpiration", required = false) String numOfDaysUntilPasswordExpiration) throws
            Exception {
        if (!URIUtils.isValidPostbackURL(postbackURL)) {
            log.warn(String.format("Postback URL '%s' not valid - doesn't start with a '/' - XSS detected.  Returning 401", postbackURL));
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }

        Errors error = null;
        Object[] errorParams = null;
        if (changeReason != null) {
            switch (changeReason.intValue()) {
                case AuthenticationConstants.RESULT_PASSWORD_EXPIRED:
                    error = Errors.PASSWORD_EXPIRED;
                    break;
                case AuthenticationConstants.RESULT_SUCCESS_PASSWORD_EXP:
                    error = Errors.PASSWORD_WILL_EXPIRE;

					/* if the password will expire today, set a friendlier message than 'Your password will expire in 0 days' */
                    if (numOfDaysUntilPasswordExpiration == null || StringUtils.equalsIgnoreCase(numOfDaysUntilPasswordExpiration, "null")) {
                        error = Errors.PASSWORD_WILL_EXPIRE_SOON;
                    } else if (StringUtils.equalsIgnoreCase(numOfDaysUntilPasswordExpiration, "0")) {
                        error = Errors.PASSWORD_WILL_EXPIRE_TODAY;
                    } else {
                        errorParams = new Object[]{numOfDaysUntilPasswordExpiration};
                    }
                    break;
                case AuthenticationConstants.RESULT_PASSWORD_CHANGE_AFTER_RESET:
                    error = Errors.PASSWORD_RESETED;
                    break;
                default:
                    error = Errors.CHANGE_PASSWORD;
                    break;
            }
        } else {
            error = Errors.CHANGE_PASSWORD;
        }
        Policy policy = this.getAuthentificationPolicy();
        String currentLogin = getRequesterPrincipal(request);
        if (currentLogin == null) {
            currentLogin = getTemporaryLoginCookie(request);
        }
//Actualize to internal login
        String internalLogin = this.buildLoginAccordingAuthPolicy(policy, currentLogin);

        String userId = null;
        if (StringUtils.isNotBlank(currentLogin)) {
            LoginResponse res = loginServiceClient.getLoginByManagedSys(internalLogin, this.getAuthentificationManagedSystem(policy));
            if (res.isSuccess()) {
                userId = res.getPrincipal().getUserId();
            }
        }

        request.setAttribute("currentLogin", currentLogin);
        request.setAttribute("currentUserId", userId);
        request.setAttribute("changeMessage", new ErrorToken(error, errorParams));


        final Map<String, Object> hiddenAttributes = new HashMap<String, Object>();
        hiddenAttributes.put("changeReason", changeReason);
        hiddenAttributes.put("postbackURL", postbackURL);
        hiddenAttributes.put("managedSysId", this.getAuthentificationManagedSystem(policy));
        if (numOfDaysUntilPasswordExpiration != null) {
            hiddenAttributes.put("numOfDaysUntilPasswordExpiration", numOfDaysUntilPasswordExpiration);
        }
        request.setAttribute("hiddenAttributes", hiddenAttributes);
        request.setAttribute("onCancelChangePasswordURL", onCancelChangePasswordURL);
        return "core/changePassword";
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public String changePassword(final HttpServletRequest request,
                                 final HttpServletResponse response,
                                 @RequestParam(value = "postbackURL", required = false) String postbackURL,
                                 final ChangePasswordFormRequest formRequest) throws Exception {
        if (!URIUtils.isValidPostbackURL(postbackURL)) {
            log.warn(String.format("Postback URL '%s' not valid - doesn't start with a '/' - XSS detected.  Returning 401", postbackURL));
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }

		/*
        postbackURL = new StringBuilder(URIUtils.getBaseURI(request)).append(postbackURL).toString();
		if(log.isDebugEnabled()) {
			log.debug(String.format("Changed postback URL to '%s'", postbackURL));
		}
		*/
        Policy policy = this.getAuthentificationPolicy();

        SetPasswordToken token = null;
        try {
            if (formRequest.hasEmptyField()) {
                throw new ErrorTokenException(new ErrorToken(Errors.CHANGE_PASSWORD_FAILED));
            }

            if (!StringUtils.equals(formRequest.getNewPassword(), formRequest.getNewPasswordConfirm())) {
                throw new ErrorTokenException(new ErrorToken(Errors.PASSWORDS_NOT_EQUAL));
            }
            token = validatePassword(formRequest.getLogin(), this.getAuthentificationManagedSystem(policy), formRequest.getNewPassword(), false);
            if (token.hasErrors()) {
                throw new ErrorTokenException(token.getErrorList());
            }
            String externalPassword = buildExternalPassword(policy, formRequest.getLogin());
            /* confirm that the old password is correct */
            final AuthenticationRequest loginRequest = new AuthenticationRequest();
            loginRequest.setClientIP(request.getRemoteAddr());
            loginRequest.setPassword(formRequest.getCurrentPassword());
            loginRequest.setPrincipal(externalPassword);
            final AuthenticationResponse loginResponse = authenticate.login(loginRequest);
            if (ResponseStatus.SUCCESS != loginResponse.getStatus()) {
                if (!isValidErrorCodeForPasswordChange(loginResponse.getAuthErrorCode())) {
                    throw new ErrorTokenException(new ErrorToken(Errors.INVALID_LOGIN));
                }
            }


            token = attemptResetPassword(request, formRequest.getNewPassword(), formRequest.getUserId(),null, true, false);
            if (token.hasErrors()) {
                throw new ErrorTokenException(token.getErrorList());
            }

			/* at this point, no error occurred - login using new credentials */
            final AuthenticationRequest authenticatedRequest = new AuthenticationRequest();
            authenticatedRequest.setClientIP(request.getRemoteAddr());
            authenticatedRequest.setPassword(formRequest.getNewPassword());
            authenticatedRequest.setPrincipal(externalPassword);
            try {
                authenticatedRequest.setNodeIP(InetAddress.getLocalHost().getHostAddress());
            } catch (UnknownHostException e) {

            }
            final AuthenticationResponse authenticationResponse = authenticate.login(authenticatedRequest);
            if (authenticationResponse == null || authenticationResponse.getStatus() == null) {
                throw new Exception("Can't get authentication result");
            }

			/* if login was successful, redriect */
            if (ResponseStatus.SUCCESS == authenticationResponse.getStatus()) {
                cookieProvider.setAuthInfo(request, response, formRequest.getLogin(), authenticationResponse);
                postbackURL = (StringUtils.isNotBlank(postbackURL)) ? postbackURL : changedPasswordSuccess;
                response.sendRedirect(postbackURL);
                return null;
            } else { /* no reason for the auth request to fail, except for an internal error */
                throw new Exception(String.format("Can't login using new credentials.  Authentication Response: %s", authenticationResponse));
            }
        } catch (ErrorTokenException e) {
            final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
            ajaxResponse.setStatus(500);
            ajaxResponse.addErrors(e.getTokenList());
            if (token != null) {
                ajaxResponse.setPossibleErrors(token.getRules());
            }
            ajaxResponse.process(localeResolver, messageSource, request);
            request.setAttribute("ajaxResponse", jacksonMapper.writeValueAsString(ajaxResponse));

            Integer changeReason = null;
            try {
                changeReason = Integer.valueOf(request.getParameter("changeReason"));
            } catch (Throwable ie) {

            }

            final String numOfDaysUntilPasswordExpiration = (String) request.getParameter("numOfDaysUntilPasswordExpiration");
            return getChangePasswordScreen(request,
                    response,
                    changeReason,
                    postbackURL,
                    numOfDaysUntilPasswordExpiration);
        } catch (Throwable e) {
            log.error("Can't save new password", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return null;
        }
    }



    private boolean isValidErrorCodeForPasswordChange(final int code) {
        return (code == AuthenticationConstants.RESULT_SUCCESS) ||
                (code == AuthenticationConstants.RESULT_SUCCESS_FIRST_TIME) ||
                (code == AuthenticationConstants.RESULT_SUCCESS_PASSWORD_EXP) ||
                (code == AuthenticationConstants.RESULT_PASSWORD_EXPIRED) ||
                (code == AuthenticationConstants.RESULT_PASSWORD_CHANGE_AFTER_RESET);
    }
}
