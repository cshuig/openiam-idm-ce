package org.openiam.selfsrvc.reports;

import org.openiam.idm.srvc.report.dto.ReportSubscriptionDto;
import org.springframework.web.multipart.MultipartFile;

public class SubscribeReportsCommand {
    private MultipartFile dataSourceScriptFile;
    private MultipartFile reportDesignFile;
    private ReportSubscriptionDto report = new ReportSubscriptionDto();

    public MultipartFile getDataSourceScriptFile() {
        return dataSourceScriptFile;
    }

    public void setDataSourceScriptFile(MultipartFile dataSourceScriptFile) {
        this.dataSourceScriptFile = dataSourceScriptFile;
    }

    public MultipartFile getReportDesignFile() {
        return reportDesignFile;
    }

    public void setReportDesignFile(MultipartFile reportDesignFile) {
        this.reportDesignFile = reportDesignFile;
    }

    public ReportSubscriptionDto getReport() {
        return report;
    }

    public void setReport(ReportSubscriptionDto report) {
        this.report = report;
    }
}
