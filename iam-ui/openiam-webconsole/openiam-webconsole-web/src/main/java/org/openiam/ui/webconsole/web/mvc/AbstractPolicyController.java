package org.openiam.ui.webconsole.web.mvc;

import org.openiam.authmanager.service.AuthorizationManagerMenuWebService;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.srvc.policy.service.PolicyDataService;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.mvc.AbstractController;
import org.openiam.ui.webconsole.web.model.PolicyType;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractPolicyController extends AbstractController {

    @Resource(name = "authorizationManagerMenuServiceClient")
    protected AuthorizationManagerMenuWebService authManagerMenuService;

    @Resource(name = "policyServiceClient")
    protected PolicyDataService policyServiceClient;

    protected void getPolicies(final HttpServletRequest request,
                               final HttpServletResponse response,
                               final String policyDefId,
                               final String menuTreeRoot,
                               final String policyPageURL,
                               final PolicyType policyType) {


        request.setAttribute("policyType", policyType);
        request.setAttribute("policyPageURL", policyPageURL);
        request.setAttribute("policyDefId", policyDefId);
        setMenuTree(request, menuTreeRoot);
    }

    public BasicAjaxResponse deletePolicy(final String policyId,
                                          final String redirectURL) {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        final Response wsResponse = policyServiceClient.deletePolicy(policyId);

        if (ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {

            ajaxResponse.setStatus(200);
            ajaxResponse.setRedirectURL(redirectURL);
            ajaxResponse.setSuccessToken(new SuccessToken(
                    SuccessMessage.POLICY_DELETED));
        } else {
            ajaxResponse.addError(new ErrorToken(Errors.DELETE_POLICY_FAIL));
        }
        return ajaxResponse;
    }

    protected ErrorToken handlePolicySaveError(Response wsResponse) {
        Errors error = Errors.SAVE_POLICY_FAIL;
        if (wsResponse.getErrorCode() != null) {
            switch (wsResponse.getErrorCode()) {
                case POLICY_ATTRIBUTES_EMPTY_VALUE:
                    error = Errors.POLICY_ATTRIBUTE_EMPTY_VALUE;
                    break;
                case INVALID_VALUE:
                    error = Errors.INVALID_VALUE_POLICY_ATTRIBUTE;
                    break;
                case POLICY_NAME_NOT_SET:
                    error = Errors.INVALID_POLICY_NAME;
                    break;
                case NAME_TAKEN:
                    error = Errors.POLICY_NAME_TAKEN;
                    break;
                default:
                    break;
            }
        }
        return new ErrorToken(error);
    }
}
