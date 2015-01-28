package org.openiam.ui.rest.transport;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * An object which represents a simple URL. Once an object is instantiated, path
 * components and parameters can be added. Once all of the elements are in
 * place, the object can be serialized into a string. This class is used by
 * internal classes and not by clients directly.
 *
 * @author Alexander Dukkadrt
 *
 */
public class UrlWrapper {
    private String base;
    private List<String> components;
    private LinkedHashMap<String, String> queryStringParameters;

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public List<String> getComponents() {
        return components;
    }

    public void setComponents(List<String> components) {
        this.components = components;
    }

    public LinkedHashMap<String, String> getQueryStringParameters() {
        return queryStringParameters;
    }

    public void setQueryStringParameters(
            LinkedHashMap<String, String> queryStringParameters) {
        this.queryStringParameters = queryStringParameters;
    }

    public UrlWrapper(String base) {
        this.base = base;
        this.components = new Vector<String>();
        this.queryStringParameters = new LinkedHashMap<String, String>();
    }

    /**
     * Adds passed String to the path component queue.
     *
     * @param component
     *            Path component to add
     */
    public void addPathComponent(String component) {
        components.add(component);
    }

    /**
     * Creates a new entry in queryStringParameters Map with the passed key and
     * value; used for adding URL parameters such as oauth_signature and the
     * various other OAuth parameters that are required in order to submit a
     * signed request.
     *
     * @param key
     *            Parameter name
     * @param value
     *            Parameter value
     */
    public void addQueryStringParameter(String key, String value) {
        try {
            queryStringParameters.put(URLEncoder.encode(key, "UTF-8"),
                                      URLEncoder.encode(value, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // "UTF-8" is a supported encoding so this exception should never be
            // thrown
        }
    }

    /**
     * Returns a String representing the serialized URL including the base
     * followed by any path components added to the path component queue and,
     * last but not least, appending any query string parameters as name-value
     * pairs after the full path.
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(this.base);
        for (String pathComponent : this.components) {
            if (s.charAt(s.length() - 1) != '/') {
                s.append("/");
            }
            s.append(pathComponent);
        }

        String connector = "?";
        for (Map.Entry<String, String> e : this.queryStringParameters
                .entrySet()) {
            s.append(connector);
            s.append(e.getKey());
            s.append("=");
            s.append(e.getValue());
            connector = "&";
        }
        return s.toString();
    }

    public URI toUri() throws URISyntaxException {
        return new URI(this.toString());
    }
}
