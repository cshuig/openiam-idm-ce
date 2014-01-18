package org.openiam.idm.srvc.edu.course.service;

import org.openiam.idm.srvc.edu.course.dto.term.Term;

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
    Term findById(String id);

    Term add(Term instance);

    Term update(Term instance);

    void remove(Term instance);

    List<Term> getTermsByDistrict(String districtId);






}
