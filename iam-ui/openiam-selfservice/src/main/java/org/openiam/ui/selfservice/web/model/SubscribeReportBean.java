package org.openiam.ui.selfservice.web.model;


import org.openiam.ui.rest.api.model.KeyNameBean;

public class SubscribeReportBean extends KeyNameBean {

	private String reportName;
	private String reportId;
	private String status;
	private String reportInfoId;
	
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		setId(reportId);
		this.reportId = reportId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "SubscribeReportBean [reportName=" + reportName + ", reportId="
				+ reportId + ", status=" + status + ", reportInfoId=" + reportInfoId +"]";
	}
	public String getReportInfoId() {
		return reportInfoId;
	}
	public void setReportInfoId(String reportInfoId) {
		this.reportInfoId = reportInfoId;
	}
	
	
	
}
