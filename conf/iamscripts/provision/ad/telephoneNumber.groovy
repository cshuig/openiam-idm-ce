import org.openiam.idm.srvc.continfo.dto.Phone

output = null
def phoneList = user.phones

if (phoneList) {
	for (Phone p : phoneList) {
		if (p.isDefault && p.phoneNbr) {
			output = p.areaCd + "-" + p.phoneNbr
			return
		}
	}
}