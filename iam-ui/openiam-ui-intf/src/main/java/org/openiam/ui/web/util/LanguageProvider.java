package org.openiam.ui.web.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openiam.idm.srvc.lang.dto.Language;
import org.openiam.idm.srvc.lang.service.LanguageWebService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component("languageProvider")
public class LanguageProvider implements InitializingBean {
	
    @Resource(name="languageServiceClient")
    protected LanguageWebService languageWebService;
	
	private static Logger LOG = Logger.getLogger(LanguageProvider.class);
	
	private Map<String, Language> languageMap = new HashMap<String, Language>();
	private Language defaultLanguage = null;

	@Override
	public void afterPropertiesSet() throws Exception {
		try {
			sweep();
		} catch(Throwable e) {
			/* in case ESB not started */
			LOG.warn("Could not load Lanagues.. - will retry on next hit, or refesh interval...");
		}
	}

	public void sweep() {
		LOG.debug("Sweeping Language provider...");
		final List<Language> languageList = languageWebService.getUsedLanguages(null);
		if(CollectionUtils.isEmpty(languageList)) {
			throw new RuntimeException("There are no languages with the 'used' flag marked as 'true'.");
		}
        Set<String> inactiveKeys = new HashSet<>(languageMap.keySet());
		for(final Language language : languageList) {
            final String key = StringUtils.lowerCase(language.getLanguageCode());
            languageMap.put(key, language);
            inactiveKeys.remove(key);
			if(language.getIsDefault()) {
				defaultLanguage = language;
			}
		}
        languageMap.keySet().removeAll(inactiveKeys);
        LOG.debug("Sweeping Language provider... - DONE! ");
	}
	
	public Language getCurrentLanguage(final HttpServletRequest request, final LocaleResolver localeResolver) {
		return getCurrentLanguage(localeResolver.resolveLocale(request));
	}

    public Language getCurrentLanguage(Locale locale) {
        Language language = languageMap.get(StringUtils.lowerCase(locale.getLanguage()));
        return (language != null) ? language : getDefaultLanguage();
    }

    public Language getDefaultLanguage(){
        return defaultLanguage;
    }

    public List<Language> getLocalizedLanguageList(final HttpServletRequest request, final LocaleResolver localeResolver){
        return getLocalizedLanguageList(localeResolver.resolveLocale(request));
    }

    public List<Language> getLocalizedLanguageList(Locale locale){
        Language currentLang = getCurrentLanguage(locale);
        List<Language> result = new ArrayList<Language>();
        if(languageMap!=null){
            for(String key: languageMap.keySet()){
                Language lang = languageMap.get(key);

                if(lang!=null){
                    try {
                        Language clonedLang = lang.clone();
                        if(clonedLang.getDisplayNameMap()!=null && clonedLang.getDisplayNameMap().get(currentLang.getId())!=null){
                            clonedLang.setName(lang.getDisplayNameMap().get(currentLang.getId()).getValue());
                            result.add(clonedLang);
                        }
                    } catch (CloneNotSupportedException e) {
                        LOG.error(e.getMessage(), e);
                    }
                }
            }
        }
        return result;
    }
}
