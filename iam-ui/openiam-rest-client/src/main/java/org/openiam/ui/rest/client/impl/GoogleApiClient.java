package org.openiam.ui.rest.client.impl;

import org.openiam.ui.rest.client.AbstractAPIClient;
import org.springframework.beans.factory.annotation.Value;

public abstract class GoogleApiClient extends AbstractAPIClient{
    @Value("${org.openiam.ui.api.google.apikey}")
    protected String API_KEY;
    @Value("${org.openiam.ui.api.translate.google.contentType}")
    private String contentTypeProperty;
    @Value("${org.openiam.ui.api.google.baseurl}")
    private String baseUrl;

    protected String getBaseUrl(){
        return baseUrl;
    }

    public String getAPI_KEY() {
        return API_KEY;
    }

    public void setAPI_KEY(String API_KEY) {
        this.API_KEY = API_KEY;
    }

    protected String getContentTypeProperty(){
        return contentTypeProperty;
    }
}
