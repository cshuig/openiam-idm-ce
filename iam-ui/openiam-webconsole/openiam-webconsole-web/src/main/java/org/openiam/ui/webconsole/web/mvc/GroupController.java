package org.openiam.ui.webconsole.web.mvc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.searchbeans.GroupSearchBean;
import org.openiam.idm.srvc.audit.constant.AuditAction;
import org.openiam.idm.srvc.audit.constant.AuditSource;
import org.openiam.idm.srvc.audit.dto.IdmAuditLog;
import org.openiam.idm.srvc.auth.dto.IdentityDto;
import org.openiam.idm.srvc.auth.ws.IdentityWebService;
import org.openiam.idm.srvc.grp.dto.Group;
import org.openiam.provision.dto.ProvisionGroup;
import org.openiam.provision.service.ObjectProvisionService;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.mvc.entitlements.AbstractGroupController;
import org.openiam.ui.webconsole.web.provider.MenuNodeViewProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class GroupController extends AbstractGroupController {
	
	private static final Log log = LogFactory.getLog(GroupController.class);
	
	@Value("${org.openiam.ui.landingpage.group.root.name}")
	private String groupRootMenuName;
	
	@Value("${org.openiam.ui.group.edit.menu.name}")
	private String groupEditMenuName;

    @Resource(name = "groupProvisionServiceClient")
    private ObjectProvisionService<ProvisionGroup> groupProvisionService;

	@Autowired
	private MenuNodeViewProvider menuNodeViewProvider;

    @Resource(name = "identityServiceClient")
    private IdentityWebService identityService;

	@Override
	protected String getRootMenu() {
		return groupRootMenuName;
	}

	@Override
	protected String getEditMenu() {
		return groupEditMenuName;
	}
	
	@Override
	protected BasicAjaxResponse doDelete(final HttpServletRequest request, final HttpServletResponse response, final Group entity) throws Exception {
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        final String callerId = getRequesterId(request);
        IdmAuditLog idmAuditLog = new IdmAuditLog();
        idmAuditLog.setRequestorUserId(callerId);
        idmAuditLog.setAction(AuditAction.DELETE_GROUP.value());
        idmAuditLog.setAuditDescription("Delete group");
        idmAuditLog.setTargetGroup(entity.getId(), entity.getName());

        List<IdentityDto> identityDtos = identityService.getIdentities(entity.getId());
        Response wsResponse = null;
        if(identityDtos != null && identityDtos.size() > 0) {
            wsResponse = groupProvisionService.remove(entity.getId(), callerId);
        } else {
            wsResponse = groupServiceClient.deleteGroup(entity.getId(), callerId);
        }

        if(wsResponse.isSuccess()) {
            new Response(ResponseStatus.SUCCESS);
			ajaxResponse.setStatus(200);
			ajaxResponse.setRedirectURL("groups.html");
			ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.GROUP_DELETED));
            idmAuditLog.succeed();
		} else {
			ajaxResponse.addErrors(getDeleteErrors(wsResponse, request, entity));
            idmAuditLog.fail();
            idmAuditLog.setFailureReason(wsResponse.getErrorCode());
            idmAuditLog.setFailureReason(wsResponse.getErrorText());
		}
        auditLogService.addLog(idmAuditLog);

		return ajaxResponse;
	}
	
	@Override
	protected BasicAjaxResponse doEdit(final HttpServletRequest request, final HttpServletResponse response, final Group group) throws Exception {
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();

        String requesterId = getRequesterId(request);
        IdmAuditLog idmAuditLog = new IdmAuditLog();
        idmAuditLog.setRequestorUserId(getRequesterId(request));
        idmAuditLog.setRequestorPrincipal(getRequesterPrincipal(request));
        boolean isNew = group.getId() == null;
        if(isNew) {
            idmAuditLog.setAction(AuditAction.ADD_GROUP.value());
            idmAuditLog.setAuditDescription("Create new group");
        } else {
            idmAuditLog.setAction(AuditAction.EDIT_GROUP.value());
            idmAuditLog.setAuditDescription("Edit new group");
        }

		final Response wsResponse = groupServiceClient.saveGroup(group, requesterId);

		if(wsResponse.isSuccess()) {
            String groupId = (String) wsResponse.getResponseValue();
            Group createdGroup  = groupServiceClient.getGroup(groupId, requesterId);
            ProvisionGroup provisionGroup = new ProvisionGroup(createdGroup);
            Response groupResponse = isNew ? groupProvisionService.add(provisionGroup):
                    groupProvisionService.modify(provisionGroup);
            //TODO group provisioning processing the result

            idmAuditLog.setTargetGroup(groupId, group.getName());
			ajaxResponse.setStatus(200);
			ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.GROUP_SAVED));
			if(StringUtils.isBlank(group.getId())) {
				ajaxResponse.setRedirectURL(new StringBuilder("editGroup.html?id=").append(wsResponse.getResponseValue()).toString());
			}
            idmAuditLog.succeed();
		} else {
			ajaxResponse.addErrors(getEditErrors(wsResponse, request, group));
            idmAuditLog.fail();
            idmAuditLog.setFailureReason(wsResponse.getErrorCode());
            idmAuditLog.setFailureReason(wsResponse.getErrorText());
            idmAuditLog.setTargetGroup(group.getId(), group.getName());
		}
        auditLogService.addLog(idmAuditLog);
		return ajaxResponse;
	}
	
	@RequestMapping(value="/groupMenuTree", method=RequestMethod.GET)
	public String groupMenuTree(final HttpServletRequest request,
								final HttpServletResponse response,
								final @RequestParam(value="id") String groupId) throws IOException {
        String requesterId = getRequesterId(request);

		final GroupSearchBean searchBean = new GroupSearchBean();
		searchBean.setKey(groupId);
		searchBean.setDeepCopy(false);
		
		final List<Group> beans = groupServiceClient.findBeans(searchBean,requesterId, 0, 1);
		if(CollectionUtils.isEmpty(beans)) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, String.format("Group with ID '%s' not found", groupId));
			return null;
		}
		
		final Group group = beans.get(0);
		setMenuTree(request, groupEditMenuName);
		return menuNodeViewProvider.menuTreeEntitlementsRequest(request, "group", groupId, group.getName());
	}

	@Override
	protected BasicAjaxResponse addGroup2Group(HttpServletRequest request,
			String groupId, String childGroupId) {
        final String callerId = getRequesterId(request);
		final Response wsResponse = groupServiceClient.addChildGroup(groupId, childGroupId, callerId);
        return getResponseAfterEntity2EntityAddition(wsResponse, false);
	}

	@Override
	protected BasicAjaxResponse removeGroupFromGroup(
			HttpServletRequest request, String groupId, String childGroupId) {
        final String callerId = getRequesterId(request);
		final Response wsResponse = groupServiceClient.removeChildGroup(groupId, childGroupId.toLowerCase(), callerId);
        return getResponseAfterEntity2EntityAddition(wsResponse, true);
	}

	
}
