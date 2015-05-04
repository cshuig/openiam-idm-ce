package org.openiam.ui.rest.api.model;

import org.openiam.idm.srvc.recon.dto.ReconExecStatusOptions;

public class ReconcileConfigBean extends KeyNameBean {
    private String resourceId;
    private String managedSysId;
    private String reconType;
    private String status;
    private String execStatus;
    private String customProcessorScript;
    private String targetSystemSearchFilter;

    public String getExecStatus() {
        return execStatus;
    }

    public String getExecStatusValue() {
        return execStatus;
    }

    public void setExecStatus(String execStatus) {
        this.execStatus = execStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getManagedSysId() {
        return managedSysId;
    }

    public void setManagedSysId(String managedSysId) {
        this.managedSysId = managedSysId;
    }

    public String getReconType() {
        return reconType;
    }

    public void setReconType(String reconType) {
        this.reconType = reconType;
    }

    public String getCustomProcessorScript() {
        return customProcessorScript;
    }

    public void setCustomProcessorScript(String customProcessorScript) {
        this.customProcessorScript = customProcessorScript;
    }

    public String getTargetSystemSearchFilter() {
        return targetSystemSearchFilter;
    }

    public void setTargetSystemSearchFilter(String targetSystemSearchFilter) {
        this.targetSystemSearchFilter = targetSystemSearchFilter;
    }
}
