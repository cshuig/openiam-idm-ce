package org.openiam.ui.webconsole.validator;

import org.apache.commons.collections.MapUtils;
import org.openiam.idm.srvc.lang.dto.LanguageMapping;
import org.openiam.idm.srvc.meta.dto.MetadataValidValue;
import org.openiam.ui.webconsole.web.model.CustomFieldBean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Map;

@Component
public class CustomFieldValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return CustomFieldBean.class.equals(clazz);
    }

    @Override
    public void validate(Object model, Errors errors) {
        CustomFieldBean field = (CustomFieldBean) model;

        if (!StringUtils.hasText(field.getName())) {
            errors.rejectValue("name", "required", org.openiam.ui.util.messages.Errors.CUSTOM_FIELD_NAME_NOT_SET.getMessageName());
        }
        if (!StringUtils.hasText(field.getTypeId())) {
            errors.rejectValue("typeId", "required", org.openiam.ui.util.messages.Errors.CUSTOM_FIELD_TYPE_NOT_SET.getMessageName());
        }
        if(field.getDisplayNameLanguageMap()==null || field.getDisplayNameLanguageMap().isEmpty()){
            errors.rejectValue("displayNameLanguageMap", "required", org.openiam.ui.util.messages.Errors.CUSTOM_FIELD_DISPLAY_NAME_NOT_SET.getMessageName());
        } else{
            if(checkLanguageMapping(field.getDisplayNameLanguageMap())){
                errors.rejectValue("displayNameLanguageMap", "required", org.openiam.ui.util.messages.Errors.CUSTOM_FIELD_DISPLAY_NAME_NOT_SET.getMessageName());
            }
        }

        if("SELECT".equals(field.getTypeId())
                || "MULTI_SELECT".equals(field.getTypeId())
                || "CHECKBOX".equals(field.getTypeId())
                || "RADIO".equals(field.getTypeId())){
           // check valid values

           if(field.getValidValues()!=null && !field.getValidValues().isEmpty()){

               for (MetadataValidValue value: field.getValidValues()){
                   if (!StringUtils.hasText(value.getUiValue())) {
                       errors.rejectValue("validValues", "required", org.openiam.ui.util.messages.Errors.CUSTOM_FIELD_OPTION_VALUE_NOT_SET.getMessageName());
                   }
                   if(MapUtils.isNotEmpty(value.getLanguageMap())) {
                	   if(checkLanguageMapping(value.getLanguageMap())){
                		   errors.rejectValue("validValues", "required", org.openiam.ui.util.messages.Errors.CUSTOM_FIELD_OPTION_DISPLAY_NAME_NOT_SET.getMessageName());
                	   }
                   }
               }

           }

        }
    }

    private boolean checkLanguageMapping(Map<String,LanguageMapping> languageMap){
        boolean isEmpty = true;
        for(String key : languageMap.keySet()){
            LanguageMapping lm = languageMap.get(key);
            if(lm==null) continue;
            if(lm.getValue()!=null && !lm.getValue().trim().isEmpty()){
                isEmpty=false;
                break;
            }
        }
        return  isEmpty;
    }
}
