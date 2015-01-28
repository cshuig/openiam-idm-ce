package org.openiam.ui.rest.api.model;

import org.openiam.ui.web.model.AbstractBean;

public class ResetPasswordBean extends AbstractBean {

    protected String userId;
    protected String password;
    protected String confPassword;
    protected String principal;
    protected String managedSystemId;
    protected boolean notifyUserViaEmail;
    protected boolean autoGeneratePassword;

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
}
