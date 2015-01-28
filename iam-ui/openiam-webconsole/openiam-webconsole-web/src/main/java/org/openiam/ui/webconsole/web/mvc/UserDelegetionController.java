package org.openiam.ui.webconsole.web.mvc;

import org.apache.commons.collections.CollectionUtils;
import org.openiam.base.AttributeOperationEnum;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.searchbeans.*;
import org.openiam.idm.srvc.grp.ws.GroupDataWebService;
import org.openiam.idm.srvc.res.service.ResourceDataService;
import org.openiam.idm.srvc.role.ws.RoleDataWebService;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.idm.srvc.user.dto.UserAttribute;
import org.openiam.idm.srvc.user.util.DelegationFilterHelper;
import org.openiam.provision.dto.ProvisionUser;
import org.openiam.ui.rest.api.model.KeyNameBean;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.webconsole.util.WSUtils;
import org.openiam.ui.webconsole.web.model.DelegationFilterModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Controller
public class UserDelegetionController extends BaseUserController {
    //
    @Resource(name="groupServiceClient")
    private GroupDataWebService groupServiceClient;
    @Resource(name="roleServiceClient")
    private RoleDataWebService roleServiceClient;
    @Resource(name="resourceServiceClient")
    private ResourceDataService resourceDataService;

    @Value("${org.openiam.organization.type.id}")
    private String organizationTypeId;
    @Value("${org.openiam.division.type.id}")
    private String divisionTypeId;
    @Value("${org.openiam.department.type.id}")
    private String departmentTypeId;


    @RequestMapping(value="/userDelegation", method = RequestMethod.GET)
    public String getUserDelegation(final HttpServletRequest request,final HttpServletResponse response, Model model,
                                    final @RequestParam(required=false, value="id") String userId) throws IOException {
        final UserSearchBean searchBean = new UserSearchBean();
        searchBean.setKey(userId);
        searchBean.setDeepCopy(true);
        final List<User> userList = userDataWebService.findBeans(searchBean, 0, 1);
        if(CollectionUtils.isEmpty(userList)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, String.format("User with id '%s' does not exist", userId));
            return null;
        }
        User usr = userList.get(0);
        Map<String, UserAttribute> attrMap = usr.getUserAttributes();

        String requesterId = getRequesterId(request);

        DelegationFilterModel filterModel = new DelegationFilterModel();

//        if (DelegationFilterHelper.isAPPFilterSet(attrMap)) {
//            ResourceSearchBean resourceSearchBean = new ResourceSearchBean();
//            resourceSearchBean.setDeepCopy(false);
//            resourceSearchBean.setResourceTypeId("NO-PROVISION-APP");
//
//
//            filterModel.setAppList(DelegationFilterHelper.getAPPFilterFromString(attrMap));
//        }
        if (DelegationFilterHelper.isGroupFilterSet(attrMap))  {
            GroupSearchBean groupSearchBean = new GroupSearchBean();
            groupSearchBean.setDeepCopy(false);
            groupSearchBean.setKeys(new HashSet<String>(DelegationFilterHelper.getGroupFilterFromString(attrMap)));

            filterModel.setGroupList(mapper.mapToList(
                    groupServiceClient.findBeans(groupSearchBean, requesterId, 0, Integer.MAX_VALUE), KeyNameBean.class));
        }
        if (DelegationFilterHelper.isOrgFilterSet(attrMap)) {

            OrganizationSearchBean orgSearchBean = new OrganizationSearchBean();
            orgSearchBean.setDeepCopy(false);
            orgSearchBean.setOrganizationTypeId(organizationTypeId);
            orgSearchBean.setKeys(DelegationFilterHelper.getOrgIdFilterFromString(attrMap));

            filterModel.setOrgFilter(mapper.mapToList(getOrganizationList(orgSearchBean, requesterId),
                                                      KeyNameBean.class));
        }
        if (DelegationFilterHelper.isDivisionFilterSet(attrMap)) {
            OrganizationSearchBean orgSearchBean = new OrganizationSearchBean();
            orgSearchBean.setDeepCopy(false);
            orgSearchBean.setOrganizationTypeId(divisionTypeId);
            orgSearchBean.setKeys(DelegationFilterHelper.getDivisionFilterFromString(attrMap));


            filterModel.setDivFilter(mapper.mapToList(getOrganizationList(orgSearchBean, requesterId),
                                                      KeyNameBean.class));
        }
        if (DelegationFilterHelper.isDeptFilterSet(attrMap)) {
            OrganizationSearchBean orgSearchBean = new OrganizationSearchBean();
            orgSearchBean.setDeepCopy(false);
            orgSearchBean.setOrganizationTypeId(departmentTypeId);
            orgSearchBean.setKeys(DelegationFilterHelper.getDeptFilterFromString(attrMap));

            filterModel.setDeptFilter(mapper.mapToList(getOrganizationList(orgSearchBean, requesterId),
                                                       KeyNameBean.class));
        }

        if (DelegationFilterHelper.isRoleFilterSet(attrMap)) {
            final RoleSearchBean roleSearchBean = new RoleSearchBean();
            roleSearchBean.setDeepCopy(false);
            roleSearchBean.setKeys(new HashSet<String>(DelegationFilterHelper.getRoleFilterFromString(attrMap)));

            filterModel.setRoleList(mapper.mapToList(
                    roleServiceClient.findBeans(roleSearchBean, requesterId, 0, Integer.MAX_VALUE), KeyNameBean.class));
        }

        filterModel.setMngRptFlag(DelegationFilterHelper.isMngRptFilterSet(attrMap));

        filterModel.setUseOrgInhFlag(DelegationFilterHelper.isUseOrgInhFilterSet(attrMap));

        filterModel.setOrganizationTypeId(organizationTypeId);
        filterModel.setDivisionTypeId(divisionTypeId);
        filterModel.setDepartmentTypeId(departmentTypeId);

        model.addAttribute("user", usr);
        model.addAttribute("filter", jacksonMapper.writeValueAsString(filterModel));

//        final ResourceSearchBean resourceSearchBean = new ResourceSearchBean();
//        resourceSearchBean.setDeepCopy(false);
//        resourceSearchBean.setResourceTypeId("NO-PROVISION-APP");
//        model.addAttribute("resourceList",  mapper.mapToList(
//                resourceDataService.findBeans(resourceSearchBean, 0, Integer.MAX_VALUE, getCurrentLanguage()), KeyNameBean.class));

        setMenuTree(request, userEditRootMenuId);

        return "users/delegation";
    }

    @RequestMapping(value="/userDelegation", method=RequestMethod.POST)
    public String saveUserDelegation(final HttpServletRequest request, @RequestBody DelegationFilterModel filter) {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        try{
            final UserSearchBean searchBean = new UserSearchBean();
            searchBean.setKey(filter.getUserId());
            searchBean.setDeepCopy(true);
            final List<User> userList = userDataWebService.findBeans(searchBean, 0, 1);
            if(CollectionUtils.isEmpty(userList)) {
                ajaxResponse.addError(new ErrorToken(Errors.USER_NOT_SET));
            } else{
                User usr = userList.get(0);
                Map<String, UserAttribute> attrMap = usr.getUserAttributes();

//                updateUserAttr(attrMap,DelegationFilterHelper.DLG_FLT_APP,  DelegationFilterHelper.getValueFromList(filter.getAppList()), filter.getUserId());
                updateUserAttr(attrMap,DelegationFilterHelper.DLG_FLT_GRP, DelegationFilterHelper.getValueFromList(filter.getGroupKeys()), filter.getUserId());
                updateUserAttr(attrMap,DelegationFilterHelper.DLG_FLT_ROLE, DelegationFilterHelper.getValueFromList(filter.getRoleKeys()), filter.getUserId());
                updateUserAttr(attrMap,DelegationFilterHelper.DLG_FLT_ORG, DelegationFilterHelper.getValueFromList(filter.getOrgFilterKeys()), filter.getUserId());
                updateUserAttr(attrMap,DelegationFilterHelper.DLG_FLT_DIV, DelegationFilterHelper.getValueFromList(filter.getDivFilterKeys()), filter.getUserId());
                updateUserAttr(attrMap,DelegationFilterHelper.DLG_FLT_DEPT, DelegationFilterHelper.getValueFromList(filter.getDeptFilterKeys()), filter.getUserId());
                updateUserAttr(attrMap,DelegationFilterHelper.DLG_FLT_MNG_RPT, filter.getMngRptFlag().toString(), filter.getUserId());
                updateUserAttr(attrMap,DelegationFilterHelper.DLG_FLT_USE_ORG_INH, filter.getUseOrgInhFlag().toString(), filter.getUserId());

                SuccessMessage succsessMessage = SuccessMessage.USER_DELEGETION_FILTER_SAVED;
                Response wsResponse = null;


                for (String attributeName : DelegationFilterHelper.getFilterTypes()) {
                    UserAttribute attribute = attrMap.get(attributeName);
                    if(attribute==null)
                        continue;
                    if (attribute.getId() == null && attribute.getValue() != null) {
                        // add
                        wsResponse = userDataWebService.addAttribute(attribute);
                    } else if (attribute.getValue() == null) {
                        // remove
                        wsResponse = userDataWebService.removeAttribute(attribute.getId());
                    } else if (attribute.getId() != null && attribute.getValue() != null) {
                        //update
                        wsResponse = userDataWebService.updateAttribute(attribute);
                    }
                }


                if(ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
                    if(provisionServiceFlag){
                        User user = userDataWebService.getUserWithDependent(usr.getId(),cookieProvider.getUserId(request),true);
                        ProvisionUser pUser = new ProvisionUser(user);
                        pUser.setRequestorUserId(getRequesterId(request));
                        WSUtils.setWSClientTimeout(provisionService, 360000L);
                        provisionService.modifyUser(pUser);
                    }
                    ajaxResponse.setStatus(HttpServletResponse.SC_OK);
                    ajaxResponse.setSuccessToken(new SuccessToken(succsessMessage));
                } else {
                    ajaxResponse.addError(new ErrorToken(Errors.USER_DELEGETION_FILTER_COULD_NOT_SAVED));
                    ajaxResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            }
        }  catch (Exception e){
            log.error(e.getMessage(), e);
            ajaxResponse.addError(new ErrorToken(Errors.INTERNAL_ERROR));
        }
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }



    private void updateUserAttr(Map<String, UserAttribute> attrMap, String attrName, String value,  String personId) {
        UserAttribute attr =  attrMap.get(attrName);
        if (attr == null) {
            // new attr
            attr = new UserAttribute(attrName, value);
            attr.setOperation(AttributeOperationEnum.ADD);
            attr.setId(null);
            attr.setUserId(personId);
            attrMap.put(attrName,attr );
        }else {
            // update existing attr
            attr.setOperation(AttributeOperationEnum.REPLACE);
            attr.setValue(value);
            attrMap.put(attrName, attr);
        }
    }
}
