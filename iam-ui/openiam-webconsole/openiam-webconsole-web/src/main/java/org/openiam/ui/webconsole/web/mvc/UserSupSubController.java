package org.openiam.ui.webconsole.web.mvc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.openiam.base.AttributeOperationEnum;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseCode;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.provision.dto.ProvisionUser;
import org.openiam.ui.util.WSUtils;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.mvc.entitlements.AbstractSupervisorController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

@Controller
public class UserSupSubController extends AbstractSupervisorController {

    @Value("${org.openiam.ui.landingpage.user.edit.root.id}")
    protected String userMenu;
    
	@Override
	protected String getEditMenu() {
		return userMenu;
	}

    private BasicAjaxResponse handleModifySupSubRequest(HttpServletRequest request, String supervisorId, String userId, AttributeOperationEnum operation) {
        Response wsResponse;
        String requesterId = this.getRequesterId(request);
        if (provisionServiceFlag) {
            final User superior = userDataWebService.getUserWithDependent(supervisorId, requesterId, true);
            final User user = userDataWebService.getUserWithDependent(userId, requesterId, true);

            if (superior == null || user == null) {
                wsResponse = new Response();
                wsResponse.fail();
                wsResponse.setErrorCode(ResponseCode.UNAUTHORIZED);
            } else {
                final ProvisionUser pUser = new ProvisionUser(user);
                pUser.setRequestorUserId(getRequesterId(request));
                List<User> superiors = userDataWebService.getSuperiors(userId, -1, -1);
                superior.setOperation(operation);
                pUser.addSuperior(superior);

                pUser.addSuperiors(superiors);
                WSUtils.setWSClientTimeout(provisionService, 360000L);
                wsResponse = provisionService.modifyUser(pUser);
            }
        } else {
            wsResponse = userDataWebService.addSuperior(supervisorId, userId, requesterId);
        }

        return getResponseAfterEntity2EntityAddition(wsResponse, AttributeOperationEnum.DELETE == operation);
    }

	@Override
	protected BasicAjaxResponse doAddSupervisor(final HttpServletRequest request, final String userId, final String subordinateId) {
		return handleModifySupSubRequest(request, userId, subordinateId, AttributeOperationEnum.ADD);
	}

	@Override
	protected BasicAjaxResponse doRemoveSupervisor(final HttpServletRequest request, final String userId, final String subordinateId) {
		return handleModifySupSubRequest(request, userId, subordinateId, AttributeOperationEnum.DELETE);
	}
}
