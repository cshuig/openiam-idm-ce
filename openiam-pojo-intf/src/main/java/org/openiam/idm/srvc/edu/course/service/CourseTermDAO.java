package org.openiam.idm.srvc.edu.course.service;

import org.openiam.idm.srvc.edu.course.domain.CourseTermEntity;

/**
 * Data access object interface for Courses.
 *
 * @author Suneet Shah
 */
public interface CourseTermDAO {

    /**
     * Return an Organization object for the id.
     *
     * @param id
     */
    CourseTermEntity findById(String id);

    void add(CourseTermEntity instance);

    void update(CourseTermEntity instance);

    void remove(CourseTermEntity instance);








}
