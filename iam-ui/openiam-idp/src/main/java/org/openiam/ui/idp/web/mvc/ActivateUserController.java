package org.openiam.ui.idp.web.mvc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.exception.ObjectNotFoundException;
import org.openiam.idm.srvc.auth.dto.Login;
import org.openiam.idm.srvc.policy.dto.Policy;
import org.openiam.idm.srvc.pswd.dto.ValidatePasswordResetTokenResponse;
import org.openiam.ui.exception.ErrorMessageException;
import org.openiam.ui.exception.ErrorTokenException;
import org.openiam.ui.idp.web.model.UnlockPasswordFormRequest;
import org.openiam.ui.security.OpeniamWebAuthenticationDetails;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.model.SetPasswordToken;
import org.openiam.ui.web.mvc.AbstractPasswordController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by alexander on 10.04.15.
 */
@Controller
public class ActivateUserController  extends AbstractUserStatusController {
    private static Logger LOG = Logger.getLogger(ActivateUserController.class);

    @RequestMapping(value="/activate", method= RequestMethod.GET)
    public String activateForm(final HttpServletRequest request,
                                     final HttpServletResponse response,
                                     final @RequestParam(required=true, value="token") String token) throws Exception {
        request.setAttribute("token", token);
        request.setAttribute("isActivate", true);
        return passwordResetFormView;
    }

    @RequestMapping(value = "/activate", method = RequestMethod.POST)
    public String activateForm(final HttpServletRequest request,
                                     final HttpServletResponse response,
                                     final UnlockPasswordFormRequest formRequest) throws Exception {
        final String redirectTo = "/selfservice";

        List<ErrorToken> errorList = null;
        String view = null;
        SetPasswordToken token = null;
        try {
            Policy policy = this.getAuthentificationPolicy();
            final Login login =attemptToResetPassword(request, formRequest,policy);

        } catch (ErrorTokenException e) {
            errorList = e.getTokenList();
        } catch (ErrorMessageException e) {
            errorList = new LinkedList<>();
            errorList.add(new ErrorToken(e.getError()));
        } catch (Throwable e) {
            LOG.error("Can't activate user", e);
            errorList = new LinkedList<>();
            errorList.add(new ErrorToken(Errors.INTERNAL_ERROR));
        } finally {
            if (CollectionUtils.isNotEmpty(errorList)) {
                final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
                ajaxResponse.addErrors(errorList);
                if (token != null) {
                    ajaxResponse.setPossibleErrors(token.getRules());
                }
                ajaxResponse.setStatus(500);
                ajaxResponse.process(localeResolver, messageSource, request);
                request.setAttribute("ajaxResponse", jacksonMapper.writeValueAsString(ajaxResponse));
                view = activateForm(request, response, formRequest.getToken());
            } else {
                request.setAttribute("message", SuccessMessage.PASSWORD_CHANGED.getMessageName());
                request.setAttribute("loginTo", redirectTo);
                request.setAttribute("isActivate", true);
                view = "common/message";
            }
        }
        return view;
    }


}
