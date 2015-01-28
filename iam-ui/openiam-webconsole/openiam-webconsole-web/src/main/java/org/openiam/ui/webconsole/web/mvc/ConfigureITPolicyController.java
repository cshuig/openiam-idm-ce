package org.openiam.ui.webconsole.web.mvc;

import org.openiam.base.ws.Response;
import org.openiam.idm.srvc.audit.constant.AuditAction;
import org.openiam.idm.srvc.audit.constant.AuditSource;
import org.openiam.idm.srvc.audit.dto.IdmAuditLog;
import org.openiam.idm.srvc.audit.ws.IdmAuditLogWebDataService;
import org.openiam.idm.srvc.policy.dto.ITPolicyApproveType;
import org.openiam.idm.srvc.policy.service.PolicyDataService;
import org.openiam.ui.audit.AuditLogProvider;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.webconsole.web.model.ITPolicyBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.openiam.idm.srvc.policy.dto.ITPolicy;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.openiam.base.ws.ResponseStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

@Controller
public class ConfigureITPolicyController extends AbstractWebconsoleController {

    @Resource(name = "policyServiceClient")
    private PolicyDataService policyDataService;

    @Resource(name = "auditServiceClient")
    private IdmAuditLogWebDataService auditServiceClient;
    
    @Autowired
    private AuditLogProvider logProvider;

    @RequestMapping(value = "/configurePolicy", method = RequestMethod.GET)
    public String edit(Model model) {
        ITPolicyBean itPolicyBean;
        ITPolicy itPolicy = policyDataService.findITPolicy();
        if (itPolicy != null) {
            itPolicyBean =  mapper.mapToObject(itPolicy, ITPolicyBean.class);
        } else {
            itPolicyBean = new ITPolicyBean();
        }
        model.addAttribute("itPolicyBean", itPolicyBean);
        return "configurePolicy/edit";
    }

    @RequestMapping(value = "/saveITPolicy", method= RequestMethod.POST)
    public String saveITPolicy(final HttpServletRequest request,
                               @RequestBody final ITPolicyBean itPolicyBean) {

        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();

        ITPolicy itPolicy = mapper.mapToObject(itPolicyBean, ITPolicy.class);
        String requesterId = getRequesterId(request);
        Date date = new Date();
        ITPolicy found = policyDataService.findITPolicy();
        if (found == null) {
            itPolicy.setCreatedBy(requesterId);
            itPolicy.setCreateDate(date);
        } else {
            itPolicy.setPolicyId(found.getPolicyId());
        }
        itPolicy.setUpdatedBy(requesterId);
        itPolicy.setUpdateDate(date);
        Response wsResponse = policyDataService.saveOrUpdateITPolicy(itPolicy);

        if(ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
            ajaxResponse.setStatus(200);
            ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.IT_POLICY_SAVED));

        } else {
            if(wsResponse.getErrorCode() == null) {
                ajaxResponse.addError(new ErrorToken(Errors.INTERNAL_ERROR));
            } else {
                switch(wsResponse.getErrorCode()) {
                    case IT_POLICY_EXISTS:
                        ajaxResponse.addError(new ErrorToken(Errors.IT_POLICY_EXISTS_ERR));
                        break;
                }
            }
        }

        final IdmAuditLog idmAuditLog = new IdmAuditLog();
        idmAuditLog.setAction(AuditAction.SAVE_IT_POLICY.value());
        idmAuditLog.succeed();
        idmAuditLog.setRequestorUserId(requesterId);
        idmAuditLog.setRequestorPrincipal(getRequesterPrincipal(request));
        logProvider.add(AuditSource.WEBCONSOLE, request, idmAuditLog);
        
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/resetITPolicy", method = RequestMethod.POST)
    public String reset(final HttpServletRequest request) {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        final ITPolicy found = policyDataService.findITPolicy();
        if (found != null) {
            final Response wsResponse = policyDataService.resetITPolicy();
            if(ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
                ajaxResponse.setStatus(200);
                ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.IT_POLICY_RESET));
            } else {
                ajaxResponse.addError(new ErrorToken(Errors.INTERNAL_ERROR));
            }
            
            final IdmAuditLog idmAuditLog = new IdmAuditLog();
            idmAuditLog.setAction(AuditAction.RESET_IT_POLICY.value());
            idmAuditLog.succeed();
            idmAuditLog.setRequestorUserId(getRequesterId(request));
            idmAuditLog.setRequestorPrincipal(getRequesterPrincipal(request));
            logProvider.add(AuditSource.WEBCONSOLE, request, idmAuditLog);
            
            request.setAttribute("response", ajaxResponse);
        } else {
            ajaxResponse.addError(new ErrorToken(Errors.INTERNAL_ERROR));
        }
        return "common/basic.ajax.response";
    }

    @ModelAttribute(value = "approveTypeItems")
    public Map<String, String> approveTypeItems() {
        Map <String, String> ret = new LinkedHashMap<String, String>();
        for (ITPolicyApproveType type : ITPolicyApproveType.values()) {
            ret.put(type.name(), type.getValue());
        }
        return ret;
    }

    @ModelAttribute("statusItems")
    public Map<Boolean, String> statusItems(final HttpServletRequest request) {
        Locale locale = localeResolver.resolveLocale(request);
        Map<Boolean, String> ret = new LinkedHashMap<Boolean, String>();
        ret.put(true, messageSource.getMessage("openiam.ui.report.subscription.status.active", null, "Active", locale));
        ret.put(false, messageSource.getMessage("openiam.ui.report.subscription.status.inactive", null, "Inactive", locale));
        return ret;
    }

    public IdmAuditLog toLog(String action, String domainId, String principal,
                             String srcSystem, String userId, String targetSystem,
                             String objectType, String objectId, String objectName,
                             String actionStatus, String linkedLogId, String attrName,
                             String attrValue, String requestId, String reason,
                             String sessionId, String reasonDetail) {

        IdmAuditLog log = new IdmAuditLog();
        /*
        log.setObjectId(objectId);
        log.setObjectTypeId(objectType);
        log.setActionId(action);
        log.setActionStatus(actionStatus);
        log.setDomainId(domainId);
        log.setUserId(userId);
        log.setPrincipal(principal);
        log.setLinkedLogId(linkedLogId);
        log.setSrcSystemId(srcSystem);
        log.setTargetSystemId(targetSystem);
        log.updateCustomRecord(attrName, attrName, 1,
                CustomIdmAuditLogType.ATTRIB);
        log.setRequestId(requestId);
        log.setReason(reason);
        log.setSessionId(sessionId);
        log.setReasonDetail(reasonDetail);
        */
        return log;
    }

}
