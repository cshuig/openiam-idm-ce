package org.openiam.ui.webconsole.web.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zaporozhec on 10/1/14.
 */
public class PolicyAttributesModel {
    List<PolicyAttributeBean> passwordComposition = new ArrayList<PolicyAttributeBean>();
    List<PolicyAttributeBean> passwordChangeRule = new ArrayList<PolicyAttributeBean>();
    List<PolicyAttributeBean> forgotPassword = new ArrayList<PolicyAttributeBean>();

    public List<PolicyAttributeBean> getPasswordComposition() {
        return passwordComposition;
    }

    public void setPasswordComposition(List<PolicyAttributeBean> passwordComposition) {
        this.passwordComposition = passwordComposition;
    }

    public List<PolicyAttributeBean> getPasswordChangeRule() {
        return passwordChangeRule;
    }

    public void setPasswordChangeRule(List<PolicyAttributeBean> passwordChangeRule) {
        this.passwordChangeRule = passwordChangeRule;
    }

    public List<PolicyAttributeBean> getForgotPassword() {
        return forgotPassword;
    }

    public void setForgotPassword(List<PolicyAttributeBean> forgotPassword) {
        this.forgotPassword = forgotPassword;
    }
}
