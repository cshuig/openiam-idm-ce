package org.openiam.ui.selfservice.web.mvc.entitlements;

import org.apache.commons.collections.CollectionUtils;
import org.openiam.authmanager.service.AuthorizationManagerAdminWebService;
import org.openiam.idm.searchbeans.UserSearchBean;
import org.openiam.idm.srvc.grp.dto.Group;
import org.openiam.idm.srvc.org.dto.Organization;
import org.openiam.idm.srvc.res.dto.Resource;
import org.openiam.idm.srvc.role.dto.Role;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.mvc.entitlements.AbstractUserEntitlementsController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@Controller
public class UserEntitlementsController extends AbstractUserEntitlementsController {
	@javax.annotation.Resource(name = "authManagerAdminClient")
	private AuthorizationManagerAdminWebService authWebService;

	@Value("${org.openiam.selfservice.activiti.user.menu}")
	private String userMenu;


    @RequestMapping(value="/userOrganizations", method= RequestMethod.GET)
    public String userOrganizations(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final @RequestParam(value="id", required=true) String userId) throws IOException {

        final UserSearchBean searchBean = new UserSearchBean();
        searchBean.setKey(userId);
        final List<User> userList = userDataWebService.findBeans(searchBean, 0, 1);
        if(CollectionUtils.isEmpty(userList)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, String.format("User with id '%s' does not exist", userId));
            return null;
        }

        final User user = userList.get(0);

        request.setAttribute("user", user);
        setMenuTree(request, getEditMenu());

        final String btnString = getButtonsAsJson(request, "Organizations");

        request.setAttribute("buttonsMenu", btnString);
        return "user/userOrganizations";
    }

	@Override
	protected BasicAjaxResponse user2OrganizationOperation(
			HttpServletRequest request, String userId, String organizationId,
			boolean isAddition) {
		final User member = getEntity(userId, request);
		final Organization entity = organizationDataService.getOrganizationLocalized(organizationId, getRequesterId(request), getCurrentLanguage());
		return makeMembershipRequest(request, entity, member, isAddition);
	}

	@Override
	protected BasicAjaxResponse user2ResourceOperation(
			HttpServletRequest request, String userId, String resourceId,
			boolean isAddition) {
		final User member = getEntity(userId, request);
		final Resource entity = resourceDataService.getResource(resourceId, getCurrentLanguage());
		return makeMembershipRequest(request, entity, member, isAddition);
	}

	@Override
	protected BasicAjaxResponse user2GroupOperation(HttpServletRequest request,
			String userId, String groupId, boolean isAddition) {

		Set<String> groupOwnerIds =  authWebService.getOwnerIdsForGroup(groupId);
		String requestorId = getRequesterId(request);
		if(CollectionUtils.isNotEmpty(groupOwnerIds) && groupOwnerIds.contains(requestorId)){
			return super.user2GroupOperation(request, userId, groupId, isAddition);
		} else {
			final User member = getEntity(userId, request);
			final Group entity = groupServiceClient.getGroup(groupId, getRequesterId(request));

			return makeMembershipRequest(request, entity, member, groupOwnerIds, isAddition);
		}
	}

	@Override
	protected BasicAjaxResponse user2RoleOperation(HttpServletRequest request,
			String userId, String roleId, boolean isAddition) {
		final User member = getEntity(userId, request);
		final Role entity = roleServiceClient.getRoleLocalized(roleId, getRequesterId(request), getCurrentLanguage());
		return makeMembershipRequest(request, entity, member, isAddition);
	}

	@Override
	protected String getEditMenu() {
		return userMenu;
	}
}
