package org.openiam.ui.webconsole.idm.web.mvc.provisioning.bean;

import org.openiam.ui.rest.api.model.KeyNameDescriptionBean;

public class ManagedSysBean extends KeyNameDescriptionBean {
	private String status;
	private String hostURL;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getHostURL() {
		return hostURL;
	}
	public void setHostURL(String hostURL) {
		this.hostURL = hostURL;
	}
	
	
}
