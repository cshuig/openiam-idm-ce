package org.openiam.ui.webconsole.web.model;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonProperty;
import org.openiam.idm.srvc.policy.dto.PolicyAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class IdentityQuestionRequest {
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("questionText")
	private String questionText;
	
	
	@JsonProperty("active")
	private String active;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getQuestionText() {
		return questionText;
	}


	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}


	public String getActive() {
		return active;
	}


	public void setActive(String active) {
		this.active = active;
	}
	
	
}
