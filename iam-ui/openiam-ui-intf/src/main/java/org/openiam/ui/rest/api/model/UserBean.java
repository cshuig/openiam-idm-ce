package org.openiam.ui.rest.api.model;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openiam.base.ws.SortParam;
import org.openiam.idm.srvc.continfo.dto.Phone;
import org.openiam.idm.srvc.org.domain.OrganizationEntity;
import org.openiam.idm.srvc.org.dto.Organization;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.idm.srvc.user.dto.UserStatusEnum;
import org.openiam.ui.web.model.AbstractBean;
import org.openiam.ui.web.util.DateFormatStr;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UserBean extends AbstractBean {
    private static SimpleDateFormat sdf;
    private static String organizationTypeId;
    private static String departmentTypeId;
    private static List<String> fullNameComposeOrderList;


	private String name;
	private String phone;
	private String email;
	private UserStatusEnum userStatus;
	private UserStatusEnum accountStatus;
    private String principal;
    private String organization;
    private String department;
    private String title;
    private String employeeId;
    private String startDate;

	public UserBean() {

	}

    public static void setOrganizationTypeId(String organizationTypeId) {
        UserBean.organizationTypeId = organizationTypeId;
    }

    public static void setDepartmentTypeId(String departmentTypeId) {
        UserBean.departmentTypeId = departmentTypeId;
    }

    public static void setFullNameComposeOrderList(List<String> fullNameComposeOrderList) {
        UserBean.fullNameComposeOrderList = fullNameComposeOrderList;
    }

    public static void setSDF(SimpleDateFormat sdf) {
        UserBean.sdf = sdf;
    }

    public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserStatusEnum getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatusEnum userStatus) {
		this.userStatus = userStatus;
	}

	public UserStatusEnum getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(UserStatusEnum accountStatus) {
		this.accountStatus = accountStatus;
	}

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }


    public static UserBean getInstance(User user){
        UserBean bean = new UserBean();
        bean.setId(user.getId());

        StringBuilder sb = new StringBuilder();
        if(CollectionUtils.isNotEmpty(fullNameComposeOrderList)){
            for(String field: fullNameComposeOrderList){
                if(sb.length()>0)
                    sb.append(" ");

                switch (field) {
                    case "firstName":
                        sb.append( (StringUtils.isNotBlank(user.getFirstName()))? user.getFirstName():"");
                        break;
                    case "lastName":
                        sb.append( (StringUtils.isNotBlank(user.getLastName()))?user.getLastName():"");
                        break;
                    case "maidenName":
                        sb.append( (StringUtils.isNotBlank(user.getMaidenName()))?user.getMaidenName():"");
                        break;
                    case "nickname":
                        sb.append( (StringUtils.isNotBlank(user.getNickname()))? user.getNickname():"");
                        break;
                    case "middleInit":
                        sb.append( (StringUtils.isNotBlank(user.getMiddleInit()))? user.getMiddleInit():"");
                        break;
                    case "prefix":
                        sb.append( (StringUtils.isNotBlank(user.getPrefix()))? user.getPrefix():"");
                        break;
                    case "suffix":
                        sb.append( (StringUtils.isNotBlank(user.getSuffix()))? user.getSuffix():"");
                        break;
                }
            }
        } else{
            sb.append( (StringUtils.isNotBlank(user.getFirstName()))? user.getFirstName():"");
            if(sb.length()>0)
                sb.append(" ");
            sb.append( (StringUtils.isNotBlank(user.getLastName()))?user.getLastName():"");
        }
        bean.setName(sb.toString());

        Phone defaultPhone = user.getDefaultPhone();
        bean.setPhone("");
        if(defaultPhone!=null){
            StringBuilder phoneBuilder = new StringBuilder();

            if(StringUtils.isNotBlank(defaultPhone.getCountryCd())) {
                phoneBuilder.append(String.format("%s ", defaultPhone.getCountryCd()));
            }
            if(StringUtils.isNotBlank(defaultPhone.getAreaCd())) {
                phoneBuilder.append(String.format("(%s) ", defaultPhone.getAreaCd()));
            }

            if(StringUtils.isNotBlank(defaultPhone.getPhoneNbr())) {
                phoneBuilder.append(String.format("%s ", defaultPhone.getPhoneNbr()));
            }

            if(StringUtils.isNotBlank(defaultPhone.getPhoneExt())) {
                phoneBuilder.append(String.format("EXT: %s", defaultPhone.getPhoneExt()));
            }
            bean.setPhone(phoneBuilder.toString());
        }
        bean.setEmail(StringUtils.trimToEmpty(user.getEmail()));
        bean.setUserStatus(user.getStatus());
        bean.setAccountStatus(user.getSecondaryStatus());
        bean.setPrincipal(StringUtils.trimToEmpty(user.getDefaultLogin()));
        bean.setTitle(StringUtils.trimToEmpty(user.getTitle()));
        bean.setEmployeeId(StringUtils.trimToEmpty(user.getEmployeeId()));
        bean.setStartDate("");
        if(user.getStartDate()!=null){
            bean.setStartDate(sdf.format(user.getStartDate()));
        }

        if(CollectionUtils.isNotEmpty(user.getAffiliations())){
            List<Organization>  userOrgs = new ArrayList<Organization>(user.getAffiliations());

            Collections.sort(userOrgs, new Comparator<Organization>() {
                @Override
                public int compare(Organization o1, Organization o2) {
                    int result = o1.getOrganizationTypeId().compareTo(o2.getOrganizationTypeId());
                    if (result != 0) {
                        return result;
                    }
                    return  o1.getName().compareTo(o2.getName());
                }
            });

            for(Organization org: userOrgs){
                if(organizationTypeId.equals(org.getOrganizationTypeId())
                        && StringUtils.isBlank(bean.getOrganization())){
                    bean.setOrganization(org.getName());
                }else if(departmentTypeId.equals(org.getOrganizationTypeId())
                            && StringUtils.isBlank(bean.getDepartment())){
                    bean.setDepartment(org.getName());
                }
            }
        }
        return bean;
    }
}
