package org.openiam.idm.srvc.edu.course.ws;

import org.openiam.idm.srvc.edu.course.dto.*;
import org.openiam.idm.srvc.edu.course.dto.term.Term;
import org.openiam.idm.srvc.edu.course.dto.term.TermBooleanResponse;
import org.openiam.idm.srvc.edu.course.dto.term.TermListResponse;
import org.openiam.idm.srvc.edu.course.dto.term.TermResponse;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

/**
 * Interface for  <code>CourseManagementWebService</code>. All course management activities are handled through
 * this service.
 */
@WebService(targetNamespace = "http://www.openiam.org/service/course", name = "CourseManagementWebService")
public interface CourseManagementWebService {

	@WebMethod
    ProgramListResponse getAllPrograms();


    @WebMethod
    public void removeProgram(
            @WebParam(name = "programId", targetNamespace = "")
            String programId);

    @WebMethod
    public ProgramResponse addProgram(
            @WebParam(name = "program", targetNamespace = "")
            Program program);

    @WebMethod
    public ProgramResponse updateProgram(
            @WebParam(name = "program", targetNamespace = "")
            Program program);


    @WebMethod
    public CourseResponse addCourse(
            @WebParam(name = "course", targetNamespace = "")
            Course course);

    @WebMethod
    public CourseResponse updateCourse(
            @WebParam(name = "course", targetNamespace = "")
            Course course);

    @WebMethod
    public void removeCourse(
            @WebParam(name = "courseId", targetNamespace = "")
            String courseId);

    @WebMethod
    public CourseSearchResponse searchCourses(
            @WebParam(name = "search", targetNamespace = "")
            CourseSearch search);

    @WebMethod
    CourseSearchResponse getCourseByTerm(
            @WebParam(name = "courseId", targetNamespace = "")
            String courseId,
            @WebParam(name = "termId", targetNamespace = "")
            String termId);


    @WebMethod
    public TermResponse addTerm(
            @WebParam(name = "term", targetNamespace = "")
            Term term) ;
    @WebMethod
    public TermResponse updateTerm(
            @WebParam(name = "term", targetNamespace = "")
            Term term) ;
    @WebMethod
    public void removeTerm(
            @WebParam(name = "termId", targetNamespace = "")
            String termId) ;
    @WebMethod
    public TermListResponse getTermsByDistrict(
            @WebParam(name = "districtId", targetNamespace = "")
            String districtId) ;

    @WebMethod
    public void updateTermList(
            @WebParam(name = "termList", targetNamespace = "")
            List<Term> termList ) ;

    @WebMethod
    public TermBooleanResponse hasCourses(
            @WebParam(name = "termId", targetNamespace = "")
            String termId);


}