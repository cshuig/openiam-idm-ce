package org.openiam.ui.webconsole.web.mvc;

import org.apache.commons.lang.StringUtils;
import org.openiam.base.ws.Response;
import org.openiam.idm.srvc.audit.constant.AuditAction;
import org.openiam.idm.srvc.audit.constant.AuditSource;
import org.openiam.idm.srvc.audit.dto.IdmAuditLog;
import org.openiam.idm.srvc.org.dto.Organization;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.mvc.entitlements.AbstractOrganizationController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class OrganizationController extends AbstractOrganizationController {
	
	@Value("${org.openiam.ui.organization.root.menu.id}")
	private String organizationRootMenuName;
	
	@Value("${org.openiam.ui.organization.edit.menu.id}")
	private String organizationEditMenuName;

	@Override
	protected String getRootMenu() {
		return organizationRootMenuName;
	}

	@Override
	protected String getEditMenu() {
		return organizationEditMenuName;
	}

	@RequestMapping(value="/organizationMembership", method=RequestMethod.GET)
	public String editOrganization(final HttpServletRequest request,
						   		   final HttpServletResponse response,
						   		   final @RequestParam(required=true,value="id") String organizationId,
						   		   @RequestParam(required=false,value="type") String type) throws IOException {
			
		final Organization organization = organizationDataService.getOrganizationLocalized(organizationId, this.getRequesterId(request), getCurrentLanguage());
		if(organization == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, String.format("Organization with ID: '%s' not found", organizationId));
			return null;
		}
		
		if(type == null) {
			type = "childorganizations";
		}
		
		request.setAttribute("type", type);
		request.setAttribute("organization", organization);
		setMenuTree(request, organizationEditMenuName);
		return "organization/organizationMembership";
	}
	
	@Override
	protected BasicAjaxResponse doEdit(HttpServletRequest request,
			HttpServletResponse response, Organization organization) throws Exception {
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();

		final Response wsResponse = organizationDataService.saveOrganization(organization, getRequesterId(request));
		if(wsResponse.isSuccess()) {
            String orgId = (String) wsResponse.getResponseValue();

			ajaxResponse.setStatus(200);
			ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.ORGANIZTION_SAVED));
			if(StringUtils.isBlank(organization.getId())) {
				ajaxResponse.setRedirectURL(new StringBuilder("editOrganization.html?id=").append(wsResponse.getResponseValue()).toString());
			}
		} else {
			ajaxResponse.setErrorList(getEditErrors(wsResponse, request, organization));
			ajaxResponse.setStatus(500);
		}
		return ajaxResponse;
	}

	@Override
	protected BasicAjaxResponse doDelete(HttpServletRequest request,
			HttpServletResponse response, Organization entity) throws Exception {
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        final String callerId = getRequesterId(request);

		final Response wsResponse = organizationDataService.deleteOrganization(entity.getId());
		if(wsResponse.isSuccess()) {
			ajaxResponse.setStatus(200);
			ajaxResponse.setRedirectURL("organizations.html");
			ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.ORGANIZTION_DELETED));
		} else {
			ajaxResponse.setErrorList(getDeleteErrors(wsResponse, request, entity));
		}
		return ajaxResponse;
	}
}
