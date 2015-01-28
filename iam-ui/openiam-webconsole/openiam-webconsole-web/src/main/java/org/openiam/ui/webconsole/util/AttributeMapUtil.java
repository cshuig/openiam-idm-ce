package org.openiam.ui.webconsole.util;

import java.util.List;

import org.openiam.idm.srvc.mngsys.dto.AttributeMap;
import org.openiam.idm.srvc.mngsys.dto.PolicyMapObjectTypeOptions;

public class AttributeMapUtil {
	public static boolean isPrincipalMissing(List<AttributeMap> attrMap) {

		if (!attrMap.isEmpty()) {
			for (AttributeMap mapObj : attrMap) {
				if (PolicyMapObjectTypeOptions.PRINCIPAL.name().equalsIgnoreCase(mapObj.getMapForObjectType())) {
					return false;
				}
                if (PolicyMapObjectTypeOptions.GROUP_PRINCIPAL.name().equalsIgnoreCase(mapObj.getMapForObjectType())) {
                    return false;
                }
			}
		}
		return true;
	}
}
