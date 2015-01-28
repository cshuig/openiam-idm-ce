package org.openiam.ui.selfservice.web.mvc.entitlements;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openiam.base.ws.Response;
import org.openiam.bpm.activiti.ActivitiService;
import org.openiam.bpm.request.GenericWorkflowRequest;
import org.openiam.bpm.util.ActivitiConstants;
import org.openiam.bpm.util.ActivitiRequestType;
import org.openiam.idm.srvc.grp.dto.Group;
import org.openiam.idm.srvc.mngsys.domain.AssociationType;
import org.openiam.idm.srvc.role.dto.Role;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.mvc.entitlements.AbstractRoleController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

@Controller
public class RoleController extends AbstractRoleController {
	
	@Value("${org.openiam.selfservice.accessmanagment.role.root}")
	private String rootMenu;
	
	@Value("${org.openiam.selfservice.accessmanagment.role.edit.root}")
	private String editMenu;
	
	@Override
	protected String getRootMenu() {
		return rootMenu;
	}

	@Override
	protected String getEditMenu() {
		return editMenu;
	}

	@Override
	protected BasicAjaxResponse doEdit(HttpServletRequest request,
			HttpServletResponse response, Role entity) throws Exception {
		/*
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		Response wsResponse = roleServiceClient.validateEdit(role);
		List<ErrorToken> tokenList = getEditErrors(wsResponse, request, role);
		if(CollectionUtils.isEmpty(tokenList)) {
			final String description = (StringUtils.isNotBlank(role.getId())) ? 
					String.format("Edit Role %s", role.getName()) :
					"Create New Role";
			final ActivitiRequestType requestType = StringUtils.isNotBlank(role.getId()) ? 
					ActivitiRequestType.EDIT_ROLE : ActivitiRequestType.NEW_ROLE;
			wsResponse = makeEditEntityActivitiRequest(role, requestType, AssociationType.ROLE, ActivitiConstants.ROLE, request, description, description);
			if(wsResponse != null && wsResponse.isFailure()) {
				tokenList = new LinkedList<ErrorToken>();
				ErrorToken token = new ErrorToken(Errors.INTERNAL_ERROR);
				if(wsResponse.getErrorCode() != null) {
					switch(wsResponse.getErrorCode()) {
						default:
							break;
					}
				}
				tokenList.add(token);
			}
		}
		
		if(CollectionUtils.isNotEmpty(tokenList)) {
			ajaxResponse.addErrors(tokenList);
			ajaxResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} else {
			ajaxResponse.setRedirectURL("roles.html");
			ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.WORKFLOW_INITIATED));
			ajaxResponse.setStatus(200);
		}
		return ajaxResponse;
		*/
		return makeCRUDRequest(request, entity, false, "roles.html");
	}

	@Override
	protected BasicAjaxResponse doDelete(HttpServletRequest request,
			HttpServletResponse response, Role entity) throws Exception {
		/*
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		Response wsResponse = validateDelete(roleId);
		List<ErrorToken> tokenList = getDeleteErrors(wsResponse, request);
		if(CollectionUtils.isEmpty(tokenList)) {
			final Role entity = roleServiceClient.getRole(roleId, getRequesterId(request));
			if(entity == null) {
				tokenList = new LinkedList<ErrorToken>();
				tokenList.add(new ErrorToken(Errors.UNAUTHORIZED));
			} else {
				final String description = String.format("Delete Role %s", entity.getName());
				
				wsResponse = makeDeleteEntityActivitiRequest(request, ActivitiRequestType.DELETE_ROLE, ActivitiConstants.ROLE_ID, AssociationType.ROLE, entity, description, description);
				if(wsResponse != null && wsResponse.isFailure()) {
					tokenList = new LinkedList<ErrorToken>();
					ErrorToken token = new ErrorToken(Errors.INTERNAL_ERROR);
					if(wsResponse.getErrorCode() != null) {
						switch(wsResponse.getErrorCode()) {
							default:
								break;
						}
					}
					tokenList.add(token);
				}
			}
		}
		
		if(CollectionUtils.isNotEmpty(tokenList)) {
			ajaxResponse.addErrors(tokenList);
			ajaxResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} else {
			ajaxResponse.setRedirectURL("roles.html");
			ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.WORKFLOW_INITIATED));
			ajaxResponse.setStatus(200);
		}
		return ajaxResponse;
		*/
		return makeCRUDRequest(request, entity, true, "roles.html");
	}

	@Override
	protected BasicAjaxResponse doAddRole2Role(HttpServletRequest request,
			String roleId, String childRoleId) {
		final Role entity = getEntity(roleId, request);
		final Role member = getEntity(childRoleId, request);
		return makeMembershipRequest(request, entity, member, true);
	}

	@Override
	protected BasicAjaxResponse doRemoveRoleFromRole(
			HttpServletRequest request, String roleId, String childRoleId) {
		final Role entity = getEntity(roleId, request);
		final Role member = getEntity(childRoleId, request);
		return makeMembershipRequest(request, entity, member, false);
	}

	@Override
	protected BasicAjaxResponse doAddRole2Group(HttpServletRequest request,
			String roleId, String groupId) {
		final Group entity = groupServiceClient.getGroup(groupId, getRequesterId(request));
		final Role member = getEntity(roleId, request);
		return makeMembershipRequest(request, entity, member, true);
	}

	@Override
	protected BasicAjaxResponse doRemoveRoleFromGroup(
			HttpServletRequest request, String roleId, String groupId) {
		final Group entity = groupServiceClient.getGroup(groupId, getRequesterId(request));
		final Role member = getEntity(roleId, request);
		return makeMembershipRequest(request, entity, member, false);
	}

}
