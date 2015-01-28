package org.openiam.ui.webconsole.am.web.model;

import org.openiam.ui.rest.api.model.KeyNameBean;

public class ContentProviderBean extends KeyNameBean {

	private String domainPattern;

	public String getDomainPattern() {
		return domainPattern;
	}

	public void setDomainPattern(String domainPattern) {
		this.domainPattern = domainPattern;
	}
	
	
}
