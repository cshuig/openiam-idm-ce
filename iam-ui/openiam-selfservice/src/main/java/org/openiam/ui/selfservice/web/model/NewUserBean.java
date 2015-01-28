package org.openiam.ui.selfservice.web.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openiam.idm.srvc.auth.dto.Login;
import org.openiam.idm.srvc.continfo.dto.Address;
import org.openiam.idm.srvc.continfo.dto.EmailAddress;
import org.openiam.idm.srvc.continfo.dto.Phone;
import org.openiam.idm.srvc.meta.dto.PageTempate;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.idm.srvc.user.dto.UserProfileRequestModel;
import org.openiam.ui.web.util.DateFormatStr;

public class NewUserBean extends UserProfileRequestModel implements Serializable {

	private String login;
	private String startDateAsStr;
	private String endDateAsStr;
	private String birthdateAsStr;
	private List<String> roleIds;
	private List<String> groupIds;
	private List<String> organizationIds;
	private List<String> supervisorIds;
	
	public String getBirthdateAsStr() {
		return birthdateAsStr;
	}
	public void setBirthdateAsStr(String birthdateAsStr) {
		this.birthdateAsStr = birthdateAsStr;
	}
	
	
	
	public String getStartDateAsStr() {
		return startDateAsStr;
	}
	public void setStartDateAsStr(String startDateAsStr) {
		this.startDateAsStr = startDateAsStr;
	}
	public String getEndDateAsStr() {
		return endDateAsStr;
	}
	public void setEndDateAsStr(String endDateAsStr) {
		this.endDateAsStr = endDateAsStr;
	}
	public List<String> getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}
	
	public List<String> getGroupIds() {
		return groupIds;
	}
	public void setGroupIds(List<String> groupIds) {
		this.groupIds = groupIds;
	}
	public String getOrganizationAtIdx(final int idx) {
		String retVal = null;
		try {
			retVal = organizationIds.get(idx);
		} catch(Throwable e) { /* catch NPE and IndexOutofBounds */
			
		}
		return retVal;
	}
	
	public List<String> getOrganizationIds() {
		return organizationIds;
	}
	public void setOrganizationIds(List<String> organizationIds) {
		this.organizationIds = organizationIds;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public void formatDates() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DateFormatStr.getSdfDate());

			if(this.getUser().getBirthdate()!=null){
				this.birthdateAsStr = sdf.format(this.getUser().getBirthdate());
			}
			if(this.birthdateAsStr != null) {
				this.getUser().setBirthdate(sdf.parse(birthdateAsStr));
			}
			
			if(this.getUser().getStartDate()!= null) {
				this.startDateAsStr = sdf.format(this.getUser().getStartDate());
			}
			if(this.startDateAsStr != null) {
				this.getUser().setStartDate(sdf.parse(startDateAsStr));
			}
			
			if(this.getUser().getLastDate()!= null) {
				this.endDateAsStr = sdf.format(this.getUser().getLastDate());
			}
			if(this.endDateAsStr != null) {
				this.getUser().setLastDate(sdf.parse(endDateAsStr));
			}
		} catch(Throwable e) {
			
		}
    }
	public List<String> getSupervisorIds() {
		return supervisorIds;
	}
	public void setSupervisorIds(List<String> supervisorIds) {
		this.supervisorIds = supervisorIds;
	}
	
	
}
