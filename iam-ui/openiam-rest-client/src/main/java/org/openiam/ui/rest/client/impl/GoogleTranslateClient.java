package org.openiam.ui.rest.client.impl;

import org.openiam.ui.rest.client.TranslateAPIClient;
import org.openiam.ui.rest.response.GoogleTranslateResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;

@Service("googleTranslateClient")
public class GoogleTranslateClient extends GoogleApiClient implements TranslateAPIClient {


    @Override
    public GoogleTranslateResponse translate(String text, String sourceLang, String targetLang) throws Exception {

        Deque<String> reqParams = new ArrayDeque<String>();
        reqParams.push("language/translate/v2");

        LinkedHashMap<String, String> queryParams= new LinkedHashMap<String, String>();
        queryParams.put("key",API_KEY);
        queryParams.put("target",targetLang);
        queryParams.put("q",text);
        queryParams.put("source",sourceLang);

        return getResponse(reqParams, queryParams, "GET", null, GoogleTranslateResponse.class);
    }


}
