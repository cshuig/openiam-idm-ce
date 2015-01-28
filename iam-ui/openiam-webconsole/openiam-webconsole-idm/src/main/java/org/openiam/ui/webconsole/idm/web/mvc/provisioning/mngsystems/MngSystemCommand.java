package org.openiam.ui.webconsole.idm.web.mvc.provisioning.mngsystems;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.openiam.am.srvc.constants.SearchScopeType;
import org.openiam.idm.srvc.mngsys.dto.ManagedSysDto;
import org.openiam.idm.srvc.mngsys.dto.ManagedSystemObjectMatch;
import org.openiam.idm.srvc.res.dto.ResourceProp;

import java.util.HashSet;
import java.util.Set;

public class MngSystemCommand {
    public static final String HIDDEN_PASSWORD = "******";

    private String id;
    @NotBlank
    private String name;
    private String description;
    private String status;
    @NotBlank
    private String connectorId;
    private String hostUrl;
    private String port;
    private String clientCommProtocol;
    private String jdbcDriverUrl;
    private String connectionString;
    private String login;
    private String password;
    private Integer primaryRepository = 0;
    private String secondaryRepositoryId;
    private Integer updateSecondary = 0;
    private String handler5;
    private String addHandler;
    private String modifyHandler;
    private String deleteHandler;
    private String passwordHandler;
    private String suspendHandler;
    private String resumeHandler;
    private String searchHandler;
    private String lookupHandler;
    private String testConnectionHandler;
    private String reconcileResourceHandler;
    private String attributeNamesHandler;
    private String attributeNamesLookup;
    private SearchScopeType searchScope = SearchScopeType.SUBTREE_SCOPE;
    private String resourceId;
    // ManagedSystemObjectMatch
    private String keyField;
    private String baseDn;
    private String searchBaseDn;
    private String searchFilter;
    private String objectSearchId;
    private boolean skipGroupProvision;
    private String keyFieldGroup;
    private String baseDnGroup;
    private String searchBaseDnGroup;
    private String searchFilterGroup;
    private String objectSearchIdGroup;
    private String submitType;
    private Set<ResourceProp> resourceProps;

    public MngSystemCommand() {
    }

    public MngSystemCommand(ManagedSysDto managedSysDto) {
        this.status = managedSysDto.getStatus();
        this.port = (managedSysDto.getPort() != null) ? managedSysDto.getPort().toString() : null;
        this.hostUrl = managedSysDto.getHostUrl();
        this.description = managedSysDto.getDescription();
        this.name = managedSysDto.getName();
        this.id = managedSysDto.getId();
        this.clientCommProtocol = managedSysDto.getCommProtocol();
        this.connectorId = managedSysDto.getConnectorId();
        this.jdbcDriverUrl = managedSysDto.getDriverUrl();
        this.connectionString = managedSysDto.getConnectionString();
        this.login = managedSysDto.getUserId();
        this.password = managedSysDto.getDecryptPassword();
        this.primaryRepository = managedSysDto.getPrimaryRepository();
        this.secondaryRepositoryId = managedSysDto.getSecondaryRepositoryId();
        this.updateSecondary = managedSysDto.getUpdateSecondary();
        this.handler5 = managedSysDto.getHandler5();
        this.addHandler = managedSysDto.getAddHandler();
        this.modifyHandler = managedSysDto.getModifyHandler();
        this.deleteHandler = managedSysDto.getDeleteHandler();
        this.passwordHandler = managedSysDto.getPasswordHandler();
        this.suspendHandler = managedSysDto.getSuspendHandler();
        this.resumeHandler = managedSysDto.getResumeHandler();
        this.searchHandler = managedSysDto.getSearchHandler();
        this.lookupHandler = managedSysDto.getLookupHandler();
        this.testConnectionHandler = managedSysDto.getTestConnectionHandler();
        this.reconcileResourceHandler = managedSysDto.getReconcileResourceHandler();
        this.attributeNamesHandler = managedSysDto.getAttributeNamesHandler();
        this.attributeNamesLookup = managedSysDto.getAttributeNamesLookup();
        this.searchScope = managedSysDto.getSearchScope();
        this.resourceId = managedSysDto.getResourceId();
        this.skipGroupProvision = managedSysDto.getSkipGroupProvision();
        if (managedSysDto.getMngSysObjectMatchs().size() > 0) {
            for (ManagedSystemObjectMatch systemObjectMatch : managedSysDto.getMngSysObjectMatchs()) {
                if (ManagedSystemObjectMatch.USER.equals(systemObjectMatch.getObjectType())) {
                    this.objectSearchId = systemObjectMatch.getObjectSearchId();
                    this.baseDn = systemObjectMatch.getBaseDn();
                    this.searchBaseDn = systemObjectMatch.getSearchBaseDn();
                    this.keyField = systemObjectMatch.getKeyField();
                    this.searchFilter = systemObjectMatch.getSearchFilter();
                } else if (ManagedSystemObjectMatch.GROUP.equals(systemObjectMatch.getObjectType())) {
                    this.objectSearchIdGroup = systemObjectMatch.getObjectSearchId();
                    this.baseDnGroup = systemObjectMatch.getBaseDn();
                    this.searchBaseDnGroup = systemObjectMatch.getSearchBaseDn();
                    this.keyFieldGroup = systemObjectMatch.getKeyField();
                    this.searchFilterGroup = systemObjectMatch.getSearchFilter();
                }
            }
        }
        this.resourceProps = new HashSet<ResourceProp>(0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResumeHandler() {
        return resumeHandler;
    }

    public void setResumeHandler(String resumeHandler) {
        this.resumeHandler = resumeHandler;
    }

    public String getConnectorId() {
        return connectorId;
    }

    public void setConnectorId(String connectorId) {
        this.connectorId = connectorId;
    }

    public String getHostUrl() {
        return hostUrl;
    }

    public void setHostUrl(String hostUrl) {
        this.hostUrl = hostUrl;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getClientCommProtocol() {
        return clientCommProtocol;
    }

    public void setClientCommProtocol(String clientCommProtocol) {
        this.clientCommProtocol = clientCommProtocol;
    }

    public String getSubmitType() {
        return submitType;
    }

    public void setSubmitType(String submitType) {
        this.submitType = submitType;
    }

    public String getJdbcDriverUrl() {
        return jdbcDriverUrl;
    }

    public void setJdbcDriverUrl(String jdbcDriverUrl) {
        this.jdbcDriverUrl = jdbcDriverUrl;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return StringUtils.isNotEmpty(this.password) ? HIDDEN_PASSWORD : StringUtils.EMPTY;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKeyField() {
        return keyField;
    }

    public void setKeyField(String keyField) {
        this.keyField = keyField;
    }

    public String getBaseDn() {
        return baseDn;
    }

    public void setBaseDn(String baseDn) {
        this.baseDn = baseDn;
    }

    public String getSearchBaseDn() {
        return searchBaseDn;
    }

    public void setSearchBaseDn(String searchBaseDn) {
        this.searchBaseDn = searchBaseDn;
    }

    public String getSearchFilter() {
        return searchFilter;
    }

    public void setSearchFilter(String searchFilter) {
        this.searchFilter = searchFilter;
    }

    public String getObjectSearchId() {
        return objectSearchId;
    }

    public void setObjectSearchId(String objectSearchId) {
        this.objectSearchId = objectSearchId;
    }

    public Integer getPrimaryRepository() {
        return primaryRepository;
    }

    public void setPrimaryRepository(Integer primaryRepository) {
        this.primaryRepository = primaryRepository;
    }

    public String getSecondaryRepositoryId() {
        return secondaryRepositoryId;
    }

    public void setSecondaryRepositoryId(String secondaryRepositoryId) {
        this.secondaryRepositoryId = secondaryRepositoryId;
    }

    public Integer getUpdateSecondary() {
        return updateSecondary;
    }

    public void setUpdateSecondary(Integer updateSecondary) {
        this.updateSecondary = updateSecondary;
    }

    public String getHandler5() {
        return handler5;
    }

    public void setHandler5(String handler5) {
        this.handler5 = handler5;
    }

    public String getAddHandler() {
        return addHandler;
    }

    public void setAddHandler(String addHandler) {
        this.addHandler = addHandler;
    }

    public String getModifyHandler() {
        return modifyHandler;
    }

    public void setModifyHandler(String modifyHandler) {
        this.modifyHandler = modifyHandler;
    }

    public String getDeleteHandler() {
        return deleteHandler;
    }

    public void setDeleteHandler(String deleteHandler) {
        this.deleteHandler = deleteHandler;
    }

    public String getPasswordHandler() {
        return passwordHandler;
    }

    public void setPasswordHandler(String passwordHandler) {
        this.passwordHandler = passwordHandler;
    }

    public String getSuspendHandler() {
        return suspendHandler;
    }

    public void setSuspendHandler(String suspendHandler) {
        this.suspendHandler = suspendHandler;
    }

    public String getSearchHandler() {
        return searchHandler;
    }

    public void setSearchHandler(String searchHandler) {
        this.searchHandler = searchHandler;
    }

    public String getLookupHandler() {
        return lookupHandler;
    }

    public void setLookupHandler(String lookupHandler) {
        this.lookupHandler = lookupHandler;
    }

    public String getTestConnectionHandler() {
        return testConnectionHandler;
    }

    public void setTestConnectionHandler(String testConnectionHandler) {
        this.testConnectionHandler = testConnectionHandler;
    }

    public String getReconcileResourceHandler() {
        return reconcileResourceHandler;
    }

    public void setReconcileResourceHandler(String reconcileResourceHandler) {
        this.reconcileResourceHandler = reconcileResourceHandler;
    }

    public String getAttributeNamesHandler() {
        return attributeNamesHandler;
    }

    public void setAttributeNamesHandler(String attributeNamesHandler) {
        this.attributeNamesHandler = attributeNamesHandler;
    }

    public boolean getSkipGroupProvision() {
        return skipGroupProvision;
    }

    public void setSkipGroupProvision(boolean skipGroupProvision) {
        this.skipGroupProvision = skipGroupProvision;
    }

    public ManagedSystemObjectMatch getManagedSystemObjectMatchUser() {
        ManagedSystemObjectMatch matchObj = new ManagedSystemObjectMatch();
        matchObj.setObjectSearchId(this.objectSearchId);
        matchObj.setBaseDn(this.baseDn);
        matchObj.setSearchBaseDn(this.searchBaseDn);
        matchObj.setKeyField(this.keyField);
        matchObj.setObjectType(ManagedSystemObjectMatch.USER);
        matchObj.setSearchFilter(this.searchFilter);
        return matchObj;
    }

    public ManagedSystemObjectMatch getManagedSystemObjectMatchGroup() {
        ManagedSystemObjectMatch matchObj = new ManagedSystemObjectMatch();
        matchObj.setObjectSearchId(this.objectSearchIdGroup);
        matchObj.setBaseDn(this.baseDnGroup);
        matchObj.setSearchBaseDn(this.searchBaseDnGroup);
        matchObj.setKeyField(this.keyFieldGroup);
        matchObj.setObjectType(ManagedSystemObjectMatch.GROUP);
        matchObj.setSearchFilter(this.searchFilterGroup);
        return matchObj;
    }

    public ManagedSysDto getUpdatedManagedSys(ManagedSysDto managedSysDto) {
        fillDTO(managedSysDto);
        if(!HIDDEN_PASSWORD.equalsIgnoreCase(this.password)) {
            managedSysDto.setPswd(this.password);
        } else {
            managedSysDto.setPswd(managedSysDto.getDecryptPassword());
        }
        return managedSysDto;
    }

    public ManagedSysDto getManagedSysDto() {
        ManagedSysDto managedSysDto = new ManagedSysDto();
        managedSysDto.setId(this.id);
        fillDTO(managedSysDto);
        // set password
        if(!MngSystemCommand.HIDDEN_PASSWORD.equalsIgnoreCase(this.password)) {
            managedSysDto.setPswd(this.password);
        }
        return managedSysDto;
    }

    private void fillDTO(ManagedSysDto managedSysDto) {
        managedSysDto.setName(this.name);
        managedSysDto.setDescription(this.description);
        managedSysDto.setHostUrl(this.hostUrl);
        try {
            managedSysDto.setPort(Integer.valueOf(this.port));
        } catch (Throwable e) {
            managedSysDto.setPort(null);
        }
        managedSysDto.setStatus(this.status);
        managedSysDto.setCommProtocol(this.clientCommProtocol);
        managedSysDto.setConnectorId(this.connectorId);
        managedSysDto.setDriverUrl(this.jdbcDriverUrl);
        managedSysDto.setConnectionString(this.connectionString);
        managedSysDto.setUserId(this.login);
        managedSysDto.setPrimaryRepository(this.primaryRepository);
        managedSysDto.setSecondaryRepositoryId(this.secondaryRepositoryId);
        managedSysDto.setUpdateSecondary(this.updateSecondary);
        managedSysDto.setHandler5(this.handler5);
        managedSysDto.setAddHandler(this.addHandler);
        managedSysDto.setModifyHandler(this.modifyHandler);
        managedSysDto.setDeleteHandler(this.deleteHandler);
        managedSysDto.setPasswordHandler(this.passwordHandler);
        managedSysDto.setSuspendHandler(this.suspendHandler);
        managedSysDto.setResumeHandler(this.resumeHandler);
        managedSysDto.setSearchHandler(this.searchHandler);
        managedSysDto.setLookupHandler(this.lookupHandler);
        managedSysDto.setTestConnectionHandler(this.testConnectionHandler);
        managedSysDto.setAttributeNamesHandler(this.attributeNamesHandler);
        managedSysDto.setReconcileResourceHandler(this.reconcileResourceHandler);
        managedSysDto.setAttributeNamesLookup(attributeNamesLookup);
        managedSysDto.setSearchScope(searchScope);
        managedSysDto.setResourceId(resourceId);
        managedSysDto.setSkipGroupProvision(skipGroupProvision);
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getAttributeNamesLookup() {
        return attributeNamesLookup;
    }

    public void setAttributeNamesLookup(String attributeNamesLookup) {
        this.attributeNamesLookup = attributeNamesLookup;
    }

    public SearchScopeType getSearchScope() {
        return searchScope;
    }

    public void setSearchScope(SearchScopeType searchScope) {
        this.searchScope = searchScope;
    }

    public String getKeyFieldGroup() {
        return keyFieldGroup;
    }

    public void setKeyFieldGroup(String keyFieldGroup) {
        this.keyFieldGroup = keyFieldGroup;
    }

    public String getBaseDnGroup() {
        return baseDnGroup;
    }

    public void setBaseDnGroup(String baseDnGroup) {
        this.baseDnGroup = baseDnGroup;
    }

    public String getSearchBaseDnGroup() {
        return searchBaseDnGroup;
    }

    public void setSearchBaseDnGroup(String searchBaseDnGroup) {
        this.searchBaseDnGroup = searchBaseDnGroup;
    }

    public String getSearchFilterGroup() {
        return searchFilterGroup;
    }

    public void setSearchFilterGroup(String searchFilterGroup) {
        this.searchFilterGroup = searchFilterGroup;
    }

    public String getObjectSearchIdGroup() {
        return objectSearchIdGroup;
    }

    public void setObjectSearchIdGroup(String objectSearchIdGroup) {
        this.objectSearchIdGroup = objectSearchIdGroup;
    }

    public Set<ResourceProp> getResourceProps() {
        return resourceProps;
    }

    public void setResourceProps(Set<ResourceProp> resourceProps) {
        this.resourceProps = resourceProps;
    }
}
