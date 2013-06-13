package org.openiam.webadmin.res;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.common.util.StringUtils;
import org.openiam.idm.srvc.batch.dto.BatchTask;
import org.openiam.idm.srvc.batch.service.BatchDataService;
import org.openiam.idm.srvc.file.FileWebService;
import org.openiam.idm.srvc.menu.dto.Menu;
import org.openiam.idm.srvc.menu.ws.NavigatorDataWebService;
import org.openiam.idm.srvc.mngsys.dto.ManagedSys;
import org.openiam.idm.srvc.mngsys.dto.ProvisionConnector;
import org.openiam.idm.srvc.mngsys.service.ConnectorDataService;
import org.openiam.idm.srvc.mngsys.service.ManagedSystemDataService;
import org.openiam.idm.srvc.recon.dto.ReconciliationConfig;
import org.openiam.idm.srvc.recon.dto.ReconciliationSituation;
import org.openiam.idm.srvc.recon.service.ReconciliationCommand;
import org.openiam.idm.srvc.recon.ws.AsynchReconciliationService;
import org.openiam.idm.srvc.recon.ws.ReconciliationWebService;
import org.openiam.idm.srvc.res.dto.Resource;
import org.openiam.idm.srvc.res.service.ResourceDataService;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.CancellableFormController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Controller for the new hire form.
 * 
 * @author suneet
 */
public class ReconConfigurationController extends CancellableFormController {

	protected String redirectView;
	protected NavigatorDataWebService navigationDataService;
	protected ReconciliationWebService reconcileService;
	protected FileWebService fileWebService;
	protected BatchDataService batchDataService;
	protected AsynchReconciliationService asynchReconService;
	private ConnectorDataService connectorService;
	private ResourceDataService resourceDataService;

	public void setManagedSysService(ManagedSystemDataService managedSysService) {
		this.managedSysService = managedSysService;
	}

	protected ManagedSystemDataService managedSysService;

	private static final Log log = LogFactory
			.getLog(ReconConfigurationController.class);

	public ReconConfigurationController() {
		super();
	}

	@Override
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {

		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("MM/dd/yyyy"), true));
	}

	@Override
	protected ModelAndView onCancel(Object command) throws Exception {
		return new ModelAndView(new RedirectView(getCancelView(), true));
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String menuGrp = request.getParameter("menugrp");
		String resId = request.getParameter("objId");

		if (resId != null && resId.length() > 0) {
			request.setAttribute("objId", resId);
		}

		ReconConfigurationCommand cmd = new ReconConfigurationCommand();
		try {
			ManagedSys mSys = managedSysService.getManagedSysByResource(resId);
			ProvisionConnector pCon = connectorService.getConnector(mSys
					.getConnectorId());

			boolean isCSV = pCon != null
					&& pCon.getServiceUrl().contains("CSVConnectorService");
			cmd.setIsCSV(isCSV);
			if (isCSV) {
				session.setAttribute("mSysId", mSys.getManagedSysId());
				session.setAttribute("resId", mSys.getResourceId());
				if (mSys.getResourceId() != null
						&& !StringUtils.isEmpty(fileWebService
								.getFile(ReconConfigurationController
										.getFileName(mSys, "recon_", "csv"))))
					cmd.setReconCSVName(ReconConfigurationController
							.getFileName(mSys, "recon_", "csv"));
				else {
					cmd.setReconCSVName("Resource not exists or CSV is not uploaded");
				}
			}
			String file = this.getFileContent(mSys, "report_", "csv");
			cmd.setIsReportExist(!StringUtils.isEmpty(file));
		} catch (Exception e) {
			log.error(e.getMessage());
			cmd.setIsCSV(false);
		}
		ReconciliationConfig config = reconcileService.getConfigByResource(
				resId).getConfig();
		if (config == null) {
			cmd.getConfig().setResourceId(resId);
            cmd.getSituationList().addAll(getDefaultSituations());
        } else {
            cmd.setConfig(config);
            if(CollectionUtils.isEmpty(config.getSituationSet())) {
               cmd.getSituationList().addAll(getDefaultSituations());
           } else {
               cmd.getSituationList().addAll(config.getSituationSet());
           }
        }


		List<Menu> level3MenuList = navigationDataService.menuGroupByUser(
				menuGrp, userId, "en").getMenuList();

		Resource res = resourceDataService.getResource(resId);
		request.setAttribute("menuL3", ResourceMenuHelper.resourceTypeMenu(res
				.getResourceType().getResourceTypeId(), level3MenuList));

		return cmd;
	}

    private List<ReconciliationSituation> getDefaultSituations() {
        List<ReconciliationSituation> situations = new LinkedList<ReconciliationSituation>();
        situations.add(
                new ReconciliationSituation(null, ReconciliationCommand.IDM_EXISTS__SYS_EXISTS));
        situations.add(
                new ReconciliationSituation(null, ReconciliationCommand.IDM_DELETED__SYS_EXISTS));
        situations.add(
                new ReconciliationSituation(null, ReconciliationCommand.IDM_EXISTS__SYS_NOT_EXISTS));
        situations.add(
                new ReconciliationSituation(null, ReconciliationCommand.SYS_EXISTS__IDM_NOT_EXISTS));
        return situations;
    }

    public void setConnectorService(ConnectorDataService connectorService) {
		this.connectorService = connectorService;
	}

	public static String getFileName(ManagedSys ms, String preffix, String type) {
		if (ms == null)
			return "";
		StringBuilder sb = new StringBuilder();
		sb.append(preffix);
		sb.append(ms.getResourceId());
		sb.append(".");
		sb.append(type);
		return sb.toString();
	}

	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		log.debug("onSubmit called");

		ReconConfigurationCommand configCommand = (ReconConfigurationCommand) command;

		ReconciliationConfig config = configCommand.getConfig();
		List<ReconciliationSituation> situationList = configCommand
				.getSituationList();
		ManagedSys mSys = managedSysService.getManagedSysByResource(config
				.getResourceId());
		String btn = request.getParameter("btn");
		String configId = config.getReconConfigId();
		ModelAndView mavDownload = null;
		if (btn != null && btn.equalsIgnoreCase("Export to CSV")) {
			mavDownload = download(response, mSys, "");
			if (mavDownload != null)
				return mavDownload;
		}

		if (btn != null && btn.equalsIgnoreCase("Export report")) {
			mavDownload = download(response, mSys, "report_");
			if (mavDownload != null)
				return mavDownload;
		}

		if (btn != null && btn.equalsIgnoreCase("Delete")) {
			reconcileService.removeConfig(configId);

			// remove the synch job that is linked to it.
			String name = "Reconcil:" + configId;
			// check if a batch object for this already exists
			BatchTask task = batchDataService.getTaskByName(name);
			if (task != null) {
				batchDataService.removeBatchTask(task.getTaskId());
			}

			ModelAndView mav = new ModelAndView("/deleteconfirm");
			mav.addObject("msg", "Configuration has been successfully deleted.");
			return mav;

		}

		if (config.getReconConfigId() == null
				|| config.getReconConfigId().length() == 0) {
			// new
			log.info("Creating new configuration..");

			config.setReconConfigId(null);
			// build the set of situation objects
			Set<ReconciliationSituation> situationSet = new HashSet<ReconciliationSituation>();
			for (ReconciliationSituation s : situationList) {
				s.setReconConfigId(null);
				s.setReconSituationId(null);
				situationSet.add(s);

			}
			config.setSituationSet(situationSet);
			configId = reconcileService.addConfig(config).getConfig()
					.getReconConfigId();

		} else {
			// existing record
			Set<ReconciliationSituation> situationSet = new HashSet<ReconciliationSituation>();
			for (ReconciliationSituation s : situationList) {
				situationSet.add(s);

			}
			config.setSituationSet(situationSet);
			reconcileService.updateConfig(config);

		}
		// update the batch configuration
		if (configId != null) {
			String name = "Reconcil:" + configId;
			// check if a batch object for this already exists
			BatchTask task = batchDataService.getTaskByName(name);
			if (task == null) {
				task = new BatchTask();
				task.setTaskName(name);
				task.setTaskId(null);
				task.setParam1(configId);
				task.setTaskUrl("batch/reconciliation.groovy");
			}
			if (config.getFrequency() == null
					|| config.getFrequency().length() == 0) {
				task.setFrequencyUnitOfMeasure(null);
				task.setEnabled(0);
			} else {
				task.setFrequencyUnitOfMeasure(config.getFrequency());
				task.setEnabled(1);
			}
			if (task.getTaskId() == null) {
				this.batchDataService.addBatchTask(task);
			} else {
				batchDataService.upateBatchTask(task);
			}
		}

		String view = redirectView
				+ "?mode=1&menuid=RECONCILCONFIG&menugrp=SECURITY_RES&objId="
				+ config.getResourceId();
		log.info("redirecting to=" + view);

		if (btn != null && btn.equalsIgnoreCase("Show report")) {
			response.getWriter().write(
					fileWebService.getFile(ReconConfigurationController
							.getFileName(mSys, "report_", "html")));
			response.getWriter().flush();
			return null;
		}

		if (btn != null && btn.equalsIgnoreCase("Reconcile Now")) {
			if (config != null) {
				asynchReconService.startReconciliation(config);
				return new ModelAndView(new RedirectView(view, true));
			}

		}
		return new ModelAndView(new RedirectView(view, true));

	}

	public static char getCharFromString(String str) {
		if (!StringUtils.isEmpty(str) || "comma".equals(str))
			return ',';
		if ("tab".equals(str))
			return '\t';
		if ("semicolon".equals(str))
			return ';';
		if ("space".equals(str))
			return ' ';
		if ("enter".equals(str))
			return '\n';
		return ',';
	}

	private ModelAndView download(HttpServletResponse response,
			ManagedSys mSys, String preffix) {
		String file = this.getFileContent(mSys, preffix, "csv");
		if (StringUtils.isEmpty(file)) {
			log.error("Nothing to Export");
		} else {
			response.setContentType("text/plain");
			response.setHeader("Content-Disposition",
					"attachment;filename=ExportData.csv");
			try {
				response.getWriter().write(file);
				response.getWriter().flush();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}

		}
		return null;
	}

	private String getFileContent(ManagedSys ms, String preffix, String type) {
		return fileWebService.getFile(ReconConfigurationController.getFileName(
				ms, preffix, type));

	}

	public String getRedirectView() {
		return redirectView;
	}

	public void setRedirectView(String redirectView) {
		this.redirectView = redirectView;
	}

	public ReconciliationWebService getReconcileService() {
		return reconcileService;
	}

	public void setReconcileService(ReconciliationWebService reconcileService) {
		this.reconcileService = reconcileService;
	}

	public NavigatorDataWebService getNavigationDataService() {
		return navigationDataService;
	}

	public void setNavigationDataService(
			NavigatorDataWebService navigationDataService) {
		this.navigationDataService = navigationDataService;
	}

	public BatchDataService getBatchDataService() {
		return batchDataService;
	}

	public void setBatchDataService(BatchDataService batchDataService) {
		this.batchDataService = batchDataService;
	}

	public void setAsynchReconService(
			AsynchReconciliationService asynchReconService) {
		this.asynchReconService = asynchReconService;
	}

	public ResourceDataService getResourceDataService() {
		return resourceDataService;
	}

	public void setResourceDataService(ResourceDataService resourceDataService) {
		this.resourceDataService = resourceDataService;
	}

	/**
	 * @param fileWebService
	 *            the fileWebService to set
	 */
	public void setFileWebService(FileWebService fileWebService) {
		this.fileWebService = fileWebService;
	}
}
