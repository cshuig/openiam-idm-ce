package org.openiam.ui.selfservice.web.mvc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.openiam.idm.searchbeans.OrganizationSearchBean;
import org.openiam.idm.srvc.auth.dto.Login;
import org.openiam.idm.srvc.continfo.dto.Address;
import org.openiam.idm.srvc.org.dto.Organization;
import org.openiam.idm.srvc.org.service.OrganizationDataService;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.ui.rest.api.model.UserBean;
import org.openiam.ui.rest.api.model.UserSearchModel;
import org.openiam.ui.web.model.BeanResponse;
import org.openiam.ui.web.util.UserBeanPropertiesParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Controller
public class DirectoryLookupController extends AbstractSelfServiceController {

    @Resource(name = "organizationServiceClient")
    private OrganizationDataService organizationServiceClient;
    @Autowired
    private UserBeanPropertiesParser userBeanPropertiesParser;

    @Value("${org.openiam.ui.selfservice.directory.lookup.skip.delegation.filter}")
    private boolean skipDeletagionFilterForDirectoryLookup;

    @Value("${org.openiam.ui.selfservice.directory.lookup.show.details}")
    String showDetails;

    @RequestMapping(value = "/dirLookup", method = RequestMethod.GET)
    public String dirLookup(final HttpServletRequest request) throws IOException {
        String requestorId = getRequesterId(request);
//        final OrganizationSearchBean searchBean = new OrganizationSearchBean();
//        searchBean.setDeepCopy(true);
//        List<Organization> organizationList = organizationServiceClient.findBeansLocalized(searchBean, requestorId, 0, Integer.MAX_VALUE, getCurrentLanguage());
//        request.setAttribute("organizationList", organizationList);

        request.setAttribute("showDetails", showDetails);
        request.setAttribute("columnList", jacksonMapper.writeValueAsString(userBeanPropertiesParser.getDirLookupSearchResultColumnsList()));
        return "user/dirLookup";
    }

    @RequestMapping(value = "/viewUser", method = RequestMethod.GET)
    public String viewUser(final HttpServletRequest request,
                           final HttpServletResponse response,
                           @RequestParam(value = "id", required = true) String id) throws IOException {

        final String requestorId = skipDeletagionFilterForDirectoryLookup ? null : getRequesterId(request);
        final User user = userDataWebService.getUserWithDependent(id, requestorId, true);
        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        final List<Organization> organizationList = organizationServiceClient.getOrganizationsForUserLocalized(id, requestorId, 0, Integer.MAX_VALUE, getCurrentLanguage());
        final List<User> supervisorList = userDataWebService.getSuperiors(id, 0, Integer.MAX_VALUE);
        final List<User> subordinateList = userDataWebService.getSubordinates(id, -1, -1);

        String profilePicture = this.getProfilePicture(id);
        request.setAttribute("organizationList", organizationList);
        request.setAttribute("supervisorList", supervisorList);
        request.setAttribute("subordinateList", subordinateList);
        if (StringUtils.isNotBlank(profilePicture)) {
            request.setAttribute("profilePicture", profilePicture);
        }

        request.setAttribute("user", user);

        if (CollectionUtils.isNotEmpty(user.getPrincipalList())) {
            for (Login l : user.getPrincipalList()) {
                if (defaultManagedSysId.equals(l.getManagedSysId())) {
                    request.setAttribute("defaultLogin", l.getLogin());
                    break;
                }
            }
        }
        request.setAttribute("columnList", jacksonMapper.writeValueAsString(userBeanPropertiesParser.getViewUserSearchResultColumnsList()));
        return "user/viewUser";

    }


    @RequestMapping(value = "/subordinates/search", method = RequestMethod.POST)
    public
    @ResponseBody
    BeanResponse searchSubordinates(final @RequestBody UserSearchModel searchModel) {
        final int from = searchModel.getFrom();
        final int size = searchModel.getSize();
        final String requesterId = searchModel.getRequesterId();
        final List<User> subordinateList = userDataWebService.getSubordinates(requesterId, from, size);
        final Integer count = (from == 0) ? userDataWebService.getSubordinatesCount(requesterId) : null;

        final List<UserBean> beanList = new LinkedList<UserBean>();
        if (CollectionUtils.isNotEmpty(subordinateList)) {
            for (final User user : subordinateList) {
                beanList.add(UserBean.getInstance(user));
            }
        }
        return new BeanResponse(beanList, count);
    }
}
