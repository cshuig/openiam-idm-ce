package org.openiam.ui.selfservice.web.groovy;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.openiam.idm.srvc.continfo.dto.Address;
import org.openiam.idm.srvc.continfo.dto.EmailAddress;
import org.openiam.idm.srvc.continfo.dto.Phone;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.ui.util.SpringContextProvider;
import org.openiam.ui.util.messages.Errors;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.support.RequestContext;

public class AbstractAuthenticatedContactInfoProcessor extends AbstractContactInfoProcessor {

	protected User user;
	
	public void init(final Map<String, Object> bindingMap) {
		this.user = (User)bindingMap.get("user");
		super.init(bindingMap);
	}
}
