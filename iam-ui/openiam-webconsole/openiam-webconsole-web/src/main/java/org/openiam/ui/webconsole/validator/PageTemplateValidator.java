package org.openiam.ui.webconsole.validator;

import org.openiam.idm.srvc.meta.dto.MetadataElementPageTemplateXref;
import org.openiam.idm.srvc.meta.dto.MetadataFieldTemplateXref;
import org.openiam.ui.webconsole.web.model.PageTemplateBean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.HashSet;

@Component
public class PageTemplateValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return PageTemplateBean.class.equals(clazz);
    }

    @Override
    public void validate(Object model, Errors errors) {
        PageTemplateBean template = (PageTemplateBean) model;

        if (!StringUtils.hasText(template.getName())) {
            errors.rejectValue("name", "required", org.openiam.ui.util.messages.Errors.PAGE_TEMPLATE_NAME_NOT_SET.getMessageName());
        }
        if (!StringUtils.hasText(template.getName())) {
            errors.rejectValue("templateTypeId", "required", org.openiam.ui.util.messages.Errors.TEMPLATE_TYPE_REQUIRED.getMessageName());
        }

        if(template.getTemplateFieldList()!=null && !template.getTemplateFieldList().isEmpty()){
            int size= template.getTemplateFieldList().size();

            HashSet<MetadataFieldTemplateXref> elemSet  = new  HashSet<MetadataFieldTemplateXref>(template.getTemplateFieldList());
            if(size!=elemSet.size())
                errors.rejectValue("templateFieldList", "required", org.openiam.ui.util.messages.Errors.PAGE_TEMPLATE_FIELD_DUPLICATE.getMessageName());

        }

        if(template.getCustomFieldList()!=null && !template.getCustomFieldList().isEmpty()){
            int size= template.getCustomFieldList().size();

            HashSet<MetadataElementPageTemplateXref> elemSet  = new  HashSet<MetadataElementPageTemplateXref>(template.getCustomFieldList());
            if(size!=elemSet.size())
                errors.rejectValue("customFieldList", "required", org.openiam.ui.util.messages.Errors.PAGE_TEMPLATE_CUSTOM_FIELD_DUPLICATE.getMessageName());

        }
    }

}
