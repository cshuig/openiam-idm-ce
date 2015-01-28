package org.openiam.ui.rest.response;

import java.io.Serializable;

public class RestResponse implements Serializable {
    private String errorCode = null;
    private String errorMsg = null;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
