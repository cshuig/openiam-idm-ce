package org.openiam.ui.web.filter;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openiam.am.srvc.uriauth.dto.URIFederationResponse;
import org.openiam.am.srvc.ws.URIFederationWebService;
import org.openiam.idm.srvc.lang.dto.Language;
import org.openiam.idm.srvc.lang.service.LanguageWebService;
import org.openiam.ui.security.OpenIAMCookieProvider;
import org.openiam.ui.security.http.OpeniamHttpServletRequest;
import org.openiam.ui.security.model.XSSPatternProcessor;
import org.openiam.ui.security.model.XSSPatternRule;
import org.openiam.ui.security.model.XSSPatternWrapper;
import org.openiam.ui.util.CustomJacksonMapper;
import org.openiam.ui.util.HeaderUtils;
import org.openiam.ui.util.SpringContextProvider;
import org.openiam.ui.util.URIUtils;
import org.openiam.ui.web.util.ContentProviderCacheProvider;
import org.openiam.ui.web.util.LanguageProvider;
import org.openiam.ui.web.util.OpeniamCookieLocaleResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class OpeniamFilter implements Filter {

    private boolean isInitialized = false;
    private static Logger LOG = Logger.getLogger(OpeniamFilter.class);

    @Autowired
    private URIFederationWebService uriFederationServiceClient;

    @Autowired
    private OpenIAMCookieProvider cookieProvider;
    
    @Autowired
    @Qualifier("xssJsonResource")
    private Resource xssJsonResource;
    
    @Autowired
    private CustomJacksonMapper jacksonMapper;

    @Value("${org.openiam.locale.request.param.name}")
    private String paramName;

    @Value("${org.openiam.selfservice.password.authlevel.id}")
    private String passwordAuthLevelId;

    @Value("${org.openiam.ui.page.title.organization.name}")
    private String titleOrganizatioName;

    @Value("${org.openiam.ui.page.footer.copyright}")
    private String footerCopyright;

    @Value("${org.openiam.ui.page.footer.nav}")
    private String footerNav;


    @Value("${org.openiam.ui.page.header.copyright}")
    private String headerCopyright;

    @Value("${org.openiam.ui.page.header.nav}")
    private String headerNav;

    @Value("${org.openiam.ui.ie.compatability.header}")
    private String ieCompatabilityHeader;

    @Value("${org.openiam.locale.i18n.enabled}")
    private Boolean isI18nEnabled;

    @Autowired
//    @Qualifier("localeResolver")
    private OpeniamCookieLocaleResolver localeResolver;

    @Autowired
    protected LanguageWebService languageWebService;

    @Autowired
    protected LanguageProvider languageProvider;
    
    private XSSPatternWrapper xssPatternWrapper;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        final String requestURI = httpRequest.getRequestURI();

		/* do autowiring - has to go here - init() gets called BEFORE applicationContext is initialized */
        if (!isInitialized) {
            if (SpringContextProvider.isInitialized()) {
                synchronized (this) {
                    if (!isInitialized) {
                        SpringContextProvider.autowire(this);
                        SpringContextProvider.resolveProperties(this);
                        
                        final InputStream stream = xssJsonResource.getInputStream();
                        xssPatternWrapper = (XSSPatternWrapper)jacksonMapper.readValue(stream, XSSPatternWrapper.class);
                        isInitialized = true;
                    }
                }
            }
        }
        if (isInitialized) {
            final String userId = cookieProvider.getUserId(httpRequest);

            String newLocale = request.getParameter(this.paramName);

            Locale localeObj = localeResolver.resolveLocale(httpRequest);
            if (newLocale != null) {
//                LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
//                if (localeResolver == null) {
//                    throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
//                }
                localeObj = org.springframework.util.StringUtils.parseLocaleString(newLocale);
                localeResolver.setLocale(httpRequest, (HttpServletResponse) response, localeObj);
                ((HttpServletResponse) response).sendRedirect(httpRequest.getRequestURI() + this.getQueryString(httpRequest));
                return;
            }

            if (org.apache.commons.lang.StringUtils.isNotBlank(ieCompatabilityHeader)) {
                httpResponse.setHeader("X-UA-Compatible", ieCompatabilityHeader);
            }

            request.setAttribute("localeParam", this.paramName);
            request.setAttribute("welcomeMessage", getMessage("openiam.ui.common.welcome", localeObj));
            request.setAttribute("logoutBtn", getMessage("openiam.ui.common.logout", localeObj));
            request.setAttribute("supportMessage", getMessage("openiam.ui.common.support", localeObj));
            request.setAttribute("websiteMessage", getMessage("openiam.ui.common.website", localeObj));
            request.setAttribute("pageTitle", getMessage(getApplicationTitle(), localeObj));

            request.setAttribute("titleOrganizatioName", titleOrganizatioName);
            request.setAttribute("footerCopyright", footerCopyright);
            request.setAttribute("footerNav", footerNav);

            request.setAttribute("headerCopyright", headerCopyright);
            request.setAttribute("headerNav", headerNav);

            if (isI18nEnabled) {
                request.setAttribute("supportedLanguageList", languageProvider.getLocalizedLanguageList(localeObj));
            }
            request.setAttribute("selectedCountry", localeObj.getCountry());
            request.setAttribute("selectedLanguage", getCurrentLanguage(localeObj));

            final XSSPatternRule xssRule = xssPatternWrapper.getProcessors().get(requestURI);
            chain.doFilter(new OpeniamHttpServletRequest(httpRequest, xssRule), response);
        } else {
            throw new IOException(String.format("Could not initialize '%s'.", this.getClass().getCanonicalName()));
        }
    }

    private String getQueryString(HttpServletRequest httpRequest) {
        StringBuilder sb = new StringBuilder();
        if (httpRequest.getParameterMap() != null && !httpRequest.getParameterMap().isEmpty()) {
            sb.append("?");

            Iterator it = httpRequest.getParameterMap().keySet().iterator();
            while (it.hasNext()) {
                Object key = it.next();
                if (!this.paramName.equals(key)) {
                    String[] val = (String[]) httpRequest.getParameterMap().get(key);
                    if (val != null && val.length > 0) {
                        if (sb.length() > 1)
                            sb.append("&");
                        sb.append(key).append("=").append(val[0]);
                    }
                }
            }
        }
        return sb.toString();
    }

    protected Language getCurrentLanguage(Locale localeObj) {
        return languageProvider.getCurrentLanguage(localeObj);
    }

    private String getMessage(String key, Locale locale) {
        if (org.springframework.util.StringUtils.hasText(key)) {
            ResourceBundle rb = ResourceBundle.getBundle("messages", locale);
            if (rb != null) {
                return rb.getString(key);
            }
        }
        return "";
    }


    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    public static Language getCurrentLangauge(final HttpServletRequest request) {
        return (request != null) ? (Language) request.getAttribute("selectedLanguage") : null;
    }

    protected String getApplicationTitle() {
        return "";
    }
}
