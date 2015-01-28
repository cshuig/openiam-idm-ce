package org.openiam.ui.web.mvc.entitlements;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openiam.base.KeyDTO;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.bpm.request.GenericWorkflowRequest;
import org.openiam.bpm.util.ActivitiConstants;
import org.openiam.bpm.util.ActivitiRequestType;
import org.openiam.idm.srvc.grp.dto.Group;
import org.openiam.idm.srvc.mngsys.domain.AssociationType;
import org.openiam.idm.srvc.res.dto.Resource;
import org.openiam.idm.srvc.role.dto.Role;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.mvc.AbstractController;

public abstract class AbstractEntityEntitlementsController<T extends KeyDTO> extends AbstractEntitlementsController<T> {

	protected abstract BasicAjaxResponse doEdit(final HttpServletRequest request, final HttpServletResponse response, final T entity) throws Exception;
	protected abstract BasicAjaxResponse doDelete(final HttpServletRequest request, final HttpServletResponse response, final T entity) throws Exception;
	
	@Override
	protected List<ErrorToken> getDeleteErrors(final Response wsResponse, final HttpServletRequest request, final T entity) {
		final List<ErrorToken> errorList = new LinkedList<ErrorToken>();
		if(wsResponse == null) {
			errorList.add(new ErrorToken(Errors.INTERNAL_ERROR));
		} else if(wsResponse.isFailure()) {
			if(wsResponse.getErrorCode() == null) {
				errorList.add(new ErrorToken(Errors.INTERNAL_ERROR));
			} else {
				switch(wsResponse.getErrorCode()) {
                    case FAIL_PREPROCESSOR:
                        errorList.add(new ErrorToken(Errors.ORG_FAIL_PREPROCESSOR));
                        break;
                    case FAIL_POSTPROCESSOR:
                        errorList.add(new ErrorToken(Errors.ORG_FAIL_POSTPROCESSOR));
                        break;
					default:
						errorList.add(new ErrorToken(Errors.INTERNAL_ERROR));
						break;
				}
			}
		}
		return errorList;
	}
	 
	/*
	 protected Response makeEditEntityActivitiRequest(final T entity, final ActivitiRequestType requestType, final AssociationType associationType, 
			 										  final ActivitiConstants jsonParameterName, final HttpServletRequest request,
			 										  final String name, final String description) throws Exception {
		 final GenericWorkflowRequest workflowRequest = new GenericWorkflowRequest();
		 workflowRequest.setActivitiRequestType(requestType.getKey());
		 workflowRequest.setRequestorUserId(getRequesterId(request));
		 workflowRequest.setDescription(description);
		 workflowRequest.setName(name);
		 workflowRequest.addJSONParameter(jsonParameterName.getName(), entity, jacksonMapper);
		 workflowRequest.setAssociationId(entity.getId());
		 workflowRequest.setAssociationType(associationType);
		 if(StringUtils.isBlank(entity.getId())) {
			workflowRequest.setCustomApproverIds(activitiService.getDefaultApproversForEntityCreation(getRequesterId(request), associationType));
		 }
		return activitiService.initiateWorkflow(workflowRequest);
	 }
	 
	 protected Response makeDeleteEntityActivitiRequest(final HttpServletRequest request, final ActivitiRequestType requestType, final ActivitiConstants parameterName,
			 											final AssociationType associationType, final T entity, final String name, final String description) {
		 final GenericWorkflowRequest workflowRequest = new GenericWorkflowRequest();
		 workflowRequest.setActivitiRequestType(requestType.getKey());
		 workflowRequest.setDescription(description);
		 workflowRequest.setName(name);
		 workflowRequest.addParameter(parameterName.getName(), entity.getId());
		 workflowRequest.setRequestorUserId(getRequesterId(request));
		 workflowRequest.setAssociationId(entity.getId());
		 workflowRequest.setAssociationType(associationType);
		 return activitiService.initiateWorkflow(workflowRequest);
	 }
	 */
}
