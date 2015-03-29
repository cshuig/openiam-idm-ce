package org.openiam.ui.webconsole.validator;

import org.openiam.base.AttributeOperationEnum;
import org.openiam.ui.rest.api.model.AddressBean;
import org.openiam.ui.rest.api.model.EmailBean;
import org.openiam.ui.rest.api.model.PhoneBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserContactInfoValidator implements Validator {
    @Value("${org.openiam.email.validation.regexp}")
    private String emailPattern;

//    private static final String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public boolean supports(Class cls) {
        return EmailBean.class.equals(cls) || PhoneBean.class.equals(cls) || AddressBean.class.equals(cls);
    }

    @Override
    public void validate(Object target, Errors errors) {

        if (target instanceof EmailBean) {
            EmailBean emailBean = (EmailBean) target;
            if (emailBean.getOperation() == null || emailBean.getOperation() != AttributeOperationEnum.DELETE) {
                if (!StringUtils.hasText(emailBean.getEmail())) {
                    errors.rejectValue("email", "required", org.openiam.ui.util.messages.Errors.REQUIRED_EMAIL_ADDRESS.getMessageName());
                } else {
                    Pattern pattern = Pattern.compile(emailPattern);
                    Matcher matcher = pattern.matcher(emailBean.getEmail());
                    if (!matcher.matches()) {
                        errors.rejectValue("email", "invalid", org.openiam.ui.util.messages.Errors.INVALID_EMAIL_ADDRESS.getMessageName());
                    }

                    if (!StringUtils.hasText(emailBean.getTypeId())) {
                        errors.rejectValue("type", "required", org.openiam.ui.util.messages.Errors.EMAIL_TYPE_REQUIRED.getMessageName());
                    }
                }
            }
        } else if (target instanceof PhoneBean) {
            PhoneBean phone = (PhoneBean) target;
            if (phone.getOperation() == null || phone.getOperation() != AttributeOperationEnum.DELETE) {
                if (!StringUtils.hasText(phone.getTypeId())) {
                    errors.rejectValue("type", "required", org.openiam.ui.util.messages.Errors.PHONE_TYPE_REQUIRED.getMessageName());
                }
                if (!StringUtils.hasText(phone.getAreaCd())) {
                    errors.rejectValue("areaCd", "required", org.openiam.ui.util.messages.Errors.REQUIRED_PHONE_AREA_CD.getMessageName());
                }
                if (!StringUtils.hasText(phone.getPhoneNbr())) {
                    errors.rejectValue("phoneNbr", "required", org.openiam.ui.util.messages.Errors.REQUIRED_PHONE_NUMBER.getMessageName());
                }
            }
        } else if (target instanceof AddressBean) {
            AddressBean address = (AddressBean) target;
            if (address.getOperation() == null || address.getOperation() != AttributeOperationEnum.DELETE) {
                if (!StringUtils.hasText(address.getTypeId())) {
                    errors.rejectValue("type", "required", org.openiam.ui.util.messages.Errors.ADDRESS_TYPE_REQUIRED.getMessageName());
                }
                if (!StringUtils.hasText(address.getAddress1())) {
                    errors.rejectValue("address1", "required", org.openiam.ui.util.messages.Errors.ADDRESS1_REQUIRED.getMessageName());
                }
            }
        }
    }
}
