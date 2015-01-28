package org.openiam.ui.selfservice.web.mvc.activiti;

import org.apache.commons.lang.StringUtils;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.bpm.activiti.ActivitiService;
import org.openiam.bpm.request.GenericWorkflowRequest;
import org.openiam.bpm.util.ActivitiConstants;
import org.openiam.bpm.util.ActivitiRequestType;
import org.openiam.idm.srvc.auth.dto.Login;
import org.openiam.idm.srvc.auth.ws.LoginListResponse;
import org.openiam.idm.srvc.mngsys.domain.AssociationType;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.idm.srvc.user.ws.UserDataWebService;
import org.openiam.ui.exception.ErrorMessageException;
import org.openiam.ui.rest.api.model.KeyNameBean;
import org.openiam.ui.selfservice.web.model.LoginRequestModel;
import org.openiam.ui.selfservice.web.mvc.AbstractSelfServiceController;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Controller
public class UserController extends AbstractSelfServiceController {
	
    @Resource(name = "userServiceClient")
    protected UserDataWebService userDataWebService;
    
	@Resource(name="activitiClient")
	private ActivitiService activitiService;
	
	@Value("${org.openiam.selfservice.activiti.user.menu}")
	private String userMenu;

	 @RequestMapping(value="/users", method = RequestMethod.GET)
	 public String getSearchPage(final HttpServletRequest request) {
		 return "user/entity/search";
	 }
	 
	 @RequestMapping(value="/userIdenties", method=RequestMethod.GET)
	 public String myIdentities(final HttpServletRequest request, 
								final HttpServletResponse response,
								@RequestParam(required=false, value="id") String userId) {
		final LoginListResponse loginListResponse = loginServiceClient.getLoginByUser(userId);
			
		final HashMap<String, KeyNameBean> managedSysMap = new HashMap<String, KeyNameBean>();
		final List<KeyNameBean> managedSystems = getManagedSystemsAsKeyNameBeans();
		if(managedSystems != null) {
			for(final KeyNameBean sys : managedSystems) {
				managedSysMap.put(sys.getId(), sys);
			}
		}
		
		final User user = userDataWebService.getUserWithDependent(userId, getRequesterId(request), false);
		
		setMenuTree(request, userMenu);
		request.setAttribute("user", user);
		request.setAttribute("managedSysMap", managedSysMap);
		request.setAttribute("loginList", (loginListResponse != null) ? loginListResponse.getPrincipalList() : null);
		return "user/userIdentities";
	}
		
	@RequestMapping(value="/identity", method=RequestMethod.GET)
	public String identityGET(final HttpServletRequest request, 
							  final HttpServletResponse response,
							  @RequestParam(required=false, value="id") String id,
							  @RequestParam(required=false, value="loginId") String loginId) throws IOException {
		if(loginId != null) {
			id = null;
		}
		if(id != null) {
			loginId = null;
		}
		
		String userId = null;
		final Login login = loginServiceClient.findById(loginId);
		if(login == null) {
			userId = id;
		} else {
			userId = login.getUserId();
		}
		
		final User user = userDataWebService.getUserWithDependent(userId, getRequesterId(request), false);
		
		request.setAttribute("login", login);
		request.setAttribute("user", user);
		request.setAttribute("managedSystems", managedSysServiceClient.getAllManagedSys());
		setMenuTree(request, userMenu);
		return "user/editIdentity";
	}
	
	private GenericWorkflowRequest getLoginWorkflow(final ActivitiRequestType requestType,
													final String targetUserId,
													final String callerId,
													final String name,
													final String description) {
		final GenericWorkflowRequest activitiRequest = new GenericWorkflowRequest();
		activitiRequest.setActivitiRequestType(requestType.getKey());
		activitiRequest.setAssociationId(targetUserId);
		activitiRequest.setAssociationType(AssociationType.USER);
		activitiRequest.setDescription(description);
		activitiRequest.setName(name);
		activitiRequest.setRequestorUserId(callerId);
		activitiRequest.setUserCentricUserId(targetUserId);
		return activitiRequest;
	}
		
	@RequestMapping(value="/saveIdentity", method=RequestMethod.POST)
	public String saveIdentity(final HttpServletRequest request, 
							   final HttpServletResponse response, 
							   final @RequestBody LoginRequestModel requestModel) throws Exception {
		final Login login = new Login();
		login.setUserId(requestModel.getUserId());
		login.setLoginId(requestModel.getLoginId());
		login.setManagedSysId(requestModel.getManagedSysId());
		login.setLogin(requestModel.getLogin());
		Response wsResponse = loginServiceClient.isValidLogin(login);
		
		
		Errors error = null;
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		if(wsResponse.isSuccess()) {
			final String callerId = getRequesterId(request);
			final User user = userDataWebService.getUserWithDependent(requestModel.getUserId(), callerId, false);
			final String description = StringUtils.isBlank(requestModel.getLoginId()) ? 
					String.format("Create new login for user %s", user.getDisplayName()) :
					String.format("Modify Login for user %s", user.getDisplayName());
					
			
			
			final GenericWorkflowRequest activitiRequest = getLoginWorkflow(ActivitiRequestType.SAVE_LOGIN, requestModel.getUserId(), callerId, description, description);
			activitiRequest.addJSONParameter(ActivitiConstants.LOGIN.getName(), login, jacksonMapper);
			
			wsResponse = activitiService.initiateWorkflow(activitiRequest);
		}
		
		if(!wsResponse.isSuccess()) {
			error = Errors.INTERNAL_ERROR;
			if(wsResponse.getErrorCode() != null) {
				switch(wsResponse.getErrorCode()) {
					default:
						error = Errors.WORKFLOW_NOT_INITIATED;
				}
			}
		}
		
		if(error == null) {
			ajaxResponse.setStatus(200);
			ajaxResponse.setRedirectURL(String.format("userIdenties.html?id=%s", requestModel.getUserId()));
			ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.WORKFLOW_INITIATED));
		} else {
			ajaxResponse.addError(new ErrorToken(error));
			ajaxResponse.setStatus(500);
		}
	
		request.setAttribute("response", ajaxResponse);
		return "common/basic.ajax.response";
	}
		
	@RequestMapping(value="/deleteIdentity", method=RequestMethod.POST)
	public String deleteIdentity(final HttpServletRequest request,
								 final HttpServletResponse response,
								 final @RequestParam(required=true, value="id") String loginId) throws Exception {
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		
		Errors error = null;
		final Login login = loginServiceClient.findById(loginId);
		try {
			if(login == null) {
				throw new ErrorMessageException(Errors.WORKFLOW_NOT_INITIATED);
			}
			
			final String callerId = getRequesterId(request);
			final User user = userDataWebService.getUserWithDependent(login.getUserId(), callerId, false);
			if(user == null) {
				throw new ErrorMessageException(Errors.UNAUTHORIZED);
			}
			
			final String description = String.format("Delete login '%s' for %s", login.getLogin(), user.getDisplayName());
			
			final GenericWorkflowRequest activitiRequest = getLoginWorkflow(ActivitiRequestType.DELETE_LOGIN, login.getUserId(), callerId, description, description);
			activitiRequest.addJSONParameter(ActivitiConstants.LOGIN.getName(), login, jacksonMapper);
			final Response wsResponse = activitiService.initiateWorkflow(activitiRequest);
			
			if(wsResponse.getStatus() == ResponseStatus.SUCCESS) {
				ajaxResponse.setRedirectURL(String.format("userIdenties.html?id=%s", user.getId()));
			} else {
				error = Errors.INTERNAL_ERROR;
				if(wsResponse.getErrorCode() != null) {
					switch(wsResponse.getErrorCode()) {
						default:
							error = Errors.COULD_NOT_DELETE_LOGIN;
					}
				}
			}
		} catch(ErrorMessageException e) {
			error = e.getError();
		} finally {
			if(error != null) {
				ajaxResponse.setStatus(500);
				ajaxResponse.addError(new ErrorToken(error));
			} else {
				ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.WORKFLOW_INITIATED));
				ajaxResponse.setStatus(200);
			}
		}
		request.setAttribute("response", ajaxResponse);
		return "common/basic.ajax.response";
	}
}
