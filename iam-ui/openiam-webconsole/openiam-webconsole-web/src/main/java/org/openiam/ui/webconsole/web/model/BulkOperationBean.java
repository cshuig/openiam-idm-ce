package org.openiam.ui.webconsole.web.model;

import org.openiam.idm.searchbeans.UserSearchBean;
import org.openiam.idm.srvc.prov.request.dto.OperationBean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class BulkOperationBean implements Serializable {

    private BulkOperationUserSearchBean userSearchBean = new BulkOperationUserSearchBean();
    private Set<String> userIds = new HashSet<String>();
    private Set<OperationBean> operations = new LinkedHashSet<OperationBean>();
    private int startPos;
    private int endPos;
    private int pageSize=10;
    private int usersNum = 0;

    public BulkOperationBean() {
        resetPagination();
    }

    public void resetPagination() {
        startPos = 1;
        endPos = pageSize;
    }

    public BulkOperationUserSearchBean getUserSearchBean() {
        return userSearchBean;
    }

    public void setUserSearchBean(BulkOperationUserSearchBean userSearchBean) {
        this.userSearchBean = userSearchBean;
    }

    public Set<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<String> userIds) {
        this.userIds = userIds;
    }

    public Set<OperationBean> getOperations() {
        return operations;
    }

    public void setOperations(Set<OperationBean> operations) {
        this.operations = operations;
    }

    public int getStartPos() {
        return startPos;
    }

    public void setStartPos(int startPos) {
        this.startPos = startPos;
    }

    public int getEndPos() {
        return endPos;
    }

    public void setEndPos(int endPos) {
        this.endPos = endPos;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getUsersNum() {
        return usersNum;
    }

    public void setUsersNum(int usersNum) {
        this.usersNum = usersNum;
    }
}
