package org.openiam.ui.idp.saml.bean.converter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.openiam.am.srvc.dto.AuthProvider;
import org.openiam.am.srvc.dto.AuthProviderAttribute;
import org.openiam.am.srvc.dto.AuthResourceAttributeMap;
import org.openiam.ui.bean.converter.BeanConverter;
import org.openiam.ui.idp.saml.provider.SAMLIdentityProvider;
import org.openiam.ui.idp.saml.provider.SAMLServiceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SAMLServiceProviderConverter extends AbstractSAMLConverter<SAMLServiceProvider, AuthProvider> {

	@Value("${org.openiam.saml.sp.attribute.login.url.id}")
	private String samlSPLoginURLAttributeId;
	
	@Value("${org.openiam.saml.sp.attribute.logout.url.id}")
	private String samlSPLogoutURLAttributeId;
	
	@Value("${org.openiam.saml.sp.attribute.issuer.name.id}")
	private String samlSPIssuerId;
	
	@Override
	public SAMLServiceProvider convert(AuthProvider bean) {
		SAMLServiceProvider provider = null;
		if(bean != null) {
			provider = new SAMLServiceProvider();
			provider.setDescription(bean.getDescription());
			provider.setId(bean.getProviderId());
			provider.setManagedSysId(bean.getManagedSysId());
			provider.setName(bean.getName());
			provider.setPublicKey(bean.getPublicKey());
			provider.setResource(bean.getResource());
			if(MapUtils.isNotEmpty(bean.getResourceAttributeMap())) {
				provider.setHasAMAttributes(true);
				provider.setAmAttributes(new ArrayList<AuthResourceAttributeMap>(bean.getResourceAttributeMap().values()));
			}
			if(CollectionUtils.isNotEmpty(bean.getProviderAttributeSet())) {
				for(final AuthProviderAttribute attribute : bean.getProviderAttributeSet()) {
					final List<String> value = getValue(attribute);
					if(CollectionUtils.isNotEmpty(value)) {
						if(StringUtils.equals(samlSPLoginURLAttributeId, attribute.getAttributeId())) {
							provider.setLoginURL(value.get(0));
						} else if(StringUtils.equals(samlSPLogoutURLAttributeId, attribute.getAttributeId())) {
							provider.setLogoutURL(value.get(0));
						} else if(StringUtils.equals(samlSPIssuerId, attribute.getAttributeId())) {
							provider.setIssuer(value.get(0));
						}
					}
				}
			}
		}
		return provider;
	}

}
