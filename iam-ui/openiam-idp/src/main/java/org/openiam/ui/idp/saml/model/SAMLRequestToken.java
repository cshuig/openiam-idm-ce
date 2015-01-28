package org.openiam.ui.idp.saml.model;

import org.openiam.ui.idp.saml.provider.SAMLServiceProvider;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.metadata.Endpoint;

public class SAMLRequestToken extends AbstractSAMLToken {
	private SAMLServiceProvider serviceProvider;
	private AuthnRequest authnRequest;
	private Endpoint endpoint;
	
	public SAMLRequestToken() {
		
	}
	
	public AuthnRequest getAuthnRequest() {
		return authnRequest;
	}
	
	public void setAuthnRequest(final AuthnRequest authnRequest) {
		this.authnRequest = authnRequest;
	}

	public SAMLServiceProvider getServiceProvider() {
		return serviceProvider;
	}

	public void setServiceProvider(SAMLServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	public Endpoint getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(Endpoint endpoint) {
		this.endpoint = endpoint;
	}
	
	
}
