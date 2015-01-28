package org.openiam.ui.constants;

public enum ReconConfigTypeOptions {

    USER("USER"), ROLE("ROLE"), GROUP("GROUP"), ORG("ORG");

    private String value;

    public String getValue() {
        return value;
    }

    private ReconConfigTypeOptions(String value) {
        this.value = value;
    }

}
