if (user.phones != null && user.phones.size() > 0) {
for (org.openiam.idm.srvc.continfo.dto.Phone phone:user.phones){
	if (user.phones.size() == 1 || phone.isDefault){
	    output=phone.phoneNbr;
		break;	
	}
}
}else {
    output=null
}
