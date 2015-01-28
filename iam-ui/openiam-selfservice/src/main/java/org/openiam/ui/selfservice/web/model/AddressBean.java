package org.openiam.ui.selfservice.web.model;

import org.openiam.idm.srvc.continfo.dto.Address;
import org.openiam.ui.selfservice.web.groovy.AuthenticatedContactInfoPreprocessor;

public class AddressBean extends Address {

	public AddressBean() {}
	
	private boolean editable;
	
	public void preprocess(final AuthenticatedContactInfoPreprocessor preProcessor) {
		if(preProcessor != null) {
			editable = preProcessor.isEditable(this);
		}
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}
}
