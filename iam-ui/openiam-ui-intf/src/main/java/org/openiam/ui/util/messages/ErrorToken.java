package org.openiam.ui.util.messages;

public class ErrorToken {

	private String i18nError;
	private Errors error;
    private String validationError;
	private Object[] params;
    private String message;

    public ErrorToken(){}

    public ErrorToken(final String i18nError) {
		this.i18nError = i18nError;
	}

    public ErrorToken(final Errors error) {
        this.error = error;
    }

    public ErrorToken(final Errors error, final Object[] params) {
        this.error = error;
        this.params = params;
    }

    public ErrorToken(final Errors error, final Object param) {
        this.error = error;
        this.params = new Object[1];
        this.params[0] = param;
    }

    public Errors getError() {
        return error;
    }

    public void setError(Errors error) {
        this.error = error;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public String getI18nError() {
        return i18nError;
    }

    public String getValidationError() {
        return validationError;
    }

    public void setValidationError(String validationError) {
        this.validationError = validationError;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
