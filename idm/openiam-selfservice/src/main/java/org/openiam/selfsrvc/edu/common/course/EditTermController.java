package org.openiam.selfsrvc.edu.common.course;


import org.apache.axis.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openiam.idm.srvc.edu.course.dto.term.Term;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class EditTermController extends SimpleFormController {

	private static final Log log = LogFactory.getLog(EditTermController.class);
    protected OrganizationDataService orgManager;
    protected CourseManagementWebService courseManager;
    protected String redirectView;

	public EditTermController() {
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


        EditTermCommand cmd = new EditTermCommand();
        cmd.setDistrictId(request.getParameter("district"));

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

		return cmd;
		
		
	}


    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {

        EditTermCommand cmd =(EditTermCommand)command;

        String btn = request.getParameter("btn");

        List<Term> termList =  cmd.getTermList();


        if (btn.equalsIgnoreCase("Delete")) {

            if (termList != null) {

                for ( Term t : termList) {
                    if (t.getSelected()) {
                        if (!StringUtils.isEmpty(t.getId()) ) {
                            if (!courseManager.hasCourses(t.getId()).isVal()) {
                                // do not remove terms that have course.
                                courseManager.removeTerm(t.getId());
                            }
                        }
                    }
                }
            }
        }else {

            for ( Term t : termList) {
                    if (!StringUtils.isEmpty(t.getId()) ) {

                        // exiting record
                        courseManager.updateTerm(t);
                    } else {

                        if (!t.getName().equalsIgnoreCase("**ADD TERM**") ) {
                            t.setId(null);
                            courseManager.addTerm(t);
                        }
                    }

            }
        }



        ModelAndView mav =  new ModelAndView(new RedirectView(redirectView+cmd.getDistrictId(), true));
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

    public String getRedirectView() {
        return redirectView;
    }

    public void setRedirectView(String redirectView) {
        this.redirectView = redirectView;
    }
}
