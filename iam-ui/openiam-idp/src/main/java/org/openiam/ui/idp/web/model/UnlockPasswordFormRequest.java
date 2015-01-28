package org.openiam.ui.idp.web.model;

import org.apache.cxf.common.util.StringUtils;

public class UnlockPasswordFormRequest {

	private String token;
	private String newPassword;
	private String newPasswordConfirm;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
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
	
	public boolean hasEmptyField() {
		return StringUtils.isEmpty(token) || 
			   StringUtils.isEmpty(newPassword) || 
			   StringUtils.isEmpty(newPasswordConfirm);
	}
}
