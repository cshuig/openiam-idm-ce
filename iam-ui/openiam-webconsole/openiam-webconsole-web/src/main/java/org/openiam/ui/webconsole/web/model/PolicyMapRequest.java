package org.openiam.ui.webconsole.web.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.openiam.idm.srvc.mngsys.dto.AttributeMap;
import org.openiam.idm.srvc.res.dto.Resource;

public class PolicyMapRequest {

    @JsonProperty("attrMapList")
    private List<AttributeMap> attrMapList;
    @JsonProperty("resourseName")
    private String resourseName;
    @JsonProperty("resourceId")
    private String resourceId;
    @JsonProperty("mangSysId")
    private String mangSysId;
    @JsonProperty("synchConfigId")
    private String synchConfigId;
    @JsonProperty("synchConfigName")
    private String synchConfigName;


    public List<AttributeMap> getAttrMapList() {
        return attrMapList;
    }

    public void setAttrMapList(List<AttributeMap> attrMapList) {
        this.attrMapList = attrMapList;
    }

    public String getResourseName() {
        return resourseName;
    }

    public void setResourseName(String resourseName) {
        this.resourseName = resourseName;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getMangSysId() {
        return mangSysId;
    }

    public void setMangSysId(String mangSysId) {
        this.mangSysId = mangSysId;
    }

    public String getSynchConfigId() {
        return synchConfigId;
    }

    public void setSynchConfigId(String synchConfigId) {
        this.synchConfigId = synchConfigId;
    }

    public String getSynchConfigName() {
        return synchConfigName;
    }

    public void setSynchConfigName(String synchConfigName) {
        this.synchConfigName = synchConfigName;
    }
}
