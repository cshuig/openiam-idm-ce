package org.openiam.ui.util.messages;

public class SuccessToken {

	private SuccessMessage message;
	
	public SuccessToken(final SuccessMessage message) {
		this.message = message;
	}

	public SuccessMessage getMessage() {
		return message;
	}

	public void setMessage(SuccessMessage message) {
		this.message = message;
	}
	
	
}
