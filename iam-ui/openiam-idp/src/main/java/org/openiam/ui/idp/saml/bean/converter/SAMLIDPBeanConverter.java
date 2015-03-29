package org.openiam.ui.idp.saml.bean.converter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.openiam.am.srvc.constants.AuthAttributeDataType;
import org.openiam.am.srvc.dto.AuthProvider;
import org.openiam.am.srvc.dto.AuthProviderAttribute;
import org.openiam.am.srvc.dto.AuthResourceAttributeMap;
import org.openiam.ui.bean.converter.BeanConverter;
import org.openiam.ui.idp.saml.model.SAMLUploadToken;
import org.openiam.ui.idp.saml.provider.SAMLIdentityProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SAMLIDPBeanConverter extends AbstractSAMLConverter<SAMLIdentityProvider, AuthProvider> {

	@Value("${org.openiam.idp.provider.saml.attribute.requestIssuer.id}")
	private String requestIssuerAttributeId;
	
	@Value("${org.openiam.idp.provider.saml.attribute.responseIssuer.id}")
	private String responseIssuerAttributeId;
	
	@Value("${org.openiam.idp.provider.saml.attribute.acs.id}")
	private String acsAttributeId;
	
	@Value("${org.openiam.idp.provider.saml.attribute.audiences.id}")
	private String audienceAttributeId;
	
	@Value("${org.openiam.idp.provider.saml.attribute.authncontextclassref.id}")
	private String authContextClassRefAtttributeId;
	
	@Value("${org.openiam.idp.provider.saml.attribute.nameIdFormat.id}")
	private String nameIdFormateAttributeId;
	
	@Value("${org.openiam.idp.provider.saml.attribute.signLoginId.id}")
	private String signLoginIdAttributeId;
	
	@Value("${org.openiam.idp.provider.saml.attribute.spNameQualifier.id}")
	private String spNameQualifierAttributeId;
	
	@Value("${org.openiam.idp.provider.saml.attribute.metadataExposed.id}")
	private String metadataExposedId;
	
	@Value("${org.openiam.idp.provider.saml.attribute.nameQualifier.id}")
	private String nameQualifierAttributeId;
	
	@Override
	public AuthProvider convert(final SAMLUploadToken token) throws Exception {
		final AuthProvider provider = new AuthProvider();
		
		return provider;
	}
	
	@Override
	public SAMLIdentityProvider convert(final AuthProvider bean) {
		SAMLIdentityProvider samlprovider = null;
		if(bean != null) {
			samlprovider = new SAMLIdentityProvider();
			samlprovider.setDescription(bean.getDescription());
			samlprovider.setId(bean.getProviderId());
			samlprovider.setManagedSysId(bean.getManagedSysId());
			samlprovider.setName(bean.getName());
			samlprovider.setPrivateKey(bean.getPrivateKey());
			samlprovider.setPublicKey(bean.getPublicKey());
			samlprovider.setResource(bean.getResource());
			samlprovider.setSign(bean.isSignRequest());
			if(MapUtils.isNotEmpty(bean.getResourceAttributeMap())) {
				samlprovider.setHasAMAttributes(true);
				samlprovider.setAmAttributes(new ArrayList<AuthResourceAttributeMap>(bean.getResourceAttributeMap().values()));
			}
			if(CollectionUtils.isNotEmpty(bean.getProviderAttributeSet())) {
				for(final AuthProviderAttribute attribute : bean.getProviderAttributeSet()) {
					final List<String> value = getValue(attribute);
					if(CollectionUtils.isNotEmpty(value)) {
						if(StringUtils.equals(requestIssuerAttributeId, attribute.getAttributeId())) {
							samlprovider.setRequestIssuer(value.get(0));
						} else if(StringUtils.equals(responseIssuerAttributeId, attribute.getAttributeId())) {
							samlprovider.setResponseIssuer(value.get(0));
						} else if(StringUtils.equals(acsAttributeId, attribute.getAttributeId())) {
							samlprovider.setAssertionConsumerURL(value.get(0));
						} else if(StringUtils.equals(audienceAttributeId, attribute.getAttributeId())) {
							samlprovider.setAudiences(new HashSet<String>(value));
						} else if(StringUtils.equals(authContextClassRefAtttributeId, attribute.getAttributeId())) {
							samlprovider.setAuthContextClassRef(value.get(0));
						} else if(StringUtils.equals(nameIdFormateAttributeId, attribute.getAttributeId())) {
							samlprovider.setNameIdFormat(value.get(0));
						} else if(StringUtils.equals(signLoginIdAttributeId, attribute.getAttributeId())) {
							samlprovider.setSignLoginId(StringUtils.equalsIgnoreCase(Boolean.TRUE.toString(), value.get(0)));
						} else if(StringUtils.equals(spNameQualifierAttributeId, attribute.getAttributeId())) {
							samlprovider.setSpNameQualifier(value.get(0));
						} else if(StringUtils.equals(metadataExposedId, attribute.getAttributeId())) {
							samlprovider.setMetadataExposed(StringUtils.equalsIgnoreCase(Boolean.TRUE.toString(), value.get(0)));
						} else if(StringUtils.equals(nameQualifierAttributeId, attribute.getAttributeId())) {
							samlprovider.setNameQualifier(value.get(0));
						}
					}
				}
			}
		}
		return samlprovider;
	}
}
