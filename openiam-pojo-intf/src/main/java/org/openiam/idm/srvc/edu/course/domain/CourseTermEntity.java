package org.openiam.idm.srvc.edu.course.domain;

import org.hibernate.annotations.GenericGenerator;
import org.openiam.dozer.DozerDTOCorrespondence;
import org.openiam.idm.srvc.edu.course.dto.CourseTerm;

import javax.persistence.*;



@Entity
@Table(name = "COURSE_TERM")
@DozerDTOCorrespondence(CourseTerm.class)
public class CourseTermEntity {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name="COURSE_TERM_ID", length=32)
    private String id;

    @Column(name="TERM_ID",length=32)
    private String termId;

    @Column(name="COURSE_ID",length=32)
    private String courseId;

    @Column(name="SECTION_NBR",length=40)
    private String section;

    @Column(name="STATUS",length=20)
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
