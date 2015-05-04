package org.openiam.ui.idp.saml.provider;

import java.io.Serializable;

import org.openiam.ui.idp.saml.groovy.AbstractJustInTimeSAMLAuthenticator;

public class SAMLServiceProvider extends SAMLAuthenticationProvider {

	private String loginURL;
	private String logoutURL;
	private String issuer;
	private String justInTimeSAMLAuthenticatorScript;
	private String spNameQualifier;
	private String authnContextClassRef;
	private String samlIssuerFormat;
	private String nameIdFormatInSAMLRequest;
	private boolean includeDestinationInAuthnRequest;
	private boolean allowCreateOnNameIdPolicy;
	
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

	public String getSpNameQualifier() {
		return spNameQualifier;
	}

	public void setSpNameQualifier(String spNameQualifier) {
		this.spNameQualifier = spNameQualifier;
	}

	public boolean isIncludeDestinationInAuthnRequest() {
		return includeDestinationInAuthnRequest;
	}

	public void setIncludeDestinationInAuthnRequest(
			boolean includeDestinationInAuthnRequest) {
		this.includeDestinationInAuthnRequest = includeDestinationInAuthnRequest;
	}

	public boolean isAllowCreateOnNameIdPolicy() {
		return allowCreateOnNameIdPolicy;
	}

	public void setAllowCreateOnNameIdPolicy(boolean allowCreateOnNameIdPolicy) {
		this.allowCreateOnNameIdPolicy = allowCreateOnNameIdPolicy;
	}

	public String getAuthnContextClassRef() {
		return authnContextClassRef;
	}

	public void setAuthnContextClassRef(String authnContextClassRef) {
		this.authnContextClassRef = authnContextClassRef;
	}

	public String getSamlIssuerFormat() {
		return samlIssuerFormat;
	}

	public void setSamlIssuerFormat(String samlIssuerFormat) {
		this.samlIssuerFormat = samlIssuerFormat;
	}

	public String getNameIdFormatInSAMLRequest() {
		return nameIdFormatInSAMLRequest;
	}

	public void setNameIdFormatInSAMLRequest(String nameIdFormatInSAMLRequest) {
		this.nameIdFormatInSAMLRequest = nameIdFormatInSAMLRequest;
	}

	
	
}
