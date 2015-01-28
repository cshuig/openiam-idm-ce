package org.openiam.ui.selfservice.web.groovy;

import org.openiam.idm.srvc.continfo.dto.Address;
import org.openiam.idm.srvc.continfo.dto.EmailAddress;
import org.openiam.idm.srvc.continfo.dto.Phone;
import org.openiam.ui.util.messages.Errors;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

public class ContactInfoPostprocessor extends AbstractAuthenticatedContactInfoProcessor {

	public String validate(final EmailAddress address) {
		String retVal = null;
		if(StringUtils.isBlank(address.getEmailAddress())) {
			retVal = Errors.EMAIL_MISSING.getMessageName();
		} else if(!address.getEmailAddress().contains("@")) { /* don't go overboard with validation - client can do it in groovy */
			retVal = Errors.EMAIL_INVALID.getMessageName();
		}
		return retVal;
	}
	
	public String validate(final Phone phone) {
		return null;
	}
	
	public String validate(final Address address) {
		return null;
	}
}
