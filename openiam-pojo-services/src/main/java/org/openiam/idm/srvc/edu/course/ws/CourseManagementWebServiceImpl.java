package org.openiam.idm.srvc.edu.course.ws;

import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.srvc.edu.course.dto.*;
import org.openiam.idm.srvc.edu.course.dto.term.Term;
import org.openiam.idm.srvc.edu.course.dto.term.TermBooleanResponse;
import org.openiam.idm.srvc.edu.course.dto.term.TermListResponse;
import org.openiam.idm.srvc.edu.course.dto.term.TermResponse;
import org.openiam.idm.srvc.edu.course.service.CourseManagementService;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;


@WebService(endpointInterface = "org.openiam.idm.srvc.edu.course.ws.CourseManagementWebService",
        targetNamespace = "http://www.openiam.org/service/course",
        portName = "CourseManagementWebServicePort",
        serviceName = "CourseManagementWebService")

public class CourseManagementWebServiceImpl implements CourseManagementWebService {

    private CourseManagementService courseService;


    @Transactional(readOnly = true)
    public ProgramListResponse getAllPrograms() {
        ProgramListResponse resp = new ProgramListResponse();
        resp.setStatus(ResponseStatus.FAILURE);


        List<Program> programList = courseService.getAllPrograms();

        if (programList != null && !programList.isEmpty()) {
            resp.setProgramList( programList);
            resp.setStatus(ResponseStatus.SUCCESS);
        }

        return resp;
    }

    @Transactional
    public void removeProgram(@WebParam(name = "programId", targetNamespace = "") String programId) {

        courseService.removeProgram(programId);
    }

    @Transactional
    public ProgramResponse addProgram(@WebParam(name = "program", targetNamespace = "") Program program) {
        ProgramResponse resp = new ProgramResponse();
        resp.setStatus(ResponseStatus.FAILURE);

        Program prg = courseService.addProgram(program);
        if (prg != null && prg.getId() != null) {
            resp.setProgram(  prg);
            resp.setStatus(ResponseStatus.SUCCESS);
        }

        return resp;
    }

    @Transactional
    public ProgramResponse updateProgram(@WebParam(name = "program", targetNamespace = "") Program program) {
        ProgramResponse resp = new ProgramResponse();
        resp.setStatus(ResponseStatus.FAILURE);

        Program prg = courseService.updateProgram(program);
        if (prg != null && prg.getId() != null) {
            resp.setProgram(  prg);
            resp.setStatus(ResponseStatus.SUCCESS);
        }

        return resp;

    }

    @Override
    @Transactional
    public CourseResponse addCourse(@WebParam(name = "course", targetNamespace = "") Course course) {
        CourseResponse resp = new CourseResponse();
        resp.setStatus(ResponseStatus.FAILURE);

        Course c =  courseService.addCourse(course);
        if (c != null && c.getId() != null ) {
            resp.setCourse(c);
            resp.setStatus(ResponseStatus.SUCCESS);

        }
        return resp;

    }

    @Override
    @Transactional
    public CourseResponse updateCourse(@WebParam(name = "course", targetNamespace = "") Course course) {
        CourseResponse resp = new CourseResponse();
        resp.setStatus(ResponseStatus.FAILURE);

        Course c =  courseService.updateCourse(course);
        if (c != null && c.getId() != null ) {
            resp.setCourse(c);
            resp.setStatus(ResponseStatus.SUCCESS);

        }
        return resp;

    }

    @Override
    @Transactional
    public void removeCourse(@WebParam(name = "courseId", targetNamespace = "") String courseId) {

        courseService.removeCourse(courseId);

    }

    @Override
    public CourseSearchResponse searchCourses(@WebParam(name = "search", targetNamespace = "")
                                                CourseSearch search) {

        CourseSearchResponse resp = new CourseSearchResponse();
        resp.setStatus(ResponseStatus.FAILURE);

        List<CourseSearchResult> courseList =  courseService.searchCourses(search);
        if (courseList != null && !courseList.isEmpty()) {
            resp.setCourseList(courseList);
            resp.setStatus(ResponseStatus.SUCCESS);

        }
        return resp;

    }

    @Override
    @Transactional
    public TermResponse addTerm(Term term) {
        TermResponse resp = new TermResponse();
        resp.setStatus(ResponseStatus.FAILURE);

        Term t =  courseService.addTerm(term);
        if (t != null && t.getId() != null ) {
            resp.setTerm(t);
            resp.setStatus(ResponseStatus.SUCCESS);

        }
        return resp;

    }

    @Override
    @Transactional
    public TermResponse updateTerm(Term term) {
        TermResponse resp = new TermResponse();
        resp.setStatus(ResponseStatus.FAILURE);

        Term t =  courseService.updateTerm(term);
        if (t != null && t.getId() != null ) {
            resp.setTerm(t);
            resp.setStatus(ResponseStatus.SUCCESS);

        }
        return resp;
    }

    @Override
    @Transactional
    public void removeTerm(String termId) {
        courseService.removeTerm(termId);
    }

    @Override
    public TermListResponse getTermsByDistrict(String districtId) {
        TermListResponse resp = new TermListResponse();
        resp.setStatus(ResponseStatus.FAILURE);

        List<Term> termList =  courseService.getTermsByDistrict(districtId);
        if (termList != null && !termList.isEmpty()) {
            resp.setTermList(termList);
            resp.setStatus(ResponseStatus.SUCCESS);

        }
        return resp;
    }


    @Override
    public TermBooleanResponse hasCourses(@WebParam(name = "termId", targetNamespace = "") String termId) {
        TermBooleanResponse resp = new TermBooleanResponse();
        resp.setStatus(ResponseStatus.FAILURE);

        boolean val =  courseService.hasCourses(termId);
        if (val) {
            resp.setVal(true);
            resp.setStatus(ResponseStatus.SUCCESS);

        }
        return resp;
    }

    @Override
    public void updateTermList(List<Term> termList) {
        courseService.updateTermList(termList);
    }

    public CourseManagementService getCourseService() {
        return courseService;
    }

    public void setCourseService(CourseManagementService courseService) {
        this.courseService = courseService;
    }
}
