package org.openiam.ui.webconsole.idm.web.mvc.provisioning.approverassoc;

import java.io.Serializable;

import org.openiam.idm.srvc.mngsys.domain.AssociationType;

public class ApproverAssociationRequest implements Serializable {

	private String id;
	private String requestType;
	private boolean applyDelegationFilter;
	private String associationType;
	private String associationEntityId;
	private Integer approverLevel;
	private String onApproveEntityId;
	private String onRejectEntityId;
	private String onApproveEntityType;
	private String onRejectEntityType;
	private String approverEntityId;
	private String approverEntityType;
	
	public ApproverAssociationRequest() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public boolean isApplyDelegationFilter() {
		return applyDelegationFilter;
	}

	public void setApplyDelegationFilter(boolean applyDelegationFilter) {
		this.applyDelegationFilter = applyDelegationFilter;
	}

	public String getAssociationType() {
		return associationType;
	}

	public void setAssociationType(String associationType) {
		this.associationType = associationType;
	}

	public String getAssociationEntityId() {
		return associationEntityId;
	}

	public void setAssociationEntityId(String associationEntityId) {
		this.associationEntityId = associationEntityId;
	}

	public Integer getApproverLevel() {
		return approverLevel;
	}

	public void setApproverLevel(Integer approverLevel) {
		this.approverLevel = approverLevel;
	}

	public String getOnApproveEntityId() {
		return onApproveEntityId;
	}

	public void setOnApproveEntityId(String onApproveEntityId) {
		this.onApproveEntityId = onApproveEntityId;
	}

	public String getOnRejectEntityId() {
		return onRejectEntityId;
	}

	public void setOnRejectEntityId(String onRejectEntityId) {
		this.onRejectEntityId = onRejectEntityId;
	}

	public String getOnApproveEntityType() {
		return onApproveEntityType;
	}

	public void setOnApproveEntityType(String onApproveEntityType) {
		this.onApproveEntityType = onApproveEntityType;
	}

	public String getOnRejectEntityType() {
		return onRejectEntityType;
	}

	public void setOnRejectEntityType(String onRejectEntityType) {
		this.onRejectEntityType = onRejectEntityType;
	}

	public String getApproverEntityId() {
		return approverEntityId;
	}

	public void setApproverEntityId(String approverEntityId) {
		this.approverEntityId = approverEntityId;
	}

	public String getApproverEntityType() {
		return approverEntityType;
	}

	public void setApproverEntityType(String approverEntityType) {
		this.approverEntityType = approverEntityType;
	}
	
	
}
