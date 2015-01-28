package org.openiam.ui.rest.api.model;

import java.util.List;

public class GroupSearchMetadata {

	private List<KeyNameBean> managedSystems;
	
	public GroupSearchMetadata() {}
	
	public List<KeyNameBean> getManagedSystems() {
		return managedSystems;
	}
	public void setManagedSystems(List<KeyNameBean> managedSystems) {
		this.managedSystems = managedSystems;
	}
}
