package org.openiam.ui.selfservice.web.model;

public class TaskSummary {

	private int numOfAssignedTasks;
	private int numOfCandidateTasks;
	
	private TaskSummary() {}
	
	public TaskSummary(final int numOfAssignedTasks, final int numOfCandidateTasks) {
		this.numOfAssignedTasks = numOfAssignedTasks;
		this.numOfCandidateTasks = numOfCandidateTasks;
	}

	public int getNumOfAssignedTasks() {
		return numOfAssignedTasks;
	}

	public void setNumOfAssignedTasks(int numOfAssignedTasks) {
		this.numOfAssignedTasks = numOfAssignedTasks;
	}

	public int getNumOfCandidateTasks() {
		return numOfCandidateTasks;
	}

	public void setNumOfCandidateTasks(int numOfCandidateTasks) {
		this.numOfCandidateTasks = numOfCandidateTasks;
	}
	
	
}
