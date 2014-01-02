package org.openiam.idm.srvc.edu.course.ws;

import org.openiam.idm.srvc.edu.course.dto.*;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

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

}