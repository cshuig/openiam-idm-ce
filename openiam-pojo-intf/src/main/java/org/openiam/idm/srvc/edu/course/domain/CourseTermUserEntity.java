package org.openiam.idm.srvc.edu.course.domain;

import org.hibernate.annotations.GenericGenerator;
import org.openiam.dozer.DozerDTOCorrespondence;
import org.openiam.idm.srvc.edu.course.dto.CourseTermUser;

import javax.persistence.*;


@Entity
@Table(name = "COURSE_TERM_USER")
@DozerDTOCorrespondence(CourseTermUser.class)
public class CourseTermUserEntity implements java.io.Serializable {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name="COURSE_TERM_USER_ID", length=32)
    private String id;


    @Column(name="COURSE_ID",length=32)
    private String courseId;

    @Column(name="USER_ID",length=32)
    private String userId;

    @Column(name="USER_TYPE",length=30)
    private String userType;

    // organization
    @Column(name="ROLE_ID",length=32)
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
