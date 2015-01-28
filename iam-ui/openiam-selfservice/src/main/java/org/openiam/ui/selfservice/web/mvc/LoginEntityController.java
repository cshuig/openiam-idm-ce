package org.openiam.ui.selfservice.web.mvc;

import org.apache.commons.lang.StringUtils;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.bpm.activiti.ActivitiService;
import org.openiam.idm.srvc.auth.dto.Login;
import org.openiam.idm.srvc.auth.ws.LoginDataWebService;
import org.openiam.idm.srvc.auth.ws.LoginListResponse;
import org.openiam.idm.srvc.mngsys.dto.ManagedSysDto;
import org.openiam.idm.srvc.mngsys.ws.ManagedSystemWebService;
import org.openiam.ui.security.OpenIAMCookieProvider;
import org.openiam.ui.selfservice.web.model.LoginRequestModel;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
public class LoginEntityController extends AbstractSelfServiceController {

	/*
    @Autowired
    @Resource(name="loginServiceClient")
    private LoginDataWebService loginServiceClient;
    
	@Resource(name="managedSysServiceClient")
	private ManagedSystemWebService managedSysServiceClient;
	
	@Autowired
	private OpenIAMCookieProvider cookieProvider;
	
	@Value("${org.openiam.selfservice.activiti.user.menu}")
	private String userMenu;
	
	@Value("${org.openiam.defaultSecurityDomain}")
	private String defaultSecurityDomain;
	
	@Resource(name="activitiClient")
	private ActivitiService activitiService;
	
	@RequestMapping(value="/userIdenties", method=RequestMethod.GET)
	public String myIdentities(final HttpServletRequest request, 
							   final HttpServletResponse response,
							   @RequestParam(required=false, value="id") String userId) {
		final LoginListResponse loginListResponse = loginServiceClient.getLoginByUser(userId);
		
		final HashMap<String, ManagedSysDto> managedSysMap = new HashMap<String, ManagedSysDto>();
		final ManagedSysDto[] managedSystems = managedSysServiceClient.getAllManagedSys();
		if(managedSystems != null) {
			for(final ManagedSysDto sys : managedSystems) {
				managedSysMap.put(sys.getManagedSysId(), sys);
			}
		}
		
		setMenuTree(request, userMenu);
		request.setAttribute("managedSysMap", managedSysMap);
		request.setAttribute("loginList", (loginListResponse != null) ? loginListResponse.getPrincipalList() : null);
		return "user/myIdentities";
	}
	
	@RequestMapping(value="/identity", method=RequestMethod.GET)
	public String identityGET(final HttpServletRequest request, 
							  final HttpServletResponse response,
							  @RequestParam(required=false, value="id") String loginId) {
		if(StringUtils.isNotBlank(loginId)) {
			final Login login = loginServiceClient.findById(loginId);
			request.setAttribute("login", login);
		}
		
		request.setAttribute("managedSystems", managedSysServiceClient.getAllManagedSys());
		setMenuTree(request, identityRootMenu);
		return "user/editIdentity";
	}
	
	@RequestMapping(value="/saveIdentity", method=RequestMethod.POST)
	public String saveIdentity(final HttpServletRequest request, 
							   final HttpServletResponse response, 
							   final @RequestBody LoginRequestModel requestModel) throws IOException {
		final Login login = (StringUtils.isNotBlank(requestModel.getLoginId())) ? loginServiceClient.findById(requestModel.getLoginId()) : new Login();
		login.setManagedSysId(requestModel.getManagedSysId());
		login.setDomainId(StringUtils.isNotBlank(requestModel.getSecurityDomainId()) ? requestModel.getSecurityDomainId() : defaultSecurityDomain);
		login.setLogin(requestModel.getLogin());
		
		
		final Response wsResponse = loginServiceClient.saveLogin(login);
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		if(wsResponse.getStatus() == ResponseStatus.SUCCESS) {
			ajaxResponse.setStatus(200);
			ajaxResponse.setRedirectURL(String.format("identity.html?id=%s", wsResponse.getResponseValue()));
			ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.LOGIN_SAVED));
		} else {
			Errors error = Errors.INTERNAL_ERROR;
			if(wsResponse.getErrorCode() != null) {
				switch(wsResponse.getErrorCode()) {
					case LOGIN_EXISTS:
						error = Errors.LOGIN_TAKEN;
						break;
					default:
						error = Errors.COULD_NOT_SAVE_LOGIN;
				}
			}
			ajaxResponse.addError(new ErrorToken(error));
			ajaxResponse.setStatus(500);
		}
		request.setAttribute("response", ajaxResponse);
		return "common/basic.ajax.response";
	}
	
	@RequestMapping(value="/deleteIdentity", method=RequestMethod.POST)
	public String deleteIdentity(final HttpServletRequest request,
								 final HttpServletResponse response,
								 final @RequestParam(required=true, value="id") String loginId) throws IOException {
		final Response wsResponse = loginServiceClient.deleteLogin(loginId);
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		if(wsResponse.getStatus() == ResponseStatus.SUCCESS) {
			ajaxResponse.setStatus(200);
			ajaxResponse.setRedirectURL("myIdentities.html");
			ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.LOGIN_DELETED));
		} else {
			Errors error = Errors.INTERNAL_ERROR;
			if(wsResponse.getErrorCode() != null) {
				switch(wsResponse.getErrorCode()) {
					default:
						error = Errors.COULD_NOT_DELETE_LOGIN;
			}
			}
			ajaxResponse.addError(new ErrorToken(error));
			ajaxResponse.setStatus(500);
		}
		request.setAttribute("response", ajaxResponse);
		return "common/basic.ajax.response";
	}
	*/
}
