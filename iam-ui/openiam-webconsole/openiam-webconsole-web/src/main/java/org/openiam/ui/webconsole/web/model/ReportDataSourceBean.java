package org.openiam.ui.webconsole.web.model;

import org.openiam.ui.rest.api.model.KeyNameBean;

public class ReportDataSourceBean extends KeyNameBean {

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
