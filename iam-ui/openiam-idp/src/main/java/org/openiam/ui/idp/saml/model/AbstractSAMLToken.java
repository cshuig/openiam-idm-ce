package org.openiam.ui.idp.saml.model;

import org.openiam.ui.util.messages.Errors;

public class AbstractSAMLToken {

	private Errors error;
	private Throwable exception;
	
	public boolean isError() {
		return (error != null);
	}
	
	public Errors getError() {
		return error;
	}
	
	public Throwable getException() {
		return exception;
	}
	

	public void setError(final Errors error, final Throwable exception) {
		this.error = error;
		this.exception = exception;
	}
}
