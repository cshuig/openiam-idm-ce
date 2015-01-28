package org.openiam.ui.webconsole.idm.web.mvc.provisioning.mngsystems;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.openiam.am.srvc.constants.SearchScopeType;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.searchbeans.ResourceSearchBean;
import org.openiam.idm.srvc.mngsys.dto.ManagedSysDto;
import org.openiam.idm.srvc.mngsys.dto.ManagedSysRuleDto;
import org.openiam.idm.srvc.mngsys.dto.ManagedSysSearchBean;
import org.openiam.idm.srvc.mngsys.dto.ManagedSystemObjectMatch;
import org.openiam.idm.srvc.mngsys.dto.ProvisionConnectorDto;
import org.openiam.idm.srvc.mngsys.dto.ProvisionConnectorSearchBean;
import org.openiam.idm.srvc.mngsys.ws.ManagedSystemWebService;
import org.openiam.idm.srvc.mngsys.ws.ProvisionConnectorWebService;
import org.openiam.idm.srvc.res.service.ResourceDataService;
import org.openiam.provision.service.ProvisionService;
import org.openiam.ui.rest.api.model.KeyNameBean;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.model.BeanResponse;
import org.openiam.ui.web.mvc.AbstractController;
import org.openiam.ui.webconsole.idm.web.mvc.provisioning.bean.ManagedSysBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MngSystemController extends AbstractController {
    @Autowired
    private ProvisionConnectorWebService connectorServiceClient;
    @Value("${org.openiam.ui.idm.mngconnections.root.name}")
    private String rootMenuName;
    @Autowired
    private ManagedSystemWebService managedSysServiceClient;
    @Resource(name = "resourceServiceClient")
    private ResourceDataService resourceDataService;
    @Value("${org.openiam.defaultUserId}")
    private String defaultUserId;
    @Value("${org.openiam.ui.idm.mngedit.root.name}")
    private String managedSystemEditPageRootMenuName;
    @Value("${org.openiam.ui.idm.mngedit_without_res.root.name}")
    private String managedSystemEditWithoutResourcePageRootMenuName;

    @Resource(name = "provisionServiceClient")
    protected ProvisionService provisionService;

    private List<KeyNameBean> getConnectorsAsKeyNameBeans(final List<ProvisionConnectorDto> connectors) {
        return mapper.mapToList(connectors, KeyNameBean.class);
    }

    private List<KeyNameBean> getManagedSystemsAsKeyNameBeans(final List<ManagedSysDto> allSystems) {
        return mapper.mapToList(allSystems, KeyNameBean.class);
    }

    @RequestMapping(value = "/provisioning/mngsystemlist", method = RequestMethod.GET)
    public String list(final HttpServletRequest request) throws Exception {
        setMenuTree(request, rootMenuName);
        return "/provisioning/managedSystemList";
    }

    @RequestMapping(value = "/provisioning/searchManagedSystems", method = RequestMethod.GET)
    public @ResponseBody
    BeanResponse searchManagedSystems(@RequestParam(required = false, value = "name") String name,
            final @RequestParam(required = false, value = "domainId") String domainId,
            final @RequestParam(required = true, value = "size") Integer size,
            final @RequestParam(required = true, value = "from") Integer from) {
        if (StringUtils.isNotBlank(name)) {
            if (name.charAt(0) != '*') {
                name = "*" + name;
            }

            if (name.charAt(name.length() - 1) != '*') {
                name = name + "*";
            }
        }

        final ManagedSysSearchBean searchBean = new ManagedSysSearchBean();
        searchBean.setDeepCopy(false);
        searchBean.setName(name);
        final List<ManagedSysDto> managedSystems = managedSysServiceClient.getManagedSystems(searchBean, size, from);
        final Integer count = managedSysServiceClient.getManagedSystemsCount(searchBean);
        final List<ManagedSysBean> beans = mapper.mapToList(managedSystems, ManagedSysBean.class);
        return new BeanResponse(beans, count);
    }

    @RequestMapping(value = "/provisioning/testManagedSysConnection.html", method = RequestMethod.POST)
    public String testManagedSysConnection(final HttpServletRequest request, final HttpServletResponse response,
            final @RequestParam(value = "id", required = false) String id) {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        final String callerId = getRequesterId(request);
        final Response wsResponse = provisionService.testConnectionConfig(id, callerId);

        if (ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
            ajaxResponse.setStatus(200);
            ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.MANAGED_SYS_TEST_CONNECT));
        } else {
            Object[] params = null;
            Errors error = Errors.MANAGED_SYS_TEST_CONNECT_FAILRE;
            if (wsResponse.getErrorCode() != null) {
                switch (wsResponse.getErrorCode()) {
                default:
                    break;
                }
            }
            ajaxResponse.addError(new ErrorToken(error, params));
            ajaxResponse.setStatus(500);
        }
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/provisioning/certRequest.html", method = RequestMethod.POST)
    public String sslCertificateRequest(final HttpServletRequest request, final HttpServletResponse response,
                                     final @RequestParam(value = "id", required = false) String id) {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        final String callerId = getRequesterId(request);
        final ManagedSysDto managedSysDto = managedSysServiceClient.getManagedSys(id);
        final Response wsResponse = managedSysServiceClient.requestSSLCert(managedSysDto, callerId);

        if (ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
            ajaxResponse.setStatus(200);
            ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.MANAGED_SYS_CERTIFICATE_REQUEST));
        } else {
            Object[] params = null;
            Errors error = Errors.MANAGED_SYS_CERTIFICATE_REQUEST_FAILRE;
            if (wsResponse.getErrorCode() != null) {
                switch (wsResponse.getErrorCode()) {
                    default:
                        break;
                }
            }
            ajaxResponse.addError(new ErrorToken(error, params));
            ajaxResponse.setStatus(500);
        }
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/provisioning/mngsystem.html", method = RequestMethod.GET)
    public String formEdit(final @RequestParam(value = "id", required = false) String id, final Model model,
            final HttpServletRequest request) throws IOException {

        org.openiam.idm.srvc.res.dto.Resource res = null;
        MngSystemCommand mngSystemCommand = null;
        ManagedSysDto managedSysDto = null;
        if (StringUtils.isNotEmpty(id)) {
            managedSysDto = managedSysServiceClient.getManagedSys(id);
        }
        if (managedSysDto != null) {
            mngSystemCommand = new MngSystemCommand(managedSysDto);
            if (StringUtils.isNotBlank(managedSysDto.getResourceId())) {
                res = resourceDataService.getResource(managedSysDto.getResourceId(), getCurrentLanguage());
                model.addAttribute("linkedResource", res);
                model.addAttribute("resourceAsJSON", jacksonMapper.writeValueAsString(res));
                List<String> attrList = provisionService.getManagedSystemAttributesList(managedSysDto.getId());
                List<KeyNameBean> mngSysProps = new ArrayList<KeyNameBean>();
                if (CollectionUtils.isNotEmpty(attrList)) {
                    for (String attrName: attrList) {
                        mngSysProps.add(new KeyNameBean(attrName, attrName));
                    }
                    request.setAttribute("mngSysPropsAsJSON", jacksonMapper.writeValueAsString(mngSysProps));
                }
                setMenuTree(request, managedSystemEditPageRootMenuName);

            } else {
                setMenuTree(request, managedSystemEditWithoutResourcePageRootMenuName);
            }
        } else {
            mngSystemCommand = new MngSystemCommand();
            setMenuTree(request, managedSystemEditWithoutResourcePageRootMenuName);
        }

        final List<ProvisionConnectorDto> connectors = connectorServiceClient.getProvisionConnectors(
                new ProvisionConnectorSearchBean(), 0, Integer.MAX_VALUE);
        final List<ManagedSysDto> allSystems = managedSysServiceClient.getAllManagedSys();
        model.addAttribute("systems", getManagedSystemsAsKeyNameBeans(allSystems));
        model.addAttribute("connectors", getConnectorsAsKeyNameBeans(connectors));
        model.addAttribute("mngSystemCommand", mngSystemCommand);
        return "/provisioning/managedSystem";
    }

    @RequestMapping(value = "/provisioning/deleteManagedSystem.html", method = RequestMethod.POST)
    public String deleteManagedSys(final @RequestParam(required = true, value = "id") String id,
            final HttpServletResponse response, final HttpServletRequest request) {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        final Response wsResponse = managedSysServiceClient.removeManagedSystem(id);
        if (ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
            ajaxResponse.setStatus(200);
            ajaxResponse.setRedirectURL("mngsystemlist.html");
            ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.MANAGED_SYS_DELETED));
        } else {
            Object[] params = null;
            Errors error = Errors.MANAGED_SYS_DELETE_ERROR;
            if (wsResponse.getErrorCode() != null) {
                switch (wsResponse.getErrorCode()) {
                case LINKED_TO_AUTHENTICATION_PROVIDER:
                    params = new Object[1];
                    params[0] = wsResponse.getResponseValue();
                    error = Errors.MANAGED_SYS_LINKED_TO_AUTHENTICATION_PROVIDER;
                    break;
                default:
                    break;
                }
            }
            ajaxResponse.addError(new ErrorToken(error, params));
            ajaxResponse.setStatus(500);
        }
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/provisioning/mngsystem.html", method = RequestMethod.POST)
    public String save(final @RequestBody MngSystemCommand mngSystemCommand, final HttpServletRequest request) {
        ManagedSysDto msysDto;
        ManagedSystemObjectMatch matchObjUser = null;
        ManagedSystemObjectMatch matchObjGroup = null;
        if (StringUtils.isNotEmpty(mngSystemCommand.getId())) {
            msysDto = managedSysServiceClient.getManagedSys(mngSystemCommand.getId());
            msysDto = mngSystemCommand.getUpdatedManagedSys(msysDto);

            matchObjUser = mngSystemCommand.getManagedSystemObjectMatchUser();
            matchObjUser.setManagedSys(mngSystemCommand.getId());

            matchObjGroup = mngSystemCommand.getManagedSystemObjectMatchGroup();
            matchObjGroup.setManagedSys(mngSystemCommand.getId());

            msysDto.setRules(managedSysServiceClient.getRulesByManagedSysId(mngSystemCommand.getId()));
        } else {
            msysDto = mngSystemCommand.getManagedSysDto();
            matchObjUser = mngSystemCommand.getManagedSystemObjectMatchUser();
            matchObjUser.setObjectSearchId(null);
            matchObjUser.setManagedSys(msysDto.getId());

            matchObjGroup = mngSystemCommand.getManagedSystemObjectMatchGroup();
            matchObjGroup.setObjectSearchId(null);
            matchObjGroup.setManagedSys(msysDto.getId());
        }
        msysDto.addManagedSysObjectMatch(matchObjUser);
        msysDto.addManagedSysObjectMatch(matchObjGroup);

        if (StringUtils.isEmpty(msysDto.getUserId())) {
            msysDto.setUserId(defaultUserId);
        }

        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        Response wsResponse = null;
        Errors error = null;
        Object[] params = null;
        String managedSysId = null;
        try {
            wsResponse = managedSysServiceClient.saveManagedSystem(msysDto);
            if (ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
                managedSysId = (String) wsResponse.getResponseValue();
                matchObjUser.setManagedSys(managedSysId);
                matchObjGroup.setManagedSys(managedSysId);
                wsResponse = managedSysServiceClient.saveManagedSystemObjectMatch(matchObjUser);
                wsResponse = managedSysServiceClient.saveManagedSystemObjectMatch(matchObjGroup);
            } else {
                error = Errors.MANAGED_SYS_SAVE_ERROR;
                if (wsResponse.getErrorCode() != null) {
                    final Object responseValue = wsResponse.getResponseValue();
                    switch (wsResponse.getErrorCode()) {
                    case NO_NAME:
                        error = Errors.MANAGED_SYS_NAME_REQUIRED;
                        break;
                    case CONNECTOR_REQUIRED:
                        error = Errors.CONNECTOR_REQUIRED;
                        break;
                    default:
                        break;
                    }
                }

            }
            if (error == null) {
                String resourceId = mngSystemCommand.getResourceId();
                if (StringUtils.isNotBlank(resourceId)) {
                    final ResourceSearchBean searchBean = new ResourceSearchBean();
                    searchBean.setKey(resourceId);
                    searchBean.setDeepCopy(true);
                    List<org.openiam.idm.srvc.res.dto.Resource> resultList = resourceDataService.findBeans(searchBean, 0, 1, getCurrentLanguage());
                    if (CollectionUtils.isNotEmpty(resultList)) {
                        org.openiam.idm.srvc.res.dto.Resource resource = resultList.get(0);
                        resource.setResourceProps(mngSystemCommand.getResourceProps());
                        wsResponse = resourceDataService.saveResource(resource, getRequesterId(request));
                        if (wsResponse.isFailure()) {
                            error = Errors.RESOURCE_PROP_COULD_NOT_SAVE;
                        }
                    }

                }
            }

        } finally {
            if (error == null) {
                ajaxResponse.setRedirectURL(String.format("mngsystem.html?id=%s", managedSysId));
                ajaxResponse.setStatus(200);
                ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.MANAGED_SYS_SAVED));
            } else {
                ajaxResponse.addError(new ErrorToken(error, params));
                ajaxResponse.setStatus(500);
            }
        }
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";

    }

    // managed system RULES >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @RequestMapping(value = "/provisioning/mngsystemrules.html", method = RequestMethod.GET)
    public String getRules(final @RequestParam(value = "id", required = true) String id,
            final HttpServletRequest request, final HttpServletResponse response, final Model model) throws IOException {
        setMenuTree(request, managedSystemEditPageRootMenuName);
        final ManagedSysDto mSys = managedSysServiceClient.getManagedSys(id);
        if (mSys == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        final MngSystemRuleCommand command = new MngSystemRuleCommand();
        command.setmSysId(mSys.getId());
        command.setResourceId(mSys.getResourceId());
        command.setmSysRules(mSys.getRules());
        command.setmSysName(mSys.getName());
        model.addAttribute("command", command);
        return "/provisioning/managedSystemRules";
    }

    @RequestMapping(value = "/provisioning/managed-system-rule-save", method = RequestMethod.POST)
    public String addRuleAttribute(final HttpServletRequest request, final HttpServletResponse response,
            final @RequestBody ManagedSysRuleDto rule) {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();

        if (!ajaxResponse.isError()) {
            final ManagedSysRuleDto newRule = managedSysServiceClient.addRules(rule);
            if (newRule != null && newRule.getManagedSysRuleId() != null) {
                ajaxResponse.setStatus(HttpServletResponse.SC_OK);
                ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.RESOURCE_PROPERTY_SAVED));
            } else {
                ajaxResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                ajaxResponse.addError(new ErrorToken(Errors.RESOURCE_PROP_COULD_NOT_SAVE));
            }
        }

        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/provisioning/managed-system-role-delete", method = RequestMethod.POST)
    public String deleteResoruceProp(final HttpServletRequest request, final HttpServletResponse response,
            final @RequestParam(required = true, value = "id") String ruleId) {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        try {
            managedSysServiceClient.deleteRules(ruleId);
            ajaxResponse.setStatus(HttpServletResponse.SC_OK);
            ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.RESOURCE_PROPERT_DELETED));
        } catch (Exception e) {
            ajaxResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ajaxResponse.addError(new ErrorToken(Errors.RESOURCE_PROP_COULD_NOT_SAVE));
        }
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @ModelAttribute("searchScopeItems")
    public SearchScopeType[] populateSearchScopeItems() {
        return SearchScopeType.values();
    }
}
