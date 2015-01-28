package org.openiam.ui.rest.api.model;

import org.openiam.base.AttributeOperationEnum;
import org.openiam.idm.srvc.continfo.dto.EmailAddress;
import org.openiam.ui.web.model.AbstractBean;

public class EmailBean extends AbstractBean {
    private AttributeOperationEnum operation = AttributeOperationEnum.NO_CHANGE;
    private String email;
    private String type;
    private String typeId;
    private String description;
    private Boolean isDefault = Boolean.FALSE;
    private Boolean isActive = Boolean.TRUE;
    private String userId;

    public EmailBean(){
    }

    public EmailBean(EmailAddress email) {
        setId(email.getEmailId());
        this.email = email.getEmailAddress();
        this.type = email.getTypeDescription();
        this.typeId = email.getMetadataTypeId();
        this.description = email.getDescription();
        this.isDefault = email.getIsDefault();
        this.isActive = email.getIsActive();
    }


    public AttributeOperationEnum getOperation() {
        return operation;
    }

    public void setOperation(AttributeOperationEnum operation) {
        this.operation = operation;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
}
