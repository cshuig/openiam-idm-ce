package org.openiam.ui.idp.saml.decoder;

import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.saml2.binding.decoding.HTTPPostDecoder;
import org.opensaml.ws.message.decoder.MessageDecodingException;

public class OpenIAMHTTPPostDecoder extends HTTPPostDecoder {

	private boolean skipcheckEndpointURI;
	
	private OpenIAMHTTPPostDecoder() {
		
	}
	
	public OpenIAMHTTPPostDecoder(final boolean skipcheckEndpointURI) {
		this.skipcheckEndpointURI = skipcheckEndpointURI;
	}
	
	@Override
	protected void checkEndpointURI(SAMLMessageContext messageContext) throws org.opensaml.xml.security.SecurityException, MessageDecodingException {
		if(!skipcheckEndpointURI) {
			super.checkEndpointURI(messageContext);
		}
	}
}
