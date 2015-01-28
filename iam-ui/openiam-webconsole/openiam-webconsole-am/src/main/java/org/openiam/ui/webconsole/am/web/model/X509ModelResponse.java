package org.openiam.ui.webconsole.am.web.model;

import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;

public class X509ModelResponse {
	
	private String startDate;
	private String expirationDate;
	private int version;
	private String certificateType;
	private String principalName;
	private String subjectDN;
	private String issuerDN;
	private String signatureAlgorithmName;
	private String signatureAlgorithmOID;

	public X509ModelResponse(final X509Certificate cert) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		startDate = (cert.getNotBefore() != null) ? dateFormat.format(cert.getNotBefore()) : null;
		expirationDate = (cert.getNotAfter() != null) ? dateFormat.format(cert.getNotAfter()) : null;
		version = cert.getVersion();
		certificateType = cert.getType();
		principalName = (cert.getSubjectX500Principal() != null) ? cert.getSubjectX500Principal().getName() : null;
		subjectDN = (cert.getSubjectDN() != null) ? cert.getSubjectDN().getName() : null;
		signatureAlgorithmName = cert.getSigAlgName();
		signatureAlgorithmOID = cert.getSigAlgOID();
		issuerDN = (cert.getIssuerDN() != null) ? cert.getIssuerDN().getName() : null;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	public String getSubjectDN() {
		return subjectDN;
	}

	public void setSubjectDN(String subjectDN) {
		this.subjectDN = subjectDN;
	}

	public String getIssuerDN() {
		return issuerDN;
	}

	public void setIssuerDN(String issuerDN) {
		this.issuerDN = issuerDN;
	}

	public String getSignatureAlgorithmName() {
		return signatureAlgorithmName;
	}

	public void setSignatureAlgorithmName(String signatureAlgorithmName) {
		this.signatureAlgorithmName = signatureAlgorithmName;
	}

	public String getSignatureAlgorithmOID() {
		return signatureAlgorithmOID;
	}

	public void setSignatureAlgorithmOID(String signatureAlgorithmOID) {
		this.signatureAlgorithmOID = signatureAlgorithmOID;
	}
}
