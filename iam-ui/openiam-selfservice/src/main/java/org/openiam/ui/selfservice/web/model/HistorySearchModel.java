package org.openiam.ui.selfservice.web.model;

import org.openiam.bpm.request.HistorySearchBean;

public class HistorySearchModel {

	private HistorySearchBean searchBean;
	private int from;
	private int size;
	public HistorySearchBean getSearchBean() {
		return searchBean;
	}
	public void setSearchBean(HistorySearchBean searchBean) {
		this.searchBean = searchBean;
	}
	public int getFrom() {
		return from;
	}
	public void setFrom(int from) {
		this.from = from;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	
}
