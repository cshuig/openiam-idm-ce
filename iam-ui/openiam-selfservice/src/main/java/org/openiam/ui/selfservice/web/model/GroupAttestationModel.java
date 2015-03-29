package org.openiam.ui.selfservice.web.model;

import org.openiam.ui.rest.api.model.UserBean;

import java.util.List;

/**
 * Created by alexander on 28.02.15.
 */
public class GroupAttestationModel {
    private String taskId;
    private String groupId;
    private String groupName;
    private List<UserBean> removedUsers;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<UserBean> getRemovedUsers() {
        return removedUsers;
    }

    public void setRemovedUsers(List<UserBean> removedUsers) {
        this.removedUsers = removedUsers;
    }
}
