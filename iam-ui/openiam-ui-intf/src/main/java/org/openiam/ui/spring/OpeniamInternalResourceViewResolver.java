package org.openiam.ui.spring;

import org.springframework.beans.BeanUtils;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

public class OpeniamInternalResourceViewResolver extends InternalResourceViewResolver {
	
	private Boolean exposePathVariables;

	public OpeniamInternalResourceViewResolver() {
		super();
	}
	
	@Override
	public void setExposePathVariables(Boolean exposePathVariables) {
		this.exposePathVariables = exposePathVariables;
	}

	@Override
	protected AbstractUrlBasedView buildView(String viewName) throws Exception {
		if(viewName.startsWith("jar:")) {
			viewName = viewName.substring("jar:".length());
			final AbstractUrlBasedView view = (AbstractUrlBasedView) BeanUtils.instantiateClass(getViewClass());
			view.setUrl(viewName + getSuffix());
			String contentType = getContentType();
			if (contentType != null) {
				view.setContentType(contentType);
			}
			view.setRequestContextAttribute(getRequestContextAttribute());
			view.setAttributesMap(getAttributesMap());
			if (this.exposePathVariables != null) {
				view.setExposePathVariables(exposePathVariables);
			}
			return view;
		} else {
			return super.buildView(viewName);
		}
	}
	
	
}
