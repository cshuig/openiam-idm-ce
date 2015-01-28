package org.openiam.ui.rest.response;

import org.openiam.ui.rest.response.bean.GoogleTranslateData;

public class GoogleTranslateResponse extends  RestResponse {
    private GoogleTranslateData data;

    public GoogleTranslateData getData() {
        return data;
    }

    public void setData(GoogleTranslateData data) {
        this.data = data;
    }
}
