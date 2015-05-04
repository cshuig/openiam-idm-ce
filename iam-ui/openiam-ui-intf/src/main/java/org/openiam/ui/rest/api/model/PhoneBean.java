package org.openiam.ui.rest.api.model;

import org.openiam.base.AttributeOperationEnum;
import org.openiam.idm.srvc.continfo.dto.Phone;
import org.openiam.ui.web.model.AbstractBean;

public class PhoneBean extends AbstractBean {
    private AttributeOperationEnum operation = AttributeOperationEnum.NO_CHANGE;

    private boolean isActive = true;
    private boolean isDefault = false;

    private String type;
    private String typeId;
    private String areaCd;
    private String phoneExt;
    private String phoneNbr;
    private String countryCd;

    private String description;
    private String userId;

    public PhoneBean(){}

    public PhoneBean(Phone phone){
    	setId(phone.getPhoneId());
        this.isActive = phone.getIsActive();
        this.isDefault = phone.getIsDefault();
        this.type = phone.getTypeDescription();
        this.typeId = phone.getMetadataTypeId();
        this.areaCd = phone.getAreaCd();
        this.phoneExt = phone.getPhoneExt();
        this.phoneNbr = phone.getPhoneNbr();
        this.countryCd = phone.getCountryCd();

        StringBuilder  str= new StringBuilder();

        if(this.countryCd!=null && !this.countryCd.trim().isEmpty()){
            str.append(this.countryCd);
        }


        str.append("(").append(this.areaCd).append(") ")
                                              .append(this.phoneNbr);
        if(this.phoneExt!=null && !this.phoneExt.trim().isEmpty()){
         str.append(" EXT: ")
                    .append(this.phoneExt);
        }

        this.description = str.toString();
    }

    public AttributeOperationEnum getOperation() {
        return operation;
    }

    public void setOperation(AttributeOperationEnum operation) {
        this.operation = operation;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAreaCd() {
        return areaCd;
    }

    public void setAreaCd(String areaCd) {
        this.areaCd = areaCd;
    }

    public String getPhoneExt() {
        return phoneExt;
    }

    public void setPhoneExt(String phoneExt) {
        this.phoneExt = phoneExt;
    }

    public String getPhoneNbr() {
        return phoneNbr;
    }

    public void setPhoneNbr(String phoneNbr) {
        this.phoneNbr = phoneNbr;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

	public String getCountryCd() {
		return countryCd;
	}

	public void setCountryCd(String countryCd) {
		this.countryCd = countryCd;
	}
    
    
}
