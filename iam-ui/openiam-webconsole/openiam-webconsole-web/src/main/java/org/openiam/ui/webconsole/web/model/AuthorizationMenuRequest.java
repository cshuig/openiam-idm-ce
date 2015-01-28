package org.openiam.ui.webconsole.web.model;

import org.codehaus.jackson.annotate.JsonProperty;
import org.openiam.authmanager.common.model.AuthorizationMenu;

public class AuthorizationMenuRequest {

	@JsonProperty("tree")
	private AuthorizationMenu tree;
	
	@JsonProperty("rootId")
	private String rootId;

	public AuthorizationMenu getTree() {
		return tree;
	}

	public void setTree(AuthorizationMenu tree) {
		this.tree = tree;
	}

	public String getRootId() {
		return rootId;
	}

	public void setRootId(String rootId) {
		this.rootId = rootId;
	}
	
	
}
