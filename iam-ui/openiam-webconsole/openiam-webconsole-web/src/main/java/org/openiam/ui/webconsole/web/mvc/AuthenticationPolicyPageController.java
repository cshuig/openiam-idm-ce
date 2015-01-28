package org.openiam.ui.webconsole.web.mvc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.srvc.policy.dto.Policy;
import org.openiam.idm.srvc.policy.dto.PolicyAttribute;
import org.openiam.idm.srvc.policy.dto.PolicyConstants;
import org.openiam.idm.srvc.policy.dto.PolicyDefParam;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.webconsole.web.model.PolicyRequest;
import org.openiam.ui.webconsole.web.model.PolicyType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@Controller
public class AuthenticationPolicyPageController extends AbstractPolicyController {

    private static final Log log = LogFactory.getLog(PasswordPolicyPageController.class);

    @Value("${org.openiam.ui.page.policy.auth.root.id}")
    private String authenticationPolicyRootMenuName;

    @RequestMapping("/authenticationPolicy")
    public String getPolicies(final HttpServletRequest request, final HttpServletResponse response) {
        super.getPolicies(request, response, PolicyConstants.AUTHENTICATION_POLICY, authenticationPolicyRootMenuName, "editAuthenticationPolicy.html", PolicyType.AUTHENTICATION);
        return "policy/policySearch";
    }

    @RequestMapping(value = "/editAuthenticationPolicy", method = RequestMethod.GET)
    public String editAuthenticationPolicy(final HttpServletRequest request,
                                           final HttpServletResponse response,
                                           final @RequestParam(required = false, value = "id") String policyId)
            throws IOException {
        Policy policy = new Policy();
        policy.setPolicyDefId(PolicyConstants.ATTRIBUTE_POLICY);
        final List<PolicyDefParam> policyAttrList = policyServiceClient.getAllPolicyAttributes(PolicyConstants.AUTHENTICATION_POLICY, null);
        if (StringUtils.isEmpty(policyId)) {
            setMenuTree(request, authenticationPolicyRootMenuName);
            if (CollectionUtils.isNotEmpty(policyAttrList)) {
                for (final PolicyDefParam param : policyAttrList) {
                    policy.addPolicyAttribute(createAttribute(param));
                }
            }
        } else {
            policy = policyServiceClient.getPolicy(policyId);
            if (policy == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return null;
            }

            if (CollectionUtils.isNotEmpty(policyAttrList)) {
                for (final PolicyDefParam param : policyAttrList) {
                    if (CollectionUtils.isEmpty(policy.getPolicyAttributes())) {
                        policy.addPolicyAttribute(createAttribute(param));
                    } else {
                        boolean contains = false;
                        for (final PolicyAttribute attribute : policy.getPolicyAttributes()) {
                            if (StringUtils.equals(attribute.getDefParamId(), param.getDefParamId())) {
                                contains = true;
                            }
                        }

                        if (!contains) {
                            policy.addPolicyAttribute(createAttribute(param));
                        }
                    }
                }
            }

            setMenuTree(request, authenticationPolicyRootMenuName);
        }

        final List<PolicyAttribute> attributes = new LinkedList<>(policy.getPolicyAttributes());
        Collections.sort(attributes, new PolicyAttributeComparator());
        request.setAttribute("policy", policy);
        request.setAttribute("policyAttributes", attributes);
        request.setAttribute("policyAttrList", policyAttrList);
        request.setAttribute("policyAsJSON", jacksonMapper.writeValueAsString(policy));
        request.setAttribute("managedSystems", getManagedSystemsAsKeyNameBeans());
        return "authenticationPolicy/editAuthenticationPolicy";
    }

    private PolicyAttribute createAttribute(final PolicyDefParam param) {
        final PolicyAttribute attribute = new PolicyAttribute();
        attribute.setDefParamId(param.getDefParamId());
        attribute.setName(param.getName());
        //attribute.setPolicyAttrId(policyAttrId);
//		attribute.setValue1(param.getValue1());
//		attribute.setValue2(param.getValue2());
        return attribute;
    }

    @RequestMapping(value = "/editAuthenticationPolicy", method = RequestMethod.POST)
    public
    @ResponseBody
    BasicAjaxResponse savePolicy(final HttpServletRequest request,
                                 final HttpServletResponse response,
                                 final @RequestBody Policy policy) {

        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        policy.setPolicyDefId(PolicyConstants.AUTHENTICATION_POLICY);
        final Response wsResponse = policyServiceClient.savePolicy(policy);

        if (wsResponse.isSuccess()) {
            ajaxResponse.setStatus(HttpServletResponse.SC_OK);
            ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.POLICY_SAVED));
            ajaxResponse.setRedirectURL(String.format("editAuthenticationPolicy.html?id=%s", wsResponse.getResponseValue()));
        } else {

            if (wsResponse.getErrorCode() == null) {
                ajaxResponse.addError(new ErrorToken(Errors.INTERNAL_ERROR));
            } else {
                switch (wsResponse.getErrorCode()) {
                    case POLICY_NAME_NOT_SET:
                        ajaxResponse.addError(new ErrorToken(Errors.INVALID_POLICY_NAME));
                        break;
                    case NAME_TAKEN:
                        ajaxResponse.addError(new ErrorToken(Errors.POLICY_NAME_TAKEN));
                        break;
                    default:
                        ajaxResponse.addError(new ErrorToken(Errors.INTERNAL_ERROR));
                        break;
                }
            }

        }

        ajaxResponse.process(localeResolver, messageSource, request);
        return ajaxResponse;
    }

    @RequestMapping(value = "/deleteAuthenticationPolicy", method = RequestMethod.POST)
    public
    @ResponseBody
    BasicAjaxResponse deletePolicy(final @RequestParam(value = "id") String policyId,
                                   final HttpServletResponse response,
                                   final HttpServletRequest request) throws IOException {
        final BasicAjaxResponse ajaxResponse = deletePolicy(policyId, "authenticationPolicy.html");
        ajaxResponse.process(localeResolver, messageSource, request);
        return ajaxResponse;
    }

    private class PolicyAttributeComparator implements Comparator<PolicyAttribute> {

        @Override
        public int compare(PolicyAttribute o1, PolicyAttribute o2) {
            if (o1 == null && o2 == null) {
                return 0;
            } else if (o1 != null && o2 == null) {
                return 1;
            } else if (o1 == null && o2 != null) {
                return -1;
            } else {
                if (StringUtils.isBlank(o1.getName()) && StringUtils.isBlank(o2.getName())) {
                    return 0;
                } else if (StringUtils.isNotBlank(o1.getName()) && StringUtils.isBlank(o2.getName())) {
                    return 1;
                } else if (StringUtils.isBlank(o2.getName()) && StringUtils.isNotBlank(o2.getName())) {
                    return -1;
                } else {
                    return o1.getName().compareTo(o2.getName());
                }
            }
        }

    }
}
