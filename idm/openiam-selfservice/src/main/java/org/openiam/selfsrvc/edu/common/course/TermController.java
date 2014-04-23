package org.openiam.selfsrvc.edu.common.course;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openiam.idm.srvc.edu.course.ws.CourseManagementWebService;
import org.openiam.idm.srvc.org.dto.Organization;
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


public class TermController extends SimpleFormController {

	private static final Log log = LogFactory.getLog(TermController.class);
    protected OrganizationDataService orgManager;
    protected CourseManagementWebService courseManager;
    protected String redirectView;


	public TermController() {
		super();
	}
	

	@Override
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("MM/dd/yyyy"),true) );
	}

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        TermCommand cmd = new TermCommand();

        List<Organization> districtList =  orgManager.getOrganizationByType("districtType", null);

        if ( districtList == null) {
            districtList = new LinkedList<Organization>();

        }

        cmd.setDistrictList(districtList);

        return cmd;



    }



    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        TermCommand cmd =(TermCommand)command;



        return new ModelAndView(new RedirectView(redirectView+cmd.getDistrictId(), true));

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
