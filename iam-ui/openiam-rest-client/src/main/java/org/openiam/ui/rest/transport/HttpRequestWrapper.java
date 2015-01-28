package org.openiam.ui.rest.transport;

import org.apache.http.*;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.*;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;
import org.openiam.ui.rest.constant.ContentType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;

public abstract class HttpRequestWrapper {
    private final UrlWrapper url;
    private HttpRequestBase request;

    protected HttpRequestWrapper(String method, UrlWrapper url, HashMap<String, String> headers) {
        this.url = url;
        if (method.equals("GET")) {
            this.request = new HttpGet();
        } else if (method.equals("PUT")) {
            this.request = new HttpPut();
        } else if (method.equals("POST")) {
            this.request = new HttpPost();
        } else if (method.equals("DELETE")) {
            this.request = new HttpDelete();
        }
        if (headers == null) {
            this.request.setHeader("Content-Type", getDefaultContentType());
        } else {
            for (Entry<String, String> entry : headers.entrySet()) {
                this.request.setHeader(entry.getKey(), entry.getValue());
            }
        }

    }
    protected HttpRequestWrapper(String method, UrlWrapper url) {
        this(method, url, null);
    }


    public static HttpRequestWrapper getInstance(ContentType contentType, String method, UrlWrapper url, HashMap<String, String> headers) throws Exception{
        switch (contentType){
            case Json:
                return new JsonHttpRequestWrapper(method, url, headers);
            case Xml:
                return new XmlHttpRequestWrapper(method, url, headers);
            default:
                throw new UnsupportedOperationException("Unknown content type. Cannot find Request Wrapper");
        }
    }
    public static HttpRequestWrapper getInstance(ContentType contentType, String method, UrlWrapper url) throws Exception{
        return  getInstance(contentType, method, url, null);
    }


    protected abstract String getDefaultContentType();

    public String execute() throws Exception {
        return execute(null);
    }

    @SuppressWarnings("deprecation")
    public String execute(HashMap<String, String> headers) throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setBooleanParameter(
                "http.protocol.expect-continue", false);
        httpClient.getParams().setParameter("http.protocol.content-charset",
                                            "UTF-8");
        httpClient.addRequestInterceptor(new HttpRequestInterceptor() {

            @Override
            public void process(final HttpRequest request,
                                final HttpContext context) throws HttpException, IOException {
                if (!request.containsHeader("Accept-Encoding")) {
                    request.addHeader("Accept-Encoding", "gzip");
                }
            }
        });

        httpClient.addResponseInterceptor(new HttpResponseInterceptor() {
            @Override
            public void process(final HttpResponse response,
                                final HttpContext context) throws HttpException,
                    IOException {
                HttpEntity entity = response.getEntity();
                Header ceheader = entity.getContentEncoding();
                if (ceheader != null) {
                    HeaderElement[] codecs = ceheader.getElements();
                    for (int i = 0; i < codecs.length; i++) {
                        if (codecs[i].getName().equalsIgnoreCase("gzip")) {
                            response.setEntity(new GzipDecompressingEntity(
                                    response.getEntity()));
                            return;
                        }
                    }
                }
            }
        });


        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try {
            if (headers != null) {
                for (Entry<String, String> entry : headers.entrySet()) {
                    this.request.setHeader(entry.getKey(), entry.getValue());
                }
            }

            this.request.setURI(this.url.toUri());
            // return httpClient.execute(this.request, responseHandler);
            return new String(httpClient.execute(this.request, responseHandler)
                                        .getBytes("UTF-8"));
        } catch (java.net.URISyntaxException e) {
            throw new Exception("Malformed URL: " + this.url.toString());
        }
    }

    public void setBody(Object body) throws Exception {
        String requestMethod = this.request.getMethod();

        if (requestMethod.equals("PUT") || requestMethod.equals("POST")) {
            HttpEntityEnclosingRequestBase entityRequest = (HttpEntityEnclosingRequestBase) this.request;

            if (body instanceof String) {
                StringEntity requestEntity = new StringEntity(body.toString(),
                                                              "UTF-8");
                requestEntity
                        .setContentType("application/x-www-form-urlencoded");
                entityRequest.setEntity(requestEntity);
            } else if (body instanceof File) {
                MultipartEntity reqEntity = new MultipartEntity();
                InputStreamKnownSizeBody bin = new InputStreamKnownSizeBody(
                        new FileInputStream((File) body),
                        (int) (((File) body).length()), "image/jpeg",
                        ((File) body).getName());
                reqEntity.addPart("image", bin);
                entityRequest.setEntity(reqEntity);
            }
        }
    }

    public UrlWrapper getUrl() {
        return this.url;
    }

    public String getMethod() {
        return this.request.getMethod();
    }

    // public void setAuthHeader(String userName, String password) {
    // String auth = "Basic "
    // + new String(Base64Encoder.encode((userName + ":" + password)
    // .getBytes()));
    // request.addHeader("Authorization", auth);
    // }

    static class InputStreamKnownSizeBody extends InputStreamBody {
        private final int lenght;

        public InputStreamKnownSizeBody(final InputStream in, final int lenght,
                                        final String mimeType, final String filename) {
            super(in, mimeType, filename);
            this.lenght = lenght;
        }

        @Override
        public long getContentLength() {
            return this.lenght;
        }
    }

    static class GzipDecompressingEntity extends HttpEntityWrapper {

        public GzipDecompressingEntity(final HttpEntity entity) {
            super(entity);
        }

        @Override
        public InputStream getContent() throws IOException,
                IllegalStateException {

            // the wrapped entity's getContent() decides about repeatability
            InputStream wrappedin = wrappedEntity.getContent();

            return new GZIPInputStream(wrappedin);
        }

        @Override
        public long getContentLength() {
            // length of ungzipped content is not known
            return -1;
        }

    }
}
