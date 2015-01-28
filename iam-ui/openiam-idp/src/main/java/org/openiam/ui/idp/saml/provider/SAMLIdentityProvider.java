package org.openiam.ui.idp.saml.provider;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.openiam.am.srvc.dto.AuthProviderAttribute;
import org.openiam.am.srvc.dto.AuthResourceAttributeMap;

public class SAMLIdentityProvider extends AuthenticationProvider implements Serializable {

	private String assertionConsumerURL;
	private String requestIssuer;
	private String responseIssuer;
	private byte[] publicKey;
	private byte[] privateKey;
	private Set<String> audiences;
	private String authContextClassRef;
	private boolean signLoginId = false;
	private boolean metadataExposed = false;
	private String nameIdFormat;
	private String spNameQualifier;
	private String nameQualifier;

	public String getAssertionConsumerURL() {
		return assertionConsumerURL;
	}

	public void setAssertionConsumerURL(String assertionConsumerURL) {
		this.assertionConsumerURL = assertionConsumerURL;
	}

	public byte[] getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(byte[] publicKey) {
		this.publicKey = publicKey;
	}

	public byte[] getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(byte[] privateKey) {
		this.privateKey = privateKey;
	}

	public String getRequestIssuer() {
		return requestIssuer;
	}

	public void setRequestIssuer(String requestIssuer) {
		this.requestIssuer = requestIssuer;
	}

	public Set<String> getAudiences() {
		return audiences;
	}

	public void setAudiences(Set<String> audienceList) {
		this.audiences = audienceList;
	}
	
	public void addAudience(final String audience) {
		if(this.audiences == null) {
			this.audiences = new LinkedHashSet<String>();
		}
		this.audiences.add(audience);
	}

	public String getResponseIssuer() {
		return responseIssuer;
	}

	public void setResponseIssuer(String responseIssuer) {
		this.responseIssuer = responseIssuer;
	}

	public String getAuthContextClassRef() {
		return authContextClassRef;
	}

	public void setAuthContextClassRef(String authContextClassRef) {
		this.authContextClassRef = authContextClassRef;
	}

	public boolean isSignLoginId() {
		return signLoginId;
	}

	public void setSignLoginId(boolean signLoginId) {
		this.signLoginId = signLoginId;
	}

	public String getNameIdFormat() {
		return nameIdFormat;
	}

	public void setNameIdFormat(String nameIdFormat) {
		this.nameIdFormat = nameIdFormat;
	}

	public String getSpNameQualifier() {
		return spNameQualifier;
	}

	public void setSpNameQualifier(String spNameQualifier) {
		this.spNameQualifier = spNameQualifier;
	}

	public boolean isMetadataExposed() {
		return metadataExposed;
	}

	public void setMetadataExposed(boolean metadataExposed) {
		this.metadataExposed = metadataExposed;
	}

	public String getNameQualifier() {
		return nameQualifier;
	}

	public void setNameQualifier(String nameQualifier) {
		this.nameQualifier = nameQualifier;
	}
}
