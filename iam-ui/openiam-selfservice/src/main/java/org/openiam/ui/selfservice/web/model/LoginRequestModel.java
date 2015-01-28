package org.openiam.ui.selfservice.web.model;

public class LoginRequestModel {

	private String userId;
	private String loginId;
	private String login;
	private String managedSysId;
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getManagedSysId() {
		return managedSysId;
	}
	public void setManagedSysId(String managedSysId) {
		this.managedSysId = managedSysId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
