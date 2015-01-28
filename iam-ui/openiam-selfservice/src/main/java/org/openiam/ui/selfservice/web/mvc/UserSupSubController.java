package org.openiam.ui.selfservice.web.mvc;

import javax.servlet.http.HttpServletRequest;

import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseCode;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.bpm.request.GenericWorkflowRequest;
import org.openiam.bpm.util.ActivitiConstants;
import org.openiam.bpm.util.ActivitiRequestType;
import org.openiam.idm.srvc.mngsys.domain.AssociationType;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.mvc.entitlements.AbstractSupervisorController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

@Controller
public class UserSupSubController extends AbstractSupervisorController {

	@Value("${org.openiam.selfservice.activiti.user.menu}")
	private String userMenu;
	
	@Override
	protected String getEditMenu() {
		return userMenu;
	}

	@Override
	protected BasicAjaxResponse doAddSupervisor(final HttpServletRequest request, final String userId, final String subordinateId) {
		final User entity = getEntity(userId, request);
		final User member = getEntity(subordinateId, request);
		return makeMembershipRequest(request, entity, member, true);
	}	

	@Override
	protected BasicAjaxResponse doRemoveSupervisor(final HttpServletRequest request, final String userId, final String subordinateId) {
		final User entity = getEntity(userId, request);
		final User member = getEntity(subordinateId, request);
		return makeMembershipRequest(request, entity, member, false);
	}
}
