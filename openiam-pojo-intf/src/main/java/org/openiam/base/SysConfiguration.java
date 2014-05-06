package org.openiam.base;

/**
 * Obtains configuration information for password change and reset from spring configuration files.
 *
 * @author suneet
 * @version 2
 */
public class SysConfiguration {
    protected String defaultManagedSysId = null;
    protected String defaultSecurityDomain = null;
    protected String organization = null;
    protected String division = null;
    protected String dept = null;

    public String getDefaultManagedSysId() {
        return defaultManagedSysId;
    }

    public void setDefaultManagedSysId(String defaultManagedSysId) {
        this.defaultManagedSysId = defaultManagedSysId;
    }

    public String getDefaultSecurityDomain() {
        return defaultSecurityDomain;
    }

    public void setDefaultSecurityDomain(String defaultSecurityDomain) {
        this.defaultSecurityDomain = defaultSecurityDomain;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
}
