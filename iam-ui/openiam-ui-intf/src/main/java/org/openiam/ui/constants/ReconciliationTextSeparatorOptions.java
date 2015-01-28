package org.openiam.ui.constants;

public enum ReconciliationTextSeparatorOptions {

    COMMA("comma"), SEMICOLON("semicolon"), TAB("tab"), SPACE("space"), ENTER(
            "enter");
    private String value;

    public String getValue() {
        return value;
    }

    private ReconciliationTextSeparatorOptions(String value) {
        this.value = value;
    }

}
