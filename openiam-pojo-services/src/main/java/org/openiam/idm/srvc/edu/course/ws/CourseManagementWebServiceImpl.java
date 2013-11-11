package org.openiam.idm.srvc.edu.course.ws;

import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.srvc.edu.course.dto.Program;
import org.openiam.idm.srvc.edu.course.dto.ProgramListResponse;
import org.openiam.idm.srvc.edu.course.dto.ProgramResponse;
import org.openiam.idm.srvc.edu.course.service.CourseManagementService;

import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;


@WebService(endpointInterface = "org.openiam.idm.srvc.edu.course.ws.CourseManagementWebService",
        targetNamespace = "http://www.openiam.org/service/course",
        portName = "CourseManagementWebServicePort",
        serviceName = "CourseManagementWebService")

public class CourseManagementWebServiceImpl implements CourseManagementWebService {

    private CourseManagementService courseService;


    @Override
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

    @Override
    public void removeProgram(@WebParam(name = "programId", targetNamespace = "") String programId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
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

    @Override
    public ProgramResponse updateProgram(@WebParam(name = "program", targetNamespace = "") Program program) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public CourseManagementService getCourseService() {
        return courseService;
    }

    public void setCourseService(CourseManagementService courseService) {
        this.courseService = courseService;
    }
}
