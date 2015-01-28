package org.openiam.ui.constants;

public enum CommonStatusOptions {

    ACTIVE("ACTIVE"), INACTIVE("INACTIVE");

    private String value;

    public String getValue() {
        return value;
    }

    private CommonStatusOptions(String value) {
        this.value = value;
    }

}
