package org.openiam.ui.webconsole.idm.web.mvc.provisioning.logviewer;

import org.hibernate.validator.constraints.NotBlank;
import org.openiam.ui.web.util.DateFormatStr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SynchReportCommand {

    private String synchConfigId;
    private Date auditLogDate;
    private String auditLogDateAsStr;
    private String auditLogId;
    private String reportType;

    public String getAuditLogId() {
        return auditLogId;
    }

    public void setAuditLogId(String auditLogId) {
        this.auditLogId = auditLogId;
    }

    public String getSynchConfigId() {
        return synchConfigId;
    }

    public void setSynchConfigId(String synchConfigId) {
        this.synchConfigId = synchConfigId;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }


    public Date getAuditLogDate() {
        return auditLogDate;
    }

    public void setAuditLogDate(Date auditLogDate) {
        this.auditLogDate = auditLogDate;
    }


    public String getAuditLogDateAsStr() {
        return auditLogDateAsStr;
    }

    public void setAuditLogDateAsStr(String auditLogDateAsStr) {
        this.auditLogDateAsStr = auditLogDateAsStr;
    }

    public void setDatesFromStr(){
        SimpleDateFormat sdf = new SimpleDateFormat(DateFormatStr.getSdfDate());

        if(this.auditLogDateAsStr!=null){
            try {
                this.auditLogDate = sdf.parse(this.auditLogDateAsStr);
            } catch (ParseException e) {
                this.auditLogDateAsStr = null;
            }
        }
    }
}
