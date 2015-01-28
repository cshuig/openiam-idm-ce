package org.openiam.ui.webconsole.idm.web.mvc.provisioning.approverassoc;

import org.openiam.idm.srvc.mngsys.domain.AssociationType;

public class AssociationModel {

	private String displayName;
	private String id;
	private AssociationType type;
	
	public AssociationModel() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AssociationType getType() {
		return type;
	}

	public void setType(AssociationType type) {
		this.type = type;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	
}
