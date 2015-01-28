package org.openiam.ui.bean.converter;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dozer.CustomConverter;
import org.openiam.idm.srvc.res.dto.Resource;

public class ResourceNameConverter implements CustomConverter {
	
	private static Logger LOG = Logger.getLogger(ResourceNameConverter.class);

	@Override
	public Object convert(Object existingDestinationFieldValue,
			Object sourceFieldValue, Class<?> destinationClass,
			Class<?> sourceClass) {
		String retVal = null;
		if(sourceFieldValue != null) {
			if(sourceFieldValue instanceof Resource) {
				final Resource resource = (Resource)sourceFieldValue;
				if(StringUtils.isNotBlank(resource.getDisplayName())) {
					retVal = resource.getDisplayName();
				} else if(StringUtils.isNotBlank(resource.getCoorelatedName())) {
					retVal = resource.getCoorelatedName();
				} else {
					retVal = resource.getName();
				}
			}
		}
		return retVal;
	}

}
