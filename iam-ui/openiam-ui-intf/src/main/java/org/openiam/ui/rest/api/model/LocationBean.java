package org.openiam.ui.rest.api.model;

import org.openiam.base.AttributeOperationEnum;
import org.openiam.idm.srvc.loc.dto.Location;
import org.openiam.ui.web.model.AbstractBean;

public class LocationBean extends AbstractBean {
    private AttributeOperationEnum operation = AttributeOperationEnum.NO_CHANGE;
    private String name;
    private String description;
    private String country;
    private String bldgNum;
    private String streetDirection;
    private String address1;
    private String address2;
    private String address3;
    private String city;
    private String state;
    private String postalCd;
    private String organizationId;
    private boolean isActive;


    private String displayDescription;


    public LocationBean(){}

    public LocationBean(Location location){
        this.setId(location.getLocationId());
        this.name = location.getName();
        this.description = location.getDescription();
        this.country = location.getCountry();
        this.bldgNum=location.getBldgNum();
        this.streetDirection = location.getStreetDirection();
        this.address1=location.getAddress1();
        this.address2=location.getAddress2();
        this.address3=location.getAddress3();
        this.city=location.getCity();
        this.state=location.getState();
        this.postalCd=location.getPostalCd();
        this.organizationId=location.getOrganizationId();
        this.isActive=location.getIsActive();

        this.displayDescription = new StringBuilder().append(this.bldgNum!=null?this.bldgNum:"").append(",")
                .append(this.country!=null?this.country:"").append(",")
                .append(this.state!=null?this.state:"").append(",")
                .append(this.city!=null?this.city:"").append(",")
                .append(this.address1!=null?this.address1:"").append(",")
                .append(this.address2!=null?this.address2:"").append(",")
                .append(this.address3!= null ? this.address3 : "").append(",")
                .append(this.postalCd!=null?this.postalCd:"").toString();
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

    public String getBldgNum() {
        return bldgNum;
    }

    public void setBldgNum(String bldgNum) {
        this.bldgNum = bldgNum;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getDisplayDescription() {
        return displayDescription;
    }

    public void setDisplayDescription(String displayDescription) {
        this.displayDescription = displayDescription;
    }

    public String getStreetDirection() {
        return streetDirection;
    }

    public void setStreetDirection(String streetDirection) {
        this.streetDirection = streetDirection;
    }
}
