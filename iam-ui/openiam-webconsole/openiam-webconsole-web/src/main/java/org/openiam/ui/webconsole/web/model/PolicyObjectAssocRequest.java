package org.openiam.ui.webconsole.web.model;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonProperty;
import org.openiam.idm.srvc.policy.dto.PolicyAttribute;
import org.springframework.web.bind.annotation.RequestParam;

public class PolicyObjectAssocRequest {
	
	@JsonProperty("policyObjectId")
	 private String policyObjectId;
	
	@JsonProperty("policyId")
	    private String policyId;
	
	@JsonProperty("associationLevel")
	    private String associationLevel;
	
	@JsonProperty("associationValue")
	    private String associationValue;
	
	@JsonProperty("objectType")
	    private String objectType;
	
	@JsonProperty("objectId")
	    private String objectId;
	
	@JsonProperty("parentAssocId")
	    private String parentAssocId;
	

	public String getPolicyObjectId() {
		return policyObjectId;
	}

	public void setPolicyObjectId(String policyObjectId) {
		this.policyObjectId = policyObjectId;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getAssociationLevel() {
		return associationLevel;
	}

	public void setAssociationLevel(String associationLevel) {
		this.associationLevel = associationLevel;
	}

	public String getAssociationValue() {
		return associationValue;
	}

	public void setAssociationValue(String associationValue) {
		this.associationValue = associationValue;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getParentAssocId() {
		return parentAssocId;
	}

	public void setParentAssocId(String parentAssocId) {
		this.parentAssocId = parentAssocId;
	}

	
	
}
