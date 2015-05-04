package org.openiam.ui.rest.api.model;

import org.openiam.ui.web.model.AuthTokenInfo;
import org.openiam.ui.web.model.BasicAjaxResponse;

public class LoginAjaxResponse extends BasicAjaxResponse {
	
	public LoginAjaxResponse() {
		super();
	}
	
	private boolean passwordExpired;
	private String userId;
	private String unlockURL;
	private AuthTokenInfo tokenInfo;

	public String getUnlockURL() {
		return unlockURL;
	}

	public void setUnlockURL(String unlockURL) {
		this.unlockURL = unlockURL;
	}

	public AuthTokenInfo getTokenInfo() {
		return tokenInfo;
	}

	public void setTokenInfo(AuthTokenInfo tokenInfo) {
		this.tokenInfo = tokenInfo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isPasswordExpired() {
		return passwordExpired;
	}

	public void setPasswordExpired(boolean passwordExpired) {
		this.passwordExpired = passwordExpired;
	}

	
}
