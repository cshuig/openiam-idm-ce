package org.openiam.idm.srvc.edu.course.service;


import org.apache.commons.lang.StringUtils;
import org.openiam.idm.srvc.edu.course.dto.Course;
import org.openiam.idm.srvc.edu.course.dto.CourseSearch;
import org.openiam.idm.srvc.edu.course.dto.CourseSearchResult;
import org.openiam.idm.srvc.edu.course.dto.Program;
import org.openiam.idm.srvc.edu.course.dto.term.Term;

import java.util.List;

public class CourseManagementServiceImpl implements CourseManagementService {

    protected ProgramDAO programDao;
    protected CourseDAO courseDao;
    protected TermDAO termDao;


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

    /* Term operation */

    public Term addTerm(Term term) {
        Term t = termDao.add(term);
        return t;

    }

    @Override
    public Term updateTerm(Term term) {
        Term t = termDao.update(term);
        return t;
    }

    @Override
    public void removeTerm(String termId) {
        Term t = termDao.findById(termId);
        if (t != null) {

            termDao.remove(t);
        }

    }

    @Override
    public List<Term> getTermsByDistrict(String districtId) {
        return termDao.getTermsByDistrict(districtId);
    }

    public void updateTermList(List<Term> termList ) {

        if (termList != null && !termList.isEmpty()) {

            for (Term t : termList) {
                if (StringUtils.isEmpty(t.getId()))  {

                    termDao.add(t);


                }else {
                    termDao.update(t);
                }

            }

        }

    }

    @Override
    public boolean hasCourses(String termId) {
        return termDao.hasCourses(termId);
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

    public TermDAO getTermDao() {
        return termDao;
    }

    public void setTermDao(TermDAO termDao) {
        this.termDao = termDao;
    }
}
