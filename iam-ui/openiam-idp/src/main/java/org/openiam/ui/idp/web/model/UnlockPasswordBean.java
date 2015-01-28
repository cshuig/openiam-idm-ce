package org.openiam.ui.idp.web.model;

public class UnlockPasswordBean {

    private String principal;
    private String email;

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
