package org.openiam.ui.selfservice.web.mvc.activiti;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openiam.access.review.constant.AccessReviewConstant;
import org.openiam.access.review.model.AccessViewBean;
import org.openiam.base.OrderConstants;
import org.openiam.base.ws.SortParam;
import org.openiam.bpm.request.ActivitiRequestDecision;
import org.openiam.bpm.response.TaskWrapper;
import org.openiam.idm.searchbeans.UserSearchBean;
import org.openiam.idm.srvc.audit.constant.AuditAction;
import org.openiam.idm.srvc.audit.dto.IdmAuditLog;
import org.openiam.idm.srvc.grp.dto.Group;
import org.openiam.idm.srvc.grp.ws.GroupDataWebService;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.idm.srvc.user.ws.UserDataWebService;
import org.openiam.ui.rest.api.model.UserBean;
import org.openiam.ui.selfservice.web.model.GroupAttestationModel;
import org.openiam.ui.selfservice.web.model.RecertificationRequestModel;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.mvc.AbstractActivitiController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexander on 26.02.15.
 */
@Controller
public class GroupAttestationController extends AbstractActivitiController {

    @Resource(name="groupServiceClient")
    protected GroupDataWebService groupServiceClient;
    @Resource(name = "userServiceClient")
    private UserDataWebService userServiceClient;

    @RequestMapping(value = "/groupAttestation", method = RequestMethod.GET)
    public String accessReview(final HttpServletRequest request, final HttpServletResponse response,
                               final @RequestParam(value = "id", required = true) String groupId,
                               final @RequestParam(value = "taskId", required = true) String taskId) throws IOException {
        TaskWrapper task = null;
        String requesterId = getRequesterId(request);
        if (StringUtils.isNotBlank(taskId)) {
            task = activitiService.getTask(taskId);
        }
        final Group group = groupServiceClient.getGroup(groupId, requesterId);

        if (group==null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, String.format("Group with id '%s' does not exist", groupId));
            return null;
        }

        request.setAttribute("group", group);
        request.setAttribute("taskId", taskId);

        return "group/attestation";
    }

    @RequestMapping(value = "/groupCertify", method = RequestMethod.POST)
    public @ResponseBody
    BasicAjaxResponse recertificationDecision(final HttpServletRequest request,
                                              final HttpServletResponse response,
                                              final @RequestBody GroupAttestationModel groupAttestationModel) throws IOException {
        String requesterId = this.getRequesterId(request);
        String requesterLogin = this.getRequesterPrincipal(request);

        final IdmAuditLog parentAuditLog = new IdmAuditLog();
        parentAuditLog.setRequestorPrincipal(requesterLogin);
        parentAuditLog.setRequestorUserId(requesterId);
        parentAuditLog.setAction(AuditAction.GROUP_ATTESTATION.value());
        parentAuditLog.setAuditDescription(String.format("Attestation task for Group: %s", groupAttestationModel.getGroupName()));
        parentAuditLog.setTargetGroup(groupAttestationModel.getGroupId(), groupAttestationModel.getGroupName());


        ActivitiRequestDecision decision = new ActivitiRequestDecision();
        decision.setTaskId(groupAttestationModel.getTaskId());
        decision.setAccepted(true);

        if(CollectionUtils.isNotEmpty(groupAttestationModel.getRemovedUsers())){
            for(UserBean user : groupAttestationModel.getRemovedUsers()){
                final IdmAuditLog idmAuditLog = new IdmAuditLog();
                idmAuditLog.setRequestorPrincipal(parentAuditLog.getPrincipal());
                idmAuditLog.setRequestorUserId(parentAuditLog.getUserId());
                idmAuditLog.setAction(AuditAction.RECERTIFICATION_DONT_CERTIFY.value());
                idmAuditLog.setTargetUser(user.getId(), user.getName());
                idmAuditLog.succeed();
                parentAuditLog.addChild(idmAuditLog);
            }
        }
        List<User> certifiedUsers = getUserForGroup(requesterId, groupAttestationModel.getGroupId());
        if(CollectionUtils.isNotEmpty(certifiedUsers)){
            for(User user : certifiedUsers){
                final IdmAuditLog idmAuditLog = new IdmAuditLog();
                idmAuditLog.setRequestorPrincipal(parentAuditLog.getPrincipal());
                idmAuditLog.setRequestorUserId(parentAuditLog.getUserId());
                idmAuditLog.setAction(AuditAction.RECERTIFICATION_CERTIFY.value());
                idmAuditLog.setTargetUser(user.getId(), user.getDisplayName());
                idmAuditLog.succeed();
                parentAuditLog.addChild(idmAuditLog);
            }
        }


        BasicAjaxResponse ajaxResponse = doDecision(request, response,  decision);

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


    private List<User> getUserForGroup(final String requesterId, String groupId){
        UserSearchBean searchBean = new UserSearchBean();
        searchBean.setRequesterId(requesterId);
        searchBean.addGroupId(groupId);
        searchBean.setDeepCopy(false);

        return  userServiceClient.findBeans(searchBean, -1, -1);
    }

}
