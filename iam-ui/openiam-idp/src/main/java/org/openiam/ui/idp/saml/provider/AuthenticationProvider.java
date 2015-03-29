package org.openiam.ui.idp.saml.provider;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.openiam.am.srvc.dto.AuthResourceAttributeMap;
import org.openiam.idm.srvc.mngsys.dto.ManagedSysDto;
import org.openiam.idm.srvc.res.dto.Resource;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuthenticationProvider", propOrder = {
	"name",
	"id",
	"managedSysId",
	"description",
	"resource",
	"amAttributes",
	"hasAMAttributes"
})
public abstract class AuthenticationProvider implements Serializable {
	
	private String name;
	private String id;
	private String managedSysId;
	private String description;
	private Resource resource;
	private List<AuthResourceAttributeMap> amAttributes;
	private boolean hasAMAttributes;
	private boolean sign;
	
	public boolean isSign() {
		return sign;
	}

	public void setSign(boolean sign) {
		this.sign = sign;
	}

	public String getId() {
		return id;
	}
	
	public void setId(final String id) {
		this.id = id;
	}

	public String getManagedSysId() {
		return managedSysId;
	}

	public void setManagedSysId(String managedSysId) {
		this.managedSysId = managedSysId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(final Resource resource) {
		this.resource = resource;
	}

	public void setAmAttributes(List<AuthResourceAttributeMap> amAttributes) {
		this.amAttributes = amAttributes;
	}

	public List<AuthResourceAttributeMap> getAmAttributes() {
		return amAttributes;
	}

	public boolean isHasAMAttributes() {
		return hasAMAttributes;
	}

	public void setHasAMAttributes(boolean hasAMAttributes) {
		this.hasAMAttributes = hasAMAttributes;
	}
}
