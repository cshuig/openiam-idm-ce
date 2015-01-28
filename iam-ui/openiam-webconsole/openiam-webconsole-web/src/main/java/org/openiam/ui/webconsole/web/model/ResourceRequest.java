package org.openiam.ui.webconsole.web.model;

import java.io.IOException;

import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.web.bind.annotation.RequestParam;

public class ResourceRequest {
	
	@JsonProperty("resourceTypeId")
	private String resourceTypeId;
	
	@JsonProperty("resourceId")
	private String resourceId;
	
	@JsonProperty("resourceName")
	private String resourceName;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("managedSysId")
	private String managedSysId;
	
	@JsonProperty("resourceURL")
	private String resourceURL;
	
	@JsonProperty("resourceDomain")
	private String resourceDomain;
	
	@JsonProperty("isPublic")
	private Boolean isPublic;
	
	@JsonProperty("isSSL")
	private Boolean isSSL;
	
	public String getResourceId() {
		return resourceId;
	}
	
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
	public String getResourceName() {
		return resourceName;
	}
	
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getManagedSysId() {
		return managedSysId;
	}
	
	public void setManagedSysId(String managedSysId) {
		this.managedSysId = managedSysId;
	}
	
	public String getResourceURL() {
		return resourceURL;
	}
	
	public void setResourceURL(String resourceURL) {
		this.resourceURL = resourceURL;
	}
	
	public String getResourceDomain() {
		return resourceDomain;
	}
	
	public void setResourceDomain(String resourceDomain) {
		this.resourceDomain = resourceDomain;
	}
	
	public Boolean getIsPublic() {
		return isPublic;
	}
	
	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}
	
	public Boolean getIsSSL() {
		return isSSL;
	}
	
	public void setIsSSL(Boolean isSSL) {
		this.isSSL = isSSL;
	}

	public String getResourceTypeId() {
		return resourceTypeId;
	}

	public void setResourceTypeId(String resourceTypeId) {
		this.resourceTypeId = resourceTypeId;
	}
}
