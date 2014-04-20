package org.openiam.idm.srvc.edu.course.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Terms that are course is offered in
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseTerm", propOrder = {
        "id",
        "status",
        "termId",
        "courseId",
        "section"}
)
public class CourseTerm {

    private String id;
    private String termId;
    private String courseId;
    private String section;
    private String status;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
