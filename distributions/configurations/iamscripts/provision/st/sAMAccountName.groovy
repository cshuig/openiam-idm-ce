import org.openiam.idm.srvc.user.dto.UserAttribute



UserAttribute attr = user.getUserAttributes().get("samAccountName");
if (attr != null && attr.value != null ) {
	output =  attr.value;
}else {
	output=user.employeeId;
}

