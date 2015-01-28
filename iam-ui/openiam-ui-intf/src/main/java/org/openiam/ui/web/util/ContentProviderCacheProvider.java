package org.openiam.ui.web.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openiam.am.srvc.dto.AuthLevel;
import org.openiam.am.srvc.dto.AuthLevelGroupingContentProviderXref;
import org.openiam.am.srvc.dto.AuthLevelGroupingURIPatternXref;
import org.openiam.am.srvc.dto.ContentProvider;
import org.openiam.am.srvc.dto.URIPattern;
import org.openiam.am.srvc.searchbeans.ContentProviderSearchBean;
import org.openiam.am.srvc.ws.ContentProviderWebService;
import org.openiam.idm.searchbeans.UIThemeSearchBean;
import org.openiam.idm.srvc.ui.theme.UIThemeWebService;
import org.openiam.idm.srvc.ui.theme.dto.UITheme;
import org.openiam.ui.util.HeaderUtils;
import org.openiam.ui.web.filter.ContentProviderFilter;
import org.openiam.ui.web.filter.OpeniamFilter;
import org.openiam.ui.web.model.ServerManagerWrapper;
import org.openid4java.server.ServerManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("contentProviderProvider")
public class ContentProviderCacheProvider implements InitializingBean {
	

	@Resource(name="contentProviderServiceClient")
	private ContentProviderWebService contentProviderServiceClient;
	
	@Resource(name="uiThemeClient")
	private UIThemeWebService uiThemeClient;
	
	private static Logger LOG = Logger.getLogger(ContentProviderCacheProvider.class);
	
	//private Map<String, AuthLevel> authLevelMap = new HashMap<String, AuthLevel>();
	private Map<String, UITheme> uriPattern2ThemeMap = new HashMap<String, UITheme>();
	
	private Map<String, ContentProvider> cpMap = new HashMap<String, ContentProvider>();
	
	private Map<String, ServerManagerWrapper> serverManagerMap = new ConcurrentHashMap<String, ServerManagerWrapper>();
	
	private Set<String> openURIPatterns = new HashSet<>();
	
	@Value("${org.openiam.openid.url.entrypoint}")
	private String openidURL;
	
	@Value("${org.openiam.content.provider.auth.level.none}")
	private String groupingWithNoAuthentication;
	
	public void sweep() {
		LOG.debug("Sweeping Content Provider provider...");
		/*
		final List<AuthLevel> authLevelList = contentProviderServiceClient.getAuthLevelList();
		final Map<String, AuthLevel> tempAuthLevelMap = new HashMap<String, AuthLevel>();
		if(CollectionUtils.isNotEmpty(authLevelList)) {
			for(final AuthLevel level : authLevelList) {
				tempAuthLevelMap.put(level.getId(), level);
			}
		}
		*/
		
		final Set<String> tempOpenURIPatterns = new HashSet<>();
		final Map<String, UITheme> tempUIThemeMap = new HashMap<String, UITheme>();
		final UIThemeSearchBean uiThemeSearchBean = new UIThemeSearchBean();
		uiThemeSearchBean.setDeepCopy(false);
		final List<UITheme> uiThemeList = uiThemeClient.findBeans(uiThemeSearchBean, 0, Integer.MAX_VALUE);
		if(CollectionUtils.isNotEmpty(uiThemeList)) {
			for(final UITheme theme : uiThemeList) {
				tempUIThemeMap.put(theme.getId(), theme);
			}
		}
		
		final Map<String, UITheme> tempUriPattern2ThemeMap = new HashMap<String, UITheme>();
		final Map<String, ContentProvider> tempCPMap = new HashMap<String, ContentProvider>();
		
		final ContentProviderSearchBean contentProviderSearchBean = new ContentProviderSearchBean();
		contentProviderSearchBean.setDeepCopy(true);
		final List<ContentProvider> cpList = contentProviderServiceClient.findBeans(contentProviderSearchBean, 0, Integer.MAX_VALUE);
		if(CollectionUtils.isNotEmpty(cpList)) {
			for(final ContentProvider provider : cpList) {
				if(!serverManagerMap.containsKey(provider.getId())) {
					serverManagerMap.put(provider.getId(), new ServerManagerWrapper(provider, openidURL));
				}
				final ServerManagerWrapper wrapper = serverManagerMap.get(provider.getId());
				if(wrapper.isModified(provider)) {
					serverManagerMap.put(provider.getId(), new ServerManagerWrapper(provider, openidURL));
				}
				
				tempCPMap.put(provider.getId(), provider);
				if(CollectionUtils.isNotEmpty(provider.getPatternSet())) {
					for(final URIPattern pattern : provider.getPatternSet()) {
						final String themeId = StringUtils.isNotEmpty(pattern.getThemeId()) ?  pattern.getThemeId() : provider.getThemeId();
						if(themeId != null) {
							final UITheme theme = tempUIThemeMap.get(themeId);
							if(theme != null) {
								tempUriPattern2ThemeMap.put(pattern.getId(), theme);
							}
						}
						
						if(CollectionUtils.isNotEmpty(pattern.getGroupingXrefs())) {
							for(final AuthLevelGroupingURIPatternXref xref : pattern.getGroupingXrefs()) {
								if(StringUtils.equals(xref.getId().getGroupingId(), groupingWithNoAuthentication)) {
									tempOpenURIPatterns.add(pattern.getId());
									break;
								}
							}
						} else if(CollectionUtils.isNotEmpty(provider.getGroupingXrefs())) {
							for(final AuthLevelGroupingContentProviderXref xref : provider.getGroupingXrefs()) {
								if(StringUtils.equals(xref.getId().getGroupingId(), groupingWithNoAuthentication)) {
									tempOpenURIPatterns.add(pattern.getId());
									break;
								}
							}
						}
					}
				}
			}
		}
		
		openURIPatterns = tempOpenURIPatterns;
		cpMap = tempCPMap;
		uriPattern2ThemeMap = tempUriPattern2ThemeMap;
		//authLevelMap = tempAuthLevelMap;
		LOG.debug("Done sweeping Content Provider provider...");
	}
	
	/*
	public AuthLevel getCachedAuthLevel(final String id) {
		return authLevelMap.get(id);
	}
	*/
	
	public boolean patternRequiresAuthentiation(final String patternId) {
		return (patternId != null) ? !openURIPatterns.contains(patternId) : true;
	}
	
	public ContentProvider getCachedContentProvider(final HttpServletRequest request) {
		final String cpId = ContentProviderFilter.getContentProviderForRequest(request);
		final ContentProvider cp = (cpId != null) ? cpMap.get(cpId) : null;
		return cp;
	}
	
	public ServerManager getServerManager(final HttpServletRequest request) {
		final String cpId = ContentProviderFilter.getContentProviderForRequest(request);
		final ServerManagerWrapper wrapper = (cpId != null) ? serverManagerMap.get(cpId) : null;
		return (wrapper != null) ? wrapper.getServerManager(request) : null;
	}
	
	public String getOverrideStylesheetForPatternId(final String patternId) {
		String stylesheet = null;
		if(patternId != null) {
			final UITheme theme = uriPattern2ThemeMap.get(patternId);
			if(theme != null) {
				stylesheet = theme.getUrl();
			}
		}
		return stylesheet;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		try {
			sweep();
		} catch(Throwable e) {
			/* in case ESB not started */
			LOG.warn("Could not load Conent Providers Providers - will retry on next hit, or refesh interval...");
		}
	}
}
