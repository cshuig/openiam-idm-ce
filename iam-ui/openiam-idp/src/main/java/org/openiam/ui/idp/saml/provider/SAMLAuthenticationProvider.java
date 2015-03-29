package org.openiam.ui.idp.saml.provider;

public abstract class SAMLAuthenticationProvider extends AuthenticationProvider {

	private byte[] publicKey;
	

	public byte[] getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(byte[] publicKey) {
		this.publicKey = publicKey;
	}
}
