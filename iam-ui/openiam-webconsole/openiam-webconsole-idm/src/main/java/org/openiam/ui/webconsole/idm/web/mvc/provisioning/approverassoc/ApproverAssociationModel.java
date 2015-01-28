package org.openiam.ui.webconsole.idm.web.mvc.provisioning.approverassoc;

import org.openiam.idm.srvc.mngsys.domain.AssociationType;
import org.openiam.idm.srvc.mngsys.dto.ApproverAssociation;

public class ApproverAssociationModel extends ApproverAssociation {

	private String associationTypeDispayName;
	private String approverDisplayName;
	private String onApproveDisplayName;
	private String onRejectDisplayName;
	
	public String getAssociationTypeDispayName() {
		return associationTypeDispayName;
	}
	public void setAssociationTypeDispayName(String associationTypeDispayName) {
		this.associationTypeDispayName = associationTypeDispayName;
	}
	public String getApproverDisplayName() {
		return approverDisplayName;
	}
	public void setApproverDisplayName(String approverDisplayName) {
		this.approverDisplayName = approverDisplayName;
	}
	public String getOnRejectDisplayName() {
		return onRejectDisplayName;
	}
	public void setOnRejectDisplayName(String onRejectDisplayName) {
		this.onRejectDisplayName = onRejectDisplayName;
	}
	public String getOnApproveDisplayName() {
		return onApproveDisplayName;
	}
	public void setOnApproveDisplayName(String onApproveDisplayName) {
		this.onApproveDisplayName = onApproveDisplayName;
	}
	
	
}
