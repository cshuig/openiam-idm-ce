package org.openiam.webadmin.user;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.srvc.menu.dto.Menu;
import org.openiam.idm.srvc.menu.ws.NavigatorDataWebService;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.idm.srvc.user.dto.UserNote;
import org.openiam.idm.srvc.user.ws.UserDataWebService;
import org.openiam.idm.srvc.user.ws.UserResponse;
import org.openiam.webadmin.admin.AppConfiguration;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.CancellableFormController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;


public class UserNotesController extends CancellableFormController {


	protected NavigatorDataWebService navigationDataService;
	protected String redirectView;
	protected UserDataWebService userMgr;
    protected AppConfiguration configuration;

	private static final Log log = LogFactory.getLog(UserNotesController.class);


	public UserNotesController() {
		super();
	}
	

	@Override
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("MM/dd/yyyy"),true) );
		
	}

    @Override
      protected ModelAndView onCancel(Object command) throws Exception {
          return new ModelAndView(new RedirectView(getCancelView(),true));
     }

	
	
	@Override
	protected Map referenceData(HttpServletRequest request) throws Exception {
		log.info("refernceData called.");
		
		Map<Object, Object> dataMap = new HashMap<Object, Object>();
	
		return dataMap;
		

	}
	


	@Override
	protected Object formBackingObject(HttpServletRequest request)		throws Exception {
		
		
		
		UserNotesCommand notesCommand = new UserNotesCommand();
		
		HttpSession session =  request.getSession();
		String userId = (String)session.getAttribute("userId");
		
		String menuGrp = request.getParameter("menugrp");
		String personId = request.getParameter("personId");
		
		// get the level 3 menu
		List<Menu> level3MenuList =  navigationDataService.menuGroupByUser(menuGrp, userId, "en").getMenuList();
		request.setAttribute("menuL3", level3MenuList);	
		request.setAttribute("personId", personId);

        notesCommand.setPerId(personId);


        List<UserNote> noteList =  userMgr.getAllNotes(personId).getUserNoteList();
        if ( noteList == null ||  noteList.isEmpty() ) {
             noteList = new LinkedList<UserNote>();

            UserNote note = new UserNote();
            note.setUserId(personId);
            note.setDescription("<Enter your comments here>");
            noteList.add(note);

        }else {
            UserNote note = new UserNote();
            note.setUserId(personId);
            note.setDescription("<Enter your comments here>");
            noteList.add(note);
        }


        notesCommand.setNoteList(noteList);




		return notesCommand;
		
	}

		
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors) throws Exception {

        User usr = null;



        String btn = request.getParameter("saveBtn");
        if (btn.equals("Cancel")) {
            return new ModelAndView(configuration.getHomePage());
        }

        UserNotesCommand cmd =(UserNotesCommand)command;
        String personId = cmd.getPerId();
        if (personId == null || personId.length() == 0 ) {
            ModelAndView mv = new ModelAndView(configuration.getErrorUrl());
            mv.addObject("msg", "User id is null. ");
            return mv;

        }
        UserResponse resp = userMgr.getUserWithDependent(personId, true);
        if (resp.getStatus() == ResponseStatus.FAILURE) {
            ModelAndView mv = new ModelAndView(configuration.getErrorUrl());
            mv.addObject("msg", "Invalid user id. ");
            return mv;
        }
        usr = resp.getUser();

        String url =  redirectView + "&personId=" + personId + "&menugrp=QUERYUSER";


        HttpSession session = request.getSession();
        String userId = (String)session.getAttribute("userId");

        List<UserNote> noteList =  cmd.getNoteList();


        if (btn.equalsIgnoreCase("Delete")) {

            if (noteList != null) {

                for ( UserNote note : noteList) {
                    if (note.getSelected()) {
                        if ( note.getUserNoteId() != null && note.getUserNoteId().length() > 0) {
                            // set the delete flag
                            userMgr.removeNote(note);

                        }
                    }
                }
            }
        }else {


            for ( UserNote note : noteList) {
                if ( note.getUserNoteId() != null && note.getUserNoteId().length() > 0) {
                    // UPDATE
                    if (!"<Enter your comments here>".equalsIgnoreCase(note.getDescription())) {
                        note.setUserId(personId);
                        userMgr.updateNote(note);

                    }

                }else {
                    // NEW
                    if (!"<Enter your comments here>".equalsIgnoreCase(note.getDescription())) {

                        Date curDate = new Date(System.currentTimeMillis());
                        note.setUserNoteId(null);
                        note.setUserId(personId);
                        note.setCreatedBy(userId);
                        note.setCreateDate(curDate);
                        note.setNoteType("ADMIN_COMMENT");

                        userMgr.addNote(note);

                    }

                }
            }
        }

        return new ModelAndView(new RedirectView(url, true));

    }




	


	public NavigatorDataWebService getNavigationDataService() {
		return navigationDataService;
	}


	public void setNavigationDataService(
			NavigatorDataWebService navigationDataService) {
		this.navigationDataService = navigationDataService;
	}





	public String getRedirectView() {
		return redirectView;
	}


	public void setRedirectView(String redirectView) {
		this.redirectView = redirectView;
	}





	public UserDataWebService getUserMgr() {
		return userMgr;
	}


	public void setUserMgr(UserDataWebService userMgr) {
		this.userMgr = userMgr;
	}

    public AppConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(AppConfiguration configuration) {
        this.configuration = configuration;
    }
}
