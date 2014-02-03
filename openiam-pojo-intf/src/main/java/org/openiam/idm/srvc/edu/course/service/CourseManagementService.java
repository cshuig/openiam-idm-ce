package org.openiam.idm.srvc.edu.course.service;


import org.openiam.idm.srvc.edu.course.dto.Course;
import org.openiam.idm.srvc.edu.course.dto.CourseSearch;
import org.openiam.idm.srvc.edu.course.dto.CourseSearchResult;
import org.openiam.idm.srvc.edu.course.dto.Program;
import org.openiam.idm.srvc.edu.course.dto.term.Term;

import java.util.List;


/**
 * Interface for  <code>CourseManagementService</code>. All course management activities are handled through
 * this service.
 */

public interface CourseManagementService {


    List<Program> getAllPrograms();

    public void removeProgram( String programId);

    public Program addProgram(Program program);

    public Program updateProgram(Program program);

    public Course addCourse(Course course);
    public Course updateCourse(Course course);
    public void removeCourse(String courseId);

    public List<CourseSearchResult> searchCourses(CourseSearch search);

    // Terms

    public Term addTerm(Term term) ;
    public Term updateTerm(Term term) ;
    public void removeTerm(String courseId) ;
    public List<Term> getTermsByDistrict(String districtId) ;
    public void updateTermList(List<Term> termList ) ;
    public boolean hasCourses(String termId);

}