package org.openiam.ui.webconsole.am.web.model;

import org.openiam.ui.rest.api.model.KeyNameBean;

public class AuthLevelAttributeBean extends KeyNameBean {
	
	public AuthLevelAttributeBean() {
		
	}

	private String metaTypeName;

	public String getMetaTypeName() {
		return metaTypeName;
	}

	public void setMetaTypeName(String metaTypeName) {
		this.metaTypeName = metaTypeName;
	}
	
	
}
