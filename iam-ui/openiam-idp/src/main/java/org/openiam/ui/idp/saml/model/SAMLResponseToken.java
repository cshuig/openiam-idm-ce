package org.openiam.ui.idp.saml.model;

import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.Response;

public class SAMLResponseToken extends AbstractSAMLToken {

	private Response response = null;
	private String relayState = null;
	public String encodedResponse = null;
	public String assertionConsumerURL;
	
	public String getAssertionConsumerURL() {
		return assertionConsumerURL;
	}

	public void setAssertionConsumerURL(String assertionConsumerURL) {
		this.assertionConsumerURL = assertionConsumerURL;
	}

	public Response getResponse() {
		return response;
	}
	
	public void setResponse(Response response) {
		this.response = response;
	}

	public String getRelayState() {
		return relayState;
	}

	public void setRelayState(String relayState) {
		this.relayState = relayState;
	}

	public String getEncodedResponse() {
		return encodedResponse;
	}

	public void setEncodedResponse(String encodedResponse) {
		this.encodedResponse = encodedResponse;
	}
	
	
}
