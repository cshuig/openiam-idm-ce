package org.openiam.idm.srvc.edu.course.service;

import org.openiam.idm.srvc.edu.course.domain.ProgramEntity;

import java.util.List;

/**
 * Data access object interface for Organization.
 *
 * @author Suneet Shah
 */
public interface ProgramDAO {

    /**
     * Return an Organization object for the id.
     *
     * @param id
     */
    ProgramEntity findById(String id);

    void add(ProgramEntity instance);

    void update(ProgramEntity instance);

    void remove(ProgramEntity instance);
    List<ProgramEntity> getAllPrograms();





}
