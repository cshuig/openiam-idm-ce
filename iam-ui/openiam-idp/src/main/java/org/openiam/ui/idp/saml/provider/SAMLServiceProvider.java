package org.openiam.ui.idp.saml.provider;

import java.io.Serializable;

import org.openiam.ui.idp.saml.groovy.AbstractJustInTimeSAMLAuthenticator;

public class SAMLServiceProvider extends SAMLAuthenticationProvider {

	private String loginURL;
	private String logoutURL;
	private String issuer;
	private String justInTimeSAMLAuthenticatorScript;
	
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

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getJustInTimeSAMLAuthenticatorScript() {
		return justInTimeSAMLAuthenticatorScript;
	}

	public void setJustInTimeSAMLAuthenticatorScript(
			String justInTimeSAMLAuthenticatorScript) {
		this.justInTimeSAMLAuthenticatorScript = justInTimeSAMLAuthenticatorScript;
	}

	
}
