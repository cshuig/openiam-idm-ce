package org.openiam.ui.idp.saml.service;

import net.sf.ehcache.Ehcache;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.mule.api.endpoint.EndpointBuilder;
import org.openiam.am.srvc.dto.AuthResourceAttributeMap;
import org.openiam.am.srvc.dto.SSOAttribute;
import org.openiam.am.srvc.ws.AuthResourceAttributeWebService;
import org.openiam.authmanager.common.model.AuthorizationResource;
import org.openiam.authmanager.service.AuthorizationManagerWebService;
import org.openiam.authmanager.ws.request.UserToResourceAccessRequest;
import org.openiam.authmanager.ws.response.AccessResponse;
import org.openiam.base.ws.MatchType;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.base.ws.SearchParam;
import org.openiam.idm.searchbeans.EmailSearchBean;
import org.openiam.idm.srvc.audit.constant.AuditAction;
import org.openiam.idm.srvc.audit.constant.AuditAttributeName;
import org.openiam.idm.srvc.audit.constant.AuditSource;
import org.openiam.idm.srvc.audit.dto.IdmAuditLog;
import org.openiam.idm.srvc.auth.domain.AuthStateEntity;
import org.openiam.idm.srvc.auth.domain.AuthStateId;
import org.openiam.idm.srvc.auth.dto.Login;
import org.openiam.idm.srvc.auth.service.AuthenticationService;
import org.openiam.idm.srvc.auth.ws.LoginDataWebService;
import org.openiam.idm.srvc.auth.ws.LoginListResponse;
import org.openiam.idm.srvc.auth.ws.LoginResponse;
import org.openiam.idm.srvc.continfo.dto.EmailAddress;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.idm.srvc.user.ws.UserDataWebService;
import org.openiam.idm.srvc.user.ws.UserResponse;
import org.openiam.provision.dto.ProvisionUser;
import org.openiam.provision.resp.ProvisionUserResponse;
import org.openiam.provision.service.ProvisionService;
import org.openiam.script.ScriptIntegration;
import org.openiam.ui.audit.AuditLogProvider;
import org.openiam.ui.idp.common.service.AuthenticationSweeper;
import org.openiam.ui.idp.saml.exception.AuthenticationException;
import org.openiam.ui.idp.saml.groovy.AbstractJustInTimeSAMLAuthenticator;
import org.openiam.ui.idp.saml.model.SAMLIDPMetadataResponse;
import org.openiam.ui.idp.saml.model.SAMLLogoutRequestToken;
import org.openiam.ui.idp.saml.model.SAMLLogoutResponseToken;
import org.openiam.ui.idp.saml.model.SAMLRequestToken;
import org.openiam.ui.idp.saml.model.SAMLResponseToken;
import org.openiam.ui.idp.saml.model.SAMLSPMetadataResponse;
import org.openiam.ui.idp.saml.provider.AuthenticationProvider;
import org.openiam.ui.idp.saml.provider.SAMLAuthenticationProvider;
import org.openiam.ui.idp.saml.provider.SAMLIdentityProvider;
import org.openiam.ui.idp.saml.provider.SAMLServiceProvider;
import org.openiam.ui.idp.saml.util.SAMLAttributeBuilder;
import org.openiam.ui.idp.saml.util.SAMLDigestUtils;
import org.openiam.ui.idp.saml.util.SAMLEncoder;
import org.openiam.ui.idp.saml.util.SAMLUtils;
import org.openiam.ui.login.LoginActionToken;
import org.openiam.ui.security.OpenIAMCookieProvider;
import org.openiam.ui.util.OpenIAMConstants;
import org.openiam.ui.util.URIUtils;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.web.util.LoginProvider;
import org.opensaml.common.SAMLException;
import org.opensaml.common.SAMLObjectBuilder;
import org.opensaml.common.SignableSAMLObject;
import org.opensaml.common.impl.SecureRandomIdentifierGenerator;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.*;
import org.opensaml.saml2.core.impl.*;
import org.opensaml.saml2.metadata.AssertionConsumerService;
import org.opensaml.saml2.metadata.Endpoint;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml2.metadata.KeyDescriptor;
import org.opensaml.saml2.metadata.NameIDFormat;
import org.opensaml.saml2.metadata.SPSSODescriptor;
import org.opensaml.saml2.metadata.SingleLogoutService;
import org.opensaml.saml2.metadata.SingleSignOnService;
import org.opensaml.saml2.metadata.impl.AssertionConsumerServiceBuilder;
import org.opensaml.saml2.metadata.impl.EntityDescriptorBuilder;
import org.opensaml.saml2.metadata.impl.IDPSSODescriptorBuilder;
import org.opensaml.saml2.metadata.impl.KeyDescriptorBuilder;
import org.opensaml.saml2.metadata.impl.NameIDFormatBuilder;
import org.opensaml.saml2.metadata.impl.SPSSODescriptorBuilder;
import org.opensaml.saml2.metadata.impl.SingleLogoutServiceBuilder;
import org.opensaml.saml2.metadata.impl.SingleSignOnServiceBuilder;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallerFactory;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.schema.XSString;
import org.opensaml.xml.security.SecurityConfiguration;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.credential.UsageType;
import org.opensaml.xml.security.keyinfo.KeyInfoGenerator;
import org.opensaml.xml.security.keyinfo.KeyInfoGeneratorFactory;
import org.opensaml.xml.security.keyinfo.KeyInfoGeneratorManager;
import org.opensaml.xml.security.keyinfo.NamedKeyInfoGeneratorManager;
import org.opensaml.xml.security.x509.BasicX509Credential;
import org.opensaml.xml.security.x509.X509KeyInfoGeneratorFactory;
import org.opensaml.xml.signature.KeyInfo;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.signature.SignatureConstants;
import org.opensaml.xml.signature.SignatureException;
import org.opensaml.xml.signature.SignatureValidator;
import org.opensaml.xml.signature.Signer;
import org.opensaml.xml.signature.X509Data;
import org.opensaml.xml.signature.impl.KeyInfoBuilder;
import org.opensaml.xml.signature.impl.SignatureBuilder;
import org.opensaml.xml.signature.impl.X509CertificateBuilder;
import org.opensaml.xml.signature.impl.X509DataBuilder;
import org.opensaml.xml.util.XMLHelper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import javax.xml.namespace.QName;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component("samlService")
@ManagedResource(objectName="org.openiam.authorization.manager:name=SAMLService")
public class SAMLServiceImpl implements SAMLService, InitializingBean {
	
	private static Logger log = Logger.getLogger(SAMLServiceImpl.class);
	
	@Autowired
	@Qualifier("samlProviderCache")
	private Ehcache samlIdpCache;
	
	@Autowired
	@Qualifier("samlSPCache")
	private Ehcache samlSPCache;
	
	@Autowired
	@Qualifier("samlProviderIdCache")
	private Ehcache samlIdCache;
	
	private Map<String, SAMLServiceProvider> spIdCache;
	
	private boolean cacheInitialized = false;
	
	@Autowired
	@Qualifier("samlAuthenticationSweeper")
	private AuthenticationSweeper authSweeper;
	
	@Autowired
	@Resource(name="authAttributeServiceClient")
	private AuthResourceAttributeWebService authAttributeServiceClient;
	
	@Autowired
	@Resource(name="loginServiceClient")
	private LoginDataWebService loginServiceClient;
	
	@Autowired
	@Resource(name="authorizationManagerServiceClient")
	private AuthorizationManagerWebService authorizationManager;
	
	@Value("${org.openiam.saml.time.threshold.minutes}")
	private long samlIssueThreshold;
	
	@Autowired
	private OpenIAMCookieProvider cookieProvider;
	
	@Value("${org.openiam.idp.provider.sweeptime}")
	private long providerSweepTime;
	
	@Value("${org.openiam.idp.provider.saml.ssourl}")
	private String samlLoginURL;
	
	@Value("${openiam.ui.logouturl}")
	private String samlLogoutURL;
	
	@Value("${org.openiam.idp.saml.skip.uri.check}")
	private boolean skipURICheck;
	
    @Autowired
    @Qualifier("configurableGroovyScriptEngine")
    protected ScriptIntegration scriptRunner;

    @Value("${org.openiam.provision.service.flag}")
    protected Boolean provisionServiceFlag;
	
	private long samlIssueThresholdInMillis;
	
	@Autowired
	private AuditLogProvider auditLogProvider;
	
	@Autowired
	private LoginProvider loginProvider;
	
	@Resource(name="authServiceClient")
	private AuthenticationService authServiceClient;

    @Resource(name = "userServiceClient")
    protected UserDataWebService userDataWebService;
    

    @Resource(name = "provisionServiceClient")
    protected ProvisionService provisionService;
	
	@ManagedOperation(description="sweep the SAML Cache")
	public void sweep() {
		log.debug("Attempting to Sweep SAML Provider cache...");
		final List<SAMLIdentityProvider> samlProviders = authSweeper.getSAMLIdpProviders();
		if(CollectionUtils.isNotEmpty(samlProviders)) {
			final Map<String, SAMLIdentityProvider> providerCache = new HashMap<String, SAMLIdentityProvider>();
			final Map<String, SAMLIdentityProvider> providerIdCache = new HashMap<String, SAMLIdentityProvider>();
			for(final SAMLIdentityProvider provider : samlProviders) {
				if(StringUtils.isNotBlank(provider.getRequestIssuer())) {
					providerCache.put(StringUtils.lowerCase(provider.getRequestIssuer()), provider);
				}
				providerIdCache.put(provider.getId(), provider);
			}
			
			/* purge deleted items */
			final List<String> cacheKeys = (List<String>)samlIdpCache.getKeys();
			for(final String key : cacheKeys) {
				if(!providerCache.containsKey(key)) {
					samlIdpCache.remove(key);
				}
			}
			
			final List<String> cacheIdKeys = (List<String>)samlIdCache.getKeys();
			for(final String key : cacheIdKeys) {
				if(!providerIdCache.containsKey(key)) {
					samlIdCache.remove(key);
				}
			}
			
			/* update/add existing/new items */
			for(final SAMLIdentityProvider provider : samlProviders) {
				final String issuer = provider.getRequestIssuer();
				if(StringUtils.isNotEmpty(issuer)) {
					samlIdpCache.put(new net.sf.ehcache.Element(StringUtils.lowerCase(issuer), provider));
				}
				samlIdCache.put(new net.sf.ehcache.Element(provider.getId(), provider));
			}
		} else {
			samlIdpCache.flush();
			samlIdCache.flush();
		}
		
		final Map<String, SAMLServiceProvider> tempSpIdCache = new HashMap<>();
		final List<SAMLServiceProvider> samlServiceProviders = authSweeper.getSAMLServiceProviders();
		if(CollectionUtils.isNotEmpty(samlServiceProviders)) {
			final Map<String, SAMLServiceProvider> providerCache = new HashMap<String, SAMLServiceProvider>();
			for(final SAMLServiceProvider provider : samlServiceProviders) {
				if(StringUtils.isNotBlank(provider.getName())) {
					providerCache.put(StringUtils.lowerCase(provider.getIssuer()), provider);
				}
			}
			
			/* purge deleted items */
			final List<String> cacheKeys = (List<String>)samlSPCache.getKeys();
			for(final String key : cacheKeys) {
				if(!providerCache.containsKey(key)) {
					samlSPCache.remove(key);
				}
			}
			
			/* update/add existing/new items */
			for(final SAMLServiceProvider provider : samlServiceProviders) {
				final String issuer = provider.getIssuer();
				if(StringUtils.isNotEmpty(issuer)) {
					samlSPCache.put(new net.sf.ehcache.Element(StringUtils.lowerCase(issuer), provider));
					tempSpIdCache.put(provider.getId(), provider);
				}
			}
		} else {
			samlSPCache.flush();
		}
		
		spIdCache = tempSpIdCache;
		cacheInitialized = true;
		log.debug("Done sweeping SAML Cache...");
	}
	
	private SAMLIdentityProvider getSAMLProvider(final String issuer) {
		try {
			if(!cacheInitialized) { /* in case the ESB was down at startup, retry */
				sweep();
			}
		
			SAMLIdentityProvider provider = null;
			if(issuer != null) {
				String lowerIssuer = issuer.toLowerCase();
				final net.sf.ehcache.Element cacheElement = samlIdpCache.get(lowerIssuer);
				if(cacheElement != null) {
					provider = (SAMLIdentityProvider)cacheElement.getObjectValue();
				}
			}
			return provider;
		} catch(Throwable e) { /* would happen if the ESB is down - just return null */
			return null;
		}
	}
	
	public SAMLServiceProvider getSAMLServiceProvierById(final String id) {
		try {
			if(!cacheInitialized) { /* in case the ESB was down at startup, retry */
				sweep();
			}
			return spIdCache.get(id);
		} catch(Throwable e) { /* would happen if the ESB is down - just return null */
			return null;
		}
	}
	
	private SAMLServiceProvider getSAMLServiceProviderByIssuer(final String issuer) {
		try {
			if(!cacheInitialized) { /* in case the ESB was down at startup, retry */
				sweep();
			}
			
			SAMLServiceProvider provider = null;
			if(issuer != null) {
				final net.sf.ehcache.Element cacheElement = samlSPCache.get(issuer.toLowerCase());
				if(cacheElement != null) {
					provider = (SAMLServiceProvider)cacheElement.getObjectValue();
				}
			}
			return provider;
		} catch(Throwable e) {
			return null;
		}
	}
	
	private SAMLIdentityProvider getSAMLProviderById(final String id) {
		try {
			if(!cacheInitialized) { /* in case the ESB was down at startup, retry */
				sweep();
			}
			SAMLIdentityProvider provider = null;
			if(id != null) {
				final net.sf.ehcache.Element cacheElement = samlIdCache.get(id);
				if(cacheElement != null) {
					provider = (SAMLIdentityProvider)cacheElement.getObjectValue();
				}
			}
			return provider;
		} catch(Throwable e) { /* would happen if the ESB is down - just return null */
			return null;
		}
	}
	
	/*
	 * Does basic validation on the SAML Request
	 */
	private void validateAuthnRequest(final AuthnRequest authnRequest, final SAMLIdentityProvider provider) throws SAMLException {
		//TODO: timestamp validation
		
	}
	

	@Override
	public SAMLSPMetadataResponse getSPMetadata(final HttpServletRequest request, final String serviceProviderId) {
		final SAMLSPMetadataResponse token = new SAMLSPMetadataResponse();
		
		try {
			final MarshallerFactory marshallerFactory = Configuration.getMarshallerFactory();
			
			final SAMLServiceProvider provider = getSAMLServiceProvierById(serviceProviderId);
			if(provider == null) {
				throw new AuthenticationException(String.format("SAMLServiceProvider with ID '%s' not configured or doesn't exist", serviceProviderId), Errors.SAML_SERVICE_PROVIDER_NOT_FOUND);
			}
			
			final XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();
			final EntityDescriptor entityDescriptor = ((EntityDescriptorBuilder)builderFactory.getBuilder(EntityDescriptor.DEFAULT_ELEMENT_NAME)).buildObject();
			entityDescriptor.setID(generateId());
			entityDescriptor.setCacheDuration(providerSweepTime);
			entityDescriptor.setEntityID(provider.getIssuer());
			
			final SPSSODescriptor descriptor = ((SPSSODescriptorBuilder)builderFactory.getBuilder(SPSSODescriptor.DEFAULT_ELEMENT_NAME)).buildObject();
			descriptor.setAuthnRequestsSigned(false);
			descriptor.setWantAssertionsSigned(false); /* we only expect the Response to be signed */
			descriptor.addSupportedProtocol("urn:oasis:names:tc:SAML:2.0:protocol"); /* looks like Foregrock reads the metadata as invalid w/o this */
			
			final KeyDescriptor keyDescriptor = ((KeyDescriptorBuilder)builderFactory.getBuilder(KeyDescriptor.DEFAULT_ELEMENT_NAME)).buildObject();
			if(provider.isSign()) {
				keyDescriptor.setUse(UsageType.SIGNING);
				final Credential signingCredential = getSigningCredential(provider);
				final X509KeyInfoGeneratorFactory keyInfoGeneratorFactory = new X509KeyInfoGeneratorFactory();  
				keyInfoGeneratorFactory.setEmitEntityCertificate(true);
				final KeyInfoGenerator keyInfoGenerator = keyInfoGeneratorFactory.newInstance();
				keyDescriptor.setKeyInfo(keyInfoGenerator.generate(signingCredential));
			} else {
				keyDescriptor.setUse(UsageType.UNSPECIFIED);
			}
			descriptor.getKeyDescriptors().add(keyDescriptor);
			
			/* NameID would go here, but we have no name ID */
			for(final String format : new String[] {NameIDType.EMAIL, NameIDType.TRANSIENT}) {
				final NameIDFormat nameIdFormat = ((NameIDFormatBuilder)builderFactory.getBuilder(NameIDFormat.DEFAULT_ELEMENT_NAME)).buildObject();
				nameIdFormat.setFormat(format);
				descriptor.getNameIDFormats().add(nameIdFormat);
			}
			
			final String baseURI = URIUtils.getBaseURI(request);
			final String entryPointURL = new StringBuilder(baseURI).append("/idp/sp/login").toString();
			final String applicationLoginURL = provider.getLoginURL();
			final String applicationLogoutURL = provider.getLogoutURL();
			
			descriptor.getAssertionConsumerServices();
			//descriptor.getSingleLogoutServices();
			
			for(final String binding : new String[] {SAMLConstants.SAML2_POST_BINDING_URI}) {
				final AssertionConsumerService ssoService = ((AssertionConsumerServiceBuilder)builderFactory.getBuilder(AssertionConsumerService.DEFAULT_ELEMENT_NAME)).buildObject();
				ssoService.setBinding(binding);
				ssoService.setLocation(entryPointURL);
				//ssoService.setResponseLocation(applicationLoginURL);
				ssoService.setIndex(0);
				ssoService.setIsDefault(true);
				descriptor.getAssertionConsumerServices().add(ssoService);
			}
			
			descriptor.addSupportedProtocol(SAMLConstants.SAML20_NS);
			entityDescriptor.getRoleDescriptors().add(descriptor);
			
			final Element entityDescriptorElement = marshallerFactory.getMarshaller(entityDescriptor).marshall(entityDescriptor);
			token.setEntityDescriptor(entityDescriptor);
			token.setEntityDescriptorElement(entityDescriptorElement);
		} catch(SecurityException e) {
			token.setError(Errors.SAML_SECURITY_EXCEPTION, e);
			log.warn(String.format("Authencation Exception: %s", e.getMessage()));
		} catch(AuthenticationException e) {
			token.setError(e.getError(), e);
			log.warn(String.format("Authencation Exception: %s", e.getMessage()));
		} catch(Throwable e) {
			log.error("SAML Exception", e);
			token.setError(Errors.INTERNAL_ERROR, e);
		}
		return token;
	}
	
	@Override
	public SAMLIDPMetadataResponse getSAMLIDPMetadata(HttpServletRequest request) {
		final SAMLIDPMetadataResponse token = new SAMLIDPMetadataResponse();
		try {
			final String samlProviderId = request.getParameter("id");
			final MarshallerFactory marshallerFactory = Configuration.getMarshallerFactory();
			
			final SAMLIdentityProvider provider = getSAMLProviderById(samlProviderId);
			
			if(provider == null) {
				throw new AuthenticationException(String.format("SAMLProvider with ID '%s' not configured or doesn't exist", samlProviderId), Errors.SAML_NOT_CONFIGURED_FOR_ACS);
			}
			
			if(!provider.isMetadataExposed()) {
				throw new AuthenticationException(String.format("SAMLProvider with ID '%s' does not expose metdata", samlProviderId), Errors.SAML_METADATA_NOT_EXPOSED);
			}
			
			final XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();
			
			final EntityDescriptor entityDescriptor = ((EntityDescriptorBuilder)builderFactory.getBuilder(EntityDescriptor.DEFAULT_ELEMENT_NAME)).buildObject();

			entityDescriptor.setEntityID(provider.getRequestIssuer());
			entityDescriptor.setCacheDuration(providerSweepTime);
			//entityDescriptor.setValidUntil(new DateTime().plus(providerSweepTime));
			entityDescriptor.setID(generateId());

			final IDPSSODescriptor idpSSODescriptor = ((IDPSSODescriptorBuilder)builderFactory.getBuilder(IDPSSODescriptor.DEFAULT_ELEMENT_NAME)).buildObject();
			idpSSODescriptor.setWantAuthnRequestsSigned(false);
			//idpSSODescriptor.addSupportedProtocol(SAMLConstants.SAML20_NS);
			
			final KeyDescriptor keyDescriptor = ((KeyDescriptorBuilder)builderFactory.getBuilder(KeyDescriptor.DEFAULT_ELEMENT_NAME)).buildObject();
			if(provider.isSign()) {
				keyDescriptor.setUse(UsageType.SIGNING);
				final Credential signingCredential = getSigningCredential(provider);
				final X509KeyInfoGeneratorFactory keyInfoGeneratorFactory = new X509KeyInfoGeneratorFactory();  
				keyInfoGeneratorFactory.setEmitEntityCertificate(true);
				final KeyInfoGenerator keyInfoGenerator = keyInfoGeneratorFactory.newInstance();
				keyDescriptor.setKeyInfo(keyInfoGenerator.generate(signingCredential));
			} else {
				keyDescriptor.setUse(UsageType.UNSPECIFIED);
			}
			idpSSODescriptor.getKeyDescriptors().add(keyDescriptor);
			
			final NameIDFormat nameIdFormat = ((NameIDFormatBuilder)builderFactory.getBuilder(NameIDFormat.DEFAULT_ELEMENT_NAME)).buildObject();
			nameIdFormat.setFormat(getNameIdFormat(provider));
			idpSSODescriptor.getNameIDFormats().add(nameIdFormat);
			
			final String baseURI = URIUtils.getBaseURI(request);
			final String loginURI = new StringBuilder(baseURI).append(samlLoginURL).toString();
			final String logoutURI = new StringBuilder(baseURI).append(samlLogoutURL).toString();
			for(final String binding : new String[] {SAMLConstants.SAML2_REDIRECT_BINDING_URI, SAMLConstants.SAML2_POST_BINDING_URI}) {
				final SingleSignOnService ssoService = ((SingleSignOnServiceBuilder)builderFactory.getBuilder(SingleSignOnService.DEFAULT_ELEMENT_NAME)).buildObject();
				ssoService.setBinding(binding);
				ssoService.setLocation(loginURI);
				ssoService.setResponseLocation(loginURI);
				idpSSODescriptor.getSingleSignOnServices().add(ssoService);
				
				final SingleLogoutService ssoLogoutService = ((SingleLogoutServiceBuilder)builderFactory.getBuilder(SingleLogoutService.DEFAULT_ELEMENT_NAME)).buildObject();
				ssoLogoutService.setBinding(binding);
				ssoLogoutService.setLocation(logoutURI);
				ssoLogoutService.setResponseLocation(logoutURI);
				idpSSODescriptor.getSingleLogoutServices().add(ssoLogoutService);
			}
			
			if(CollectionUtils.isNotEmpty(provider.getAmAttributes())) {
				for(final AuthResourceAttributeMap attribute : provider.getAmAttributes()) {
					idpSSODescriptor.getAttributes().add(SAMLAttributeBuilder.buildMetadataAttribute(attribute, builderFactory));
				}
			}
			
			idpSSODescriptor.addSupportedProtocol(SAMLConstants.SAML20_NS);
			entityDescriptor.getRoleDescriptors().add(idpSSODescriptor);

			/* sign the response.  this MUST BE THE LAST BLOCK, otheriwse you'll get no signature!!!!! */
			if(provider.isSign()) {
				final Signature signature = getSignature(provider, builderFactory);
				entityDescriptor.setSignature(signature);
				marshallerFactory.getMarshaller(entityDescriptor).marshall(entityDescriptor);
				Signer.signObject(signature);
			}
			final Element entityDescriptorElement = marshallerFactory.getMarshaller(entityDescriptor).marshall(entityDescriptor);
			token.setEntityDescriptor(entityDescriptor);
			token.setEntityDescriptorElement(entityDescriptorElement);
		} catch(SecurityException e) {
			token.setError(Errors.SAML_SECURITY_EXCEPTION, e);
			log.warn(String.format("Authencation Exception: %s", e.getMessage()));
		} catch(AuthenticationException e) {
			token.setError(e.getError(), e);
			log.warn(String.format("Authencation Exception: %s", e.getMessage()));
		} catch(Throwable e) {
			log.error("SAML Exception", e);
			token.setError(Errors.INTERNAL_ERROR, e);
		}
		return token;
	}
	
	/**
	 * This function is called after the user has logged into our system.  At this point, he has a SAML Token, as well as a Session
	 * within the IdP.  Since he has already authenticated, we need to:
	 * 1) Check if the User has a login with the requested Managed System (part of the SAMLProvider)
	 * 2) Generate a SAML Response to be sent back to the ACS
	 * 
	 * The following links were used to reverse engineer SAML, generates the keys, etc
	 * https://developers.google.com/google-apps/sso/saml_reference_implementation
	 * https://developers.google.com/google-apps/sso/saml_reference_implementation_web#samlReferenceImplementationWebCodeStructure
	 * https://developers.google.com/google-apps/help/faq/saml-sso
	 * https://developers.google.com/google-apps/help/articles/sso-keygen
	 * http://docs.oasis-open.org/security/saml/v2.0/saml-core-2.0-os.pdf
	 * http://na14.salesforce.com/help/doc/en/sso_saml_assertion_examples.htm
	 */
	@Override
	public SAMLResponseToken samlLogin(final HttpServletRequest httpRequest) {
		final SAMLResponseToken token = new SAMLResponseToken();
		final String relayState = httpRequest.getParameter("RelayState");
		final String userId = cookieProvider.getUserId(httpRequest);
		final DateTime now = new DateTime(DateTimeZone.UTC);
		
		if(log.isDebugEnabled()) {
			log.debug(String.format("Relay State", relayState));
			log.debug(String.format("userId: %s", userId));
		}
		
		final IdmAuditLog auditLog = new IdmAuditLog();
		auditLog.setAction(AuditAction.SAML_LOGIN.value());
        auditLog.setRequestorUserId(userId);
		auditLog.setRequestorPrincipal(cookieProvider.getPrincipal(httpRequest));
		auditLog.addAttribute(AuditAttributeName.RELAY_STATE, relayState);
		try {
			final MarshallerFactory marshallerFactory = Configuration.getMarshallerFactory();
			final AuthnRequest authnRequest = SAMLDigestUtils.getAuthNRequest(httpRequest, skipURICheck);
			
			final Element marshalledRequest = marshallerFactory.getMarshaller(authnRequest).marshall(authnRequest);
			final String samlReqeustString = XMLHelper.prettyPrintXML(marshalledRequest);
			auditLog.addAttribute(AuditAttributeName.SAML_REQUEST_XML, samlReqeustString);
			log.info(String.format("SAML Request: %s", samlReqeustString));
			
			final String requestIssuer = authnRequest.getIssuer().getValue();
			
			final SAMLIdentityProvider provider = getSAMLProvider(requestIssuer);
			
			if(provider == null) {
				throw new AuthenticationException(String.format("SAMLProvider with Issuer '%s' not configured or doesn't exist", requestIssuer), Errors.SAML_NOT_CONFIGURED_FOR_ACS);
			}
			
			auditLog.addAttribute(AuditAttributeName.SAML_PROVIDER, provider.getId());
			
			if(!isUserEntitledTo(userId, provider)) {
				final String message = String.format("User with ID '%s' not entitled to SSO with SAML Provider '%s", userId, provider.getName());
				throw new AuthenticationException(message, Errors.SAML_USER_NOT_AUTHORIZED_TO_SSO);
			}
			
			final String acs = provider.getAssertionConsumerURL();
			
			validateAuthnRequest(authnRequest, provider);
			
			/* before even trying to generate the SAML Response, check to see if the User has a login with the SAML Provider */
			Login providerLogin = null;
			final LoginListResponse loginListResponse = loginServiceClient.getLoginByUser(userId);
			if(loginListResponse.getStatus() == ResponseStatus.SUCCESS) {
				if(CollectionUtils.isNotEmpty(loginListResponse.getPrincipalList())) {
					for(final Login login : loginListResponse.getPrincipalList()) {
						if(StringUtils.equals(login.getManagedSysId(), provider.getManagedSysId())) {
							providerLogin = login;
							break;
						}
					}
				}
			}
			
			if(providerLogin == null) {
				throw new AuthenticationException("User does not have a login with this managed system", Errors.AUTH_USER_NOT_PROVISIONED);
			}
			
			final XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();
			final Response response = ((ResponseBuilder)builderFactory.getBuilder(Response.DEFAULT_ELEMENT_NAME)).buildObject();
			response.setDestination(acs);
			response.setIssueInstant(now);
			response.setInResponseTo(authnRequest.getID());
			response.setID(generateId());
			
			
			/* set issuer on response */
			response.setIssuer(getIssuer(httpRequest, builderFactory, provider));
			
			/* set status on response */
			final Status status = ((StatusBuilder)builderFactory.getBuilder(Status.DEFAULT_ELEMENT_NAME)).buildObject();
			final StatusCode statuscode = ((StatusCodeBuilder)builderFactory.getBuilder(StatusCode.DEFAULT_ELEMENT_NAME)).buildObject();
			statuscode.setValue(StatusCode.SUCCESS_URI);
			status.setStatusCode(statuscode);
			response.setStatus(status);
			 
			/* create assertion on response */
			final Assertion assertion = ((AssertionBuilder) builderFactory.getBuilder(Assertion.DEFAULT_ELEMENT_NAME)).buildObject();
			assertion.setID(generateId());
			assertion.setIssueInstant(now);
			assertion.setIssuer(getIssuer(httpRequest, builderFactory, provider));
			response.getAssertions().add(assertion);
			
			/* create subject */
			final Subject subject = ((SubjectBuilder) builderFactory.getBuilder(Subject.DEFAULT_ELEMENT_NAME)).buildObject();
			assertion.setSubject(subject);
			
			/* set subject confirmation data */
			final SubjectConfirmation bearerConfirmation = ((SubjectConfirmationBuilder)builderFactory.getBuilder(SubjectConfirmation.DEFAULT_ELEMENT_NAME)).buildObject();
			bearerConfirmation.setMethod(SubjectConfirmation.METHOD_BEARER);
			subject.getSubjectConfirmations().add(bearerConfirmation);
			
			final SubjectConfirmationData confirmationData = ((SubjectConfirmationDataBuilder)builderFactory.getBuilder(SubjectConfirmationData.DEFAULT_ELEMENT_NAME)).buildObject();
			bearerConfirmation.setSubjectConfirmationData(confirmationData);
			confirmationData.setInResponseTo(authnRequest.getID());
			confirmationData.setRecipient(acs);
			confirmationData.setNotOnOrAfter(now.plus(samlIssueThresholdInMillis));
			
			/* set nameId on subject */
			log.debug(String.format("Sending %s as the login ID", providerLogin.getLogin()));
			final NameID nameId = ((NameIDBuilder)builderFactory.getBuilder(NameID.DEFAULT_ELEMENT_NAME)).buildObject();
			nameId.setValue(providerLogin.getLogin());
			nameId.setFormat(getNameIdFormat(provider));
			nameId.setSPNameQualifier(getSPNameQualifier(provider));
			nameId.setNameQualifier(getNameQualifier(provider));
			subject.setNameID(nameId);
			
			/* set audience conditions */
			final List<Audience> audienceList = getAudiences(acs, provider, builderFactory);
			final Conditions condition = ((ConditionsBuilder)builderFactory.getBuilder(Conditions.DEFAULT_ELEMENT_NAME)).buildObject();
			condition.setNotBefore(now.minus(samlIssueThresholdInMillis));
			condition.setNotOnOrAfter(now.plus(samlIssueThresholdInMillis));
			if(CollectionUtils.isNotEmpty(audienceList)) {
				final AudienceRestriction audienceRestriction = ((AudienceRestrictionBuilder)builderFactory.getBuilder(AudienceRestriction.DEFAULT_ELEMENT_NAME)).buildObject();
				condition.getAudienceRestrictions().add(audienceRestriction);
				audienceRestriction.getAudiences().addAll(audienceList);
			}
			assertion.setConditions(condition);
			
			/* set authentication statement */
			final AuthnStatement authnStatement = ((AuthnStatementBuilder)builderFactory.getBuilder(AuthnStatement.DEFAULT_ELEMENT_NAME)).buildObject();
			authnStatement.setAuthnInstant(now);
			final AuthnContext authnContext = ((AuthnContextBuilder)builderFactory.getBuilder(AuthnContext.DEFAULT_ELEMENT_NAME)).buildObject();
			final AuthnContextClassRef authnContextClassRef = ((AuthnContextClassRefBuilder)builderFactory.getBuilder(AuthnContextClassRef.DEFAULT_ELEMENT_NAME)).buildObject();
			authnContextClassRef.setAuthnContextClassRef(getAuthnContextClassRef(provider));
			authnStatement.setAuthnContext(authnContext);
			authnContext.setAuthnContextClassRef(authnContextClassRef);
			assertion.getAuthnStatements().add(authnStatement);
			
			if(provider.isHasAMAttributes()) {
				final List<SSOAttribute> ssoAttributes = 
						authAttributeServiceClient.getSSOAttributes(provider.getId(), userId);
				if(CollectionUtils.isNotEmpty(ssoAttributes)) {
					final AttributeStatement attrStatement = ((AttributeStatementBuilder)builderFactory.getBuilder(AttributeStatement.DEFAULT_ELEMENT_NAME)).buildObject();
					for(final SSOAttribute ssoAttribute : ssoAttributes) {
						final Attribute attr = SAMLAttributeBuilder.buildAttribute(ssoAttribute, builderFactory);
						if(attr != null) {
							attrStatement.getAttributes().add(attr);
						}
					}
					assertion.getAttributeStatements().add(attrStatement);
				}
			}
			
			if(authnRequest.isSigned()) {
				final BasicX509Credential credential = new BasicX509Credential();

				credential.setUsageType(UsageType.SIGNING);

			    final InputStream inStream = new ByteArrayInputStream(provider.getPublicKey());
			    final CertificateFactory cf = CertificateFactory.getInstance("X.509");
			    final X509Certificate cert = (X509Certificate)cf.generateCertificate(inStream);
			    inStream.close();
			    credential.setEntityCertificate(cert);
			    credential.setPublicKey(cert.getPublicKey());
				
				final Signature signature = authnRequest.getSignature();
				final SignatureValidator validator = new SignatureValidator(credential);
				validator.validate(signature);
			}
			
			/* sign the response.  this MUST BE THE LAST BLOCK, otheriwse you'll get no signature!!!!! */
			Element responseElmt = null;
			if(provider.isSign()) {
				final Signature signature = getSignature(provider, builderFactory);
				response.setSignature(signature);
				responseElmt = marshallerFactory.getMarshaller(response).marshall(response);
				Signer.signObject(signature);
			} else {
				responseElmt = marshallerFactory.getMarshaller(response).marshall(response);	
			}
			
			token.setEncodedResponse(SAMLEncoder.encodeResponse(response));
			token.setAssertionConsumerURL(acs);
			token.setResponse(response);
			token.setRelayState(relayState);
			
			final String prettyXML = XMLHelper.prettyPrintXML(responseElmt);
				
			log.info(String.format("SAML XML: %s", prettyXML));
			auditLog.addAttribute(AuditAttributeName.SAML_REQUEST_XML, samlReqeustString);
			auditLog.addAttribute(AuditAttributeName.SAML_RESPONSE_XML, prettyXML);
			auditLog.succeed();
		} catch(SecurityException e) {
			auditLog.setFailureReason(e.getMessage());
            auditLog.setException(e);
            auditLog.fail();
			token.setError(Errors.SAML_SECURITY_EXCEPTION, e);
			log.warn(String.format("Authencation Exception: %s", e.getMessage()));
		} catch(AuthenticationException e) {
			auditLog.setFailureReason(e.getMessage());
            auditLog.setException(e);
            auditLog.fail();
			token.setError(e.getError(), e);
			log.warn(String.format("Authencation Exception: %s", e.getMessage()));
		} catch(Throwable e) {
			auditLog.setFailureReason(e.getMessage());
            auditLog.setException(e);
            auditLog.fail();
			log.error("SAML Exception", e);
			token.setError(Errors.INTERNAL_ERROR, e);
		} finally {
			auditLogProvider.add(AuditSource.IDP, httpRequest, auditLog);
		}
		return token;
	}
	
	private Signature getSignature(final SAMLIdentityProvider provider, final XMLObjectBuilderFactory builderFactory) throws SecurityException, InvalidKeySpecException, NoSuchAlgorithmException, CertificateException, IOException {
		final Credential signingCredential = getSigningCredential(provider);
		final Signature signature = ((SignatureBuilder)builderFactory.getBuilder(Signature.DEFAULT_ELEMENT_NAME)).buildObject();
		signature.setSigningCredential(signingCredential);
		signature.setSignatureAlgorithm(SignatureConstants.ALGO_ID_SIGNATURE_RSA_SHA1);
		signature.setCanonicalizationAlgorithm(SignatureConstants.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);
		signature.setKeyInfo(getKeyInfo(signingCredential));
		return signature;
	}
	
	private String generateId() {
		try {
			return new SecureRandomIdentifierGenerator().generateIdentifier();
		} catch(Throwable e) {
			log.error("Can't generate ID", e);
			return RandomStringUtils.randomAlphanumeric(16);
		}
	}
	
	private String getNameQualifier(final SAMLIdentityProvider provider) {
		return StringUtils.trimToNull(provider.getNameQualifier());
	}
	
	private String getSPNameQualifier(final SAMLIdentityProvider provider) {
		return StringUtils.trimToNull(provider.getSpNameQualifier());
	}
	
	private String getNameIdFormat(final SAMLIdentityProvider provider) {
		String retval = null;
		if(StringUtils.isNotBlank(provider.getNameIdFormat())) {
			retval = provider.getNameIdFormat();
		} else {
			retval = NameIDType.EMAIL;
		}
		return retval;
	}
	
	private String getAuthnContextClassRef(final SAMLIdentityProvider provider) {
		String retVal = null;
		if(StringUtils.isNotBlank(provider.getAuthContextClassRef())) {
			retVal = provider.getAuthContextClassRef();
		} else {
			retVal = AuthnContext.PASSWORD_AUTHN_CTX;
		}
		return retVal;
	}
	
	private List<Audience> getAudiences(final String assertionConsumerURL, final SAMLIdentityProvider provider, final XMLObjectBuilderFactory builderFactory) {
		final List<Audience> audienceList = new LinkedList<Audience>();
		if(CollectionUtils.isNotEmpty(provider.getAudiences())) {
			for(final String audienceURI : provider.getAudiences()) {
				final Audience audience = ((AudienceBuilder)builderFactory.getBuilder(Audience.DEFAULT_ELEMENT_NAME)).buildObject();
				audience.setAudienceURI(audienceURI);
				audienceList.add(audience);
			}
		}
		return audienceList;
	}
	
	private Issuer getIssuer(final HttpServletRequest request, final XMLObjectBuilderFactory builderFactory, final SAMLIdentityProvider provider) {
		final Issuer issuer = ((IssuerBuilder)builderFactory.getBuilder(Issuer.DEFAULT_ELEMENT_NAME)).buildObject();
		String issuerURL = null;
		if(StringUtils.isNotBlank(provider.getResponseIssuer())) {
			issuerURL = provider.getResponseIssuer();
		} else { /* default to current URL*/
			issuerURL = URIUtils.getRequestURL(request);
		}
		issuer.setValue(issuerURL);
		return issuer;
	}
	
	private KeyInfo getKeyInfo(final Credential credential) throws SecurityException {
		final SecurityConfiguration secConfiguration = Configuration.getGlobalSecurityConfiguration();
		final NamedKeyInfoGeneratorManager namedKeyInfoGeneratorManager = secConfiguration.getKeyInfoGeneratorManager();
		final KeyInfoGeneratorManager keyInfoGeneratorManager = namedKeyInfoGeneratorManager.getDefaultManager();
		final KeyInfoGeneratorFactory keyInfoGeneratorFactory = keyInfoGeneratorManager.getFactory(credential);
		final KeyInfoGenerator keyInfoGenerator = keyInfoGeneratorFactory.newInstance();
		final KeyInfo keyInfo = keyInfoGenerator.generate(credential);
		return keyInfo;
	}
	
	private BasicX509Credential getSigningCredential(final SAMLAuthenticationProvider provider) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException, java.security.cert.CertificateException {
		final BasicX509Credential credential = new BasicX509Credential();

		credential.setUsageType(UsageType.SIGNING);

	    final InputStream inStream = new ByteArrayInputStream(provider.getPublicKey());
	    final CertificateFactory cf = CertificateFactory.getInstance("X.509");
	    final X509Certificate cert = (X509Certificate)cf.generateCertificate(inStream);
	    inStream.close();
	    credential.setEntityCertificate(cert);
	    credential.setPublicKey(cert.getPublicKey());
	    
	    if(provider instanceof SAMLIdentityProvider) {
	    	final PKCS8EncodedKeySpec kspec = new PKCS8EncodedKeySpec(((SAMLIdentityProvider)provider).getPrivateKey());
	    	final KeyFactory kf = KeyFactory.getInstance("RSA");
	    	final PrivateKey privKey = kf.generatePrivate(kspec);
	    	credential.setPrivateKey(privKey);
	    }
	    
	    return credential;
	}
	
	private boolean isUserEntitledTo(final String userId, final AuthenticationProvider provider) {
		final UserToResourceAccessRequest accessRequest = new UserToResourceAccessRequest();
		accessRequest.setUserId(userId);
		final AuthorizationResource authorizationResource = new AuthorizationResource();
		authorizationResource.setId(provider.getResource().getId());
		accessRequest.setResource(authorizationResource);
		final AccessResponse response = authorizationManager.isUserEntitledTo(accessRequest);
		return response.getResult();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		try {
			//sweep();
		} catch(Throwable e) {
			/* in case ESB not started */
			log.warn("Could not load SAML Providers - will retry on next hit, or refesh interval...");
		}
		samlIssueThresholdInMillis = samlIssueThreshold * 60 * 1000;
	}

	@Override
	public SAMLRequestToken getSAMLRequestForSP(final HttpServletRequest request, final String issuerName) {
		final SAMLRequestToken token = new SAMLRequestToken();
		final String userId = cookieProvider.getUserIdFromCookie(request);
		final DateTime now = new DateTime();
		
		
		final IdmAuditLog auditLog = new IdmAuditLog();
		auditLog.setAction(AuditAction.SAML_LOGIN.value());
        auditLog.setRequestorUserId(userId);
		auditLog.setRequestorPrincipal(cookieProvider.getPrincipal(request));
		
		try {
			final MarshallerFactory marshallerFactory = Configuration.getMarshallerFactory();
			final SAMLServiceProvider serviceProvider = getSAMLServiceProviderByIssuer(issuerName);
			if(serviceProvider == null) {
				throw new AuthenticationException(String.format("SAMLServiceProvider with Name '%s' not configured or doesn't exist", issuerName), Errors.SAML_SERVICE_PROVIDER_NOT_FOUND);
			}
			
			final XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();
			final AuthnRequest authnRequest = ((AuthnRequestBuilder)builderFactory.getBuilder(AuthnRequest.DEFAULT_ELEMENT_NAME)).buildObject();
			authnRequest.setAssertionConsumerServiceURL(URIUtils.getRequestURL(request));
			authnRequest.setID(generateId());
			authnRequest.setIssueInstant(now);
			authnRequest.setProtocolBinding(SAMLConstants.SAML2_POST_BINDING_URI);
			authnRequest.setProviderName(serviceProvider.getName());
			/* IDMAPPS-2807 */
			if(serviceProvider.isIncludeDestinationInAuthnRequest()) {
				authnRequest.setDestination(serviceProvider.getLoginURL());
			}
			
			final Issuer issuer = ((IssuerBuilder)builderFactory.getBuilder(Issuer.DEFAULT_ELEMENT_NAME)).buildObject();
			issuer.setValue(serviceProvider.getIssuer());
			issuer.setFormat(serviceProvider.getSamlIssuerFormat());
			authnRequest.setIssuer(issuer);
			
			serviceProvider.setLoginURL(serviceProvider.getLoginURL());
			serviceProvider.setLogoutURL(serviceProvider.getLogoutURL());
			token.setServiceProvider(serviceProvider);
			
			final Endpoint endpoint = ((SAMLObjectBuilder<Endpoint>) builderFactory.getBuilder(AssertionConsumerService.DEFAULT_ELEMENT_NAME)).buildObject();
			endpoint.setLocation(serviceProvider.getLoginURL());
			token.setEndpoint(endpoint);

			/* IDMAPPS-2807 */
			if(StringUtils.isNotBlank(serviceProvider.getNameIdFormatInSAMLRequest())) {
				final NameIDPolicy nameIdPolicy = ((NameIDPolicyBuilder)builderFactory.getBuilder(NameIDPolicy.DEFAULT_ELEMENT_NAME)).buildObject();
				nameIdPolicy.setAllowCreate(serviceProvider.isAllowCreateOnNameIdPolicy());
				nameIdPolicy.setSPNameQualifier(serviceProvider.getSpNameQualifier());
				nameIdPolicy.setFormat(serviceProvider.getNameIdFormatInSAMLRequest());
				authnRequest.setNameIDPolicy(nameIdPolicy);
			}
			
			if(StringUtils.isNotBlank(serviceProvider.getAuthnContextClassRef())) {
				final RequestedAuthnContext context = ((RequestedAuthnContextBuilder)builderFactory.getBuilder(RequestedAuthnContext.DEFAULT_ELEMENT_NAME)).buildObject();
				context.setComparison(AuthnContextComparisonTypeEnumeration.EXACT);
				final AuthnContextClassRef classRef = ((AuthnContextClassRefBuilder)builderFactory.getBuilder(AuthnContextClassRef.DEFAULT_ELEMENT_NAME)).buildObject();
				classRef.setAuthnContextClassRef(serviceProvider.getAuthnContextClassRef());
				context.getAuthnContextClassRefs().add(classRef);
				authnRequest.setRequestedAuthnContext(context);
			}
			
			Element authnRequestElmt = null;
			if(serviceProvider.isSign()) {
				final BasicX509Credential credential = getSigningCredential(serviceProvider);
				final Signature signature = ((SignatureBuilder)builderFactory.getBuilder(Signature.DEFAULT_ELEMENT_NAME)).buildObject();
				signature.setSigningCredential(credential);
				signature.setSignatureAlgorithm(SignatureConstants.ALGO_ID_SIGNATURE_RSA_SHA1);
				signature.setCanonicalizationAlgorithm(SignatureConstants.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);
				final KeyInfo keyInfo = ((KeyInfoBuilder) builderFactory.getBuilder(KeyInfo.DEFAULT_ELEMENT_NAME)).buildObject();
				final X509Data data = ((X509DataBuilder) builderFactory.getBuilder(X509Data.DEFAULT_ELEMENT_NAME)).buildObject();
				final org.opensaml.xml.signature.X509Certificate cert = ((X509CertificateBuilder) 
						builderFactory.getBuilder(org.opensaml.xml.signature.X509Certificate.DEFAULT_ELEMENT_NAME)).buildObject();
				final String value = org.apache.xml.security.utils.Base64.encode(credential.getEntityCertificate().getEncoded());
				cert.setValue(value);
				data.getX509Certificates().add(cert);
				keyInfo.getX509Datas().add(data);
				signature.setKeyInfo(keyInfo);
				
	            authnRequest.setSignature(signature);
			}
			
			authnRequestElmt = marshallerFactory.getMarshaller(authnRequest).marshall(authnRequest);
			
			final String prettyXML = XMLHelper.prettyPrintXML(authnRequestElmt);
			auditLog.addAttribute(AuditAttributeName.SAML_REQUEST_XML, prettyXML);
			
			token.setAuthnRequest(authnRequest);
		/*
		} catch(SecurityException e) {
			auditLog.setFailureReason(e.getMessage());
            auditLog.setException(e);
            auditLog.fail();
			token.setError(Errors.SAML_SECURITY_EXCEPTION, e);
			log.warn(String.format("Authencation Exception: %s", e.getMessage()));
		*/
		} catch(AuthenticationException e) {
			auditLog.setFailureReason(e.getMessage());
            auditLog.setException(e);
            auditLog.fail();
			token.setError(e.getError(), e);
			log.warn(String.format("Authencation Exception: %s", e.getMessage()));
		} catch(Throwable e) {
			auditLog.setFailureReason(e.getMessage());
            auditLog.setException(e);
            auditLog.fail();
			log.error("SAML Exception", e);
			token.setError(Errors.INTERNAL_ERROR, e);
		} finally {
			auditLogProvider.add(AuditSource.SP, request, auditLog);
		}
		return token;
	}

	@Override
	public SAMLResponseToken processSAMLResponse(final HttpServletRequest httpRequest, final HttpServletResponse httpResponse, final boolean isDebugRequest) {
		final SAMLResponseToken token = new SAMLResponseToken();
		final String relayState = httpRequest.getParameter("RelayState");
		final DateTime now = new DateTime(DateTimeZone.UTC);
		
		if(log.isDebugEnabled()) {
			log.debug(String.format("Relay State", relayState));
		}
		
		final IdmAuditLog auditLog = new IdmAuditLog();
		auditLog.setAction(AuditAction.SAML_SP_RESPONSE_PROCESS.value());
		auditLog.addAttribute(AuditAttributeName.RELAY_STATE, relayState);
		auditLog.addAttribute(AuditAttributeName.DEBUG_REQUEST, Boolean.valueOf(isDebugRequest).toString());
		try {
			final MarshallerFactory marshallerFactory = Configuration.getMarshallerFactory();
			final Response response = SAMLDigestUtils.getSAMLResponse(httpRequest, skipURICheck);
			
			final Element marshalledResponse = marshallerFactory.getMarshaller(response).marshall(response);
			final String samlResponseString = XMLHelper.prettyPrintXML(marshalledResponse);
			auditLog.addAttribute(AuditAttributeName.SAML_RESPONSE_XML, samlResponseString);
			log.info(String.format("SAML Request: %s", samlResponseString));
			
			
			//TODO:  check ID
			if(!isDebugRequest) {
				if(response.getIssueInstant() != null) {
					final DateTime issueInstant = response.getIssueInstant().toDateTime(DateTimeZone.UTC);
					if(issueInstant.isAfter(now.plusMillis((int)samlIssueThresholdInMillis))) {
						throw new AuthenticationException(String.format("response.issueInstant.isAfter threshhold %s", samlIssueThresholdInMillis), Errors.SAML_SP_EXCEPTION);
					}
					if(issueInstant.isBefore(now.minusMillis((int)samlIssueThresholdInMillis))) {
						throw new AuthenticationException(String.format("response.issueInstant.isBefore threshhold %s", samlIssueThresholdInMillis), Errors.SAML_SP_EXCEPTION);
					}
				} else {
					throw new AuthenticationException(String.format("response.issueInstant is null"), Errors.SAML_SP_EXCEPTION);
				}
			}
			
			if(response.getIssuer() == null) {
				throw new AuthenticationException(String.format("response.issuer is null"), Errors.SAML_SP_EXCEPTION);
			}
			
			if(CollectionUtils.isEmpty(response.getAssertions())) {
				throw new AuthenticationException(String.format("response.assertions is null"), Errors.SAML_SP_EXCEPTION);
			}
			
			final Assertion assertion = response.getAssertions().get(0);
			
			final Conditions conditions = assertion.getConditions();
			if(conditions == null) {
				throw new AuthenticationException(String.format("response.assertion.conditions is null"), Errors.SAML_SP_EXCEPTION);
			}
			
			final String serviceProviderIssuer = response.getIssuer().getValue();
			SAMLServiceProvider serviceProvider = getSAMLServiceProviderByIssuer(serviceProviderIssuer);
			if(serviceProvider == null) {
				if(CollectionUtils.isNotEmpty(conditions.getAudienceRestrictions())) {
					for(final AudienceRestriction restriction : conditions.getAudienceRestrictions()) {
						if(CollectionUtils.isNotEmpty(restriction.getAudiences())) {
							for(final Audience audience : restriction.getAudiences()) {
								if(StringUtils.isNotBlank(audience.getAudienceURI())) {
									serviceProvider = getSAMLServiceProviderByIssuer(audience.getAudienceURI());
								}
								if(serviceProvider != null) {
									log.info(String.format("Found Service Provider from Audience '%s'", audience.getAudienceURI()));
									break;
								}
							}
						}
						if(serviceProvider != null) {
							break;
						}
					}
				}
			}
			
			if(serviceProvider == null) {
				throw new AuthenticationException(String.format("SAMLServiceProvider with Issuer '%s' not configured or doesn't exist", serviceProviderIssuer), Errors.SAML_SERVICE_PROVIDER_NOT_FOUND);
			}
			
			if(conditions.getNotOnOrAfter() == null) {
				throw new AuthenticationException(String.format("response.assertion.conditions.notOnOrAfter is null"), Errors.SAML_SP_EXCEPTION);
			}
			
			if(conditions.getNotBefore() == null) {
				throw new AuthenticationException(String.format("response.assertion.conditions.notBefore is null"), Errors.SAML_SP_EXCEPTION);
			}
			
			if(!isDebugRequest) {
				if(now.isAfter(conditions.getNotOnOrAfter().toDateTime(DateTimeZone.UTC).getMillis())) {
					throw new AuthenticationException(String.format("response.assertion.conditions.notOnOrAfter threshhold %s", samlIssueThresholdInMillis), Errors.SAML_SP_EXCEPTION);
				}
			
				if(now.isBefore(conditions.getNotBefore().toDateTime(DateTimeZone.UTC))) {
					throw new AuthenticationException(String.format("response.assertion.conditions.notBefore threshhold %s", samlIssueThresholdInMillis), Errors.SAML_SP_EXCEPTION);
				}
			}
			
			final Subject subject = assertion.getSubject();
			if(subject == null) {
				throw new AuthenticationException(String.format("response.assertions.subject is null"), Errors.SAML_SP_EXCEPTION);
			}
			
			final NameID nameId = subject.getNameID();
			if(nameId == null) {
				throw new AuthenticationException(String.format("response.assertions.subject.nameId is null"), Errors.SAML_SP_EXCEPTION);
			}
			
			if(nameId.getFormat() == null) {
				throw new AuthenticationException(String.format("response.assertions.subject.nameId.format is null"), Errors.SAML_SP_EXCEPTION);
			}
			
			if(serviceProvider.isSign() && !isDebugRequest) {
				Signature signature = response.getSignature();
				if(signature == null) {
					log.warn("SAML Response isn't signed - looking at assertion");
					signature = assertion.getSignature();
				}
				final BasicX509Credential credential = new BasicX509Credential();

				credential.setUsageType(UsageType.SIGNING);

			    final InputStream inStream = new ByteArrayInputStream(serviceProvider.getPublicKey());
			    final CertificateFactory cf = CertificateFactory.getInstance("X.509");
			    final X509Certificate cert = (X509Certificate)cf.generateCertificate(inStream);   
			    inStream.close();
			    credential.setEntityCertificate(cert);
			    credential.setPublicKey(cert.getPublicKey());
				
				final SignatureValidator validator = new SignatureValidator(credential);
				validator.validate(signature);
			}
			
			final String nameIdFormat = nameId.getFormat();
			final String nameIdValue = nameId.getValue();
			final String managedSysId = serviceProvider.getManagedSysId();
			
			final boolean justInTimeAuthEnabled = StringUtils.isNotBlank(serviceProvider.getJustInTimeSAMLAuthenticatorScript());
			
			String principal = null;
			switch(nameIdFormat) {
				case NameIDType.EMAIL:
					final EmailSearchBean searchBean = new EmailSearchBean();
					searchBean.setDeepCopy(false);
					searchBean.setEmailMatchToken(new SearchParam(nameIdValue, MatchType.EXACT));
					final List<EmailAddress> emailAddresses = userDataWebService.findEmailBeans(searchBean, 10, 0);
					
					/* if just-in-time auth is not enabled, and there is no match, throw an exception */
					if(!justInTimeAuthEnabled && CollectionUtils.isEmpty(emailAddresses)) {
						throw new SecurityException(String.format("Email address '%s' resulted in no results", nameIdValue));
					}
					
					/* 
					 * If any matching email address is found, try to find a login match 
					 */
					if(emailAddresses != null) {
						/* bad data check */
						if(emailAddresses.size() > 1) {
							throw new SecurityException(String.format("Email address '%s' maps to mroe than one record", nameIdValue));
						}
						
						/* bad data check */
						final LoginListResponse loginListResponse = loginServiceClient.getLoginByUser(emailAddresses.get(0).getParentId());
						if(CollectionUtils.isEmpty(loginListResponse.getPrincipalList())) {
							throw new SecurityException(String.format("Email address '%s' maps to a user with no principals", nameIdValue));
						}
						
						for(final Login login : loginListResponse.getPrincipalList()) {
							if(StringUtils.equals(login.getManagedSysId(), managedSysId)) {
								principal = login.getLogin();
								break;
							}
						}
						
						if(!justInTimeAuthEnabled && principal == null) {
							throw new SecurityException(String.format("Could not find principal '%s'", nameIdValue));
						}
					}
					break;
				default:
					principal = nameIdValue;
					
					/* none was sent */
					if(StringUtils.isBlank(principal)) {
						throw new SecurityException(String.format("No principal sent in AuthnResponse"));
					}
					break;
			}
			
			/* if a principal is sent, check to see if it maps to a user */
			LoginResponse loginResponse = null;
			if(principal != null) {
				loginResponse = loginServiceClient.getLoginByManagedSys(principal, managedSysId);
				if(!justInTimeAuthEnabled && loginResponse.isFailure()) {
					throw new SecurityException(String.format("Could not find principal '%s' using managed System '%s'", principal, managedSysId));
				}
			}
			
			/* try just-in-time auth */
			if(justInTimeAuthEnabled) {
				if(loginResponse == null || loginResponse.isFailure()) {
					final AbstractJustInTimeSAMLAuthenticator authenticator = (AbstractJustInTimeSAMLAuthenticator)scriptRunner.instantiateClass(null, serviceProvider.getJustInTimeSAMLAuthenticatorScript());
					authenticator.init(response, nameId, serviceProvider);
					User user = authenticator.createUser();
					if (provisionServiceFlag) {
						final ProvisionUser pUser = new ProvisionUser(user);
						final ProvisionUserResponse userResponse = provisionService.addUser(pUser);
						if(userResponse.isFailure()) {
							throw new AuthenticationException("Just in time provisioning failed due to the provisioning service returning a failure", 
									Errors.SAML_SECURITY_EXCEPTION);
						}
						user = userResponse.getUser();
					} else {
						final UserResponse userResponse = userDataWebService.saveUserInfo(user, null);
						if(userResponse.isFailure()) {
							throw new AuthenticationException("Just in time provisioning failed due to the provisioning service returning a failure", 
									Errors.SAML_SECURITY_EXCEPTION);
						}
						user = userResponse.getUser();
					}
					
					if(CollectionUtils.isEmpty(user.getPrincipalList())) {
						throw new SecurityException("The return value from the provisioning service did not have any principals.");
					}
					
					for(final Login login : user.getPrincipalList()) {
						if(StringUtils.equals(login.getManagedSysId(), managedSysId)) {
							principal = login.getLogin();
							break;
						}
					}
					
					loginResponse = loginServiceClient.getLoginByManagedSys(principal, managedSysId);
					if(loginResponse.isFailure()) {
						throw new SecurityException(String.format("Could not find principal '%s' using managed System '%s' AFTER Just-in-time provisioning", principal, managedSysId));
					}
				}
			}
			
			
			final Login login = loginResponse.getPrincipal();
			final org.openiam.base.ws.Response passwordResponse = loginServiceClient.decryptPassword(login.getUserId(), login.getPassword());
			if(passwordResponse.isFailure()) {
				throw new Exception("Could not decrypt password");
			}
			
			if(!isUserEntitledTo(login.getUserId(), serviceProvider)) {
				final String message = String.format("User with ID '%s' not entitled to SSO with SAML SP Provider '%s", login.getUserId(), serviceProvider.getName());
				throw new AuthenticationException(message, Errors.SAML_USER_NOT_AUTHORIZED_TO_SSO);
			}
			
			final String password = (String)passwordResponse.getResponseValue();
			
			if(!isDebugRequest) {
				loginProvider.doLogout(httpRequest, httpResponse, false);
				final LoginActionToken loginActionToken = loginProvider.getLoginActionToken(httpRequest, httpResponse, login.getLogin(), password);
				if(loginActionToken.isSuccess()) {
					cookieProvider.setAuthInfo(httpRequest, httpResponse, login.getLogin(), loginActionToken.getAuthResponse());
					final AuthStateEntity authState = new AuthStateEntity();
					authState.setToken(serviceProvider.getId());
					final AuthStateId authStateId = new AuthStateId();
					authStateId.setUserId(login.getUserId());
					authStateId.setTokenType("SAML_SP");
					authState.setId(authStateId);
					final org.openiam.base.ws.Response authStateResponse = authServiceClient.save(authState);
					if(authStateResponse.isFailure()) {
						throw new Exception(String.format("Could not save Auth State: %s", authState));
					}
				} else {
					throw new SecurityException(String.format("Exception From Login token: %s", loginActionToken.getErrCode()));
				}
			}
			
			auditLog.succeed();
		} catch(SecurityException e) {
			auditLog.setFailureReason(e.getMessage());
            auditLog.setException(e);
            auditLog.fail();
			token.setError(Errors.SAML_SECURITY_EXCEPTION, e);
			log.warn(String.format("Authencation Exception: %s", e.getMessage()));
		} catch(AuthenticationException e) {
			auditLog.setFailureReason(e.getMessage());
            auditLog.setException(e);
            auditLog.fail();
			token.setError(e.getError(), e);
			log.warn(String.format("Authencation Exception: %s", e.getMessage()));
		} catch(Throwable e) {
			auditLog.setFailureReason(e.getMessage());
            auditLog.setException(e);
            auditLog.fail();
			log.error("SAML Exception", e);
			token.setError(Errors.INTERNAL_ERROR, e);
		} finally {
			auditLogProvider.add(AuditSource.SP, httpRequest, auditLog);
		}
		return token;
	}
}
