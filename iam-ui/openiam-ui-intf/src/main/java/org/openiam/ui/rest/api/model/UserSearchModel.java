package org.openiam.ui.rest.api.model;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openiam.base.OrderConstants;
import org.openiam.base.ws.MatchType;
import org.openiam.base.ws.SearchParam;
import org.openiam.base.ws.SortParam;
import org.openiam.idm.searchbeans.LoginSearchBean;
import org.openiam.idm.searchbeans.PotentialSupSubSearchBean;
import org.openiam.idm.searchbeans.UserSearchBean;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by: Alexander Duckardt
 * Date: 10.11.12
 */
public class UserSearchModel implements Serializable {
	
	private static transient Logger LOG = Logger.getLogger(UserSearchModel.class);
	
	private String jobCode;
    private String requesterId;
    private String lastName;
    private String firstName;
    private String principal;
    private String email;
    private Set<String> roleIds;
    private Set<String> groupIds;
    private String userStatus;
    private Set<String> organizationIds;
    private String phoneCode;
    private String phoneNumber;
    private String accountStatus;
    private String attributeName;
    private String attributeElementId;
    private String attributeValue;
    private String employeeId;
    private String resourceId;
    private String employeeType;
    private int from;
    private int size;
    private String targetUserId;
    protected String maidenName;

    private String sortBy;
    private OrderConstants orderBy;

    public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(String requesterId) {
        this.requesterId = requesterId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }


    public String getAttributeElementId() {
        return attributeElementId;
    }

    public void setAttributeElementId(String attributeElementId) {
        this.attributeElementId = attributeElementId;
    }

    public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

    public String getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
    }

    public Set<String> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Set<String> roleIds) {
		this.roleIds = roleIds;
	}

	public Set<String> getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(Set<String> groupIds) {
		this.groupIds = groupIds;
	}

	public Set<String> getOrganizationIds() {
		return organizationIds;
	}

	public void setOrganizationIds(Set<String> organizationIds) {
		this.organizationIds = organizationIds;
	}

	public boolean isEmpty() {
    	return StringUtils.isBlank(lastName) && 
    		   StringUtils.isBlank(firstName) &&
    		   StringUtils.isBlank(principal) &&
    		   StringUtils.isBlank(email) &&
    		   CollectionUtils.isEmpty(roleIds) &&
    		   CollectionUtils.isEmpty(groupIds) &&
    		   StringUtils.isBlank(userStatus) &&
    		   CollectionUtils.isEmpty(organizationIds) &&
    		   StringUtils.isBlank(phoneCode) &&
    		   StringUtils.isBlank(phoneNumber) &&
    		   StringUtils.isBlank(accountStatus) &&
    		   StringUtils.isBlank(attributeName) &&
    		   StringUtils.isBlank(attributeValue) &&
               StringUtils.isBlank(attributeElementId) &&
               StringUtils.isBlank(employeeId) &&
               StringUtils.isBlank(resourceId) &&
               StringUtils.isBlank(employeeType) &&
               StringUtils.isBlank(maidenName) &&
               StringUtils.isBlank(jobCode);
    }

    public <T extends UserSearchBean> T buildSearchBean(String requesterId, Class<T> clazz){
        T searchBean = null;
        try {
            searchBean = clazz.newInstance();
            searchBean.setRequesterId(StringUtils.isNotBlank(this.requesterId) ? this.requesterId : requesterId);

            if(StringUtils.isNotBlank(this.firstName)) {
                searchBean.setFirstNameMatchToken(new SearchParam(StringUtils.trimToNull(this.firstName), MatchType.STARTS_WITH));
            }

            if(StringUtils.isNotBlank(this.lastName)) {
                searchBean.setLastNameMatchToken(new SearchParam(StringUtils.trimToNull(this.lastName), MatchType.STARTS_WITH));
            }

            if(StringUtils.isNotBlank(this.accountStatus)) {
                searchBean.setAccountStatus(StringUtils.trimToNull(this.accountStatus));
            }

            if(StringUtils.isNotBlank(this.userStatus)) {
                searchBean.setUserStatus(StringUtils.trimToNull(this.userStatus));
            }

            if(StringUtils.isNotBlank(this.attributeName)
               || StringUtils.isNotBlank(this.attributeValue)
               || StringUtils.isNotBlank(this.attributeElementId)) {
                searchBean.addAttribute(this.attributeName, this.attributeValue, this.attributeElementId);
            }
            
            searchBean.setMaidenNameMatchToken(new SearchParam(StringUtils.trimToNull(maidenName), MatchType.STARTS_WITH));
            searchBean.setJobCode(StringUtils.trimToNull(jobCode));

            if(StringUtils.isNotBlank(this.email)) {
                searchBean.setEmailAddressMatchToken(new SearchParam(StringUtils.trimToNull(this.email), MatchType.STARTS_WITH));
            }

            searchBean.setGroupIdSet(this.groupIds);
            searchBean.setOrganizationIdSet(this.organizationIds);
            searchBean.setRoleIdSet(this.roleIds);
            
            if(StringUtils.isNotBlank(this.phoneCode)) {
                searchBean.setPhoneAreaCd(StringUtils.trimToNull(this.phoneCode));
            }

            if(StringUtils.isNotBlank(this.phoneNumber)) {
                searchBean.setPhoneNbr(StringUtils.trimToNull(this.phoneNumber));
            }

            if(StringUtils.isNotBlank(this.principal)) {
                LoginSearchBean lsb = new LoginSearchBean();
                lsb.setLoginMatchToken(new SearchParam(this.principal, MatchType.STARTS_WITH));
                searchBean.setPrincipal(lsb);
            }
            
            searchBean.setEmployeeIdMatchToken(new SearchParam(StringUtils.trimToNull(this.employeeId), MatchType.EXACT));


            if(StringUtils.isNotBlank(this.resourceId)) {
                searchBean.addResourceId(this.resourceId);
            }
            if(StringUtils.isNotEmpty(this.employeeType)) {
                searchBean.setEmployeeType(this.employeeType);
            }

            if(searchBean instanceof PotentialSupSubSearchBean){
                ((PotentialSupSubSearchBean)searchBean).addTargetUserId(this.getTargetUserId());
            }
            searchBean.setDeepCopy(false);
        } catch (Throwable e) {
            LOG.error("Exception", e);
        }
        if(StringUtils.isNotEmpty(this.employeeType)) {
            searchBean.setEmployeeType(this.employeeType);
        }

        if(StringUtils.isNotEmpty(this.sortBy)) {
            List<SortParam> sortParamList = new ArrayList<>();
            sortParamList.add(new SortParam((this.orderBy==null)?OrderConstants.ASC:this.orderBy, this.sortBy));
            searchBean.setSortBy(sortParamList);
        }
        searchBean.setDeepCopy(false);
        return searchBean;
    }
    
    public String getMaidenName() {
		return maidenName;
	}

	public void setMaidenName(String maidenName) {
		this.maidenName = maidenName;
	}
	
	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public OrderConstants getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(OrderConstants orderBy) {
        this.orderBy = orderBy;
    }
}
