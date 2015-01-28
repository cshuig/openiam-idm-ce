package org.openiam.ui.selfservice.web.model;

import org.openiam.idm.srvc.continfo.dto.EmailAddress;
import org.openiam.ui.selfservice.web.groovy.AuthenticatedContactInfoPreprocessor;

public class EmailBean extends EmailAddress {
	
	private boolean editable;
	
	public EmailBean() {}
	
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
