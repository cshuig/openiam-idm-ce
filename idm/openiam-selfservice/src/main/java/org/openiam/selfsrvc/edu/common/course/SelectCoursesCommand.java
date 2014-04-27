package org.openiam.selfsrvc.edu.common.course;

import org.openiam.idm.srvc.edu.course.dto.CourseSearch;
import org.openiam.idm.srvc.org.dto.Organization;
import org.openiam.idm.srvc.edu.course.dto.Program;
import org.openiam.idm.srvc.user.dto.User;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Command object for the SelectCourses
 * @author suneet
 *
 */
public class SelectCoursesCommand implements Serializable {
	 

	private static final long serialVersionUID = -667408382835178231L;

    CourseSearch search = new CourseSearch();

    List<Organization> districtList = new LinkedList<Organization>();
    List<Organization> schoolList = new LinkedList<Organization>();
    List<Program> programList = new LinkedList<Program>();

    List<User> teacherList = new LinkedList<User>();


    public CourseSearch getSearch() {
        return search;
    }

    public void setSearch(CourseSearch search) {
        this.search = search;
    }

    public List<Organization> getSchoolList() {
        return schoolList;
    }

    public void setSchoolList(List<Organization> schoolList) {
        this.schoolList = schoolList;
    }

    public List<Organization> getDistrictList() {
        return districtList;
    }

    public void setDistrictList(List<Organization> districtList) {
        this.districtList = districtList;
    }

    public List<Program> getProgramList() {
        return programList;
    }

    public void setProgramList(List<Program> programList) {
        this.programList = programList;
    }

    public List<User> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<User> teacherList) {
        this.teacherList = teacherList;
    }
}
