package org.openiam.ui.web.mvc.entitlements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import org.openiam.authmanager.common.model.AuthorizationMenu;
import org.openiam.authmanager.ws.request.MenuRequest;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.searchbeans.UserSearchBean;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.provision.resp.ProvisionUserResponse;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public abstract class AbstractUserEntitlementsController extends AbstractEntitlementsController<User> {

	@Override
	public String getRootMenu() {
		return null;
	}
	
	@Override
	public User getEntity(final String id, final HttpServletRequest request) {
		return userDataWebService.getUserWithDependent(id, getRequesterId(request), true);
	}

    @RequestMapping(value="/userEntitlements", method= RequestMethod.GET)
    public String userEntitlements(final HttpServletRequest request,
                                   final HttpServletResponse response,
                                   final @RequestParam(value="id", required=true) String userId) throws IOException {

        // Select 1st menu item, requester is authorized for
        final String requesterId = cookieProvider.getUserId(request);
        final MenuRequest menuRequest = new MenuRequest();
        menuRequest.setMenuName("USER_ENTITLEMENTS_PAGE");
        menuRequest.setUserId(requesterId);
        final AuthorizationMenu menu = authManagerMenuService.getMenuTreeForUserId(menuRequest, null);
        String type = (menu != null) ? getActiveType(menu) : null;
        return userEntitlementsByType(request, response, userId, type);
    }

    @RequestMapping(value="/userEntitlements{type:Groups|Roles|Resources|Organizations}", method= RequestMethod.GET)
    public String userEntitlementsByType(final HttpServletRequest request,
                                   final HttpServletResponse response,
                                   final @RequestParam(value="id", required=true) String userId,
                                   @PathVariable(value="type") String type) throws IOException {

        final UserSearchBean searchBean = new UserSearchBean();
        searchBean.setKey(userId);
        final List<User> userList = userDataWebService.findBeans(searchBean, 0, 1);
        if(CollectionUtils.isEmpty(userList)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, String.format("User with id '%s' does not exist", userId));
            return null;
        }

        final User user = userList.get(0);

        request.setAttribute("user", user);
        request.setAttribute("type", type);
        setMenuTree(request, getEditMenu());
        final String menuString = getInitialMenuAsJson(request, "USER_ENTITLEMENTS_PAGE");
        request.setAttribute("initialMenu", menuString);
        return "jar:users/entitlements";
    }

    @RequestMapping(value = "/addUserToResource", method = RequestMethod.POST)
    public String addUserToResource(final HttpServletRequest request, final @RequestParam(required = true, value = "resourceId") String resourceId,
                                    final @RequestParam(required = true, value = "userId") String userId) {
        final BasicAjaxResponse ajaxResponse = user2ResourceOperation(request, userId, resourceId, true);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/removeUserFromResource", method = RequestMethod.POST)
    public String removeUserFromResource(final HttpServletRequest request,
                                         final @RequestParam(required = true, value = "resourceId") String resourceId,
                                         final @RequestParam(required = true, value = "userId") String userId) {
        final BasicAjaxResponse ajaxResponse = user2ResourceOperation(request, userId, resourceId, false);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/addUserToGroup", method = RequestMethod.POST)
    public String addUserToGroup(final HttpServletRequest request, final @RequestParam(required = true, value = "groupId") String groupId,
                                 final @RequestParam(required = true, value = "userId") String userId) {
        final BasicAjaxResponse ajaxResponse = user2GroupOperation(request, userId, groupId, true);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/removeUserFromGroup", method = RequestMethod.POST)
    public String removeUserFromGroup(final HttpServletRequest request, final @RequestParam(required = true, value = "groupId") String groupId,
                                      final @RequestParam(required = true, value = "userId") String userId) {
        final BasicAjaxResponse ajaxResponse = user2GroupOperation(request, userId, groupId, false);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/addUserToRole", method = RequestMethod.POST)
    public String addUserToRole(final HttpServletRequest request, final @RequestParam(required = true, value = "roleId") String roleId,
                                final @RequestParam(required = true, value = "userId") String userId) {
        final BasicAjaxResponse ajaxResponse = user2RoleOperation(request, userId, roleId, true);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/removeUserFromRole", method = RequestMethod.POST)
    public String removeUserFromRole(final HttpServletRequest request, final @RequestParam(required = true, value = "roleId") String roleId,
                                     final @RequestParam(required = true, value = "userId") String userId) {
        final BasicAjaxResponse ajaxResponse = user2RoleOperation(request, userId, roleId, false);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/addUserToOrganization", method = RequestMethod.POST)
    public String addUserToOrganization(final HttpServletRequest request,
                                        final @RequestParam(required = true, value = "organizationId") String organizationId,
                                        final @RequestParam(required = true, value = "userId") String userId) {
        final BasicAjaxResponse ajaxResponse = user2OrganizationOperation(request, userId, organizationId, true);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/removeUserFromOrganization", method = RequestMethod.POST)
    public String removeUserFromOrganization(final HttpServletRequest request,
                                             final @RequestParam(required = true, value = "organizationId") String organizationId,
                                             final @RequestParam(required = true, value = "userId") String userId) {
        final BasicAjaxResponse ajaxResponse = user2OrganizationOperation(request, userId, organizationId, false);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/provisionUserByRole", method = RequestMethod.POST)
    public String provisionUserByRole(final HttpServletRequest request, final @RequestParam(required = true, value = "roleId") String roleId,
                                     final @RequestParam(required = true, value = "userId") String userId) {

        List<String> userIds = new LinkedList<String>();
        userIds.add(userId);
        List<String> roleIds = new LinkedList<String>();
        roleIds.add(roleId);
        ProvisionUserResponse provisionUserResponse = provisionService.provisionUsersToResourceByRole(userIds, getRequesterId(request),roleIds);

        final BasicAjaxResponse ajaxResponse = getResponseAfterEntity2EntityAddition(provisionUserResponse, false);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }
    @RequestMapping(value = "/deprovisionUserByRole", method = RequestMethod.POST)
    public String deprovisionUserByRole(final HttpServletRequest request, final @RequestParam(required = true, value = "roleId") String roleId,
                                      final @RequestParam(required = true, value = "userId") String userId) {

        List<String> userIds = new LinkedList<String>();
        userIds.add(userId);
        List<String> roleIds = new LinkedList<String>();
        roleIds.add(roleId);
        ProvisionUserResponse provisionUserResponse = provisionService.deProvisionUsersToResourceByRole(userIds, getRequesterId(request),roleIds);

        final BasicAjaxResponse ajaxResponse = getResponseAfterEntity2EntityAddition(provisionUserResponse, false);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/provisionUserByOrg", method = RequestMethod.POST)
    public String provisionUserByOrg(final HttpServletRequest request, final @RequestParam(required = true, value = "orgId") String orgId,
                                      final @RequestParam(required = true, value = "userId") String userId) {

        List<String> userIds = new LinkedList<String>();
        userIds.add(userId);
        List<String> orgIds = new LinkedList<String>();
        orgIds.add(orgId);
        //ProvisionUserResponse provisionUserResponse = provisionService.provisionUsersToResourceByOrg(userIds, getRequesterId(request),orgIds);

        final BasicAjaxResponse ajaxResponse = getResponseAfterEntity2EntityAddition(new ProvisionUserResponse(ResponseStatus.SUCCESS), false);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }
    @RequestMapping(value = "/deprovisionUserByOrg", method = RequestMethod.POST)
    public String deprovisionUserByOrg(final HttpServletRequest request, final @RequestParam(required = true, value = "orgId") String orgId,
                                        final @RequestParam(required = true, value = "userId") String userId) {

        List<String> userIds = new LinkedList<String>();
        userIds.add(userId);
        List<String> orgIds = new LinkedList<String>();
        orgIds.add(orgId);
        //ProvisionUserResponse provisionUserResponse = provisionService.deProvisionUsersToResourceByOrg(userIds, getRequesterId(request),orgIds);

        final BasicAjaxResponse ajaxResponse = getResponseAfterEntity2EntityAddition(new ProvisionUserResponse(ResponseStatus.SUCCESS), false);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/provisionUserByGroup", method = RequestMethod.POST)
    public String provisionUserByGroup(final HttpServletRequest request, final @RequestParam(required = true, value = "groupId") String groupId,
                                     final @RequestParam(required = true, value = "userId") String userId) {
        List<String> userIds = new LinkedList<String>();
        userIds.add(userId);
        List<String> groupIds = new LinkedList<String>();
        groupIds.add(groupId);
        ProvisionUserResponse provisionUserResponse = provisionService.provisionUsersToResourceByRole(userIds, getRequesterId(request), groupIds);

        final BasicAjaxResponse ajaxResponse = getResponseAfterEntity2EntityAddition(provisionUserResponse, false);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/deprovisionUserByGroup", method = RequestMethod.POST)
    public String deprovisionUserByGroup(final HttpServletRequest request, final @RequestParam(required = true, value = "groupId") String groupId,
                                       final @RequestParam(required = true, value = "userId") String userId) {
        List<String> userIds = new LinkedList<String>();
        userIds.add(userId);
        List<String> groupIds = new LinkedList<String>();
        groupIds.add(groupId);
        ProvisionUserResponse provisionUserResponse = provisionService.deProvisionUsersToResourceByGroup(userIds, getRequesterId(request), groupIds);

        final BasicAjaxResponse ajaxResponse = getResponseAfterEntity2EntityAddition(provisionUserResponse, false);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/provisionUserByResource", method = RequestMethod.POST)
    public String provisionUserByResource(final HttpServletRequest request, final @RequestParam(required = true, value = "resourceId") String resourceId,
                                     final @RequestParam(required = true, value = "userId") String userId) {
        List<String> userIds = new LinkedList<String>();
        userIds.add(userId);
        List<String> resourceIds = new LinkedList<String>();
        resourceIds.add(resourceId);
        ProvisionUserResponse provisionUserResponse = provisionService.provisionUsersToResourceByRole(userIds, getRequesterId(request), resourceIds);

        final BasicAjaxResponse ajaxResponse = getResponseAfterEntity2EntityAddition(provisionUserResponse, false);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/deprovisionUserByResource", method = RequestMethod.POST)
    public String deprovisionUserByResource(final HttpServletRequest request, final @RequestParam(required = true, value = "resourceId") String resourceId,
                                          final @RequestParam(required = true, value = "userId") String userId) {
        List<String> userIds = new LinkedList<String>();
        userIds.add(userId);
        List<String> resourceIds = new LinkedList<String>();
        resourceIds.add(resourceId);
        ProvisionUserResponse provisionUserResponse = provisionService.deProvisionUsersToResource(userIds, getRequesterId(request), resourceIds);

        final BasicAjaxResponse ajaxResponse = getResponseAfterEntity2EntityAddition(provisionUserResponse, false);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

	protected abstract BasicAjaxResponse user2OrganizationOperation(final HttpServletRequest request, final String userId, final String organizationId, boolean isAddition);
	protected abstract BasicAjaxResponse user2ResourceOperation(final HttpServletRequest request, final String userId, final String resourceId, boolean isAddition);
	protected abstract BasicAjaxResponse user2GroupOperation(final HttpServletRequest request, final String userId, final String groupId, boolean isAddition);
	protected abstract BasicAjaxResponse user2RoleOperation(final HttpServletRequest request, final String userId, final String roleId, boolean isAddition);

    private String getActiveType(AuthorizationMenu menu) {
        String activeType = null;
        AuthorizationMenu menuNode = menu.getFirstChild();
        while(menuNode != null) {
            Pattern pattern = Pattern.compile("userEntitlements(Groups|Roles|Resources|Organizations)");
            Matcher matcher = pattern.matcher(menuNode.getUrl());
            if (matcher.find()) {
                activeType = matcher.group(1);
                break;
            }
            menuNode = menuNode.getNextSibling();
        }
        return activeType;
    }
}
