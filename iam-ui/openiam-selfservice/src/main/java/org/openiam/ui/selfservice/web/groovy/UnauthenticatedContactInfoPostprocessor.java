package org.openiam.ui.selfservice.web.groovy;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.openiam.idm.srvc.auth.dto.Login;
import org.openiam.idm.srvc.continfo.dto.EmailAddress;
import org.springframework.beans.factory.annotation.Value;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

public class UnauthenticatedContactInfoPostprocessor extends ContactInfoPostprocessor {

	private String login;
	private List<EmailAddress> emails;
	
	public String validateLogin(final String login) {
		return null;
	}
	
	@Override
	public void init(final Map<String, Object> bindingMap) {
		login = (String)bindingMap.get("login");
		emails = (List<EmailAddress>)bindingMap.get("emails");
		createLoginIfNotExists();
		super.init(bindingMap);
	}
	
	private void createLoginIfNotExists() {
		if(login == null) {
			login = createNewLogin();
		}
	}
	
	protected String createNewLogin() {
		return (CollectionUtils.isNotEmpty(emails)) ? 
				StringUtils.trimToNull(emails.get(0).getEmailAddress()) : null;
	}
	
	public String getProcessedLogin() {
		return login;
	}
}
