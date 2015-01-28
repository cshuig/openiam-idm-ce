package org.openiam.ui.selfservice.web.mvc.activiti;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openiam.access.review.constant.AccessReviewConstant;
import org.openiam.access.review.model.AccessViewBean;
import org.openiam.access.review.model.AccessViewFilterBean;
import org.openiam.access.review.model.AccessViewResponse;
import org.openiam.access.review.service.ws.AccessReviewWebService;
import org.openiam.authmanager.common.SetStringResponse;
import org.openiam.authmanager.service.AuthorizationManagerAdminWebService;
import org.openiam.base.ws.Response;
import org.openiam.bpm.request.ActivitiRequestDecision;
import org.openiam.bpm.request.GenericWorkflowRequest;
import org.openiam.bpm.response.TaskWrapper;
import org.openiam.bpm.util.ActivitiRequestType;
import org.openiam.idm.searchbeans.UserSearchBean;
import org.openiam.idm.srvc.audit.constant.AuditAction;
import org.openiam.idm.srvc.audit.dto.IdmAuditLog;
import org.openiam.idm.srvc.auth.dto.Login;
import org.openiam.idm.srvc.auth.ws.LoginDataWebService;
import org.openiam.idm.srvc.mngsys.domain.AssociationType;
import org.openiam.idm.srvc.mngsys.dto.ManagedSysDto;
import org.openiam.idm.srvc.mngsys.ws.ManagedSystemWebService;
import org.openiam.idm.srvc.res.service.ResourceDataService;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.idm.srvc.user.ws.UserDataWebService;
import org.openiam.ui.rest.api.model.LoginBean;
import org.openiam.ui.selfservice.web.model.RecertificationRequestModel;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.mvc.AbstractActivitiController;
import org.openiam.ui.web.util.DateFormatStr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA. User: alexander Date: 11/7/13 Time: 2:12 AM To
 * change this template use File | Settings | File Templates.
 */
@Controller
public class AccessReviewController extends AbstractActivitiController {

    @Resource(name = "resourceServiceClient")
    private ResourceDataService resourceDataService;

    @Resource(name = "authManagerAdminClient")
    private AuthorizationManagerAdminWebService authWebService;

    @Resource(name = "userServiceClient")
    protected UserDataWebService userDataWebService;

    @Resource(name = "managedSysServiceClient")
    protected ManagedSystemWebService managedSystemWebService;

    @Resource(name = "loginServiceClient")
    private LoginDataWebService loginDataWebService;

    @Value("${org.openiam.selfservice.activiti.user.menu}")
    private String userMenu;

    @Value("${org.openiam.access.view.max.level}")
    private int maxHierarchyLevel;

    @Resource(name = "accessReviewServiceClient")
    private AccessReviewWebService accessReviewWebService;

    @RequestMapping(value = "/accessReview", method = RequestMethod.GET)
    public String accessReview(final HttpServletRequest request, final HttpServletResponse response,
                               final @RequestParam(value = "id", required = true) String userId,
                               final @RequestParam(value = "taskId", required = false) String taskId,
                               @RequestParam(value = "type", required = false) String type) throws IOException {
        TaskWrapper task = null;
        if (StringUtils.isBlank(type)) {
            type = "resources";
        }

        if (StringUtils.isNotBlank(taskId)) {
            task = activitiService.getTask(taskId);
        }


        final UserSearchBean searchBean = new UserSearchBean();
        searchBean.setKey(userId);
        final List<User> userList = userDataWebService.findBeans(searchBean, 0, 1);
        if (CollectionUtils.isEmpty(userList)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, String.format("User with id '%s' does not exist", userId));
            return null;
        }

        final User user = userList.get(0);
        final User supervisor = userDataWebService.getPrimarySupervisor(userId);

        request.setAttribute("user", user);
        request.setAttribute("userInfo", getUserInfo(user, supervisor));
        request.setAttribute("type", type);
        request.setAttribute("taskId", taskId);
        request.setAttribute("riskList", jacksonMapper.writeValueAsString(getResourceRiskAsKeyNameBeans()));
        setMenuTree(request, userMenu);
        return "user/entity/accessReview";
    }

    @RequestMapping(value = "/accessreview/logininfo", method = RequestMethod.GET)
    public @ResponseBody LoginBean getLogininfo(final HttpServletResponse response,
                                            final @RequestParam(value = "loginId", required = true) String loginId) throws IOException {
        Login login = loginDataWebService.findById(loginId);
        final LoginBean loginBean = new LoginBean();
        if(login==null){
            response.sendError(HttpServletResponse.SC_NOT_FOUND, String.format("Login with id '%s' does not exist", loginId));
            return loginBean;
        }
        final SimpleDateFormat sdf = new SimpleDateFormat(DateFormatStr.getSdfDateTime());


        loginBean.setId(login.getLoginId());
        loginBean.setLocked(login.getIsLocked() > 0);
        if(login.getLastLogin() != null) {
            loginBean.setLastLogin(sdf.format(login.getLastLogin()));
        }
        loginBean.setUserId(login.getUserId());
        loginBean.setLogin(login.getLogin());
        loginBean.setStatus(login.getStatus());
        if(login.getManagedSysId() != null) {
            ManagedSysDto mngsys = managedSysServiceClient.getManagedSys(login.getManagedSysId());
            if(mngsys != null) {
                loginBean.setManagedSys(mngsys.getName());
                loginBean.setManagedSysId(mngsys.getId());
            }
        }
        loginBean.setGracePeriod(login.getGracePeriod());
        loginBean.setPwdExp(login.getPwdExp());
        loginBean.setLastUpdate(login.getLastUpdate());
        loginBean.formatDates();

        return loginBean;
    }

    @RequestMapping(value = "/accessReview/getRolesView", method = RequestMethod.POST)
    public @ResponseBody
    AccessViewResponse getRolesView(final HttpServletResponse response,
                                    final @RequestBody AccessViewFilterBean filter) throws IOException {
        return getAccessView(response, filter, "roles");
    }
    @RequestMapping(value = "/accessReview/getResourceView", method = RequestMethod.POST)
    public @ResponseBody AccessViewResponse getResourceView(final HttpServletResponse response,
                                                      final @RequestBody AccessViewFilterBean filter) throws IOException {
        return getAccessView(response, filter, "resources");
    }
    @RequestMapping(value = "/accessReview/getGroupView", method = RequestMethod.POST)
    public @ResponseBody AccessViewResponse getGroupView(final HttpServletResponse response,
                                                         final @RequestBody AccessViewFilterBean filter) throws IOException {
        return getAccessView(response, filter, "groups");
    }
    @RequestMapping(value = "/accessReview/certify", method = RequestMethod.POST)
    public @ResponseBody BasicAjaxResponse recertificationDecision(final HttpServletRequest request, final HttpServletResponse response,
                                                                   final @RequestBody
                                                                   RecertificationRequestModel recertificationRequest) throws IOException {
        String requesterId = this.getRequesterId(request);
        String requesterLogin = this.getRequesterPrincipal(request);

        final IdmAuditLog parentAuditLog = new IdmAuditLog();
        parentAuditLog.setRequestorPrincipal(requesterLogin);
        parentAuditLog.setRequestorUserId(requesterId);
        parentAuditLog.setAction(AuditAction.RECERTIFICATION.value());
        parentAuditLog.setAuditDescription(String.format("Re-certification task for User: %s",
                                                         recertificationRequest.getUserName()));
        parentAuditLog.setTargetUser(recertificationRequest.getUserId(), recertificationRequest.getUserName());


        ActivitiRequestDecision decision = new ActivitiRequestDecision();
        decision.setTaskId(recertificationRequest.getTaskId());
        decision.setAccepted(true);

        BasicAjaxResponse ajaxResponse = doDecision(request, response,  decision);

        if(!ajaxResponse.isError()){
            List<ErrorToken> errors = createCertificationTasks(request, recertificationRequest, parentAuditLog);
            ajaxResponse.addErrors(errors);
        }
        if(!ajaxResponse.isError()){
            parentAuditLog.succeed();
            ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.TASK_COMPLETED));
            ajaxResponse.setStatus(200);
        } else {
            parentAuditLog.fail();
            ajaxResponse.setSuccessToken(null);
            response.setStatus(500);
        }

        auditWS.addLog(parentAuditLog);
        ajaxResponse.process(localeResolver, messageSource, request);
        return ajaxResponse;
    }

    private List<ErrorToken>  createCertificationTasks(HttpServletRequest request, RecertificationRequestModel recertificationRequest, IdmAuditLog parentAuditLog) {

        List<ErrorToken> errorList = new ArrayList<>();
// add to audit log those data that are not certified for user
        if(CollectionUtils.isNotEmpty(recertificationRequest.getUnSelectedItems())){
            for(AccessViewBean item : recertificationRequest.getUnSelectedItems()){
                final IdmAuditLog idmAuditLog = new IdmAuditLog();
                idmAuditLog.setRequestorPrincipal(parentAuditLog.getPrincipal());
                idmAuditLog.setRequestorUserId(parentAuditLog.getUserId());
                idmAuditLog.setAction(AuditAction.RECERTIFICATION_DONT_CERTIFY.value());
                idmAuditLog.addCustomRecord("USER:", recertificationRequest.getUserName());

                if(AccessReviewConstant.RESOURCE_TYPE.equals(item.getBeanType())){
                    idmAuditLog.setTargetResource(item.getId(), item.getName());
                } else if(AccessReviewConstant.ROLE_TYPE.equals(item.getBeanType())){
                    idmAuditLog.setTargetRole(item.getId(), item.getName());
                }
                idmAuditLog.succeed();
                parentAuditLog.addChild(idmAuditLog);
            }
        }

        if(CollectionUtils.isNotEmpty(recertificationRequest.getSelectedItems())){
            Set<String> resourceIds = new HashSet<>();
            for(AccessViewBean item : recertificationRequest.getSelectedItems()){
                if(AccessReviewConstant.RESOURCE_TYPE.equals(item.getBeanType())){
                    resourceIds.add(item.getId());
                }
            }

            Map<String, SetStringResponse> resourceOwnersMap = authWebService.getOwnerIdsForResourceSet(resourceIds);

            for(AccessViewBean item : recertificationRequest.getSelectedItems()){

                final IdmAuditLog idmAuditLog = new IdmAuditLog();
                idmAuditLog.setRequestorPrincipal(parentAuditLog.getPrincipal());
                idmAuditLog.setRequestorUserId(parentAuditLog.getUserId());
                idmAuditLog.setAction(AuditAction.RECERTIFICATION_CERTIFY.value());
                idmAuditLog.addCustomRecord("USER:", recertificationRequest.getUserName());


                if(AccessReviewConstant.RESOURCE_TYPE.equals(item.getBeanType())){
                    idmAuditLog.setTargetResource(item.getId(), item.getName());
                    Response wsResponse = createCertificationTask(request, recertificationRequest.getUserId(),recertificationRequest.getUserName(), item, resourceOwnersMap);
                    if(wsResponse.isFailure()){
                        idmAuditLog.fail();
                        idmAuditLog.setFailureReason(wsResponse.getErrorCode());
                        Errors error = Errors.COULD_NOT_CREATE_RESOURCE_CERTIFICATION_WORKFLOW;
                        errorList.add(new ErrorToken(error, new Object[]{recertificationRequest.getUserName(), item.getName()}));
                    }
                } else if(AccessReviewConstant.ROLE_TYPE.equals(item.getBeanType())){
                    idmAuditLog.setTargetRole(item.getId(), item.getName());
                    idmAuditLog.succeed();
                }
                parentAuditLog.addChild(idmAuditLog);
            }
        }
        return errorList;
    }

    private Response createCertificationTask(HttpServletRequest request, String userId, String userName, AccessViewBean resource, Map<String, SetStringResponse> resourceOwnersMap) {
        final GenericWorkflowRequest workflowRequest = new GenericWorkflowRequest();
        workflowRequest.setActivitiRequestType(ActivitiRequestType.RESOURCE_CERTIFICATION.getKey());
        workflowRequest.setDescription(String.format("Certify access to %s for user %s ", resource.getName(), userName));
        workflowRequest.setName(String.format("Certify access to %s for user %s ", resource.getName(), userName));
        workflowRequest.setRequestorUserId(getRequesterId(request));
        workflowRequest.setAssociationId(resource.getId());
        workflowRequest.setAssociationType(AssociationType.RESOURCE);
        workflowRequest.setMemberAssociationId(userId);
        workflowRequest.setMemberAssociationType(AssociationType.USER);

        Set<String> customApprovers = (resourceOwnersMap!=null && resourceOwnersMap.get(resource.getId())!=null) ? resourceOwnersMap.get(resource.getId()).getSetString():null;
        if(CollectionUtils.isNotEmpty(customApprovers)){
            workflowRequest.setCustomApproverIds(customApprovers);
        }

        return activitiService.initiateWorkflow(workflowRequest);
    }


    public AccessViewResponse getAccessView(final HttpServletResponse response, AccessViewFilterBean filter, String viewType) throws IOException {
        final UserSearchBean searchBean = new UserSearchBean();
        searchBean.setKey(filter.getUserId());
        searchBean.setDeepCopy(false);
        final List<User> userList = userDataWebService.findBeans(searchBean, 0, 1);
        if (CollectionUtils.isEmpty(userList)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, String.format("User with id '%s' does not exist", filter.getUserId()));
            return AccessViewResponse.EMPTY_RESPONSE;
        }
        return accessReviewWebService.getAccessReviewTree(filter, viewType, getCurrentLanguage());
    }

    private String getUserInfo(User user, User supervisor) {
        SimpleDateFormat sdf = new SimpleDateFormat(DateFormatStr.getSdfDate());
        StringBuilder sb  = new StringBuilder();

        if(user.getStatus()!=null)
            sb.append(user.getStatus());

        if(StringUtils.isNotBlank(user.getTitle()))
            sb.append(", ").append(user.getTitle());

        if(StringUtils.isNotBlank(user.getEmployeeId()))
            sb.append(", ").append(user.getEmployeeId());

        if(supervisor!=null && StringUtils.isNotBlank(supervisor.getDisplayName()))
            sb.append(", ").append(supervisor.getDisplayName());

        if(user.getStartDate()!=null)
            sb.append(", ").append(sdf.format(user.getStartDate()));

        return sb.toString();
    }

}
