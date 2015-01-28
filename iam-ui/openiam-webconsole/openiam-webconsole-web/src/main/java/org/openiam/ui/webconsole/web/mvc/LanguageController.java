package org.openiam.ui.webconsole.web.mvc;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.searchbeans.LanguageSearchBean;
import org.openiam.idm.srvc.lang.dto.Language;
import org.openiam.idm.srvc.lang.dto.LanguageLocale;
import org.openiam.idm.srvc.lang.dto.LanguageMapping;
import org.openiam.ui.rest.api.model.LanguageBean;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.model.BeanResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LanguageController extends AbstractWebconsoleController {

    @Value("${org.openiam.ui.language.root.menu.id}")
    private String rootLanguageMenu;

    @RequestMapping(value = "/languages", method = RequestMethod.GET)
    public String search(final HttpServletRequest request, final HttpServletResponse response) {
        setMenuTree(request, rootLanguageMenu);
        return "language/languages";
    }

    @RequestMapping(value = "/languages/rest/search", method = RequestMethod.GET)
    public @ResponseBody
    BeanResponse search(final HttpServletRequest request, @RequestParam(required = false, value = "code") String code,
            final @RequestParam(required = true, value = "size") Integer size,
            final @RequestParam(required = true, value = "from") Integer from) {
        LanguageSearchBean searchBean = new LanguageSearchBean();
        searchBean.setCode(code);
        searchBean.setDeepCopy(true);
        final List<Language> results = languageWebService.findBeans(searchBean, from, size, this.getCurrentLanguage());
        Integer count = (from.intValue() == 0) ? languageWebService.count(searchBean) : null;
        return new BeanResponse(mapper.mapToList(results, LanguageBean.class), count);
    }

    @RequestMapping(value = "/languageEdit", method = RequestMethod.GET)
    public String get(final HttpServletRequest request, final HttpServletResponse response,
            @RequestParam(required = false, value = "id") String id) throws JsonGenerationException,
            JsonMappingException, IOException {
        Language language = null;

        if (StringUtils.isEmpty(id)) {
            language = new Language();
            language.setLocales(new HashMap<String, LanguageLocale>());
            language.setDisplayNameMap(new HashMap<String, LanguageMapping>());
        } else {
            LanguageSearchBean searchBean = new LanguageSearchBean();
            searchBean.setKey(id);
            List<Language> languages = languageWebService.findBeans(searchBean, -1, -1, this.getCurrentLanguage());
            if (CollectionUtils.isEmpty(languages)) {
                language = new Language();
                language.setLocales(new HashMap<String, LanguageLocale>());
                language.setDisplayNameMap(new HashMap<String, LanguageMapping>());
            } else {
                language = languages.get(0);
            }
        }
        Locale[] locales = SimpleDateFormat.getAvailableLocales();
        Map<String, Set<LanguageLocale>> localesMap = new HashMap<String, Set<LanguageLocale>>();
        for (Locale l : locales) {
            String name = l.getLanguage();
            if (localesMap.get(name) == null) {
                localesMap.put(name, new HashSet<LanguageLocale>());
            }
            if (name.equals(l.toString()))
                continue;
            LanguageLocale ll = new LanguageLocale();
            ll.setLanguageId(l.getLanguage());
            ll.setId(l.toString());
            ll.setLocale(l.getDisplayName());
            localesMap.get(name).add(ll);
        }
        request.setAttribute("isAdd", language.getId() == null);
        request.setAttribute("jsonLanguageView", jacksonMapper.writeValueAsString(localesMap));
        request.setAttribute("jsonLanguage", jacksonMapper.writeValueAsString(language));
        setMenuTree(request, rootLanguageMenu);
        return "language/edit";
    }

    @RequestMapping(value = "/languageEdit", method = RequestMethod.POST)
    public String save(final HttpServletRequest request, final HttpServletResponse response,
            @RequestBody Language language) throws Exception {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        final Response wsResponse = languageWebService.save(language);
        if (ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
            ajaxResponse.setStatus(HttpServletResponse.SC_OK);
            ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.LANGUAGE_SAVED));
            ajaxResponse
                    .setRedirectURL(String.format("languageEdit.html?id=%s", (String) wsResponse.getResponseValue()));
        } else {
            Errors error = null;
            switch (wsResponse.getErrorCode()) {
            case INTERNAL_ERROR:
                error = Errors.INTERNAL_ERROR;
                break;
            case LOCALE_ALREADY_EXISTS:
                error = Errors.LOCALE_ALREADY_EXISTS;
                break;
            case NO_DEFAULT_LANGUAGE:
                error = Errors.NO_DEFAULT_LANGUAGE;
                break;
            case DISPLAY_NAME_REQUIRED:
                error = Errors.DISPLAY_NAME_REQUIRED;
                break;
            case NAME_MISSING:
                error = Errors.LANGUAGE_NAME_REQUIRED;
                break;
            case LANGUAGE_CODE_MISSING:
                error = Errors.LANGUAGE_CODE_REQUIRED;
                break;
            default:
                break;
            }
            ajaxResponse.addError(new ErrorToken(error, wsResponse.getResponseValue()));
            ajaxResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }
}
