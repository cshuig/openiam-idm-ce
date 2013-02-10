package org.openiam.selfsrvc.reports;

import org.openiam.idm.srvc.report.dto.ReportSubscriptionDto;
import org.springframework.web.multipart.MultipartFile;

public class SubscribeReportsCommand {
    private ReportSubscriptionDto report = new ReportSubscriptionDto();
    private String[] paramName;
    private String[] paramValue;
    private String[] paramTypeId;

    public ReportSubscriptionDto getReport() {
        return report;
    }

    public void setReport(ReportSubscriptionDto report) {
        this.report = report;
    }

	public String[] getParamName() {
		return paramName;
	}

	public void setParamName(String[] paramName) {
		this.paramName = paramName;
	}

	public String[] getParamValue() {
		return paramValue;
	}

	public void setParamValue(String[] paramValue) {
		this.paramValue = paramValue;
	}

	public String[] getParamTypeId() {
		return paramTypeId;
	}

	public void setParamTypeId(String[] paramTypeId) {
		this.paramTypeId = paramTypeId;
	}
}
