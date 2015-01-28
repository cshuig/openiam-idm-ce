package org.openiam.ui.webconsole.web.mvc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.srvc.audit.constant.AuditAction;
import org.openiam.idm.srvc.audit.constant.AuditAttributeName;
import org.openiam.idm.srvc.audit.constant.AuditSource;
import org.openiam.idm.srvc.audit.dto.IdmAuditLog;
import org.openiam.idm.srvc.org.dto.Organization;
import org.openiam.idm.srvc.policy.dto.*;
import org.openiam.idm.srvc.role.dto.Role;
import org.openiam.ui.dozer.DozerBeanMapper;
import org.openiam.ui.rest.api.model.KeyNameBean;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.webconsole.web.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Controller
public class PasswordPolicyPageController extends AbstractPolicyController {

    @Autowired
    private DozerBeanMapper beanMapper;

    private static final Log log = LogFactory
            .getLog(PasswordPolicyPageController.class);

    @Value("${org.openiam.upload.root}")
    private String uploadRoot;

    @Value("${org.openiam.ui.page.policy.password.edit.menu.id}")
    private String policyEditPageRootMenuName;

    @Value("${org.openiam.ui.page.policy.password.root.id}")
    private String passwordPolicyRootMenuName;

    /**
     * Gets the policies.
     *
     * @param request  the request
     * @param response the response
     * @return the policies
     */
    @RequestMapping("/passwordPolicy")
    public String getPolicies(final HttpServletRequest request, final HttpServletResponse response) {
        super.getPolicies(request, response, PolicyConstants.PASSWORD_POLICY, passwordPolicyRootMenuName, "editPolicy.html", PolicyType.PASSWORD);
        return "policy/policySearch";
    }

    @RequestMapping(value = "/getPasswordAttributes", method = RequestMethod.GET)
    public
    @ResponseBody
    PolicyAttributesModel getPasswordAttributes(final HttpServletRequest request,
                                                final HttpServletResponse response,
                                                final @RequestParam(required = false, value = "id") String policyId) {

        PolicyAttributesModel model = new PolicyAttributesModel();
        if (StringUtils.isBlank(policyId)) {
            final List<PolicyDefParam> allDefaultPolicies = policyServiceClient.getAllPolicyAttributes(PolicyConstants.PASSWORD_POLICY, null);
            for (PolicyDefParam param : allDefaultPolicies) {
                switch (param.getParamGroup()) {
                    case PolicyConstants.PSWD_COMPOSITION:
                        model.getPasswordComposition().add(beanMapper.mapToObject(param, PolicyAttributeBean.class));
                        break;
                    case PolicyConstants.FORGET_PSWD:
                        model.getForgotPassword().add(beanMapper.mapToObject(param, PolicyAttributeBean.class));
                        break;
                    case PolicyConstants.PSWD_CHANGE_RULE:
                        model.getPasswordChangeRule().add(beanMapper.mapToObject(param, PolicyAttributeBean.class));
                        break;
                    default:
                        break;
                }
            }
        } else {
            final Policy policy = policyServiceClient.getPolicy(policyId);
            List<PolicyAttribute> pa = new ArrayList(policy.getPolicyAttributes());
            Collections.sort(pa);
            for (PolicyAttribute param : pa) {
                switch (param.getGrouping()) {
                    case PolicyConstants.PSWD_COMPOSITION:
                        model.getPasswordComposition().add(beanMapper.mapToObject(param, PolicyAttributeBean.class));
                        break;
                    case PolicyConstants.FORGET_PSWD:
                        model.getForgotPassword().add(beanMapper.mapToObject(param, PolicyAttributeBean.class));
                        break;
                    case PolicyConstants.PSWD_CHANGE_RULE:
                        model.getPasswordChangeRule().add(beanMapper.mapToObject(param, PolicyAttributeBean.class));
                        break;
                    default:
                        break;
                }
            }
        }
        return model;
    }

    /**
     * Edits the policy.
     *
     * @param request  the request
     * @param response the response
     * @param policyId the policy id
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @RequestMapping(value = "/editPolicy", method = RequestMethod.GET)
    public String editPolicy(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final @RequestParam(required = false, value = "id") String policyId)
            throws IOException {
        Policy policy = null;
        if (StringUtils.isEmpty(policyId)) {
            // for new policy
            setMenuTree(request, passwordPolicyRootMenuName);
            policy = new Policy();
            policy.setPolicyDefId(PolicyConstants.PASSWORD_POLICY);
            request.setAttribute("policy", policy);
            return "passwordPolicy/editPolicy";
        } else {
            // for edit
            policy = policyServiceClient.getPolicy(policyId);
            setMenuTree(request, policyEditPageRootMenuName);
            request.setAttribute("policy", policy);
            if (policy.getRule() == null) {
                return "passwordPolicy/editPolicy";
            } else {
                return "passwordPolicy/uploadCustomPswdPolicy";
            }
        }

    }

    /**
     * Save policy.
     *
     * @param request       the request
     * @param response      the response
     * @param policyRequest the policy request
     * @return the string
     */
    @RequestMapping(value = "/editPolicy", method = RequestMethod.POST)
    public String savePolicy(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final @RequestBody PolicyRequest policyRequest) {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        //TODO add server validation here
        Response wsResponse = new Response();
        final IdmAuditLog idmAuditLog = new IdmAuditLog();
        idmAuditLog.setAction(AuditAction.PASSWORD_POLICY_CHANGE.value());
        try {
            Policy policyadd = new Policy();
            policyadd.setDescription(policyRequest.getDescription());
            policyadd.setName(policyRequest.getName());
            policyadd.setPolicyAttributes(policyRequest.getPolicyAttributes());
            policyadd.setStatus(policyRequest.getStatus());
            policyadd.setPolicyDefId(PolicyConstants.PASSWORD_POLICY);
            policyadd.setRule(policyRequest.getRule());
            policyadd.setRuleSrcUrl((policyRequest.getRuleSrcUrl() != null) ? policyRequest.getRuleSrcUrl().getOriginalFilename() : null);

            if (StringUtils.isNotBlank(policyRequest.getPolicyId())) {
                policyadd.setPolicyId(policyRequest.getPolicyId());

                wsResponse = policyServiceClient.savePolicy(policyadd);
            } else {

                wsResponse = policyServiceClient.savePolicy(policyadd);
            }
        } catch (Throwable e) {
            log.error("Exception", e);
            idmAuditLog.addAttribute(AuditAttributeName.EXCEPTION, ExceptionUtils.getStackTrace(e));
            idmAuditLog.setFailureReason("Exception");
        }

        try {
            if (ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
                ajaxResponse.setStatus(HttpServletResponse.SC_OK);
                ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.POLICY_SAVED));
                ajaxResponse.setRedirectURL(new StringBuilder("editPolicy.html?id=").append(wsResponse.getResponseValue()).toString());
                idmAuditLog.succeed();
            } else {
                idmAuditLog.fail();
                if (wsResponse.getErrorCode() != null) {
                    idmAuditLog.setFailureReason(wsResponse.getErrorCode().name());
                }
                ajaxResponse.addError(this.handlePolicySaveError(wsResponse));
            }
        } finally {
            auditLogService.addLog(idmAuditLog);
        }

        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
        // return "passwordPolicy/editPolicy";
    }

    /**
     * Delete policy.
     *
     * @param policyId the policy id
     * @param response the response
     * @param request  the request
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @RequestMapping(value = "/deletePolicy", method = RequestMethod.POST)
    public String deletePolicy(
            final @RequestParam(value = "id") String policyId,
            final HttpServletResponse response, final HttpServletRequest request)
            throws IOException {

        final BasicAjaxResponse ajaxResponse = deletePolicy(policyId, "passwordPolicy.html");
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    /**
     * Gets the assoc password policies.
     *
     * @param request  the request
     * @param response the response
     * @param policyId the policy id
     * @return the assoc password policies
     */
    @RequestMapping(value = "/assocPasswordPolicy", method = RequestMethod.GET)
    public String getAssocPasswordPolicies(final HttpServletRequest request,
                                           final HttpServletResponse response,
                                           final @RequestParam(value = "id") String policyId) {

        List<PolicyObjectAssoc> assocList = policyServiceClient
                .getAssociationsForPolicy(policyId);

        PolicyObjectAssoc policyAssociation = new PolicyObjectAssoc();
        if (assocList != null && !assocList.isEmpty()) {
            policyAssociation = assocList.get(0);
        } else {
            policyAssociation.setPolicyId(policyId);
        }
        try {
            if (policyAssociation.getObjectId() != null) {
                if ("ORGANIZATION".equals(policyAssociation.getObjectType())) {
                    Organization org = organizationDataService.getOrganizationLocalized(policyAssociation.getObjectId(), this.getRequesterId(request), this.getCurrentLanguage());
                    if (org != null) {
                        request.setAttribute("organization", jacksonMapper.writeValueAsString(mapper.mapToObject(org, KeyNameBean.class)));
                    }
                } else if ("ROLE".equals(policyAssociation.getObjectType())) {
                    Role role = roleServiceClient.getRoleLocalized(policyAssociation.getObjectId(), this.getRequesterId(request), this.getCurrentLanguage());
                    if (role != null) {
                        request.setAttribute("role", jacksonMapper.writeValueAsString(mapper.mapToObject(role, KeyNameBean.class)));
                    }
                }
            }
        } catch (Exception exp) {
            log.error(exp);
        }

        request.setAttribute("policyAssociation", policyAssociation);
        return "passwordPolicy/assocPasswordPolicy";
    }

    /**
     * Save policy association.
     *
     * @param request                  the request
     * @param response                 the response
     * @param policyobjectAssocRequest the policyobject assoc request
     * @return the string
     */
    @RequestMapping(value = "/savePolicyAssociation", method = RequestMethod.POST)
    public String savePolicyAssociation(final HttpServletRequest request,
                                        final HttpServletResponse response,
                                        final @RequestBody PolicyObjectAssocRequest policyobjectAssocRequest) {

        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();

        PolicyObjectAssoc policyObjectAssoc = new PolicyObjectAssoc();

        policyObjectAssoc.setAssociationLevel(policyobjectAssocRequest
                .getAssociationLevel());
        policyObjectAssoc.setAssociationValue(policyobjectAssocRequest
                .getAssociationValue());
        policyObjectAssoc.setObjectId(policyobjectAssocRequest.getObjectId());
        policyObjectAssoc.setObjectType(policyobjectAssocRequest
                .getObjectType());
        policyObjectAssoc.setParentAssocId(policyobjectAssocRequest
                .getParentAssocId());
        policyObjectAssoc.setPolicyId(policyobjectAssocRequest.getPolicyId());
        policyObjectAssoc.setPolicyObjectId(policyobjectAssocRequest
                .getPolicyObjectId());

        final Response wsResponse = policyServiceClient
                .savePolicyAssoc(policyObjectAssoc);

        if (ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
            ajaxResponse.setStatus(200);
            ajaxResponse.setSuccessToken(new SuccessToken(
                    SuccessMessage.POLICY_SAVED));
            // ajaxResponse.setRedirectURL("assocPasswordPolicy.html");
        } else {
            ajaxResponse.addError(new ErrorToken(Errors.INTERNAL_ERROR));
        }
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
        // return "passwordPolicy/assocPasswordPolicy";
    }

    /**
     * Custom pswd policy.
     *
     * @param request  the request
     * @param response the response
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @RequestMapping(value = "/uploadCustomPswdPolicy", method = RequestMethod.GET)
    public String customPswdPolicy(final HttpServletRequest request,
                                   final HttpServletResponse response) throws IOException {
        String policyId = (String) request.getAttribute("policyId");
        if (StringUtils.isEmpty(policyId)) {
            // for new policy
            setMenuTree(request, passwordPolicyRootMenuName);

        } else {
            // for edit
            final Policy policy = policyServiceClient.getPolicy(policyId);

            setMenuTree(request, policyEditPageRootMenuName);

            request.setAttribute("policy", policy);
        }

        return "passwordPolicy/uploadCustomPswdPolicy";
    }

    @RequestMapping(value = "/customPswdPolicy", method = RequestMethod.POST)
    public String saveCustomPswdPolicy(final HttpServletRequest request,
                                       final HttpServletResponse response,
                                       final PolicyRequest policyRequest) {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();

        ErrorToken errorToken = null;
        SuccessToken successToken = null;
        try {

            Policy policyadd = new Policy();
            policyadd.setDescription(policyRequest.getDescription());
            policyadd.setName(policyRequest.getName());
            policyadd.setPolicyAttributes(policyRequest.getPolicyAttributes());
            policyadd.setStatus(policyRequest.getStatus());
            policyadd.setPolicyDefId(PolicyConstants.PASSWORD_POLICY);
            policyadd.setRule(policyRequest.getRule());

            MultipartFile file = policyRequest.getRuleSrcUrl();
            if (file != null && !file.isEmpty()) {
                BufferedReader reader = null;
                BufferedWriter writer = null;
                try {
                    String uploadPath = genUploadPath(file
                            .getOriginalFilename());
                    FileUtils.forceMkdir(new File(FilenameUtils
                            .getFullPath(uploadPath)));

                    reader = new BufferedReader(new InputStreamReader(
                            file.getInputStream()));
                    writer = new BufferedWriter(new OutputStreamWriter(
                            new FileOutputStream(new File(uploadPath))));

                    String row;
                    while ((row = reader.readLine()) != null) {
                        writer.write(row);
                        writer.newLine(); // Writes OS dependent EOL
                    }

                    reader.close();
                    writer.close();

                    policyadd.setRuleSrcUrl(FilenameUtils.getName(uploadPath));

                } catch (IOException e) {
                    log.error("Exception", e);
                } finally {
                    IOUtils.closeQuietly(reader);
                    IOUtils.closeQuietly(writer);
                }
            }
            if (errorToken == null) {
                Response wsResponse = null;
                if (StringUtils.isNotBlank(policyRequest.getPolicyId())) {
                    policyadd.setPolicyId(policyRequest.getPolicyId());

                    wsResponse = policyServiceClient.savePolicy(policyadd);
                } else {

                    wsResponse = policyServiceClient.savePolicy(policyadd);
                    if (wsResponse.getResponseValue() != null)
                        request.setAttribute("policyId", wsResponse
                                .getResponseValue().toString());

                }

                if (wsResponse.getStatus() == ResponseStatus.SUCCESS) {
                    request.setAttribute("policy", policyadd);
                    successToken = new SuccessToken(
                            SuccessMessage.POLICY_SAVED);
                } else {
                    Errors error = Errors.EMPTY_POLICY_MAP;

                    if (wsResponse.getErrorCode() != null) {
                        switch (wsResponse.getErrorCode()) {
                            case POLICY_NAME_NOT_SET:
                                error = Errors.INVALID_POLICY_NAME;
                                break;
                            case NAME_TAKEN:
                                error = Errors.POLICY_NAME_TAKEN;
                                break;
                            case RULE_NOT_SET:
                                error = Errors.INVALID_RULE;
                                break;

                            default:
                        }
                    }
                    errorToken = new ErrorToken(error);

                }
            }
        } catch (Throwable e) {
            errorToken = new ErrorToken(Errors.INTERNAL_ERROR);
            log.error("Exception while saving password policy", e);
        } finally {
            request.setAttribute("errorToken", errorToken);
            request.setAttribute("successToken", successToken);

            return "passwordPolicy/uploadCustomPswdPolicy";
        }
    }

    private String genUploadPath(String fileName) {
        String name = uploadRoot + "/passwordpolicy/"
                + FilenameUtils.getBaseName(fileName) + "_"
                + new Date().getTime() + FilenameUtils.EXTENSION_SEPARATOR
                + FilenameUtils.getExtension(fileName);
        return name;
    }
}
