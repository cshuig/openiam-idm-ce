package org.openiam.ui.webconsole.validator;

import org.openiam.base.AttributeOperationEnum;
import org.openiam.ui.rest.api.model.AddressBean;
import org.openiam.ui.rest.api.model.EmailBean;
import org.openiam.ui.rest.api.model.LocationBean;
import org.openiam.ui.rest.api.model.PhoneBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class LocationValidator implements Validator {

    public boolean supports(Class cls) {
        return LocationBean.class.equals(cls) || PhoneBean.class.equals(cls) || AddressBean.class.equals(cls);
    }

    @Override
    public void validate(Object target, Errors errors) {

    if (target instanceof LocationBean) {
        LocationBean location = (LocationBean) target;
            if (location.getOperation() == null || location.getOperation() != AttributeOperationEnum.DELETE) {
                if (!StringUtils.hasText(location.getName())) {
                    errors.rejectValue("name", "required", org.openiam.ui.util.messages.Errors.LOCATION_NAME_REQUIRED.getMessageName());
                }
                if (!StringUtils.hasText(location.getAddress1())) {
                    errors.rejectValue("address1", "required", org.openiam.ui.util.messages.Errors.ADDRESS1_REQUIRED.getMessageName());
                }
            }
        }
    }
}
