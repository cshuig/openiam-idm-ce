package org.openiam.ui.web.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.util.OpeniamCookieLocaleResolver;
import org.springframework.context.MessageSource;

public class BasicAjaxResponse {

	private int status;
	private List<ErrorToken> errorList;
	private String redirectURL;
	private SuccessToken successToken;
    private String successMessage;
	private Map<String, Object> contextValues;
	
	/* this is a list present in case the caller needs a list of all possible errors for this ajax call.  
	 * Used to display password policy, for the most part 
	 * */
	private List<ErrorToken> possibleErrors;
	
	public BasicAjaxResponse() {}
	
	public BasicAjaxResponse(final int status) {
		this.status = status;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List<ErrorToken> getErrorList() {
		return errorList;
	}
	public void setErrorList(List<ErrorToken> errorList) {
		this.errorList = errorList;
	}
	
	public String getRedirectURL() {
		return redirectURL;
	}

	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}

	public void addError(final ErrorToken error) {
		if(error != null) {
			if(this.errorList == null) {
				this.errorList = new LinkedList<ErrorToken>();
			}
			this.errorList.add(error);
		}
	}
	
	public void addErrors(final Collection<ErrorToken> errorTokens) {
		if(errorTokens != null) {
			if(this.errorList == null) {
				this.errorList = new LinkedList<ErrorToken>();
			}
			this.errorList.addAll(errorTokens);
		}
	}

	public SuccessToken getSuccessToken() {
		return successToken;
	}

	public void setSuccessToken(SuccessToken successToken) {
		this.successToken = successToken;
	}

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public boolean isError() {
		return (CollectionUtils.isNotEmpty(errorList));
	}
	
	public void addContextValues(final String paramName, final Object paramValue) {
		if(contextValues == null) {
			contextValues = new HashMap<String, Object>();
		}
		contextValues.put(paramName, paramValue);
	}
	
	public Map<String, Object> getContextValues() {
		return contextValues;
	}
	
	public List<ErrorToken> getPossibleErrors() {
		return possibleErrors;
	}

	public void setPossibleErrors(List<ErrorToken> possibleErrors) {
		this.possibleErrors = possibleErrors;
	}

	public final void process(final OpeniamCookieLocaleResolver localeResolver, final MessageSource messageSource, final HttpServletRequest request) {
		if(successToken != null) {
			successMessage = messageSource.getMessage(
								successToken.getMessage().getMessageName(), 
								null, 
								localeResolver.resolveLocale(request));
		}
		
		if(CollectionUtils.isNotEmpty(errorList)) {
			for(final ErrorToken errorToken : errorList) {
				 errorToken.setMessage(messageSource.getMessage(
	                        errorToken.getError().getMessageName(),
	                        errorToken.getParams(),
	                        localeResolver.resolveLocale(request)));
			}
		}
		if(CollectionUtils.isNotEmpty(possibleErrors)) {
			for(final ErrorToken token : possibleErrors) {
				token.setMessage(messageSource.getMessage(
						token.getError().getMessageName(),
						token.getParams(),
                        localeResolver.resolveLocale(request)));
			}
		}
	}
}
