package org.openiam.ui.rest.api.model;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 10/26/13
 * Time: 12:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class RoleBean extends KeyNameBean {

    private String managedSysId;
    private String managedSysName;

    public RoleBean(){}

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
}
