package org.openiam.ui.webconsole.web.mvc;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.searchbeans.PolicySearchBean;
import org.openiam.idm.srvc.policy.dto.Policy;
import org.openiam.idm.srvc.policy.dto.PolicyConstants;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.webconsole.web.model.AttributePolicyCommand;
import org.openiam.ui.webconsole.web.model.PolicyBean;
import org.openiam.ui.webconsole.web.model.PolicySearchResultBean;
import org.openiam.ui.webconsole.web.model.PolicyType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AttributePolicyPageController extends AbstractPolicyController {

    private static final Log log = LogFactory
            .getLog(AttributePolicyPageController.class);

    @Value("${org.openiam.ui.page.policy.attribute.edit.menu.id}")
    private String attributeEditPageRootMenuName;

    @Value("${org.openiam.ui.page.policy.attribute.root.id}")
    private String attributePolicyRootMenuName;

    @RequestMapping("/attributePolicy")
    public String getPolicies(final HttpServletRequest request, final HttpServletResponse response) {
        super.getPolicies(request, response, PolicyConstants.ATTRIBUTE_POLICY, attributePolicyRootMenuName, "editAttributePolicy.html", PolicyType.ATTRIBUTE);
        return "policy/policySearch";
    }

    @RequestMapping(value = "/editAttributePolicy", method = RequestMethod.GET)
    public String editAttributePolicy(final HttpServletRequest request,
                                      final HttpServletResponse response,
                                      final @RequestParam(required = false, value = "id") String id) throws IOException {
        Policy policy = null;
        if (StringUtils.isNotBlank(id)) {
            policy = policyServiceClient.getPolicy(id);
            if (policy == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return null;
            }
        } else {
            policy = new Policy();
        }
        request.setAttribute("policy", policy);
        setMenuTree(request, attributeEditPageRootMenuName);
        return "attributePolicy/edit";
    }

    @RequestMapping(value = "/editAttributePolicy", method = RequestMethod.POST)
    public String saveAttributePolicy(final HttpServletRequest request, @RequestBody Policy policy) {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        policy.setPolicyDefId(PolicyConstants.ATTRIBUTE_POLICY);
        final Response wsResponse = policyServiceClient.savePolicy(policy);
        if (ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
            ajaxResponse.setRedirectURL("editAttributePolicy.html?id=" + (String) wsResponse.getResponseValue());
            ajaxResponse.setStatus(HttpServletResponse.SC_OK);
            ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.POLICY_SAVED));
        } else {
            ajaxResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ajaxResponse.addError(handlePolicySaveError(wsResponse));
        }
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/deleteAttributePolicy", method = RequestMethod.POST)
    public String deletePolicy(
            final @RequestParam(value = "id") String policyId,
            final HttpServletResponse response, final HttpServletRequest request)
            throws IOException {

        final BasicAjaxResponse ajaxResponse = deletePolicy(policyId, "attributePolicy.html");
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }
}
