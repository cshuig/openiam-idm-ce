package org.openiam.selfsrvc.edu.common.course;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openiam.idm.srvc.edu.course.dto.CourseSearch;
import org.openiam.idm.srvc.edu.course.dto.CourseSearchResult;
import org.openiam.idm.srvc.edu.course.dto.term.Term;
import org.openiam.idm.srvc.edu.course.ws.CourseManagementWebService;
import org.openiam.idm.srvc.org.service.OrganizationDataService;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class CourseDetailController extends SimpleFormController {

	private static final Log log = LogFactory.getLog(CourseDetailController.class);

    protected OrganizationDataService orgManager;
    protected CourseManagementWebService courseManager;

	public CourseDetailController() {
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

        List<Term> termList = null;

       CourseDetailCommand cmd = new CourseDetailCommand();

        String courseId =  request.getParameter("courseid");
        String termId =  request.getParameter("termid");

        CourseSearch search = new CourseSearch();
        search.setCourseId(courseId);
        search.setTerm(termId);


        CourseSearchResult course = courseManager.getCourseByTerm(courseId, termId).getCourse();


        if (course != null) {


            cmd.setCourse(course.getCourse());

            termList = courseManager.getTermsByDistrict(course.getDistrictId()).getTermList();
            cmd.setTermList(termList);


        }

        cmd.setProgramList(courseManager.getAllPrograms().getProgramList());



		
		
		return cmd;
		
		
	}
	
	
	@Override
	protected ModelAndView onSubmit(Object command) throws Exception {
		// TODO Auto-generated method stub


        CourseDetailCommand rptIncidentCmd =(CourseDetailCommand)command;

		
		ModelAndView mav = new ModelAndView(getSuccessView());
		mav.addObject("rptIncidentCmd",rptIncidentCmd);
		
		
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
