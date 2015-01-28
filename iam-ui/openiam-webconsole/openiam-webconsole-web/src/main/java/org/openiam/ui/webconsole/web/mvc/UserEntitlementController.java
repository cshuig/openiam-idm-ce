package org.openiam.ui.webconsole.web.mvc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openiam.idm.searchbeans.UserSearchBean;
import org.openiam.idm.srvc.user.dto.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class UserEntitlementController extends BaseUserController{

    @RequestMapping(value="/userEntitlementGraph", method= RequestMethod.GET)
    public String userEntitlementGraph(final HttpServletRequest request,
                                   	   final HttpServletResponse response,
                                   	   final @RequestParam(value="id", required=true) String userId,
                                   	   @RequestParam(value="type", required=false) String type) throws IOException {
    	if(StringUtils.isBlank(type)) {
            type = "list";
        }
    	
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
        setMenuTree(request, userEditRootMenuId);
        return "users/entitlementsView";
    }
}
