package org.openiam.ui.web.mvc;

import org.openiam.ui.util.LanguageSupport;
import org.openiam.ui.web.model.LanguageSupportBean;
import org.openiam.ui.web.util.OpeniamCookieLocaleResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by: Alexander Duckardt
 * Date: 3/21/14.
 */
@Controller
public class LanguageSupportController extends AbstractController {

    @Value("${org.openiam.language.support.script.base.name}")
    private String BASE_NAME;

    @Autowired
    @Qualifier("localeResolver")
    private OpeniamCookieLocaleResolver localeResolver;

    @RequestMapping(value="/language/languageSupport", method= RequestMethod.GET)
    public @ResponseBody
    LanguageSupportBean getLanguageSupport(final HttpServletRequest request,
                                           @RequestParam(value = "methodName", required = true) String methodName,
                                           @RequestParam(value = "word", required = true) String word) {
        String result = "";
        try {
            LanguageSupport languageSupport= (LanguageSupport) scriptRunner.instantiateClass(null, buildClassName(request));

            switch (methodName){
                case "addArticle":
                    result = languageSupport.addArticle(word);
                    break;
            }
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
        }
        return new LanguageSupportBean(result);
    }

    private String buildClassName(final HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        return sb.append(BASE_NAME)
                 .append(localeResolver.resolveLocale(request).getLanguage().toUpperCase())
                 .append(".groovy").toString();
    }

}
