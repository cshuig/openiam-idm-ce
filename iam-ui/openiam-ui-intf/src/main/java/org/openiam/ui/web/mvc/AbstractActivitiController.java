package org.openiam.ui.web.mvc;

import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.bpm.activiti.ActivitiService;
import org.openiam.bpm.request.ActivitiRequestDecision;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by: Alexander Duckardt
 * Date: 8/27/14.
 */
public class AbstractActivitiController extends AbstractController {
    @Resource(name="activitiClient")
    protected ActivitiService activitiService;

    protected BasicAjaxResponse doDecision(final HttpServletRequest request,
                                           final HttpServletResponse response,
                                           final ActivitiRequestDecision decision){

        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();

        decision.setRequestorUserId(cookieProvider.getUserId(request));
        final Response wsResponse = activitiService.makeDecision(decision);
        Errors error = null;
        try {
            if(wsResponse == null) {
                error = Errors.INTERNAL_ERROR;
            }
            if(wsResponse.getStatus() == ResponseStatus.FAILURE) {
                error = Errors.INTERNAL_ERROR;
                if(wsResponse.getErrorCode() != null) {
                    switch(wsResponse.getErrorCode()) {
                        default:
                            break;
                    }
                }
            }
        } finally {
            if(error != null) {
                ajaxResponse.addError(new ErrorToken(error));
                ajaxResponse.setStatus(500);
            } else {
                ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.TASK_COMPLETED));
                ajaxResponse.setRedirectURL("myTasks.html");
                ajaxResponse.setStatus(200);
            }
            ajaxResponse.process(localeResolver, messageSource, request);
        }
        return ajaxResponse;
    }
}
