package org.openiam.ui.login;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.openiam.idm.srvc.lang.dto.Language;
import org.openiam.ui.model.Hyperlink;
import org.openiam.ui.rest.api.model.KeyNameBean;

public class DefaultLoginPageDisplayHandler {

	protected HttpServletRequest request;
	
	protected Language locale;
	
	public DefaultLoginPageDisplayHandler() {
		
	}
	
	public void init(final Map<String, Object> bindingMap) {
		request = (HttpServletRequest)bindingMap.get("REQUEST");
		locale = (Language)bindingMap.get("LANGUAGE");
	}
	
	public List<Hyperlink> getAdditionalHyperlinks() {
		return Collections.EMPTY_LIST;
	}
}
