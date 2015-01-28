package org.openiam.ui.web.mvc;

import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringUtils;
import org.openiam.base.ws.Response;
import org.openiam.idm.srvc.audit.constant.AuditAction;
import org.openiam.idm.srvc.audit.constant.AuditSource;
import org.openiam.idm.srvc.audit.dto.IdmAuditLog;
import org.openiam.idm.srvc.auth.ws.LoginDataWebService;
import org.openiam.idm.srvc.policy.dto.ITPolicy;
import org.openiam.idm.srvc.policy.service.PolicyDataService;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.ui.security.OpeniamWebAuthenticationDetails;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.model.UsePolicyBean;
import org.openiam.ui.web.util.UsePolicyHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class UsePolicyController extends AbstractLoginController {

    @Resource(name = "policyServiceClient")
    private PolicyDataService policyDataService;

    @Resource(name = "loginServiceClient")
    private LoginDataWebService loginDataWebService;

    @RequestMapping("/usePolicy")
    public String usePolicy(final HttpServletRequest request, final HttpServletResponse response, Model model,
                            @RequestParam(value = "postbackUrl", required = false) String postbackUrl) throws IOException {
        final String userId = cookieProvider.getUserId(request);
        final User user = userDataWebService.getUserWithDependent(userId, null, false);
        ITPolicy itPolicy = policyDataService.findITPolicy();
        Boolean status = UsePolicyHelper.getUsePolicyStatus(itPolicy, user);
        if (status != null) {
            request.setAttribute("usePolicy", itPolicy);
            request.setAttribute("itPolicyStatus", status);
            if (StringUtils.isNotBlank(postbackUrl)) {
                request.setAttribute("postbackUrl", postbackUrl);
            }

            model.addAttribute("usePolicyBean", new UsePolicyBean());
            return "jar:user/usePolicy";
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
    }

    @RequestMapping(value = "/usePolicy", method = RequestMethod.POST)
    public String submit(final HttpServletRequest request, final HttpServletResponse response,
                         @RequestParam(value = "accept", required = false) String accept,
                         @RequestParam(value = "decline", required = false) String decline,
                         @RequestParam(value = "postbackUrl", required = false) String postbackUrl) throws IOException {

        final String userId = getRequesterId(request);
        String login = loginDataWebService.getPrimaryIdentity(userId).getPrincipal().getLogin();
        IdmAuditLog idmAuditLog = new IdmAuditLog();
        idmAuditLog.setRequestorUserId(userId);
        idmAuditLog.setTargetUser(userId, login);

        if (StringUtils.isNotBlank(accept) && StringUtils.isBlank(decline)) {
            Response res = userDataWebService.acceptITPolicy(userId);
            idmAuditLog.setAction(AuditAction.CONFIRM_IT_POLICY.value());
            if (res.isFailure()) {
                idmAuditLog.fail();
                idmAuditLog.setFailureReason(res.getErrorCode());
                request.setAttribute("messages", res.getErrorText());
                return "jar:user/usePolicy";

            } else {
                final SecurityContext ctx = SecurityContextHolder.getContext();
                final Authentication authentication = (ctx != null) ? ctx.getAuthentication() : null;
                if (authentication != null && authentication.getDetails() != null) {
                    ((OpeniamWebAuthenticationDetails) authentication.getDetails()).setUsePolicyConfirmed(true);
                    idmAuditLog.succeed();
                    idmAuditLog.setAuditDescription("IT Usage Policy confirmed");
                }
            }
            if (StringUtils.isNotBlank(postbackUrl)) {
                response.setHeader("Location", URIUtil.encodeQuery(postbackUrl));
                response.setStatus(301);
                return null;
            }
        } else {
            idmAuditLog.setAction(AuditAction.DECLINE_IT_POLICY.value());
            idmAuditLog.setAuditDescription("IT Usage Policy declined");
            doLogout(request, response, false);
        }

        auditLogService.addLog(idmAuditLog);
        return "redirect:/";

    }

}