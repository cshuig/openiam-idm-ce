package org.openiam.ui.webconsole.web.model;

import org.openiam.idm.srvc.policy.dto.ITPolicyApproveType;

public class ITPolicyBean {

    private String policyId;
    private boolean active = false;
    private ITPolicyApproveType approveType = ITPolicyApproveType.ONCE;
    private String policyContent;
    private String confirmation = "I have read and accept information above";

    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ITPolicyApproveType getApproveType() {
        return approveType;
    }

    public void setApproveType(ITPolicyApproveType approveType) {
        this.approveType = approveType;
    }

    public String getPolicyContent() {
        return policyContent;
    }

    public void setPolicyContent(String policyContent) {
        this.policyContent = policyContent;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }
}
