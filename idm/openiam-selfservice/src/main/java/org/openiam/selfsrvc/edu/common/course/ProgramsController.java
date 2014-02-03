package org.openiam.selfsrvc.edu.common.course;


import org.apache.axis.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openiam.idm.srvc.edu.course.dto.Program;
import org.openiam.idm.srvc.edu.course.ws.CourseManagementWebService;
import org.openiam.idm.srvc.org.service.OrganizationDataService;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class ProgramsController extends SimpleFormController {

	private static final Log log = LogFactory.getLog(ProgramsController.class);
    protected OrganizationDataService orgManager;
    protected CourseManagementWebService courseManager;
    protected String  redirectView;


	public ProgramsController() {
		super();
	}
	

	@Override
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("MM/dd/yyyy"),true) );
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {

        ProgramsCommand cmd = new ProgramsCommand();

        List<Program> programList = courseManager.getAllPrograms().getProgramList();
        if (programList == null ) {
            programList = new LinkedList<Program>();
        }
        addBlankProgram(programList);
        cmd.setProgramList(programList );
		return cmd;
		
		
	}

    private void addBlankProgram(List<Program> programList) {

        if (programList != null) {
            programList.add(new Program( "**ADD Program**"));

        }

    }


    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {

        ProgramsCommand cmd =(ProgramsCommand)command;
        String btn = request.getParameter("btn");

        List<Program> programList =  cmd.getProgramList();


        if (btn.equalsIgnoreCase("Delete")) {

            if (programList != null) {

                for ( Program p : programList) {
                    if (p.getSelected()) {
                        if (!StringUtils.isEmpty(p.getId()) ) {
                            //if (!courseManager.hasCourses(t.getId()).isVal()) {
                                // do not remove terms that have course.
                                courseManager.removeProgram(p.getId());
                            //}
                        }
                    }
                }
            }
        }else {

            for ( Program p : programList) {
                if (!StringUtils.isEmpty(p.getId()) ) {

                    // exiting record
                    courseManager.updateProgram(p);
                } else {

                    if (!p.getName().equalsIgnoreCase("**ADD PROGRAM**") ) {
                        p.setId(null);
                        courseManager.addProgram(p);
                    }
                }

            }
        }



        ModelAndView mav =  new ModelAndView(new RedirectView(redirectView, true));
        return mav;

    }


    public OrganizationDataService getOrgManager() {
        return orgManager;
    }

    public void setOrgManager(OrganizationDataService orgManager) {
        this.orgManager = orgManager;
    }

    public CourseManagementWebService getCourseManager() {
        return courseManager;
    }

    public void setCourseManager(CourseManagementWebService courseManager) {
        this.courseManager = courseManager;
    }

    public String getRedirectView() {
        return redirectView;
    }

    public void setRedirectView(String redirectView) {
        this.redirectView = redirectView;
    }
}
