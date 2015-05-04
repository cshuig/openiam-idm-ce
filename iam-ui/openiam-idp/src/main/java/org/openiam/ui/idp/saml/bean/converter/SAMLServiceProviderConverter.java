package org.openiam.ui.idp.saml.bean.converter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openiam.am.srvc.dto.AuthProvider;
import org.openiam.am.srvc.dto.AuthProviderAttribute;
import org.openiam.am.srvc.dto.AuthResourceAttributeMap;
import org.openiam.ui.bean.converter.BeanConverter;
import org.openiam.ui.idp.saml.model.SAMLUploadToken;
import org.openiam.ui.idp.saml.provider.SAMLIdentityProvider;
import org.openiam.ui.idp.saml.provider.SAMLServiceProvider;
import org.opensaml.Configuration;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml2.metadata.SPSSODescriptor;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallerFactory;
import org.opensaml.xml.signature.KeyInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

@Component
public class SAMLServiceProviderConverter extends AbstractSAMLConverter<SAMLServiceProvider, AuthProvider> {
	
	private static Logger log = Logger.getLogger(SAMLServiceProviderConverter.class);

	@Value("${org.openiam.saml.sp.attribute.login.url.id}")
	private String samlSPLoginURLAttributeId;
	
	@Value("${org.openiam.saml.sp.attribute.logout.url.id}")
	private String samlSPLogoutURLAttributeId;
	
	@Value("${org.openiam.saml.sp.attribute.issuer.name.id}")
	private String samlSPIssuerId;
	
	@Value("${org.openiam.saml.sp.attribute.issuer.justintime.groovy.script.id}")
	private String justInTimeGroovyScriptAttributeId;
	
	@Value("${org.openiam.saml.sp.name.qualifier}")
	private String spNameQualifierAttributeId;

	@Value("${org.openiam.idp.provider.saml.sp.id}")
	private String samlSPProviderId;
	
	@Value("${org.openiam.saml.sp.authn.request.destination.attribute.id}")
	private String authnRequestDesinationId;
	
	@Value("${org.openiam.saml.sp.nameid.allow.create}")
	private String spAllowCreateOnNameIdPolicyId;
	
	@Value("${org.openiam.saml.sp.context.class.ref}")
	private String spContextClassRefId;
	
	@Value("${org.openiam.saml.sp.issuer.format}")
	private String samlIssuerFormatId;
	
	@Value("${org.openiam.saml.sp.saml.request.name.id.format}")
	private String nameIdFomatInSAMLRequest;

	@Override
	public AuthProvider convert(final SAMLUploadToken token) throws Exception {
		ByteArrayInputStream is = new ByteArrayInputStream(token.getFile().getBytes());
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setNamespaceAware(true);
		DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();

		Document document = docBuilder.parse(is);
		Element element = document.getDocumentElement();
		
		UnmarshallerFactory unmarshallerFactory = Configuration.getUnmarshallerFactory();
		Unmarshaller unmarshaller = unmarshallerFactory.getUnmarshaller(element);
		XMLObject responseXmlObj = unmarshaller.unmarshall(element);
		
		EntityDescriptor descriptor = (EntityDescriptor) responseXmlObj;
		
		final AuthProvider provider = new AuthProvider();
		provider.setManagedSysId(defaultManagedSysId);
		provider.setProviderType(samlSPProviderId);
		
		provider.setProviderAttributeSet(new HashSet<AuthProviderAttribute>());
		final AuthProviderAttribute issuerAttribute = getAttribute(samlSPIssuerId, descriptor.getEntityID());
		if(issuerAttribute != null) {
			provider.getProviderAttributeSet().add(issuerAttribute);
		}
		
		if(CollectionUtils.isNotEmpty(descriptor.getRoleDescriptors())) {
			final SPSSODescriptor roleDescriptor = (SPSSODescriptor)descriptor.getRoleDescriptors().get(0);
			
			if(CollectionUtils.isNotEmpty(roleDescriptor.getKeyDescriptors())) {
				final KeyInfo keyInfo = roleDescriptor.getKeyDescriptors().get(0).getKeyInfo();
				if(keyInfo != null) {
					try {
						final byte[] publicKey = Base64.decodeBase64(keyInfo.getX509Datas().get(0).getX509Certificates().get(0).getValue());
						provider.setPublicKey(publicKey);
						provider.setSignRequest(true);
					} catch(Throwable e) {
						log.error("Can't get key info", e);
					}
				}
			}
			
			if(CollectionUtils.isNotEmpty(roleDescriptor.getAssertionConsumerServices())) {
				final AuthProviderAttribute loginURLAttribute = getAttribute(samlSPLoginURLAttributeId, roleDescriptor.getAssertionConsumerServices().get(0).getResponseLocation());
				if(loginURLAttribute != null) {
					provider.getProviderAttributeSet().add(loginURLAttribute);
				}
			}
		}
		return provider;
	}
	
	private AuthProviderAttribute getAttribute(final String id, final String value) {
		AuthProviderAttribute attribute = null;
		if(value != null) {
			attribute = new AuthProviderAttribute();
			attribute.setAttributeId(id);
			attribute.setValue(value);
		}
		return attribute;
	}
	
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
			provider.setSign(bean.isSignRequest());
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
						} else if(StringUtils.equals(justInTimeGroovyScriptAttributeId, attribute.getAttributeId())) {
							provider.setJustInTimeSAMLAuthenticatorScript(value.get(0));
						} else if(StringUtils.equals(spNameQualifierAttributeId, attribute.getAttributeId())) {
							provider.setSpNameQualifier(value.get(0));
						} else if(StringUtils.equals(authnRequestDesinationId, attribute.getAttributeId())) {
							provider.setIncludeDestinationInAuthnRequest(StringUtils.equalsIgnoreCase(value.get(0), Boolean.TRUE.toString()));
						} else if(StringUtils.equals(spAllowCreateOnNameIdPolicyId, attribute.getAttributeId())) {
							provider.setAllowCreateOnNameIdPolicy(StringUtils.equalsIgnoreCase(value.get(0), Boolean.TRUE.toString()));
						} else if(StringUtils.equals(spContextClassRefId, attribute.getAttributeId())) {
							provider.setAuthnContextClassRef(value.get(0));
						} else if(StringUtils.equals(samlIssuerFormatId, attribute.getAttributeId())) {
							provider.setSamlIssuerFormat(value.get(0));
						} else if(StringUtils.equals(nameIdFomatInSAMLRequest, attribute.getAttributeId())) {
							provider.setNameIdFormatInSAMLRequest(value.get(0));
						}
					}
				}
			}
		}
		return provider;
	}

}
