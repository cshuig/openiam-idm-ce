package org.openiam.ui.selfservice.web.mvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openiam.base.ws.Response;
import org.openiam.bpm.request.GenericWorkflowRequest;
import org.openiam.bpm.util.ActivitiConstants;
import org.openiam.bpm.util.ActivitiRequestType;
import org.openiam.idm.srvc.mngsys.domain.AssociationType;
import org.openiam.idm.srvc.org.dto.Organization;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.mvc.entitlements.AbstractOrganizationController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

@Controller
public class OrganizationController extends AbstractOrganizationController {

    @Value("${org.openiam.selfservice.accessmanagment.org.root}")
    private String rootMenu;
    
    @Value("${org.openiam.selfservice.accessmanagment.org.edit.root}")
    private String editMenu;

	@Override
	protected String getRootMenu() {
		return rootMenu;
	}

	@Override
	protected String getEditMenu() {
		return editMenu;
	}
	
	@Override
	protected BasicAjaxResponse doEdit(HttpServletRequest request,
			HttpServletResponse response, Organization entity) throws Exception {
		/*
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		Response wsResponse = organizationDataService.validateEdit(organization);
		List<ErrorToken> tokenList = getEditErrors(wsResponse, request, organization);
		if(CollectionUtils.isEmpty(tokenList)) {
			final String description = (StringUtils.isNotBlank(organization.getId())) ? 
					String.format("Edit Organization %s", organization.getName()) :
					"Create New Organization";
			final ActivitiRequestType requestType = StringUtils.isNotBlank(organization.getId()) ? 
					ActivitiRequestType.EDIT_ORGANIZATION : ActivitiRequestType.NEW_ORGANIZATION;
			wsResponse = makeEditEntityActivitiRequest(organization, requestType, AssociationType.ORGANIZATION, ActivitiConstants.ORGANIZATION, request, description, description);
			if(wsResponse != null && wsResponse.isFailure()) {
				tokenList = new LinkedList<ErrorToken>();
				ErrorToken token = new ErrorToken(Errors.INTERNAL_ERROR);
				if(wsResponse.getErrorCode() != null) {
					switch(wsResponse.getErrorCode()) {
						default:
							break;
					}
				}
				tokenList.add(token);
			}
		}
		
		if(CollectionUtils.isNotEmpty(tokenList)) {
			ajaxResponse.addErrors(tokenList);
			ajaxResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} else {
			ajaxResponse.setRedirectURL("organizations.html");
			ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.WORKFLOW_INITIATED));
			ajaxResponse.setStatus(200);
		}
		return ajaxResponse;
		*/
		return makeCRUDRequest(request, entity, false, "organizations.html");
	}

	@Override
	protected BasicAjaxResponse doDelete(HttpServletRequest request,
			HttpServletResponse response, Organization entity) throws Exception {
		/*
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		Response wsResponse = validateDelete(id);
		List<ErrorToken> tokenList = getDeleteErrors(wsResponse, request);
		if(CollectionUtils.isEmpty(tokenList)) {
			final Organization entity = organizationDataService.getOrganization(id, getRequesterId(request));
			if(entity == null) {
				tokenList = new LinkedList<ErrorToken>();
				tokenList.add(new ErrorToken(Errors.UNAUTHORIZED));
			} else {
				final String description = String.format("Delete Organization %s", entity.getName());
				
				wsResponse = makeDeleteEntityActivitiRequest(request, ActivitiRequestType.DELETE_ORGANIZATION, ActivitiConstants.ORGANIZATION_ID, AssociationType.ORGANIZATION, entity, description, description);
				if(wsResponse != null && wsResponse.isFailure()) {
					tokenList = new LinkedList<ErrorToken>();
					ErrorToken token = new ErrorToken(Errors.INTERNAL_ERROR);
					if(wsResponse.getErrorCode() != null) {
						switch(wsResponse.getErrorCode()) {
							default:
								break;
						}
					}
					tokenList.add(token);
				}
			}
		}
		
		if(CollectionUtils.isNotEmpty(tokenList)) {
			ajaxResponse.addErrors(tokenList);
			ajaxResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} else {
			ajaxResponse.setRedirectURL("organizations.html");
			ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.WORKFLOW_INITIATED));
			ajaxResponse.setStatus(200);
		}
		return ajaxResponse;
		*/
		return makeCRUDRequest(request, entity, true, "organizations.html");
	}
}
