import org.openiam.base.AttributeOperationEnum
import org.openiam.base.id.UUIDGen
import org.openiam.base.ws.ResponseCode
import org.openiam.base.ws.ResponseStatus
import org.openiam.connector.ConnectorService
import org.openiam.connector.type.ObjectValue
import org.openiam.connector.type.constant.StatusCodeType
import org.openiam.connector.type.request.SearchRequest
import org.openiam.connector.type.response.SearchResponse
import org.openiam.dozer.converter.ManagedSysDozerConverter
import org.openiam.dozer.converter.ManagedSystemObjectMatchDozerConverter
import org.openiam.dozer.converter.UserDozerConverter
import org.openiam.exception.ScriptEngineException
import org.openiam.idm.searchbeans.LoginSearchBean
import org.openiam.idm.searchbeans.ManualReconciliationSearchBean
import org.openiam.idm.searchbeans.GroupSearchBean
import org.openiam.idm.srvc.audit.constant.AuditAction
import org.openiam.idm.srvc.audit.constant.AuditAttributeName
import org.openiam.idm.srvc.audit.dto.IdmAuditLog
import org.openiam.idm.srvc.audit.service.AuditLogService
import org.openiam.idm.srvc.auth.domain.LoginEntity
import org.openiam.idm.srvc.auth.dto.IdentityDto
import org.openiam.idm.srvc.auth.dto.IdentityTypeEnum
import org.openiam.idm.srvc.auth.login.IdentityService
import org.openiam.idm.srvc.grp.domain.GroupEntity
import org.openiam.idm.srvc.grp.dto.Group
import org.openiam.idm.srvc.grp.service.GroupDataService
import org.openiam.idm.srvc.key.constant.KeyName
import org.openiam.idm.srvc.key.service.KeyManagementService
import org.openiam.idm.srvc.mngsys.domain.AttributeMapEntity
import org.openiam.idm.srvc.mngsys.domain.ManagedSysEntity
import org.openiam.idm.srvc.mngsys.domain.ManagedSystemObjectMatchEntity
import org.openiam.idm.srvc.mngsys.dto.ManagedSysDto
import org.openiam.idm.srvc.mngsys.dto.PolicyMapObjectTypeOptions
import org.openiam.idm.srvc.mngsys.dto.ProvisionConnectorDto
import org.openiam.idm.srvc.mngsys.service.ManagedSystemService
import org.openiam.idm.srvc.mngsys.ws.ManagedSystemWebService
import org.openiam.idm.srvc.mngsys.ws.ProvisionConnectorWebService
import org.openiam.idm.srvc.recon.command.CreateIdmAccountUserCommand
import org.openiam.idm.srvc.recon.command.CreateResourceAccountUserCommand
import org.openiam.idm.srvc.recon.command.DeleteIdmUserExcludeTargetCommand
import org.openiam.idm.srvc.recon.command.DeleteResourceAccountUserCommand
import org.openiam.idm.srvc.recon.command.DisableIdmAccountUserCommand
import org.openiam.idm.srvc.recon.command.DoNothingUserCommand
import org.openiam.idm.srvc.recon.command.ReconciliationCommandFactory
import org.openiam.idm.srvc.recon.command.RemoveIdmUserCommand
import org.openiam.idm.srvc.recon.command.UpdateIdmUserCommand
import org.openiam.idm.srvc.recon.domain.ReconciliationConfigEntity
import org.openiam.idm.srvc.recon.dto.ReconExecStatusOptions;
import org.openiam.idm.srvc.recon.dto.ReconciliationConfig;
import org.openiam.idm.srvc.recon.dto.ReconciliationResponse
import org.openiam.idm.srvc.recon.dto.ReconciliationSituation;
import org.openiam.idm.srvc.recon.result.dto.ReconciliationResultBean
import org.openiam.idm.srvc.recon.service.IDMSearchScript
import org.openiam.idm.srvc.recon.service.ReconciliationCommand
import org.openiam.idm.srvc.recon.service.ReconciliationConfigDAO
import org.openiam.idm.srvc.recon.service.ReconciliationObjectCommand
import org.openiam.idm.srvc.recon.service.ReconciliationProcessor
import org.openiam.idm.srvc.recon.service.ReconciliationSituationResponseOptions
import org.openiam.idm.srvc.recon.util.Serializer;
import org.apache.commons.lang.StringUtils
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log
import org.openiam.idm.srvc.res.dto.Resource
import org.openiam.idm.srvc.res.service.ResourceDataService
import org.openiam.idm.srvc.synch.dto.Attribute
import org.openiam.idm.srvc.synch.service.MatchObjectRule
import org.openiam.idm.srvc.synch.srcadapter.MatchRuleFactory
import org.openiam.idm.srvc.user.domain.UserEntity
import org.openiam.idm.srvc.user.dto.User
import org.openiam.idm.srvc.user.dto.UserStatusEnum
import org.openiam.idm.srvc.user.service.UserDataService
import org.openiam.idm.srvc.user.service.UserMgr
import org.openiam.provision.dto.ProvisionGroup
import org.openiam.provision.resp.LookupObjectResponse
import org.openiam.provision.service.AbstractProvisioningService
import org.openiam.provision.service.ConnectorAdapter
import org.openiam.provision.service.ObjectProvisionService
import org.openiam.provision.service.ProvisionService
import org.openiam.provision.service.ProvisionServiceUtil
import org.openiam.provision.type.ExtensibleAttribute
import org.openiam.provision.type.ExtensibleGroup
import org.openiam.script.ScriptIntegration
import org.openiam.util.MuleContextProvider
import org.openiam.util.encrypt.Cryptor;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.BeansException;
import org.apache.commons.collections.CollectionUtils;

public class GroupProcessor implements ReconciliationProcessor {

    protected ApplicationContext context;

    private static final Log log = LogFactory.getLog(ReconciliationProcessor.class);

    private AuditLogService auditLogService;

    private ResourceDataService resourceDataService

    private ManagedSystemService managedSysService;

    private KeyManagementService keyManagementService;

    private Cryptor cryptor;

    private ProvisionConnectorWebService connectorService;

    private ManagedSysDozerConverter managedSysDozerConverter;

    private ScriptIntegration scriptRunner;

    private GroupDataService groupManager;

    private ConnectorAdapter connectorAdapter;

    private MatchRuleFactory matchRuleFactory;

    private ReconciliationCommandFactory commandFactory;

    private ReconciliationConfigDAO reconConfigDao;

    private IdentityService identityService;

    private ObjectProvisionService provisionService;

    private ManagedSystemObjectMatchDozerConverter objectMatchDozerConverter;
    private UserDozerConverter userDozerConverter;
    private UserDataService userManager;

    public GroupProcessor() {
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.context = applicationContext;
    }

    public ReconciliationResponse startReconciliation(final ReconciliationConfig config, final IdmAuditLog idmAuditLog) {
        auditLogService = context.getBean("auditDataService");
        resourceDataService = context.getBean("resourceDataService");
        managedSysService = context.getBean(ManagedSystemService.class);
        keyManagementService = context.getBean("keyManagementService");
        cryptor = context.getBean("cryptor");
        managedSysDozerConverter = context.getBean("managedSysDozerConverter");
        connectorService = context.getBean("provisionConnectorWebService");
        scriptRunner = context.getBean("configurableGroovyScriptEngine");
        groupManager = context.getBean("groupManager");
        connectorAdapter = context.getBean(ConnectorAdapter.class);
        matchRuleFactory = context.getBean("matchRuleFactory");
        reconConfigDao = context.getBean(ReconciliationConfigDAO.class);
        commandFactory = context.getBean("reconciliationFactory");
        identityService = context.getBean("identityManager");
        provisionService = context.getBean("groupProvision");
        objectMatchDozerConverter =  context.getBean("managedSystemObjectMatchDozerConverter");
        userManager = context.getBean("userManager");
        userDozerConverter = context.getBean("userDozerConverter");

        log.debug("Reconciliation started for configId=" + config.getReconConfigId() + " - resource="
                + config.getResourceId());

        Resource res = resourceDataService.getResource(config.getResourceId(), null);

        ManagedSysEntity mSys = managedSysService.getManagedSysByResource(res.getId(), "ACTIVE");
        String managedSysId = (mSys != null) ? mSys.getId() : null;
        // have resource
           idmAuditLog.addAttribute(AuditAttributeName.DESCRIPTION,
                   "Reconciliation for target system: " + mSys.getName() + " is started..." + new Date());

        log.debug("ManagedSysId = " + managedSysId);
        log.debug("Getting identities for managedSys");

        ManagedSysDto sysDto = null;
        if (mSys != null) {
            sysDto = managedSysDozerConverter.convertToDTO(mSys, true);
            if (sysDto != null && sysDto.getPswd() != null) {
                try {
                    final byte[] bytes = keyManagementService.getSystemUserKey(KeyName.password.name());
                    sysDto.setDecryptPassword(cryptor.decrypt(bytes, mSys.getPswd()));
                } catch (Exception e) {
                    log.error("Can't decrypt", e);
                }
            }
        }
        // have situations
        Map<String, ReconciliationObjectCommand> situations = new HashMap<String, ReconciliationObjectCommand>();
        for (ReconciliationSituation situation : config.getSituationSet()) {
            situations.put(situation.getSituation().trim(),
                    commandFactory.createGroupCommand(situation.getSituationResp(), situation, managedSysId));
            log.debug("Created Command for: " + situation.getSituation());
        }

        // have resource connector
        ProvisionConnectorDto connector = connectorService.getProvisionConnector(sysDto.getConnectorId());
        List<AttributeMapEntity> attrMap = managedSysService.getResourceAttributeMaps(sysDto.getResourceId());
        // initialization match parameters of connector
        List<ManagedSystemObjectMatchEntity> matchObjAry = managedSysService.managedSysObjectParam(managedSysId,
                "GROUP");
        // execute all Reconciliation Commands need to be check
        if (CollectionUtils.isEmpty(matchObjAry)) {
            log.error("No match object found for this managed sys");
            return new ReconciliationResponse(ResponseStatus.FAILURE);
        }
        String keyField = matchObjAry.get(0).getKeyField();
        String baseDnField = matchObjAry.get(0).getBaseDn();

        List<LoginEntity> idmIdentities = new ArrayList<LoginEntity>();

        GroupSearchBean searchBean;
        if (StringUtils.isNotBlank(config.getMatchScript())) {
            Map<String, Object> bindingMap = new HashMap<String, Object>();
            bindingMap.put(AbstractProvisioningService.TARGET_SYS_MANAGED_SYS_ID, mSys.getId());
            bindingMap.put("searchFilter", config.getSearchFilter());
            bindingMap.put("updatedSince", config.getUpdatedSince());
            IDMSearchScript searchScript = (IDMSearchScript) scriptRunner.instantiateClass(bindingMap,
                    config.getMatchScript());
            searchBean = searchScript.createGroupSearchBean(bindingMap);
        } else {
            searchBean = new GroupSearchBean();
        }

// checking for STOP status
              ReconciliationConfigEntity configEntity = reconConfigDao.get(config.getReconConfigId());
               reconConfigDao.refresh(configEntity);
               if (configEntity.getExecStatus() == ReconExecStatusOptions.STOPPING) {
                   configEntity.setExecStatus(ReconExecStatusOptions.STOPPED);
                   idmAuditLog.addAttribute(AuditAttributeName.DESCRIPTION, "Reconciliation was manually stopped at "
                           + new Date());
                   return new ReconciliationResponse(ResponseStatus.SUCCESS);
               }

        // First get All Groups by search bean from IDM for processing
        List<String> processedGroupIds = new ArrayList<String>();

        if (searchBean != null) {
            searchBean.setManagedSysId(mSys.getId());
            List<GroupEntity> idmGroups = groupManager.findBeans(searchBean, null, 0, Integer.MAX_VALUE);
             idmAuditLog.addAttribute(AuditAttributeName.DESCRIPTION, "Starting processing '" + usersCount
                     + "' users from Repository to " + mSys.getName());
            int counter = 0;
            for (GroupEntity group : idmGroups) {
                counter++;
                // checking for STOPING status for every 10 users
                   if (counter == 10) {
                       configEntity = reconConfigDao.get(config.getReconConfigId());
                       reconConfigDao.refresh(configEntity);
                       if (configEntity.getExecStatus() == ReconExecStatusOptions.STOPPING) {
                           configEntity.setExecStatus(ReconExecStatusOptions.STOPPED);
                           idmAuditLog.addAttribute(AuditAttributeName.DESCRIPTION,
                                   "Reconciliation was manually stopped at " + new Date());
                           return new ReconciliationResponse(ResponseStatus.SUCCESS);
                       }
                       counter = 0;
                   }

                processedGroupIds.add(group.getId());
                // IDs to avoid
                // double
                // processing
                 idmAuditLog.addAttribute(AuditAttributeName.DESCRIPTION, "starting reconciliation for group: "
                         + group.getName());

                 reconciliationIDMGroupToTargetSys(attrMap, group, sysDto, situations,
                        config.getManualReconciliationFlag(), idmAuditLog);

                   idmAuditLog.addAttribute(AuditAttributeName.DESCRIPTION, "finished reconciliation for user: "
                            + identity);
            }
        }

             idmAuditLog.addAttribute(AuditAttributeName.DESCRIPTION,
                     "Reconciliation from Repository to target system: " + mSys.getName() + " is complete.");

        // 2. Do reconciliation users from Target Managed System to IDM
        // search for all Roles and Groups related with resource
        // GET Users from ConnectorAdapter by BaseDN and query rules

                idmAuditLog.addAttribute(AuditAttributeName.DESCRIPTION,
                        "Starting processing from target system: " + mSys.getName() + " to Repository");

        // checking for STOPPING status
            configEntity = reconConfigDao.get(config.getReconConfigId());
            reconConfigDao.refresh(configEntity);
            if (configEntity.getExecStatus() == ReconExecStatusOptions.STOPPING) {
                configEntity.setExecStatus(ReconExecStatusOptions.STOPPED);
                idmAuditLog.addAttribute(AuditAttributeName.DESCRIPTION, "Reconciliation was manually stopped at "
                        + new Date());
                return new ReconciliationResponse(ResponseStatus.SUCCESS);
            }

        processingTargetToIDM(config, managedSysId, sysDto, situations, connector, keyField, baseDnField,
                processedGroupIds, idmAuditLog);

         idmAuditLog.addAttribute(AuditAttributeName.DESCRIPTION,
                 "Reconciliation from target system: " + mSys.getName() + " to Repository is complete.");

            configEntity.setLastExecTime(new Date());
            configEntity.setExecStatus(ReconExecStatusOptions.FINISHED);

            idmAuditLog.addAttribute(AuditAttributeName.DESCRIPTION,
                    "Reconciliation for target system: " + mSys.getName() + " is complete.");

        return new ReconciliationResponse(ResponseStatus.SUCCESS);
    };


    private ReconciliationResponse processingTargetToIDM(ReconciliationConfig config, String managedSysId,
                                                         ManagedSysDto mSys,
                                                         Map<String, ReconciliationObjectCommand> situations,
                                                         ProvisionConnectorDto connector,
                                                         String keyField,
                                                         String baseDnField,
                                                         List<String> processedGroupIds,
                                                         final IdmAuditLog idmAuditLog)
    throws ScriptEngineException {

        if (config == null) {
            log.error("Reconciliation config is null");
            return new ReconciliationResponse(ResponseStatus.FAILURE);
        }
        if (StringUtils.isBlank(config.getTargetSystemMatchScript())) {
            log.error("SearchQuery is not defined for reconciliation config.");
            return new ReconciliationResponse(ResponseStatus.FAILURE);
        }

        Map<String, Object> bindingMap = new HashMap<String, Object>();
        bindingMap.put(AbstractProvisioningService.TARGET_SYS_MANAGED_SYS_ID, mSys.getId());
        bindingMap.put("baseDnField", baseDnField);
        bindingMap.put("searchFilter", config.getTargetSystemSearchFilter());
        bindingMap.put("lastExecTime", config.getLastExecTime());
        bindingMap.put("updatedSince", config.getUpdatedSince());
        String searchQuery = (String) scriptRunner.execute(bindingMap, config.getTargetSystemMatchScript());
        if (StringUtils.isEmpty(searchQuery)) {
            log.error("SearchQuery not defined for this reconciliation config.");
            return new ReconciliationResponse(ResponseStatus.FAILURE);
        }
        log.debug("processingTargetToIDM: mSys=" + mSys);
        SearchRequest searchRequest = new SearchRequest();
        String requestId = "R" + UUIDGen.getUUID();
        searchRequest.setRequestID(requestId);
        searchRequest.setBaseDN(baseDnField);
        searchRequest.setScriptHandler(mSys.getSearchHandler());
        searchRequest.setSearchValue(keyField);
        searchRequest.setSearchQuery(searchQuery);
        searchRequest.setTargetID(managedSysId);
        searchRequest.setHostUrl(mSys.getHostUrl());
        searchRequest.setHostPort((mSys.getPort() != null) ? mSys.getPort().toString() : null);
        searchRequest.setHostLoginId(mSys.getUserId());
        searchRequest.setHostLoginPassword(mSys.getDecryptPassword());
        searchRequest.setExtensibleObject(new ExtensibleGroup());
        SearchResponse searchResponse;

        log.debug("Calling reconcileResource with Local connector");
        searchResponse = connectorAdapter.search(searchRequest, connector, MuleContextProvider.getCtx());

        if (searchResponse != null && searchResponse.getStatus() == StatusCodeType.SUCCESS) {
            List<ObjectValue> groupsFromRemoteSys = searchResponse.getObjectList();
            if (groupsFromRemoteSys != null) {

                // AUDITLOG COUNT of proccessing users from target sys
                int counter = 0;
                for (ObjectValue groupValue : groupsFromRemoteSys) {
                    counter++;
                    // AUDITLOG start processing user Y from target systems to
                    // IDM

                    // checking for STOPPING status every 10 users
                    if (counter == 10) {
                        ReconciliationConfigEntity configEntity = reconConfigDao.findById(config.getReconConfigId());
                        reconConfigDao.refresh(configEntity);
                        if (configEntity.getExecStatus() == ReconExecStatusOptions.STOPPING) {
                            configEntity.setExecStatus(ReconExecStatusOptions.STOPPED);
                            idmAuditLog.addAttribute(AuditAttributeName.DESCRIPTION,
                                    "Reconciliation was manually stopped at " + new Date());
                            return new ReconciliationResponse(ResponseStatus.SUCCESS);
                        }
                        counter=0;
                    }

                    List<ExtensibleAttribute> extensibleAttributes = groupValue.getAttributeList() != null ? groupValue
                            .getAttributeList() : new LinkedList<ExtensibleAttribute>();

                    String targetUserPrincipal = reconcilationTargetGroupObjectToIDM(managedSysId, mSys, situations,
                            extensibleAttributes, config, processedGroupIds, idmAuditLog);

                    if (StringUtils.isNotEmpty(targetUserPrincipal)) {
                        idmAuditLog.addAttribute(AuditAttributeName.DESCRIPTION, "reconciled group: "
                                + targetUserPrincipal);
                    } else {
                        idmAuditLog.addAttribute(AuditAttributeName.DESCRIPTION,
                                "reconciled group: " + groupValue.getObjectIdentity());
                    }
                }
            }
        } else {
            log.debug(searchResponse.getErrorMessage());
            idmAuditLog.addAttribute(AuditAttributeName.DESCRIPTION, "Error: " + searchResponse);
        }
        return new ReconciliationResponse(ResponseStatus.SUCCESS);
    }

    private boolean reconciliationIDMGroupToTargetSys(List<AttributeMapEntity> attrMap,
                                                     final Group group,
                                                     final ManagedSysDto mSys,
                                                     final Map<String, ReconciliationObjectCommand<ProvisionGroup>> situations,
                                                     boolean isManualRecon,
                                                     IdmAuditLog idmAuditLog) {

        IdentityDto identity = identityService.getIdentity(group.getId(), mSys.getId());

        log.debug("1 Reconciliation for group " + group);

        List<ExtensibleAttribute> requestedExtensibleAttributes = new ArrayList<ExtensibleAttribute>();

        for (AttributeMapEntity ame : attrMap) {
            if ("GROUP".equalsIgnoreCase(ame.getMapForObjectType()) && "ACTIVE".equalsIgnoreCase(ame.getStatus())) {
                requestedExtensibleAttributes.add(new ExtensibleAttribute(ame.getAttributeName(), null));
            }
        }

        String principal = identity.getIdentity();
        log.debug("looking up identity in resource: " + principal);
        idmAuditLog.addAttribute(AuditAttributeName.DESCRIPTION, "looking up identity in resource: " + principal);

        LookupObjectResponse lookupResp = provisionService.getTargetSystemObject(principal, mSys.getId(),
                requestedExtensibleAttributes);

        log.debug("Lookup status for " + principal + " =" + lookupResp.getStatus());
        idmAuditLog.addAttribute(AuditAttributeName.DESCRIPTION,
                "Lookup status for " + principal + " =" + lookupResp.getStatus());

        boolean userFoundInTargetSystem = lookupResp.getStatus() == ResponseStatus.SUCCESS;
        ExtensibleGroup fromIDM = new ExtensibleGroup();
        ExtensibleGroup fromTS = new ExtensibleGroup();

        List<ExtensibleAttribute> extensibleAttributes = lookupResp.getAttrList() != null ? lookupResp.getAttrList()
        : new LinkedList<ExtensibleAttribute>();
        fromTS.setAttributes(extensibleAttributes);
        fromTS.setPrincipalFieldName(lookupResp.getPrincipalName());
        fromIDM.setAttributes(new ArrayList<ExtensibleAttribute>());
        this.getValuesForExtensibleGroup(fromIDM, group, attrMap, identity);
        if (userFoundInTargetSystem) {
            // Record exists in resource
            if (UserStatusEnum.DELETED.equals(group.getStatus())) {
                // IDM_DELETED__SYS_EXISTS

                if (!isManualRecon) {
                    ReconciliationObjectCommand<ProvisionGroup> command = situations.get(ReconciliationCommand.IDM_DELETED__SYS_EXISTS);
                    if (command != null) {
                        log.debug("Call command for: Record in resource but deleted in IDM");
                        ProvisionGroup provisionGroup = new ProvisionGroup(group);
                        provisionGroup.setParentAuditLogId(idmAuditLog.getId());
                        provisionGroup.setSrcSystemId(mSys.getId());
                        idmAuditLog.addAttribute(AuditAttributeName.DESCRIPTION,
                                "SYS_EXISTS__IDM_NOT_EXISTS for group= " + principal);

                        command.execute(identity, provisionGroup, extensibleAttributes);
                    }
                }
            } else {
                // IDM_EXISTS__SYS_EXISTS

                if (!isManualRecon) {
                    ReconciliationObjectCommand<ProvisionGroup> command = situations.get(ReconciliationCommand.IDM_EXISTS__SYS_EXISTS);
                    if (command != null) {
                        log.debug("Call command for: Record in resource and in IDM");
                        ProvisionGroup provisionGroup = new ProvisionGroup(group);
                        provisionGroup.setParentAuditLogId(idmAuditLog.getId());
                        provisionGroup.setSrcSystemId(mSys.getId());

                        idmAuditLog.addAttribute(AuditAttributeName.DESCRIPTION, "IDM_EXISTS__SYS_EXISTS for group= "
                                + principal);

                        command.execute(identity, provisionGroup, extensibleAttributes);
                    }
                }
            }

        } else {
            // Record not found in resource
            if (!UserStatusEnum.DELETED.equals(group.getStatus())) {
                // IDM_EXISTS__SYS_NOT_EXISTS
                if (!isManualRecon) {
                    ReconciliationObjectCommand<ProvisionGroup> command = situations.get(ReconciliationCommand.IDM_EXISTS__SYS_NOT_EXISTS);
                    if (command != null) {
                        log.debug("Call command for: Record in resource and in IDM");
                        ProvisionGroup provisionGroup = new ProvisionGroup(group);
                        provisionGroup.setParentAuditLogId(idmAuditLog.getId());
                        provisionGroup.setSrcSystemId(mSys.getId());

                        idmAuditLog.addAttribute(AuditAttributeName.DESCRIPTION,
                                "IDM_EXISTS__SYS_NOT_EXISTS for group= " + principal);

                        command.execute(identity, provisionGroup, extensibleAttributes);
                    }
                }
            }
        }

        return true;
    }
    private void getValuesForExtensibleGroup(ExtensibleGroup fromIDM, Group group, List<AttributeMapEntity> attrMap,
                                            IdentityDto identity) {
        Map<String, Object> bindingMap = new HashMap<String, Object>();
        try {
            bindingMap.put("group", new ProvisionGroup(group));
            bindingMap.put("managedSysId", identity.getManagedSysId());
            final List<ManagedSystemObjectMatchEntity> matchList = managedSysService.managedSysObjectParam(
                    identity.getManagedSysId(), "GROUP");
            if (CollectionUtils.isNotEmpty(matchList)) {
                bindingMap.put("matchParam", objectMatchDozerConverter.convertToDTO(matchList.get(0), false));
            }

            // get all users for group
            List<User> curUserList = userDozerConverter.convertToDTOList(userManager.getUsersForGroup(group.getId(), "3000", -1, -1), false);

            String decPassword = "";
            if (identity != null) {
                if (StringUtils.isEmpty(identity.getReferredObjectId())) {
                    throw new IllegalArgumentException("Identity userId can not be empty");
                }

                bindingMap.put("identity", identity);
                bindingMap.put("targetSystemIdentity", identity.getIdentity());
            }

            // make the role and group list before these updates available to
            // the
            // attribute policies
            bindingMap.put("currentUserList", curUserList);
            for (AttributeMapEntity attr : attrMap) {
                fromIDM.getAttributes().add(
                        new ExtensibleAttribute(attr.getAttributeName(), (String) ProvisionServiceUtil
                                .getOutputFromAttrMap(attr, bindingMap, scriptRunner)));
                if (PolicyMapObjectTypeOptions.GROUP_PRINCIPAL.name().equalsIgnoreCase(attr.getMapForObjectType())
                        && !"INACTIVE".equalsIgnoreCase(attr.getStatus())) {
                    fromIDM.setPrincipalFieldName(attr.getAttributeName());
                }
            }

        } catch (Exception e) {
            log.error(e);
            // e.printStackTrace();
        }
    }

    // Reconciliation processingTargetToIDM
    private String reconcilationTargetGroupObjectToIDM(String managedSysId, ManagedSysDto mSys,
                                                      Map<String, ReconciliationObjectCommand<Group>> situations,
                                                      List<ExtensibleAttribute> extensibleAttributes,
                                                      ReconciliationConfig config,
                                                      List<String> processedGroupIds,
                                                      final IdmAuditLog idmAuditLog) {
        String targetGroupPrincipal = null;

        Map<String, Attribute> attributeMap = new HashMap<String, Attribute>();
        for (ExtensibleAttribute attr : extensibleAttributes) {
            // search principal attribute by KeyField
            attributeMap.put(attr.getName(), attr);
            if (attr.getName().equals(config.getCustomMatchAttr())) {
                targetGroupPrincipal = attr.getValue();
                break;
            }
        }

        if (StringUtils.isBlank(targetGroupPrincipal)) {
            throw new IllegalArgumentException("Target system Principal can not be defined with Match Attribute Name: "
                    + config.getCustomMatchAttr());
        }

        try {
            MatchObjectRule matchObjectRule = matchRuleFactory.create(config.getCustomIdentityMatchScript());
            Group grp = matchObjectRule.lookupGroup(config, attributeMap);

            if (grp != null) {
                if (processedGroupIds.contains(grp.getId())) { // already
                    // processed
                    return targetGroupPrincipal;
                }
                Group gr = groupManager.getGroupDTO(grp.getId());
                //TODO check the identity with groupIdentity
                IdentityDto identityDto =  identityService.getIdentity(gr.getId(),managedSysId);
                // situation TARGET EXIST, IDM EXIST do modify
                // if user exists but don;t have principal for current target
                // sys
                ReconciliationObjectCommand command = situations.get(ReconciliationCommand.IDM_EXISTS__SYS_EXISTS);
                if (command != null) {
                    ProvisionGroup newGroup = new ProvisionGroup(gr);
                    newGroup.setSrcSystemId(managedSysId);

                    log.debug("Call command for IDM Match Found");
                    idmAuditLog.addAttribute(AuditAttributeName.DESCRIPTION, "IDM_EXISTS__SYS_EXISTS for group= "
                            + identityDto.identity);

                    // AUDIT LOG Y user processing IDM_EXISTS__SYS_EXISTS
                    // situation
                    command.execute(identityDto, newGroup, extensibleAttributes);

                }
            } else {
                // create new user in IDM
                ReconciliationObjectCommand command = situations.get(ReconciliationCommand.SYS_EXISTS__IDM_NOT_EXISTS);
                if (command != null) {
                    ProvisionGroup newGroup = new ProvisionGroup();

                    newGroup.setSrcSystemId(managedSysId);

                    IdentityDto identityDto =  new IdentityDto(IdentityTypeEnum.GROUP, managedSysId, targetGroupPrincipal, newGroup.getId());
                    log.debug("Call command for Match Not Found");
                    idmAuditLog.addAttribute(AuditAttributeName.DESCRIPTION, "SYS_EXISTS__IDM_NOT_EXISTS for group= "
                            + targetGroupPrincipal);

                    // AUDIT LOG Y user processing SYS_EXISTS__IDM_NOT_EXISTS
                    // situation
                    command.execute(identityDto, newGroup, extensibleAttributes);
                }
            }

        } catch (ClassNotFoundException cnfe) {
            log.error(cnfe);
        }
        return targetGroupPrincipal;
    }
}