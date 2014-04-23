package org.openiam.selfsrvc.edu.common.course;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openiam.idm.srvc.edu.course.dto.CourseSearch;
import org.openiam.idm.srvc.edu.course.dto.CourseSearchResult;
import org.openiam.idm.srvc.edu.course.ws.CourseManagementWebService;
import org.openiam.idm.srvc.org.dto.Organization;
import org.openiam.idm.srvc.org.service.OrganizationDataService;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;


public class SelectCoursesController extends SimpleFormController {

	private static final Log log = LogFactory.getLog(SelectCoursesController.class);
    protected OrganizationDataService orgManager;
    protected CourseManagementWebService courseManager;

	
	public SelectCoursesController() {
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
		
		SelectCoursesCommand coursesCommand = new SelectCoursesCommand();

        List<Organization> districtList =  orgManager.getOrganizationByType("districtType", null);

        if ( districtList == null) {
            districtList = new LinkedList<Organization>();

        }

        coursesCommand.setDistrictList(districtList);

        List<Organization> schoolList = new LinkedList<Organization>();

        for (Organization org : districtList) {

            // build the list of schools so that we in the name we can show the district-> schoolname
            List<Organization> childOrgList =  orgManager.getOrganizationList(org.getOrgId(), "ACTIVE") ;
            if (childOrgList != null && !childOrgList.isEmpty()) {

                for (Organization cOrg : childOrgList) {
                    cOrg.setOrganizationName( org.getOrganizationName() + " - " + cOrg.getOrganizationName());
                    schoolList.add(cOrg);
                }


            }


        }
        coursesCommand.setSchoolList(schoolList);
        coursesCommand.setProgramList(courseManager.getAllPrograms().getProgramList());


		return coursesCommand;
		
		
	}

    @Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
        Map<Object, Object> dataMap = new HashMap<Object, Object>();
        List<Organization> districtList =  orgManager.getOrganizationByType("districtType", null);
        List<Organization> schoolList =  orgManager.getOrganizationByType("schoolType", null);

        dataMap.put("district", districtList ) ;
        dataMap.put("school", schoolList ) ;

        return dataMap;


    }

    @Override
	protected ModelAndView onSubmit(Object command) throws Exception {

		SelectCoursesCommand coursesCommand =(SelectCoursesCommand)command;

        // get the list of course

        CourseSearch search =  coursesCommand.getSearch();

        List<CourseSearchResult> courseList = courseManager.searchCourses(search).getCourseList();


        System.out.println("Courses: " + courseList);

      //  return new ModelAndView(new RedirectView(redirectView+"&mode=1", true));
		ModelAndView mav = new ModelAndView(getSuccessView());
		mav.addObject("courseList",courseList);
        mav.addObject("courseSelCmd", coursesCommand);
		
		
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
