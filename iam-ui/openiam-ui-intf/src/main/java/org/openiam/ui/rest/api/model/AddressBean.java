package org.openiam.ui.rest.api.model;

import org.apache.commons.lang.StringUtils;
import org.openiam.base.AttributeOperationEnum;
import org.openiam.idm.srvc.continfo.dto.Address;
import org.openiam.ui.web.model.AbstractBean;

public class AddressBean extends AbstractBean {
    private AttributeOperationEnum operation = AttributeOperationEnum.NO_CHANGE;
    private boolean isActive;
    private boolean isDefault;

    private String bldgNumber;
    private String address1;
    private String address2;
    private String city;
    private String postalCd;
    private String state;
    private String type;
    private String typeId;
    private String userId;
    private String country;

    private String description;

    public AddressBean() {
    }

    public AddressBean(Address address) {
        setId(address.getAddressId());

        this.isActive = address.getIsActive();
        this.isDefault = address.getIsDefault();

        this.bldgNumber = address.getBldgNumber();
        this.address1 = address.getAddress1();
        this.address2 = address.getAddress2();
        this.city = address.getCity();
        this.postalCd = address.getPostalCd();
        this.state = address.getState();
        this.type = address.getTypeDescription();
        this.typeId = address.getMetadataTypeId();
        this.userId = address.getParentId();
        this.country = address.getCountry();
        this.description = address.getDescription();
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

    public String getBldgNumber() {
        return bldgNumber;
    }

    public void setBldgNumber(String bldgNumber) {
        this.bldgNumber = bldgNumber;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCd() {
        return postalCd;
    }

    public void setPostalCd(String postalCd) {
        this.postalCd = postalCd;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String label) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDescription() {
        StringBuilder builder = new StringBuilder();
        appendAddressLine(builder, bldgNumber);
        appendAddressLine(builder, address1);
        appendAddressLine(builder, address2);
        appendAddressLine(builder, city);
        appendAddressLine(builder, state);
        appendAddressLine(builder, country);
        appendAddressLine(builder, postalCd);
        description = builder.toString();
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    private void appendAddressLine(StringBuilder builder, final String line) {
        if (StringUtils.isNotBlank(line)) {
            if (builder.length() > 0) {
                builder.append(", ");
            }
            builder.append(line.trim());
        }
    }
}
