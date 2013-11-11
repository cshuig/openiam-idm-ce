package org.openiam.idm.srvc.edu.course.service;

import org.openiam.idm.srvc.edu.course.dto.Program;

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
    Program findById(String id);

    Program add(Program instance);

    Program update(Program instance);

    void remove(Program instance);
    List<Program> getAllPrograms();





}
