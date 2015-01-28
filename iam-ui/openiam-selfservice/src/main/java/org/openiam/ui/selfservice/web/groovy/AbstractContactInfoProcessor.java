package org.openiam.ui.selfservice.web.groovy;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.openiam.ui.util.SpringContextProvider;
import org.openiam.ui.util.messages.Errors;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.support.RequestContext;

public abstract class AbstractContactInfoProcessor {
	
	protected HttpServletRequest request;
	protected ApplicationContext context;
	private RequestContext requestContext;

	public void init(final Map<String, Object> bindingMap) {
		this.request = (HttpServletRequest)bindingMap.get("request");
		this.context = (ApplicationContext)bindingMap.get("context");
		requestContext = new RequestContext(request);
		SpringContextProvider.autowire(this);
	}
	
	public final String getMessage(final String key, final Object argument) {
		return getMessage(key, new Object[] {argument});
	}
	
	public final String getMessage(final String key, final Object[] arguments) {
		return requestContext.getMessage(key, arguments);
	}
	
	public final String getMessage(final Errors error, final Object[] arguments) {
		return getMessage(error.getMessageName(), arguments);
	}
	
	public final String getMessage(final Errors error, final Object argument) {
		return getMessage(error.getMessageName(), argument);
	}
	
	public boolean isSupportsEmailCreation() {
		return true;
	}
	
	public boolean isSupportsPhoneCreation() {
		return true;
	}
	
	public boolean isSupportsAddressCreation() {
		return true;
	}
}
