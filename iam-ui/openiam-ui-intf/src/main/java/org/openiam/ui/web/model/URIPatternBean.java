package org.openiam.ui.web.model;

public class URIPatternBean extends AbstractBean {
    private String providerId;
    private String providerName;
    private String pattern;
    private boolean isPublic;
    private String resourceName;

    public URIPatternBean(){}

    public URIPatternBean(String Id, String providerId, String providerName, String pattern, boolean isPublic, String resourceName) {
    	setId(Id);
        this.providerId = providerId;
        this.providerName = providerName;
        this.pattern = pattern;
        this.isPublic = isPublic;
        this.resourceName = resourceName;
    }

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

    public boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
}
