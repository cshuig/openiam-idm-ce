package org.openiam.ui.webconsole.web.mvc;

import org.openiam.base.AttributeOperationEnum;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseCode;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.srvc.audit.constant.AuditAction;
import org.openiam.idm.srvc.audit.constant.AuditSource;
import org.openiam.idm.srvc.audit.dto.IdmAuditLog;
import org.openiam.idm.srvc.grp.dto.Group;
import org.openiam.idm.srvc.org.dto.Organization;
import org.openiam.idm.srvc.role.dto.Role;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.provision.dto.ProvisionUser;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.mvc.entitlements.AbstractUserEntitlementsController;
import org.openiam.ui.webconsole.util.WSUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserEntitlementsController extends AbstractUserEntitlementsController {

    @Value("${org.openiam.ui.landingpage.user.edit.root.id}")
    protected String menu;

    @Value("${org.openiam.provision.service.user.entitlements.flag}")
    protected Boolean provisionUserEntitlementsFlag;

	@Override
	protected BasicAjaxResponse user2OrganizationOperation(
			HttpServletRequest request, String userId, String organizationId,
			boolean isAddition) {
		Response wsResponse = null;


        if (provisionUserEntitlementsFlag) {
            final String requestorId = cookieProvider.getUserId(request);
            final User user = userDataWebService.getUserWithDependent(userId, requestorId, true);
            final Organization organization = organizationDataService.getOrganizationLocalized(organizationId, requestorId, getCurrentLanguage());
            if (user != null && organization != null) {
                final ProvisionUser pUser = new ProvisionUser(user);
                pUser.setRequestorUserId(getRequesterId(request));
                IdmAuditLog idmAuditLog = new IdmAuditLog();
                idmAuditLog.setRequestorUserId(getRequesterId(request));
                idmAuditLog.setTargetUser(pUser.getId(), pUser.getLogin());
                idmAuditLog.setTargetOrg(organization.getId(), organization.getName());
                if (isAddition) {
                    organization.setOperation(AttributeOperationEnum.ADD);
                    pUser.getAffiliations().add(organization);
                    idmAuditLog.setAction(AuditAction.ADD_USER_TO_ORG.value());
                    idmAuditLog.setAuditDescription("Add organization to user");
                    auditLogService.addLog(idmAuditLog);
                } else {
                    pUser.getAffiliations().remove(organization);
                    organization.setOperation(AttributeOperationEnum.DELETE);
                    pUser.getAffiliations().add(organization);
                    idmAuditLog.setAction(AuditAction.REMOVE_USER_FROM_ORG.value());
                    idmAuditLog.setAuditDescription("Remove resource from organization");
                    auditLogService.addLog(idmAuditLog);
                }
                WSUtils.setWSClientTimeout(provisionService, 360000L);
                wsResponse = provisionService.modifyUser(pUser);
            }
        } else {
            if (isAddition) {
                wsResponse = organizationDataService.addUserToOrg(organizationId, userId);
            } else {
                wsResponse = organizationDataService.removeUserFromOrg(organizationId, userId);
            }
        }
        //final BasicAjaxResponse ajaxResponse = getResponseAfterEntity2EntityAddition(wsResponse, false);
        final BasicAjaxResponse ajaxResponse = getResponseAfterEntity2EntityAddition(wsResponse, isAddition);
        return ajaxResponse;
	}

	@Override
	protected BasicAjaxResponse getResponseAfterEntity2EntityAddition(
			Response wsResponse, boolean isAddition) {
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        if (ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
            ajaxResponse.setStatus(200);
            ajaxResponse.setSuccessToken(new SuccessToken((isAddition) ? SuccessMessage.ENTITY_ADDED :  SuccessMessage.ENTITY_DELETED));
        } else {
            if (wsResponse.getErrorCode() != null) {
                switch (wsResponse.getErrorCode()) {
                case CANT_ADD_YOURSELF_AS_CHILD:
                    ajaxResponse.addError(new ErrorToken(Errors.CANT_MAKE_ENTITY_A_CHILD_OF_ITSELF));
                    break;
                case CIRCULAR_DEPENDENCY:
                    ajaxResponse.addError(new ErrorToken(Errors.CIRCULAR_DEPENDENCY));
                    break;
                case RELATIONSHIP_EXISTS:
                    ajaxResponse.addError(new ErrorToken(Errors.RELATIONSHIP_EXISTS));
                    break;
                case OBJECT_NOT_FOUND:
                    ajaxResponse.addError(new ErrorToken(Errors.OBJECT_DOES_NOT_EXIST));
                    break;
                case INVALID_ARGUMENTS:
                    ajaxResponse.addError(new ErrorToken(Errors.INTERNAL_ERROR));
                    break;
                case FAIL_PREPROCESSOR_CUSTOM_ERROR_1:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_PREPROCESSOR_CUSTOM_ERROR_1));
                    break;
                case FAIL_PREPROCESSOR_CUSTOM_ERROR_2:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_PREPROCESSOR_CUSTOM_ERROR_2));
                    break;
                case FAIL_PREPROCESSOR_CUSTOM_ERROR_3:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_PREPROCESSOR_CUSTOM_ERROR_3));
                    break;
                case FAIL_PREPROCESSOR_CUSTOM_ERROR_4:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_PREPROCESSOR_CUSTOM_ERROR_4));
                    break;
                case FAIL_PREPROCESSOR_CUSTOM_ERROR_5:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_PREPROCESSOR_CUSTOM_ERROR_5));
                    break;
                case FAIL_PREPROCESSOR_CUSTOM_ERROR_6:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_PREPROCESSOR_CUSTOM_ERROR_6));
                    break;
                case FAIL_PREPROCESSOR_CUSTOM_ERROR_7:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_PREPROCESSOR_CUSTOM_ERROR_7));
                    break;
                case FAIL_PREPROCESSOR_CUSTOM_ERROR_8:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_PREPROCESSOR_CUSTOM_ERROR_8));
                    break;
                case FAIL_PREPROCESSOR_CUSTOM_ERROR_9:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_PREPROCESSOR_CUSTOM_ERROR_9));
                    break;
                case FAIL_PREPROCESSOR_CUSTOM_ERROR_10:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_PREPROCESSOR_CUSTOM_ERROR_10));
                    break;
                case FAIL_PREPROCESSOR_CUSTOM_ERROR_11:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_PREPROCESSOR_CUSTOM_ERROR_11));
                    break;    
                case FAIL_PREPROCESSOR_CUSTOM_ERROR_12:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_PREPROCESSOR_CUSTOM_ERROR_12));
                    break;
                case FAIL_PREPROCESSOR_CUSTOM_ERROR_13:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_PREPROCESSOR_CUSTOM_ERROR_13));
                    break;
                case FAIL_PREPROCESSOR_CUSTOM_ERROR_14:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_PREPROCESSOR_CUSTOM_ERROR_14));
                    break;
                case FAIL_PREPROCESSOR_CUSTOM_ERROR_15:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_PREPROCESSOR_CUSTOM_ERROR_15));
                    break;
                case FAIL_PREPROCESSOR_CUSTOM_ERROR_16:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_PREPROCESSOR_CUSTOM_ERROR_16));
                    break;
                case FAIL_PREPROCESSOR_CUSTOM_ERROR_17:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_PREPROCESSOR_CUSTOM_ERROR_17));
                    break;
                case FAIL_PREPROCESSOR_CUSTOM_ERROR_18:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_PREPROCESSOR_CUSTOM_ERROR_18));
                    break;
                case FAIL_PREPROCESSOR_CUSTOM_ERROR_19:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_PREPROCESSOR_CUSTOM_ERROR_19));
                    break;
                case FAIL_PREPROCESSOR_CUSTOM_ERROR_20:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_PREPROCESSOR_CUSTOM_ERROR_20));
                    break;                    
                case FAIL_POSTPROCESSOR_CUSTOM_ERROR_1:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_POSTPROCESSOR_CUSTOM_ERROR_1));
                    break;
                case FAIL_POSTPROCESSOR_CUSTOM_ERROR_2:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_POSTPROCESSOR_CUSTOM_ERROR_2));
                    break;
                case FAIL_POSTPROCESSOR_CUSTOM_ERROR_3:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_POSTPROCESSOR_CUSTOM_ERROR_3));
                    break;
                case FAIL_POSTPROCESSOR_CUSTOM_ERROR_4:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_POSTPROCESSOR_CUSTOM_ERROR_4));
                    break;
                case FAIL_POSTPROCESSOR_CUSTOM_ERROR_5:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_POSTPROCESSOR_CUSTOM_ERROR_5));
                    break;
                case FAIL_POSTPROCESSOR_CUSTOM_ERROR_6:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_POSTPROCESSOR_CUSTOM_ERROR_6));
                    break;
                case FAIL_POSTPROCESSOR_CUSTOM_ERROR_7:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_POSTPROCESSOR_CUSTOM_ERROR_7));
                    break;
                case FAIL_POSTPROCESSOR_CUSTOM_ERROR_8:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_POSTPROCESSOR_CUSTOM_ERROR_8));
                    break;
                case FAIL_POSTPROCESSOR_CUSTOM_ERROR_9:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_POSTPROCESSOR_CUSTOM_ERROR_9));
                    break;
                case FAIL_POSTPROCESSOR_CUSTOM_ERROR_10:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_POSTPROCESSOR_CUSTOM_ERROR_10));
                    break;
                case FAIL_POSTPROCESSOR_CUSTOM_ERROR_11:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_POSTPROCESSOR_CUSTOM_ERROR_11));
                    break;
                case FAIL_POSTPROCESSOR_CUSTOM_ERROR_12:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_POSTPROCESSOR_CUSTOM_ERROR_12));
                    break;
                case FAIL_POSTPROCESSOR_CUSTOM_ERROR_13:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_POSTPROCESSOR_CUSTOM_ERROR_13));
                    break;
                case FAIL_POSTPROCESSOR_CUSTOM_ERROR_14:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_POSTPROCESSOR_CUSTOM_ERROR_14));
                    break;
                case FAIL_POSTPROCESSOR_CUSTOM_ERROR_15:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_POSTPROCESSOR_CUSTOM_ERROR_15));
                    break;
                case FAIL_POSTPROCESSOR_CUSTOM_ERROR_16:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_POSTPROCESSOR_CUSTOM_ERROR_16));
                    break;
                case FAIL_POSTPROCESSOR_CUSTOM_ERROR_17:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_POSTPROCESSOR_CUSTOM_ERROR_17));
                    break;
                case FAIL_POSTPROCESSOR_CUSTOM_ERROR_18:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_POSTPROCESSOR_CUSTOM_ERROR_18));
                    break;
                case FAIL_POSTPROCESSOR_CUSTOM_ERROR_19:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_POSTPROCESSOR_CUSTOM_ERROR_19));
                    break;
                case FAIL_POSTPROCESSOR_CUSTOM_ERROR_20:
                    ajaxResponse.addError(new ErrorToken(Errors.FAIL_POSTPROCESSOR_CUSTOM_ERROR_20));
                    break;                    
                default:
                    ajaxResponse.addError(new ErrorToken(Errors.INTERNAL_ERROR));
                    break;
                }
            } else {
                ajaxResponse.addError(new ErrorToken(Errors.INTERNAL_ERROR));
            }
        }
        return ajaxResponse;
	}

	@Override
	protected BasicAjaxResponse user2ResourceOperation(
			HttpServletRequest request, String userId, String resourceId,
			boolean isAddition) {
		Response wsResponse = null;
        final String requestorId = cookieProvider.getPrincipal(request);
        final User user = userDataWebService.getUserWithDependent(userId, requestorId, true);
        final org.openiam.idm.srvc.res.dto.Resource resource = resourceDataService.getResource(resourceId, getCurrentLanguage());
        final ProvisionUser pUser = new ProvisionUser(user);
        pUser.setRequestorUserId(getRequesterId(request));
        IdmAuditLog idmAuditLog = new IdmAuditLog();
        idmAuditLog.setTargetResource(resource.getId(), resource.getName());
        idmAuditLog.setRequestorUserId(getRequesterId(request));
        idmAuditLog.setTargetUser(pUser.getId(), pUser.getLogin());

        final String callerId = getRequesterId(request);

        if (provisionUserEntitlementsFlag) {
            if (user != null) {

                if (isAddition) {
                    resource.setOperation(AttributeOperationEnum.ADD);
                    pUser.getResources().add(resource);
                    idmAuditLog.setAction(AuditAction.ADD_USER_TO_RESOURCE.value());
                    idmAuditLog.setAuditDescription("Add user to resource");
                    auditLogService.addLog(idmAuditLog);
                } else {
                    pUser.getResources().remove(resource);
                    resource.setOperation(AttributeOperationEnum.DELETE);
                    pUser.getResources().add(resource);
                    idmAuditLog.setAction(AuditAction.REMOVE_USER_FROM_RESOURCE.value());
                    idmAuditLog.setAuditDescription("Remove user from resource");
                    auditLogService.addLog(idmAuditLog);
                }
                wsResponse = provisionService.modifyUser(pUser);
            } else {
                wsResponse = new Response();
                wsResponse.setErrorCode(ResponseCode.UNAUTHORIZED);
            }
        } else {
            if (isAddition) {
                wsResponse = resourceDataService.addUserToResource(resourceId, userId, callerId);
            } else {
                wsResponse = resourceDataService.removeUserFromResource(resourceId, userId, callerId);
            }
        }
        //final BasicAjaxResponse ajaxResponse = getResponseAfterEntity2EntityAddition(wsResponse, false);
        final BasicAjaxResponse ajaxResponse = getResponseAfterEntity2EntityAddition(wsResponse, isAddition);
        return ajaxResponse;
	}

	@Override
	protected BasicAjaxResponse user2GroupOperation(HttpServletRequest request,
			String userId, String groupId, boolean isAddition) {
		Response wsResponse = null;
        final String requestorId = cookieProvider.getPrincipal(request);
        final User user = userDataWebService.getUserWithDependent(userId, requestorId, true);
        final Group group = groupServiceClient.getGroup(groupId, requestorId);

        IdmAuditLog idmAuditLog = new IdmAuditLog();
        idmAuditLog.setRequestorUserId(getRequesterId(request));
        idmAuditLog.setTargetUser(user.getId(), user.getLogin());
        idmAuditLog.setTargetGroup(group.getId(), group.getName());

        final String callerId = getRequesterId(request);
        if (provisionUserEntitlementsFlag) {
            if (user != null && group != null) {
                final ProvisionUser pUser = new ProvisionUser(user);
                pUser.setRequestorUserId(getRequesterId(request));

                if (isAddition) {
                    group.setOperation(AttributeOperationEnum.ADD);
                    pUser.getGroups().add(group);
                    idmAuditLog.setAction(AuditAction.ADD_USER_TO_GROUP.value());
                    idmAuditLog.setAuditDescription("Add user to resource");
                    auditLogService.addLog(idmAuditLog);
                } else {
                    pUser.getGroups().remove(group);
                    group.setOperation(AttributeOperationEnum.DELETE);
                    pUser.getGroups().add(group);
                    idmAuditLog.setAction(AuditAction.REMOVE_USER_FROM_GROUP.value());
                    idmAuditLog.setAuditDescription("Remove user from resource");
                    auditLogService.addLog(idmAuditLog);
                }
                wsResponse = provisionService.modifyUser(pUser);
            } else {
                wsResponse = new Response();
                wsResponse.setErrorCode(ResponseCode.UNAUTHORIZED);
            }
        } else {
            if (isAddition) {
                wsResponse = groupServiceClient.addUserToGroup(groupId, userId, callerId);
            } else {
                wsResponse = groupServiceClient.removeUserFromGroup(groupId, userId, callerId);
            }
        }
        //final BasicAjaxResponse ajaxResponse = getResponseAfterEntity2EntityAddition(wsResponse, false);
        final BasicAjaxResponse ajaxResponse = getResponseAfterEntity2EntityAddition(wsResponse, isAddition);
        return ajaxResponse;
	}

	@Override
	protected BasicAjaxResponse user2RoleOperation(HttpServletRequest request,
			String userId, String roleId, boolean isAddition) {
		Response wsResponse = null;
        final String requestorId = cookieProvider.getUserId(request);
        final User user = userDataWebService.getUserWithDependent(userId, requestorId, true);
        final Role role = roleServiceClient.getRoleLocalized(roleId, requestorId, getCurrentLanguage());
        IdmAuditLog idmAuditLog = new IdmAuditLog();
        idmAuditLog.setRequestorUserId(getRequesterId(request));
        idmAuditLog.setTargetUser(user.getId(), user.getLogin());
        idmAuditLog.setTargetRole(role.getId(), role.getName());


        final String callerId = getRequesterId(request);
        if (provisionUserEntitlementsFlag) {
            if (user != null && role != null) {
                final ProvisionUser pUser = new ProvisionUser(user);
                pUser.setRequestorUserId(getRequesterId(request));
                if (isAddition) {
                    role.setOperation(AttributeOperationEnum.ADD);
                    pUser.getRoles().add(role);
                    idmAuditLog.setAction(AuditAction.ADD_USER_TO_ROLE.value());
                    idmAuditLog.setAuditDescription("Add user to role");
                    auditLogService.addLog(idmAuditLog);
                } else {
                    pUser.getRoles().remove(role);
                    role.setOperation(AttributeOperationEnum.DELETE);
                    pUser.getRoles().add(role);
                    idmAuditLog.setAction(AuditAction.REMOVE_USER_FROM_ROLE.value());
                    idmAuditLog.setAuditDescription("Remove user from role");
                    auditLogService.addLog(idmAuditLog);
                }
                wsResponse = provisionService.modifyUser(pUser);
            } else {
                wsResponse = new Response();
                wsResponse.setErrorCode(ResponseCode.UNAUTHORIZED);
            }
        } else {
            if (isAddition) {
                wsResponse = roleServiceClient.addUserToRole(roleId, userId, callerId);
            } else {
                wsResponse = roleServiceClient.removeUserFromRole(roleId, userId, callerId);
            }
        }
        //final BasicAjaxResponse ajaxResponse = getResponseAfterEntity2EntityAddition(wsResponse, false);
        final BasicAjaxResponse ajaxResponse = getResponseAfterEntity2EntityAddition(wsResponse, isAddition);
        return ajaxResponse;
	}

	@Override
	protected String getEditMenu() {
		return menu;
	}

}
