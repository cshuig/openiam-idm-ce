package org.openiam.ui.rest.client;

import org.openiam.ui.rest.response.RestResponse;

public interface TranslateAPIClient extends RestAPIClient{
      public <TranslateResponse extends RestResponse> TranslateResponse translate(String text, String sourceLang, String targetLang) throws
              Exception;
}
