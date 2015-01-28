package org.openiam.ui.rest.api.mvc;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.exception.ObjectNotFoundException;
import org.openiam.idm.srvc.auth.dto.Login;
import org.openiam.idm.srvc.policy.dto.Policy;
import org.openiam.idm.srvc.pswd.dto.Password;
import org.openiam.idm.srvc.pswd.dto.PasswordValidationResponse;
import org.openiam.idm.srvc.pswd.dto.ValidatePasswordResetTokenResponse;
import org.openiam.ui.exception.ErrorMessageException;
import org.openiam.ui.rest.api.model.PasswordRulesBean;
import org.openiam.ui.rest.api.model.ValidatePasswordRequestBean;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.web.model.SetPasswordToken;
import org.openiam.ui.web.mvc.AbstractController;
import org.openiam.ui.web.mvc.AbstractLoginController;
import org.openiam.ui.web.mvc.AbstractPasswordController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/password")
public class PasswordRestController extends AbstractPasswordController {

    @RequestMapping(value = "/validation/rules", method = RequestMethod.POST)
    public
    @ResponseBody
    SetPasswordToken getValidationRules(final HttpServletRequest request, final @RequestBody ValidatePasswordRequestBean bean) throws ObjectNotFoundException, ErrorMessageException {
        String principal = getPrincipal(bean);
        if (principal == null) {
            return null;
        }
        Policy policy = this.getAuthentificationPolicy();
        String internalLogin = this.buildLoginAccordingAuthPolicy(policy, principal);
        String managedSystemId = this.getAuthentificationManagedSystem(policy);
        final SetPasswordToken token = validatePassword(internalLogin, managedSystemId, null,  bean.isForgotPassword());
        token.addRule(new ErrorToken(Errors.PASSWORDS_NOT_EQUAL));
        token.process(localeResolver, messageSource, request);
        return token;
    }

    @RequestMapping(value = "/validation/validate", method = RequestMethod.POST)
    public
    @ResponseBody
    SetPasswordToken validate(final HttpServletRequest request,
                              final @RequestBody ValidatePasswordRequestBean bean) throws ErrorMessageException, ObjectNotFoundException {

        String principal = getPrincipal(bean);
        if (principal == null) {
            return null;
        }
        Policy policy = this.getAuthentificationPolicy();
        String internalLogin = this.buildLoginAccordingAuthPolicy(policy, principal);
        String managedSystemId = this.getAuthentificationManagedSystem(policy);
        final SetPasswordToken token = validatePassword(internalLogin, managedSystemId,  bean.getPassword(),bean.isForgotPassword());
        token.process(localeResolver, messageSource, request);

        if (StringUtils.isBlank(bean.getConfirmPassword()) || !StringUtils.equals(bean.getPassword(), bean.getConfirmPassword())) {
            token.addError(new ErrorToken(Errors.PASSWORDS_NOT_EQUAL));
        }

        return token;
    }

    private String getPrincipal(final ValidatePasswordRequestBean bean) throws ErrorMessageException {
        String principal = null;
        if (StringUtils.isNotBlank(bean.getLogin())) {
            principal = bean.getLogin();
        } else if (StringUtils.isNotBlank(bean.getToken())) {
            final ValidatePasswordResetTokenResponse validateResponse = passwordService.validatePasswordResetToken(bean.getToken());
            if (validateResponse == null || validateResponse.getStatus() != ResponseStatus.SUCCESS || validateResponse.getPrincipal() == null) {
                throw new ErrorMessageException(Errors.UNAUTHORIZED);
            }

            final Login login = validateResponse.getPrincipal();
            principal = login.getLogin();
        }
        return principal;
    }
}
