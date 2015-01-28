package org.openiam.ui.idp.openid.exception;

import org.openid4java.message.Message;

public class OpenIDMessageException extends Exception {

	private Message message;
	
	public OpenIDMessageException(final Message message) {
		this.message = message;
	}
	
	public Message getOpenIDMessage() {
		return message;
	}
}
