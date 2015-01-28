package org.openiam.ui.rest.client;

import org.apache.log4j.Logger;
import org.openiam.ui.rest.constant.ContentType;
import org.openiam.ui.rest.processor.AbstractProcessor;
import org.openiam.ui.rest.processor.ProcessorFactory;
import org.openiam.ui.rest.response.RestResponse;
import org.openiam.ui.rest.transport.HttpRequestWrapper;
import org.openiam.ui.rest.transport.UrlWrapper;

import javax.annotation.PostConstruct;
import java.util.*;

public abstract class AbstractAPIClient {
    protected final Logger log = Logger.getLogger(this.getClass());
    protected  ContentType contentType = ContentType.Json;
    protected static final String REST_BASE_URI = "base";
    protected Map<String, String> properties;

    private AbstractProcessor processor;

    protected abstract String getContentTypeProperty();
    protected abstract String getBaseUrl();

    @PostConstruct
    public void initAPIClient() throws Exception {
        setContentType();
        setDefaultUrl(getBaseUrl());
        processor = ProcessorFactory.getInstance(this.contentType);
    }

    private void setContentType() throws Exception {
        try{
            this.contentType = ContentType.valueOf(getContentTypeProperty());
        }catch (Exception ex){
            log.error(ex.getMessage(), ex);
            throw new UnsupportedOperationException("Cannot initialized Content Type. Passed  Content Type is not found");
        }
    }

    /**
     * @return the properties
     */
    protected Map<String, String> getProperties() {
        return properties;
    }

    /**
     * @param properties
     *            the properties to set
     */
    protected void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    /**
     * Returns the value of the property with the passed name.
     *
     * @param name
     *            Name of desired property
     */
    protected String getProperty(String name) {
        return properties.get(name);
    }

    protected void setProperty(String key, String value) {
        if (properties == null) {
            properties = new HashMap<String, String>();
        }
        properties.put(key, value);
    }

    /**
     *
     * @param defaultUrl
     */
    protected void setDefaultUrl(String defaultUrl) {
        setProperty(REST_BASE_URI, defaultUrl);
    }

    protected UrlWrapper getRequestUrl(Deque<String> reqParams,
                                    LinkedHashMap<String, String> queryParams) {
        UrlWrapper requestUrl = new UrlWrapper(getProperty(REST_BASE_URI));
        while (reqParams != null && !reqParams.isEmpty()) {
            requestUrl.addPathComponent(reqParams.pop());
        }
        String key = "";
        if (queryParams != null) {
            for (Iterator<String> it = queryParams.keySet().iterator(); it.hasNext();) {
                key = it.next();
                requestUrl.addQueryStringParameter(key, queryParams.get(key));
            }
        }
        return requestUrl;
    }

    protected <ServiceResponse extends RestResponse> ServiceResponse getResponse(
            Deque<String> reqParams, LinkedHashMap<String, String> queryParams,
            String method, Object postBody, Class<ServiceResponse> responseClass)
            throws Exception {
        return processor.readResponse(getResponse(reqParams, queryParams, method, postBody), responseClass);
    }

    protected String getResponse(Deque<String> reqParams,
                              LinkedHashMap<String, String> queryParams, String method,
                              Object postBody) throws Exception {
        HttpRequestWrapper request = getRequest(reqParams, queryParams, method,
                                                postBody);
        signRequest(method, postBody, request.getUrl());
        return request.execute(addHeaders(request.getUrl()));
    }

    protected HttpRequestWrapper getRequest(Deque<String> reqParams,
                                         LinkedHashMap<String, String> queryParams, String method,
                                         Object postBody) throws Exception {
        UrlWrapper requestUrl = getRequestUrl(reqParams, queryParams);
        return getRequest(method, postBody, requestUrl);
    }

    protected HttpRequestWrapper getRequest(String method, Object postBody,
                                         UrlWrapper requestUrl) throws Exception {
        return getRequest(method, postBody, requestUrl, null);
    }

    protected HttpRequestWrapper getRequest(String method, Object postBody,
                                         UrlWrapper requestUrl, HashMap<String, String> headers)
            throws Exception {
        return processor.getRequest(method, postBody, requestUrl, headers);
    }

    /**
     * @return
     * @throws Exception
     */
    protected HashMap<String, String> addHeaders() throws Exception {
        return addHeaders(null);
    }

    /**
     * @param requestUrl
     * @return
     * @throws Exception
     */
    protected HashMap<String, String> addHeaders(UrlWrapper requestUrl) throws Exception {
        return null;
    }

    protected void signRequest(String method, Object postBody, UrlWrapper requestUrl) throws Exception {
    }

}
