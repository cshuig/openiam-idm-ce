package org.openiam.ui.rest.api.model;

public class LanguageBean extends KeyNameBean {

    private String code;
    private boolean isDefault;
    private boolean isUsed;

    public LanguageBean() {
        super();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean getIsDefault() {
        return isDefault;
    }

    public boolean getIsUsed() {
        return isUsed;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public void setIsUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

}
