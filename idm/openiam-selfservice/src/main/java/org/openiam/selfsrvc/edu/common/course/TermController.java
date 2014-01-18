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
import org.openiam.idm.srvc.edu.course.dto.term.Term;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;


public class TermController extends SimpleFormController {

	private static final Log log = LogFactory.getLog(TermController.class);
    protected OrganizationDataService orgManager;
    protected CourseManagementWebService courseManager;


	public TermController() {
		super();
	}
	

	@Override
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("MM/dd/yyyy"),true) );
	}

    @Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
        Map<Object, Object> dataMap = new HashMap<Object, Object>();
        List<Organization> districtList =  orgManager.getOrganizationByType("districtType", null);
        dataMap.put("district", districtList ) ;
        return dataMap;


    }

	/*@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {

        TermCommand cmd = new TermCommand();
		
        // get the list of terms
        courseManager.
		
		
		return cmd;
		
		
	}
	*/

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        TermCommand cmd =(TermCommand)command;

        String submitName = request.getParameter("btn");

        if (submitName.equalsIgnoreCase("Search")) {

            List<Term> termList = courseManager.getTermsByDistrict(cmd.getDistrictId()).getTermList();
            if (termList != null && !termList.isEmpty()) {

                addBlankTerm(termList, cmd.getDistrictId());
                cmd.setTermList(termList);

            } else {

                // termList is null
                termList = new ArrayList<Term>();
                addBlankTerm(termList, cmd.getDistrictId());
                cmd.setTermList(termList);
            }


        }else {
            // save the list

        }



        //  return new ModelAndView(new RedirectView(redirectView+"&mode=1", true));
        ModelAndView mav = new ModelAndView(getSuccessView());

        List<Organization> districtList =  orgManager.getOrganizationByType("districtType", null);
        mav.addObject("district", districtList ) ;
        mav.addObject("termCmd", cmd ) ;


        return mav;
    }


    private void addBlankTerm(List<Term> termList, String districtId ) {

        if (termList != null) {
               termList.add(new Term(null, "**ADD TERM**",districtId));

        }

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
