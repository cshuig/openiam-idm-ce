package org.openiam.ui.webconsole.web.mvc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.openiam.authmanager.common.model.AuthorizationMenu;
import org.openiam.authmanager.util.AuthorizationConstants;
import org.openiam.base.ws.*;
import org.openiam.idm.searchbeans.BatchTaskSearchBean;
import org.openiam.idm.searchbeans.ManualReconciliationSearchBean;
import org.openiam.idm.srvc.batch.dto.BatchTask;
import org.openiam.idm.srvc.batch.service.BatchDataService;
import org.openiam.idm.srvc.file.ws.FileWebService;
import org.openiam.idm.srvc.mngsys.dto.ManagedSysDto;
import org.openiam.idm.srvc.mngsys.dto.ProvisionConnectorDto;
import org.openiam.idm.srvc.mngsys.ws.ManagedSystemWebService;
import org.openiam.idm.srvc.mngsys.ws.ProvisionConnectorWebService;
import org.openiam.idm.srvc.recon.dto.ReconExecStatusOptions;
import org.openiam.idm.srvc.recon.dto.ReconciliationConfig;
import org.openiam.idm.srvc.recon.dto.ReconciliationSituation;
import org.openiam.idm.srvc.recon.result.dto.ReconciliationResultBean;
import org.openiam.idm.srvc.recon.result.dto.ReconciliationResultCase;
import org.openiam.idm.srvc.recon.service.ReconciliationCommand;
import org.openiam.idm.srvc.recon.ws.AsynchReconciliationService;
import org.openiam.idm.srvc.recon.ws.ReconciliationWebService;
import org.openiam.idm.srvc.report.dto.ReportQueryDto;
import org.openiam.idm.srvc.report.ws.ReportWebService;
import org.openiam.idm.srvc.res.service.ResourceDataService;
import org.openiam.ui.constants.ReconConfigTypeOptions;
import org.openiam.ui.rest.api.model.KeyNameBean;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.mvc.entitlements.AbstractEntitlementsController;
import org.openiam.ui.web.propertyEditor.DateEditor;
import org.openiam.ui.web.util.DateFormatStr;
import org.openiam.ui.webconsole.web.model.ReconciliationConfigCommand;
import org.openiam.ui.webconsole.web.model.ReconciliationConfigListCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author zaporozhec
 * 
 */
@Controller
public class ReconcilationController extends AbstractWebconsoleController {

    private static final Log log = LogFactory.getLog(ReconcilationController.class);

    @Resource(name = "resourceServiceClient")
    private ResourceDataService resourceDataService;
    @Resource(name = "connectorServiceClient")
    private ProvisionConnectorWebService provisionConnectorWebService;

    @Resource(name = "reconciliationServiceClient")
    private ReconciliationWebService reconciliationWebService;

    @Resource(name = "fileServiceClient")
    private FileWebService fileWebService;

    @Resource(name = "batchServiceClient")
    private BatchDataService batchDataService;

    @Resource(name = "asyncReconciliationServiceClient")
    private AsynchReconciliationService asyncReconciliationServiceClient;

    @Resource(name = "managedSysServiceClient")
    private ManagedSystemWebService managedSysServiceClient;

    @Resource(name = "reportServiceClient")
    private ReportWebService reportServiceClient;

    @Value("${org.openiam.ui.idm.mngedit.root.name}")
    private String managedSystemEditPageRootMenuName;

    @Autowired
    @Qualifier("jacksonMapper")
    private ObjectMapper jacksonMapper;

    private String handlMenuResource(final HttpServletRequest request, final HttpServletResponse response,
            final String resourceId) {
        final AuthorizationMenu root = authManagerMenuService.getMenuTree(resourceId, getCurrentLanguage());

        String menuTreeString = null;
        try {
            menuTreeString = (root != null) ? jacksonMapper.writeValueAsString(root) : null;
        } catch (Throwable e) {
            log.error("Can't serialize menu tree", e);
        }
        request.setAttribute("editableMenuTree", root);
        request.setAttribute("editableMenuTreeAsString", menuTreeString);
        return "resources/menuTreeView";
    }

    @RequestMapping(value = "/reconciliations.html", method = RequestMethod.GET)
    public String getReconciliations(final @RequestParam(value = "id", required = true) String mngSysId,
                           final HttpServletRequest request, final HttpServletResponse response, final Model model) throws IOException {
        setMenuTree(request, managedSystemEditPageRootMenuName);
        ReconciliationConfigListCommand listCommand = new ReconciliationConfigListCommand();

        final ManagedSysDto mSys = managedSysServiceClient.getManagedSys(mngSysId);
        if (mSys == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        String resourceId = mSys.getResourceId();
        org.openiam.idm.srvc.res.dto.Resource res = resourceDataService.getResource(resourceId,
                getCurrentLanguage());
        // if such resource was deleted go to create new resource
        if (res == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Managed System not associated with a resource");
            return null;
        }
        if (StringUtils.equalsIgnoreCase(res.getResourceType().getId(),
                AuthorizationConstants.MENU_ITEM_RESOURCE_TYPE)) {
            return handlMenuResource(request, response, resourceId);
        } else {
            // get recon config
            List<ReconciliationConfig> reconciliationConfigList = (List<ReconciliationConfig>)reconciliationWebService.getConfigsByResourceId(resourceId)
                    .getConfigList();
            listCommand.setConfigList(reconciliationConfigList);
        }
        listCommand.setmSysId(mSys.getId());
        listCommand.setmSysName(mSys.getName());
        listCommand.setResourceId(resourceId);

        List<KeyNameBean>  reconType = new LinkedList<KeyNameBean>();
        reconType.add(new KeyNameBean("USER", "USER"));
        reconType.add(new KeyNameBean("GROUP", "GROUP"));
        reconType.add(new KeyNameBean("ROLE", "ROLE"));
        reconType.add(new KeyNameBean("ORG", "ORG"));
        model.addAttribute("mngSystem",mSys);
        model.addAttribute("reconciliationTypes", jacksonMapper.writeValueAsString(reconType));
        model.addAttribute("command", listCommand);
        return "/resources/reconciliations";
    }

    @RequestMapping(value = "/removeReconConfig", method = RequestMethod.POST)
    public String removeReconConfig(final HttpServletRequest request, final HttpServletResponse response,
                                  final @RequestParam(required = false, value = "id") String reconId,
                                  final @RequestParam(required = false, value = "managedSysId") String mngSysId) throws Exception {
        final BasicAjaxResponse ajaxResponse = doRemoveReconConfig(request, reconId, mngSysId);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    protected BasicAjaxResponse doRemoveReconConfig(HttpServletRequest request, String reconId, String mngSysId) {
        final String callerId = getRequesterId(request);
        Response wsResponse = reconciliationWebService.removeConfig(reconId, callerId);

        return getResponseAfterEntity2EntityAddition(wsResponse, true);
    }

    protected BasicAjaxResponse getResponseAfterEntity2EntityAddition(final Response wsResponse, final boolean isDelete) {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        if (org.openiam.base.ws.ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
            ajaxResponse.setStatus(200);
            ajaxResponse.setSuccessToken(new SuccessToken((isDelete) ? SuccessMessage.ENTITY_DELETED : SuccessMessage.ENTITY_ADDED));
        } else {
            if (wsResponse.getErrorCode() != null) {
                switch (wsResponse.getErrorCode()) {
                    case CANT_ADD_YOURSELF_AS_CHILD:
                        ajaxResponse.addError(new ErrorToken(Errors.CANT_MAKE_ENTITY_A_CHILD_OF_ITSELF));
                        break;
                    case UNAUTHORIZED:
                        ajaxResponse.addError(new ErrorToken(Errors.UNAUTHORIZED));
                        break;
                    case CIRCULAR_DEPENDENCY:
                        ajaxResponse.addError(new ErrorToken(Errors.CIRCULAR_DEPENDENCY));
                        break;
                    case RELATIONSHIP_EXISTS:
                        ajaxResponse.addError(new ErrorToken(Errors.RELATIONSHIP_EXISTS));
                        break;
                    case OBJECT_NOT_FOUND:
                        ajaxResponse.addError(new ErrorToken(Errors.OBJECT_DOES_NOT_EXIST));
                        break;
                    case INVALID_ARGUMENTS:
                        ajaxResponse.addError(new ErrorToken(Errors.INTERNAL_ERROR));
                        break;
                    default:
                        ajaxResponse.addError(new ErrorToken(Errors.INTERNAL_ERROR));
                        break;
                }
            } else {
                ajaxResponse.addError(new ErrorToken(Errors.INTERNAL_ERROR));
            }
        }
        return ajaxResponse;
    }

    @RequestMapping(value = "/reconciliationEdit", method = RequestMethod.GET)
    public String reconConfigEdit(final HttpServletRequest request, final HttpServletResponse response,
            final @RequestParam(required = false, value = "id") String reconId, final @RequestParam(required = true, value = "managedSysId") String mngSysId) throws Exception {
        if (StringUtils.isEmpty(mngSysId)) {
            setMenuTree(request, managedSystemEditPageRootMenuName);
        } else {
            // Get msys
            ManagedSysDto mSysDto = managedSysServiceClient.getManagedSys(mngSysId);
            // get resource
            if (mSysDto == null || mSysDto.getResourceId() == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return null;
            }
            String resourceId = mSysDto.getResourceId();
            org.openiam.idm.srvc.res.dto.Resource res = resourceDataService.getResource(resourceId,
                    getCurrentLanguage());
            // if such resource was deleted go to create new resource
            if (res == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Managed System not associated with a resource");
                return null;
            }

            if (StringUtils.equalsIgnoreCase(res.getResourceType().getId(),
                    AuthorizationConstants.MENU_ITEM_RESOURCE_TYPE)) {
                return handlMenuResource(request, response, resourceId);
            } else {
                ReconciliationConfigCommand reconCommand = new ReconciliationConfigCommand();
                ReconciliationConfig reconciliationConfig;
                if(reconId != null) {
                // get recon config
                    reconciliationConfig = reconciliationWebService.getConfigById(reconId).getConfig();
                    if (CollectionUtils.isEmpty(reconciliationConfig.getSituationSet())) {
                        reconciliationConfig.setSituationSet(this.getDefaultSituations());
                    }
                } else {
                // if config for current res is not exist "NEW CREATION"
                    reconciliationConfig = new ReconciliationConfig();
                    reconciliationConfig.setResourceId(resourceId);
                    reconciliationConfig.setManagedSysId(mngSysId);
                    reconciliationConfig.setSituationSet(this.getDefaultSituations());
                }

                // Check mSYs existing
                ManagedSysDto mSys = managedSysServiceClient.getManagedSysByResource(resourceId);
                // if not exist redirect to Msys creation
                if (mSys == null) {
                    return "/webconsole-idm/provisioning/managedSystem";
                }
                ProvisionConnectorDto pc = provisionConnectorWebService.getProvisionConnector(mSys.getConnectorId());
                // if not exist redirect to Msys creation
                if (pc == null) {
                    return "/webconsole-idm/provisioning/connector";
                }
                setMenuTree(request, managedSystemEditPageRootMenuName);
                reconCommand.setReconConfig(reconciliationConfig);
                reconCommand.setResourceName(res.getName());

                if (!StringUtils.isEmpty(fileWebService.getFile(resourceId + ".csv"))) {
                    reconCommand.setCSVFileName(resourceId + ".csv");
                }
                reconCommand.setIsCSV(pc.getServiceUrl().contains("CSV"));
                reconCommand.setResource(res);
                reconCommand.setSituationSet(new ArrayList<ReconciliationSituation>(reconciliationConfig
                        .getSituationSet()));
                request.setAttribute("managedSystem", mSysDto);
                request.setAttribute("reconCommand", reconCommand);
            }
            request.setAttribute("mngSystem",mSysDto);
            request.setAttribute("dateFormatDP", DateFormatStr.getDpDate());
            request.setAttribute("dateFormatJSTL", DateFormatStr.getSdfDate());
            request.setAttribute("dateFormatJSTLFull", DateFormatStr.getSdfDateTime());
        }
        return "resources/reconciliationEdit";
    }

    private Set<ReconciliationSituation> getDefaultSituations() {
        Set<ReconciliationSituation> situations = new HashSet<ReconciliationSituation>();
        situations.add(new ReconciliationSituation(null, ReconciliationCommand.IDM_EXISTS__SYS_EXISTS));
        situations.add(new ReconciliationSituation(null, ReconciliationCommand.IDM_DELETED__SYS_EXISTS));
        situations.add(new ReconciliationSituation(null, ReconciliationCommand.IDM_EXISTS__SYS_NOT_EXISTS));
        situations.add(new ReconciliationSituation(null, ReconciliationCommand.SYS_EXISTS__IDM_NOT_EXISTS));
        return situations;
    }

    @RequestMapping(value = "/save-reconciliation-config", method = RequestMethod.POST)
    public String save(final HttpServletRequest request, final HttpServletResponse response,
            final ReconciliationConfigCommand requestBody) throws Exception {
        log.info(requestBody.toString());
        SuccessToken successToken = null;
        ErrorToken errorToken = null;
        try {
            this.uploadCSV(requestBody);
            successToken = this.saveReconciliationConfig(requestBody);

        } catch (Exception e) {
            errorToken = new ErrorToken(Errors.INTERNAL_ERROR);
        }
        request.setAttribute("errorToken", errorToken);
        request.setAttribute("successToken", successToken);
        ManagedSysDto sysDto = managedSysServiceClient.getManagedSysByResource(requestBody.getReconConfig()
                .getResourceId());
        ReconciliationConfig config = requestBody.getReconConfig();
        return this.reconConfigEdit(request, response, config.getReconConfigId(), sysDto.getId());
    }

    private void updateBatchTask(ReconciliationConfig reconciliationConfig) {
        // update the batch configuration

        String configId = reconciliationConfig.getReconConfigId();
        if (configId != null) {
            ManagedSysDto managedSysDto = managedSysServiceClient.getManagedSys(reconciliationConfig.getManagedSysId());
            String name = "Reconcile=" + managedSysDto.getName();
            // check if a batch object for this already exists
            BatchTaskSearchBean searchBean = new BatchTaskSearchBean();
            BatchTask task = null;
            searchBean.setName(name);
            List<BatchTask> tasksList = batchDataService.findBeans(searchBean, -1, -1);
            if (!CollectionUtils.isEmpty(tasksList)) {
                task = tasksList.get(0);
            }

            if (StringUtils.isEmpty(reconciliationConfig.getFrequency())
                    || "INACTIVE".equalsIgnoreCase(reconciliationConfig.getStatus())) {
                if (task != null) {
                    batchDataService.removeBatchTask(task.getId());
                }

            } else {
                if (task == null) {
                    task = new BatchTask();
                    task.setName(name);
                    task.setId(null);
                    task.setParam1(configId);
                    task.setTaskUrl("batch/reconciliation.groovy");
                }
                task.setCronExpression(reconciliationConfig.getFrequency());
                task.setEnabled(true);
                task.setLastModifiedDate(new Date());
                batchDataService.save(task);
            }

        }
    }

/*    @RequestMapping(value = "/save-manual-recon", method = RequestMethod.POST)
    public String saveManualRecon(final HttpServletRequest request, final HttpServletResponse response,
            final @RequestBody ManualReconciliationBean requestBody) throws Exception {

        reconciliationWebService.manualReconciliation(requestBody.getReconResultBean(), requestBody.getResourceId());
        System.out.print(requestBody);
        return "";
    }*/

    @RequestMapping(value = "/start-reconciliation", method = RequestMethod.POST)
    public String reconcilation(final HttpServletRequest request, final HttpServletResponse response,
            final ReconciliationConfigCommand requestBody) throws Exception {
        log.info(requestBody.toString());
        SuccessToken successToken = null;
        ErrorToken errorToken = null;
        try {
            this.uploadCSV(requestBody);
            requestBody.getReconConfig().setExecStatus(ReconExecStatusOptions.STARTING);

            successToken = this.saveReconciliationConfig(requestBody);

        } catch (Exception e) {
            errorToken = new ErrorToken(Errors.INTERNAL_ERROR);
        }

        // try execute reconcile
        if (successToken != null) {
            try {
                asyncReconciliationServiceClient.startReconciliation(requestBody.getReconConfig());
            } catch (Exception e) {
                errorToken = new ErrorToken(Errors.INTERNAL_ERROR);
                successToken = null;
            }

        } else {
            errorToken = new ErrorToken(Errors.INTERNAL_ERROR);
        }
        request.setAttribute("errorToken", errorToken);
        request.setAttribute("successToken", successToken);

        ManagedSysDto sysDto = managedSysServiceClient.getManagedSysByResource(requestBody.getReconConfig()
                .getResourceId());
        return this.reconConfigEdit(request, response, requestBody.getReconConfig().getReconConfigId(), sysDto.getId());
    }

    @RequestMapping(value = "/stop-reconciliation", method = RequestMethod.POST)
    public String reconcilationStop(final HttpServletRequest request, final HttpServletResponse response,
            final ReconciliationConfigCommand requestBody) throws Exception {
        log.info(requestBody.toString());
        SuccessToken successToken = null;
        ErrorToken errorToken = null;
        try {
            this.uploadCSV(requestBody);
            ReconciliationConfig srcConfig = reconciliationWebService.getConfigById(
                    requestBody.getReconConfig().getReconConfigId()).getConfig();
            requestBody.getReconConfig().setExecStatus(ReconExecStatusOptions.STOPPED);

            successToken = this.saveReconciliationConfig(requestBody);
        } catch (Exception e) {
            errorToken = new ErrorToken(Errors.INTERNAL_ERROR);
        }

        request.setAttribute("errorToken", errorToken);
        request.setAttribute("successToken", successToken);

        ManagedSysDto sysDto = managedSysServiceClient.getManagedSysByResource(requestBody.getReconConfig()
                .getResourceId());
        return this.reconConfigEdit(request, response, requestBody.getReconConfig().getReconConfigId(), sysDto.getId());
    }

    @RequestMapping(value = "/get-reconciliation-report", method = RequestMethod.POST)
    public String getReport(final HttpServletRequest request, final HttpServletResponse response,
            final ReconciliationConfigCommand requestBody) {
        log.info(requestBody.toString());
        ManagedSysDto managedSys = managedSysServiceClient.getManagedSysByResource(requestBody.getReconConfig()
                .getResourceId());
        ReportQueryDto reportQuery = new ReportQueryDto();
        reportQuery.setReportName("RECONCILIATION_REPORT");
        reportQuery.addParameterValue("MANAGED_SYS_ID", managedSys.getId());
        String reportRunUri = reportServiceClient.getReportUrl(reportQuery, null,
                "../reportviewer/", localeResolver.resolveLocale(request).toString());

        if (StringUtils.isNotEmpty(reportRunUri)) {
            return "redirect:" + reportRunUri;
        } else {
            return null;
        }
    }

    private void uploadCSV(ReconciliationConfigCommand command) {
        MultipartFile file = command.getCsvFileUpload();
        if (file == null || StringUtils.isEmpty(file.getOriginalFilename()))
            return;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String row;
            StringBuilder text = new StringBuilder();
            while ((row = reader.readLine()) != null) {
                text.append(row);
                text.append("\n");
            }
            reader.close();
            fileWebService.saveFile(command.getReconConfig().getResourceId() + ".csv", text.toString());
        } catch (IOException e) {
            log.error("Exception", e);
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

    @ModelAttribute("matchFieldNameItems")
    public Map<String, String> populateMatchFieldNameItems() {
        Map<String, String> matchFieldNameItems = new LinkedHashMap<String, String>();
        matchFieldNameItems.put("", String.format("-%s-", getLocalizedMessage("openiam.ui.common.value.pleaseselect", null)));
        matchFieldNameItems.put("USERID", "IDM UserId");
        matchFieldNameItems.put("PRINCIPAL", "PRINCIPAL");
        matchFieldNameItems.put("MANAGED_SYS_PRINCIPAL", "MANAGED SYS PRINCIPAL");
        matchFieldNameItems.put("IDENTITY", "IDENTITY");
        matchFieldNameItems.put("EMAIL", "PRIMARY EMAIL ADDRESS");
        matchFieldNameItems.put("EMPLOYEE_ID", "EMPLOYEE ID");
        matchFieldNameItems.put("ATTRIBUTE", "CUSTOM ATTRIBUTE");
        matchFieldNameItems.put("NAME", "NAME");

        return matchFieldNameItems;
    }

    private SuccessToken saveReconciliationConfig(ReconciliationConfigCommand requestBody) throws Exception {
        ReconciliationConfig config = requestBody.getReconConfig();
        if (config == null) {
            throw new Exception("config is null");
        }
        config.setSituationSet(new HashSet<ReconciliationSituation>(requestBody.getSituationSet()));
        requestBody.getReconConfig().setUpdatedSince(new Date());

        if(!requestBody.isUseCustomScript()) {
            config.setCustomProcessorScript(null);
        }

        if (StringUtils.isEmpty(config.getReconConfigId())) {
            config.setReconConfigId(reconciliationWebService.addConfig(config).getConfig().getReconConfigId());
        } else {
            reconciliationWebService.updateConfig(config);
        }
        this.updateBatchTask(config);
        return new SuccessToken(SuccessMessage.RECONCILIATION_CONFIG_SAVED);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, "reconConfig.updatedSince", new DateEditor(DateFormatStr.getSdfDate()));
        binder.registerCustomEditor(Date.class, "reconConfig.lastExecTime", new DateEditor(DateFormatStr.getSdfDateTime()));
    }

}
