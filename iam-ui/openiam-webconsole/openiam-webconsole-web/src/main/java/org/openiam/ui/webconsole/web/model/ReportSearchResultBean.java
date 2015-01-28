package org.openiam.ui.webconsole.web.model;

import java.util.List;


public class ReportSearchResultBean {
	
	private int size;
	private List<ReportBean> beans;
	
	public ReportSearchResultBean() {
		
	}

	public ReportSearchResultBean(final int size, final List<ReportBean> beans) {
		this.size = size;
		this.beans = beans;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<ReportBean> getBeans() {
		return beans;
	}

	public void setBeans(List<ReportBean> beans) {
		this.beans = beans;
	}
	
	
}
