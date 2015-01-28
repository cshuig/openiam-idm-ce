package org.openiam.ui.selfservice.web.model;

import java.util.HashSet;
import java.util.Set;

public class AttestationRequetModel {

	private String taskId;
	private String userId;
	private HashSet<String> roleIds;
	private HashSet<String> groupIds;
	private HashSet<String> resourceIds;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Set<String> getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(HashSet<String> roleIds) {
		this.roleIds = roleIds;
	}
	public HashSet<String> getGroupIds() {
		return groupIds;
	}
	public void setGroupIds(HashSet<String> groupIds) {
		this.groupIds = groupIds;
	}
	public Set<String> getResourceIds() {
		return resourceIds;
	}
	public void setResourceIds(HashSet<String> resourceIds) {
		this.resourceIds = resourceIds;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	
}
