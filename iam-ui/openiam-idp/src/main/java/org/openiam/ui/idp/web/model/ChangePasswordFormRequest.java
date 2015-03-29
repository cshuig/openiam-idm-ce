package org.openiam.ui.idp.web.model;

import org.apache.cxf.common.util.StringUtils;

public class ChangePasswordFormRequest {

	private String login;
    private String userId;
	private String currentPassword;
	private String newPassword;
	private String newPasswordConfirm;
	private String managedSystemId;
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getCurrentPassword() {
		return currentPassword;
	}
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getNewPasswordConfirm() {
		return newPasswordConfirm;
	}
	public void setNewPasswordConfirm(String newPasswordConfirm) {
		this.newPasswordConfirm = newPasswordConfirm;
	}

	public String getManagedSystemId() {
		return managedSystemId;
	}

	public void setManagedSystemId(String managedSystemId) {
		this.managedSystemId = managedSystemId;
	}

	public boolean hasEmptyField() {
		return StringUtils.isEmpty(login) ||
               StringUtils.isEmpty(userId) ||
			   StringUtils.isEmpty(currentPassword) || 
			   StringUtils.isEmpty(newPassword) || 
			   StringUtils.isEmpty(newPasswordConfirm);
	}
}
