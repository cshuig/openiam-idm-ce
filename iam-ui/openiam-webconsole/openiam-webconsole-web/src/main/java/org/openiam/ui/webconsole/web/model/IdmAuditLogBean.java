package org.openiam.ui.webconsole.web.model;

import org.openiam.ui.web.model.AbstractBean;

import java.util.Date;

public class IdmAuditLogBean extends AbstractBean{
    private Date actionDatetime;
    private String actionId;
    private String actionStatus;
    private String resourceName;
    private String reason;
    private String principal;
    private String targetPrincipal;

    public IdmAuditLogBean() {
    }

    public Date getActionDatetime() {
        return actionDatetime;
    }

    public void setActionDatetime(Date actionDatetime) {
        this.actionDatetime = actionDatetime;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(String actionStatus) {
        this.actionStatus = actionStatus;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getTargetPrincipal() {
        return targetPrincipal;
    }

    public void setTargetPrincipal(String targetPrincipal) {
        this.targetPrincipal = targetPrincipal;
    }
}
