package org.openiam.ui.rest.api.model;

import org.openiam.ui.rest.api.model.KeyNameBean;

public class IdentityQuestionBean extends KeyNameBean {

	private Boolean active;
	
	public Boolean getActive() {
		return active;
	}
	
	public void setActive(Boolean status) {
		this.active = status;
	}
}
