package org.openiam.ui.rest.api.model;

import org.openiam.idm.srvc.org.dto.Organization;
import org.openiam.idm.srvc.org.dto.OrganizationType;
import org.openiam.ui.rest.api.model.KeyNameBean;
import org.openiam.ui.web.model.AbstractBean;

public class OrganizationBean extends KeyNameBean {
	
	public OrganizationBean() {}

	private String type;
	private boolean selectable = true;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isSelectable() {
		return selectable;
	}

	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}
}
