package org.openiam.ui.web.util;

import org.openiam.idm.srvc.lang.dto.Language;
import org.openiam.ui.util.OpenIAMConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.util.CookieGenerator;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Created by: Alexander Duckardt
 * Date: 3/13/14.
 */
public class OpeniamCookieLocaleResolver  extends CookieGenerator implements LocaleResolver {
    private static final String COOKIE_LOCALE_NAME = "OPENIAM_LOCALE";
    private Locale defaultLocale = OpenIAMConstants.DEFAULT_LOCALE;
    @Value("${org.openiam.locale.i18n.enabled}")
    private Boolean isI18nEnabled;
    private LanguageProvider languageProvider;

    public OpeniamCookieLocaleResolver() {
        setCookieName(COOKIE_LOCALE_NAME);
    }

    public void setLanguageProvider(LanguageProvider languageProvider){
        this.languageProvider=languageProvider;
    }

    /**
     * Set a fixed Locale that this resolver will return if no cookie found.
     */
    public void setDefaultLocale(String defaultLocale) {
        if(StringUtils.hasText(defaultLocale))
            this.defaultLocale = StringUtils.parseLocaleString(defaultLocale);
    }

    /**
     * Return the fixed Locale that this resolver will return if no cookie found,
     * if any.
     */
    private Locale getDefaultLocale() {
        return this.defaultLocale;
    }

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        if (!isI18nEnabled) {
            return defaultLocale;
        }
        // Retrieve and parse cookie value.
        Cookie cookie = WebUtils.getCookie(request, getCookieName());
        if (cookie != null) {
            Locale locale = StringUtils.parseLocaleString(cookie.getValue());
            if (logger.isDebugEnabled()) {
                logger.debug("Parsed cookie value [" + cookie.getValue() + "] into locale '" + locale + "'");
            }
            if (locale != null) {
                return locale;
            }
        }
        return determineDefaultLocale(request);
    }

    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        if (locale != null) {
            addCookie(response, locale.toString());
        }
        else {
            removeCookie(response);
        }
    }

    private Locale determineDefaultLocale(HttpServletRequest request) {
        Locale defaultLocale = request.getLocale();

        if(defaultLocale!=null){
            if(languageProvider!=null){
                Language lang = languageProvider.getCurrentLanguage(defaultLocale);
                if(lang!=null && lang.getLocales()!=null && !lang.getLocales().isEmpty()){
                    return StringUtils.parseLocaleString(lang.getLocales().keySet().iterator().next());
                }
            } else{
                return defaultLocale;
            }
        }
        return getDefaultLocale();
    }
}
