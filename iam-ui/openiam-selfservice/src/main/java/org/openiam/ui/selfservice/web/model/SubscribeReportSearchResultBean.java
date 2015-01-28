package org.openiam.ui.selfservice.web.model;

import java.util.List;


public class SubscribeReportSearchResultBean {
	
	private int size;
	private List<SubscribeReportBean> beans;
	
	public SubscribeReportSearchResultBean() {
		
	}

	public SubscribeReportSearchResultBean(final int size, final List<SubscribeReportBean> beans) {
		this.size = size;
		this.beans = beans;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<SubscribeReportBean> getBeans() {
		return beans;
	}

	public void setBeans(List<SubscribeReportBean> beans) {
		this.beans = beans;
	}
	
	
}
