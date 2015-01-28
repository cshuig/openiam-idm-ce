package org.openiam.ui.rest.api.mvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.openiam.idm.searchbeans.MetadataElementSearchBean;
import org.openiam.idm.srvc.meta.dto.MetadataElement;
import org.openiam.idm.srvc.meta.dto.MetadataType;
import org.openiam.ui.rest.api.model.FieldSearchResponse;
import org.openiam.ui.web.model.BeanResponse;
import org.openiam.ui.web.mvc.AbstractController;
import org.openiam.ui.webconsole.web.model.CustomFieldBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FieldRestController extends AbstractController {

	protected static final String metaTypeGrouping ="UI_WIDGET";
	
	@RequestMapping(value = "/fields/searchCustomFields", method = RequestMethod.GET)
    public @ResponseBody
    BeanResponse searchCustomFields(@RequestParam(required = false, value = "name") String name,
                                    @RequestParam(required = false, value = "typeId") String typeId,
                                    final @RequestParam(required = true, value = "size") int size,
                                    final @RequestParam(required = true, value = "from") int from) {

        FieldSearchResponse resp = findCustomFieldList(name, typeId, from, size);

        List<CustomFieldBean> fields = new ArrayList<CustomFieldBean>();
        HashMap<String, MetadataType> typeMap = getFieldTypeMap(resp.getTypeList());

        if (resp.getResultList() != null && !resp.getResultList().isEmpty()) {
            for (MetadataElement elem : resp.getResultList()) {
                MetadataType type = typeMap.get(elem.getMetadataTypeId());

                CustomFieldBean field = new CustomFieldBean();
                field.setId(elem.getId());
                field.setName(elem.getAttributeName());

                if (type != null) {
                    field.setTypeId(type.getId());
                    field.setFieldTypeDescription(type.getDescription());
                }

                fields.add(field);
            }
        }

        return new BeanResponse(fields, resp.getCount());
    }
	
	private FieldSearchResponse findCustomFieldList(String fieldName, String fieldType, int from,int size){
		if(StringUtils.isNotBlank(fieldName)) {
			if(fieldName.charAt(0) != '*') {
				fieldName = "*" + fieldName;
			}
			if(fieldName.charAt(fieldName.length() - 1) != '*') {
				fieldName = fieldName + "*";
			}
		}

		final List<MetadataType> typeList = getFieldTypeList();

		final Set<String> typeSetToSearch = new HashSet<String>();
		if(StringUtils.isBlank(fieldType)){
			if(typeList!=null){
				for (MetadataType type: typeList){
					typeSetToSearch.add(type.getId());
				}
			}
		} else{
			typeSetToSearch.add(fieldType);
		}

		final MetadataElementSearchBean searchBean = new MetadataElementSearchBean();
		searchBean.setAttributeName(fieldName);
		searchBean.setTypeIdSet(typeSetToSearch);
		searchBean.setDeepCopy(false);
		final List<MetadataElement> resultList = metadataWebService.findElementBeans(searchBean, from, size, getCurrentLanguage());
		final int count = metadataWebService.countElementBeans(searchBean);

		return new FieldSearchResponse(resultList, typeList, count);
	}
}
