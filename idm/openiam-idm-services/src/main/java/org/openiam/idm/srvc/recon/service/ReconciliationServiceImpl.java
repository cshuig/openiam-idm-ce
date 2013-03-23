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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.api.MuleContext;
import org.mule.api.context.MuleContextAware;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.connector.type.RemoteReconciliationConfig;
import org.openiam.idm.srvc.auth.dto.Login;
import org.openiam.idm.srvc.auth.login.LoginDataService;
import org.openiam.idm.srvc.csv.CSVParser;
import org.openiam.idm.srvc.csv.ReconciliationObject;
import org.openiam.idm.srvc.mngsys.dto.AttributeMap;
import org.openiam.idm.srvc.mngsys.dto.ManagedSys;
import org.openiam.idm.srvc.mngsys.dto.ProvisionConnector;
import org.openiam.idm.srvc.mngsys.service.ConnectorDataService;
import org.openiam.idm.srvc.mngsys.service.ManagedSystemDataService;
import org.openiam.idm.srvc.recon.command.ReconciliationCommandFactory;
import org.openiam.idm.srvc.recon.dto.ReconciliationConfig;
import org.openiam.idm.srvc.recon.dto.ReconciliationResponse;
import org.openiam.idm.srvc.recon.dto.ReconciliationSituation;
import org.openiam.idm.srvc.recon.report.ReconciliationReport;
import org.openiam.idm.srvc.recon.report.ReconciliationReportResults;
import org.openiam.idm.srvc.recon.report.ReconciliationReportRow;
import org.openiam.idm.srvc.res.dto.Resource;
import org.openiam.idm.srvc.res.dto.ResourceRole;
import org.openiam.idm.srvc.res.service.ResourceDataService;
import org.openiam.idm.srvc.role.service.RoleDataService;
import org.openiam.idm.srvc.user.domain.UserWrapperEntity;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.idm.srvc.user.dto.UserStatusEnum;
import org.openiam.idm.srvc.user.service.UserDataService;
import org.openiam.provision.dto.ProvisionUser;
import org.openiam.provision.resp.LookupUserResponse;
import org.openiam.provision.service.ConnectorAdapter;
import org.openiam.provision.service.ProvisionService;
import org.openiam.provision.service.RemoteConnectorAdapter;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author suneet
 * 
 */
public class ReconciliationServiceImpl implements ReconciliationService,
		MuleContextAware {

	private ReconciliationSituationDAO reconSituationDao;
	private ReconciliationResultDAO reconResultDao;

	/**
	 * @param userCSVParser
	 *            the userCSVParser to set
	 */
	public void setUserCSVParser(CSVParser<ProvisionUser> userCSVParser) {
		this.userCSVParser = userCSVParser;
	}

	private ReconciliationConfigDAO reconConfigDao;
	private ReconciliationResultDAO reconResultDetailDao;
	private MuleContext muleContext;
	private LoginDataService loginManager;
	private ProvisionService provisionService;
	private ResourceDataService resourceDataService;
	private UserDataService userMgr;
	private ManagedSystemDataService managedSysService;
	private ConnectorDataService connectorService;
	private ConnectorAdapter connectorAdapter;
	private RemoteConnectorAdapter remoteConnectorAdapter;
	private RoleDataService roleDataService;
	private CSVParser<ProvisionUser> userCSVParser;
	private String pathToCSV;

	/**
	 * @param pathToCSV
	 *            the pathToCSV to set
	 */
	public void setPathToCSV(String pathToCSV) {
		this.pathToCSV = pathToCSV;
	}

	private static final Log log = LogFactory
			.getLog(ReconciliationServiceImpl.class);

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
		ReconciliationConfig config = reconConfigDao.findById(configId);
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

	public void setReconSituationDao(
			ReconciliationSituationDAO reconSituationDao) {
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

	public void setReconResultDetailDao(
			ReconciliationResultDAO reconResultDetailDao) {
		this.reconResultDetailDao = reconResultDetailDao;
	}

    @Transactional(readOnly = true)
	public ReconciliationConfig getConfigById(String configId) {
		if (configId == null) {
			throw new IllegalArgumentException("configId parameter is null");
		}
		return reconConfigDao.findById(configId);

	}

	public void setMuleContext(MuleContext ctx) {

		muleContext = ctx;
	}

    @Transactional
	public ReconciliationResponse startReconciliation(
			ReconciliationConfig config) {
		ReconciliationReport report = new ReconciliationReport();
		ReconciliationResponse resp = new ReconciliationResponse();
		Resource res = resourceDataService.getResource(config.getResourceId());
		String managedSysId = res.getManagedSysId();
		ManagedSys mSys = managedSysService.getManagedSys(managedSysId);
		try {
			log.debug("Reconciliation started for configId="
					+ config.getReconConfigId() + " - resource="
					+ config.getResourceId());

			ProvisionConnector connector = connectorService.getConnector(mSys
					.getConnectorId());
			// IF managed system is CSV
			if (connector.getServiceUrl().contains("CSV")) {
				List<UserWrapperEntity> users = userMgr.getAllUsers();
				if (users == null) {
					resp = new ReconciliationResponse(ResponseStatus.FAILURE);
					resp.setErrorText("USERS table is empty");
					return resp;
				}
				// Get user without fetches
				config.setUserList(users);
				log.debug("Start recon");
				connectorAdapter.reconcileResource(mSys, config, muleContext);
				log.debug("end recon");
				resp = new ReconciliationResponse(ResponseStatus.SUCCESS);
				return resp;
			}

			List<AttributeMap> attrMap = managedSysService
					.getResourceAttributeMaps(config.getResourceId());
			// Fill report header
			report.getReport().add(new ReconciliationReportRow(attrMap));
			log.debug("ManagedSysId = " + managedSysId);
			log.debug("Getting identities for managedSys");

			List<UserWrapperEntity> users = new ArrayList<UserWrapperEntity>();
			for (ResourceRole rRole : res.getResourceRoles()) {
				List<UserWrapperEntity> ids = roleDataService.findUserWByRole(
						mSys.getDomainId(), rRole.getId().getRoleId());
				users.addAll(ids);
			}

			Map<String, ReconciliationCommand> situations = new HashMap<String, ReconciliationCommand>();
			for (ReconciliationSituation situation : config.getSituationSet()) {
				situations.put(situation.getSituation().trim(),
						ReconciliationCommandFactory.createCommand(
								situation.getSituationResp(), situation,
								managedSysId));
				log.debug("Created Command for: " + situation.getSituation());
			}

			List<Login> principalList = loginManager
					.getAllLoginByManagedSys(managedSysId);
			if (principalList == null || principalList.isEmpty()) {
				log.debug("No identities found for managedSysId in IDM repository");
				resp = new ReconciliationResponse(ResponseStatus.SUCCESS);
				report.getReport()
						.add(new ReconciliationReportRow(
								"No identities found for managedSysId in IDM repository",
								ReconciliationReport.getHeader(attrMap).size() + 1));
				report.save(pathToCSV, mSys);
				return resp;
			}
			for (UserWrapperEntity u : users) {
				Login l = null;
				User user = userMgr.getUserWithDependent(u.getUserId(), true);
				List<Login> logins = user.getPrincipalList();
				if (logins != null) {
					for (Login login : logins) {
						if (login.getId().getDomainId()
								.equalsIgnoreCase(mSys.getDomainId())
								&& login.getId().getManagedSysId()
										.equalsIgnoreCase(managedSysId)) {
							l = login;
							break;
						}
					}
				}
				if (l == null) {
					if (user.getStatus().equals(UserStatusEnum.DELETED)) {
						// User is deleted and has no Identity for this managed
						// system -> goto next user
						continue;
					}
					// There was never a resource account for this user.
					// Possibility: User was created before the managed Sys was
					// associated.
					// Situation: Login Not Found

					report.getReport()
							.add(new ReconciliationReportRow(
									"DB: ",
									ReconciliationReportResults.LOGIN_NOT_FOUND,
									this.objectToString(ReconciliationReport
											.getHeader(attrMap), attrMap, u)));
					ReconciliationCommand command = situations
							.get("Login Not Found");
					if (command != null) {
						log.debug("Call command for IDM Login Not Found");
						command.execute(l, user, null);
					}
					new ReconciliationResponse(ResponseStatus.SUCCESS);
					report.getReport()
							.add(new ReconciliationReportRow(
									"No identities found for managedSysId in IDM repository",
									ReconciliationReport.getHeader(attrMap)
											.size() + 1));
					return resp;
				}

				String principal = l.getId().getLogin();
				log.debug("looking up identity in resource: " + principal);

				LookupUserResponse lookupResp = provisionService
						.getTargetSystemUser(principal, managedSysId);

				log.debug("Lookup status for " + principal + " ="
						+ lookupResp.getStatus());

				// User user =
				// userMgr.getUserByPrincipal(l.getId().getDomainId(),
				// l.getId().getLogin(), l.getId().getManagedSysId(), true);

				if (lookupResp.getStatus() == ResponseStatus.FAILURE
						&& !l.getStatus().equalsIgnoreCase("INACTIVE")) {
					// Situation: Resource Delete
					ReconciliationCommand command = situations
							.get("Resource Delete");
					report.getReport()
							.add(new ReconciliationReportRow(
									"DB: ",
									ReconciliationReportResults.RESOURCE_DELETED,
									this.objectToString(ReconciliationReport
											.getHeader(attrMap), attrMap, u)));
					if (command != null) {
						log.debug("Call command for Resource Delete");
						command.execute(l, user, null);
					}
				} else if (lookupResp.getStatus() == ResponseStatus.SUCCESS) {
					// found entry in managed sys
					if (l.getStatus().equalsIgnoreCase("INACTIVE")
							|| user.getStatus().equals(UserStatusEnum.DELETED)) {
						// Situation: IDM Delete
						ReconciliationCommand command = situations
								.get("IDM Delete");
						report.getReport()
								.add(new ReconciliationReportRow(
										"DB: ",
										ReconciliationReportResults.IDM_DELETED,
										this.objectToString(
												ReconciliationReport
														.getHeader(attrMap),
												attrMap, u)));
						if (command != null) {
							log.debug("Call command for IDM Delete");
							command.execute(l, user, null);
						}
					} else {
						// Situation: IDM Changed/Resource Changed/Match Found
						ReconciliationCommand command = situations
								.get("Match Found");
						report.getReport()
								.add(new ReconciliationReportRow(
										"",
										ReconciliationReportResults.MATCH_FOUND,
										this.objectToString(
												ReconciliationReport
														.getHeader(attrMap),
												matchFields(attrMap, u,
														new UserWrapperEntity()))));
						if (command != null) {
							log.debug("Call command for Match Found");
							command.execute(l, user, null);
						}
					}

				}
			}

			if (connector.getConnectorInterface() != null
					&& connector.getConnectorInterface().equalsIgnoreCase(
							"REMOTE")) {

				log.debug("Calling reconcileResource with Remote connector");
				RemoteReconciliationConfig remoteReconciliationConfig = null;
				if (config != null) {
					remoteReconciliationConfig = new RemoteReconciliationConfig(
							config);
					remoteReconciliationConfig.setScriptHandler(mSys
							.getReconcileResourceHandler());
				}
				remoteConnectorAdapter.reconcileResource(
						remoteReconciliationConfig, connector, muleContext);
			} else {

				log.debug("Calling reconcileResource local connector");
				connectorAdapter.reconcileResource(mSys, config, muleContext);
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			resp = new ReconciliationResponse(ResponseStatus.FAILURE);
			resp.setErrorText(e.getMessage());
		}

		try {
			report.save(pathToCSV, mSys);
			return resp;
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
			resp = new ReconciliationResponse(ResponseStatus.FAILURE);
			resp.setErrorText("CAN't call report.save()" + e.getMessage());
			return resp;
		}
	}

	private String objectToString(List<String> head, Map<String, String> obj) {
		return userCSVParser.objectToString(head, obj);
	}

	private ReconciliationObject<ProvisionUser> toReconUser(
			List<AttributeMap> attrMapList, UserWrapperEntity u) {
		return userCSVParser.toReconciliationObject(new ProvisionUser(u),
				attrMapList);
	}

	private String objectToString(List<String> head,
			List<AttributeMap> attrMapList, UserWrapperEntity u) {
		return userCSVParser.objectToString(head, attrMapList,
				toReconUser(attrMapList, u));
	}

	private Map<String, String> matchFields(List<AttributeMap> attrMap,
			UserWrapperEntity u, UserWrapperEntity o) {
		return userCSVParser.matchFields(attrMap, toReconUser(attrMap, u),
				toReconUser(attrMap, o));
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

	public void setRemoteConnectorAdapter(
			RemoteConnectorAdapter remoteConnectorAdapter) {
		this.remoteConnectorAdapter = remoteConnectorAdapter;
	}

	public void setRoleDataService(RoleDataService roleDataService) {
		this.roleDataService = roleDataService;
	}
}
