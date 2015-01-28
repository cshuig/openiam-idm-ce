package org.openiam.ui.exception;

import java.util.LinkedList;
import java.util.List;

import org.openiam.ui.util.messages.ErrorToken;

public class ErrorTokenException extends Exception {

	private List<ErrorToken> tokenList;
	
	public ErrorTokenException(final ErrorToken error) {
		this.tokenList = new LinkedList<>();
		this.tokenList.add(error);
	}
	
	public ErrorTokenException(final List<ErrorToken> tokenList) {
		this.tokenList = tokenList;
	}
	
	public List<ErrorToken> getTokenList() {
		return tokenList;
	}
}
