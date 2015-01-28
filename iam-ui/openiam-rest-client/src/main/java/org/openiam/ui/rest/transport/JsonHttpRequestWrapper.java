package org.openiam.ui.rest.transport;

import java.util.HashMap;

class JsonHttpRequestWrapper  extends HttpRequestWrapper  {

    protected JsonHttpRequestWrapper(String method, UrlWrapper url, HashMap<String, String> headers) {
        super(method, url, headers);
    }

    protected JsonHttpRequestWrapper(String method, UrlWrapper url) {
        super(method, url);
    }

    @Override
    protected String getDefaultContentType() {
        return "application/json;charset=UTF-8";
    }
}
