package org.openiam.ui.idp.saml.model;

import org.opensaml.saml2.metadata.EntityDescriptor;
import org.w3c.dom.Element;

public class SAMLIDPMetadataResponse extends AbstractSAMLToken {

	private Element entityDescriptorElement;
	private EntityDescriptor entityDescriptor;

	public EntityDescriptor getEntityDescriptor() {
		return entityDescriptor;
	}

	public void setEntityDescriptor(final EntityDescriptor entityDescriptor) {
		this.entityDescriptor = entityDescriptor;
	}

	public Element getEntityDescriptorElement() {
		return entityDescriptorElement;
	}

	public void setEntityDescriptorElement(Element entityDescriptorElement) {
		this.entityDescriptorElement = entityDescriptorElement;
	}
	
	
}
