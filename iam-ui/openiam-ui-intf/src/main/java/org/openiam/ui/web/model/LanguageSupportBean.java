package org.openiam.ui.web.model;

/**
 * Created by: Alexander Duckardt
 * Date: 3/21/14.
 */
public class LanguageSupportBean extends AbstractBean{
    private String resultValue;

    public LanguageSupportBean() {
        this.resultValue = "";
    }

    public LanguageSupportBean(String resultValue) {
        this.resultValue = resultValue;
    }

    public String getResultValue() {
        return resultValue;
    }

    public void setResultValue(String resultValue) {
        this.resultValue = resultValue;
    }
}
