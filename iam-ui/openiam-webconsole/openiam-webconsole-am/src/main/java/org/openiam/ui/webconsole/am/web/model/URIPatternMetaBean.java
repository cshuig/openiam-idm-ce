package org.openiam.ui.webconsole.am.web.model;

import org.openiam.ui.web.model.AbstractBean;

public class URIPatternMetaBean extends AbstractBean {
    private String name;

    public URIPatternMetaBean(String id, String name) {
        setId(id);
        this.name = name;
    }

    public URIPatternMetaBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
