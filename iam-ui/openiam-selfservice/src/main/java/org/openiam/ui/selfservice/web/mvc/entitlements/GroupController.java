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
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.mvc.entitlements.AbstractGroupController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

@Controller
public class GroupController extends AbstractGroupController {
	
	@Value("${org.openiam.selfservice.accessmanagment.group.root}")
	private String rootMenu;
	
	@Value("${org.openiam.selfservice.accessmanagment.group.edit.root}")
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
	protected BasicAjaxResponse doDelete(HttpServletRequest request, HttpServletResponse response, Group entity) throws Exception {
		/*
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		Response wsResponse = validateDelete(groupId);
		List<ErrorToken> tokenList = getDeleteErrors(wsResponse, request);
		if(CollectionUtils.isEmpty(tokenList)) {
			final Group group = groupServiceClient.getGroup(groupId, getRequesterId(request));
			if(group == null) {
				tokenList = new LinkedList<ErrorToken>();
				tokenList.add(new ErrorToken(Errors.UNAUTHORIZED));
			} else {
				final String description = String.format("Delete Group %s", group.getName());
				
				wsResponse = makeDeleteEntityActivitiRequest(request, ActivitiRequestType.DELETE_GROUP, ActivitiConstants.GROUP_ID, AssociationType.GROUP, group, description, description);
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
			ajaxResponse.setRedirectURL("groups.html");
			ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.WORKFLOW_INITIATED));
			ajaxResponse.setStatus(200);
		}
		return ajaxResponse;
		*/
		
		return makeCRUDRequest(request, entity, true, "groups.html");
	}

	@Override
	protected BasicAjaxResponse doEdit(HttpServletRequest request, HttpServletResponse response, final Group entity) throws Exception {
		/*
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		Response wsResponse = groupServiceClient.validateEdit(group);
		List<ErrorToken> tokenList = getEditErrors(wsResponse, request, group);
		if(CollectionUtils.isEmpty(tokenList)) {
			final String description = (StringUtils.isNotBlank(group.getId())) ? 
					String.format("Edit Group %s", group.getName()) :
					"Create New Group";
			final ActivitiRequestType requestType = StringUtils.isNotBlank(group.getId()) ? 
					ActivitiRequestType.EDIT_GROUP : ActivitiRequestType.NEW_GROUP;
			wsResponse = makeEditEntityActivitiRequest(group, requestType, AssociationType.GROUP, ActivitiConstants.GROUP, request, description, description);
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
			ajaxResponse.setRedirectURL("groups.html");
			ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.WORKFLOW_INITIATED));
			ajaxResponse.setStatus(200);
		}
		return ajaxResponse;
		*/
		return makeCRUDRequest(request, entity, false, "groups.html");
	}

	@Override
	protected BasicAjaxResponse addGroup2Group(HttpServletRequest request, String groupId, String childGroupId) {
		final Group entity = getEntity(groupId, request);
		final Group member = getEntity(childGroupId, request);
		return makeMembershipRequest(request, entity, member, true);
	}

	@Override
	protected BasicAjaxResponse removeGroupFromGroup(
			HttpServletRequest request, String groupId, String childGroupId) {
		final Group entity = getEntity(groupId, request);
		final Group member = getEntity(childGroupId, request);
		return makeMembershipRequest(request, entity, member, false);
	}
}
