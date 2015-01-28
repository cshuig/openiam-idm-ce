package org.openiam.ui.login;

import org.openiam.idm.srvc.auth.ws.AuthenticationResponse;

public class LoginActionToken {
	
	public LoginActionToken() {}

	private AuthenticationResponse authResponse;
	private boolean successWithWarning;
	private boolean success;
	private int errCode;
	private Integer numOfDaysUntilPasswordExpiration;
	private Integer httpErrorCode;
	private boolean doRedirect;
	
	public boolean isSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public int getErrCode() {
		return errCode;
	}
	
	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}
	
	public Integer getNumOfDaysUntilPasswordExpiration() {
		return numOfDaysUntilPasswordExpiration;
	}
	
	public void setNumOfDaysUntilPasswordExpiration(
			Integer numOfDaysUntilPasswordExpiration) {
		this.numOfDaysUntilPasswordExpiration = numOfDaysUntilPasswordExpiration;
	}

	public boolean isDoRedirect() {
		return doRedirect;
	}

	public void setDoRedirect(boolean doRedirect) {
		this.doRedirect = doRedirect;
	}

	public Integer getHttpErrorCode() {
		return httpErrorCode;
	}

	public void setHttpErrorCode(int httpErrorCode) {
		this.httpErrorCode = Integer.valueOf(httpErrorCode);
	}

	public boolean isSuccessWithWarning() {
		return successWithWarning;
	}

	public void setSuccessWithWarning(boolean successWithWarning) {
		this.successWithWarning = successWithWarning;
	}

	public AuthenticationResponse getAuthResponse() {
		return authResponse;
	}

	public void setAuthResponse(AuthenticationResponse authResponse) {
		this.authResponse = authResponse;
	}
	
	
}
