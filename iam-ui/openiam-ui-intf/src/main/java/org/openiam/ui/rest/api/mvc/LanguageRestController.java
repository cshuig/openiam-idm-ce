package org.openiam.ui.rest.api.mvc;

import org.openiam.ui.rest.api.model.TranslateBean;
import org.openiam.ui.rest.client.factory.TranslateApiClientFactory;
import org.openiam.ui.rest.constant.ClientProvider;
import org.openiam.ui.rest.response.GoogleTranslateResponse;
import org.openiam.ui.rest.response.bean.GoogleTranslation;
import org.openiam.ui.web.mvc.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by: Alexander Duckardt
 * Date: 3/27/14.
 */
@Controller
public class LanguageRestController extends AbstractController {

    @Value("${org.openiam.ui.api.translate.provider}")
    private ClientProvider translateApiProvider;
    @Autowired
    private TranslateApiClientFactory translateApiClientFactory;

    @RequestMapping(value="/language/translate", method= RequestMethod.POST)
    public @ResponseBody
    TranslateBean translateLabels(final HttpServletRequest request,
                                  final @RequestBody TranslateBean translateBean) {
        TranslateBean beanResponse = new TranslateBean();
        try {

            if(translateBean.getText()!=null && !translateBean.getText().trim().isEmpty()){

                GoogleTranslateResponse translateResponse =  translateApiClientFactory.getApiClient(translateApiProvider).translate(translateBean.getText(),
                                                                                                                                    translateBean.getSourceLang(),
                                                                                                                                    translateBean.getTargetLang());

                if(translateResponse.getData()!=null && translateResponse.getData().getTranslations()!=null && !translateResponse.getData().getTranslations().isEmpty()){
                    // TODO: need to think how to make it more common ( independent from api provider)
                    for(GoogleTranslation translation: translateResponse.getData().getTranslations()){
                        beanResponse.setText(translation.getTranslatedText());
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return beanResponse;
    }
}
