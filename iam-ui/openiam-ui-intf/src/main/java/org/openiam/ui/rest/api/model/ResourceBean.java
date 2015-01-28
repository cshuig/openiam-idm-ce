package org.openiam.ui.rest.api.model;

import org.openiam.idm.srvc.res.dto.ResourceRisk;

public class ResourceBean extends KeyNameDescriptionBean {

	private String resourceType;
	private String resourceTypeId;
    private ResourceRisk risk;
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getResourceTypeId() {
		return resourceTypeId;
	}
	public void setResourceTypeId(String resourceTypeId) {
		this.resourceTypeId = resourceTypeId;
	}

    public ResourceRisk getRisk() {
        return risk;
    }

    public void setRisk(ResourceRisk risk) {
        this.risk = risk;
    }
}
