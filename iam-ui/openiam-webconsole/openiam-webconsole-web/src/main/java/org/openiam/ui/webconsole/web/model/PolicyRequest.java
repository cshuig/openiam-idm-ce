package org.openiam.ui.webconsole.web.model;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonProperty;
import org.openiam.idm.srvc.policy.dto.PolicyAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class PolicyRequest {
	
	@JsonProperty("policyId")
	private String policyId;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("status")
	private int status;
	
	@JsonProperty("policyDefId")
	private String policyDefId;
	
	@JsonProperty("rule")
	 private String rule;
	
	@JsonProperty("ruleSrcUrl")
	    private  CommonsMultipartFile ruleSrcUrl;
	
	@JsonProperty("policyAttributes")
	private Set<PolicyAttribute> policyAttributes = new HashSet<PolicyAttribute>(
            0);
	


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

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPolicyDefId() {
		return policyDefId;
	}

	public void setPolicyDefId(String policyDefId) {
		this.policyDefId = policyDefId;
	}

	public Set<PolicyAttribute> getPolicyAttributes() {
		return policyAttributes;
	}

	public void setPolicyAttributes(Set<PolicyAttribute> policyAttributes) {
		this.policyAttributes = policyAttributes;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public  CommonsMultipartFile getRuleSrcUrl() {
		return ruleSrcUrl;
	}

	public void setRuleSrcUrl( CommonsMultipartFile ruleSrcUrl) {
		this.ruleSrcUrl = ruleSrcUrl;
	}
	
	
	
	
	
	
}
