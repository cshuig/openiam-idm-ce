package org.openiam.ui.webconsole.web.mvc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openiam.base.ws.Response;
import org.openiam.idm.searchbeans.RoleSearchBean;
import org.openiam.idm.srvc.audit.constant.AuditAction;
import org.openiam.idm.srvc.audit.dto.IdmAuditLog;
import org.openiam.idm.srvc.grp.dto.Group;
import org.openiam.idm.srvc.role.dto.Role;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.mvc.entitlements.AbstractRoleController;
import org.openiam.ui.webconsole.web.provider.MenuNodeViewProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class RoleController extends AbstractRoleController {
	
	@Autowired
	private MenuNodeViewProvider menuNodeViewProvider;
	
	@Value("${org.openiam.ui.landingpage.role.root.name}")
	private String roleRootMenuName;
	
	@Value("${org.openiam.ui.role.edit.menu.name}")
	private String roleEditMenuName;
	
	@Override
	protected String getRootMenu() {
		return roleRootMenuName;
	}

	@Override
	protected String getEditMenu() {
		return roleEditMenuName;
	}
	
	@RequestMapping(value="/roleMenuTree", method=RequestMethod.GET)
	public String roleMenuTree(final HttpServletRequest request,
							   final HttpServletResponse response,
							   final @RequestParam(value="id") String roleId) throws IOException {
        String requesterId = getRequesterId(request);

		final RoleSearchBean searchBean = new RoleSearchBean();
		searchBean.setKey(roleId);
		searchBean.setDeepCopy(false);
		final List<Role> beans = roleServiceClient.findBeans(searchBean,requesterId, 0, 1);
		if(CollectionUtils.isEmpty(beans)) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, String.format("Role with ID '%s' not found", roleId));
			return null;
		}
		
		final Role role = beans.get(0);
		
		setMenuTree(request, roleEditMenuName);
		return menuNodeViewProvider.menuTreeEntitlementsRequest(request, "role", roleId, role.getName());
	}

	@Override
	protected BasicAjaxResponse doEdit(HttpServletRequest request,
			HttpServletResponse response, Role role) throws Exception {
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        IdmAuditLog idmAuditLog = new IdmAuditLog();
        idmAuditLog.setRequestorUserId(getRequesterId(request));
        if(role.getId() == null) {
            idmAuditLog.setAction(AuditAction.ADD_ROLE.value());
            idmAuditLog.setAuditDescription("Create new role");
        } else {
            idmAuditLog.setAction(AuditAction.EDIT_ROLE.value());
            idmAuditLog.setAuditDescription("Edit new role");
        }

		final Response wsResponse = roleServiceClient.saveRole(role, getRequesterId(request));
		if(wsResponse.isSuccess()) {
            String roleId = (String) wsResponse.getResponseValue();
			ajaxResponse.setStatus(200);
			ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.ROLE_SAVED));
			if(StringUtils.isBlank(role.getId())) {
				ajaxResponse.setRedirectURL(new StringBuilder("editRole.html?id=").append(wsResponse.getResponseValue()).toString());
			}
            idmAuditLog.setTargetRole(roleId, role.getName());
            idmAuditLog.succeed();
		} else {
			final List<ErrorToken> errorList = getEditErrors(wsResponse, request, role);
			ajaxResponse.setErrorList(errorList);
			ajaxResponse.setStatus(500);
            idmAuditLog.fail();
            idmAuditLog.setFailureReason(wsResponse.getErrorCode());
            idmAuditLog.setFailureReason(wsResponse.getErrorText());
            idmAuditLog.setTargetRole(role.getId(), role.getName());
		}
        auditLogService.addLog(idmAuditLog);
		return ajaxResponse;
	}

	@Override
	protected BasicAjaxResponse doDelete(HttpServletRequest request,
			HttpServletResponse response, Role entity) throws Exception {
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        final String callerId = getRequesterId(request);
        IdmAuditLog idmAuditLog = new IdmAuditLog();
        idmAuditLog.setRequestorUserId(callerId);
        idmAuditLog.setAction(AuditAction.DELETE_ROLE.value());
        idmAuditLog.setAuditDescription("Delete role");
        idmAuditLog.setTargetResource(entity.getId(), entity.getName());

		final Response wsResponse = roleServiceClient.removeRole(entity.getId(), callerId);
		if(wsResponse.isSuccess()) {
			ajaxResponse.setStatus(200);
			ajaxResponse.setRedirectURL("roles.html");
			ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.ROLE_DELETE));
            idmAuditLog.succeed();
		} else {
			ajaxResponse.setErrorList(getDeleteErrors(wsResponse, request, entity));
            idmAuditLog.fail();
            idmAuditLog.setFailureReason(wsResponse.getErrorCode());
            idmAuditLog.setFailureReason(wsResponse.getErrorText());
            idmAuditLog.setTargetResource(entity.getId(), entity.getName());
		}
        auditLogService.addLog(idmAuditLog);
		
		return ajaxResponse;
	}

	@Override
	protected BasicAjaxResponse doAddRole2Role(HttpServletRequest request,
			String roleId, String childRoleId) {
        final String callerId = getRequesterId(request);
        IdmAuditLog idmAuditLog = new IdmAuditLog();
        idmAuditLog.setRequestorUserId(callerId);
        idmAuditLog.setAction(AuditAction.ADD_CHILD_ROLE.value());
        idmAuditLog.setAuditDescription("Add child role");

        Role roleParent = roleServiceClient.getRole(roleId, callerId);
        idmAuditLog.setTargetRole(roleParent.getId(), roleParent.getName());
        Role roleChild = roleServiceClient.getRole(childRoleId, callerId);
        idmAuditLog.setTargetRole(roleChild.getId(), roleChild.getName());

		final Response wsResponse = roleServiceClient.addChildRole(roleId, childRoleId, callerId);
        if(wsResponse.isSuccess()) {
            idmAuditLog.succeed();
        } else {
            idmAuditLog.fail();
            idmAuditLog.setFailureReason(wsResponse.getErrorCode());
            idmAuditLog.setFailureReason(wsResponse.getErrorText());
        }
        auditLogService.addLog(idmAuditLog);
        return getResponseAfterEntity2EntityAddition(wsResponse, false);
	}

	@Override
	protected BasicAjaxResponse doRemoveRoleFromRole(
			HttpServletRequest request, String roleId, String childRoleId) {
        final String callerId = getRequesterId(request);
        IdmAuditLog idmAuditLog = new IdmAuditLog();
        idmAuditLog.setRequestorUserId(callerId);
        idmAuditLog.setAction(AuditAction.REMOVE_CHILD_ROLE.value());
        idmAuditLog.setAuditDescription("Remove child role");

        Role roleParent = roleServiceClient.getRole(roleId, callerId);
        idmAuditLog.setTargetRole(roleParent.getId(), roleParent.getName());
        Role roleChild = roleServiceClient.getRole(childRoleId, callerId);
        idmAuditLog.setTargetRole(roleChild.getId(), roleChild.getName());

        final Response wsResponse = roleServiceClient.removeChildRole(roleId, childRoleId, callerId);

        if(wsResponse.isSuccess()) {
            idmAuditLog.succeed();
        } else {
            idmAuditLog.fail();
            idmAuditLog.setFailureReason(wsResponse.getErrorCode());
            idmAuditLog.setFailureReason(wsResponse.getErrorText());
        }
        auditLogService.addLog(idmAuditLog);
        return getResponseAfterEntity2EntityAddition(wsResponse, true);
	}

	@Override
	protected BasicAjaxResponse doAddRole2Group(HttpServletRequest request,
			String roleId, String groupId) {
        final String callerId = getRequesterId(request);
        IdmAuditLog idmAuditLog = new IdmAuditLog();
        idmAuditLog.setRequestorUserId(callerId);
        idmAuditLog.setAction(AuditAction.ADD_ROLE_TO_GROUP.value());
        idmAuditLog.setAuditDescription("Add role to group");

        Role roleParent = roleServiceClient.getRole(roleId, callerId);
        idmAuditLog.setTargetRole(roleParent.getId(), roleParent.getName());
        Group group = groupServiceClient.getGroup(groupId, callerId);
        idmAuditLog.setTargetGroup(group.getId(), group.getName());

		final Response wsResponse = roleServiceClient.addGroupToRole(roleId, groupId, callerId);

        if(wsResponse.isSuccess()) {
            idmAuditLog.succeed();
        } else {
            idmAuditLog.fail();
            idmAuditLog.setFailureReason(wsResponse.getErrorCode());
            idmAuditLog.setFailureReason(wsResponse.getErrorText());
        }
        auditLogService.addLog(idmAuditLog);
        return getResponseAfterEntity2EntityAddition(wsResponse, false);
	}

	@Override
	protected BasicAjaxResponse doRemoveRoleFromGroup(
			HttpServletRequest request, String roleId, String groupId) {
        final String callerId = getRequesterId(request);
        IdmAuditLog idmAuditLog = new IdmAuditLog();
        idmAuditLog.setRequestorUserId(callerId);
        idmAuditLog.setAction(AuditAction.REMOVE_ROLE_FROM_GROUP.value());
        idmAuditLog.setAuditDescription("Remove role from group");
        Role roleParent = roleServiceClient.getRole(roleId, callerId);
        idmAuditLog.setTargetRole(roleParent.getId(), roleParent.getName());
        Group group = groupServiceClient.getGroup(groupId, callerId);
        idmAuditLog.setTargetGroup(group.getId(), group.getName());

		final Response wsResponse = roleServiceClient.removeGroupFromRole(roleId, groupId, callerId);

        if(wsResponse.isSuccess()) {
            idmAuditLog.succeed();
        } else {
            idmAuditLog.fail();
            idmAuditLog.setFailureReason(wsResponse.getErrorCode());
            idmAuditLog.setFailureReason(wsResponse.getErrorText());
        }
        return getResponseAfterEntity2EntityAddition(wsResponse, true);
	}
}
