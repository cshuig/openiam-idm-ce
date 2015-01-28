package org.openiam.ui.webconsole.web.model;

import org.apache.commons.collections.CollectionUtils;
import org.openiam.ui.rest.api.model.KeyNameBean;

import java.util.ArrayList;
import java.util.List;

public class DelegationFilterModel {
    protected String userId; // personId

    protected List<KeyNameBean> orgFilter;
    protected List<KeyNameBean> divFilter;
    protected List<KeyNameBean> deptFilter;
    protected List<KeyNameBean> groupList;
    protected List<KeyNameBean> roleList;
    protected List<String> appList;
    protected Boolean mngRptFlag;
    protected Boolean useOrgInhFlag;

    private String organizationTypeId;
    private String divisionTypeId;
    private String departmentTypeId;


    public String getUserId() {
        return userId;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }


    public List<KeyNameBean> getOrgFilter() {
        return orgFilter;
    }

    public void setOrgFilter(List<KeyNameBean> orgFilter) {
        this.orgFilter = orgFilter;
    }

    public List<String> getOrgFilterKeys() {
        return getFilerKeys(orgFilter);
    }

    public List<KeyNameBean> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<KeyNameBean> groupList) {
        this.groupList = groupList;
    }
    public List<String> getGroupKeys() {
        return getFilerKeys(groupList);
    }

    public List<KeyNameBean> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<KeyNameBean> roleList) {
        this.roleList = roleList;
    }
    public List<String> getRoleKeys() {
        return getFilerKeys(roleList);
    }

    public List<String> getAppList() {
        return appList;
    }

    public void setAppList(List<String> appList) {
        this.appList = appList;
    }

    public Boolean getMngRptFlag() {
        return mngRptFlag;
    }

    public void setMngRptFlag(Boolean mngRptFlag) {
        this.mngRptFlag = mngRptFlag;
    }

    public Boolean getUseOrgInhFlag() {
        return useOrgInhFlag;
    }

    public void setUseOrgInhFlag(Boolean useOrgInhFlag) {
        this.useOrgInhFlag = useOrgInhFlag;
    }

    public List<KeyNameBean> getDivFilter() {
        return divFilter;
    }

    public void setDivFilter(List<KeyNameBean> divFilter) {
        this.divFilter = divFilter;
    }

    public List<KeyNameBean> getDeptFilter() {
        return deptFilter;
    }

    public void setDeptFilter(List<KeyNameBean> deptFilter) {
        this.deptFilter = deptFilter;
    }

    public List<String> getDeptFilterKeys() {
        return getFilerKeys(deptFilter);
    }
    public List<String> getDivFilterKeys() {
        return getFilerKeys(divFilter);
    }

    private List<String> getFilerKeys(List<KeyNameBean> filter) {
        List<String> result = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(filter)){
            for(KeyNameBean bean : filter){
                result.add(bean.getId());
            }
        }
        return result;
    }


    public String getOrganizationTypeId() {
        return organizationTypeId;
    }

    public void setOrganizationTypeId(String organizationTypeId) {
        this.organizationTypeId = organizationTypeId;
    }

    public String getDivisionTypeId() {
        return divisionTypeId;
    }

    public void setDivisionTypeId(String divisionTypeId) {
        this.divisionTypeId = divisionTypeId;
    }

    public String getDepartmentTypeId() {
        return departmentTypeId;
    }

    public void setDepartmentTypeId(String departmentTypeId) {
        this.departmentTypeId = departmentTypeId;
    }
}
