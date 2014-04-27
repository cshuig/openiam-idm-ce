package org.openiam.idm.srvc.edu.course.dto;

import org.openiam.dozer.DozerDTOCorrespondence;
import org.openiam.idm.srvc.edu.course.domain.CourseTermUserEntity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseTermUser", propOrder = {
        "id",
        "courseId",
        "userId",
        "userType",
        "roleId"})
@XmlRootElement(name = "CourseTermUser")
@DozerDTOCorrespondence(CourseTermUserEntity.class)
public class CourseTermUser implements java.io.Serializable {

    private String id;

    private String courseId;

    private String userId;

    private String userType;

    private String roleId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
