package org.openiam.ui.selfservice.web.model;

import java.util.List;

import org.openiam.bpm.response.TaskWrapper;

public class TaskHistoryBean {

	private int size;
	private List<TaskWrapper> tasks;
	
	public TaskHistoryBean() {}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<TaskWrapper> getTasks() {
		return tasks;
	}

	public void setTasks(List<TaskWrapper> tasks) {
		this.tasks = tasks;
	}
	
	
}
