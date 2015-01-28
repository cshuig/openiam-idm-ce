package org.openiam.ui.selfservice.web.model;

import java.util.List;


public class SubCriteriaParamReportSearchResultBean {
	
	private int size;
	private List<SubCriteriaParamReportBean> beans;
	
	public SubCriteriaParamReportSearchResultBean() {
		
	}

	public SubCriteriaParamReportSearchResultBean(final int size, final List<SubCriteriaParamReportBean> beans) {
		this.size = size;
		this.beans = beans;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<SubCriteriaParamReportBean> getBeans() {
		return beans;
	}

	public void setBeans(List<SubCriteriaParamReportBean> beans) {
		this.beans = beans;
	}
	
	
}
