package org.openiam.ui.webconsole.idm.web.mvc.provisioning.batch;

import java.util.Date;

import org.openiam.ui.rest.api.model.KeyNameBean;

public class BatchDataBean extends KeyNameBean {

	private boolean enabled;
	private String cronExpression;
	private Date runOn;
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public Date getRunOn() {
		return runOn;
	}
	public void setRunOn(Date runOn) {
		this.runOn = runOn;
	}
	
	
}
