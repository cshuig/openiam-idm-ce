package org.openiam.ui.idp.saml.decoder;

import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.saml2.binding.decoding.HTTPRedirectDeflateDecoder;
import org.opensaml.ws.message.decoder.MessageDecodingException;

public class OpenIAMHTTPRedirectDeflateDecoder extends HTTPRedirectDeflateDecoder {

	private boolean skipcheckEndpointURI;

	private OpenIAMHTTPRedirectDeflateDecoder() {
		
	}
	
	public OpenIAMHTTPRedirectDeflateDecoder(final boolean skipcheckEndpointURI) {
		this.skipcheckEndpointURI = skipcheckEndpointURI;
	}
	
	@Override
	protected void checkEndpointURI(SAMLMessageContext messageContext) throws org.opensaml.xml.security.SecurityException, MessageDecodingException {
		if(!skipcheckEndpointURI) {
			super.checkEndpointURI(messageContext);
		}
	}
}
