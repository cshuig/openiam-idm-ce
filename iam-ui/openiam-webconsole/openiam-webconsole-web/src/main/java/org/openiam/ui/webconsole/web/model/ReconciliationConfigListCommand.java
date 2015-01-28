package org.openiam.ui.webconsole.web.model;

import org.openiam.idm.srvc.recon.dto.ReconciliationConfig;

import java.util.LinkedList;
import java.util.List;

public class ReconciliationConfigListCommand {
    private String mSysId;
    private String resourceId;
    private String mSysName;

    private List<ReconciliationConfig> configList = new LinkedList<ReconciliationConfig>();

    public ReconciliationConfigListCommand() {
    }

    public ReconciliationConfigListCommand(String mSysId, String resourceId, String mSysName) {
        this.mSysId = mSysId;
        this.resourceId = resourceId;
        this.mSysName = mSysName;
    }

    public ReconciliationConfigListCommand(String mSysId, String resourceId, String mSysName, List<ReconciliationConfig> configList) {
        this.mSysId = mSysId;
        this.resourceId = resourceId;
        this.mSysName = mSysName;
        this.configList = configList;
    }

    public List<ReconciliationConfig> getConfigList() {
        return configList;
    }

    public void setConfigList(List<ReconciliationConfig> configList) {
        this.configList = configList;
    }

    public String getmSysId() {
        return mSysId;
    }

    public void setmSysId(String mSysId) {
        this.mSysId = mSysId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getmSysName() {
        return mSysName;
    }

    public void setmSysName(String mSysName) {
        this.mSysName = mSysName;
    }
}
