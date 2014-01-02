package org.openiam.idm.srvc.edu.course.dto;

/**
 * Created with IntelliJ IDEA.
 * User: suneetshah
 * Date: 12/3/13
 * Time: 12:50 AM
 * To change this template use File | Settings | File Templates.
 */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseSearch", propOrder = {
        "courseId",
        "name",
        "courseNumber",
        "term",
        "program",
        "districtId",
        "schoolId",
        "instructorId"}
)
public class CourseSearch {
    String courseId;
    String name;
    String courseNumber;
    String districtId;
    String schoolId;
    String instructorId;
    String term;
    String program;

    public CourseSearch() {
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
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

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }
}
