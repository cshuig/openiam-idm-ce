package org.openiam.ui.exception;

import org.openiam.ui.util.messages.Errors;

public class ErrorMessageException extends Exception {

	private Errors error;
	
	public ErrorMessageException(final Errors error) {
		this.error = error;
	}
	
	public Errors getError() {
		return error;
	}
}
