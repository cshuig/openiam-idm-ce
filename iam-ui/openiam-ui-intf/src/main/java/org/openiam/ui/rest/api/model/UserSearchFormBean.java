package org.openiam.ui.rest.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.openiam.ui.rest.api.model.KeyNameBean;
import org.openiam.ui.rest.api.model.OrganizationBean;
import org.openiam.ui.rest.api.model.UserSearchModel;

/**
 * Created by: Alexander Duckardt
 * Date: 12.11.12
 */
public class UserSearchFormBean implements Serializable{

    private String requesterId;
    private UserSearchModel userSearchModel;
    private List<KeyNameBean> statusList;
    private List<KeyNameBean> userSecondaryStatusList;
    private List<KeyNameBean> attributeList;
    private List<OrganizationBean> organizationList;
    private List<KeyNameBean> groupList;
    private List<KeyNameBean> roleList;
    private List<Application> applications;
    private List<KeyNameBean> employeeTypes;
    private List<String> additionalSearchCriteria;
    private Integer pageNumber;
    private Integer size;

    public UserSearchModel getUserSearchModel() {
        return userSearchModel;
    }

    public void setUserSearchModel(UserSearchModel userSearchModel) {
        this.userSearchModel = userSearchModel;
    }

    public String getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(String requesterId) {
        this.requesterId = requesterId;
    }

    public List<KeyNameBean> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<KeyNameBean> statusList) {
        this.statusList = statusList;
    }

    public List<KeyNameBean> getUserSecondaryStatusList() {
        return userSecondaryStatusList;
    }

    public void setUserSecondaryStatusList(List<KeyNameBean> userSecondaryStatusList) {
        this.userSecondaryStatusList = userSecondaryStatusList;
    }

    public List<KeyNameBean> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<KeyNameBean> attributeList) {
        this.attributeList = attributeList;
    }
    
    public List<OrganizationBean> getOrganizationList() {
		return organizationList;
	}

	public void setOrganizationList(List<OrganizationBean> organizationList) {
		this.organizationList = organizationList;
	}

	public List<KeyNameBean> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<KeyNameBean> groupList) {
		this.groupList = groupList;
	}

	public List<KeyNameBean> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<KeyNameBean> roleList) {
		this.roleList = roleList;
	}

	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}

	public List<KeyNameBean> getEmployeeTypes() {
		return employeeTypes;
	}

	public void setEmployeeTypes(List<KeyNameBean> employeeTypes) {
		this.employeeTypes = employeeTypes;
	}

	public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<String> getAdditionalSearchCriteria() {
        return additionalSearchCriteria;
    }

    public void setAdditionalSearchCriteria(List<String> additionalSearchCriteria) {
        this.additionalSearchCriteria = additionalSearchCriteria;
    }

    public void addAdditionalSearchCriteria(String criteria){
        if(CollectionUtils.isEmpty(this.additionalSearchCriteria)){
            this.additionalSearchCriteria = new LinkedList<>();
        }
        this.additionalSearchCriteria.add(criteria);
    }
}
