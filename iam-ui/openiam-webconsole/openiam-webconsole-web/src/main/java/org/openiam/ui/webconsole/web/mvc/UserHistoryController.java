package org.openiam.ui.webconsole.web.mvc;

import org.apache.commons.lang.StringUtils;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.ui.web.util.DateFormatStr;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class UserHistoryController extends BaseUserController{

    @RequestMapping(value="/userHistory", method = RequestMethod.GET)
    public String getUserHistory(final HttpServletRequest request,
    							 final HttpServletResponse response,
    							 @RequestParam(required=false, value="type") String type,
    							 final @RequestParam(required=true, value="id") String userId) throws IOException {
    	final User user = userDataWebService.getUserWithDependent(userId, getRequesterId(request), false);
    	if(user == null) {
    		response.sendError(HttpServletResponse.SC_NOT_FOUND);
    		return null;
    	}
    	
    	type = StringUtils.isBlank(type) ? "source" : type;
    	
    	request.setAttribute("type", type);
    	setMenuTree(request, userEditRootMenuId);
    	request.setAttribute("user", user);
        request.setAttribute("dateFormatDP", DateFormatStr.getDpDate());
    	return "users/history";
    }
}
