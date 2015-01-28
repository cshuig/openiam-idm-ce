package org.openiam.ui.idp.saml.provider;

import java.io.Serializable;

public class SAMLServiceProvider extends AuthenticationProvider implements Serializable {

	private String loginURL;
	private String logoutURL;
	private byte[] publicKey;
	private String issuer;
	
	public String getLoginURL() {
		return loginURL;
	}
	
	public void setLoginURL(String loginURL) {
		this.loginURL = loginURL;
	}
	
	public String getLogoutURL() {
		return logoutURL;
	}
	
	public void setLogoutURL(String logoutURL) {
		this.logoutURL = logoutURL;
	}
	
	public byte[] getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(byte[] publicKey) {
		this.publicKey = publicKey;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	
}
