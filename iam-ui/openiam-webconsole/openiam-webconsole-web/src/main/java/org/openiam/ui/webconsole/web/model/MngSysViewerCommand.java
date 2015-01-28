package org.openiam.ui.webconsole.web.model;

import org.openiam.idm.srvc.auth.dto.LoginStatusEnum;

import java.io.Serializable;

public class MngSysViewerCommand implements Serializable {

    String managedSysId;
    String managedSysName;
    String managedSysStatus;
    String principalId;
    String principalName;
    int isPrincipalLocked;
    LoginStatusEnum principalStatus;

    public String getManagedSysId() {
        return managedSysId;
    }

    public void setManagedSysId(String managedSysId) {
        this.managedSysId = managedSysId;
    }

    public String getManagedSysName() {
        return managedSysName;
    }

    public void setManagedSysName(String managedSysName) {
        this.managedSysName = managedSysName;
    }

    public String getManagedSysStatus() {
        return managedSysStatus;
    }

    public void setManagedSysStatus(String managedSysStatus) {
        this.managedSysStatus = managedSysStatus;
    }

    public String getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public int getIsPrincipalLocked() {
        return isPrincipalLocked;
    }

    public void setIsPrincipalLocked(int isPrincipalLocked) {
        this.isPrincipalLocked = isPrincipalLocked;
    }

    public LoginStatusEnum getPrincipalStatus() {
        return principalStatus;
    }

    public void setPrincipalStatus(LoginStatusEnum principalStatus) {
        this.principalStatus = principalStatus;
    }
}
