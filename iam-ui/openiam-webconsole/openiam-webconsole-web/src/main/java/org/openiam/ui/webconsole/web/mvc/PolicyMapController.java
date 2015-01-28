package org.openiam.ui.webconsole.web.mvc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.openiam.authmanager.common.model.AuthorizationMenu;
import org.openiam.authmanager.service.AuthorizationManagerMenuWebService;
import org.openiam.authmanager.util.AuthorizationConstants;
import org.openiam.base.ws.SortParam;
import org.openiam.idm.searchbeans.AttributeMapSearchBean;
import org.openiam.idm.searchbeans.PolicySearchBean;
import org.openiam.idm.searchbeans.ResourceSearchBean;
import org.openiam.idm.srvc.audit.constant.AuditAction;
import org.openiam.idm.srvc.audit.constant.AuditSource;
import org.openiam.idm.srvc.audit.dto.IdmAuditLog;
import org.openiam.idm.srvc.audit.ws.IdmAuditLogWebDataService;
import org.openiam.idm.srvc.mngsys.dto.AttributeMap;
import org.openiam.idm.srvc.mngsys.dto.ManagedSysDto;
import org.openiam.idm.srvc.mngsys.dto.PolicyMapDataTypeOptions;
import org.openiam.idm.srvc.mngsys.dto.PolicyMapObjectTypeOptions;
import org.openiam.idm.srvc.mngsys.ws.ManagedSystemWebService;
import org.openiam.idm.srvc.policy.dto.Policy;
import org.openiam.idm.srvc.policy.dto.PolicyConstants;
import org.openiam.idm.srvc.policy.service.PolicyDataService;
import org.openiam.idm.srvc.res.service.ResourceDataService;
import org.openiam.idm.srvc.synch.dto.SynchConfig;
import org.openiam.idm.srvc.synch.ws.IdentitySynchWebService;
import org.openiam.provision.service.ProvisionService;
import org.openiam.ui.audit.AuditLogProvider;
import org.openiam.ui.constants.ReconResourceTypeOptions;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.webconsole.util.AttributeMapUtil;
import org.openiam.ui.webconsole.validator.PolicyMapReqestValidator;
import org.openiam.ui.webconsole.web.model.PolicyMapRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

/**
 * 
 * @author zaporozhec
 * 
 */
@Controller
public class PolicyMapController extends AbstractWebconsoleController {

    private static final Log log = LogFactory.getLog(PolicyMapController.class);
    @Resource(name = "synchConfigServiceClient")
    private IdentitySynchWebService synchConfigServiceClient;
    @Resource(name = "resourceServiceClient")
    private ResourceDataService resourceDataService;

    @Resource(name = "managedSysServiceClient")
    private ManagedSystemWebService managedSysServiceClient;

    @Resource(name = "authorizationManagerMenuServiceClient")
    private AuthorizationManagerMenuWebService authManagerMenuService;

    @Resource(name = "provisionServiceClient")
    private ProvisionService provisionServiceClient;

    @Resource(name = "policyServiceClient")
    private PolicyDataService policyDataService;

    @Resource(name = "auditServiceClient")
    private IdmAuditLogWebDataService auditServiceClient;

    @Value("${org.openiam.ui.idm.mngedit.root.name}")
    private String managedSystemEditPageRootMenuName;

    @Autowired
    private AuditLogProvider logProvider;

    @Autowired
    @Qualifier("jacksonMapper")
    private ObjectMapper jacksonMapper;
    @Autowired
    PolicyMapReqestValidator policyMapReqestValidator;
    @Value("${org.openiam.ui.idm.synchronization.edit.root.name}")
    private String syncConfigEditRootMenuName;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(policyMapReqestValidator);
    }

    private String handlMenuResource(final HttpServletRequest request,
            final HttpServletResponse response, final String resourceId) {
        final AuthorizationMenu root = authManagerMenuService.getMenuTree(resourceId, getCurrentLanguage());

        String menuTreeString = null;
        try {
            menuTreeString = (root != null) ? jacksonMapper
                    .writeValueAsString(root) : null;
        } catch (Throwable e) {
            log.error("Can't serialize menu tree", e);
        }
        request.setAttribute("editableMenuTree", root);
        request.setAttribute("editableMenuTreeAsString", menuTreeString);
        return "resources/menuTreeView";
    }

    @RequestMapping(value = "/synchPolicyMap", method = RequestMethod.GET)
    public String getsynchPolicyMap(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final @RequestParam(required = false, value = "id") String synchConfigId)
            throws Exception {

        SynchConfig synchConfig = null;
        if (StringUtils.isNotEmpty(synchConfigId)) {
            synchConfig = synchConfigServiceClient.findById(synchConfigId)
                    .getConfig();
        }
        if (synchConfig != null) {
            setMenuTree(request, syncConfigEditRootMenuName);

            AttributeMapSearchBean searchBean = new AttributeMapSearchBean();
            searchBean.setSynchConfigId(synchConfigId);
            List<AttributeMap> attrMap = synchConfigServiceClient
                    .findSynchConfigAttributeMaps(searchBean);
            if (attrMap == null) {
                attrMap = new ArrayList<AttributeMap>();
            }

            request.setAttribute("synchConfig", synchConfig);
            request.setAttribute("attributesMap", attrMap);
            // request.setAttribute("isPrincipalExist",
            // AttributeMapUtil.isPrincipalMissing(attrMap));
            request.setAttribute("policyList", getReferenceData());
            request.setAttribute("defaultReconList",
                    managedSysServiceClient.getAllDefaultReconcileMap());
        }
        return "resources/policyMap";
    }

    @RequestMapping(value = "/policyMap", method = RequestMethod.GET)
    public String getPolicyMap(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final @RequestParam(required = false, value = "id") String managedSysId)
            throws Exception {
        if (StringUtils.isEmpty(managedSysId)) {
            setMenuTree(request, managedSystemEditPageRootMenuName);
        } else {
            ResourceSearchBean searchBean = new ResourceSearchBean();
            ManagedSysDto sysDto = managedSysServiceClient
                    .getManagedSys(managedSysId);
            String resourceId = sysDto.getResourceId();
            searchBean.setKey(resourceId);
            searchBean.setDeepCopy(false);
            List<org.openiam.idm.srvc.res.dto.Resource> resultList = resourceDataService.findBeans(searchBean, 0, 1, getCurrentLanguage());
            if (CollectionUtils.isEmpty(resultList)) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, String
                        .format("Resource with ID: '%s' not found", resourceId));
                return null;
            }
            final org.openiam.idm.srvc.res.dto.Resource resource = resultList
                    .get(0);
            List<String> targetAttrubutesList = null;
            final ManagedSysDto mSys = managedSysServiceClient
                    .getManagedSysByResource(resource.getId());
            targetAttrubutesList = (mSys != null) ? provisionServiceClient
                    .getPolicyMapAttributesList(mSys.getId()) : null;
            HttpSession session = request.getSession(true);
            session.setAttribute("resourceId", resourceId);
            if (StringUtils.equalsIgnoreCase(
                    resource.getResourceType().getId(),
                    AuthorizationConstants.MENU_ITEM_RESOURCE_TYPE)) {
                return handlMenuResource(request, response, resourceId);
            } else {
                AttributeMapSearchBean attrSearchBean = new AttributeMapSearchBean();
                attrSearchBean.setResourceId(resourceId);
                List<AttributeMap> attrMap = managedSysServiceClient
                        .findResourceAttributeMaps(attrSearchBean);
                if (attrMap == null) {
                    attrMap = new ArrayList<AttributeMap>();
                }

                setMenuTree(request, managedSystemEditPageRootMenuName);
                request.setAttribute("targetSystemAttributes",
                        targetAttrubutesList);
                request.setAttribute("resource", resource);
                request.setAttribute("attributesMap", attrMap);
                request.setAttribute("mngSystem", sysDto);
                request.setAttribute("isPrincipalExist",
                        AttributeMapUtil.isPrincipalMissing(attrMap));
                request.setAttribute("policyList", this.getReferenceData());
                request.setAttribute("defaultReconList",
                        managedSysServiceClient.getAllDefaultReconcileMap());
            }
        }
        return "resources/policyMap";
    }

    @RequestMapping(value = "/delete-attribute-map", method = RequestMethod.POST)
    public String deleteAttrMap(final HttpServletRequest request,
            final HttpServletResponse response,
            final @RequestBody PolicyMapRequest policyMap) {
        log.info(policyMap.toString());

        String userId = getRequesterId(request);
        String login = getRequesterPrincipal(request);
        String resName = policyMap.getResourseName();
        String resId = policyMap.getResourceId();
        String synchConfigId = policyMap.getSynchConfigId();
        String synchConfigName = policyMap.getSynchConfigName();
        List<AttributeMap> attrMap = policyMap.getAttrMapList();
        List<String> idsToDelete = new ArrayList<String>();
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        try {
            if (attrMap != null) {

                for (AttributeMap a : attrMap) {
                    if (a.getSelected()) {
                        String mapId = a.getAttributeMapId();
                        if (!mapId.equalsIgnoreCase("NEW")) {
                            idsToDelete.add(mapId);

                            final IdmAuditLog idmAuditLog = new IdmAuditLog();
                            idmAuditLog.setRequestorPrincipal(login);
                            idmAuditLog.setRequestorUserId(userId);
                            idmAuditLog.setAction(AuditAction.DELETE_POLICY_MAP.value());
                            logProvider.add(AuditSource.WEBCONSOLE, request,
                                    idmAuditLog);
                        }
                    }
                }
                managedSysServiceClient.deleteAttributesMapList(idsToDelete);
            }
            ajaxResponse.setStatus(HttpServletResponse.SC_OK);
            ajaxResponse.setSuccessToken(new SuccessToken(
                    SuccessMessage.ATTRIBUTE_MAP_SAVED));
        } catch (Exception e) {
            ajaxResponse
                    .setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ajaxResponse.setErrorList(Arrays.asList(new ErrorToken(e
                    .getMessage())));
        }
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/save-attribute-map", method = RequestMethod.POST)
    public String saveAttrMap(final HttpServletRequest request,
            final HttpServletResponse response,
            final @RequestBody @Valid PolicyMapRequest policyMap) {
        log.info(policyMap.toString());
        String userId = this.getRequesterId(request);
        String login = getRequesterPrincipal(request);
        String resName = policyMap.getResourseName();
        String resId = policyMap.getResourceId();
        String synchConfigId = policyMap.getSynchConfigId();
        String synchConfigName = policyMap.getSynchConfigName();
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        try {
            managedSysServiceClient.saveAttributesMap(
                    policyMap.getAttrMapList(), policyMap.getMangSysId(),
                    resId, synchConfigId);

            final IdmAuditLog idmAuditLog = new IdmAuditLog();
            idmAuditLog.setRequestorPrincipal(login);
            idmAuditLog.setRequestorUserId(userId);
            idmAuditLog.setAction(AuditAction.SAVE_POLICY_MAP.value());
            logProvider.add(AuditSource.WEBCONSOLE, request, idmAuditLog);

            ajaxResponse.setStatus(HttpServletResponse.SC_OK);
            ajaxResponse.setSuccessToken(new SuccessToken(
                    SuccessMessage.ATTRIBUTE_MAP_SAVED));
        } catch (Exception e) {
            ajaxResponse
                    .setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ajaxResponse.setErrorList(Arrays.asList(new ErrorToken(e
                    .getMessage())));
        }
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    protected List<Policy> getReferenceData() throws Exception {
        final PolicySearchBean searchBean = new PolicySearchBean();
        List<SortParam> sortParamList = new ArrayList<>();
        sortParamList.add(new SortParam("name"));
        searchBean.setPolicyDefId(PolicyConstants.ATTRIBUTE_POLICY);
        searchBean.setSortBy(sortParamList);
        return policyDataService.findBeans(searchBean, -1, -1);
    }

    @ModelAttribute(value = "policyMapDataTypeOptions")
    public Map<String, String> approveTypeItems() {
        Map<String, String> ret = new LinkedHashMap<String, String>();
        for (PolicyMapDataTypeOptions o : PolicyMapDataTypeOptions.values()) {
            ret.put(o.name(), o.getValue());
        }
        return ret;
    }

    @ModelAttribute(value = "policyMapObjectTypeOptions")
    public Map<String, String> objectTypeItems() {
        Map<String, String> ret = new LinkedHashMap<String, String>();
        for (PolicyMapObjectTypeOptions o : PolicyMapObjectTypeOptions.values()) {
            ret.put(o.name(), o.name());
        }
        return ret;
    }

    @ModelAttribute(value = "reconResourceTypeOptions")
    public Map<String, String> reconResourceTypeItems() {
        Map<String, String> ret = new LinkedHashMap<String, String>();
        for (ReconResourceTypeOptions o : ReconResourceTypeOptions.values()) {
            ret.put(o.name(), o.getValue());
        }
        return ret;
    }

}
