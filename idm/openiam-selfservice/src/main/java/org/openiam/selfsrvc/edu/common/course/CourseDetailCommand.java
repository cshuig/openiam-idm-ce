package org.openiam.selfsrvc.edu.common.course;

import org.openiam.idm.srvc.edu.course.dto.Course;
import org.openiam.idm.srvc.edu.course.dto.Program;
import org.openiam.idm.srvc.edu.course.dto.term.Term;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


/**
 * Command object for the SelectCourses
 * @author suneet
 *
 */
public class CourseDetailCommand implements Serializable {
	 
    private Course course;

    private List<Term> termList;
    List<Program> programList = new LinkedList<Program>();

    String termId;
    String section;




    public List<Term> getTermList() {
        return termList;
    }

    public void setTermList(List<Term> termList) {
        this.termList = termList;
    }

    public List<Program> getProgramList() {
        return programList;
    }

    public void setProgramList(List<Program> programList) {
        this.programList = programList;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
