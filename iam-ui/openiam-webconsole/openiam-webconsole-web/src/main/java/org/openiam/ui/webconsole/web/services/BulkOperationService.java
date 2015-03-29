package org.openiam.ui.webconsole.web.services;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.openiam.base.ws.MatchType;
import org.openiam.base.ws.SearchParam;
import org.openiam.base.ws.SortParam;
import org.openiam.idm.searchbeans.*;
import org.openiam.idm.srvc.grp.dto.Group;
import org.openiam.idm.srvc.grp.ws.GroupDataWebService;
import org.openiam.idm.srvc.lang.dto.Language;
import org.openiam.idm.srvc.meta.domain.MetadataTypeGrouping;
import org.openiam.idm.srvc.meta.dto.MetadataType;
import org.openiam.idm.srvc.meta.ws.MetadataWebService;
import org.openiam.idm.srvc.mngsys.ws.ManagedSystemWebService;
import org.openiam.idm.srvc.org.dto.Organization;
import org.openiam.idm.srvc.org.service.OrganizationDataService;
import org.openiam.idm.srvc.prov.request.dto.BulkOperationEnum;
import org.openiam.idm.srvc.prov.request.dto.BulkOperationObjectType;
import org.openiam.idm.srvc.prov.request.dto.BulkOperationRequest;
import org.openiam.idm.srvc.prov.request.dto.OperationBean;
import org.openiam.idm.srvc.res.service.ResourceDataService;
import org.openiam.idm.srvc.role.dto.Role;
import org.openiam.idm.srvc.role.ws.RoleDataWebService;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.idm.srvc.user.ws.UserDataWebService;
import org.openiam.provision.service.AsynchUserProvisionService;
import org.openiam.ui.rest.api.model.Application;
import org.openiam.ui.rest.api.model.UserBean;
import org.openiam.ui.security.OpenIAMCookieProvider;
import org.openiam.ui.web.model.BulkOperationBean;
import org.openiam.ui.web.model.BulkOperationUserSearchBean;
import org.openiam.ui.web.util.ApplicationsProvider;
import org.openiam.ui.web.util.LanguageProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.webflow.core.collection.AttributeMap;
import org.springframework.webflow.execution.RequestContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Service("bulkOperationService")
public class BulkOperationService {

    @Autowired
    @Qualifier("localeResolver")
    protected org.openiam.ui.web.util.OpeniamCookieLocaleResolver localeResolver;

    @Autowired
    protected LanguageProvider languageProvider;

    @Autowired
    protected OpenIAMCookieProvider cookieProvider;

    @Resource(name = "userServiceClient")
    protected UserDataWebService userServiceClient;

    @Resource(name = "groupServiceClient")
    protected GroupDataWebService groupServiceClient;

    @Resource(name = "roleServiceClient")
    protected RoleDataWebService roleServiceClient;

    @Resource(name = "resourceServiceClient")
    protected ResourceDataService resourceServiceClient;

    @Resource(name = "organizationServiceClient")
    protected OrganizationDataService organizationDataService;

    @Resource(name = "managedSysServiceClient")
    protected ManagedSystemWebService managedSysServiceClient;

    @Resource(name = "metadataServiceClient")
    protected MetadataWebService metadataWebService;

    @Resource(name="asynchProvisionServiceClient")
    protected AsynchUserProvisionService asynchProvisionService;

    @Autowired
    private ApplicationsProvider applicationProvider;

    @Autowired
    @Qualifier("jacksonMapper")
    protected ObjectMapper jacksonMapper;

    public void updateSelectedUserIds(RequestContext requestContext,
                                      BulkOperationBean bulkOperationBean, List<UserBean> userList) {
        Set<String> allUserIds = new HashSet<String>();
        if (CollectionUtils.isNotEmpty(userList)) {
            for (UserBean u: userList) {
                allUserIds.add(u.getId());
            }
        }
        Set<String> selectedUserIds = new HashSet<String>();
        AttributeMap attrs = requestContext.getCurrentEvent().getAttributes();
        Object userIds = attrs.get("userIds");
        if (attrs.get("userIds") != null) {
            if (userIds instanceof String) {
                selectedUserIds.add((String) userIds);
            } else {
                CollectionUtils.addAll(selectedUserIds, (String[])userIds);
            }
        }
        Set<String> unselectedUserIds = new HashSet<String>(allUserIds);
        unselectedUserIds.removeAll(selectedUserIds);

        bulkOperationBean.getUserIds().removeAll(unselectedUserIds);
        bulkOperationBean.getUserIds().addAll(selectedUserIds);
    }

    public void lastPage(BulkOperationBean bulkOperationBean) {
        int usersNum = bulkOperationBean.getUsersNum();
        int last = usersNum % bulkOperationBean.getPageSize();
        if (last == 0) {
            last = bulkOperationBean.getPageSize();
        }
        bulkOperationBean.setStartPos(bulkOperationBean.getUsersNum() - last + 1);
        bulkOperationBean.setEndPos(bulkOperationBean.getStartPos() + bulkOperationBean.getPageSize() - 1);
    }

    public void prevPage(BulkOperationBean bulkOperationBean) {
        int usersNum = bulkOperationBean.getUsersNum();
        int oldStartPos = bulkOperationBean.getStartPos();

        bulkOperationBean.setStartPos(oldStartPos - bulkOperationBean.getPageSize());
        while (bulkOperationBean.getStartPos() >= usersNum) {
            bulkOperationBean.setStartPos(bulkOperationBean.getStartPos()-bulkOperationBean.getPageSize());
        }
        if (bulkOperationBean.getStartPos() < 1) {
            bulkOperationBean.setStartPos(1);
        }
        bulkOperationBean.setEndPos(bulkOperationBean.getStartPos() + bulkOperationBean.getPageSize() - 1);
    }

    public void nextPage(BulkOperationBean bulkOperationBean) {
        int usersNum = bulkOperationBean.getUsersNum();
        int oldEndPos = bulkOperationBean.getEndPos();

        if (oldEndPos < usersNum) {
            bulkOperationBean.setStartPos(oldEndPos+1);
        }
        while (bulkOperationBean.getStartPos() > usersNum) {
            bulkOperationBean.setStartPos(bulkOperationBean.getStartPos()-bulkOperationBean.getPageSize());
        }
        if (bulkOperationBean.getStartPos() < 1) {
            bulkOperationBean.setStartPos(1);
        }
        bulkOperationBean.setEndPos(bulkOperationBean.getStartPos() + bulkOperationBean.getPageSize() - 1);
    }

    public void firstPage(BulkOperationBean bulkOperationBean) {
        bulkOperationBean.setStartPos(1);
        bulkOperationBean.setEndPos(bulkOperationBean.getStartPos() + bulkOperationBean.getPageSize() - 1);
    }

    public List<UserBean> getUserList(RequestContext requestContext, BulkOperationBean bulkOperationBean) {
        List<UserBean> beanList = new LinkedList<UserBean>();
        initUserCollections(bulkOperationBean);
        bulkOperationBean.getUserSearchBean().setDeepCopy(false);
        final List<User> userList = userServiceClient.findBeans(bulkOperationBean.getUserSearchBean(),
                bulkOperationBean.getStartPos()-1, bulkOperationBean.getPageSize());
        if(CollectionUtils.isNotEmpty(userList)) {
            for(final User user : userList) {
                beanList.add(UserBean.getInstance(user));
            }
        }
        return beanList;
    }

    public void updateUsersNum(BulkOperationBean bulkOperationBean) {
        initUserCollections(bulkOperationBean);
        bulkOperationBean.setUsersNum(userServiceClient.count(bulkOperationBean.getUserSearchBean()));
    }

    private void initUserCollections(BulkOperationBean bulkOperationBean) {
        BulkOperationUserSearchBean bean = bulkOperationBean.getUserSearchBean();
        bean.setRoleIdSet(bean.getRoleIds());
        bean.setGroupIdSet(bean.getGroupIds());
        bean.setResourceIdSet(bean.getResourceIds());
        bean.setOrganizationIdSet(bean.getOrganizationIds());
    }

    public void updateSearchUsersCriteria(RequestContext requestContext, BulkOperationBean bulkOperationBean) {
        AttributeMap attrs = requestContext.getCurrentEvent().getAttributes();

        Object searchCriteria = attrs.get("userSearchBean.searchCriteria");
        String firstName = (String)attrs.get("userSearchBean.firstName");
        String lastName = (String)attrs.get("userSearchBean.lastName");
        String maidenName = (String)attrs.get("userSearchBean.maidenName");
        String emailAddress = (String)attrs.get("userSearchBean.emailAddress");
        String principalName = (String)attrs.get("userSearchBean.principalName");
        String employeeId = (String)attrs.get("userSearchBean.employeeId");
        String userStatus = (String)attrs.get("userSearchBean.userStatus");
        String accountStatus = (String)attrs.get("userSearchBean.accountStatus");
        String jobCode = (String)attrs.get("userSearchBean.jobCode");
        String employeeType = (String)attrs.get("userSearchBean.employeeType");
        String resourceIds = (String)attrs.get("userSearchBean.resourceIds");
        String roleIds = (String)attrs.get("userSearchBean.roleIds");
        String roleName = (String)attrs.get("userSearchBean.roleName");
        String organizationIds = (String)attrs.get("userSearchBean.organizationIds");
        String organizationName = (String)attrs.get("userSearchBean.organizationName");
        String groupIds = (String)attrs.get("userSearchBean.groupIds");
        String groupName = (String)attrs.get("userSearchBean.groupName");

        List<String> searchCriteriaList = new ArrayList<String>();
        if (searchCriteria != null) {
            if (searchCriteria instanceof String) {
                searchCriteriaList.add((String)searchCriteria);
            } else if (searchCriteria instanceof String[]) {
                CollectionUtils.addAll(searchCriteriaList, (String[])searchCriteria);
            }
        }
        bulkOperationBean.getUserSearchBean().setSearchCriteria(searchCriteriaList);
        bulkOperationBean.getUserSearchBean().setFirstNameMatchToken(new SearchParam(firstName, MatchType.STARTS_WITH));
        bulkOperationBean.getUserSearchBean().setLastNameMatchToken(new SearchParam(lastName, MatchType.STARTS_WITH));
        bulkOperationBean.getUserSearchBean().setMaidenNameMatchToken(new SearchParam(maidenName, MatchType.STARTS_WITH));
        bulkOperationBean.getUserSearchBean().setEmailAddressMatchToken(new SearchParam(StringUtils.trimToNull(emailAddress), MatchType.STARTS_WITH));
        if(StringUtils.isNotBlank(principalName)) {
            LoginSearchBean lsb = new LoginSearchBean();
            lsb.setLoginMatchToken(new SearchParam(principalName, MatchType.STARTS_WITH));
            bulkOperationBean.getUserSearchBean().setPrincipal(lsb);
            bulkOperationBean.getUserSearchBean().setPrincipalName(principalName);
        } else if (bulkOperationBean.getUserSearchBean().getPrincipal() != null) {
            bulkOperationBean.getUserSearchBean().setPrincipalName("");
            bulkOperationBean.getUserSearchBean().setPrincipal(null);
        }
        bulkOperationBean.getUserSearchBean().setEmployeeIdMatchToken(new SearchParam(StringUtils.trimToNull(employeeId), MatchType.EXACT));
        bulkOperationBean.getUserSearchBean().setUserStatus(userStatus);
        bulkOperationBean.getUserSearchBean().setAccountStatus(accountStatus);
        bulkOperationBean.getUserSearchBean().setJobCode(jobCode);
        bulkOperationBean.getUserSearchBean().setEmployeeType(employeeType);
        if (StringUtils.isNotBlank(resourceIds)) {
            bulkOperationBean.getUserSearchBean().setResourceIds(null);
            bulkOperationBean.getUserSearchBean().addResourceId(resourceIds);
        } else if (CollectionUtils.isNotEmpty( bulkOperationBean.getUserSearchBean().getResourceIds())) {
            bulkOperationBean.getUserSearchBean().setResourceIds(null);
        }
        if (StringUtils.isNotBlank(roleIds)) {
            bulkOperationBean.getUserSearchBean().setRoleIds(null);
            bulkOperationBean.getUserSearchBean().addRoleId(roleIds);
            bulkOperationBean.getUserSearchBean().setRoleName(roleName);
        } else if (CollectionUtils.isNotEmpty( bulkOperationBean.getUserSearchBean().getRoleIds())) {
            bulkOperationBean.getUserSearchBean().setRoleIds(null);
            bulkOperationBean.getUserSearchBean().setRoleName("");
        }
        if (StringUtils.isNotBlank(organizationIds)) {
            bulkOperationBean.getUserSearchBean().setOrganizationIds(null);
            bulkOperationBean.getUserSearchBean().addOrganizationId(organizationIds);
            bulkOperationBean.getUserSearchBean().setOrganizationName(organizationName);
        } else if (CollectionUtils.isNotEmpty( bulkOperationBean.getUserSearchBean().getOrganizationIds())) {
            bulkOperationBean.getUserSearchBean().setOrganizationIds(null);
            bulkOperationBean.getUserSearchBean().setOrganizationName("");
        }
        if (StringUtils.isNotBlank(groupIds)) {
            bulkOperationBean.getUserSearchBean().setGroupIds(null);
            bulkOperationBean.getUserSearchBean().addGroupId(groupIds);
            bulkOperationBean.getUserSearchBean().setGroupName(groupName);
        } else if (CollectionUtils.isNotEmpty( bulkOperationBean.getUserSearchBean().getGroupIds())) {
            bulkOperationBean.getUserSearchBean().setGroupIds(null);
            bulkOperationBean.getUserSearchBean().setGroupName("");
        }

        bulkOperationBean.resetPagination();
    }

    public String updatePageSize(RequestContext requestContext, BulkOperationBean bulkOperationBean) {
        AttributeMap attrs = requestContext.getCurrentEvent().getAttributes();
        try {
            int pageSize = Integer.parseInt((String)attrs.get("pageSize"));
            bulkOperationBean.setPageSize(pageSize);
        } catch (NumberFormatException nfe) {}
        bulkOperationBean.setEndPos(bulkOperationBean.getStartPos() + bulkOperationBean.getPageSize() - 1);

        int startPos = bulkOperationBean.getStartPos() / bulkOperationBean.getPageSize() + 1;
        int endPos = bulkOperationBean.getUsersNum() / bulkOperationBean.getPageSize();
        if (bulkOperationBean.getUsersNum() % bulkOperationBean.getPageSize() > 0) {
            endPos++;
        }
        return String.format("%d / %d", startPos, endPos);
    }

    public List<String> getPageSizeList() {
        return Arrays.asList("20", "50", "100", "200", "500");
    }

    public boolean addOperation(RequestContext requestContext,
                                OperationBean operationBean, BulkOperationBean bulkOperationBean) {

        MessageContext messageContext = requestContext.getMessageContext();
        AttributeMap attrs = requestContext.getCurrentEvent().getAttributes();

        if (isUserOperationSet(bulkOperationBean)) {
            messageContext.addMessage(new MessageBuilder().error()
                    .defaultText("Single user operation is allowed only")
                    .code("openiam.ui.webconsole.user.bulk.operations.error.single.user.operation.allowed").build());
            return false;
        }

        if (!BulkOperationObjectType.USER.equals(operationBean.getObjectType()) &&
                StringUtils.isNotBlank(operationBean.getObjectId()) &&
                operationBean.getOperation()!=null) {

            String objectName = null;
            switch (operationBean.getObjectType()) {
                case GROUP:
                    final GroupSearchBean groupSearchBean = new GroupSearchBean();
                    groupSearchBean.setDeepCopy(false);
                    groupSearchBean.setKey(operationBean.getObjectId());
                    final List<Group> groupList = groupServiceClient.findBeans(groupSearchBean, getRequesterId(), 0, 1);
                    if (CollectionUtils.isNotEmpty(groupList)) {
                        Group group = groupList.get(0);
                        objectName = group.getName();
                    }
                    break;
                case ROLE:
                    final RoleSearchBean roleSearchBean = new RoleSearchBean();
                    roleSearchBean.setDeepCopy(false);
                    roleSearchBean.setKey(operationBean.getObjectId());
                    final List<Role> roleList = roleServiceClient.findBeans(roleSearchBean, getRequesterId(), 0, 1);
                    if (CollectionUtils.isNotEmpty(roleList)) {
                        Role role = roleList.get(0);
                        objectName = role.getName();
                    }
                    break;
                case RESOURCE:
                    final ResourceSearchBean resSearchBean = new ResourceSearchBean();
                    resSearchBean.setKey(operationBean.getObjectId());
                    resSearchBean.setDeepCopy(false);
                    List<org.openiam.idm.srvc.res.dto.Resource> resList =
                            resourceServiceClient.findBeans(resSearchBean, 0, 1, getCurrentLanguage());
                    if (CollectionUtils.isNotEmpty(resList)) {
                        org.openiam.idm.srvc.res.dto.Resource res = resList.get(0);
                        objectName = res.getName();
                    }
                    break;
                case ORGANIZATION:
                    final OrganizationSearchBean orgSearchBean = new OrganizationSearchBean();
                    orgSearchBean.setKey(operationBean.getObjectId());
                    orgSearchBean.setDeepCopy(false);
                    List<Organization> orgList = organizationDataService.findBeansLocalized(
                            orgSearchBean, getRequesterId(), 0, 1, getCurrentLanguage());
                    if (CollectionUtils.isNotEmpty(orgList)) {
                        Organization org = orgList.get(0);
                        objectName = org.getName();
                    }
                    break;
            }

            operationBean.setObjectName(objectName);
            bulkOperationBean.getOperations().add(new OperationBean(operationBean));
            resetOperationBean(operationBean);

        } else if (BulkOperationObjectType.USER.equals(operationBean.getObjectType()) &&
                operationBean.getOperation()!=null) {
            if (CollectionUtils.isNotEmpty(bulkOperationBean.getOperations())) {
                messageContext.addMessage(new MessageBuilder().error()
                        .defaultText("Operation list is not empty")
                        .code("openiam.ui.webconsole.user.bulk.operations.error.operation.list.notEmpty").build());
                return false;
            }

            if (BulkOperationEnum.RESET_USER_PASSWORD.equals(operationBean.getOperation())) {

                Boolean autoGeneratePassword = Boolean.parseBoolean((String)attrs.get("autoGeneratePassword"));
                Boolean notifyUserViaEmail = Boolean.parseBoolean((String)attrs.get("notifyUserViaEmail"));

                if (!autoGeneratePassword) {
                    String password = (String)attrs.get("password");
                    String confirmPassword = (String)attrs.get("confirmPassword");
                    if (StringUtils.isBlank(password)) {
                        messageContext.addMessage(new MessageBuilder().error()
                                .defaultText("User Password is required field")
                                .code("openiam.ui.webconsole.user.password.missing").build());
                        return false;
                    } else if (!StringUtils.equals(password, confirmPassword)) {
                        messageContext.addMessage(new MessageBuilder().error()
                                .defaultText("Entered passwords don't match")
                                .code("openiam.ui.webconsole.user.password.not.match").build());
                        return false;
                    }
                    operationBean.getProperties().put("password", password);
                }
                operationBean.getProperties().put("sendPasswordToUser", notifyUserViaEmail);

            } else if (BulkOperationEnum.NOTIFY_USER.equals(operationBean.getOperation())) {
                String subject = (String)attrs.get("subject");
                String text = (String)attrs.get("text");
                if (StringUtils.isBlank(subject) && StringUtils.isBlank(text)) {
                    messageContext.addMessage(new MessageBuilder().error()
                            .defaultText("Subject or text field should not be empty")
                            .code("openiam.ui.user.notification.subject.text.empty").build());
                    return false;
                }
                if (StringUtils.isNotBlank(subject)) {
                    operationBean.getProperties().put("subject", subject);
                }
                if (StringUtils.isNotBlank(text)) {
                    operationBean.getProperties().put("text", text);
                }
            }

            bulkOperationBean.getOperations().add(new OperationBean(operationBean));
            resetOperationBean(operationBean);
        }
        return true;
    }

    public void resetOperationBean(OperationBean operationBean) {
        operationBean.setObjectType(null);
        operationBean.setObjectId(null);
        operationBean.setObjectName(null);
        operationBean.setOperation(null);
        operationBean.getProperties().clear();
    }

    public void deleteOperations(RequestContext requestContext, BulkOperationBean bulkOperationBean) {
        AttributeMap attrs = requestContext.getCurrentEvent().getAttributes();
        Object operationIds = attrs.get("operationIds");
        if (CollectionUtils.isNotEmpty(bulkOperationBean.getOperations()) && operationIds != null) {
            if (operationIds instanceof String) {
                int id = Integer.valueOf((String)operationIds);
                OperationBean ob = (OperationBean)CollectionUtils.get(bulkOperationBean.getOperations(), id);
                bulkOperationBean.getOperations().remove(ob);
            } else {
                Set<OperationBean> opsToDelete = new HashSet<OperationBean>();
                int size = bulkOperationBean.getOperations().size();
                String[] removeIds = (String[])operationIds;

                for (String sid : removeIds) {
                    int id = Integer.parseInt(sid);
                    if (id >=0 && id<size) {
                        opsToDelete.add((OperationBean)CollectionUtils.get(bulkOperationBean.getOperations(), id));
                    }
                }
                bulkOperationBean.getOperations().removeAll(opsToDelete);
            }
        }
    }

    public Map<String, String> getAllEmployeeTypes() {
        return getMetadata(MetadataTypeGrouping.USER_TYPE);
    }

    public Map<String, String> getAllUserStatuses() {
        return getMetadata(MetadataTypeGrouping.USER);
    }

    public Map<String, String> getAllAccountStatuses() {
        return getMetadata(MetadataTypeGrouping.USER_2ND_STATUS);
    }

    public Map<String, String> getAllJobCodes() {
        return getMetadata(MetadataTypeGrouping.JOB_CODE);
    }

    public Map<String, String> getAllApplications() {
        final List<Application> list = applicationProvider.getAppliationsForUser(getRequesterId(), getRequest());
        Map<String, String> result = new LinkedHashMap<String, String>();
        result.put("", "-Please Select-");
        if (CollectionUtils.isNotEmpty(list)) {
            for (Application item : list) {
                result.put(item.getId(), item.getName());
            }
        }
        return result;
    }

    public Map<String, String> getAllSearchCriteriaItems() {
        Map<String, String> result = new LinkedHashMap<String, String>();
        result.put("userStatus", getLocalizedMessage("userStatus", "openiam.ui.webconsole.user.status"));
        result.put("accountStatus", getLocalizedMessage("accountStatus", "openiam.ui.webconsole.user.accountStatus"));
        result.put("application", getLocalizedMessage("application", "openiam.ui.common.application"));
        result.put("organization", getLocalizedMessage("organization", "openiam.ui.common.organization"));
        result.put("role", getLocalizedMessage("role", "openiam.ui.common.role"));
        result.put("group", getLocalizedMessage("group", "openiam.ui.common.group"));
        result.put("employeeType", getLocalizedMessage("employeeType", "openiam.ui.common.employee.type"));
        result.put("maidenName", getLocalizedMessage("maidenName", "openiam.ui.user.maiden"));
        result.put("jobCode", getLocalizedMessage("jobCode", "openiam.ui.user.job.code"));
        return result;
    }

    private Map<String, String> getMetadata(MetadataTypeGrouping grouping) {
        final MetadataTypeSearchBean searchBean = new MetadataTypeSearchBean();
        List<SortParam> sortParamList = new ArrayList<>();
        sortParamList.add(new SortParam("name"));

        searchBean.setGrouping(grouping);
        searchBean.setActive(true);
        searchBean.setSortBy(sortParamList);
        final List<MetadataType> list = metadataWebService.findTypeBeans(searchBean, 0, Integer.MAX_VALUE, getCurrentLanguage());

        Map<String, String> statuses = new LinkedHashMap<String, String>();
        statuses.put("", "-Please Select-");
        if (CollectionUtils.isNotEmpty(list)) {
            for (MetadataType item : list) {
                statuses.put(item.getId(), item.getDisplayName());
            }
        }
        return statuses;
    }

    public Map<String, String> getObjectTypes() {
        Map<String, String> objectTypes = new LinkedHashMap<String, String>();
        objectTypes.put("", "-Please Select-");
        for (BulkOperationObjectType type : BulkOperationObjectType.values()) {
            objectTypes.put(type.name(), type.getValue());
        }
        return objectTypes;
    }

    public String getObjectOperationsAsJson() throws IOException {
        Map<String, Map<String, String>> operationMap = new HashMap<String, Map<String, String>>();
        for (BulkOperationObjectType type : BulkOperationObjectType.values()) {
            Map<String, String> operations = new LinkedHashMap<String, String>();
            if (!BulkOperationObjectType.USER.equals(type)) {
                operations.put(BulkOperationEnum.ADD_ENTITLEMENT.name(), BulkOperationEnum.ADD_ENTITLEMENT.getLabel());
                operations.put(BulkOperationEnum.DELETE_ENTITLEMENT.name(), BulkOperationEnum.DELETE_ENTITLEMENT.getLabel());

            } else {
                operations.put(BulkOperationEnum.ACTIVATE_USER.name(), BulkOperationEnum.ACTIVATE_USER.getLabel());
                operations.put(BulkOperationEnum.DEACTIVATE_USER.name(), BulkOperationEnum.DEACTIVATE_USER.getLabel());
                operations.put(BulkOperationEnum.DELETE_USER.name(), BulkOperationEnum.DELETE_USER.getLabel());
                operations.put(BulkOperationEnum.ENABLE_USER.name(), BulkOperationEnum.ENABLE_USER.getLabel());
                operations.put(BulkOperationEnum.DISABLE_USER.name(), BulkOperationEnum.DISABLE_USER.getLabel());
                operations.put(BulkOperationEnum.RESET_USER_PASSWORD.name(), BulkOperationEnum.RESET_USER_PASSWORD.getLabel());
                operations.put(BulkOperationEnum.NOTIFY_USER.name(), BulkOperationEnum.NOTIFY_USER.getLabel());
            }
            operationMap.put(type.name(), operations);
        }
        return jacksonMapper.writeValueAsString(operationMap);
    }

    public void startBulkOperation(BulkOperationBean bulkOperationBean) {
        if (CollectionUtils.isNotEmpty(bulkOperationBean.getUserIds()) &&
                CollectionUtils.isNotEmpty(bulkOperationBean.getOperations())) {
            BulkOperationRequest bulkOperationRequest = new BulkOperationRequest();
            bulkOperationRequest.setUserIds(bulkOperationBean.getUserIds());
            bulkOperationRequest.setOperations(bulkOperationBean.getOperations());
            bulkOperationRequest.setRequesterId(getRequesterId());

            asynchProvisionService.startBulkOperation(bulkOperationRequest);
        }
    }

    private boolean isUserOperationSet(BulkOperationBean bulkOperationBean) {
        Set<OperationBean> operationBeans = bulkOperationBean.getOperations();
        for (OperationBean ob : operationBeans) {
            if (BulkOperationObjectType.USER.equals(ob.getObjectType())) {
                return true;
            }
        }
        return false;
    }

    public Language getCurrentLanguage() {
        return languageProvider.getCurrentLanguage(getLocale());
    }

    public Locale getLocale() {
        return localeResolver.resolveLocale(getRequest());
    }

    public String getRequesterId() {
        return cookieProvider.getUserId(getRequest());
    }

    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    protected String getLocalizedMessage(final String defaultMsg, final String key) {
        org.springframework.web.servlet.support.RequestContext ctx = new org.springframework.web.servlet.support.RequestContext(getRequest());
        return ctx.getMessage(key, defaultMsg);
    }

}
