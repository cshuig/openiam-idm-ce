package org.openiam.ui.webconsole.idm.web.mvc.provisioning.synchronization;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.openiam.am.srvc.constants.SearchScopeType;
import org.openiam.idm.srvc.synch.dto.SynchConfig;

import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

public class SynchronizationCommand {

    private String synchConfigId;
    @NotBlank
    private String name;
    private String status;
    @NotBlank
    private String synchAdapter;
    private String customAdatperScript;
    private String processRule;
    private String synchType;
    private String synchFrequency;
    private String companyId;
    private String preSyncScript;
    private String postSyncScript;
    @NotBlank
    private String validationRule;
    private Boolean usePolicyMap = true;
    private Boolean useTransformationScript = true;
    private Boolean policyMapBeforeTransformation = true;
    private String transformationRule;
    private String matchFieldName;
    private String matchSrcFieldName;
    private String customMatchAttr;
    private String customMatchRule;
    private String wsScript;
    private String wsUrl;
    private Boolean useSystemPath = false;
    private String fileName;
    private String hiddenFileName;
    private MultipartFile fileUpload;
    private String srcHost;
    private String srcLoginId;
    private String srcPassword;
    private String driver;
    private String connectionUrl;
    private String query;
    private String baseDn;
    private String attributeNamesLookup;
    private SearchScopeType searchScope = SearchScopeType.SUBTREE_SCOPE;
    private String queryTimeField;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSZ")
    private Date   lastExecTime;
    private String lastRecProcessed;
    private String managedSysId;

    private String submitType;

    public SynchronizationCommand() {}

    public SynchronizationCommand(SynchConfig synchConfig) throws InvocationTargetException, IllegalAccessException {
        BeanUtils.copyProperties(synchConfig, this);
        hiddenFileName = synchConfig.getUseSystemPath() ? "" : fileName;
    }

    public SynchConfig getSynchConfigDTO() throws InvocationTargetException, IllegalAccessException {
        SynchConfig entity = new SynchConfig();
        String[] excludes = {};
        if (lastExecTime == null) {
            ArrayUtils.add(excludes, "lastExecTime");
        }
        BeanUtils.copyProperties(this, entity, excludes);
        return entity;
    }

    public String getSynchConfigId() {
        return synchConfigId;
    }

    public void setSynchConfigId(String synchConfigId) {
        this.synchConfigId = synchConfigId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSynchAdapter() {
        return synchAdapter;
    }

    public void setSynchAdapter(String synchAdapter) {
        this.synchAdapter = synchAdapter;
    }

    public String getCustomAdatperScript() {
        return customAdatperScript;
    }

    public void setCustomAdatperScript(String customAdatperScript) {
        this.customAdatperScript = customAdatperScript;
    }

    public String getProcessRule() {
        return processRule;
    }

    public void setProcessRule(String processRule) {
        this.processRule = processRule;
    }

    public String getSynchType() {
        return synchType;
    }

    public void setSynchType(String synchType) {
        this.synchType = synchType;
    }

    public String getSynchFrequency() {
        return synchFrequency;
    }

    public void setSynchFrequency(String synchFrequency) {
        this.synchFrequency = synchFrequency;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getPreSyncScript() {
        return preSyncScript;
    }

    public void setPreSyncScript(String preSyncScript) {
        this.preSyncScript = preSyncScript;
    }

    public String getPostSyncScript() {
        return postSyncScript;
    }

    public void setPostSyncScript(String postSyncScript) {
        this.postSyncScript = postSyncScript;
    }

    public String getValidationRule() {
        return validationRule;
    }

    public void setValidationRule(String validationRule) {
        this.validationRule = validationRule;
    }

    public Boolean getUsePolicyMap() {
        return usePolicyMap;
    }

    public void setUsePolicyMap(Boolean usePolicyMap) {
        this.usePolicyMap = usePolicyMap;
    }

    public Boolean getUseTransformationScript() {
        return useTransformationScript;
    }

    public void setUseTransformationScript(Boolean useTransformationScript) {
        this.useTransformationScript = useTransformationScript;
    }

    public Boolean getPolicyMapBeforeTransformation() {
        return policyMapBeforeTransformation;
    }

    public void setPolicyMapBeforeTransformation(Boolean policyMapBeforeTransformation) {
        this.policyMapBeforeTransformation = policyMapBeforeTransformation;
    }

    public String getTransformationRule() {
        return transformationRule;
    }

    public void setTransformationRule(String transformationRule) {
        this.transformationRule = transformationRule;
    }

    public String getMatchFieldName() {
        return matchFieldName;
    }

    public void setMatchFieldName(String matchFieldName) {
        this.matchFieldName = matchFieldName;
    }

    public String getMatchSrcFieldName() {
        return matchSrcFieldName;
    }

    public void setMatchSrcFieldName(String matchSrcFieldName) {
        this.matchSrcFieldName = matchSrcFieldName;
    }

    public String getCustomMatchAttr() {
        return customMatchAttr;
    }

    public void setCustomMatchAttr(String customMatchAttr) {
        this.customMatchAttr = customMatchAttr;
    }

    public String getCustomMatchRule() {
        return customMatchRule;
    }

    public void setCustomMatchRule(String customMatchRule) {
        this.customMatchRule = customMatchRule;
    }

    public String getWsScript() {
        return wsScript;
    }

    public void setWsScript(String wsScript) {
        this.wsScript = wsScript;
    }

    public String getWsUrl() {
        return wsUrl;
    }

    public void setWsUrl(String wsUrl) {
        this.wsUrl = wsUrl;
    }

    public Boolean getUseSystemPath() {
        return useSystemPath;
    }

    public void setUseSystemPath(Boolean useSystemPath) {
        this.useSystemPath = useSystemPath;
    }

    public String getFileName() {
        return fileName;
    }

    // returns filename without a timestamp
    public String getFileNameWTS() {
        return StringUtils.substringBeforeLast(FilenameUtils.getBaseName(fileName), "_")
                + FilenameUtils.EXTENSION_SEPARATOR
                + FilenameUtils.getExtension(fileName);
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getHiddenFileName() {
        return hiddenFileName;
    }

    public void setHiddenFileName(String hiddenFileName) {
        this.hiddenFileName = hiddenFileName;
    }

    public MultipartFile getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(MultipartFile fileUpload) {
        this.fileUpload = fileUpload;
    }

    public String getSrcHost() {
        return srcHost;
    }

    public void setSrcHost(String srcHost) {
        this.srcHost = srcHost;
    }

    public String getSrcLoginId() {
        return srcLoginId;
    }

    public void setSrcLoginId(String srcLoginId) {
        this.srcLoginId = srcLoginId;
    }

    public String getSrcPassword() {
        return srcPassword;
    }

    public void setSrcPassword(String srcPassword) {
        this.srcPassword = srcPassword;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getConnectionUrl() {
        return connectionUrl;
    }

    public void setConnectionUrl(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getBaseDn() {
        return baseDn;
    }

    public void setBaseDn(String baseDn) {
        this.baseDn = baseDn;
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

    public String getQueryTimeField() {
        return queryTimeField;
    }

    public void setQueryTimeField(String queryTimeField) {
        this.queryTimeField = queryTimeField;
    }

    public Date getLastExecTime() {
        return lastExecTime;
    }

    public void setLastExecTime(Date lastExecTime) {
        this.lastExecTime = lastExecTime;
    }

    public String getLastRecProcessed() {
        return lastRecProcessed;
    }

    public void setLastRecProcessed(String lastRecProcessed) {
        this.lastRecProcessed = lastRecProcessed;
    }

    public String getSubmitType() {
        return submitType;
    }

    public void setSubmitType(String submitType) {
        this.submitType = submitType;
    }

    public String getManagedSysId() {
        return managedSysId;
    }

    public void setManagedSysId(String managedSysId) {
        this.managedSysId = managedSysId;
    }
}
