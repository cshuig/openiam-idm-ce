package org.openiam.ui.webconsole.validator;

import org.openiam.ui.rest.api.model.ResetPasswordBean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ResetPasswordValidator  implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ResetPasswordBean.class.equals(clazz);
    }

    @Override
    public void validate(Object model, Errors errors) {
        ResetPasswordBean pwd = (ResetPasswordBean) model;

        if(!pwd.getAutoGeneratePassword()){
            if (!StringUtils.hasText(pwd.getPassword())) {
                errors.rejectValue("password", "required", org.openiam.ui.util.messages.Errors.RESET_PASSWORD_PASSWORD_NOT_SET.getMessageName());
            }else{
                if(!pwd.getPassword().equals(pwd.getConfPassword())){
                    errors.rejectValue("password", "required", org.openiam.ui.util.messages.Errors.RESET_PASSWORD_PASSWORD_NOT_MATCH.getMessageName());
                }
            }
        }
    }
}
