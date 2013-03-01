package org.openiam.spml2.spi.csv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.common.util.CollectionUtils;
import org.openiam.idm.srvc.auth.dto.Login;
import org.openiam.idm.srvc.mngsys.dto.AttributeMap;
import org.openiam.idm.srvc.mngsys.dto.ManagedSys;
import org.openiam.idm.srvc.mngsys.service.ManagedSystemDataService;
import org.openiam.idm.srvc.mngsys.service.ManagedSystemObjectMatchDAO;
import org.openiam.idm.srvc.recon.command.ReconciliationCommandFactory;
import org.openiam.idm.srvc.recon.dto.ReconciliationConfig;
import org.openiam.idm.srvc.recon.dto.ReconciliationSituation;
import org.openiam.idm.srvc.recon.service.ReconciliationCommand;
import org.openiam.idm.srvc.recon.ws.ReconciliationWebService;
import org.openiam.idm.srvc.res.dto.Resource;
import org.openiam.idm.srvc.res.dto.ResourceRole;
import org.openiam.idm.srvc.res.service.ResourceDataService;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.idm.srvc.user.dto.UserStatusEnum;
import org.openiam.idm.srvc.user.service.UserDataService;
import org.openiam.provision.dto.ProvisionUser;
import org.openiam.provision.type.ExtensibleAttribute;
import org.openiam.provision.type.ExtensibleObject;
import org.openiam.spml2.msg.ReconciliationHTMLReport;
import org.openiam.spml2.msg.ReconciliationHTMLReportResults;
import org.openiam.spml2.msg.ReconciliationHTMLRow;
import org.openiam.spml2.msg.ResponseType;
import org.openiam.spml2.msg.StatusCodeType;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import com.ctc.wstx.util.StringUtil;

public class AbstractCSVCommand implements ApplicationContextAware {
	protected static final Log log = LogFactory
			.getLog(AbstractCSVCommand.class);

	protected ManagedSystemDataService managedSysService;

	protected ResourceDataService resourceDataService;
	protected ManagedSystemObjectMatchDAO managedSysObjectMatchDao;
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
		ReconciliationHTMLReport result = new ReconciliationHTMLReport();
		List<ReconciliationHTMLRow> report = new ArrayList<ReconciliationHTMLRow>();
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
		UserCSVParser parserIDM = new UserCSVParser(pathToCSV);
		UserCSVParser parserSource = new UserCSVParser(pathToCSV);
		List<AttributeMap> attrMapList = managedSysService
				.getResourceAttributeMaps(mSys.getResourceId());
		List<CSVObject<ProvisionUser>> idmUsers;
		List<CSVObject<ProvisionUser>> sourceUsers;
		List<CSVObject<ProvisionUser>> dbUsers = new ArrayList<CSVObject<ProvisionUser>>();
		for (User u : config.getUserList()) {
			ProvisionUser pu_ = new ProvisionUser(u);
			pu_.setPrincipalList(u.getPrincipalList());
			dbUsers.add(parserIDM
					.toCsvObject(pu_, attrMapList));
		}

		try {
			idmUsers = parserIDM.getObjectListFromIDMCSV(mSys, attrMapList);
			sourceUsers = parserSource.getObjectListFromSourceCSV(mSys,
					attrMapList);
			// Fill header
			StringBuilder header = new StringBuilder();
			List<String> hList = new ArrayList<String>(0);
			for (AttributeMap map : attrMapList) {
				hList.add(map.getAttributeName());
				header.append(map.getAttributeName());
				header.append(",");
			}
			header.deleteCharAt(header.length() - 1);
			report.add(new ReconciliationHTMLRow(header.toString()));
			log.info("Generate headred " + header.toString()); // -----------------------------------------------------------
			// First run from IDM search in Sourse
			report.add(new ReconciliationHTMLRow("Records from IDM: "
					+ idmUsers.size() + " items", hList.size() + 1));
			try {
				reconCicle(hList, report, "IDM: ", parserIDM, idmUsers,
						dbUsers, attrMapList, mSys);
			} catch (Exception e) {
				log.error(e.getMessage());
				response.setStatus(StatusCodeType.FAILURE);
				response.setErrorMessage(e.getMessage());
				return response;
			}
			report.add(new ReconciliationHTMLRow("Records from Remote CSV: "
					+ sourceUsers.size() + " items", hList.size() + 1));
			try {
				reconCicle(hList, report, "Source: ", parserIDM, sourceUsers,
						dbUsers, attrMapList, mSys);
			} catch (Exception e) {
				log.error(e.getMessage());
				response.setStatus(StatusCodeType.FAILURE);
				response.setErrorMessage(e.getMessage());
				return response;
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

	private Map<String, String> matchFields(Map<String, String> one,
			Map<String, String> two) {
		Map<String, String> res = new HashMap<String, String>(0);
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

				res.put(field,
						one.get(field).equals(two.get(field)) ? one.get(field)
								: ("[" + one.get(field) + "][" + two.get(field))
										+ "]");
				continue;
			}
		}

		return res;
	}

	/**
	 * 
	 * @param hList
	 *            - List of Caption header value
	 * @param report
	 *            - List to add report strings
	 * @param preffix
	 *            - IDM or Sourcer csv pref
	 * @param parser
	 *            - UserParser for reconsile
	 * @param reconUserList
	 *            - list to reconsile with db Users list
	 * @param dbUsers
	 *            - list of IDM users
	 * @param attrMapList
	 *            - List<AttributeMap> attrMapList
	 * @param mSys
	 *            - ManagedSys mSys
	 * @throws Exception
	 */
	private void reconCicle(List<String> hList,
			List<ReconciliationHTMLRow> report, String preffix,
			UserCSVParser parser, List<CSVObject<ProvisionUser>> reconUserList,
			List<CSVObject<ProvisionUser>> dbUsers,
			List<AttributeMap> attrMapList, ManagedSys mSys) throws Exception {
		for (CSVObject<ProvisionUser> u : reconUserList) {
			log.info("User " + u.toString());
			if (u.getObject() == null || u.getPrincipal() == null) {
				log.warn("Skip USER" + u.toString() + " key or objecy is NULL");
				if (u.getObject() != null) {
					report.add(new ReconciliationHTMLRow(preffix,
							ReconciliationHTMLReportResults.BROKEN_CSV,
							this.objectToString(hList,
									parser.convertToMap(attrMapList, u))));
				}
				continue;
			}

			if (!isUnique(u, reconUserList)) {
				report.add(new ReconciliationHTMLRow(preffix,
						ReconciliationHTMLReportResults.NOT_UNIQUE_KEY, this
								.objectToString(hList,
										parser.convertToMap(attrMapList, u))));
				continue;
			}
			boolean isFind = false;
			boolean isMultiple = false;
			CSVObject<ProvisionUser> finded = null;
			for (CSVObject<ProvisionUser> o : dbUsers) {

				if (!StringUtils.hasText(o.getPrincipal())) {
					continue;
				}
				if (o.getPrincipal().equals(u.getPrincipal())) {
					if (!isFind) {
						isFind = true;
						finded = o;
						continue;
					} else {
						isMultiple = true;
						report.add(new ReconciliationHTMLRow(preffix,
								ReconciliationHTMLReportResults.NOT_UNIQUE_KEY,
								this.objectToString(hList,
										parser.convertToMap(attrMapList, u))));
						break;
					}
				}
			}

			if (!isFind) {
				report.add(new ReconciliationHTMLRow(preffix,
						ReconciliationHTMLReportResults.NOT_EXIST_IN_IDM_DB,
						this.objectToString(hList,
								parser.convertToMap(attrMapList, u))));
			} else if (!isMultiple && finded != null) {
				Login l = null;
				List<Login> logins = finded.getObject().getPrincipalList();
				if (logins != null) {
					for (Login login : logins) {
						if (login.getId().getDomainId()
								.equalsIgnoreCase(mSys.getDomainId())
								&& login.getId()
										.getManagedSysId()
										.equalsIgnoreCase(
												mSys.getManagedSysId())) {
							l = login;
							break;
						}
					}
				}
				if (l == null) {
					if (UserStatusEnum.DELETED.equals(finded.getObject()
							.getStatus())) {
						report.add(new ReconciliationHTMLRow(preffix,
								ReconciliationHTMLReportResults.IDM_DELETED,
								this.objectToString(hList,
										parser.convertToMap(attrMapList, u))));
						continue;
					}
					report.add(new ReconciliationHTMLRow(preffix,
							ReconciliationHTMLReportResults.LOGIN_NOT_FOUND,
							this.objectToString(hList,
									parser.convertToMap(attrMapList, u))));
					continue;
				} else {
					report.add(new ReconciliationHTMLRow(preffix,
							ReconciliationHTMLReportResults.MATCH_FOUND, this
									.objectToString(
											hList,
											matchFields(parser.convertToMap(
													attrMapList, u), parser
													.convertToMap(attrMapList,
															finded)))));
					continue;
				}
			}
		}
	}

	private boolean isUnique(CSVObject<ProvisionUser> obj,
			List<CSVObject<ProvisionUser>> list) {
		if (obj == null || list == null)
			return false;

		if (!StringUtils.hasText(obj.getPrincipal()))
			return false;

		boolean isFinded = false;
		for (CSVObject<ProvisionUser> l : list) {
			if (l.equals(obj)) {
				if (isFinded)
					return false;
				isFinded = true;
				continue;
			}
			if (obj.getPrincipal().equals(l.getPrincipal())) {
				if (isFinded)
					return false;
				isFinded = true;
			}
		}
		return true;
	}

	protected List<CSVObject<ProvisionUser>> getUsersFromCSV(
			ManagedSys managedSys) throws Exception {
		UserCSVParser userParser = new UserCSVParser(pathToCSV);
		List<AttributeMap> attrMapList = managedSysService
				.getResourceAttributeMaps(managedSys.getResourceId());
		return userParser.getObjectListFromIDMCSV(managedSys, attrMapList);
	}

	protected Map<String, String> getUserProvisionMap(
			CSVObject<ProvisionUser> obj, ManagedSys managedSys)
			throws Exception {
		UserCSVParser userParser = new UserCSVParser(pathToCSV);
		List<AttributeMap> attrMapList = managedSysService
				.getResourceAttributeMaps(managedSys.getResourceId());
		return userParser.convertToMap(attrMapList, obj);
	}

	protected void addUsersToCSV(String principal, ProvisionUser newUser,
			ManagedSys managedSys) throws Exception {
		UserCSVParser userParser = new UserCSVParser(pathToCSV);
		List<AttributeMap> attrMapList = managedSysService
				.getResourceAttributeMaps(managedSys.getResourceId());
		userParser.addObjectToIDMCSV(new CSVObject<ProvisionUser>(principal,
				newUser), managedSys, attrMapList);
	}

	protected void deleteUser(String principal, ProvisionUser newUser,
			ManagedSys managedSys) throws Exception {
		UserCSVParser userParser = new UserCSVParser(pathToCSV);
		List<AttributeMap> attrMapList = managedSysService
				.getResourceAttributeMaps(managedSys.getResourceId());
		userParser.deleteObjectFromIDMCSV(principal, managedSys, attrMapList);
	}

	protected void updateUser(CSVObject<ProvisionUser> newUser,
			ManagedSys managedSys) throws Exception {
		UserCSVParser userParser = new UserCSVParser(pathToCSV);
		List<AttributeMap> attrMapList = managedSysService
				.getResourceAttributeMaps(managedSys.getResourceId());
		userParser.updateObjectFromIDMCSV(newUser, managedSys, attrMapList);
	}

	protected boolean lookupObjectInCSV(String findValue,
			ManagedSys managedSys, List<ExtensibleObject> extOnjectList)
			throws Exception {
		List<CSVObject<ProvisionUser>> users = this.getUsersFromCSV(managedSys);
		List<ExtensibleAttribute> eAttr = new ArrayList<ExtensibleAttribute>(0);

		for (CSVObject<ProvisionUser> user : users) {
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

	protected boolean match(String findValue, CSVObject<ProvisionUser> user2,
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
