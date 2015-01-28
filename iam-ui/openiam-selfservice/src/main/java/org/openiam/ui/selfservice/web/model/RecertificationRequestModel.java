package org.openiam.ui.selfservice.web.model;

import org.openiam.access.review.model.AccessViewBean;

import java.util.List;

/**
 * Created by: Alexander Duckardt
 * Date: 8/27/14.
 */
public class RecertificationRequestModel {
    private String taskId;
    private String userId;
    private String userName;
    private List<AccessViewBean> selectedItems;
    private List<AccessViewBean> unSelectedItems;


    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<AccessViewBean> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<AccessViewBean> selectedItems) {
        this.selectedItems = selectedItems;
    }

    public List<AccessViewBean> getUnSelectedItems() {
        return unSelectedItems;
    }

    public void setUnSelectedItems(List<AccessViewBean> unSelectedItems) {
        this.unSelectedItems = unSelectedItems;
    }
}
