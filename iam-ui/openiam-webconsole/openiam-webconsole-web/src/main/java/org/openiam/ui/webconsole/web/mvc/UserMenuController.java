package org.openiam.ui.webconsole.web.mvc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openiam.idm.searchbeans.UserSearchBean;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.idm.srvc.user.ws.UserDataWebService;
import org.openiam.ui.webconsole.web.provider.MenuNodeViewProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class UserMenuController extends BaseUserController {
	
    @Resource(name="userServiceClient")
    private UserDataWebService userServiceClient;
	
	@Autowired
	private MenuNodeViewProvider menuNodeViewProvider;

	@RequestMapping(value="/userMenuTree", method=RequestMethod.GET)
	public String userMenuTree(final HttpServletRequest request,
							   final HttpServletResponse response,
							   final @RequestParam(value="id") String userId) throws IOException {
		final UserSearchBean searchBean = new UserSearchBean();
		searchBean.setKey(userId);
		searchBean.setDeepCopy(false);
		
		final List<User> beans = userServiceClient.findBeans(searchBean, 0, 1);
		if(CollectionUtils.isEmpty(beans)) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, String.format("User with ID '%s' not found", userId));
			return null;
		}
		
		final User user = beans.get(0);
		String displayName = null;
		if(StringUtils.isNotBlank(user.getFirstName()) && StringUtils.isNotBlank(user.getLastName())) {
			displayName = String.format("%s %s", user.getFirstName(), user.getLastName());
		} else if(StringUtils.isNotBlank(user.getLastName())) {
			displayName = StringUtils.trimToNull(user.getLastName());
		} else if(StringUtils.isNotBlank(user.getFirstName())) {
			displayName = StringUtils.trimToNull(user.getFirstName());
		}
		setMenuTree(request, userEditRootMenuId);
		return menuNodeViewProvider.menuTreeEntitlementsRequest(request, "user", userId, displayName);
	}
}
