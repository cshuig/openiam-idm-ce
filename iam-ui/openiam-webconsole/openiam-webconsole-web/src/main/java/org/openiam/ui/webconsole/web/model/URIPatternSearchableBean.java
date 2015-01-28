package org.openiam.ui.webconsole.web.model;

import org.openiam.ui.web.model.URIPatternBean;

public class URIPatternSearchableBean extends URIPatternBean{
    private String name;

    public URIPatternSearchableBean() {
    }

    public URIPatternSearchableBean(String Id, String providerId, String providerName, String pattern, boolean isPublic, String resourceName) {
        super(Id, providerId, providerName, pattern, isPublic, resourceName);
        this.name = providerName +" - "+pattern;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
