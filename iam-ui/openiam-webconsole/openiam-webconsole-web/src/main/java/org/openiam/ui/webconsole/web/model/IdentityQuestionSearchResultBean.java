package org.openiam.ui.webconsole.web.model;

import java.util.List;

import org.openiam.ui.rest.api.model.IdentityQuestionBean;


public class IdentityQuestionSearchResultBean {
	
	private int size;
	private List<IdentityQuestionBean> beans;
	
	public IdentityQuestionSearchResultBean() {
		
	}

	public IdentityQuestionSearchResultBean(final int size, final List<IdentityQuestionBean> beans) {
		this.size = size;
		this.beans = beans;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<IdentityQuestionBean> getBeans() {
		return beans;
	}

	public void setBeans(List<IdentityQuestionBean> beans) {
		this.beans = beans;
	}
	
	
}
