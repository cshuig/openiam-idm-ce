package org.openiam.ui.constants;

public enum CommonFrequencyOptions {

    FIVE_MIN("0 0/5 * * * ?", "Every 5 min"), FIFTEEN_MIN("0 0/15 * * * ?",
            "Every 15 min"), SIXTY_MIN("0 * 0/1 * * ?", "Every 1 Hour"), NIGHTLY(
            "0 0 0 * * ?", "Run Nightly");

    private String value;
    private String label;

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    private CommonFrequencyOptions(String value, String label) {
        this.value = value;
        this.label = label;
    }

}
