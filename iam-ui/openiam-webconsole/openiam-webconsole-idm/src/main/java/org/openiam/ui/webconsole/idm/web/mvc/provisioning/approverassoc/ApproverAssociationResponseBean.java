package org.openiam.ui.webconsole.idm.web.mvc.provisioning.approverassoc;

import java.util.List;

import org.openiam.idm.srvc.mngsys.domain.AssociationType;

public class ApproverAssociationResponseBean {

	private AssociationType type;
	private String entityId;
	private List<ApproverAssociationModel> beans;
	
	public ApproverAssociationResponseBean() {}
	
	public List<ApproverAssociationModel> getBeans() {
		return beans;
	}
	public void setBeans(List<ApproverAssociationModel> beans) {
		this.beans = beans;
	}

	public AssociationType getType() {
		return type;
	}

	public void setType(AssociationType type) {
		this.type = type;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	
	
}
