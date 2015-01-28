package org.openiam.ui.webconsole.web.model;

import org.openiam.ui.web.model.AbstractBean;

public class PageTemplatePatternBean extends AbstractBean {
    private String providerId;
    private String providerName;
    private String pattern;

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
