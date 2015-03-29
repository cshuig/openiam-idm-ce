package org.openiam.ui.rest.api.model;

import org.openiam.ui.web.model.AbstractBean;

import java.util.List;

public class ResetPasswordBean extends AbstractBean {

    protected String userId;
    protected String currentPassword;
    protected String password;
    protected String confPassword;
    protected String principal;
    protected String managedSystemId;
    protected List<String> managedSystem;
    protected boolean notifyUserViaEmail;
    protected boolean autoGeneratePassword;
    private boolean userActivateFlag;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfPassword() {
        return confPassword;
    }

    public void setConfPassword(String confPassword) {
        this.confPassword = confPassword;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public List<String> getManagedSystem() {
        return managedSystem;
    }

    public void setManagedSystem(List<String> managedSystem) {
        this.managedSystem = managedSystem;
    }

    public String getManagedSystemId() {
        return managedSystemId;
    }

    public void setManagedSystemId(String managedSystemId) {
        this.managedSystemId = managedSystemId;
    }

    public boolean getNotifyUserViaEmail() {
        return notifyUserViaEmail;
    }

    public void setNotifyUserViaEmail(boolean notifyUserViaEmail) {
        this.notifyUserViaEmail = notifyUserViaEmail;
    }

    public boolean getAutoGeneratePassword() {
        return autoGeneratePassword;
    }

    public void setAutoGeneratePassword(boolean autoGeneratePassword) {
        this.autoGeneratePassword = autoGeneratePassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }
    public boolean getUserActivateFlag() {
        return userActivateFlag;
    }

    public void setUserActivateFlag(boolean userActivateFlag) {
        this.userActivateFlag = userActivateFlag;
    }
}
