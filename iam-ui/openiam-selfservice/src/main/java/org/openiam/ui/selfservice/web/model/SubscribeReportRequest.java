package org.openiam.ui.selfservice.web.model;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonProperty;
import org.openiam.idm.srvc.policy.dto.PolicyAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class SubscribeReportRequest {
	
	@JsonProperty("reportId")
	private String reportId;
	
	@JsonProperty("reportInfoId")
	private String reportInfoId;
	
	@JsonProperty("reportName")
	private String reportName;
	
	
	@JsonProperty("deliveryMethod")
	private String deliveryMethod;
	
	@JsonProperty("deliveryFormat")
	private String deliveryFormat;
	
	@JsonProperty("deliveryAudience")
	private String deliveryAudience;
	
	@JsonProperty("status")
	private String status;

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getDeliveryMethod() {
		return deliveryMethod;
	}

	public void setDeliveryMethod(String deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}

	public String getDeliveryFormat() {
		return deliveryFormat;
	}

	public void setDeliveryFormat(String deliveryFormat) {
		this.deliveryFormat = deliveryFormat;
	}

	public String getDeliveryAudience() {
		return deliveryAudience;
	}

	public void setDeliveryAudience(String deliveryAudience) {
		this.deliveryAudience = deliveryAudience;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReportInfoId() {
		return reportInfoId;
	}

	public void setReportInfoId(String reportInfoId) {
		this.reportInfoId = reportInfoId;
	}
	
	



}
