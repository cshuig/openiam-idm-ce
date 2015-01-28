package org.openiam.ui.webconsole.idm.web.mvc.provisioning.common;

public class IdNameBean {

	private String id;
	private String name;
	
	public IdNameBean() {};
	
	public IdNameBean(final String id, final String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}

