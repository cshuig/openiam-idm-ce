package org.openiam.selfsrvc.edu.common.course;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CourseDetailController extends SimpleFormController {

	private static final Log log = LogFactory.getLog(CourseDetailController.class);


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

        CourseDetailCommand rptIncidentCmd = new CourseDetailCommand();
		

		
		
		return rptIncidentCmd;
		
		
	}
	
	
	@Override
	protected ModelAndView onSubmit(Object command) throws Exception {
		// TODO Auto-generated method stub


        CourseDetailCommand rptIncidentCmd =(CourseDetailCommand)command;

		
		ModelAndView mav = new ModelAndView(getSuccessView());
		mav.addObject("rptIncidentCmd",rptIncidentCmd);
		
		
		return mav;
	}

	




}
