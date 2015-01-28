package org.openiam.ui.web.mvc.entitlements;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openiam.authmanager.util.AuthorizationConstants;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.searchbeans.ResourceSearchBean;
import org.openiam.idm.searchbeans.UserSearchBean;
import org.openiam.idm.srvc.mngsys.dto.ManagedSysDto;
import org.openiam.idm.srvc.res.dto.Resource;
import org.openiam.idm.srvc.res.dto.ResourceRisk;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.ui.rest.api.model.KeyNameBean;
import org.openiam.ui.util.ErrorUtils;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public abstract class AbstractResourceController extends AbstractEntityEntitlementsController<Resource> {

    @RequestMapping(value = "/resourceEntitlements", method = RequestMethod.GET)
    public String resourceEntitlements(final HttpServletRequest request, final HttpServletResponse response,
            final @RequestParam(value = "id", required = true) String resourceId,
            @RequestParam(value = "type", required = false) String resourceType) throws IOException {
        final ResourceSearchBean searchBean = new ResourceSearchBean();
        searchBean.setKey(resourceId);
        searchBean.setDeepCopy(false);
        List<org.openiam.idm.srvc.res.dto.Resource> resultList = resourceDataService.findBeans(searchBean, 0, 1, getCurrentLanguage());
        if (CollectionUtils.isEmpty(resultList)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,
                    String.format("Resource with ID: '%s' not found", resourceId));
            return null;
        }

        final org.openiam.idm.srvc.res.dto.Resource resource = resultList.get(0);

        setMenuTree(request, getEditMenu());
        request.setAttribute("resource", resource);

        if (StringUtils.isBlank(resourceType)) {
            resourceType = "childresources";
        }

        request.setAttribute("type", resourceType);
        return "jar:resources/entitlements";
    }

    @RequestMapping("/resources")
    public String getResourcePage(final HttpServletRequest request) throws Exception {
        setMenuTree(request, this.getRootMenu());
        final List<KeyNameBean> resourceTypes = getResourceTypesAsKeyNameBeans(true);
        request.setAttribute("resourceTypeList",
                (resourceTypes != null) ? jacksonMapper.writeValueAsString(resourceTypes) : null);
        request.setAttribute("excludeResourceType", AuthorizationConstants.MENU_ITEM_RESOURCE_TYPE);
        return "jar:resources/search";
    }

    @RequestMapping(value = "/editResource", method = RequestMethod.GET)
    public String editResource(final HttpServletRequest request, final HttpServletResponse response,
            final @RequestParam(required = false, value = "id") String resourceId) throws Exception {
        org.openiam.idm.srvc.res.dto.Resource resource = new org.openiam.idm.srvc.res.dto.Resource();
        request.setAttribute("riskList", ResourceRisk.values());

        if (StringUtils.isEmpty(resourceId)) {
            setMenuTree(request, getRootMenu());
        } else {
            final ResourceSearchBean searchBean = new ResourceSearchBean();
            searchBean.setKey(resourceId);
            searchBean.setDeepCopy(true);
            List<org.openiam.idm.srvc.res.dto.Resource> resultList = resourceDataService.findBeans(searchBean, 0, 1, getCurrentLanguage());
            if (CollectionUtils.isEmpty(resultList)) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND,
                        String.format("Resource with ID: '%s' not found", resourceId));
                return null;
            }

            resource = resultList.get(0);
            if (StringUtils.equalsIgnoreCase(resource.getResourceType().getId(),
                    AuthorizationConstants.MENU_ITEM_RESOURCE_TYPE)) {
                return handlMenuResource(request, response, resourceId);
            }
            setMenuTree(request, getEditMenu());
        }
        final ManagedSysDto mSys = managedSysServiceClient.getManagedSysByResource(resource.getId());
        if (mSys != null) {
            request.setAttribute("managedSystem", mSys);
            List<String> attrList = provisionService.getManagedSystemAttributesList(mSys.getId());
            List<KeyNameBean> mngSysProps = new ArrayList<KeyNameBean>();
            if (CollectionUtils.isNotEmpty(attrList)) {
                for (String attrName: attrList) {
                    mngSysProps.add(new KeyNameBean(attrName, attrName));
                }
                request.setAttribute("mngSysPropsAsJSON", jacksonMapper.writeValueAsString(mngSysProps));
            }
        }
        request.setAttribute("resource", resource);
        request.setAttribute("resourceAsJSON", jacksonMapper.writeValueAsString(resource));
        request.setAttribute("isNew", StringUtils.isBlank(resourceId));

        request.setAttribute("resourceTypes", getResourceTypesAsKeyNameBeans());
        request.setAttribute("managedSystems", getManagedSystemsAsKeyNameBeans());
        return "jar:resources/editResource";
    }

    @RequestMapping(value = "/editResource", method = RequestMethod.POST)
    public String saveResource(final HttpServletRequest request, final HttpServletResponse response,
            final @RequestBody org.openiam.idm.srvc.res.dto.Resource resource) throws Exception {
        final BasicAjaxResponse ajaxResponse = doEdit(request, response, resource);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/deleteResource", method = RequestMethod.POST)
    public String deleteResource(final @RequestParam(value = "id") String resourceId,
            final HttpServletResponse response, final HttpServletRequest request) throws Exception {
        final Resource resource = getEntity(resourceId, request);
        final BasicAjaxResponse ajaxResponse = doDelete(request, response, resource);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/addChildResource", method = RequestMethod.POST)
    public String addChildResource(final HttpServletRequest request,
            final @RequestParam(required = true, value = "resourceId") String resourceId,
            final @RequestParam(required = true, value = "memberResourceId") String memberResourceId) {
        final BasicAjaxResponse ajaxResponse = doAddChildResource(request, resourceId, memberResourceId);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/removeChildResource", method = RequestMethod.POST)
    public String removeChildResource(final HttpServletRequest request,
            final @RequestParam(required = true, value = "resourceId") String resourceId,
            final @RequestParam(required = true, value = "memberResourceId") String memberResourceId) {
        final BasicAjaxResponse ajaxResponse = doRemoveChildResource(request, resourceId, memberResourceId);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/addRoleToResource", method = RequestMethod.POST)
    public String addRoleToResource(final HttpServletRequest request,
            final @RequestParam(required = true, value = "resourceId") String resourceId,
            final @RequestParam(required = true, value = "roleId") String roleId) {
        final BasicAjaxResponse ajaxResponse = doAddResource2Role(request, resourceId, roleId);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/removeRoleFromResource", method = RequestMethod.POST)
    public String removeRoleFromResource(final HttpServletRequest request,
            final @RequestParam(required = true, value = "resourceId") String resourceId,
            final @RequestParam(required = true, value = "roleId") String roleId) {
        final BasicAjaxResponse ajaxResponse = doRemoveResourceFromRole(request, resourceId, roleId);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/provisionResourceByRole", method = RequestMethod.POST)
    public String provisionResourceByRole(final HttpServletRequest request,
                                         final @RequestParam(required = true, value = "resourceId") String resourceId,
                                         final @RequestParam(required = true, value = "roleId") String roleId) {
        UserSearchBean usb = new UserSearchBean();
        Set<String> roleIds = new HashSet<String>();
        roleIds.add(roleId);
        usb.setRoleIdSet(roleIds);
        usb.setDeepCopy(true);
        final List<User> users = userDataWebService.findBeans(usb, -1, -1);
        List<String> uIds = new LinkedList<String>();
        for (User u : users) {
            uIds.add(u.getId());
        }
        List<String> resIds = new LinkedList<String>();
        resIds.add(resourceId);
        final Response response = provisionService.provisionUsersToResource(uIds, getRequesterId(request), resIds);

        final BasicAjaxResponse ajaxResponse = getResponseAfterEntity2EntityAddition(response, true);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/provisionResourceByGroup", method = RequestMethod.POST)
    public String provisionResourceByGroup(final HttpServletRequest request,
                                          final @RequestParam(required = true, value = "resourceId") String resourceId,
                                          final @RequestParam(required = true, value = "groupId") String groupId) {
        UserSearchBean usb = new UserSearchBean();
        Set<String> groupIds = new HashSet<String>();
        groupIds.add(groupId);
        usb.setGroupIdSet(groupIds);
        usb.setDeepCopy(true);
        final List<User> users = userDataWebService.findBeans(usb, -1, -1);
        List<String> uIds = new LinkedList<String>();
        for (User u : users) {
            uIds.add(u.getId());
        }
        List<String> resIds = new LinkedList<String>();
        resIds.add(resourceId);
        final Response response = provisionService.provisionUsersToResource(uIds, getRequesterId(request), resIds);

        final BasicAjaxResponse ajaxResponse = getResponseAfterEntity2EntityAddition(response, true);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/deprovisionResourceByRole", method = RequestMethod.POST)
    public String deprovisionResourceByRole(final HttpServletRequest request,
                                          final @RequestParam(required = true, value = "resourceId") String resourceId,
                                          final @RequestParam(required = true, value = "roleId") String roleId) {
        UserSearchBean usb = new UserSearchBean();
        Set<String> roleIds = new HashSet<String>();
        roleIds.add(roleId);
        usb.setRoleIdSet(roleIds);
        usb.setDeepCopy(true);
        final List<User> users = userDataWebService.findBeans(usb, -1, -1);
        List<String> uIds = new LinkedList<String>();
        for (User u : users) {
            uIds.add(u.getId());
        }
        List<String> resIds = new LinkedList<String>();
        resIds.add(resourceId);
        final Response response = provisionService.deProvisionUsersToResource(uIds, getRequesterId(request), resIds);

        final BasicAjaxResponse ajaxResponse = getResponseAfterEntity2EntityAddition(response, true);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/deprovisionResourceByGroup", method = RequestMethod.POST)
    public String deprovisionResourceByGroup(final HttpServletRequest request,
                                            final @RequestParam(required = true, value = "resourceId") String resourceId,
                                            final @RequestParam(required = true, value = "groupId") String groupId) {
        UserSearchBean usb = new UserSearchBean();
        Set<String> groupIds = new HashSet<String>();
        groupIds.add(groupId);
        usb.setGroupIdSet(groupIds);
        usb.setDeepCopy(true);
        final List<User> users = userDataWebService.findBeans(usb, -1, -1);
        List<String> uIds = new LinkedList<String>();
        for (User u : users) {
            uIds.add(u.getId());
        }
        List<String> resIds = new LinkedList<String>();
        resIds.add(resourceId);
        final Response response = provisionService.deProvisionUsersToResource(uIds, getRequesterId(request), resIds);

        final BasicAjaxResponse ajaxResponse = getResponseAfterEntity2EntityAddition(response, true);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }
    @RequestMapping(value = "/addGroupToResource", method = RequestMethod.POST)
    public String addGroupToResource(final HttpServletRequest request,
            final @RequestParam(required = true, value = "resourceId") String resourceId,
            final @RequestParam(required = true, value = "groupId") String groupId) {
        final BasicAjaxResponse ajaxResponse = doAddResource2Group(request, resourceId, groupId);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/deleteGroupFromResource", method = RequestMethod.POST)
    public String deleteGroupFromResource(final HttpServletRequest request,
            final @RequestParam(required = true, value = "resourceId") String resourceId,
            final @RequestParam(required = true, value = "groupId") String groupId) {
        final BasicAjaxResponse ajaxResponse = doRemoveResourceFromGroup(request, resourceId, groupId);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @Override
    public Resource getEntity(final String id, final HttpServletRequest request) {
        return resourceDataService.getResource(id, getCurrentLanguage());
    }

    @Override
    protected final List<ErrorToken> getEditErrors(final Response wsResponse, final HttpServletRequest request,
            final Resource resource) {
        final List<ErrorToken> retVal = new LinkedList<ErrorToken>();
        ErrorToken token = null;
        if (wsResponse != null && wsResponse.isFailure()) {
            if (wsResponse.getErrorCode() != null) {
                switch (wsResponse.getErrorCode()) {
                case OBJECT_NOT_FOUND:
                    token = new ErrorToken(Errors.RESOURCE_WITH_NAME_NOT_EXISTS, resource.getName());
                    break;
                case NO_NAME:
                    token = new ErrorToken(Errors.INVALID_RESOURCE_NAME);
                    break;
                case NAME_TAKEN:
                    token = new ErrorToken(Errors.RESORUCE_NAME_TAKEN, resource.getName());
                    break;
                case INVALID_RESOURCE_TYPE:
                    token = new ErrorToken(Errors.INVALID_RESOURCE_TYPE);
                    break;
                case VALIDATION_ERROR:
                    retVal.addAll(ErrorUtils.getESBErrorTokens(wsResponse));
                    break;
                default:
                    token = new ErrorToken(Errors.INTERNAL_ERROR);
                    break;
                }
            } else {
                token = new ErrorToken(Errors.INTERNAL_ERROR);
            }
        }
        if (token != null) {
            retVal.add(token);
        }
        return retVal;
    }

    @Override
    protected final List<ErrorToken> getDeleteErrors(final Response wsResponse, final HttpServletRequest request,
            final Resource resource) {
        final List<ErrorToken> retVal = new LinkedList<ErrorToken>();
        ErrorToken token = null;
        if (wsResponse != null && wsResponse.isFailure()) {
            final Object responseValue = wsResponse.getResponseValue();
            if (wsResponse.getErrorCode() != null) {
                switch (wsResponse.getErrorCode()) {
                case RESOURCE_IS_AN_ADMIN_OF_GROUP:
                    token = new ErrorToken(Errors.RESOURCE_IS_ADMIN_OF_GROUP, responseValue);
                    break;
                case RESOURCE_IS_AN_ADMIN_OF_ROLE:
                    token = new ErrorToken(Errors.RESOURCE_IS_ADMIN_OF_ROLE, responseValue);
                    break;
                case RESOURCE_IS_AN_ADMIN_OF_RESOURCE:
                    token = new ErrorToken(Errors.RESOURCE_IS_ADMIN_OF_RESOURCE, responseValue);
                    break;
                case RESOURCE_IS_AN_ADMIN_OF_ORG:
                    token = new ErrorToken(Errors.RESOURCE_IS_ADMIN_OF_ORG, responseValue);
                    break;
                case OBJECT_NOT_FOUND:
                    token = new ErrorToken(Errors.RESOURCE_DOES_NOT_EXIST);
                    break;
                case LINKED_TO_AUTHENTICATION_PROVIDER:
                    token = new ErrorToken(Errors.RESOURCE_LINKED_TO_AUTHENTICATION_PROVIDER, responseValue);
                    break;
                case LINKED_TO_CONTENT_PROVIDER:
                    token = new ErrorToken(Errors.RESOURCE_LINKED_TO_CONTENT_PROVIDER, responseValue);
                    break;
                case LINKED_TO_METADATA_ELEMENT:
                    token = new ErrorToken(Errors.RESOURCE_LINKED_TO_METADATA_ELEMENT, responseValue);
                    break;
                case LINKED_TO_PAGE_TEMPLATE:
                    token = new ErrorToken(Errors.RESOURCE_LINKED_TO_PAGE_TEMPLATE, responseValue);
                    break;
                case LINKED_TO_URI_PATTERN:
                    token = new ErrorToken(Errors.RESOURCE_LINKED_TO_URI_PATTERN, responseValue);
                    break;
                case LINKED_TO_MANAGED_SYSTEM:
                    token = new ErrorToken(Errors.RESOURCE_LINKED_TO_MANAGED_SYSTEM, responseValue);
                    break;
                default:
                    token = new ErrorToken(Errors.INTERNAL_ERROR);
                    break;
                }
            } else {
                token = new ErrorToken(Errors.INTERNAL_ERROR);
            }
        }
        if (token != null) {
            retVal.add(token);
        }
        return retVal;
    }

    protected BasicAjaxResponse getResponseAfterChildResourceManipulation(final Response wsResponse,
            final boolean isDelete) {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        if (ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
            ajaxResponse.setStatus(200);
            ajaxResponse.setSuccessToken(new SuccessToken((isDelete) ? SuccessMessage.CHILD_RESOURCE_DELETED
                    : SuccessMessage.CHILD_RESOURCE_ADDED));
        } else {
            if (wsResponse.getErrorCode() != null) {
                switch (wsResponse.getErrorCode()) {
                case RESOURCE_TYPE_NOT_SUPPORTS_HIERARCHY:
                    ajaxResponse.addError(new ErrorToken(Errors.RESOURCE_TYPE_NOT_SUPPORTS_HIERARCHY, wsResponse
                            .getResponseValue()));
                    break;
                case RELATIONSHIP_EXISTS:
                    ajaxResponse.addError(new ErrorToken(Errors.RELATIONSHIP_EXISTS));
                    break;
                case RESOURCE_TYPES_NOT_EQUAL:
                    ajaxResponse.addError(new ErrorToken(Errors.RESOURCE_TYPES_NOT_EQUAL));
                    break;
                case OBJECT_NOT_FOUND:
                    ajaxResponse.addError(new ErrorToken(Errors.RESOURCE_DOES_NOT_EXIST));
                    break;
                case INVALID_ARGUMENTS:
                    ajaxResponse.addError(new ErrorToken(Errors.INTERNAL_ERROR));
                    break;
                case CANT_ADD_YOURSELF_AS_CHILD:
                    ajaxResponse.addError(new ErrorToken(Errors.CANT_MAKE_ENTITY_A_CHILD_OF_ITSELF));
                    break;
                case CIRCULAR_DEPENDENCY:
                    ajaxResponse.addError(new ErrorToken(Errors.CIRCULAR_DEPENDENCY));
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

    protected abstract String handlMenuResource(final HttpServletRequest request, final HttpServletResponse response,
            final String resourceId) throws Exception;

    protected abstract BasicAjaxResponse doAddChildResource(final HttpServletRequest request, final String resourceId,
            final String memberResourceId);

    protected abstract BasicAjaxResponse doRemoveChildResource(final HttpServletRequest request,
            final String resourceId, final String memberResourceId);

    protected abstract BasicAjaxResponse doAddResource2Role(final HttpServletRequest request, final String resourceId,
            final String roleId);

    protected abstract BasicAjaxResponse doRemoveResourceFromRole(final HttpServletRequest request,
            final String resourceId, final String roleId);

    protected abstract BasicAjaxResponse doAddResource2Group(final HttpServletRequest request, final String resourceId,
            final String groupId);

    protected abstract BasicAjaxResponse doRemoveResourceFromGroup(final HttpServletRequest request,
            final String resourceId, final String groupId);
}
