package org.openiam.selfsrvc.edu.common.course;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openiam.idm.srvc.edu.course.ws.CourseManagementWebService;
import org.openiam.idm.srvc.org.service.OrganizationDataService;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ProgramsController extends SimpleFormController {

	private static final Log log = LogFactory.getLog(ProgramsController.class);
    protected OrganizationDataService orgManager;
    protected CourseManagementWebService courseManager;


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
		

		
		
		return cmd;
		
		
	}



    @Override
	protected ModelAndView onSubmit(Object command) throws Exception {

        ProgramsCommand cmd =(ProgramsCommand)command;




      //  return new ModelAndView(new RedirectView(redirectView+"&mode=1", true));
		ModelAndView mav = new ModelAndView(getSuccessView());

		
		
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
}
