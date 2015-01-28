package org.openiam.ui.rest.api.model;

public class GroupBean extends KeyNameBean {
	
	public GroupBean() {}
	
	private String managedSysId;
    private String managedSysName;
	public String getManagedSysId() {
		return managedSysId;
	}
	public void setManagedSysId(String managedSysId) {
		this.managedSysId = managedSysId;
	}
	public String getManagedSysName() {
		return managedSysName;
	}
	public void setManagedSysName(String managedSysName) {
		this.managedSysName = managedSysName;
	}
    
    
}
