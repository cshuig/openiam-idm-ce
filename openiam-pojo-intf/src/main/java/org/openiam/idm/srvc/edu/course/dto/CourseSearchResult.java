package org.openiam.idm.srvc.edu.course.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseSearchResult", propOrder = {
        "courseId",
        "name",
        "courseNumber",
        "districtName",
        "schoolName",
        "termName",
        "sectionNbr",
        "courseTermId",
"districtId",
"schoolId"}
)
public class CourseSearchResult implements Serializable{

    private String courseId;
    private String name;
    private String courseNumber;
    private String districtName;
    private String schoolName;
    private String termName;
    private String sectionNbr;
    private String courseTermId;
    private String districtId;
    private  String schoolId;

    public CourseSearchResult() {
    }

    public Course getCourse() {
        Course c = new Course();
        c.setId(courseId);
        c.setCourseNumber(courseNumber);
        c.setDistrictId(districtId);
        c.setDistrictName(districtName);
        c.setName(name);
        c.setSchoolId(schoolId);
        c.setSchoolName(schoolName);
        return c;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public String getSectionNbr() {
        return sectionNbr;
    }

    public void setSectionNbr(String sectionNbr) {
        this.sectionNbr = sectionNbr;
    }

    public String getCourseTermId() {
        return courseTermId;
    }

    public void setCourseTermId(String courseTermId) {
        this.courseTermId = courseTermId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public String toString() {
        return "CourseSearchResult{" +
                "courseId='" + courseId + '\'' +
                ", name='" + name + '\'' +
                ", courseNumber='" + courseNumber + '\'' +
                ", districtName='" + districtName + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", termName='" + termName + '\'' +
                ", sectionNbr='" + sectionNbr + '\'' +
                ", courseTermId='" + courseTermId + '\'' +
                ", districtId='" + districtId + '\'' +
                ", schoolId='" + schoolId + '\'' +
                '}';
    }
}
