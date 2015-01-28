package org.openiam.ui.rest.api.model;

import java.util.LinkedList;
import java.util.List;

public class AuditLogSearchFilter {
    private List<KeyNameBean> auditTargetTypes = new LinkedList<>();
    private List<KeyNameBean> auditTargetActions = new LinkedList<>();
    private List<KeyNameBean> auditTargetStatus = new LinkedList<>();
    private List<KeyNameBean> managedSystems = new LinkedList<>();

    public List<KeyNameBean> getAuditTargetTypes() {
        return auditTargetTypes;
    }

    public void setAuditTargetTypes(List<KeyNameBean> auditTargetTypes) {
        this.auditTargetTypes = auditTargetTypes;
    }

    public List<KeyNameBean> getAuditTargetActions() {
        return auditTargetActions;
    }

    public void setAuditTargetActions(List<KeyNameBean> auditTargetActions) {
        this.auditTargetActions = auditTargetActions;
    }

    public List<KeyNameBean> getAuditTargetStatus() {
        return auditTargetStatus;
    }

    public void setAuditTargetStatus(List<KeyNameBean> auditTargetStatus) {
        this.auditTargetStatus = auditTargetStatus;
    }

    public List<KeyNameBean> getManagedSystems() {
        return managedSystems;
    }

    public void setManagedSystems(List<KeyNameBean> managedSystems) {
        this.managedSystems = managedSystems;
    }
}
