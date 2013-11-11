package org.openiam.idm.srvc.edu.course.service;


import org.openiam.idm.srvc.edu.course.dto.Program;

import java.util.List;

public class CourseManagementServiceImpl implements CourseManagementService {

    protected ProgramDAO programDao;


    @Override
    public List<Program> getAllPrograms() {

        return programDao.getAllPrograms();

    }

    @Override
    public void removeProgram(String programId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Program addProgram(Program program) {

        Program prg = programDao.add(program);
        return prg;
    }

    @Override
    public Program updateProgram(Program program) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ProgramDAO getProgramDao() {
        return programDao;
    }

    public void setProgramDao(ProgramDAO programDao) {
        this.programDao = programDao;
    }
}
