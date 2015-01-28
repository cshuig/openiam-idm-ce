package org.openiam.ui.webconsole.validator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.openiam.idm.srvc.mngsys.dto.AttributeMap;
import org.openiam.ui.webconsole.web.model.PolicyMapRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PolicyMapReqestValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return PolicyMapRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object model, Errors errors) {
        PolicyMapRequest pmRequest = (PolicyMapRequest) model;
        List<AttributeMap> amList = pmRequest.getAttrMapList();
        if (CollectionUtils.isEmpty(amList)) {
            errors.rejectValue("attrMapList", "required",
                    org.openiam.ui.util.messages.Errors.EMPTY_POLICY_MAP
                            .getMessageName());
        }
        Set<String> names = new HashSet<String>();
        for (AttributeMap am : amList) {
            if (StringUtils.hasText(am.getAttributeMapId())) {
                if (!StringUtils.hasText(am.getAttributeName())) {
                    errors.rejectValue("attrMapList", "required",
                            org.openiam.ui.util.messages.Errors.EMPTY_ATTR_NAME
                                    .getMessageName());

                    break;
                } else if (names.contains(am.getAttributeName())) {
                    errors.rejectValue(
                            "attrMapList",
                            "required",
                            org.openiam.ui.util.messages.Errors.DUBLICATED_ATTR_NAME
                                    .getMessageName());
                    break;
                } else {
                    names.add(am.getAttributeName());
                }
                if (am.getReconResAttribute() == null) {
                    errors.rejectValue(
                            "attrMapList",
                            "required",
                            org.openiam.ui.util.messages.Errors.EMPTY_ATTR_POLICY
                                    .getMessageName());

                    break;
                } else if (am.getReconResAttribute()
                        .getDefaultAttributePolicy() == null
                        && am.getReconResAttribute().getAttributePolicy() == null) {
                    errors.rejectValue(
                            "attrMapList",
                            "required",
                            org.openiam.ui.util.messages.Errors.EMPTY_ATTR_POLICY
                                    .getMessageName());

                    break;
                }
            }
        }
    }
}
