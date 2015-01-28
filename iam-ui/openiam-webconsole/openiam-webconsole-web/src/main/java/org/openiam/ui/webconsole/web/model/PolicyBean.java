package org.openiam.ui.webconsole.web.model;

import org.openiam.ui.rest.api.model.KeyNameBean;

public class PolicyBean extends KeyNameBean {

	private Integer status;
	private String description;
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
