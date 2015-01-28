package org.openiam.ui.web.model;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.web.util.OpeniamCookieLocaleResolver;
import org.springframework.context.MessageSource;

public class SetPasswordToken {

	private List<ErrorToken> rules;
	private List<ErrorToken> errorList;
	
	public SetPasswordToken() {}

	public List<ErrorToken> getRules() {
		return rules;
	}

	public void setRules(List<ErrorToken> rules) {
		this.rules = rules;
	}
	
	public void addRule(final ErrorToken rule) {
		if(rule != null) {
			if(this.rules == null) {
				this.rules = new LinkedList<>();
			}
			this.rules.add(rule);
		}
	}
	
	public List<ErrorToken> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<ErrorToken> errorList) {
		this.errorList = errorList;
	}
	
	public void addError(final ErrorToken token) {
		if(token != null) {
			if(this.errorList == null) {
				this.errorList = new LinkedList<>();
			}
			this.errorList.add(token);
		}
	}
	
	public boolean hasErrors() {
		return CollectionUtils.isNotEmpty(errorList);
	}
	
	public final void process(final OpeniamCookieLocaleResolver localeResolver, final MessageSource messageSource, final HttpServletRequest request) {
		
		if(CollectionUtils.isNotEmpty(rules)) {
			for(final ErrorToken errorToken : rules) {
				 errorToken.setMessage(messageSource.getMessage(
	                        errorToken.getError().getMessageName(),
	                        errorToken.getParams(),
	                        localeResolver.resolveLocale(request)));
			}
		}
		
		if(CollectionUtils.isNotEmpty(errorList)) {
			for(final ErrorToken errorToken : errorList) {
				 errorToken.setMessage(messageSource.getMessage(
	                        errorToken.getError().getMessageName(),
	                        errorToken.getParams(),
	                        localeResolver.resolveLocale(request)));
			}
		}
	}
}
