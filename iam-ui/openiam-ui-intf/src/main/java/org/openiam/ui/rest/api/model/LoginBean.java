package org.openiam.ui.rest.api.model;

import org.openiam.idm.srvc.auth.dto.LoginStatusEnum;
import org.openiam.idm.srvc.auth.dto.ProvLoginStatusEnum;
import org.openiam.ui.web.model.AbstractBean;
import org.openiam.ui.web.util.DateFormatStr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginBean extends AbstractBean {
    private String login;
    private String managedSys;
    private String managedSysId;
    private String lastLogin;
    private LoginStatusEnum status;
    private ProvLoginStatusEnum provStatus;
    private boolean locked;
    private String userId;
    private Date pwdExp;
    private String pwdExpAsStr;
    private Date gracePeriod;
    private String gracePeriodAsStr;
    private Date lastUpdate;
    private String lastUpdateAsStr;


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

	public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
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

    public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getPwdExp() {
        return pwdExp;
    }

    public void setPwdExp(Date pwdExp) {
        this.pwdExp = pwdExp;
    }

    public Date getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(Date gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getPwdExpAsStr() {
        return pwdExpAsStr;
    }

    public void setPwdExpAsStr(String pwdExpAsStr) {
        this.pwdExpAsStr = pwdExpAsStr;
    }

    public String getGracePeriodAsStr() {
        return gracePeriodAsStr;
    }

    public void setGracePeriodAsStr(String gracePeriodAsStr) {
        this.gracePeriodAsStr = gracePeriodAsStr;
    }

    public String getLastUpdateAsStr() {
        return lastUpdateAsStr;
    }

    public void setLastUpdateAsStr(String lastUpdateAsStr) {
        this.lastUpdateAsStr = lastUpdateAsStr;
    }

    public void formatDates(){
        SimpleDateFormat sdf = new SimpleDateFormat(DateFormatStr.getSdfDate());

        if(this.gracePeriod!=null){
            this.gracePeriodAsStr = sdf.format(this.gracePeriod);
        }
        if(this.pwdExp!=null){
            this.pwdExpAsStr = sdf.format(this.pwdExp);
        }
        if(this.lastUpdate!=null){
            this.lastUpdateAsStr = sdf.format(this.lastUpdate);
        }
    }
    public void setDatesFromStr(){
        SimpleDateFormat sdf = new SimpleDateFormat(DateFormatStr.getSdfDate());

        if(this.gracePeriodAsStr!=null){
            try {
                this.gracePeriod = sdf.parse(this.gracePeriodAsStr);
            } catch (ParseException e) {
                this.gracePeriod = null;
            }
        }
        if(this.pwdExpAsStr!=null){
            try {
                this.pwdExp = sdf.parse(this.pwdExpAsStr);
            } catch (ParseException e) {
                this.pwdExp = null;
            }
        }
    }
}
