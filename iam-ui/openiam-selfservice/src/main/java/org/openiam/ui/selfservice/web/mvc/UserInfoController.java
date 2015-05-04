package org.openiam.ui.selfservice.web.mvc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openiam.authmanager.ws.request.UserRequest;
import org.openiam.authmanager.ws.response.GroupsForUserResponse;
import org.openiam.authmanager.ws.response.RolesForUserResponse;
import org.openiam.idm.searchbeans.LoginSearchBean;
import org.openiam.idm.srvc.auth.dto.Login;
import org.openiam.idm.srvc.continfo.dto.Address;
import org.openiam.idm.srvc.continfo.dto.Phone;
import org.openiam.idm.srvc.lang.dto.Language;
import org.openiam.idm.srvc.meta.dto.MetadataType;
import org.openiam.idm.srvc.meta.ws.MetadataWebService;
import org.openiam.idm.srvc.policy.dto.ITPolicy;
import org.openiam.idm.srvc.policy.service.PolicyDataService;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.ui.web.util.UsePolicyHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class UserInfoController extends AbstractSelfServiceController {

    @Resource(name = "policyServiceClient")
    private PolicyDataService policyDataService;

    @Resource(name = "metadataServiceClient")
    private MetadataWebService metadataServiceClient;

    @RequestMapping("/myInfo")
    public String myInfo(final HttpServletRequest request, final HttpServletResponse response) {
        final String userId = cookieProvider.getUserId(request);
        final User user = userDataWebService.getUserWithDependent(userId, null, false);
        final List<User> supervisorList = userDataWebService.getSuperiors(user.getId(), 0, Integer.MAX_VALUE);
        Language language = getCurrentLanguage();

        final UserRequest authRequest = new UserRequest();
        authRequest.setUserId(userId);
        final GroupsForUserResponse groupsForUserResponse = authorizationManager.getGroupsFor(authRequest);
        final RolesForUserResponse rolesForUserResponse = authorizationManager.getRolesFor(authRequest);

        final LoginSearchBean searchBean = new LoginSearchBean();
        searchBean.setUserId(userId);
        searchBean.setManagedSysId(defaultManagedSysId);
        final List<Login> loginList = loginServiceClient.findBeans(searchBean, 0, Integer.MAX_VALUE);
        final Login openiamLogin = (CollectionUtils.isNotEmpty(loginList)) ? loginList.get(0) : null;

        ITPolicy itPolicy = policyDataService.findITPolicy();
        Boolean status = UsePolicyHelper.getUsePolicyStatus(itPolicy, user);
        if (status != null) {
            request.setAttribute("itPolicyStatus", status);
        }
        String profilePicture = this.getProfilePicture(userId);
        request.setAttribute("login", openiamLogin);
        request.setAttribute("roles", rolesForUserResponse.getRoles());
        request.setAttribute("groups", groupsForUserResponse.getGroups());
        request.setAttribute("supervisorList", supervisorList);
        request.setAttribute("user", user);
        Phone defaultPhone = user.getDefaultPhone();
        if(defaultPhone != null) {
            MetadataType metadataType = metadataServiceClient.getMetadataTypeById(defaultPhone.getMetadataTypeId());
            String phoneLabel =  metadataType.getDisplayNameMap().get(language.getId()).getValue();
            request.setAttribute("defaultPhone", defaultPhone);
            request.setAttribute("phoneLabel", phoneLabel);
        }
        Address defaultAddress = user.getDefaultAddress();
        if(defaultAddress != null) {
            MetadataType metadataType = metadataServiceClient.getMetadataTypeById(defaultAddress.getMetadataTypeId());
            String defaultAddressLabel =  metadataType.getDisplayNameMap().get(language.getId()).getValue();
            request.setAttribute("defaultAddress", defaultAddress);
            request.setAttribute("defaultAddressLabel", defaultAddressLabel);
        }
        if (StringUtils.isNotBlank(profilePicture)) {
            request.setAttribute("profilePicture", profilePicture);
        }

        return "user/myInfo";
    }
}
