package org.openiam.ui.idp.saml.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openiam.ui.idp.saml.model.SAMLIDPMetadataResponse;
import org.openiam.ui.idp.saml.model.SAMLLogoutRequestToken;
import org.openiam.ui.idp.saml.model.SAMLLogoutResponseToken;
import org.openiam.ui.idp.saml.model.SAMLRequestToken;
import org.openiam.ui.idp.saml.model.SAMLResponseToken;
import org.openiam.ui.idp.saml.provider.SAMLIdentityProvider;
import org.openiam.ui.idp.saml.provider.SAMLServiceProvider;
import org.opensaml.common.SAMLException;
import org.opensaml.saml2.core.Response;

public interface SAMLService {
	
	/**
	 * Logs the user in, and generates a SAML Response Object in the corresponding Token
	 * @param request - HttpServletRequest
	 * @return The SAMLResponseToken representing what this method did/did not do.
	 */
	public SAMLResponseToken samlLogin(final HttpServletRequest request);
	
	public SAMLIDPMetadataResponse getSAMLIDPMetadata(final HttpServletRequest request);
	
	public SAMLRequestToken getSAMLRequestForSP(final HttpServletRequest request, final String spName);
	
	public SAMLResponseToken processSAMLResponse(final HttpServletRequest request, final HttpServletResponse httpResponse);
	
	public SAMLServiceProvider getSAMLServiceProvierById(final String id);
}
