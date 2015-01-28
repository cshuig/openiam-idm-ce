package org.openiam.ui.rest.api.model;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openiam.idm.srvc.audit.dto.AuditLogTarget;

import org.openiam.idm.srvc.audit.dto.IdmAuditLog;
import org.openiam.ui.web.model.AbstractBean;
import org.openiam.ui.web.util.DateFormatStr;

import java.util.Date;
import java.util.HashSet;


public class AuditLogBean extends AbstractBean {


    private String principal;
	private String source;
	private String action;
	private Date timestamp;
	private String result;
    private java.util.Set<org.openiam.idm.srvc.audit.dto.AuditLogTarget> targets;

	public AuditLogBean() {}

    public AuditLogBean(final IdmAuditLog idmAuditLog) {
        this.setId(idmAuditLog.getId());
        this.timestamp = idmAuditLog.getTimestamp();
        this.source = idmAuditLog.getSource();
        this.action = idmAuditLog.getAction();
        this.result = idmAuditLog.getResult();
        this.principal = idmAuditLog.getPrincipal();
        this.setHasChild(idmAuditLog.getChildLogs() != null  && idmAuditLog.getChildLogs().size() > 0);
        if(idmAuditLog.getTargets() != null){
            for(AuditLogTarget auditLogTarget : idmAuditLog.getTargets()) {
                this.addTarget(auditLogTarget);
            }
        }
    }

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

    public String getFormattedDate(){
        DateTimeFormatter fmt = DateTimeFormat.forPattern(DateFormatStr.getSdfDateTime());
        return fmt.print(this.timestamp.getTime());
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

    public String getTarget() {
        StringBuilder target = new StringBuilder();
        int n =0;
        if(this.targets != null) {
            for(AuditLogTarget logTarget : targets) {
                if(n > 0) {
                    target.append(", ");
                }
                target.append("[").append(logTarget.getTargetType()).append("] ").append(logTarget.getObjectPrincipal());
                n++;
            }
        }
        return target.toString();
    }

    public void addTarget(AuditLogTarget target) {
        if(this.targets == null) {
            this.targets = new HashSet<>();
        }
        this.targets.add(target);
    }
}
