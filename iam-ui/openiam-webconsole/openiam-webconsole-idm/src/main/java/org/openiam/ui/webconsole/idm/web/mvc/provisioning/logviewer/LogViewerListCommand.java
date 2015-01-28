package org.openiam.ui.webconsole.idm.web.mvc.provisioning.logviewer;

import org.openiam.ui.web.model.PaginationCommand;
import org.openiam.ui.web.util.DateFormatStr;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class LogViewerListCommand extends PaginationCommand {
    private String configId;
    @DateTimeFormat(pattern = "${org.openiam.date.format}")
    private Date startDate;
    @DateTimeFormat(pattern = "${org.openiam.date.format}5")
    private Date endDate;

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
