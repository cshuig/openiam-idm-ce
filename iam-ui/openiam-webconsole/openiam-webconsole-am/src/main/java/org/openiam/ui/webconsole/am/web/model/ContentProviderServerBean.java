package org.openiam.ui.webconsole.am.web.model;

import org.openiam.ui.web.model.AbstractBean;

public class ContentProviderServerBean extends AbstractBean{
    private String providerId;
    private String serverURL;

    public ContentProviderServerBean() {
    }

    public ContentProviderServerBean(String id, String providerId, String serverURL) {
        setId(id);
        this.providerId = providerId;
        this.serverURL = serverURL;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getServerURL() {
        return serverURL;
    }

    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }
}
