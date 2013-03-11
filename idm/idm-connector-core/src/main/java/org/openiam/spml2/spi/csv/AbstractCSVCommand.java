package org.openiam.spml2.spi.csv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openiam.idm.srvc.auth.login.LoginDataService;
import org.openiam.idm.srvc.csv.CSVParser;
import org.openiam.idm.srvc.csv.ReconciliationObject;
import org.openiam.idm.srvc.csv.constant.CSVSource;
import org.openiam.idm.srvc.mngsys.dto.AttributeMap;
import org.openiam.idm.srvc.mngsys.dto.ManagedSys;
import org.openiam.idm.srvc.mngsys.service.ManagedSystemDataService;
import org.openiam.idm.srvc.mngsys.service.ManagedSystemObjectMatchDAO;
import org.openiam.idm.srvc.recon.command.ReconciliationCommandFactory;
import org.openiam.idm.srvc.recon.dto.ReconciliationConfig;
import org.openiam.idm.srvc.recon.dto.ReconciliationSituation;
import org.openiam.idm.srvc.recon.report.ReconciliationReport;
import org.openiam.idm.srvc.recon.report.ReconciliationReportResults;
import org.openiam.idm.srvc.recon.report.ReconciliationReportRow;
import org.openiam.idm.srvc.recon.service.ReconciliationCommand;
import org.openiam.idm.srvc.res.dto.Resource;
import org.openiam.idm.srvc.res.service.ResourceDataService;
import org.openiam.idm.srvc.user.domain.UserWrapperEntity;
import org.openiam.idm.srvc.user.dto.UserStatusEnum;
import org.openiam.provision.dto.ProvisionUser;
import org.openiam.provision.type.ExtensibleAttribute;
import org.openiam.provision.type.ExtensibleObject;
import org.openiam.spml2.msg.ResponseType;
import org.openiam.spml2.msg.StatusCodeType;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

public class AbstractCSVCommand implements ApplicationContextAware {
	protected static final Log log = LogFactory
			.getLog(AbstractCSVCommand.class);

	protected ManagedSystemDataService managedSysService;
	protected ResourceDataService resourceDataService;
	protected ManagedSystemObjectMatchDAO managedSysObjectMatchDao;
	protected LoginDataService loginManager;
	protected CSVParser<ProvisionUser> userCSVParser;

	/**
	 * @param userCSVParser
	 *            the userCSVParser to set
	 */
	public void setUserCSVParser(CSVParser<ProvisionUser> userCSVParser) {
		this.userCSVParser = userCSVParser;
	}

	/**
	 * @param loginManager
	 *            the loginManager to set
	 */
	public void setLoginManager(LoginDataService loginManager) {
		this.loginManager = loginManager;
	}

	protected String pathToCSV;
	public static ApplicationContext ac;

	public void setPathToCSV(String pathToCSV) {
		this.pathToCSV = pathToCSV;
	}

	public void setManagedSysService(ManagedSystemDataService managedSysService) {
		this.managedSysService = managedSysService;
	}

	public void setResourceDataService(ResourceDataService resourceDataService) {
		this.resourceDataService = resourceDataService;
	}

	public void setManagedSysObjectMatchDao(
			ManagedSystemObjectMatchDAO managedSysObjectMatchDao) {
		this.managedSysObjectMatchDao = managedSysObjectMatchDao;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.ac = ac;
	}

	protected char getSeparator(ReconciliationConfig config) {
		char separator;

		if (StringUtils.hasText(config.getSeparator()))
			separator = config.getSeparator().toCharArray()[0];
		else
			separator = ',';
		return separator;
	}

	protected char getEndOfLine(ReconciliationConfig config) {

		char EOL;
		if (StringUtils.hasText(config.getEndOfLine()))
			EOL = config.getEndOfLine().toCharArray()[0];
		else
			EOL = '\n';
		return EOL;
	}

	protected ResponseType reconcile(ReconciliationConfig config) {
		ResponseType response = new ResponseType();
		ReconciliationReport result = new ReconciliationReport();
		List<ReconciliationReportRow> report = new ArrayList<ReconciliationReportRow>();
		result.setReport(report);
		Resource res = resourceDataService.getResource(config.getResourceId());
		String managedSysId = res.getManagedSysId();
		ManagedSys mSys = managedSysService.getManagedSys(managedSysId);

		Map<String, ReconciliationCommand> situations = new HashMap<String, ReconciliationCommand>();
		for (ReconciliationSituation situation : config.getSituationSet()) {
			situations.put(situation.getSituation().trim(),
					ReconciliationCommandFactory.createCommand(
							situation.getSituationResp(), situation,
							managedSysId));
			log.debug("Created Command for: " + situation.getSituation());
		}
		if (config.getUserList() == null) {
			log.error("user list from DB is empty");
			response.setStatus(StatusCodeType.FAILURE);
			response.setErrorMessage("user list from DB is empty");
			return response;
		}
		List<AttributeMap> attrMapList = managedSysService
				.getResourceAttributeMaps(mSys.getResourceId());
		if (CollectionUtils.isEmpty(attrMapList)) {
			log.error("user list from DB is empty");
			response.setStatus(StatusCodeType.FAILURE);
			response.setErrorMessage("attrMapList is empty");
			return response;
		}
		List<ReconciliationObject<ProvisionUser>> idmUsers;
		List<ReconciliationObject<ProvisionUser>> sourceUsers;
		List<ReconciliationObject<ProvisionUser>> dbUsers = new ArrayList<ReconciliationObject<ProvisionUser>>();
		for (UserWrapperEntity u : config.getUserList()) {
			ProvisionUser pu_ = new ProvisionUser(u);
			dbUsers.add(userCSVParser.toReconciliationObject(pu_, attrMapList));
		}

		try {
			idmUsers = userCSVParser.getObjects(mSys, attrMapList,
					CSVSource.IDM);
			sourceUsers = userCSVParser.getObjects(mSys, attrMapList,
					CSVSource.UPLOADED);
			// Fill header
			StringBuilder header = new StringBuilder();
			List<String> hList = new ArrayList<String>(0);
			for (AttributeMap map : attrMapList) {
				hList.add(map.getAttributeName());
				header.append(map.getAttributeName());
				header.append(",");
			}
			header.deleteCharAt(header.length() - 1);
			report.add(new ReconciliationReportRow(header.toString()));
			log.info("Generate headred " + header.toString()); // -----------------------------------------------------------
			// First run from IDM search in Sourse
			report.add(new ReconciliationReportRow("Records from IDM: "
					+ idmUsers.size() + " items", hList.size() + 1));
			try {
				reconCicle(hList, report, "IDM: ", idmUsers, dbUsers,
						attrMapList, mSys);
			} catch (Exception e) {
				log.error(e.getMessage());
				response.setStatus(StatusCodeType.FAILURE);
				response.setErrorMessage(e.getMessage());
				return response;
			}
			report.add(new ReconciliationReportRow("Records from Remote CSV: "
					+ sourceUsers.size() + " items", hList.size() + 1));
			try {
				dbUsers.removeAll(reconCicle(hList, report, "Source: ",
						sourceUsers, dbUsers, attrMapList, mSys));
			} catch (Exception e) {
				log.error(e.getMessage());
				response.setStatus(StatusCodeType.FAILURE);
				response.setErrorMessage(e.getMessage());
				return response;
			}
			report.add(new ReconciliationReportRow("Records from DB: "
					+ dbUsers.size() + " items", hList.size() + 1));
			for (ReconciliationObject<ProvisionUser> obj : dbUsers) {
				report.add(new ReconciliationReportRow("DB: ",
						ReconciliationReportResults.NOT_EXIST_IN_CSV, this
								.objectToString(hList, attrMapList, obj)));
			}

			// -----------------------------------------------
		} catch (Exception e) {
			log.error(e);
			response.setStatus(StatusCodeType.FAILURE);
			response.setErrorMessage(e.getMessage() + e.getStackTrace());
			return response;
		}
		try {
			result.save(pathToCSV, mSys);
		} catch (IOException e) {
			log.error("can't save report");
			response.setStatus(StatusCodeType.FAILURE);
			response.setErrorMessage(e.getMessage() + e.getStackTrace());
		}
		return response;
	}

	private String objectToString(List<String> head, Map<String, String> obj) {
		StringBuilder stb = new StringBuilder();
		for (String h : head) {
			stb.append(obj.get(h.trim()) == null ? "" : obj.get(h));
			stb.append(",");
		}
		stb.deleteCharAt(stb.length() - 1);
		return stb.toString();
	}

	private String objectToString(List<String> head,
			List<AttributeMap> attrMapList,
			ReconciliationObject<ProvisionUser> u) {
		return this.objectToString(head,
				userCSVParser.convertToMap(attrMapList, u));
	}

	private Map<String, String> matchFields(List<AttributeMap> attrMap,
			ReconciliationObject<ProvisionUser> u,
			ReconciliationObject<ProvisionUser> o) {
		Map<String, String> res = new HashMap<String, String>(0);
		Map<String, String> one = userCSVParser.convertToMap(attrMap, u);
		Map<String, String> two = userCSVParser.convertToMap(attrMap, o);
		for (String field : one.keySet()) {

			if (one.get(field) == null && two.get(field) == null) {
				res.put(field, null);
				continue;
			}
			if (one.get(field) == null && two.get(field) != null) {
				res.put(field, two.get(field));
				continue;
			}
			if (one.get(field) != null && two.get(field) == null) {
				res.put(field, one.get(field));
				continue;
			}
			if (one.get(field) != null && two.get(field) != null) {
				String firstVal = one.get(field).replaceFirst("^0*", "");
				String secondVal = two.get(field).replaceFirst("^0*", "");
				res.put(field, firstVal.equalsIgnoreCase(secondVal) ? secondVal
						: ("[" + firstVal + "][" + secondVal + "]"));
				continue;
			}
		}

		return res;
	}

	/**
	 * 
	 * @param hList
	 * @param report
	 * @param preffix
	 * @param reconUserList
	 * @param dbUsers
	 * @param attrMapList
	 * @param mSys
	 * @return
	 * @throws Exception
	 */
	private Set<ReconciliationObject<ProvisionUser>> reconCicle(
			List<String> hList, List<ReconciliationReportRow> report,
			String preffix,
			List<ReconciliationObject<ProvisionUser>> reconUserList,
			List<ReconciliationObject<ProvisionUser>> dbUsers,
			List<AttributeMap> attrMapList, ManagedSys mSys) throws Exception {
		long c = Calendar.getInstance().getTimeInMillis();
		Set<ReconciliationObject<ProvisionUser>> used = new HashSet<ReconciliationObject<ProvisionUser>>(
				0);
		for (ReconciliationObject<ProvisionUser> u : reconUserList) {
			log.info("User " + u.toString());
			if (u.getObject() == null || u.getPrincipal() == null) {
				log.warn("Skip USER" + u.toString() + " key or objecy is NULL");
				if (u.getObject() != null) {
					report.add(new ReconciliationReportRow(preffix,
							ReconciliationReportResults.BROKEN_CSV, this
									.objectToString(hList, attrMapList, u)));
				}
				continue;
			}

			// if (!isUnique(u, reconUserList)) {
			// report.add(new ReconciliationHTMLRow(preffix,
			// ReconciliationHTMLReportResults.NOT_UNIQUE_KEY, this
			// .objectToString(hList,
			// csvParser.convertToMap(attrMapList, u))));
			// continue;
			// }
			boolean isFind = false;
			boolean isMultiple = false;
			ReconciliationObject<ProvisionUser> finded = null;
			for (ReconciliationObject<ProvisionUser> o : dbUsers) {
				if (used.contains(o))
					continue;
				if (!StringUtils.hasText(o.getPrincipal())) {
					used.add(o);
					continue;
				}
				if (o.getPrincipal().contains("*")
						|| o.getPrincipal().contains("#")) {
					used.add(o);
					continue;
				}
				if (o.getPrincipal().replaceFirst("^0*", "")
						.equals(u.getPrincipal().replaceFirst("^0*", ""))) {
					if (!isFind) {
						isFind = true;
						finded = o;
						used.add(finded);
						continue;
					} else {
						isMultiple = true;
						report.add(new ReconciliationReportRow(preffix,
								ReconciliationReportResults.NOT_UNIQUE_KEY,
								this.objectToString(hList, attrMapList, u)));
						break;
					}
				}
			}

			if (!isFind) {
				report.add(new ReconciliationReportRow(preffix,
						ReconciliationReportResults.NOT_EXIST_IN_IDM_DB, this
								.objectToString(hList, attrMapList, u)));
			} else if (!isMultiple && finded != null) {
				if (UserStatusEnum.DELETED.equals(finded.getObject()
						.getStatus())) {
					report.add(new ReconciliationReportRow(preffix,
							ReconciliationReportResults.IDM_DELETED, this
									.objectToString(hList, attrMapList, u)));
				} else {
					report.add(new ReconciliationReportRow(preffix,
							ReconciliationReportResults.MATCH_FOUND,
							this.objectToString(hList,
									matchFields(attrMapList, u, finded))));
				}
				// TODO fix login cheking
				// Login l = null;
				// List<Login> logins = loginManager.getLoginByUser(finded
				// .getObject().getUserId());
				// if (logins != null) {
				// for (Login login : logins) {
				// if (login.getId().getDomainId()
				// .equalsIgnoreCase(mSys.getDomainId())
				// ) {
				// l = login;
				// break;
				// }
				// }
				// }
				// if (l == null) {
				// if (UserStatusEnum.DELETED.equals(finded.getObject()
				// .getStatus())) {
				// report.add(new ReconciliationHTMLRow(preffix,
				// ReconciliationHTMLReportResults.IDM_DELETED,
				// this.objectToString(hList,
				// csvParser.convertToMap(attrMapList, u))));
				// continue;
				// }
				// report.add(new ReconciliationHTMLRow(preffix,
				// ReconciliationHTMLReportResults.LOGIN_NOT_FOUND,
				// this.objectToString(hList,
				// csvParser.convertToMap(attrMapList, u))));
				// continue;
				// } else {
				// report.add(new ReconciliationHTMLRow(preffix,
				// ReconciliationHTMLReportResults.MATCH_FOUND, this
				// .objectToString(
				// hList,
				// matchFields(csvParser.convertToMap(
				// attrMapList, u), csvParser
				// .convertToMap(attrMapList,
				// finded)))));
				// continue;
				// }
			}
		}
		c = Calendar.getInstance().getTimeInMillis() - c;
		log.info("RECONCILIATION TIME:" + c
				+ "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		return used;
	}

	protected List<ReconciliationObject<ProvisionUser>> getUsersFromCSV(
			ManagedSys managedSys) throws Exception {
		List<AttributeMap> attrMapList = managedSysService
				.getResourceAttributeMaps(managedSys.getResourceId());
		return userCSVParser.getObjects(managedSys, attrMapList, CSVSource.IDM);
	}

	protected Map<String, String> getUserProvisionMap(
			ReconciliationObject<ProvisionUser> obj, ManagedSys managedSys)
			throws Exception {
		List<AttributeMap> attrMapList = managedSysService
				.getResourceAttributeMaps(managedSys.getResourceId());
		return userCSVParser.convertToMap(attrMapList, obj);
	}

	protected void addUsersToCSV(String principal, ProvisionUser newUser,
			ManagedSys managedSys) throws Exception {
		List<AttributeMap> attrMapList = managedSysService
				.getResourceAttributeMaps(managedSys.getResourceId());
		userCSVParser.add(new ReconciliationObject<ProvisionUser>(principal,
				newUser), managedSys, attrMapList, CSVSource.IDM);
	}

	protected void deleteUser(String principal, ProvisionUser newUser,
			ManagedSys managedSys) throws Exception {
		List<AttributeMap> attrMapList = managedSysService
				.getResourceAttributeMaps(managedSys.getResourceId());
		userCSVParser.delete(principal, managedSys, attrMapList, CSVSource.IDM);
	}

	protected void updateUser(ReconciliationObject<ProvisionUser> newUser,
			ManagedSys managedSys) throws Exception {
		List<AttributeMap> attrMapList = managedSysService
				.getResourceAttributeMaps(managedSys.getResourceId());
		userCSVParser.update(newUser, managedSys, attrMapList, CSVSource.IDM);
	}

	protected boolean lookupObjectInCSV(String findValue,
			ManagedSys managedSys, List<ExtensibleObject> extOnjectList)
			throws Exception {
		List<ReconciliationObject<ProvisionUser>> users = this
				.getUsersFromCSV(managedSys);
		List<ExtensibleAttribute> eAttr = new ArrayList<ExtensibleAttribute>(0);

		for (ReconciliationObject<ProvisionUser> user : users) {
			ExtensibleObject extOnject = new ExtensibleObject();
			if (match(findValue, user, extOnject)) {
				Map<String, String> res = this.getUserProvisionMap(user,
						managedSys);
				for (String key : res.keySet())
					if (res.get(key) != null)
						eAttr.add(new ExtensibleAttribute(key, user
								.getPrincipal()));
				extOnject.setAttributes(eAttr);
				extOnjectList.add(extOnject);
				return true;
			}
		}
		return false;
	}

	protected boolean match(String findValue,
			ReconciliationObject<ProvisionUser> user2,
			ExtensibleObject extOnject) {
		if (!StringUtils.hasText(findValue) || user2 == null) {
			return false;
		}
		if (findValue.equals(user2.getPrincipal())) {
			extOnject.setObjectId(user2.getPrincipal());
			return true;
		}
		return false;
	}
}
