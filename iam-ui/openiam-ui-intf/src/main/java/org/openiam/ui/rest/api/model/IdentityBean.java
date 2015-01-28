package org.openiam.ui.rest.api.model;

import org.openiam.idm.srvc.auth.dto.IdentityDto;
import org.openiam.idm.srvc.auth.dto.IdentityTypeEnum;
import org.openiam.idm.srvc.auth.dto.LoginStatusEnum;
import org.openiam.idm.srvc.auth.dto.ProvLoginStatusEnum;
import org.openiam.ui.web.model.AbstractBean;
import org.openiam.ui.web.util.DateFormatStr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IdentityBean extends AbstractBean {

    private String identity;
    private String managedSys;
    private String managedSysId;
    private LoginStatusEnum status;
    private ProvLoginStatusEnum provStatus;
    private org.openiam.idm.srvc.auth.dto.IdentityTypeEnum type;
    private String referredObjectId;
    private Date createDate;
    private String createdBy;
    private Date lastUpdate;

    public IdentityBean() {
    }

    public IdentityBean(IdentityDto identityDto) {
        setId(identityDto.getId());
        setIdentity(identityDto.getIdentity());
        setCreateDate(identityDto.getCreateDate());
        setCreatedBy(identityDto.getCreatedBy());
        setReferredObjectId(identityDto.getReferredObjectId());
        setType(identityDto.getType());
        setStatus(identityDto.getStatus());
        setProvStatus(identityDto.getProvStatus());
        setLastUpdate(identityDto.getLastUpdate());
        setManagedSysId(identityDto.getManagedSysId());
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getManagedSys() {
        return managedSys;
    }

    public void setManagedSys(String managedSys) {
        this.managedSys = managedSys;
    }

    public String getManagedSysId() {
        return managedSysId;
    }

    public void setManagedSysId(String managedSysId) {
        this.managedSysId = managedSysId;
    }

    public LoginStatusEnum getStatus() {
        return status;
    }

    public void setStatus(LoginStatusEnum status) {
        this.status = status;
    }

    public ProvLoginStatusEnum getProvStatus() {
        return provStatus;
    }

    public void setProvStatus(ProvLoginStatusEnum provStatus) {
        this.provStatus = provStatus;
    }

    public IdentityTypeEnum getType() {
        return type;
    }

    public void setType(IdentityTypeEnum type) {
        this.type = type;
    }

    public String getReferredObjectId() {
        return referredObjectId;
    }

    public void setReferredObjectId(String referredObjectId) {
        this.referredObjectId = referredObjectId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public String getCreateDateFormatted() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormatStr.getSdfDateTime());
        return createDate != null ? simpleDateFormat.format(createDate) : "";
    }

    public String getLastUpdateFormatted() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormatStr.getSdfDateTime());
        return lastUpdate != null ? simpleDateFormat.format(lastUpdate) : "";
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
