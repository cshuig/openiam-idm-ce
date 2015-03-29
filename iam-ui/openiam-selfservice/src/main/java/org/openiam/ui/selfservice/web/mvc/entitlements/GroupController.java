package org.openiam.ui.selfservice.web.mvc.entitlements;

import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.openiam.authmanager.service.AuthorizationManagerAdminWebService;
import org.openiam.base.ws.Response;
import org.openiam.bpm.activiti.ActivitiService;
import org.openiam.bpm.request.GenericWorkflowRequest;
import org.openiam.bpm.util.ActivitiConstants;
import org.openiam.bpm.util.ActivitiRequestType;
import org.openiam.idm.srvc.grp.dto.Group;
import org.openiam.idm.srvc.mngsys.domain.AssociationType;
import org.openiam.idm.srvc.prov.request.dto.BulkOperationEnum;
import org.openiam.idm.srvc.prov.request.dto.BulkOperationObjectType;
import org.openiam.idm.srvc.prov.request.dto.BulkOperationRequest;
import org.openiam.provision.service.AsynchUserProvisionService;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.model.BulkOperationBean;
import org.openiam.ui.web.mvc.entitlements.AbstractGroupController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class GroupController extends AbstractGroupController {
	@Resource(name = "authManagerAdminClient")
	private AuthorizationManagerAdminWebService authWebService;

	@Value("${org.openiam.selfservice.accessmanagment.group.root}")
	private String rootMenu;
	
	@Value("${org.openiam.selfservice.accessmanagment.group.edit.root}")
	private String editMenu;

    @Resource(name="asynchProvisionServiceClient")
    protected AsynchUserProvisionService asynchProvisionService;

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
		Set<String> groupOwnerIds =  authWebService.getOwnerIdsForGroup(entity.getId());
		String requestorId = getRequesterId(request);
		if(CollectionUtils.isNotEmpty(groupOwnerIds) && groupOwnerIds.contains(requestorId)){
			return super.doDelete(request,  response,  entity);
		} else {
			return makeCRUDRequest(request, entity, true, groupOwnerIds, "groups.html");
		}
		

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
		Set<String> groupOwnerIds =  authWebService.getOwnerIdsForGroup(entity.getId());
		String requestorId = getRequesterId(request);
		if(CollectionUtils.isNotEmpty(groupOwnerIds) && groupOwnerIds.contains(requestorId)){
			return doEdit(request,  response,  entity, "groups.html");
		} else {
			return makeCRUDRequest(request, entity, false, groupOwnerIds, "groups.html");
		}
	}

	@Override
	protected BasicAjaxResponse addGroup2Group(HttpServletRequest request, String groupId, String childGroupId) {
		Set<String> groupOwnerIds =  authWebService.getOwnerIdsForGroup(groupId);
		String requestorId = getRequesterId(request);
		if(CollectionUtils.isNotEmpty(groupOwnerIds) && groupOwnerIds.contains(requestorId)){
			return super.addGroup2Group(request, groupId, childGroupId);
		} else {
			final Group entity = getEntity(groupId, request);
			final Group member = getEntity(childGroupId, request);
			return makeMembershipRequest(request, entity, member, true);
		}
	}

	@Override
	protected BasicAjaxResponse removeGroupFromGroup(
			HttpServletRequest request, String groupId, String childGroupId) {
		Set<String> groupOwnerIds =  authWebService.getOwnerIdsForGroup(groupId);
		String requestorId = getRequesterId(request);
		if(CollectionUtils.isNotEmpty(groupOwnerIds) && groupOwnerIds.contains(requestorId)){
			return super.removeGroupFromGroup(request, groupId, childGroupId);
		} else {
			final Group entity = getEntity(groupId, request);
			final Group member = getEntity(childGroupId, request);
			return makeMembershipRequest(request, entity, member, false);
		}
	}

	@RequestMapping(value = "/groupsBulkOperations")
	public String getChooseGroupView(final HttpServletRequest request) throws IOException {
		setMenuTree(request, getRootMenu());
		request.setAttribute("managedSystems", jacksonMapper.writeValueAsString(getManagedSystemsAsKeyNameBeans()));
		request.setAttribute("requesterId", getRequesterId(request));
		return "groupBulkOp/selectGroup";
	}

    @RequestMapping(value = "/selectUsers")
    public String getChooseUsersView(final HttpServletRequest request,
                                     final HttpServletResponse response,
                                     final @RequestParam(value="id", required=true) String groupId) throws IOException {
        String requesterId = getRequesterId(request);
        final Group group = groupServiceClient.getGroup(groupId, requesterId);
        if(group == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, String.format("Group '%s' does not exist", groupId));
            return null;
        }
        request.setAttribute("group", group);

        List<String> operations = new LinkedList<String>();
        operations.add(BulkOperationEnum.ADD_ENTITLEMENT.name());
        operations.add(BulkOperationEnum.DELETE_ENTITLEMENT.name());

        setMenuTree(request, getRootMenu());
        request.setAttribute("operations", operations);
        request.setAttribute("requesterId", requesterId);

        return "groupBulkOp/selectUsers";
    }


    @RequestMapping(value = "/startGroupBulkOperation", method = RequestMethod.POST)
    public @ResponseBody BasicAjaxResponse startGroupBulkOperation(final HttpServletRequest request,
                                                                    final @RequestBody BulkOperationBean bulkOperationBean) throws IOException {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        final List<ErrorToken> errorList = new LinkedList<ErrorToken>();
        String requesterId = getRequesterId(request);
        if (CollectionUtils.isNotEmpty(bulkOperationBean.getUserIds()) ) {

            BulkOperationRequest bulkOperationRequest = new BulkOperationRequest();
            bulkOperationRequest.setUserIds(bulkOperationBean.getUserIds());
            bulkOperationRequest.setOperations(bulkOperationBean.getOperations());
            bulkOperationRequest.setRequesterId(requesterId);

            asynchProvisionService.startBulkOperation(bulkOperationRequest);

            ajaxResponse.setStatus(200);
            ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.GROUP_BULK_OPERATION_STARTED));
            ajaxResponse.setSuccessMessage(messageSource.getMessage(
                    ajaxResponse.getSuccessToken().getMessage().getMessageName(), null,
                    localeResolver.resolveLocale(request)));

            ajaxResponse.setRedirectURL("groupsBulkOperations.html");

        } else {
            ajaxResponse.setStatus(500);
            ErrorToken errorToken = new ErrorToken(Errors.USERS_NOT_SELECTED);
            errorToken.setMessage(messageSource.getMessage(
                    errorToken.getError().getMessageName(),
                    errorToken.getParams(),
                    localeResolver.resolveLocale(request)));
            errorList.add(errorToken);
            ajaxResponse.setErrorList(errorList);
        }
        return ajaxResponse;
    }
}
