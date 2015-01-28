package org.openiam.ui.selfservice.web.exception;

import org.openiam.ui.util.messages.Errors;

public class ValidationException extends Exception {

	private Errors error;
	
	public ValidationException(final Errors error) {
		this.error = error;
	}

	public Errors getError() {
		return error;
	}
	
	
}
