package org.openiam.ui.selfservice.web.model;

import java.util.Date;

import org.openiam.bpm.response.TaskWrapper;
import org.openiam.ui.rest.api.model.KeyNameBean;
import org.openiam.ui.web.model.AbstractBean;

public class TaskWrapperBean extends KeyNameBean {
	
	private boolean deletable = true;
	private String parentTaskId;
	private String executionId;
	private String processInstanceId;
	private String processDefinitionId;
	private String description;
	private Date createdTime;
	private Date endDate;

	public TaskWrapperBean() {}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getParentTaskId() {
		return parentTaskId;
	}

	public void setParentTaskId(String parentTaskId) {
		this.parentTaskId = parentTaskId;
	}


	public boolean isDeletable() {
		return deletable;
	}

	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}
	
	
}
