package org.openiam.ui.idp.common.service;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.openiam.am.srvc.dto.AuthProvider;
import org.openiam.am.srvc.searchbeans.AuthProviderSearchBean;
import org.openiam.am.srvc.ws.AuthProviderWebService;
import org.openiam.am.srvc.ws.AuthResourceAttributeWebService;
import org.openiam.ui.idp.saml.bean.converter.SAMLIDPBeanConverter;
import org.openiam.ui.idp.saml.bean.converter.SAMLServiceProviderConverter;
import org.openiam.ui.idp.saml.provider.SAMLIdentityProvider;
import org.openiam.ui.idp.saml.provider.SAMLServiceProvider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("samlAuthenticationSweeper")
public class SAMLAuthenticationSweeper implements AuthenticationSweeper {
	
	@Value("${org.openiam.idp.provider.saml.idp.id}")
	private String samlIDPProviderId;
	
	@Value("${org.openiam.idp.provider.saml.sp.id}")
	private String samlSPProviderId;
	
	@Resource(name="authProviderServiceClient")
	private AuthProviderWebService authProviderServiceClient;
	
	@Autowired
	@Resource(name="authAttributeServiceClient")
	private AuthResourceAttributeWebService authAttributeServiceClient;
	
	@Autowired
	private SAMLIDPBeanConverter samlIDPConverter;
	
	@Autowired
	private SAMLServiceProviderConverter samlSPConverter;

	@Override
	public List<SAMLIdentityProvider> getSAMLIdpProviders() {
		final AuthProviderSearchBean searchBean = new AuthProviderSearchBean();
		searchBean.setDeepCopy(true);
		searchBean.setProviderType(samlIDPProviderId);
		final List<AuthProvider> samlAuthProviders = authProviderServiceClient.findAuthProviderBeans(searchBean, Integer.MAX_VALUE, 0);
		final List<SAMLIdentityProvider> samlProviderList = new LinkedList<SAMLIdentityProvider>();
		if(CollectionUtils.isNotEmpty(samlAuthProviders)) {
			for(final AuthProvider authProvider : samlAuthProviders) {
				final SAMLIdentityProvider samlProvider = samlIDPConverter.convert(authProvider);
				if(samlProvider != null) {
					samlProviderList.add(samlProvider);
				}
			}
		}
		return samlProviderList;
	}

	@Override
	public List<SAMLServiceProvider> getSAMLServiceProviders() {
		final AuthProviderSearchBean searchBean = new AuthProviderSearchBean();
		searchBean.setDeepCopy(true);
		searchBean.setProviderType(samlSPProviderId);
		final List<AuthProvider> samlAuthProviders = authProviderServiceClient.findAuthProviderBeans(searchBean, Integer.MAX_VALUE, 0);
		final List<SAMLServiceProvider> samlProviderList = new LinkedList<SAMLServiceProvider>();
		if(CollectionUtils.isNotEmpty(samlAuthProviders)) {
			for(final AuthProvider authProvider : samlAuthProviders) {
				final SAMLServiceProvider samlProvider = samlSPConverter.convert(authProvider);
				if(samlProvider != null) {
					samlProviderList.add(samlProvider);
				}
			}
		}
		return samlProviderList;
	}

	
}
