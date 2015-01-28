package org.openiam.ui.selfservice.web.groovy;

import java.util.Map;
import java.util.Set;

import org.openiam.idm.srvc.continfo.dto.Address;
import org.openiam.idm.srvc.continfo.dto.EmailAddress;
import org.openiam.idm.srvc.continfo.dto.Phone;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.ui.util.SpringContextProvider;
import org.springframework.context.ApplicationContext;

public class AuthenticatedContactInfoPreprocessor extends AbstractAuthenticatedContactInfoProcessor {
	
	public boolean isEditable(final EmailAddress address) {
		return true;
	}
	
	public boolean isEditable(final Phone phone) {
		return true;
	}
	
	public boolean isEditable(final Address address) {
		return true;
	}
}
