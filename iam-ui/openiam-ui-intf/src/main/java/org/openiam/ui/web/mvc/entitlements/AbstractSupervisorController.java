package org.openiam.ui.web.mvc.entitlements;

import org.apache.commons.lang.StringUtils;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractSupervisorController extends AbstractEntitlementsController<User> {

	protected abstract String getEditMenu();
	
    @RequestMapping(value = "/supSub", method = RequestMethod.GET)
    public String edit(final HttpServletRequest request, final HttpServletResponse response,
                       final @RequestParam(required = false, value = "id") String userId,
                       final @RequestParam(required = false, value = "type") String type) throws IOException {

        final User user = getEntity(userId, request);
        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, String.format("User with id '%s' does not exist", userId));
            return null;
        }
        request.setAttribute("user", user);
        request.setAttribute("type", StringUtils.isNotBlank(type) ? type : "superiors");
        setMenuTree(request, getEditMenu());
        return "jar:users/supSub";
    }
    
    @RequestMapping(value = "/addSuperior", method = RequestMethod.POST)
    public String addSuperior(final HttpServletRequest request, final @RequestParam(required = true, value = "userId") String userId,
                              final @RequestParam(required = true, value = "supervisorId") String supervisorId) {
    	final BasicAjaxResponse response = doAddSupervisor(request, supervisorId, userId);
    	request.setAttribute("response", response);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/addSubordinate", method = RequestMethod.POST)
    public String addSubordinate(final HttpServletRequest request, final @RequestParam(required = true, value = "userId") String userId,
                                 final @RequestParam(required = true, value = "subordinateId") String subordinateId) {
    	final BasicAjaxResponse response = doAddSupervisor(request, userId, subordinateId);
    	request.setAttribute("response", response);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/removeSuperior", method = RequestMethod.POST)
    public String removeSuperior(final HttpServletRequest request, final @RequestParam(required = true, value = "userId") String userId,
                                 final @RequestParam(required = true, value = "supervisorId") String supervisorId) {
    	final BasicAjaxResponse response = doRemoveSupervisor(request, supervisorId, userId);
    	request.setAttribute("response", response);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/removeSubordinate", method = RequestMethod.POST)
    public String removeSubordinate(final HttpServletRequest request, final @RequestParam(required = true, value = "userId") String userId,
                                    final @RequestParam(required = true, value = "subordinateId") String subordinateId) {
    	final BasicAjaxResponse response = doRemoveSupervisor(request, userId, subordinateId);
    	request.setAttribute("response", response);
        return "common/basic.ajax.response";
    }
    
    @Override
	protected String getRootMenu() {
		return null;
	}

	@Override
	protected User getEntity(String id, HttpServletRequest request) {
		return userDataWebService.getUserWithDependent(id, getRequesterId(request), false);
	}
	
	protected abstract BasicAjaxResponse doAddSupervisor(final HttpServletRequest request, final String userId, final String subordinateId);
	protected abstract BasicAjaxResponse doRemoveSupervisor(final HttpServletRequest request, final String userId, final String subordinateId);
}
