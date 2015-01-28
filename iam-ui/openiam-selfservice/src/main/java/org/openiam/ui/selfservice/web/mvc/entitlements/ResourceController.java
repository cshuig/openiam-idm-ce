package org.openiam.ui.selfservice.web.mvc.entitlements;

import java.io.IOException;
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
import org.openiam.idm.srvc.res.dto.Resource;
import org.openiam.idm.srvc.role.dto.Role;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.mvc.entitlements.AbstractResourceController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

@Controller
public class ResourceController extends AbstractResourceController {

    @Value("${org.openiam.selfservice.accessmanagment.resource.root}")
    private String rootMenu;

    @Value("${org.openiam.selfservice.accessmanagment.resource.edit.root}")
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
    protected String handlMenuResource(final HttpServletRequest request, final HttpServletResponse response,
            final String resourceId) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Cannot edit menus in Selfservice");
        return null;
    }

    @Override
    protected BasicAjaxResponse doEdit(final HttpServletRequest request, final HttpServletResponse response,
            final Resource entity) throws Exception {
        /*
         * final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
         * Response wsResponse = validateEdit(resource); final List<ErrorToken>
         * errorList = getEditErrors(wsResponse, request, resource);
         * if(CollectionUtils.isEmpty(errorList)) { final String description =
         * (StringUtils.isNotBlank(resource.getId())) ?
         * String.format("Edit Resoruce %s", resource.getName()) :
         * "Create New Resource"; final ActivitiRequestType requestType =
         * StringUtils.isNotBlank(resource.getId()) ?
         * ActivitiRequestType.EDIT_RESOURCE : ActivitiRequestType.NEW_RESOURCE;
         * 
         * wsResponse = makeEditEntityActivitiRequest(resource, requestType,
         * AssociationType.RESOURCE, ActivitiConstants.RESOURCE, request,
         * description, description); if(wsResponse != null &&
         * wsResponse.isFailure()) { errorList.add(new
         * ErrorToken(Errors.INTERNAL_ERROR)); if(wsResponse.getErrorCode() !=
         * null) { switch(wsResponse.getErrorCode()) { default: break; } } } }
         * 
         * if(CollectionUtils.isNotEmpty(errorList)) {
         * ajaxResponse.setErrorList(errorList);
         * ajaxResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
         * } else { ajaxResponse.setRedirectURL("resources.html");
         * ajaxResponse.setSuccessToken(new
         * SuccessToken(SuccessMessage.WORKFLOW_INITIATED));
         * ajaxResponse.setStatus(200); } return ajaxResponse;
         */
        return makeCRUDRequest(request, entity, false, "resources.html");
    }

    @Override
    protected BasicAjaxResponse doDelete(final HttpServletRequest request, final HttpServletResponse response,
            final Resource entity) throws Exception {
        return makeCRUDRequest(request, entity, true, "resources.html");
        /*
         * final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
         * Response wsResponse = validateDelete(resourceId); final
         * List<ErrorToken> errorList = getDeleteErrors(wsResponse, request);
         * if(CollectionUtils.isEmpty(errorList)) { final Resource resource =
         * getEntity(resourceId, request); final String description =
         * String.format("Delete Resource %s", resource.getName());
         * 
         * wsResponse = makeDeleteEntityActivitiRequest(request,
         * ActivitiRequestType.DELETE_RESOURCE, ActivitiConstants.RESOURCE_ID,
         * AssociationType.RESOURCE, resource, description, description);
         * if(wsResponse != null && wsResponse.isFailure()) { errorList.add(new
         * ErrorToken(Errors.INTERNAL_ERROR)); if(wsResponse.getErrorCode() !=
         * null) { switch(wsResponse.getErrorCode()) { default: break; } } } }
         * 
         * if(CollectionUtils.isNotEmpty(errorList)) {
         * ajaxResponse.setErrorList(errorList);
         * ajaxResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
         * } else { ajaxResponse.setRedirectURL("resources.html");
         * ajaxResponse.setSuccessToken(new
         * SuccessToken(SuccessMessage.WORKFLOW_INITIATED));
         * ajaxResponse.setStatus(200); } return ajaxResponse;
         */
    }

    @Override
    protected BasicAjaxResponse doAddChildResource(HttpServletRequest request, String resourceId,
            String memberResourceId) {
        final Resource entity = getEntity(resourceId, request);
        final Resource member = getEntity(memberResourceId, request);
        return makeMembershipRequest(request, entity, member, true);
    }

    @Override
    protected BasicAjaxResponse doRemoveChildResource(HttpServletRequest request, String resourceId,
            String memberResourceId) {
        final Resource entity = getEntity(resourceId, request);
        final Resource member = getEntity(memberResourceId, request);
        return makeMembershipRequest(request, entity, member, false);
    }

    @Override
    protected BasicAjaxResponse doAddResource2Role(HttpServletRequest request, String resourceId, String roleId) {
        final Role member = roleServiceClient.getRoleLocalized(roleId, getRequesterId(request), getCurrentLanguage());
        final Resource entity = getEntity(resourceId, request);
        return makeMembershipRequest(request, entity, member, true);
    }

    @Override
    protected BasicAjaxResponse doRemoveResourceFromRole(HttpServletRequest request, String resourceId, String roleId) {
        final Role member = roleServiceClient.getRoleLocalized(roleId, getRequesterId(request), getCurrentLanguage());
        final Resource entity = getEntity(resourceId, request);
        return makeMembershipRequest(request, entity, member, false);
    }

    @Override
    protected BasicAjaxResponse doAddResource2Group(HttpServletRequest request, String resourceId, String groupId) {
        final Group member = groupServiceClient.getGroup(groupId, getRequesterId(request));
        final Resource entity = getEntity(resourceId, request);
        return makeMembershipRequest(request, entity, member, true);
    }

    @Override
    protected BasicAjaxResponse doRemoveResourceFromGroup(HttpServletRequest request, String resourceId, String groupId) {
        final Group member = groupServiceClient.getGroup(groupId, getRequesterId(request));
        final Resource entity = getEntity(resourceId, request);
        return makeMembershipRequest(request, entity, member, false);
    }

}
