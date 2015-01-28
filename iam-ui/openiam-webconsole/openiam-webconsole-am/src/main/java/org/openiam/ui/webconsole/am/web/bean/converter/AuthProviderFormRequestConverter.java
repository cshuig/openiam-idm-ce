package org.openiam.ui.webconsole.am.web.bean.converter;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.openiam.am.srvc.constants.AuthAttributeDataType;
import org.openiam.am.srvc.dto.AuthAttribute;
import org.openiam.am.srvc.dto.AuthProvider;
import org.openiam.am.srvc.dto.AuthProviderAttribute;
import org.openiam.am.srvc.searchbeans.AuthAttributeSearchBean;
import org.openiam.am.srvc.searchbeans.AuthProviderSearchBean;
import org.openiam.am.srvc.ws.AuthProviderWebService;
import org.openiam.am.srvc.ws.AuthResourceAttributeWebService;
import org.openiam.ui.bean.converter.BeanConverter;
import org.openiam.ui.webconsole.am.web.model.AuthProviderFormRequest;
import org.springframework.stereotype.Component;

@Component
public class AuthProviderFormRequestConverter implements BeanConverter<AuthProvider, AuthProviderFormRequest> {

	private static final String VALUE_DELIMITER = ",";
	
	@Resource(name="authAttributeServiceClient")
	private AuthResourceAttributeWebService authAttributeServiceClient;
	
	@Resource(name="authProviderServiceClient")
	private AuthProviderWebService authProviderServiceClient;
	
	@Override
	public AuthProvider convert(final AuthProviderFormRequest request) {
		AuthProvider provider = null;
		if(request != null) {
			final String providerId = StringUtils.trimToNull(request.getProviderId());
			
			final AuthProvider originalProvider = getAuthProvider(providerId);
			
			/* set common attributes */
			provider = new AuthProvider();
			provider.setProviderId(providerId);
			provider.setName(StringUtils.trimToNull(request.getName()));
			provider.setManagedSysId(StringUtils.trimToNull(request.getManagedSysId()));
			provider.setDescription(StringUtils.trimToNull(request.getDescription()));
			provider.setProviderType(request.getProviderType());
			provider.setSignRequest(request.isSignRequest());
			
			/* set resource and keys */
			final String resourceId = StringUtils.trimToNull(request.getResourceId());
			final org.openiam.idm.srvc.res.dto.Resource resource = new org.openiam.idm.srvc.res.dto.Resource();
			resource.setId(resourceId);
			resource.setURL(StringUtils.trimToNull(request.getApplicationURL()));
			provider.setResource(resource);
			provider.setResourceId(resourceId);
			
			if(request.isSignRequest()) {
				if(request.getPrivateKey() != null && request.getPrivateKey().getBytes().length > 0) {
					provider.setPrivateKey(request.getPrivateKey().getBytes());
				} else if(!request.isClearPrivateKey() && originalProvider != null) {
					provider.setPrivateKey(originalProvider.getPrivateKey());
				}
				
				
				if(request.getPublicKey() != null && request.getPublicKey().getBytes().length > 0) {
					provider.setPublicKey(request.getPublicKey().getBytes());
				} else if(!request.isClearPublicKey() && originalProvider != null) {
					provider.setPublicKey(originalProvider.getPublicKey());
				}
			}
			
			final Set<AuthProviderAttribute> providerAttributeSet = new HashSet<AuthProviderAttribute>();
			if(MapUtils.isNotEmpty(request.getAttributeMap())) {
				for(final String attributeId : request.getAttributeMap().keySet()) {
					final String value = request.getAttributeMap().get(attributeId);
					if(StringUtils.isNotBlank(value)) {
						final AuthAttributeSearchBean searchBean = new AuthAttributeSearchBean();
						searchBean.setKey(attributeId);
						final List<AuthAttribute> authAttributeList = authProviderServiceClient.findAuthAttributeBeans(searchBean, 1, 0);
						if(CollectionUtils.isNotEmpty(authAttributeList)) {
							final AuthAttribute authAttribute = authAttributeList.get(0);
						
							/* set value and value types */
							final AuthProviderAttribute attribute = new AuthProviderAttribute();
							attribute.setAttributeId(attributeId);
							
							/* if it's a list value, remove empty Strings, and rejoin using the delimiter */
							if(authAttribute.getDataType() == AuthAttributeDataType.listValue) {
								final String finalValue = rejoinListValue(value, VALUE_DELIMITER);
								
								/* just ignore if the input was bad */
								if(finalValue == null) {
									continue;
								}
								
								attribute.setDataType(AuthAttributeDataType.listValue);
								attribute.setValue(finalValue.toString());
							} else {
								attribute.setDataType(AuthAttributeDataType.singleValue);
								attribute.setValue(value);
							}
							attribute.setProviderId(providerId);
						
							/* set name */
							final String attributeName = authAttribute.getAttributeName();
							attribute.setAttributeName(attributeName);
							
							/* set original ID of attribute, if it existed */
							if(originalProvider != null && originalProvider.getProviderAttributeMap() != null) {
								final AuthProviderAttribute previousAttribute = originalProvider.getProviderAttributeMap().get(attributeName);
								if(previousAttribute != null) {
									attribute.setProviderAttributeId(previousAttribute.getProviderAttributeId());
								}
							}
							
							providerAttributeSet.add(attribute);
						}
					}
				}
			}
			
			
			provider.setProviderAttributeSet(providerAttributeSet);
			if(originalProvider != null) {
				provider.setResourceAttributeMap(originalProvider.getResourceAttributeMap());
			}
		}
		return provider;
	}


	private String rejoinListValue(final String value, final String delimiter) {
		String retVal = null;
		final String[] tokenizedValue = StringUtils.split(value, VALUE_DELIMITER);
		final List<String> splitValue = new LinkedList<String>();
		for(final String s : tokenizedValue) {
			if(StringUtils.isNotEmpty(s)) {
				splitValue.add(s);
			}
		}
		
		/* if there's no value, just continue looping over the next attribute */
		if(CollectionUtils.isNotEmpty(splitValue)) {
			final StringBuilder finalValue = new StringBuilder();
			for(int i = 0; i < splitValue.size(); i++) {
				finalValue.append(splitValue.get(i));
				if(i < splitValue.size() - 1) {
					finalValue.append(VALUE_DELIMITER);
				}
			}
			retVal = finalValue.toString();
		}
		return retVal;
	}
	
	private AuthProvider getAuthProvider(final String providerId) {
		final AuthProviderSearchBean searchBean = new AuthProviderSearchBean();
		searchBean.setDeepCopy(true);
		searchBean.setKey(providerId);
		final List<AuthProvider> authProvider = (providerId != null) ? authProviderServiceClient.findAuthProviderBeans(searchBean, Integer.MAX_VALUE, 0) : null;
		return (CollectionUtils.isNotEmpty(authProvider)) ? authProvider.get(0) : null;
	}
}
