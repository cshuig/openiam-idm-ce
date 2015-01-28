package org.openiam.ui.webconsole.am.web.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class AuthProviderFormRequest {

	private String providerId;
    private String providerType;
    private String managedSysId;
    private String resourceId;
    private String name;
    private String description;
    private String applicationURL;
    private boolean isSignRequest=false;
	private CommonsMultipartFile publicKey;
	private CommonsMultipartFile privateKey;
	private boolean clearPrivateKey;
	private boolean clearPublicKey;
	
	/* maps attribute ID to value, so as to set the attribute of the AuthProvider correctly */
	private Map<String, String> attributeMap = new HashMap<String, String>();
	
	public String getProviderId() {
		return providerId;
	}
	
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	
	public String getProviderType() {
		return providerType;
	}
	
	public void setProviderType(String providerType) {
		this.providerType = providerType;
	}
	
	public String getManagedSysId() {
		return managedSysId;
	}
	
	public void setManagedSysId(String managedSysId) {
		this.managedSysId = managedSysId;
	}
	
	public String getResourceId() {
		return resourceId;
	}
	
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean isSignRequest() {
		return isSignRequest;
	}
	
	public void setSignRequest(boolean isSignRequest) {
		this.isSignRequest = isSignRequest;
	}
	
	public CommonsMultipartFile getPublicKey() {
		return publicKey;
	}
	
	public void setPublicKey(CommonsMultipartFile publicKey) {
		this.publicKey = publicKey;
	}
	
	public CommonsMultipartFile getPrivateKey() {
		return privateKey;
	}
	
	public void setPrivateKey(CommonsMultipartFile privateKey) {
		this.privateKey = privateKey;
	}

	public String getApplicationURL() {
		return applicationURL;
	}

	public void setApplicationURL(String applicationURL) {
		this.applicationURL = applicationURL;
	}

	public Map<String, String> getAttributeMap() {
		return attributeMap;
	}

	public void setAttributeMap(Map<String, String> attributeMap) {
		this.attributeMap = attributeMap;
	}

	public boolean isClearPrivateKey() {
		return clearPrivateKey;
	}

	public void setClearPrivateKey(boolean clearPrivateKey) {
		this.clearPrivateKey = clearPrivateKey;
	}

	public boolean isClearPublicKey() {
		return clearPublicKey;
	}

	public void setClearPublicKey(boolean clearPublicKey) {
		this.clearPublicKey = clearPublicKey;
	}
}
