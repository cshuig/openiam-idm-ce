package org.openiam.ui.webconsole.web.model;

import org.openiam.ui.rest.api.model.KeyNameBean;

public class ReportBean extends KeyNameBean {

    private String reportUrl;
    private Integer parameterCount;

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

    public Integer getParameterCount() {
        return parameterCount;
    }

    public void setParameterCount(Integer parameterCount) {
        this.parameterCount = parameterCount;
    }
}
