package org.openiam.ui.rest.transport;

import java.util.HashMap;

class XmlHttpRequestWrapper extends HttpRequestWrapper  {

    protected XmlHttpRequestWrapper(String method, UrlWrapper url, HashMap<String, String> headers) {
        super(method, url, headers);
    }

    protected XmlHttpRequestWrapper(String method, UrlWrapper url) {
        super(method, url);
    }

    @Override
    protected String getDefaultContentType() {
        return "application/xml;charset=UTF-8";
    }
}
