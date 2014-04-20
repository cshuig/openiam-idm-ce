package org.openiam.idm.srvc.edu.course.service;

import org.openiam.idm.srvc.edu.course.dto.Course;
import org.openiam.idm.srvc.edu.course.dto.CourseSearch;
import org.openiam.idm.srvc.edu.course.dto.CourseSearchResult;

import java.util.List;

/**
 * Data access object interface for Courses.
 *
 * @author Suneet Shah
 */
public interface CourseDAO {

    /**
     * Return an Organization object for the id.
     *
     * @param id
     */
    Course findById(String id);

    Course add(Course instance);

    Course update(Course instance);

    void remove(Course instance);
    List<Course> getAllCourses();

    List<CourseSearchResult> searchCourses(CourseSearch search);
    public CourseSearchResult getCourseByTerm(String courseId, String termId);





}
