package org.openiam.idm.srvc.edu.course.service;


import org.openiam.idm.srvc.edu.course.dto.Course;
import org.openiam.idm.srvc.edu.course.dto.CourseSearch;
import org.openiam.idm.srvc.edu.course.dto.CourseSearchResult;
import org.openiam.idm.srvc.edu.course.dto.Program;

import java.util.List;

public class CourseManagementServiceImpl implements CourseManagementService {

    protected ProgramDAO programDao;
    protected CourseDAO courseDao;


    @Override
    public List<Program> getAllPrograms() {

        return programDao.getAllPrograms();

    }

    @Override
    public void removeProgram(String programId) {
        Program prg = programDao.findById(programId);
        if (prg != null) {

            programDao.remove(prg);
        }
    }

    @Override
    public Program addProgram(Program program) {

        Program prg = programDao.add(program);
        return prg;
    }

    @Override
    public Program updateProgram(Program program) {
        Program prg = programDao.update(program);
        return prg;

    }


    @Override
    public Course addCourse(Course course) {
       Course cr = courseDao.add(course);
        return cr;

    }

    @Override
    public Course updateCourse(Course course) {
        Course cr = courseDao.update(course);
        return cr;
    }

    @Override
    public void removeCourse(String courseId) {
        Course c = courseDao.findById(courseId);
        if (c != null) {

            courseDao.remove(c);
        }

    }

    @Override
    public List<CourseSearchResult> searchCourses(CourseSearch search) {
        return courseDao.searchCourses(search);
    }

    public ProgramDAO getProgramDao() {
        return programDao;
    }

    public void setProgramDao(ProgramDAO programDao) {
        this.programDao = programDao;
    }

    public CourseDAO getCourseDao() {
        return courseDao;
    }

    public void setCourseDao(CourseDAO courseDao) {
        this.courseDao = courseDao;
    }
}
