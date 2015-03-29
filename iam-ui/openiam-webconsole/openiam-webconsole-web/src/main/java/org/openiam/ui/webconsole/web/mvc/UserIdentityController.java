package org.openiam.ui.webconsole.web.mvc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openiam.base.AttributeOperationEnum;
import org.openiam.base.OrderConstants;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.base.ws.SortParam;
import org.openiam.idm.searchbeans.LoginSearchBean;
import org.openiam.idm.srvc.auth.dto.Login;
import org.openiam.idm.srvc.auth.dto.LoginStatusEnum;
import org.openiam.idm.srvc.auth.ws.LoginListResponse;
import org.openiam.idm.srvc.mngsys.dto.ManagedSysDto;
import org.openiam.idm.srvc.mngsys.ws.ManagedSystemWebService;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.provision.dto.ProvisionUser;
import org.openiam.ui.rest.api.model.KeyNameBean;
import org.openiam.ui.rest.api.model.LoginBean;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.model.BeanResponse;
import org.openiam.ui.web.util.DateFormatStr;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class UserIdentityController extends BaseUserController {

    @Resource(name="managedSysServiceClient")
    private ManagedSystemWebService managedSysServiceClient;

    private List<KeyNameBean> getManagedSysList() {
    	final List<ManagedSysDto> managedSystems = managedSysServiceClient.getAllManagedSys();
    	final List<KeyNameBean> keyNameBeanList = new LinkedList<KeyNameBean>();
    	if(managedSystems != null) {
    		for(final ManagedSysDto dto : managedSystems) {
    			if(dto != null) {
    				keyNameBeanList.add(mapper.mapToObject(dto, KeyNameBean.class));
    			}
    		}
    	}
    	return keyNameBeanList;
    }
    
    private Map<String, KeyNameBean> getManagedSysMap() {
    	final Map<String, KeyNameBean> map = new HashMap<String, KeyNameBean>();
    	final List<KeyNameBean> list = getManagedSysList();
    	if(CollectionUtils.isNotEmpty(list)) {
    		for(final KeyNameBean key : list) {
    			map.put(key.getId(), key);
    		}
    	}
    	return map;
    }
    
    @RequestMapping(value="/getUserIdentities", method = RequestMethod.GET)
    public @ResponseBody BeanResponse getUserIdentities(final @RequestParam(required=true, value="id") String userId,
                                                        final @RequestParam(required = true, value = "from") int from,
                                                        final @RequestParam(required = true, value = "size") int size,
                                                        final @RequestParam(required = false, value = "sortBy") String sortBy,
                                                        final @RequestParam(required = false, value = "orderBy") String orderBy) {

        final List<LoginBean> loginBeans = new LinkedList<LoginBean>();
        int count = 0;
        if(StringUtils.isNotEmpty(userId)) {
            LoginSearchBean searchBean = new LoginSearchBean();
            searchBean.setDeepCopy(false);
            searchBean.setUserId(userId);
            if(StringUtils.isNotEmpty(sortBy)) {
                List<SortParam> sortParamList = new ArrayList<>();
                sortParamList.add(StringUtils.isNotEmpty(orderBy)? new SortParam(OrderConstants.valueOf(orderBy), sortBy) : new SortParam(OrderConstants.ASC.getValue()));
                searchBean.setSortBy(sortParamList);
            }
            count = loginServiceClient.count(searchBean);
            final List<Login> loginList = loginServiceClient.findBeans(searchBean, from, size);
            if(CollectionUtils.isNotEmpty(loginList)) {
                final Map<String, KeyNameBean> managedSysMap = getManagedSysMap();
                final SimpleDateFormat sdf = new SimpleDateFormat(DateFormatStr.getSdfDateTime());
                for(final Login login : loginList) {
                    final LoginBean loginBean = new LoginBean();
                    loginBean.setId(login.getLoginId());
                    loginBean.setLocked(login.getIsLocked() > 0);
                    if(login.getLastLogin() != null) {
                        loginBean.setLastLogin(sdf.format(login.getLastLogin()));
                    }
                    loginBean.setUserId(userId);
                    loginBean.setLogin(login.getLogin());
                    loginBean.setStatus(login.getStatus());
                    loginBean.setProvStatus(login.getProvStatus());
                    if(login.getManagedSysId() != null) {
                        final KeyNameBean mSys = managedSysMap.get(login.getManagedSysId());
                        if(mSys != null) {
                            loginBean.setManagedSys(mSys.getName());
                            loginBean.setManagedSysId(mSys.getId());
                        }
                    }
                    loginBean.setGracePeriod(login.getGracePeriod());
                    loginBean.setPwdExp(login.getPwdExp());
                    loginBean.setLastUpdate(login.getLastUpdate());
                    loginBean.formatDates();
                    loginBeans.add(loginBean);
                }
            }
        }
    	return new BeanResponse(loginBeans, count);
    }

    @RequestMapping(value="/editUserIdentity", method = RequestMethod.GET)
    public String getEditPage(final HttpServletRequest request, 
    						  final HttpServletResponse response, Model model,
                              final @RequestParam(required=true, value="id") String userId) throws IOException {
    	final User user = userDataWebService.getUserWithDependent(userId, getRequesterId(request), false);
    	if(user == null) {
    		response.sendError(HttpServletResponse.SC_NOT_FOUND);
    		return null;
    	}
    	final List<KeyNameBean> managedSysList = getManagedSysList();
    	request.setAttribute("user", user);
    	request.setAttribute("managedSystems", (managedSysList != null) ? jacksonMapper.writeValueAsString(managedSysList) : null);
        setMenuTree(request, userEditRootMenuId);
        request.setAttribute("user", user);
        request.setAttribute("dateFormatDP", DateFormatStr.getDpDate());
    	return "users/identityUser";
    }
    
    @RequestMapping(value="/editUserIdentity", method = RequestMethod.POST)
    public String editUserIdentity(final HttpServletRequest request, 
    							   final HttpServletResponse response, 
    							   final @RequestBody LoginBean loginBean) {
    	 final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
    	 Errors error = null;
    	 
    	 try {

             loginBean.setDatesFromStr();

             Login login = (loginBean.getId() != null) ? loginServiceClient.findById(loginBean.getId()) : new Login();
             if(login == null) {
            	 response.sendError(HttpServletResponse.SC_NOT_FOUND);
            	 return null;
             }
             updateLogin(login, loginBean);
    		 
    		 Response wsResponse = loginServiceClient.isValidLogin(login);
    		 if(wsResponse.isSuccess()) {
	    		 if(provisionServiceFlag){
	                 User user = userDataWebService.getUserWithDependent(loginBean.getUserId(),getRequesterId(request),true);
	                 if(user == null) {
	                	response.sendError(HttpServletResponse.SC_NOT_FOUND);
	                	return null;
	                 }
	                 ProvisionUser pUser = new ProvisionUser(user);
                     pUser.setRequestorUserId(getRequesterId(request));
	                 if(pUser.containsLogin(login.getLoginId())) {
	                	 for (final Login l : pUser.getPrincipalList()) {
	                         if (l.getLoginId().equals(login.getLoginId())) {
	                             l.setOperation(AttributeOperationEnum.REPLACE);
	                             updateLogin(l, loginBean);
	                             break;
	                         }
	                     }
	                 } else {
	                	 //login.setOperation(AttributeOperationEnum.ADD);
	                	 pUser.addPrincipal(login);
	                 }
	                 wsResponse = provisionService.modifyUser(pUser);
	    		 } else {
	    			 wsResponse = loginServiceClient.saveLogin(login);
	    		 }
    		 }
    		 
    		 if(wsResponse != null && !wsResponse.isSuccess()) {
    			 error = Errors.COULD_NOT_SAVE_LOGIN;
    			 if(wsResponse.getErrorCode() != null) {
    				 switch(wsResponse.getErrorCode()) {
    				 	case LOGIN_EXISTS:
    				 		error = Errors.LOGIN_TAKEN;
    				 		break;
    				 	default:
    				 		break;
    				 }
    			 }
    		 }
    	 } catch(Throwable e) {
    		 log.error("Can't save identity", e);
    		 error = Errors.COULD_NOT_SAVE_LOGIN;
    	 } finally {
    		 if(error != null) {
    			 ajaxResponse.addError(new ErrorToken(error));
                 ajaxResponse.setStatus(500);
    		 } else {
    			 ajaxResponse.setStatus(200);
                 ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.LOGIN_SAVED));
    		 }
    	 }
    	 
    	 request.setAttribute("response", ajaxResponse);
         return "common/basic.ajax.response";
    }

    @RequestMapping(value="/deleteUserIdentity", method = RequestMethod.POST)
    public String deleteUserIdentity(final HttpServletRequest request,
    								 final HttpServletResponse response,
                                     final @RequestParam(value="id", required=true) String id) throws IOException {

        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();

        try {
            Response wsResponse = null;
            Login login = loginServiceClient.findById(id);
            if (login==null || defaultManagedSysId.equals(login.getManagedSysId())) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return null;
            }
            if(provisionServiceFlag){
                final User user = userDataWebService.getUserWithDependent(login.getUserId(),getRequesterId(request),true);
                if(user == null) {
                	response.sendError(HttpServletResponse.SC_NOT_FOUND);
                	return null;
                }
                final ProvisionUser pUser = new ProvisionUser(user);
                pUser.setRequestorUserId(getRequesterId(request));
                if (CollectionUtils.isNotEmpty(pUser.getPrincipalList())) {
                    for (final Login l : pUser.getPrincipalList()) {
                        if (l.getLoginId().equals(login.getLoginId())) {
                            if (LoginStatusEnum.INACTIVE.equals(l.getStatus())) {
                                final ManagedSysDto mngSys = managedSysServiceClient.getManagedSys(l.getManagedSysId());
                                l.setOperation(AttributeOperationEnum.DELETE);
                                pUser.addNotProvisioninResourcesId(mngSys.getResourceId());
                            } else {
                                l.setStatus(LoginStatusEnum.INACTIVE);
                                l.setOperation(AttributeOperationEnum.REPLACE);
                            }
                            break;
                        }
                    }
                }
                wsResponse = provisionService.modifyUser(pUser);
            } else {
                wsResponse = loginServiceClient.deleteLogin(id);
            }
            if(ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
                ajaxResponse.setStatus(200);
                ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.USER_IDENTITY_DELETED));
            } else {
                ajaxResponse.addError(new ErrorToken(Errors.USER_IDENTITY_CANNOT_DELETE));
                ajaxResponse.setStatus(500);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ajaxResponse.addError(new ErrorToken(Errors.USER_IDENTITY_CANNOT_DELETE));
        }

        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    private void updateLogin(Login login, LoginBean loginBean) {
        login.setLogin(loginBean.getLogin());
        login.setManagedSysId(loginBean.getManagedSysId());
        login.setStatus(loginBean.getStatus());
        login.setProvStatus(loginBean.getProvStatus());
        login.setIsLocked((loginBean.isLocked()) ? 1 : 0);
        login.setGracePeriod(loginBean.getGracePeriod());
        login.setPwdExp(loginBean.getPwdExp());
    }

}
