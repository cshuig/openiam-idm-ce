package org.openiam.ui.webconsole.web.model;

import org.apache.commons.lang.StringUtils;
import org.openiam.idm.searchbeans.UserSearchBean;

import java.util.*;

public class BulkOperationUserSearchBean extends UserSearchBean {

    protected List<String> searchCriteria = new ArrayList<String>();
    protected String groupName;
    protected String roleName;
    protected String organizationName;
    protected String principalName;
    protected Set<String> roleIds;
    protected Set<String> groupIds;
    protected Set<String> resourceIds;
    protected Set<String> organizationIds;

    public BulkOperationUserSearchBean() {
        groupIds = null;
        organizationIds = null;
        roleIds = null;
        resourceIds = null;
        principal = null;
    }

    public List<String> getSearchCriteria() {
        return searchCriteria;
    }

    public void setSearchCriteria(List<String> searchCriteria) {
        this.searchCriteria = searchCriteria;
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

    public Set<String> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(Set<String> resourceIds) {
        this.resourceIds = resourceIds;
    }

    public Set<String> getOrganizationIds() {
        return organizationIds;
    }

    public void setOrganizationIds(Set<String> organizationIds) {
        this.organizationIds = organizationIds;
    }

    public void addGroupId(String groupId){
        if(StringUtils.isNotBlank(groupId)){
            if(this.groupIds == null)
                this.groupIds = new HashSet<>();
            this.groupIds.add(groupId);
        }
    }
    public void addRoleId(String roleId){
        if(StringUtils.isNotBlank(roleId)){
            if(this.roleIds == null)
                this.roleIds = new HashSet<>();
            this.roleIds.add(roleId);
        }
    }
    public void addResourceId(String resourceId){
        if(StringUtils.isNotBlank(resourceId)){
            if(this.resourceIds == null)
                this.resourceIds = new HashSet<>();
            this.resourceIds.add(resourceId);
        }
    }
    public void addOrganizationId(String organizationId){
        if(StringUtils.isNotBlank(organizationId)){
            if(this.organizationIds == null)
                this.organizationIds = new HashSet<>();
            this.organizationIds.add(organizationId);
        }
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }
}
