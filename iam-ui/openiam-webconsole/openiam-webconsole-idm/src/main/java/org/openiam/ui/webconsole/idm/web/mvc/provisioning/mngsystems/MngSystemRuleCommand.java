package org.openiam.ui.webconsole.idm.web.mvc.provisioning.mngsystems;

import java.util.List;

import org.openiam.idm.srvc.mngsys.dto.ManagedSysRuleDto;

public class MngSystemRuleCommand {
    private String mSysId;
    private String resourceId;
    private String mSysName;

    private List<ManagedSysRuleDto> mSysRules;

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

    public List<ManagedSysRuleDto> getmSysRules() {
        return mSysRules;
    }

    public void setmSysRules(List<ManagedSysRuleDto> mSysRules) {
        this.mSysRules = mSysRules;
    }

    public String getmSysName() {
        return mSysName;
    }

    public void setmSysName(String mSysName) {
        this.mSysName = mSysName;
    }
}
