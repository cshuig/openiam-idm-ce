package org.openiam.ui.idp.saml.model;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class SAMLUploadToken {

	private CommonsMultipartFile file;
	public CommonsMultipartFile getFile() {
		return file;
	}
	public void setFile(CommonsMultipartFile file) {
		this.file = file;
	}
	
}
