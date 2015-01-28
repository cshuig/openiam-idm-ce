package org.openiam.ui.idp.common.service;

import java.util.List;

import org.openiam.ui.idp.saml.provider.SAMLIdentityProvider;
import org.openiam.ui.idp.saml.provider.SAMLServiceProvider;

public interface AuthenticationSweeper {

	public List<SAMLIdentityProvider> getSAMLIdpProviders();
	public List<SAMLServiceProvider> getSAMLServiceProviders();
}
