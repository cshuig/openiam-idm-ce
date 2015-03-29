package org.openiam.ui.rest.api.mvc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.SortParam;
import org.openiam.idm.searchbeans.PotentialSupSubSearchBean;
import org.openiam.idm.searchbeans.UserSearchBean;
import org.openiam.idm.srvc.org.domain.OrganizationEntity;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.idm.srvc.user.dto.UserAttribute;
import org.openiam.idm.srvc.user.ws.UserDataWebService;
import org.openiam.ui.rest.api.model.*;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.web.model.BeanResponse;
import org.openiam.ui.web.mvc.AbstractController;
import org.openiam.ui.web.util.ApplicationsProvider;
import org.openiam.ui.web.util.UserBeanPropertiesParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
public class UserRestController extends AbstractController {
    private static final Integer defaultPageSize = 10;
    private static final Integer defaultPageNumber = 0;

    @Autowired
    private UserBeanPropertiesParser userSearchParser;

    @Resource(name = "userServiceClient")
    private UserDataWebService userServiceClient;

    @Value("${org.openiam.ui.selfservice.directory.lookup.skip.delegation.filter}")
    private boolean skipDeletagionFilterForDirectoryLookup;


    @Autowired
    private ApplicationsProvider applicationProvider;

    @RequestMapping("/users/userStatuses")
    public
    @ResponseBody
    BeanResponse getUserStatuses() {
        final List<KeyNameBean> beans = getUserStatusList();
        return new BeanResponse(beans, beans.size());
    }

    @RequestMapping("/users/jobCodes")
    public
    @ResponseBody
    BeanResponse getJobCodes() {
        final List<KeyNameBean> beans = getJobCodeList();
        return new BeanResponse(beans, beans.size());
    }

    @RequestMapping("/users/userSecondaryStatuses")
    public
    @ResponseBody
    BeanResponse getUserSecondaryStatuses() {
        final List<KeyNameBean> beans = getUserSecondaryStatusList();
        return new BeanResponse(beans, beans.size());
    }

    @RequestMapping("/users/attributeList")
    public
    @ResponseBody
    BeanResponse getUserAttributeList() {
        final List<KeyNameBean> beans = getCompleteMetadataElementList();
        return new BeanResponse(beans, beans.size());
    }

    @RequestMapping("/users/employeeTypes")
    public
    @ResponseBody
    BeanResponse getEmployeeTypes() {
        final List<KeyNameBean> beans = getUserTypeList();
        return new BeanResponse(beans, beans.size());
    }

    @RequestMapping("/users/applications")
    public
    @ResponseBody
    BeanResponse getApplications(final HttpServletRequest request,
                                 @RequestParam(required = false, value = "ignoreUser") final Boolean ignoreUser) {
        final List<Application> beans = applicationProvider.getAppliationsForUser((Boolean.TRUE.equals(ignoreUser)) ? null : getRequesterId(request), request);
        return new BeanResponse(beans, beans.size());
    }

    @RequestMapping(value = "/users/getUserFormAttributes", method = RequestMethod.GET)
    public
    @ResponseBody
    UserSearchFormBean populateSearchForm(final HttpServletRequest request, String requesterId) {
        if (StringUtils.isBlank(requesterId)) {
            requesterId = getRequesterId(request);
        }

        final UserSearchFormBean userSearchFormBean = new UserSearchFormBean();
        //userSearchFormBean.setApplications(applicationProvider.getAppliationsForUser(null, request));
        userSearchFormBean.setRequesterId(requesterId);
        userSearchFormBean.setUserSearchModel(new UserSearchModel());
        //userSearchFormBean.setStatusList(getUserStatusList());
        //userSearchFormBean.setUserSecondaryStatusList(getUserSecondaryStatusList());
        //userSearchFormBean.setAttributeList(getCompleteMetadataElementList());
        //userSearchFormBean.setOrganizationList(getOrganizationBeanList(requesterId));
        //userSearchFormBean.setGroupList(getGroupList(requesterId));
        //userSearchFormBean.setRoleList(getRoleList(requesterId));
        //userSearchFormBean.setEmployeeTypes(getUserTypeList());
        userSearchFormBean.setAdditionalSearchCriteria(userSearchParser.getAdditionalSearchCriteriaList());
        //addAdditionalSearchCriteria("userStatus");
//        userSearchFormBean.addAdditionalSearchCriteria("organizationId");
        userSearchFormBean.setSize(defaultPageSize);
        userSearchFormBean.setPageNumber(defaultPageNumber);
        return userSearchFormBean;
    }

    @RequestMapping(value = "/users/searchSuperiors", method = RequestMethod.POST)
    public
    @ResponseBody
    BeanResponse searchSuperiors(final HttpServletRequest request, final @RequestBody UserSearchModel searchModel) {

        if (searchModel.isEmpty()) {
            return BeanResponse.EMPTY_RESPONSE;
        }

        PotentialSupSubSearchBean searchBean = searchModel.buildSearchBean(this.getRequesterId(request), PotentialSupSubSearchBean.class);
        //buildSearchBeanFromModel(searchModel, request);

        final int from = searchModel.getFrom();
        final int size = searchModel.getSize();
        final List<User> userList = userServiceClient.findPotentialSupSubs(searchBean, from, size);
        final Integer count = (from == 0) ? userServiceClient.findPotentialSupSubsCount(searchBean) : null;
        final List<UserBean> beanList = new LinkedList<UserBean>();
        if (CollectionUtils.isNotEmpty(userList)) {
            for (final User user : userList) {
                beanList.add(UserBean.getInstance(user));
            }
        }
        return new BeanResponse(beanList, count);
    }

    @RequestMapping(value = "/users/searchSubordinates", method = RequestMethod.POST)
    public
    @ResponseBody
    BeanResponse searchSubordinates(final HttpServletRequest request, final @RequestBody UserSearchModel searchModel) {

        if (searchModel.isEmpty()) {
            return BeanResponse.EMPTY_RESPONSE;
        }

        PotentialSupSubSearchBean searchBean = searchModel.buildSearchBean(this.getRequesterId(request), PotentialSupSubSearchBean.class);
        //buildSearchBeanFromModel(searchModel, request);

        final int from = searchModel.getFrom();
        final int size = searchModel.getSize();
        final List<User> userList = userServiceClient.findPotentialSupSubs(searchBean, from, size);
        final Integer count = (from == 0) ? userServiceClient.findPotentialSupSubsCount(searchBean) : null;
        final List<UserBean> beanList = new LinkedList<UserBean>();
        if (CollectionUtils.isNotEmpty(userList)) {
            for (final User user : userList) {
                beanList.add(UserBean.getInstance(user));
            }
        }
        return new BeanResponse(beanList, count);
    }


    @RequestMapping(value = "/users/getSuperiors", method = RequestMethod.GET)
    public
    @ResponseBody
    BeanResponse getSuperiors(final @RequestParam(required = false, value = "id") String userId,
                              final @RequestParam(required = true, value = "size") Integer size,
                              final @RequestParam(required = true, value = "from") Integer from) {

        List<User> superiors = userServiceClient.getSuperiors(userId, from, size);
        Integer count = userServiceClient.getSuperiorsCount(userId);
        final List<UserBean> beanList = new LinkedList<UserBean>();
        if (CollectionUtils.isNotEmpty(superiors)) {
            for (final User user : superiors) {
                beanList.add(UserBean.getInstance(user));
            }
        }
        return new BeanResponse(beanList, count);
    }

    @RequestMapping(value = "/users/getAllSuperiors", method = RequestMethod.GET)
    public
    @ResponseBody
    BeanResponse getAllSuperiors(final @RequestParam(required = true, value = "size") Integer size,
                                 final @RequestParam(required = true, value = "from") Integer from) {

        List<User> superiors = userServiceClient.getAllSuperiors(from, size);
        Integer count = userServiceClient.getAllSuperiorsCount();
        final List<UserBean> beanList = new LinkedList<UserBean>();
        if (CollectionUtils.isNotEmpty(superiors)) {
            for (final User user : superiors) {
                beanList.add(UserBean.getInstance(user));
            }
        }
        return new BeanResponse(beanList, count);
    }

    @RequestMapping(value = "/users/getSubordinates", method = RequestMethod.GET)
    public
    @ResponseBody
    BeanResponse getSubordinates(final @RequestParam(required = false, value = "id") String userId,
                                 final @RequestParam(required = true, value = "size") Integer size,
                                 final @RequestParam(required = true, value = "from") Integer from) {

        List<User> subordinates = userServiceClient.getSubordinates(userId, from, size);
        Integer count = userServiceClient.getSubordinatesCount(userId);
        final List<UserBean> beanList = new LinkedList<UserBean>();
        if (CollectionUtils.isNotEmpty(subordinates)) {
            for (final User user : subordinates) {
                beanList.add(UserBean.getInstance(user));
            }
        }
        return new BeanResponse(beanList, count);
    }

    @RequestMapping(value = "/users/attributes", method = RequestMethod.GET)
    public
    @ResponseBody
    BeanResponse userAttributes(final HttpServletRequest request,
                                final HttpServletResponse response,
                                final @RequestParam(required = true, value = "id") String id) {
        final List<UserAttribute> attributes = userServiceClient.getUserAttributesInternationalized(id, getCurrentLanguage());

        //IDMAPPS-2375
        if (CollectionUtils.isNotEmpty(attributes)) {
            for (final UserAttribute attribute : attributes) {
                if (StringUtils.equals(attribute.getMetadataName(), "password")) {
                    attribute.setValue("*****");
                    attribute.setValues(null);
                }
            }
        }
        final List<MetadataAttributeBean> beans = mapper.mapToList(attributes, MetadataAttributeBean.class);
        return new BeanResponse(beans, beans.size());
    }

    @RequestMapping(value = "/users/search", method = RequestMethod.POST)
    public
    @ResponseBody
    BeanResponse searchUsers(final HttpServletRequest request, final @RequestBody UserSearchModel searchModel) {
        if (searchModel.isEmpty()) {
            return BeanResponse.EMPTY_RESPONSE;
        }
        String requestorId = (skipDeletagionFilterForDirectoryLookup && searchModel.getFromDirectoryLookup()) ? null : this.getRequesterId(request);
        BeanResponse beanResponse = new BeanResponse();
        UserSearchBean searchBean = searchModel.buildSearchBean(requestorId, UserSearchBean.class);

        List<String> columnList = userSearchParser.getUserSearchResultColumnList();

        if (CollectionUtils.isNotEmpty(columnList) && columnList.contains("principal")) {
            searchBean.setInitDefaulLogin(true);
        }


        String validationError = validateSearchRequest(searchBean, request);
        final List<UserBean> beanList = new LinkedList<UserBean>();
        Integer count = 0;

        if (StringUtils.isNotBlank(validationError)) {
            beanResponse.setError(validationError);
        } else {
            final int from = searchModel.getFrom();
            final int size = searchModel.getSize();
            final List<User> userList = userServiceClient.findBeans(searchBean, from, size);
            count = (from == 0) ? userServiceClient.count(searchBean) : null;

            if (CollectionUtils.isNotEmpty(userList)) {
                for (final User user : userList) {
                    beanList.add(UserBean.getInstance(user));
                }
            }
        }
        beanResponse.setBeans(beanList);
        beanResponse.setSize(count);

        return beanResponse;
    }


    @RequestMapping(value = "/users/search/metadata", method = RequestMethod.POST)
    public
    @ResponseBody
    List<String> searchUsersMetadata(final HttpServletRequest request) {
        return userSearchParser.getUserSearchResultColumnList();
    }


    private String validateSearchRequest(UserSearchBean searchBean, final HttpServletRequest request) {
        Response wsResponse = userDataWebService.validateUserSearchRequest(searchBean);
        String errorText = null;

        if (wsResponse.getStatus() != org.openiam.base.ws.ResponseStatus.SUCCESS) {
            if (wsResponse.getErrorCode() != null) {
                switch (wsResponse.getErrorCode()) {
                    case NOT_ALLOWED_ORGANIZATION_IN_SEARCH:
                        errorText = getMessage(request, Errors.NOT_ALLOWED_ORGANIZATION_IN_SEARCH);
                        break;
                    case NOT_ALLOWED_GROUP_IN_SEARCH:
                        errorText = getMessage(request, Errors.NOT_ALLOWED_GROUP_IN_SEARCH);
                        break;
                    case NOT_ALLOWED_ROLE_IN_SEARCH:
                        errorText = getMessage(request, Errors.NOT_ALLOWED_ROLE_IN_SEARCH);
                        break;
                    default:
                        errorText = getMessage(request, Errors.INVALID_USER_SEARCH_REQUEST);
                        break;
                }
            }
        }
        return errorText;
    }

}
