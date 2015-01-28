package org.openiam.ui.rest.processor;

import org.openiam.ui.rest.constant.ContentType;
import org.openiam.ui.rest.response.RestResponse;
import org.openiam.ui.rest.transport.HttpRequestWrapper;
import org.openiam.ui.rest.transport.UrlWrapper;

import java.util.HashMap;

public abstract class AbstractProcessor {
    protected  ContentType contentType = ContentType.Json;

    protected void AbstractProcessor(){
        setContentType();
    }

    public HttpRequestWrapper getRequest(String method, Object postBody, UrlWrapper requestUrl, HashMap<String, String> headers)throws Exception{

        HttpRequestWrapper request = HttpRequestWrapper.getInstance(contentType,method, requestUrl, headers);
        if (postBody != null) {
            request.setBody(processRequestBody(postBody));


        }
        return request;
    }

    protected String preProcessBody(String requestBody, Class<?> objectClass){
        throw new UnsupportedOperationException("preProcessBody is not implemented for " + this.getClass().getName());
    }

    public abstract <ServiceResponse extends RestResponse> ServiceResponse readResponse(
            String responseBody, Class<ServiceResponse> clazz) throws Exception;

    public abstract String processRequestBody(Object postBody) throws Exception;

    protected abstract void setContentType();

}
