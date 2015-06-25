package org.openiam.ui.webconsole.web.mvc;


import org.apache.commons.lang.StringUtils;
import org.openiam.ui.web.mvc.AbstractChallengeResponseController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ChallengeResponseController extends AbstractChallengeResponseController {

    @Value("${org.openiam.ui.landingpage.user.edit.root.id}")
    protected String userEditRootMenuId;

    @RequestMapping(value="/challengeQuestRes",method=RequestMethod.GET)
    public String challengeQuestRes(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    @RequestParam(value = "postbackUrl", required = false) String postbackUrl,
                                    @RequestParam(value = "id", required = true) String userId) throws Exception {

        if (StringUtils.isNotBlank(postbackUrl)) {
            request.setAttribute("postbackUrl", postbackUrl);
        }
        request.setAttribute("userId", userId);
        setMenuTree(request, userEditRootMenuId);
        request.setAttribute("readOnly", true);
        return challengeResponse(request, userId, false);
    }

}
