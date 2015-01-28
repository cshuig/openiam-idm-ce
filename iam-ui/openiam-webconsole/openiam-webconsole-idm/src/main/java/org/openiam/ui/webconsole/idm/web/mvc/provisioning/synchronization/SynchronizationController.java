package org.openiam.ui.webconsole.idm.web.mvc.provisioning.synchronization;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openiam.am.srvc.constants.SearchScopeType;
import org.openiam.base.ws.Response;
import org.openiam.idm.searchbeans.BatchTaskSearchBean;
import org.openiam.idm.srvc.batch.dto.BatchTask;
import org.openiam.idm.srvc.batch.service.BatchDataService;
import org.openiam.idm.srvc.org.dto.Organization;
import org.openiam.idm.srvc.synch.dto.SynchConfig;
import org.openiam.idm.srvc.synch.dto.SynchConfigSearchBean;
import org.openiam.idm.srvc.synch.ws.AsynchIdentitySynchService;
import org.openiam.idm.srvc.synch.ws.IdentitySynchWebService;
import org.openiam.idm.srvc.synch.ws.SynchConfigResponse;
import org.openiam.idm.util.RemoteFileStorageManager;
import org.openiam.ui.constants.*;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.mvc.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.*;

import org.openiam.ui.util.messages.Errors;

@Controller
public class SynchronizationController extends AbstractController {

    private static final Logger LOG = Logger.getLogger(SynchronizationController.class);

    public static final String SYNC_DIR = "sync";

    @Value("${org.openiam.upload.root}")
    private String uploadRoot;

    @Value("${org.openiam.ui.idm.synchronization.root.name}")
    private String rootMenuName;

    @Value("${org.openiam.ui.idm.synchronization.edit.root.name}")
    private String syncConfigEditRootMenuName;

    @Value("${org.openiam.upload.remote.sftp.directory}")
    private String remoteFilestorageDir;

    @Value("${org.openiam.upload.remote.use}")
    private Boolean useRemoteFilestorage;

    @Autowired
    private AsynchIdentitySynchService asynchSynchServiceWS;

    @Autowired
    private IdentitySynchWebService synchConfigServiceClient;

    @Autowired
    private RemoteFileStorageManager remoteFileStorageManager;

    @Resource(name = "batchServiceClient")
    private BatchDataService batchDataService;

    @RequestMapping(value = "/provisioning/synchronizationlist", method = RequestMethod.GET)
    public String list(SynchronizationListCommand synchListCommand,
                       final HttpServletRequest request,
                       Model model) {

        setMenuTree(request, rootMenuName);

        SynchConfigSearchBean searchBean = new SynchConfigSearchBean();
        if (org.apache.commons.lang.StringUtils.isNotEmpty(synchListCommand.getName())) {
            searchBean.setName(synchListCommand.getName());
        }

        final int count = synchConfigServiceClient.getSynchConfigCount(searchBean);
        synchListCommand.setCount(count);

        List<SynchConfig> configList = synchConfigServiceClient.getSynchConfigs(searchBean,
                synchListCommand.getPage() * synchListCommand.getSize(), synchListCommand.getSize());
        model.addAttribute("configList", (configList != null) ? configList : Collections.EMPTY_LIST);

        model.addAttribute("synchListCommand", synchListCommand);
        return "/provisioning/synchronization/synchronizationlist";
    }

    @RequestMapping(value = "/provisioning/synchronization", method = RequestMethod.GET)
    public String formEdit(@RequestParam(value = "id", required = false) String synchronizationId,
                           final HttpServletRequest request, Model model) throws Exception {

        if (StringUtils.isNotEmpty(synchronizationId)) {
            setMenuTree(request, syncConfigEditRootMenuName);
        }

        SynchronizationCommand synchCommand;
        if (StringUtils.isNotEmpty(synchronizationId)) {
            SynchConfigResponse res = synchConfigServiceClient.findById(synchronizationId);
            if (res.isSuccess()) {
                try {
                    synchCommand = new SynchronizationCommand(res.getConfig());
                } catch (Exception e) {
                    synchCommand = new SynchronizationCommand();
                }
            } else {
                synchCommand = new SynchronizationCommand();
            }
        } else {
            synchCommand = new SynchronizationCommand();
        }
        synchCommand.setManagedSysId(defaultManagedSysId);
        Organization organization = new Organization();
        if (StringUtils.isNotBlank(synchCommand.getCompanyId())) {
            organization = organizationDataService.getOrganizationLocalized(synchCommand.getCompanyId(), null, getCurrentLanguage());
        }
        model.addAttribute("organization", jacksonMapper.writeValueAsString(organization));
        model.addAttribute("synchCommand", synchCommand);
        return "/provisioning/synchronization/synchronization";
    }

    private void updateBatchTask(SynchConfig synchConfig) {
        // update the batch configuration

        String configId = synchConfig.getSynchConfigId();
        if (configId != null) {
            String name = "Synchronization=" + synchConfig.getName();
            // check if a batch object for this already exists
            BatchTaskSearchBean searchBean = new BatchTaskSearchBean();
            BatchTask task = null;
            searchBean.setName(name);
            List<BatchTask> tasksList = batchDataService.findBeans(searchBean, -1,
                    -1);
            if (!CollectionUtils.isEmpty(tasksList)) {
                task = tasksList.get(0);
            }

            if (StringUtils.isEmpty(synchConfig.getSynchFrequency())
                    || "INACTIVE".equalsIgnoreCase(synchConfig.getStatus())) {
                if (task != null) {
                    batchDataService.removeBatchTask(task.getId());
                }

            } else {
                if (task == null) {
                    task = new BatchTask();
                    task.setName(name);
                    task.setId(null);
                    task.setParam1(configId);
                    task.setTaskUrl("batch/synchronization.groovy");
                }
                task.setCronExpression(synchConfig.getSynchFrequency());
                task.setEnabled(true);
                task.setLastModifiedDate(new Date());
                batchDataService.save(task);
            }

        }
    }

    @RequestMapping(value = "/provisioning/synchronization", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("synchCommand") SynchronizationCommand synchCommand,
                       BindingResult result, final HttpServletRequest request, Model model) {

        if (StringUtils.isNotEmpty(synchCommand.getSynchConfigId())) {
            setMenuTree(request, syncConfigEditRootMenuName);
        }

        if (result.hasErrors()) {
            model.addAttribute("synchCommand", synchCommand);
            return "provisioning/synchronization/synchronization";

        } else {
            if ("delete".equals(synchCommand.getSubmitType()) &&
                    StringUtils.isNotEmpty(synchCommand.getSynchConfigId())) {
                synchConfigServiceClient.removeConfig(synchCommand.getSynchConfigId());

            } else if ("submit".equals(synchCommand.getSubmitType())) {
                if (!synchCommand.getUseSystemPath()) {
                    MultipartFile file = synchCommand.getFileUpload();
                    if (file != null && !file.isEmpty()) {
                        BufferedReader reader = null;
                        BufferedWriter writer = null;
                        String generatedFileName = genFileName(file.getOriginalFilename());
                        String uploadPath = "";
                        try {
                            if (useRemoteFilestorage) {
                                try {
                                    remoteFileStorageManager.uploadFile(file.getInputStream(), SYNC_DIR, generatedFileName);
                                    uploadPath = genUploadPath(remoteFilestorageDir, generatedFileName);
                                } catch (JSchException jse) {
                                    LOG.error(jse);
                                    model.addAttribute("synchCommand", synchCommand);
                                    return "provisioning/synchronization/synchronization";
                                } catch (SftpException se) {
                                    LOG.error(se);
                                    model.addAttribute("synchCommand", synchCommand);
                                    return "provisioning/synchronization/synchronization";
                                }
                            } else {
                                uploadPath = genUploadPath(uploadRoot, generatedFileName);
                                FileUtils.forceMkdir(new File(FilenameUtils.getFullPath(uploadPath)));

                                reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(uploadPath))));

                                String row;
                                while ((row = reader.readLine()) != null) {
                                    writer.write(row);
                                    writer.newLine(); // Writes OS dependent EOL
                                }

                                reader.close();
                                writer.close();
                            }
                            if (StringUtils.isNotEmpty(uploadPath)) {
                                synchCommand.setFileName(FilenameUtils.getName(uploadPath));
                            } else {
                                model.addAttribute("synchCommand", synchCommand);
                                return "provisioning/synchronization/synchronization";
                            }
                        } catch (IOException ioe) {
                            LOG.error(ioe);
                            model.addAttribute("synchCommand", synchCommand);
                            return "provisioning/synchronization/synchronization";

                        } finally {
                            IOUtils.closeQuietly(reader);
                            IOUtils.closeQuietly(writer);
                        }

                    } else if (StringUtils.isNotBlank(synchCommand.getHiddenFileName())) {
                        synchCommand.setFileName(synchCommand.getHiddenFileName());
                    }
                }

                try {
                    synchConfigServiceClient.mergeConfig(synchCommand.getSynchConfigDTO());
                    updateBatchTask(synchCommand.getSynchConfigDTO());
                } catch (Exception e) {
                    log.error("Error in save", e);
                    model.addAttribute("synchCommand", synchCommand);
                    return "provisioning/synchronization/synchronization";
                }
            }
        }
        return "redirect:/provisioning/synchronizationlist.html";
    }

    @RequestMapping(value = "/provisioning/startSynch", method = RequestMethod.POST)
    public String startSynch(final HttpServletRequest request, final @RequestBody SynchConfigModel synchConfigModel) throws IOException {

        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        try {
            if (StringUtils.isNotBlank(synchConfigModel.getSynchConfigId())) {
                SynchConfigResponse res = synchConfigServiceClient.findById(synchConfigModel.getSynchConfigId());
                if (res.isSuccess()) {
                    Response testResponse = synchConfigServiceClient.testConnection(res.getConfig());
                    if (testResponse.isSuccess()) {
                        asynchSynchServiceWS.startSynchronization(res.getConfig());
                        ajaxResponse.setStatus(200);
                        ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.SYNCH_CONFIG_START_SUCCESS));
                    } else {
//                        org.openiam.ui.util.messages.Errors error = org.openiam.ui.util.messages.Errors.SYNCH_CONFIG_START_FAIL;
                        ajaxResponse.addError(new ErrorToken(testResponse.getErrorText()));
                        ajaxResponse.setStatus(500);
                    }
                }
            }
        } catch (Exception e) {
            org.openiam.ui.util.messages.Errors error = org.openiam.ui.util.messages.Errors.SYNCH_CONFIG_START_FAIL;
            ajaxResponse.addError(new ErrorToken(error));
            ajaxResponse.setStatus(500);
        }

        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/provisioning/testConnect", method = RequestMethod.POST)
    public String testConnect(final HttpServletRequest request,
                              final HttpServletResponse response,
                              final @RequestBody SynchConfigModel synchConfigModel) throws IOException {

        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        Response testResponse = null;
        try {
            if (StringUtils.isNotBlank(synchConfigModel.getSynchConfigId())) {
                SynchConfigResponse res = synchConfigServiceClient.findById(synchConfigModel.getSynchConfigId());
                if (res.isSuccess()) {
                    testResponse = synchConfigServiceClient.testConnection(res.getConfig());
                    if (testResponse.isSuccess()) {
                        ajaxResponse.setStatus(200);
                        ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.SYNCH_CONFIG_TEST_SUCCESS));
                    }
                }
            }

        } catch (Exception e) {
        }
        if (testResponse == null || testResponse.getErrorCode() != null) {
            Errors error = Errors.INTERNAL_ERROR;
            switch (testResponse.getErrorCode()) {
                case FILE_EXCEPTION:
                    error = Errors.FILE_NOT_EXIST;
                    break;
                case FAIL_CONNECTION:
                    error = Errors.CAN_T_CONNECT;
                    break;
                case SQL_EXCEPTION:
                    error = Errors.SQL_PROBLEMS;
                    break;
                case CLASS_NOT_FOUND:
                    error = Errors.WRONG_CLASS_IN_SCTIPT;
                    break;
                case IO_EXCEPTION:
                    error = Errors.SCRIPT_PROBLEMS;
                    break;
                default:
                    break;
            }
            ajaxResponse.addError(new ErrorToken(Errors.SYNCH_CONFIG_TEST_FAIL));
            ajaxResponse.addError(new ErrorToken(error));
            ajaxResponse.setStatus(500);
        }

        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @ModelAttribute("synchAdapterItems")
    public Map<String, String> populateSynchAdapterItems(final HttpServletRequest request) {
        Locale locale = localeResolver.resolveLocale(request);
        Map<String, String> synchAdapterItems = new LinkedHashMap<String, String>();
        synchAdapterItems.put("", messageSource.getMessage("openiam.ui.common.value.pleaseselect", null, String.format("-%s-", getLocalizedMessage("openiam.ui.common.value.pleaseselect", null)), locale));
        synchAdapterItems.put("CSV", messageSource.getMessage("openiam.ui.webconsole.idm.synch.config.CSV", null, "CSV FILE", locale));
        synchAdapterItems.put("RDBMS", messageSource.getMessage("openiam.ui.webconsole.idm.synch.config.RDBMS", null, "RDBMS", locale));
        synchAdapterItems.put("LDAP", messageSource.getMessage("openiam.ui.webconsole.idm.synch.config.LDAP", null, "LDAP", locale));
        synchAdapterItems.put("AD", messageSource.getMessage("openiam.ui.webconsole.idm.synch.config.AD", null, "ACTIVE DIRECTORY", locale));
        synchAdapterItems.put("WS", messageSource.getMessage("openiam.ui.webconsole.idm.synch.config.WS", null, "WEB SERVICE", locale));
        synchAdapterItems.put("CUSTOM", messageSource.getMessage("openiam.ui.webconsole.idm.synch.config.CUSTOM", null, "CUSTOM ADAPTER", locale));
        return synchAdapterItems;
    }

    @ModelAttribute("statusItems")
    public Map<String, String> populateStatusItems(final HttpServletRequest request) {
        Locale locale = localeResolver.resolveLocale(request);
        Map<String, String> statusItems = new LinkedHashMap<String, String>();
        statusItems.put("", messageSource.getMessage("openiam.ui.common.value.pleaseselect", null, String.format("-%s-", getLocalizedMessage("openiam.ui.common.value.pleaseselect", null)), locale));
        statusItems.put("ACTIVE", messageSource.getMessage("openiam.ui.common.active", null, "ACTIVE", locale));
        statusItems.put("INACTIVE", messageSource.getMessage("openiam.ui.common.inactive", null, "INACTIVE", locale));
        return statusItems;
    }

    @ModelAttribute("processRuleItems")
    public Map<String, String> populateProcessRuleItems(final HttpServletRequest request) {
        Locale locale = localeResolver.resolveLocale(request);
        Map<String, String> processRuleItems = new LinkedHashMap<String, String>();
        processRuleItems.put("USER", messageSource.getMessage("openiam.ui.webconsole.idm.synch.config.USER", null, "USER", locale));
        processRuleItems.put("ROLE", messageSource.getMessage("openiam.ui.webconsole.idm.synch.config.ROLE", null, "ROLE", locale));
        processRuleItems.put("ORG", messageSource.getMessage("openiam.ui.webconsole.idm.synch.config.ORG", null, "ORGANIZATION", locale));
        return processRuleItems;
    }

    @ModelAttribute("synchTypeItems")
    public Map<String, String> populateSynchTypeItems(final HttpServletRequest request) {
        Locale locale = localeResolver.resolveLocale(request);
        Map<String, String> synchTypeItems = new LinkedHashMap<String, String>();
        synchTypeItems.put("FULL", messageSource.getMessage("openiam.ui.webconsole.idm.synch.config.FULL", null, "COMPLETE", locale));
        synchTypeItems.put("INCREMENTAL", messageSource.getMessage("openiam.ui.webconsole.idm.synch.config.INCREMENTAL", null, "INCREMENTAL", locale));
        return synchTypeItems;
    }

    @ModelAttribute("synchFrequencyItems")
    public Map<String, String> populateSynchFrequencyItems(final HttpServletRequest request) {
        Locale locale = localeResolver.resolveLocale(request);
        Map<String, String> synchFrequencyItems = new LinkedHashMap<String, String>();
        synchFrequencyItems.put("", messageSource.getMessage("openiam.ui.common.value.pleaseselect", null, String.format("-%s-", getLocalizedMessage("openiam.ui.common.value.pleaseselect", null)), locale));
        for (CommonFrequencyOptions options : CommonFrequencyOptions.values()) {
            synchFrequencyItems.put(options.getValue(), options.getLabel());
        }
        return synchFrequencyItems;
    }

    @ModelAttribute("matchFieldNameItems")
    public Map<String, String> populateMatchFieldNameItems(final HttpServletRequest request) {
        Locale locale = localeResolver.resolveLocale(request);
        Map<String, String> matchFieldNameItems = new LinkedHashMap<String, String>();
        matchFieldNameItems.put("", messageSource.getMessage("openiam.ui.common.value.pleaseselect", null, String.format("-%s-", getLocalizedMessage("openiam.ui.common.value.pleaseselect", null)), locale));
        matchFieldNameItems.put("USERID", messageSource.getMessage("", null, "IDM USER ID", locale));
        matchFieldNameItems.put("PRINCIPAL", messageSource.getMessage("", null, "PRINCIPAL NAME", locale));
        matchFieldNameItems.put("EMAIL", messageSource.getMessage("", null, "PRIMARY EMAIL ADDRESS", locale));
        matchFieldNameItems.put("EMPLOYEE_ID", messageSource.getMessage("", null, "EMPLOYEE ID", locale));
        matchFieldNameItems.put("ATTRIBUTE", messageSource.getMessage("", null, "CUSTOM ATTRIBUTE", locale));
        return matchFieldNameItems;
    }

    @ModelAttribute("searchScopeItems")
    public SearchScopeType[] populateSearchScopeItems() {
        return SearchScopeType.values();
    }

    private String genFileName(final String originalFilename) {
        return FilenameUtils.getBaseName(originalFilename)
                + "_" + new Date().getTime()
                + FilenameUtils.EXTENSION_SEPARATOR
                + FilenameUtils.getExtension(originalFilename);
    }

    private String genUploadPath(final String defaultRootDir, final String generatedFileName) {
        String name = defaultRootDir + "/" + SYNC_DIR + "/"
                + generatedFileName;
        return name;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        final Validator nativeValidator = binder.getValidator();
        binder.setValidator(new Validator() {
            @Override
            public boolean supports(Class<?> clazz) {
                return SynchronizationCommand.class.isAssignableFrom(clazz) ||
                        SynchronizationListCommand.class.isAssignableFrom(clazz);
            }

            @Override
            public void validate(Object command, org.springframework.validation.Errors errors) {
                nativeValidator.validate(command, errors);
                if (command instanceof SynchronizationCommand) {
                    SynchronizationCommand cmd = (SynchronizationCommand) command;
                    if (cmd.getUseTransformationScript()) {
                        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "transformationRule", "field.required");
                    }
                    if (!cmd.getUsePolicyMap() && !cmd.getUseTransformationScript()) {
                        errors.reject("required.synchCommand.policyMapOrTransformationScript.checked",
                                "Please check at least one 'Use Policy Map' or 'Use Transformation Script'");
                    }
                }
            }
        });
    }

}
