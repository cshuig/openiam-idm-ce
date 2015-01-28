package org.openiam.ui.exception;

public class HttpErrorCodeException extends Exception {

	private int errorCode;
	private String errorMessage;
	
	public HttpErrorCodeException(final int errorCode) {
		this.errorCode = errorCode;
	}
	
	public HttpErrorCodeException(final int errorCode, final String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	
	
}
