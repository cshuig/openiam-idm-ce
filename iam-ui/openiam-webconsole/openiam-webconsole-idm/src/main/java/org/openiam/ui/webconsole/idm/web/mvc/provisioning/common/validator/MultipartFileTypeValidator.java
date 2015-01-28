package org.openiam.ui.webconsole.idm.web.mvc.provisioning.common.validator;

import org.apache.commons.lang.ArrayUtils;
import org.openiam.ui.util.messages.Errors;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MultipartFileTypeValidator implements ConstraintValidator<MultipartFileType, MultipartFile> {

    private String[] types;

    @Override
    public void initialize(MultipartFileType fileType) {
        types = (String[])ArrayUtils.addAll(types, fileType.types());
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        boolean isValid = true;
        if (value != null && !value.isEmpty()) {
            if (!ArrayUtils.isEmpty(types)) {
                isValid = ArrayUtils.contains(types, value.getContentType());
            }
            if (!isValid) {
                context.buildConstraintViolationWithTemplate( "{" + Errors.INVALID_MULTIPART_FILE_TYPE.getMessageName() + "}" )
                        .addConstraintViolation();
            }
        }
        return isValid;
    }
}
