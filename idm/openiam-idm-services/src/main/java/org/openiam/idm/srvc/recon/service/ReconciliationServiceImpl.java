/*
 * Copyright 2009, OpenIAM LLC 
 * This file is part of the OpenIAM Identity and Access Management Suite
 *
 *   OpenIAM Identity and Access Management Suite is free software: 
 *   you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License 
 *   version 3 as published by the Free Software Foundation.
 *
 *   OpenIAM is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   Lesser GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with OpenIAM.  If not, see <http://www.gnu.org/licenses/>. *
 */

/**
 * 
 */
package org.openiam.idm.srvc.recon.service;

import java.util.*;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.api.MuleContext;
import org.mule.api.context.MuleContextAware;
import org.mule.util.StringUtils;
import org.openiam.base.AttributeOperationEnum;
import org.openiam.base.id.UUIDGen;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.connector.type.*;
import org.openiam.idm.srvc.auth.dto.LoginId;
import org.openiam.idm.srvc.auth.login.LoginDataService;
import org.openiam.idm.srvc.grp.dto.Group;
import org.openiam.idm.srvc.grp.service.GroupDataService;
import org.openiam.idm.srvc.mngsys.dto.ManagedSys;
import org.openiam.idm.srvc.mngsys.dto.ManagedSystemObjectMatch;
import org.openiam.idm.srvc.mngsys.dto.ProvisionConnector;
import org.openiam.idm.srvc.mngsys.service.ConnectorDataService;
import org.openiam.idm.srvc.mngsys.service.ManagedSystemDataService;
import org.openiam.idm.srvc.recon.command.ReconciliationCommandFactory;
import org.openiam.idm.srvc.recon.dto.ReconciliationConfig;
import org.openiam.idm.srvc.recon.dto.ReconciliationResponse;
import org.openiam.idm.srvc.recon.dto.ReconciliationSituation;
import org.openiam.idm.srvc.res.dto.Resource;
import org.openiam.idm.srvc.res.dto.ResourceRole;
import org.openiam.idm.srvc.res.service.ResourceDataService;
import org.openiam.idm.srvc.role.dto.Role;
import org.openiam.idm.srvc.role.dto.RoleAttribute;
import org.openiam.idm.srvc.role.service.RoleDataService;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.idm.srvc.user.dto.UserStatusEnum;
import org.openiam.idm.srvc.user.service.UserDataService;
import org.openiam.provision.dto.ProvisionUser;
import org.openiam.provision.resp.LookupUserResponse;
import org.openiam.provision.service.ConnectorAdapter;
import  org.openiam.provision.service.ProvisionService;
import org.openiam.idm.srvc.auth.dto.Login;
import org.openiam.provision.service.RemoteConnectorAdapter;
import org.openiam.provision.type.ExtensibleAttribute;
import org.openiam.provision.type.ExtensibleObject;
import org.openiam.script.BindingModelImpl;
import org.openiam.script.ScriptFactory;
import org.openiam.script.ScriptIntegration;
import org.openiam.spml2.msg.*;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author suneet
 *
 */
public class ReconciliationServiceImpl implements ReconciliationService, MuleContextAware, ApplicationContextAware {

	protected ReconciliationSituationDAO reconSituationDao;
	protected ReconciliationResultDAO reconResultDao;
	protected ReconciliationConfigDAO reconConfigDao;
	protected ReconciliationResultDAO reconResultDetailDao;
    protected MuleContext muleContext;
    protected LoginDataService loginManager;
    protected  ProvisionService provisionService;
    protected ResourceDataService resourceDataService;
    protected UserDataService userMgr;
    protected ManagedSystemDataService managedSysService;
    protected ConnectorDataService connectorService;
    protected ConnectorAdapter connectorAdapter;
    protected RemoteConnectorAdapter remoteConnectorAdapter;
    // used to inject the application context into the groovy scripts
    public static ApplicationContext ac;

    private String scriptEngine;

    private static final Log log = LogFactory.getLog(ReconciliationServiceImpl.class);

    @Transactional
    public ReconciliationConfig addConfig(ReconciliationConfig config) {
        if (config == null) {
			throw new IllegalArgumentException("config parameter is null");
		}
		return reconConfigDao.add(config);


    }

    @Transactional
    public ReconciliationConfig updateConfig(ReconciliationConfig config) {
        if (config == null) {
                    throw new IllegalArgumentException("config parameter is null");
        }
        for(ReconciliationSituation situation : config.getSituationSet()) {
            if(StringUtils.isEmpty(situation.getReconConfigId())) {
                situation.setReconConfigId(config.getReconConfigId());
            }
        }
        return reconConfigDao.update(config);


    }

    @Transactional
    public void removeConfigByResourceId(String resourceId) {
        if (resourceId == null) {
                    throw new IllegalArgumentException("resourceId parameter is null");
        }
        reconConfigDao.removeByResourceId(resourceId);

    }

    @Transactional
    public void removeConfig(String configId) {
        if (configId == null) {
                    throw new IllegalArgumentException("configId parameter is null");
        }
        ReconciliationConfig config =  reconConfigDao.findById(configId);
        reconConfigDao.remove(config);

    }

    @Transactional(readOnly = true)
    public ReconciliationConfig getConfigByResource(String resourceId) {
    if (resourceId == null) {
                    throw new IllegalArgumentException("resourceId parameter is null");
     }
     return reconConfigDao.findByResourceId(resourceId);

    }

    public ReconciliationSituationDAO getReconSituationDao() {
		return reconSituationDao;
	}


	public void setReconSituationDao(ReconciliationSituationDAO reconSituationDao) {
		this.reconSituationDao = reconSituationDao;
	}


	public ReconciliationResultDAO getReconResultDao() {
		return reconResultDao;
	}


	public void setReconResultDao(ReconciliationResultDAO reconResultDao) {
		this.reconResultDao = reconResultDao;
	}


	public ReconciliationConfigDAO getReconConfigDao() {
		return reconConfigDao;
	}


	public void setReconConfigDao(ReconciliationConfigDAO reconConfigDao) {
		this.reconConfigDao = reconConfigDao;
	}


	public ReconciliationResultDAO getReconResultDetailDao() {
		return reconResultDetailDao;
	}


	public void setReconResultDetailDao(ReconciliationResultDAO reconResultDetailDao) {
		this.reconResultDetailDao = reconResultDetailDao;
	}

    public ReconciliationConfig getConfigById(String configId) {
       if (configId == null) {
                    throw new IllegalArgumentException("configId parameter is null");
        }
        return reconConfigDao.findById(configId);

    }

    public void setMuleContext(MuleContext ctx) {

       muleContext = ctx;
    }

    public ReconciliationResponse startReconciliation(ReconciliationConfig config) {
        try {
            log.debug("Reconciliation started for configId=" + config.getReconConfigId() + " - resource=" + config.getResourceId() );

         // have resource
            Resource res = resourceDataService.getResource(config.getResourceId());
            String managedSysId =  res.getManagedSysId();
            ManagedSys mSys = managedSysService.getManagedSys(managedSysId);
            log.debug("ManagedSysId = " + managedSysId);
            log.debug("Getting identities for managedSys");
        // have situations
            Map<String, ReconciliationCommand> situations = new HashMap<String, ReconciliationCommand>();
            for(ReconciliationSituation situation : config.getSituationSet()){
                situations.put(situation.getSituation().trim(), ReconciliationCommandFactory.createCommand(situation.getSituationResp(), situation, managedSysId));
                log.debug("Created Command for: " + situation.getSituation());
            }
         // have resource connector
            ProvisionConnector connector = connectorService.getConnector(mSys.getConnectorId());

         // TODO check IF managed system is CSV, because we don't need to do reconciliation into TargetSystem directional
            if (connector.getServiceUrl().contains("CSV")) {
				// Get user without fetches
				log.debug("Start recon");
				connectorAdapter.reconcileResource(mSys, config, muleContext);
				log.debug("end recon");
                return new ReconciliationResponse(ResponseStatus.SUCCESS);
			}
            //initialization match parameters of connector
            ManagedSystemObjectMatch[] matchObjAry = managedSysService.managedSysObjectParam(managedSysId, "USER");
            //execute all Reconciliation Commands need to be check
            if(matchObjAry.length == 0) {
                log.error("No match object found for this managed sys");
                return new ReconciliationResponse(ResponseStatus.FAILURE);
            }
            String keyField = matchObjAry[0].getKeyField();
            String baseDnField = matchObjAry[0].getBaseDn();

            ScriptIntegration scriptIntegrationCache = ScriptFactory.createModule(this.scriptEngine);

            //1. Do reconciliation users from IDM to Target Managed System search for all Roles and Groups related with resource
            // Get List with all users who have Identity if this resource
            List<Login> idmIdentities = loginManager.getAllLoginByManagedSys(managedSysId);
            for(Login identity : idmIdentities) {
                reconciliationIDMUserToTargetSys(identity, mSys, situations);
            }

            //2. Do reconciliation users from Target Managed System to IDM search for all Roles and Groups related with resource
            //GET Users from ConnectorAdapter by BaseDN and query rules
            processingTargetToIDM(config, managedSysId, mSys, situations, connector, keyField, baseDnField, scriptIntegrationCache);

		} catch(Exception e) {
			log.error(e);
            e.printStackTrace();
			ReconciliationResponse resp = new ReconciliationResponse(ResponseStatus.FAILURE);
			resp.setErrorText(e.getMessage());
			return resp;
		}

        return new ReconciliationResponse(ResponseStatus.SUCCESS);
    }

    private ReconciliationResponse processingTargetToIDM(ReconciliationConfig config, String managedSysId, ManagedSys mSys, Map<String, ReconciliationCommand> situations, ProvisionConnector connector, String keyField, String baseDnField, ScriptIntegration scriptIntegrationCache) {
        String searchQuery = (String)scriptIntegrationCache.execute(new BindingModelImpl(ac), config.getTargetSystemMatchScript());
        if(StringUtils.isEmpty(searchQuery)) {
            log.error("SearchQuery not defined for this reconciliation config.");
            return new ReconciliationResponse(ResponseStatus.FAILURE);
        }
        log.debug("processingTargetToIDM: mSys="+mSys);
        SearchRequest searchRequest = new SearchRequest();
        String requestId = "R" + UUIDGen.getUUID();
        searchRequest.setRequestID(requestId);
        searchRequest.setBaseDN(baseDnField);
        searchRequest.setScriptHandler(mSys.getSearchHandler());
        searchRequest.setSearchValue(keyField);
        searchRequest.setSearchQuery(searchQuery);
        searchRequest.setTargetID(managedSysId);
        searchRequest.setHostUrl(mSys.getHostUrl());
        searchRequest.setHostPort(mSys.getPort().toString());
        searchRequest.setHostLoginId(mSys.getUserId());
        searchRequest.setHostLoginPassword(mSys.getDecryptPassword());

        SearchResponse searchResponse;

        if (connector.getConnectorInterface() != null &&
                connector.getConnectorInterface().equalsIgnoreCase("REMOTE")) {
            log.debug("Calling reconcileResource with Remote connector");
            searchResponse = remoteConnectorAdapter.search(searchRequest, connector, muleContext);

        } else {
            log.debug("Calling reconcileResource with Local connector");
            searchResponse = connectorAdapter.search(searchRequest,connector, muleContext);
        }
        if(searchResponse != null && searchResponse.getStatus() == StatusCodeType.SUCCESS) {
            List<UserValue> usersFromRemoteSys = searchResponse.getUserList();
            if(usersFromRemoteSys != null) {
                for(UserValue userValue : usersFromRemoteSys) {
                    List<ExtensibleAttribute> extensibleAttributes = userValue.getAttributeList() != null ? userValue.getAttributeList() : new LinkedList<ExtensibleAttribute>();
                    reconcilationTargetUserObjectToIDM(managedSysId, mSys, situations, keyField, extensibleAttributes);
                }
            }
        } else {
            log.debug(searchResponse.getErrorMessage());
        }
        return new ReconciliationResponse(ResponseStatus.FAILURE);
    }

    // Reconciliation processingTargetToIDM
    private void reconcilationTargetUserObjectToIDM(String managedSysId, ManagedSys mSys, Map<String, ReconciliationCommand> situations, String keyField, List<ExtensibleAttribute> extensibleAttributes) {
        String targetUserPrincipal = null;
        for(ExtensibleAttribute attr : extensibleAttributes) {
           //search principal attribute by KeyField (matchObjAry[0].getKeyField();)
           if(attr.getName().equals(keyField)) {
               targetUserPrincipal = attr.getValue();
               break;
           }
        }
        //check if principal attribute found
        if(StringUtils.isNotEmpty(targetUserPrincipal)) {
            log.debug("reconcile principle found=> ["+keyField+"="+targetUserPrincipal+"]");
           //if principal attribute exists in user attributes from target system
           //we need to define Command
           //try to find user in IDM by login=principal
            Login login = loginManager.getLoginByManagedSys(mSys.getDomainId(), targetUserPrincipal, managedSysId);
            Login idmLogin = loginManager.getLoginByManagedSys(mSys.getDomainId(), targetUserPrincipal, "0");
            boolean identityExistsInIDM = login != null;

            if (!identityExistsInIDM) {
                // user doesn't exists in IDM

                //   SYS_EXISTS__IDM_NOT_EXISTS
                ReconciliationCommand command = situations.get(ReconciliationCommand.SYS_EXISTS__IDM_NOT_EXISTS);
                if (command != null) {
                    Login l = new Login();
                    LoginId id = new LoginId();
                    id.setDomainId(mSys.getDomainId());
                    id.setLogin(targetUserPrincipal);
                    id.setManagedSysId(managedSysId);
                    l.setId(id);
                    ProvisionUser newUser = new ProvisionUser();
                    //ADD Target user principal
                    newUser.getPrincipalList().add(l);
                    if(idmLogin != null) {
                        newUser.getPrincipalList().add(idmLogin);
                    }
                    log.debug("Call command for Match Found");
                    command.execute(l, newUser, extensibleAttributes);
                }
            }
        }
    }

    private boolean reconciliationIDMUserToTargetSys(final Login identity, final ManagedSys mSys, final Map<String, ReconciliationCommand> situations) {
        User user = userMgr.getUserWithDependent(identity.getUserId(), true);
        log.debug("1 Reconciliation for user "+user);

        String principal = identity.getId().getLogin();
        log.debug("looking up identity in resource: " + principal);

        LookupUserResponse lookupResp =  provisionService.getTargetSystemUser(principal, mSys.getManagedSysId());

        log.debug("Lookup status for " + principal + " =" +  lookupResp.getStatus());

        boolean userFoundInTargetSystem = lookupResp.getStatus() == ResponseStatus.SUCCESS;

        List<ExtensibleAttribute> extensibleAttributes = lookupResp.getAttrList() != null ? lookupResp.getAttrList() : new LinkedList<ExtensibleAttribute>();

        if (userFoundInTargetSystem) {
            // Record exists in resource
            if (user.getStatus().equals(UserStatusEnum.DELETED)) {
                //    IDM_DELETED__SYS_EXISTS
                ReconciliationCommand command = situations.get(ReconciliationCommand.IDM_DELETED__SYS_EXISTS);
                if (command != null) {
                    log.debug("Call command for: Record in resource but deleted in IDM");
                    command.execute(identity, new ProvisionUser(user), extensibleAttributes);
                }
            } else {
                //    IDM_EXISTS__SYS_EXISTS
                ReconciliationCommand command = situations.get(ReconciliationCommand.IDM_EXISTS__SYS_EXISTS);
                if (command != null) {
                    log.debug("Call command for: Record in resource and in IDM");
                    command.execute(identity, new ProvisionUser(user), extensibleAttributes);
                }
            }

        } else {
            // Record not found in resource
            if (!user.getStatus().equals(UserStatusEnum.DELETED)) {
                //    IDM_EXISTS__SYS_NOT_EXISTS
                ReconciliationCommand command = situations.get(ReconciliationCommand.IDM_EXISTS__SYS_NOT_EXISTS);
                if (command != null) {
                    log.debug("Call command for: Record in resource and in IDM");
                    command.execute(identity, new ProvisionUser(user), extensibleAttributes);
                }
            }
        }

        return true;
    }



    public LoginDataService getLoginManager() {
        return loginManager;
    }

    public void setLoginManager(LoginDataService loginManager) {
        this.loginManager = loginManager;
    }

    public ProvisionService getProvisionService() {
        return provisionService;
    }

    public void setProvisionService(ProvisionService provisionService) {
        this.provisionService = provisionService;
    }

    public ResourceDataService getResourceDataService() {
        return resourceDataService;
    }

    public void setResourceDataService(ResourceDataService resourceDataService) {
        this.resourceDataService = resourceDataService;
    }

    public UserDataService getUserMgr() {
        return userMgr;
    }

    public void setUserMgr(UserDataService userMgr) {
        this.userMgr = userMgr;
    }

    public void setManagedSysService(ManagedSystemDataService managedSysService) {
        this.managedSysService = managedSysService;
    }

    public void setConnectorService(ConnectorDataService connectorService) {
        this.connectorService = connectorService;
    }

    public void setConnectorAdapter(ConnectorAdapter connectorAdapter) {
        this.connectorAdapter = connectorAdapter;
    }

    public void setRemoteConnectorAdapter(RemoteConnectorAdapter remoteConnectorAdapter) {
        this.remoteConnectorAdapter = remoteConnectorAdapter;
    }

    public String getScriptEngine() {
        return scriptEngine;
    }

    public void setScriptEngine(String scriptEngine) {
        this.scriptEngine = scriptEngine;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ac = applicationContext;
    }
}
