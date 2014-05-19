package org.openiam.idm.srvc.edu.course.service;

import org.openiam.idm.srvc.edu.course.domain.CourseEntity;
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
    CourseEntity findById(String id);

    void add(CourseEntity instance);

    void update(CourseEntity instance);

    void remove(CourseEntity instance);
    List<CourseEntity> getAllCourses();

    List<CourseSearchResult> searchCourses(CourseSearch search);
    public CourseSearchResult getCourseByTerm(String courseId, String termId);


    CourseEntity findByExternalCourseId(String courseCode);



}
