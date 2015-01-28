package org.openiam.ui.webconsole.web.mvc;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openiam.authmanager.common.model.AuthorizationMenu;
import org.openiam.authmanager.util.AuthorizationConstants;
import org.openiam.base.AttributeOperationEnum;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseCode;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.searchbeans.UserSearchBean;
import org.openiam.idm.srvc.audit.constant.AuditAction;
import org.openiam.idm.srvc.audit.constant.AuditSource;
import org.openiam.idm.srvc.audit.dto.IdmAuditLog;
import org.openiam.idm.srvc.grp.dto.Group;
import org.openiam.idm.srvc.mngsys.dto.ManagedSysDto;
import org.openiam.idm.srvc.res.dto.Resource;
import org.openiam.idm.srvc.user.dto.UserStatusEnum;
import org.openiam.provision.dto.ProvisionGroup;
import org.openiam.provision.service.ObjectProvisionService;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.mvc.entitlements.AbstractResourceController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ResourceController extends AbstractResourceController {

    @Value("${org.openiam.ui.landingpage.resource.root.name}")
    private String rootMenu;

    @Value("${org.openiam.ui.resource.edit.menu.name}")
    private String editmenu;

    @Override
    protected String getRootMenu() {
        return rootMenu;
    }

    @javax.annotation.Resource(name = "groupProvisionServiceClient")
    private ObjectProvisionService<ProvisionGroup> groupProvisionService;

    @Override
    protected String getEditMenu() {
        return editmenu;
    }

    @RequestMapping("/menus")
    public String menus(final HttpServletRequest request) throws Exception {
        final String view = super.getResourcePage(request);
        request.setAttribute("targetResourceType", AuthorizationConstants.MENU_ITEM_RESOURCE_TYPE);
        request.setAttribute("excludeResourceType", null);
        return view;
    }

    @Override
    protected BasicAjaxResponse doDelete(final HttpServletRequest request, final HttpServletResponse response,
            final Resource entity) {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        final String callerId = getRequesterId(request);
        IdmAuditLog idmAuditLog = new IdmAuditLog();
        idmAuditLog.setRequestorUserId(callerId);
        idmAuditLog.setAction(AuditAction.DELETE_RESOURCE.value());
        idmAuditLog.setAuditDescription("Delete resource");
        idmAuditLog.setTargetResource(entity.getId(), entity.getName());

        final Response wsResponse = resourceDataService.deleteResource(entity.getId(), callerId);

        if (wsResponse.isSuccess()) {
            ajaxResponse.setStatus(200);
            ajaxResponse.setRedirectURL("resources.html");
            ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.RESOURCE_DELETED));
            idmAuditLog.succeed();
        } else {
            ajaxResponse.setErrorList(getDeleteErrors(wsResponse, request, entity));
            ajaxResponse.setStatus(500);
            idmAuditLog.fail();
            idmAuditLog.setFailureReason(wsResponse.getErrorCode());
            idmAuditLog.setFailureReason(wsResponse.getErrorText());
            idmAuditLog.setTargetResource(entity.getId(), entity.getName());
        }
        auditLogService.addLog(idmAuditLog);
        return ajaxResponse;
    }

    @Override
    protected String handlMenuResource(final HttpServletRequest request, final HttpServletResponse response,
            final String resourceId) {
        final AuthorizationMenu root = authManagerMenuService.getMenuTree(resourceId, getCurrentLanguage());

        String menuTreeString = null;
        try {
            menuTreeString = (root != null) ? jacksonMapper.writeValueAsString(root) : null;
        } catch (Throwable e) {
            log.error("Can't serialize menu tree", e);
        }
        request.setAttribute("editableMenuTree", root);
        request.setAttribute("editableMenuTreeAsString", menuTreeString);
        return "resources/menuTreeView";
    }

    @Override
    protected BasicAjaxResponse doEdit(final HttpServletRequest request, final HttpServletResponse response,
            final @RequestBody org.openiam.idm.srvc.res.dto.Resource resource) {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        String resourceId = null;
        IdmAuditLog idmAuditLog = new IdmAuditLog();
        idmAuditLog.setRequestorUserId(getRequesterId(request));
        if(resource.getId() == null) {
            idmAuditLog.setAction(AuditAction.ADD_RESOURCE.value());
            idmAuditLog.setAuditDescription("Create new resource");
        } else {
            idmAuditLog.setAction(AuditAction.EDIT_RESOURCE.value());
            idmAuditLog.setAuditDescription("Edit new resource");
        }
        final Response wsResponse = resourceDataService.saveResource(resource, getRequesterId(request));

        if (wsResponse.isSuccess()) {
            resourceId = (String) wsResponse.getResponseValue();
            idmAuditLog.setTargetResource(resourceId, resource.getName());
            idmAuditLog.succeed();
        } else {
            ajaxResponse.setErrorList(getEditErrors(wsResponse, request, resource));
            idmAuditLog.fail();
            idmAuditLog.setFailureReason(wsResponse.getErrorCode());
            idmAuditLog.setFailureReason(wsResponse.getErrorText());
            idmAuditLog.setTargetResource(resource.getId(), resource.getName());
        }
        auditLogService.addLog(idmAuditLog);

        if (!ajaxResponse.isError()) {
            ajaxResponse.setStatus(HttpServletResponse.SC_OK);
            ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.RESOURCE_SAVED));
            ajaxResponse.setRedirectURL(String.format("editResource.html?id=%s", resourceId));
        }

        return ajaxResponse;
    }

    @Override
    protected BasicAjaxResponse doAddChildResource(HttpServletRequest request, String resourceId,
            String memberResourceId) {
        final String callerId = getRequesterId(request);
        final Response wsResponse = resourceDataService.addChildResource(resourceId, memberResourceId, callerId);
        return getResponseAfterChildResourceManipulation(wsResponse, false);
    }

    @Override
    protected BasicAjaxResponse doRemoveChildResource(HttpServletRequest request, String resourceId,
            String memberResourceId) {
        final String callerId = getRequesterId(request);
        final Response wsResponse = resourceDataService.deleteChildResource(resourceId, memberResourceId, callerId);
        return getResponseAfterChildResourceManipulation(wsResponse, true);
    }

    @Override
    protected BasicAjaxResponse doAddResource2Role(HttpServletRequest request, String resourceId, String roleId) {
        final String callerId = getRequesterId(request);
        Response wsResponse = resourceDataService.addRoleToResource(resourceId, roleId, callerId);
        if (wsResponse.isSuccess()) {
//            UserSearchBean usb = new UserSearchBean();
//            Set<String> roleIds = new HashSet<String>();
//            roleIds.add(roleId);
//            usb.setRoleIdSet(roleIds);
        }
        return getResponseAfterEntity2EntityAddition(wsResponse, false);
    }

    @Override
    protected BasicAjaxResponse doRemoveResourceFromRole(HttpServletRequest request, String resourceId, String roleId) {
        final String callerId = getRequesterId(request);
        Response wsResponse = resourceDataService.removeRoleToResource(resourceId, roleId, callerId);
        if (wsResponse.isSuccess()) {
//            UserSearchBean usb = new UserSearchBean();
//            Set<String> roleIds = new HashSet<String>();
//            roleIds.add(roleId);
//            usb.setRoleIdSet(roleIds);
        }
        return getResponseAfterEntity2EntityAddition(wsResponse, true);
    }

    @Override
    protected BasicAjaxResponse doAddResource2Group(HttpServletRequest request, String resourceId, String groupId) {
        final String callerId = getRequesterId(request);
        Response wsResponse = resourceDataService.addGroupToResource(resourceId, groupId, callerId);
        if (wsResponse.isSuccess()) {
            Group group = groupServiceClient.getGroup(groupId, callerId);
            ProvisionGroup prGroup = new ProvisionGroup(group);
            Resource res = prGroup.findResource(resourceId);
            if (res != null) {
                res.setOperation(AttributeOperationEnum.ADD);
            }
            Response groupResponse = groupProvisionService.modify(prGroup);
            if(groupResponse.isSuccess()) {
                //UserSearchBean usb = new UserSearchBean();
                //Set<String> groupIds = new HashSet<String>();
                //groupIds.add(groupId);
                //usb.setGroupIdSet(groupIds);
            } else {
                wsResponse = groupResponse;
            }
        }
        return getResponseAfterEntity2EntityAddition(wsResponse, false);
    }

    @Override
    protected BasicAjaxResponse doRemoveResourceFromGroup(HttpServletRequest request, String resourceId, String groupId) {
        final String callerId = getRequesterId(request);
        Response wsResponse = resourceDataService.removeGroupToResource(resourceId, groupId, callerId);
        if (wsResponse.isSuccess()) {
            ManagedSysDto managedSysDto = managedSysServiceClient.getManagedSysByResource(resourceId);
            if(managedSysDto != null) {
                Response groupResponse = groupProvisionService.delete(managedSysDto.getId(), groupId,
                        UserStatusEnum.DELETED, callerId);

                if(groupResponse.isSuccess()) {
                    // identity deleted successfully
                } else if(groupResponse.getErrorCode() == ResponseCode.IDENTITY_NOT_FOUND) {
					// resource deleted, it's not a problem if its identity does not exist
				} else {
                    wsResponse = groupResponse;
                }
            }

        }
        return getResponseAfterEntity2EntityAddition(wsResponse, true);
    }

}
