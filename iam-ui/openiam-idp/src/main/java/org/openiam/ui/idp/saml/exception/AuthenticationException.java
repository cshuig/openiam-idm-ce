package org.openiam.ui.idp.saml.exception;

import org.openiam.ui.util.messages.Errors;

public class AuthenticationException extends Exception {

	private Errors error;
	
	public AuthenticationException(final String message, final Errors error) {
		super(message);
		this.error = error;
	}
	
	public Errors getError() {
		return error;
	}
}
