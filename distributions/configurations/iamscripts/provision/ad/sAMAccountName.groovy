import org.openiam.idm.srvc.user.dto.UserAttribute

if(org.apache.commons.lang.StringUtils.isNotEmpty(targetSystemOperation.toString()) && org.openiam.base.AttributeOperationEnum.REPLACE.equals(targetSystemOperation)) {
    output = targetSystemIdentity;
} else {
    UserAttribute attr = user.getUserAttributes().get("samAccountName");
	if (attr != null && attr.value != null ) {
		output =  attr.value;
	} else {
		output=user.employeeId;
	}
}

