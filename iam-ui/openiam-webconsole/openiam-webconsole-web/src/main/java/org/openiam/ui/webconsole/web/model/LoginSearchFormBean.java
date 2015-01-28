package org.openiam.ui.webconsole.web.model;

import org.openiam.idm.searchbeans.LoginSearchBean;
import org.openiam.ui.rest.api.model.KeyNameBean;

import java.io.Serializable;
import java.util.List;

public class LoginSearchFormBean implements Serializable{
    private LoginSearchBean loginSearchBean;
    private List<KeyNameBean> managedSysList;
    private Integer pageNumber;
    private Integer size;

    public LoginSearchBean getLoginSearchBean() {
        return loginSearchBean;
    }

    public void setLoginSearchBean(LoginSearchBean loginSearchBean) {
        this.loginSearchBean = loginSearchBean;
    }

    public List<KeyNameBean> getManagedSysList() {
        return managedSysList;
    }

    public void setManagedSysList(List<KeyNameBean> managedSysList) {
        this.managedSysList = managedSysList;
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

}
