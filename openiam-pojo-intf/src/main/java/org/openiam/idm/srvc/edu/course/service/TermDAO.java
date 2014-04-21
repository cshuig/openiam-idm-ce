package org.openiam.idm.srvc.edu.course.service;

import org.openiam.idm.srvc.edu.course.domain.TermEntity;

import java.util.List;

/**
 * Data access object interface for Courses.
 *
 * @author Suneet Shah
 */
public interface TermDAO {

    /**
     * Return an Organization object for the id.
     *
     * @param id
     */
    TermEntity findById(String id);

    void add(TermEntity instance);

    void update(TermEntity instance);

    void remove(TermEntity instance);

    List<TermEntity> getTermsByDistrict(String districtId);

    boolean hasCourses(String termId);






}
