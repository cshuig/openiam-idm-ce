package org.openiam.ui.idp.web.mvc;

import org.apache.commons.lang.StringUtils;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.exception.ObjectNotFoundException;
import org.openiam.idm.srvc.auth.dto.Login;
import org.openiam.idm.srvc.policy.dto.Policy;
import org.openiam.idm.srvc.pswd.dto.ValidatePasswordResetTokenResponse;
import org.openiam.ui.exception.ErrorMessageException;
import org.openiam.ui.exception.ErrorTokenException;
import org.openiam.ui.idp.web.model.UnlockPasswordFormRequest;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.web.model.SetPasswordToken;
import org.openiam.ui.web.mvc.AbstractPasswordController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by alexander on 13.04.15.
 */
public abstract  class AbstractUserStatusController extends AbstractPasswordController {
    protected static final String passwordResetFormView="core/user/unlockUserResetPasswordForm";

    protected Login attemptToResetPassword(final HttpServletRequest request, final UnlockPasswordFormRequest formRequest, Policy policy) throws ErrorMessageException, ObjectNotFoundException, ErrorTokenException {
        final ValidatePasswordResetTokenResponse validateResponse = passwordService.validatePasswordResetToken(formRequest.getToken());
        if (validateResponse == null || validateResponse.getStatus() != ResponseStatus.SUCCESS || validateResponse.getPrincipal() == null) {
            throw new ErrorMessageException(Errors.UNAUTHORIZED);
        }
        final Login login = validateResponse.getPrincipal();

        if (formRequest.hasEmptyField()) {
            throw new ErrorMessageException(Errors.CHANGE_PASSWORD_FAILED);
        }

        if (!StringUtils.equals(formRequest.getNewPassword(), formRequest.getNewPasswordConfirm())) {
            throw new ErrorMessageException(Errors.PASSWORDS_NOT_EQUAL);
        }

        SetPasswordToken token = validatePassword(login.getLogin(), this.getAuthentificationManagedSystem(policy), formRequest.getNewPassword(),
                true);
        if (token.hasErrors()) {
            throw new ErrorTokenException(token.getErrorList());
        }

        token = attemptResetPassword(request, formRequest.getNewPassword(), login.getUserId(), null, false, false);
        if (token.hasErrors()) {
            throw new ErrorTokenException(token.getErrorList());
        }
        return login;
    }
}
