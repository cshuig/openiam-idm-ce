package org.openiam.ui.selfservice.web.model;

import java.util.List;

import org.openiam.bpm.response.TaskHistoryWrapper;

public class TaskHistoryTreeBeanResponse {
	
	private List<TaskHistoryWrapper> treeBeans;

	public TaskHistoryTreeBeanResponse() {}
	
	public TaskHistoryTreeBeanResponse(final List<TaskHistoryWrapper> treeBeans) {
		this.treeBeans = treeBeans;
	}

	public List<TaskHistoryWrapper> getTreeBeans() {
		return treeBeans;
	}

	public void setTreeBeans(List<TaskHistoryWrapper> treeBeans) {
		this.treeBeans = treeBeans;
	}
	
	
}
