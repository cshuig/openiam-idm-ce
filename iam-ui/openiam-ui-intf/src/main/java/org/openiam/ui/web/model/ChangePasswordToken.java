package org.openiam.ui.web.model;

public class ChangePasswordToken {

	private String postbackURL;
	private int changeReason;
	private String managedSysId;
	private Integer numOfDaysUntilPasswordExpiration;
	
	public ChangePasswordToken(final String postbackURL, final int changeReason, final String managedSysId) {
		this.changeReason = changeReason;
		this.postbackURL = postbackURL;
		this.managedSysId = managedSysId;
	}
	
	public ChangePasswordToken(final String postbackURL, final int changeReason, final String managedSysId, final Integer numOfDaysUntilPasswordExpiration) {
		this(postbackURL, changeReason, managedSysId);
		this.numOfDaysUntilPasswordExpiration = numOfDaysUntilPasswordExpiration;
	}

	public String getPostbackURL() {
		return postbackURL;
	}

	public int getChangeReason() {
		return changeReason;
	}

	public String getManagedSysId() {
		return managedSysId;
	}

	public Integer getNumOfDaysUntilPasswordExpiration() {
		return numOfDaysUntilPasswordExpiration;
	}
}
