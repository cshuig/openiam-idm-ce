package org.openiam.ui.webconsole.web.mvc;

import org.apache.commons.lang.StringUtils;
import org.openiam.ui.rest.api.model.UserSearchModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by: Alexander Duckardt
 * Date: 08.11.12
 */
@Controller
public class UserSearchController extends BaseUserController {
    
    @RequestMapping(value="/users", method = RequestMethod.GET)
    public String getSearchPage(final HttpServletRequest request, Model model) {
        setMenuTree(request, userRootMenuId);
        return "users/search";
    }
}
