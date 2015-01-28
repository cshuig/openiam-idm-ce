package org.openiam.ui.rest.api.model;

import java.util.List;

/**
 * Created by: Alexander Duckardt
 * Date: 1/16/14.
 */
public class ResourceSearchMetadata {
    private List<KeyNameBean> resourceTypeList;
    private List<KeyNameBean> resourceRiskList;

    public List<KeyNameBean> getResourceTypeList() {
        return resourceTypeList;
    }

    public void setResourceTypeList(List<KeyNameBean> resourceTypeList) {
        this.resourceTypeList = resourceTypeList;
    }

    public List<KeyNameBean> getResourceRiskList() {
        return resourceRiskList;
    }

    public void setResourceRiskList(List<KeyNameBean> resourceRiskList) {
        this.resourceRiskList = resourceRiskList;
    }
}
