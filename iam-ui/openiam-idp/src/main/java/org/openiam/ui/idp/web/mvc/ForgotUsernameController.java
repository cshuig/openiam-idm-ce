package org.openiam.ui.idp.web.mvc;

import org.openiam.base.ws.Response;
import org.openiam.idm.srvc.audit.constant.AuditAttributeName;
import org.openiam.idm.srvc.audit.dto.IdmAuditLog;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.mvc.AbstractLoginController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ForgotUsernameController extends AbstractLoginController {

    @Value("${org.openiam.forgot.username.redirect.url}")
    private String redirectURL;
	
	@RequestMapping(value="/forgotUsername", method=RequestMethod.GET)
	public String forgotUsername(final HttpServletRequest request,
								 final HttpServletResponse response) throws Exception {
		if(!forgotUsernameEnabled) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		return "core/user/forgotUsername";
	}
	
	@RequestMapping(value="/forgotUsername", method=RequestMethod.POST)
	public String forgotUsernamePOST(final HttpServletRequest request,
			 					     final HttpServletResponse response,
			 					     final @RequestParam(required=true, value="email") String email) throws Exception {
		final IdmAuditLog auditLog = new IdmAuditLog();
    	auditLog.setAction("Forgot Username");
    	auditLog.addAttribute(AuditAttributeName.EMAIL, email);
		
		try {
			if(!forgotUsernameEnabled) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return null;
			}
	
	        Response wsResponce = loginServiceClient.forgotUsername(email);
	        if(wsResponce.isFailure()){
	            Errors error = null;
	            switch(wsResponce.getErrorCode()) {
	                case INVALID_ARGUMENTS:
	                    error = Errors.EMAIL_MISSING;
	                    break;
	                case NO_USER_FOUND_FOR_GIVEN_EMAIL:
	                    error = Errors.USER_NOT_FOUND;
	                    break;
	                default:
	                    error = Errors.INTERNAL_ERROR;
	                    break;
	
	            }
	            auditLog.setFailureReason(getMessage(request, error));
	            request.setAttribute("error", new ErrorToken(Errors.COULD_NOT_INITIATE_REQUEST));
	            auditLog.fail();
	        } else {
	            request.setAttribute("success", new SuccessToken(SuccessMessage.USER_IDENTITY_SENT));
	            request.setAttribute("redirectURL", redirectURL);
	            auditLog.succeed();
	        }
		} finally {
			auditLogService.addLog(auditLog);
		}
        return forgotUsername(request, response);
	}
}
