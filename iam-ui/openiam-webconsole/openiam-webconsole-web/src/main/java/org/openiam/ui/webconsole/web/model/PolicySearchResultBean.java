package org.openiam.ui.webconsole.web.model;

import java.util.List;

import org.openiam.idm.srvc.policy.dto.Policy;

public class PolicySearchResultBean {
	
	private int size;
	private List<PolicyBean> beans;
	
	public PolicySearchResultBean() {
		
	}

	public PolicySearchResultBean(final int size, final List<PolicyBean> beans) {
		this.size = size;
		this.beans = beans;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<PolicyBean> getBeans() {
		return beans;
	}

	public void setBeans(List<PolicyBean> beans) {
		this.beans = beans;
	}
	
	
}
