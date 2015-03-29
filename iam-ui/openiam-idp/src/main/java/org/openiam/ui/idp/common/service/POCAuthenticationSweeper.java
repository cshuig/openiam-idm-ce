package org.openiam.ui.idp.common.service;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.openiam.idm.srvc.mngsys.dto.ManagedSysDto;
import org.openiam.ui.idp.saml.provider.SAMLIdentityProvider;
import org.openiam.ui.idp.saml.provider.SAMLServiceProvider;
import org.openiam.ui.idp.saml.service.SAMLServiceImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * POC class.  Should be removed once we write ESB code to perform CRUD Operations and create DB tables.
 */
@Service("pocAuthenticationSweeper")
public class POCAuthenticationSweeper implements AuthenticationSweeper, InitializingBean {

	private SAMLIdentityProvider googleProvider;
	private SAMLIdentityProvider salesForceProvider;
	//private SAMLProvider boxNetProvider;
	
	@Override
	public List<SAMLIdentityProvider> getSAMLIdpProviders() {
		final List<SAMLIdentityProvider> providerList = new LinkedList<SAMLIdentityProvider>();
		providerList.add(googleProvider);
		providerList.add(salesForceProvider);
		//providerList.add(boxNetProvider);
		return providerList;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
		googleProvider = new SAMLIdentityProvider();
		googleProvider.setPublicKey(IOUtils.toByteArray(getPublicKeyInputStream()));
		googleProvider.setPrivateKey(IOUtils.toByteArray(getPrivateKeyInputStream()));
		googleProvider.setSign(true);
		googleProvider.setAssertionConsumerURL("https://www.google.com/a/openiamdemo.com/acs");
		googleProvider.setRequestIssuer("google.com/a/openiamdemo.com");
		googleProvider.setManagedSysId("103");
		
		salesForceProvider = new SAMLIdentityProvider();
		salesForceProvider.setPublicKey(IOUtils.toByteArray(getPublicKeyInputStream()));
		salesForceProvider.setPrivateKey(IOUtils.toByteArray(getPrivateKeyInputStream()));
		salesForceProvider.setSign(true);
		salesForceProvider.setAssertionConsumerURL("https://login.salesforce.com/?saml=02HKiPoin4SS8VUMTuiWxjbWxIo20RQDhx3Wjixser3dgw6HNb6FttNu1C");
		salesForceProvider.setRequestIssuer("https://openiam-dev-ed.my.salesforce.com");
		salesForceProvider.addAudience("https://saml.salesforce.com");
		salesForceProvider.addAudience("https://openiam-dev-ed.my.salesforce.com");
		salesForceProvider.setResponseIssuer("http://lnx06.openiamdemo.com:8080/idp/SAMLLogin.html");
		salesForceProvider.setManagedSysId("203");
		
		/* these guys are on hold */
		/*
		boxNetProvider = new SAMLProvider();
		boxNetProvider.setPublicKey(IOUtils.toByteArray(getPublicKeyInputStream()));
		boxNetProvider.setPrivateKey(IOUtils.toByteArray(getPrivateKeyInputStream()));
		boxNetProvider.setSignRequest(true);
		boxNetProvider.setAssertionConsumerURL("https://sso.services.box.net/sp/ACS.saml2");
		boxNetProvider.setIssuer("http://www.box.net");
		final ManagedSys boxSys = new ManagedSys();
		boxSys.setManagedSysId("BOX.NET");
		boxNetProvider.setManagedSys(boxSys);
		*/
	}

	private InputStream getPublicKeyInputStream() {
		return POCAuthenticationSweeper.class.getResourceAsStream("/poc/keys/rsacert.pem");
	}
	
	private InputStream getPrivateKeyInputStream() {
		return POCAuthenticationSweeper.class.getResourceAsStream("/poc/keys/rsaprivkey.der");
	}

	@Override
	public List<SAMLServiceProvider> getSAMLServiceProviders() {
		// TODO Auto-generated method stub
		return null;
	}
}
