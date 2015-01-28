package org.openiam.ui.constants;

public enum ReconResourceTypeOptions {

    POLICY("Policy"), DEFAULT_IDM("Default IDM");

    private String value;

    public String getValue() {
        return value;
    }

    private ReconResourceTypeOptions(String value) {
        this.value = value;
    }

}
