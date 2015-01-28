package org.openiam.ui.webconsole.web.model;

import org.openiam.provision.type.ExtensibleAttribute;

import java.util.HashMap;
import java.util.Map;

public class MngSysAttrBean {
    private String userId;
    private String principalId;
    private boolean exist;
    private Map<String, ExtensibleAttribute> idmAttrsMap = new HashMap<String, ExtensibleAttribute>();
    private Map<String, ExtensibleAttribute> mngSysAttrsMap = new HashMap<String, ExtensibleAttribute>();

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public Map<String, ExtensibleAttribute> getIdmAttrsMap() {
        return idmAttrsMap;
    }

    public void setIdmAttrsMap(Map<String, ExtensibleAttribute> idmAttrsMap) {
        this.idmAttrsMap = idmAttrsMap;
    }

    public Map<String, ExtensibleAttribute> getMngSysAttrsMap() {
        return mngSysAttrsMap;
    }

    public void setMngSysAttrsMap(Map<String, ExtensibleAttribute> mngSysAttrsMap) {
        this.mngSysAttrsMap = mngSysAttrsMap;
    }
}
