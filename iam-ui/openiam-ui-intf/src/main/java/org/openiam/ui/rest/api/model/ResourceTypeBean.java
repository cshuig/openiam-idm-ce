package org.openiam.ui.rest.api.model;

import java.util.Map;

import org.openiam.idm.srvc.lang.dto.LanguageMapping;
import org.openiam.ui.web.model.AbstractBean;

public class ResourceTypeBean extends KeyNameBean {

    private Integer provisionResource;
    private String processName;
    private boolean supportsHierarchy;
    private boolean searchable;
    private String url;
    private String imageType;
    private String description;
    private Map<String, LanguageMapping> displayNameMap;

    public String getImageType() {
    	return imageType;
    }

    public void setImageType(String imageType) {
    	this.imageType = imageType;
    }

    public String getDescription() {
    	return description;
    }

    public void setDescription(String description) {
    	this.description = description;
    }

    public Integer getProvisionResource() {
    	return provisionResource;
    }

    public void setProvisionResource(Integer provisionResource) {
    	this.provisionResource = provisionResource;
    }

    public String getProcessName() {
    	return processName;
    }

    public void setProcessName(String processName) {
    	this.processName = processName;
    }

    public boolean getSupportsHierarchy() {
    	return supportsHierarchy;
    }

    public void setSupportsHierarchy(boolean supportsHierarchy) {
    	this.supportsHierarchy = supportsHierarchy;
    }

    public boolean getSearchable() {
    	return searchable;
    }

    public void setSearchable(boolean searchable) {
    	this.searchable = searchable;
    }

    public String getUrl() {
    	return url;
    }

    public void setUrl(String url) {
    	this.url = url;
    }

	public Map<String, LanguageMapping> getDisplayNameMap() {
		return displayNameMap;
	}

	public void setDisplayNameMap(Map<String, LanguageMapping> displayNameMap) {
		this.displayNameMap = displayNameMap;
	}

    
}
