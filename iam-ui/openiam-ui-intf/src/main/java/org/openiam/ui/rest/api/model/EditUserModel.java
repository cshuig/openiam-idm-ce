package org.openiam.ui.rest.api.model;

import org.openiam.idm.srvc.user.dto.UserStatusEnum;
import org.openiam.ui.web.util.DateFormatStr;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EditUserModel implements Serializable {
    private String id;
    private UserStatusEnum status;
    private UserStatusEnum secondaryStatus;

    private String firstName;
    private String middleInit;
    private String lastName;
    private String nickname;
    private String maidenName;
    private String suffix;
    private String birthdateAsStr;
    private Date birthdate;
    private String sex;
    private Integer showInSearch;
    private String title;
    private String jobCodeId;
    private String classification;
    private String employeeId;
    private String userTypeInd;
    private String employeeTypeId;
    private String startDateAsStr;
    private Date startDate;
    private String lastDateAsStr;
    private Date lastDate;
    private String claimDateAsStr;
    private Date claimDate;
    private String supervisorId;
    private String supervisorName;
    private String alternateContactId;
    private String alternateContactName;
    private String mailCode;
    private String costCenter;
    private String metadataTypeId;

    // these fields are used only when userWS is used directly without provision
    private String login;
    private String password;
    private String confirmPassword;
    private EmailBean email;
    private AddressBean address;
    private PhoneBean phone;
    private Boolean notifyUserViaEmail=true;
    private Boolean notifySupervisorViaEmail = false;
    private Boolean provisionOnStartDate = false;
    
    private List<String> organizationIds;
    private String roleId;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserStatusEnum getStatus() {
        return status;
    }

    public void setStatus(UserStatusEnum status) {
        this.status = status;
    }

    public UserStatusEnum getSecondaryStatus() {
        return secondaryStatus;
    }

    public void setSecondaryStatus(UserStatusEnum secondaryStatus) {
        this.secondaryStatus = secondaryStatus;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleInit() {
        return middleInit;
    }

    public void setMiddleInit(String middleInit) {
        this.middleInit = middleInit;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMaidenName() {
        return maidenName;
    }

    public void setMaidenName(String maidenName) {
        this.maidenName = maidenName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getShowInSearch() {
        return showInSearch;
    }

    public void setShowInSearch(Integer showInSearch) {
        this.showInSearch = showInSearch;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJobCodeId() {
        return jobCodeId;
    }

    public void setJobCodeId(String jobCodeId) {
        this.jobCodeId = jobCodeId;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getUserTypeInd() {
        return userTypeInd;
    }

    public void setUserTypeInd(String userTypeInd) {
        this.userTypeInd = userTypeInd;
    }

    public String getEmployeeTypeId() {
        return employeeTypeId;
    }

    public void setEmployeeTypeId(String employeeTypeId) {
        this.employeeTypeId = employeeTypeId;
    }

    public String getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(String supervisorId) {
        this.supervisorId = supervisorId;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

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

    public String getLastDateAsStr() {
        return lastDateAsStr;
    }

    public void setLastDateAsStr(String lastDateAsStr) {
        this.lastDateAsStr = lastDateAsStr;
    }

    public String getClaimDateAsStr() {
        return claimDateAsStr;
    }

    public void setClaimDateAsStr(String claimDateAsStr) {
        this.claimDateAsStr = claimDateAsStr;
    }

    public Date getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(Date claimDate) {
        this.claimDate = claimDate;
    }

    public String getMailCode() {
        return mailCode;
    }

    public void setMailCode(String mailCode) {
        this.mailCode = mailCode;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public EmailBean getEmail() {
        return email;
    }

    public void setEmail(EmailBean email) {
        this.email = email;
    }

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public PhoneBean getPhone() {
        return phone;
    }

    public void setPhone(PhoneBean phone) {
        this.phone = phone;
    }

    public Boolean getNotifyUserViaEmail() {
        return notifyUserViaEmail;
    }

    public void setNotifyUserViaEmail(Boolean notifyUserViaEmail) {
        this.notifyUserViaEmail = notifyUserViaEmail;
    }

    public Boolean getNotifySupervisorViaEmail() {
        return notifySupervisorViaEmail;
    }

    public void setNotifySupervisorViaEmail(Boolean notifySupervisorViaEmail) {
        this.notifySupervisorViaEmail = notifySupervisorViaEmail;
    }

    public Boolean getProvisionOnStartDate() {
        return provisionOnStartDate;
    }

    public void setProvisionOnStartDate(Boolean provisionOnStartDate) {
        this.provisionOnStartDate = provisionOnStartDate;
    }

    public String getAlternateContactId() {
        return alternateContactId;
    }

    public void setAlternateContactId(String alternateContactId) {
        this.alternateContactId = alternateContactId;
    }

    public String getAlternateContactName() {
        return alternateContactName;
    }

    public void setAlternateContactName(String alternateContactName) {
        this.alternateContactName = alternateContactName;
    }

    public String getMetadataTypeId() {
        return metadataTypeId;
    }

    public void setMetadataTypeId(String metadataTypeId) {
        this.metadataTypeId = metadataTypeId;
    }

    public void formatDates(){
        SimpleDateFormat sdf = new SimpleDateFormat(DateFormatStr.getSdfDate());

        if(this.birthdate!=null){
            this.birthdateAsStr = sdf.format(this.birthdate);
        }
        if(this.startDate!=null){
            this.startDateAsStr = sdf.format(this.startDate);
        }
        if(this.lastDate!=null){
            this.lastDateAsStr = sdf.format(this.lastDate);
        }
        if(this.claimDate!=null){
            this.claimDateAsStr = sdf.format(this.claimDate);
        }
    }

	public List<String> getOrganizationIds() {
		return organizationIds;
	}

	public void setOrganizationIds(List<String> organizationIds) {
		this.organizationIds = organizationIds;
	}

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
