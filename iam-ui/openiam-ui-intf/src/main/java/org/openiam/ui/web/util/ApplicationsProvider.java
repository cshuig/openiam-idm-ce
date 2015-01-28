package org.openiam.ui.web.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openiam.am.srvc.dto.AuthProvider;
import org.openiam.am.srvc.dto.ContentProvider;
import org.openiam.am.srvc.searchbeans.AuthProviderSearchBean;
import org.openiam.am.srvc.searchbeans.ContentProviderSearchBean;
import org.openiam.am.srvc.ws.AuthProviderWebService;
import org.openiam.am.srvc.ws.ContentProviderWebService;
import org.openiam.authmanager.service.AuthorizationManagerWebService;
import org.openiam.idm.searchbeans.ResourceSearchBean;
import org.openiam.idm.srvc.mngsys.dto.ManagedSysDto;
import org.openiam.idm.srvc.mngsys.dto.ManagedSysSearchBean;
import org.openiam.idm.srvc.mngsys.ws.ManagedSystemWebService;
import org.openiam.idm.srvc.res.service.ResourceDataService;
import org.openiam.ui.rest.api.model.Application;
import org.openiam.ui.web.filter.OpeniamFilter;
import org.openiam.ui.web.mvc.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplicationsProvider {
	
	@Resource(name="authorizationManagerServiceClient")
	private AuthorizationManagerWebService authorizationManager;
	
	@Resource(name="resourceServiceClient")
	private ResourceDataService resourceDataService;
	
    @Resource(name="contentProviderServiceClient")
    private ContentProviderWebService contentProviderServiceClient;
	
	@Resource(name="authProviderServiceClient")
	private AuthProviderWebService authProviderServiceClient;
	
    @Resource(name="managedSysServiceClient")
    private ManagedSystemWebService managedSysServiceClient;

	public List<Application> getAppliationsForUser(final String userId, final HttpServletRequest request) {
		final List<Application> applicationList = new LinkedList<Application>();
		
		final AuthProviderSearchBean authProviderSearchBean = new AuthProviderSearchBean();
		authProviderSearchBean.setDeepCopy(false);
		final List<AuthProvider> authProviderList = 
				authProviderServiceClient.findAuthProviderBeans(authProviderSearchBean, Integer.MAX_VALUE, 0);
		if(CollectionUtils.isNotEmpty(authProviderList)) {
			for(final AuthProvider provider : authProviderList) {
				if(userId == null || authorizationManager.isUserEntitledToResource(userId, provider.getResourceId())) {
					final Application application = new Application();
					application.setId(provider.getResourceId());
					application.setName(provider.getName());
					
					final org.openiam.idm.srvc.res.dto.Resource resource = getShallowResource(provider.getResourceId(), request);
					if(resource != null && StringUtils.isNotBlank(resource.getURL())) {
						application.setUrl(resource.getURL());
						applicationList.add(application);
					}
				}
			}
		}
		
		final ContentProviderSearchBean cpSearchBean = new ContentProviderSearchBean();
		cpSearchBean.setDeepCopy(false);
		final List<ContentProvider> contentProviders = contentProviderServiceClient.findBeans(cpSearchBean, 0, Integer.MAX_VALUE);
		if(CollectionUtils.isNotEmpty(contentProviders)) {
			for(final ContentProvider provider : contentProviders) {
				if(userId == null || provider.getIsPublic() || authorizationManager.isUserEntitledToResource(userId, provider.getResourceId())) {
					if(provider.isShowOnApplicationPage()) {
						final Application application = new Application();
						application.setId(provider.getResourceId());
						application.setName(provider.getName());
				
						if(StringUtils.isNotBlank(provider.getUrl())) {
							application.setUrl(provider.getUrl());
							applicationList.add(application);
						}
					}
				}
			}
		}
		
		/*
		final ManagedSysSearchBean mngSysSearchBean = new ManagedSysSearchBean();
		mngSysSearchBean.setDeepCopy(false);
		final List<ManagedSysDto> managedSystems = managedSysServiceClient.getManagedSystems(mngSysSearchBean, Integer.MAX_VALUE, 0);
		if(CollectionUtils.isNotEmpty(managedSystems)) {
			for(final ManagedSysDto provider : managedSystems) {
				if(userId == null || authorizationManager.isUserEntitledToResource(userId, provider.getResourceId())) {
					final Application application = new Application();
					application.setId(provider.getResourceId());
					application.setName(provider.getName());
					
					final org.openiam.idm.srvc.res.dto.Resource resource = getShallowResource(provider.getResourceId(), request);
					if(resource != null) {
						application.setUrl(resource.getURL());
					}
					applicationList.add(application);
				}
			}
		}
		*/
		
		return applicationList;
	}
	
	private org.openiam.idm.srvc.res.dto.Resource getShallowResource(final String resourceId, final HttpServletRequest request) {
		final ResourceSearchBean searchBean = new ResourceSearchBean();
		searchBean.setDeepCopy(false);
		searchBean.setKey(resourceId);
		final List<org.openiam.idm.srvc.res.dto.Resource> resourceList = resourceDataService.findBeans(searchBean, 0, 1, OpeniamFilter.getCurrentLangauge(request));
		return CollectionUtils.isNotEmpty(resourceList) ? resourceList.get(0) : null;
	}
}
