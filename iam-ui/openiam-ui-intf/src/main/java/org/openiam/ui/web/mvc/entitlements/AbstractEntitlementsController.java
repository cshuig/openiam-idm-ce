package org.openiam.ui.web.mvc.entitlements;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openiam.base.AdminResourceDTO;
import org.openiam.base.KeyDTO;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.bpm.request.GenericWorkflowRequest;
import org.openiam.bpm.util.ActivitiConstants;
import org.openiam.bpm.util.ActivitiRequestType;
import org.openiam.idm.srvc.grp.dto.Group;
import org.openiam.idm.srvc.mngsys.domain.AssociationType;
import org.openiam.idm.srvc.org.dto.Organization;
import org.openiam.idm.srvc.res.dto.Resource;
import org.openiam.idm.srvc.role.dto.Role;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.mvc.AbstractActivitiController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public abstract class AbstractEntitlementsController<T extends KeyDTO> extends AbstractActivitiController {
	
	protected abstract String getEditMenu();
	protected abstract String getRootMenu();
	protected abstract T getEntity(final String id, final HttpServletRequest request);

	protected BasicAjaxResponse makeCRUDRequest(final HttpServletRequest request, final T entity, final boolean isDelete, final String redirectURL) {
		return makeCRUDRequest(request, entity,  isDelete, null, redirectURL);
	}
	protected BasicAjaxResponse makeCRUDRequest(final HttpServletRequest request, final T entity, final boolean isDelete, final Set<String> ownerIds, final String redirectURL) {
		final BasicAjaxResponse response = new BasicAjaxResponse();
		String name = null;
		AssociationType entityType = null;
		Response wsResponse = null;
		ActivitiRequestType requestType = null;
		ActivitiConstants jsonParamName = null;
		List<ErrorToken> errorList = new LinkedList<ErrorToken>();
		try {
			if(entity == null) {
				errorList.add(new ErrorToken(Errors.UNAUTHORIZED));
			} else {
				if(entity instanceof Organization) {
					entityType = AssociationType.ORGANIZATION;
					jsonParamName = ActivitiConstants.ORGANIZATION;
					if(isDelete) {
						wsResponse = validateDelete((Organization)entity);
						errorList = getDeleteErrors(wsResponse, request, entity);
						requestType = ActivitiRequestType.DELETE_ORGANIZATION;
					} else {
						wsResponse = validateEdit((Organization)entity);
						errorList = getEditErrors(wsResponse, request, entity);
						if(StringUtils.isBlank(entity.getId())) {
							requestType = ActivitiRequestType.NEW_ORGANIZATION;
						} else {
							requestType = ActivitiRequestType.EDIT_ORGANIZATION;
						}
					}
					name = getTaskName((Organization)entity, isDelete);
				} else if (entity instanceof Group) {
					entityType = AssociationType.GROUP;
					jsonParamName = ActivitiConstants.GROUP;
					if(isDelete) {
						wsResponse = validateDelete((Group)entity);
						errorList = getDeleteErrors(wsResponse, request, entity);
						requestType = ActivitiRequestType.DELETE_GROUP;
					} else {
						wsResponse = validateEdit((Group)entity);
						errorList = getEditErrors(wsResponse, request, entity);
						if(StringUtils.isBlank(entity.getId())) {
							requestType = ActivitiRequestType.NEW_GROUP;
						} else {
							requestType = ActivitiRequestType.EDIT_GROUP;
						}
					}
					name = getTaskName((Group)entity, isDelete);
				}  else if(entity instanceof Role) {
					entityType = AssociationType.ROLE;
					jsonParamName = ActivitiConstants.ROLE;
					if(isDelete) {
						wsResponse = validateDelete((Role)entity);
						errorList = getDeleteErrors(wsResponse, request, entity);
						requestType = ActivitiRequestType.DELETE_ROLE;
					} else {
						wsResponse = validateEdit((Role)entity);
						errorList = getEditErrors(wsResponse, request, entity);
						if(StringUtils.isBlank(entity.getId())) {
							requestType = ActivitiRequestType.NEW_ROLE;
						} else {
							requestType = ActivitiRequestType.EDIT_ROLE;
						}
					}
					name = getTaskName((Role)entity, isDelete);
				}  else if(entity instanceof Resource) {
					entityType = AssociationType.RESOURCE;
					jsonParamName = ActivitiConstants.RESOURCE;
					if(isDelete) {
						wsResponse = validateDelete((Resource)entity);
						errorList = getDeleteErrors(wsResponse, request, entity);
						requestType = ActivitiRequestType.DELETE_RESOURCE;
					} else {
						wsResponse = validateEdit((Resource)entity);
						errorList = getEditErrors(wsResponse, request, entity);
						if(StringUtils.isBlank(entity.getId())) {
							requestType = ActivitiRequestType.NEW_RESOURCE;
						} else {
							requestType = ActivitiRequestType.EDIT_RESOURCE;
						}
					}
					name = getTaskName((Resource)entity, isDelete);
				} else {
					throw new IllegalArgumentException("'entity' argument is invalid");
				}
			}
			
			if(CollectionUtils.isEmpty(errorList)) {
				final GenericWorkflowRequest workflowRequest = new GenericWorkflowRequest();
				 workflowRequest.setActivitiRequestType(requestType.getKey());
				 workflowRequest.setRequestorUserId(getRequesterId(request));
				 workflowRequest.setDescription(name);
				 workflowRequest.setName(name);
				 workflowRequest.addJSONParameter(jsonParamName.getName(), entity, jacksonMapper);
				 workflowRequest.setAssociationId(entity.getId());
				 workflowRequest.setAssociationType(entityType);
				 if(CollectionUtils.isNotEmpty(ownerIds)){
					 workflowRequest.setCustomApproverIds(ownerIds);
				 }
				 if(StringUtils.isNotBlank(entity.getId())) {
					 final String adminResourceId = ((AdminResourceDTO)entity).getAdminResourceId();
					 final boolean isEntitled = authorizationManager.isUserEntitledToResource(getRequesterId(request), adminResourceId);
					 workflowRequest.addParameter(ActivitiConstants.IS_ADMIN.getName(), isEntitled);
				 }
				wsResponse = activitiService.initiateWorkflow(workflowRequest);
				if(wsResponse == null || wsResponse.isFailure()) {
					errorList.add(new ErrorToken(Errors.WORKFLOW_NOT_INITIATED));
				}
			}
		} catch(Throwable e) {
			 log.error("Can't make CRUD request", e);
			 errorList.add(new ErrorToken(Errors.INTERNAL_ERROR));
		} finally {
			 if(CollectionUtils.isNotEmpty(errorList)) {
				response.setStatus(500);
				response.setErrorList(errorList);
			} else {
				response.setRedirectURL(redirectURL);
				response.setStatus(200);
				response.setSuccessToken(new SuccessToken(SuccessMessage.WORKFLOW_INITIATED));
			}
		}
		return response;
	}

	protected BasicAjaxResponse makeMembershipRequest(final HttpServletRequest request, final KeyDTO entity, final KeyDTO member, final boolean isAddition){
		return makeMembershipRequest(request, entity, member, null, isAddition);
	}

	protected BasicAjaxResponse makeMembershipRequest(final HttpServletRequest request, final KeyDTO entity, final KeyDTO member, final Set<String> ownerIds, final boolean isAddition) {
		 final BasicAjaxResponse response = new BasicAjaxResponse();
		 String name = null;
		 AssociationType entityType = null;
		 AssociationType memberType = null;
		 Response wsResponse = null;
		 ActivitiRequestType requestType = null;
		 
		 String userCentricId = null;
		 final Set<String> additionalApproverIds = new HashSet<String>();
		 final List<ErrorToken> errorList = new LinkedList<ErrorToken>();
		 if(entity == null || member == null) {
			errorList.add(new ErrorToken(Errors.UNAUTHORIZED));
		 } else {
			 try {
				 if(entity instanceof User) {
					 entityType = AssociationType.USER;
					 if(member instanceof User) {
						 memberType = AssociationType.USER;
						 name = getTaskName((User)entity, (User)member, isAddition);
						 if(isAddition) {
							 additionalApproverIds.add(entity.getId());
							 requestType = ActivitiRequestType.ADD_SUPERIOR;
							 wsResponse = validateAddition((User)entity, (User)member);
						 } else {
							 requestType = ActivitiRequestType.REMOVE_SUPERIOR; 
						 }
						 userCentricId = member.getId();
					 } else {
						 throw new IllegalArgumentException("'member' argument is invalid");
					 }
				 } else if(entity instanceof Organization) {
					 entityType = AssociationType.ORGANIZATION;
					 if(member instanceof User) {
						 name = getTaskName((Organization)entity, (User)member, isAddition);
						 memberType = AssociationType.USER;
						 if(isAddition) {
							 requestType = ActivitiRequestType.ADD_USER_TO_ORG;
							 wsResponse = validateAddition((Organization)entity, (User)member);
						 } else {
							 requestType = ActivitiRequestType.REMOVE_USER_FROM_ORG;
						 }
					 } else {
						 throw new IllegalArgumentException("'member' argument is invalid");
					 }
				 } else if(entity instanceof Group) {
					 entityType = AssociationType.GROUP;
					 if(member instanceof User) {
						 name = getTaskName((Group)entity, (User)member, isAddition);
						 memberType = AssociationType.USER;
						 if(isAddition) {
							 requestType = ActivitiRequestType.ADD_USER_TO_GROUP;
							 wsResponse = validateAddition((Group)entity, (User)member);
						 } else {
							 requestType = ActivitiRequestType.REMOVE_USER_FROM_GROUP;
						 }
					 } else if(member instanceof Group) {
						 name = getTaskName((Group)entity, (Group)member, isAddition);
						 memberType = AssociationType.GROUP;
						 if(isAddition) {
							 requestType = ActivitiRequestType.ADD_GROUP_TO_GROUP;
							 wsResponse = validateAddition((Group)entity, (Group)member);
						 } else {
							 requestType = ActivitiRequestType.REMOVE_GROUP_FROM_GROUP;
						 }
					 } else if(member instanceof Role) {
						 name = getTaskName((Group)entity, (Role)member, isAddition);
						 memberType = AssociationType.ROLE;
						 if(isAddition) {
							 requestType = ActivitiRequestType.ADD_ROLE_TO_GROUP;
							 wsResponse = validateAddition((Group)entity, (Role)member);
						 } else {
							 requestType = ActivitiRequestType.REMOVE_ROLE_FROM_GROUP;
						 }
					 } else {
						 throw new IllegalArgumentException("'member' argument is invalid");
					 }
				 } else if(entity instanceof Role) {
					 entityType = AssociationType.ROLE;
					 if(member instanceof User) {
						 name = getTaskName((Role)entity, (User)member, isAddition);
						 memberType = AssociationType.USER;
						 if(isAddition) {
							 requestType = ActivitiRequestType.ADD_USER_TO_ROLE;
							 wsResponse = validateAddition((Role)entity, (User)member);
						 } else {
							 requestType = ActivitiRequestType.REMOVE_USER_FROM_ROLE;
						 }
					 } else if(member instanceof Role) {
						 name = getTaskName((Role)entity, (Role)member, isAddition);
						 memberType = AssociationType.ROLE;
						 if(isAddition) {
							 requestType = ActivitiRequestType.ADD_ROLE_TO_ROLE;
							 wsResponse = validateAddition((Role)entity, (Role)member);
						 } else {
							 requestType = ActivitiRequestType.REMOVE_ROLE_FROM_ROLE;
						 }
					 } else {
						 throw new IllegalArgumentException("'member' argument is invalid");
					 }
				 } else if(entity instanceof Resource) {
					 entityType = AssociationType.RESOURCE;
					 if(member instanceof User) {
						 name = getTaskName((Resource)entity, (User)member, isAddition);
						 memberType = AssociationType.USER;
						 if(isAddition) {
							 requestType = ActivitiRequestType.ENTITLE_USER_TO_RESOURCE;
							 wsResponse = validateAddition((Resource)entity, (User)member);
						 } else {
							 requestType = ActivitiRequestType.DISENTITLE_USR_FROM_RESOURCE;
						 }
					 } else if(member instanceof Group) {
						 name = getTaskName((Resource)entity, (Group)member, isAddition);
						 memberType = AssociationType.GROUP;
						 if(isAddition) {
							 requestType = ActivitiRequestType.ENTITLE_RESOURCE_TO_GROUP;
							 wsResponse = validateAddition((Resource)entity, (Group)member);
						 } else {
							 requestType = ActivitiRequestType.DISENTITLE_RESOURCE_FROM_GROUP;
						 }
					 } else if(member instanceof Role) {
						 name = getTaskName((Resource)entity, (Role)member, isAddition);
						 memberType = AssociationType.ROLE;
						 if(isAddition) {
							 requestType = ActivitiRequestType.ENTITLE_RESOURCE_TO_ROLE;
							 wsResponse = validateAddition((Resource)entity, (Role)member);
						 } else {
							 requestType = ActivitiRequestType.DISENTITLE_RESOURCE_FROM_ROLE;
						 }
					 } else if(member instanceof Resource) {
						 name = getTaskName((Resource)entity, (Resource)member, isAddition);
						 memberType = AssociationType.RESOURCE;
						 if(isAddition) {
							 requestType = ActivitiRequestType.ADD_RESOURCE_TO_RESOURCE;
							 wsResponse = validateAddition((Resource)entity, (Resource)member);
						 } else {
							 requestType = ActivitiRequestType.REMOVE_RESOURCE_FROM_RESOURCE;
						 }
					 } else {
						 throw new IllegalArgumentException("'member' argument is invalid");
					 }
				 } else {
					 throw new IllegalArgumentException("'entity' argument is invalid");
				 }
				 
				 if(wsResponse != null && wsResponse.isFailure()) {
					 final BasicAjaxResponse tempResponse = getResponseAfterEntity2EntityAddition(wsResponse, false);
					 if(tempResponse.getErrorList() != null) {
						 errorList.addAll(tempResponse.getErrorList());
					 }
				 } else {
					 final GenericWorkflowRequest workflowRequest = new GenericWorkflowRequest();
					 workflowRequest.setActivitiRequestType(requestType.getKey());
					 workflowRequest.setDescription(name);
					 workflowRequest.setName(name);
					 workflowRequest.setRequestorUserId(getRequesterId(request));
					 workflowRequest.setAssociationId(entity.getId());
					 workflowRequest.setAssociationType(entityType);
					 workflowRequest.setMemberAssociationId(member.getId());
					 workflowRequest.setMemberAssociationType(memberType);
					 workflowRequest.setAdditionalApproverIds(additionalApproverIds);
					 workflowRequest.setUserCentricUserId(userCentricId);
					 if(CollectionUtils.isNotEmpty(ownerIds)){
						 workflowRequest.setCustomApproverIds(ownerIds);
					 }
					 wsResponse = activitiService.initiateWorkflow(workflowRequest);
					 if(wsResponse.isFailure()) {
						 errorList.add(new ErrorToken(Errors.WORKFLOW_NOT_INITIATED));
					 }
				 }
			 } catch(Throwable e) {
				 log.error("Can't make membership request", e);
				 errorList.add(new ErrorToken(Errors.INTERNAL_ERROR));
			 } finally {
				 if(CollectionUtils.isNotEmpty(errorList)) {
					response.setStatus(500);
					response.setErrorList(errorList);
				} else {
					response.setStatus(200);
					response.setSuccessToken(new SuccessToken(SuccessMessage.WORKFLOW_INITIATED));
				}
			 }
		 }
		 return response;
	 }
	
	protected List<ErrorToken> getDeleteErrors(final Response wsResponse, final HttpServletRequest request, final T entity) {
		throw new IllegalAccessError("getDeleteErrors() must be overridden");
	}

	protected List<ErrorToken> getEditErrors(final Response wsResponse, final HttpServletRequest request, final T entity) {
		throw new IllegalAccessError("getEditErrors() must be overridden");
	}
	
	 protected Response validateEdit(final Resource entity) {
		 return resourceDataService.validateEdit(entity);
	 }
	 
	 protected Response validateDelete(final Resource entity) {
		 return resourceDataService.validateDelete(entity.getId());
	 }
	 
	 protected Response validateEdit(final Group entity) {
		 return groupServiceClient.validateEdit(entity);
	 }
	 
	 protected Response validateDelete(final Group entity) {
		 return groupServiceClient.validateDelete(entity.getId());
	 }
	 
	 protected Response validateEdit(final Role entity) {
		 return roleServiceClient.validateEdit(entity);
	 }
	 
	 protected Response validateDelete(final Role entity) {
		 return roleServiceClient.validateDelete(entity.getId());
	 }
	 
	 protected Response validateEdit(final Organization entity) {
		 return organizationDataService.validateEdit(entity);
	 }
	 
	 protected Response validateDelete(final Organization entity) {
		 return organizationDataService.validateDelete(entity.getId());
	 }
	 
	 private Response validateAddition(final Resource entity, final User member) {
		 return resourceDataService.canAddUserToResource(member.getId(), entity.getId());
	 }
	 
	 private Response validateAddition(final Role entity, final User member) {
		 return roleServiceClient.canAddUserToRole(member.getId(), entity.getId());
	 }
	 
	 private Response validateAddition(final Group entity, final User member) {
		 return groupServiceClient.canAddUserToGroup(member.getId(), entity.getId());
	 }
	 
	 private Response validateAddition(final User entity, final User member) {
		 return new Response(ResponseStatus.SUCCESS);
	 }
	 
	 private Response validateAddition(final Organization entity, final User member) {
		 return organizationDataService.canAddUserToOrganization(entity.getId(), member.getId());
	 }
	 
	 private Response validateAddition(final Group entity, final Group member) {
		 return groupServiceClient.validateGroup2GroupAddition(entity.getId(), member.getId());
	 }
	 
	 private Response validateAddition(final Role entity, final Role member) {
		 return roleServiceClient.canAddChildRole(entity.getId(), member.getId());
	 }
	 
	 private Response validateAddition(final Group entity, final Role member) {
		 return roleServiceClient.validateGroup2RoleAddition(member.getId(), entity.getId());
	 }
	 
	 private Response validateAddition(final Resource entity, final Group member) {
		 return new Response(ResponseStatus.SUCCESS);
	 }
	 
	 private Response validateAddition(final Resource entity, final Role member) {
		 return new Response(ResponseStatus.SUCCESS);
	 }
	 
	 private Response validateAddition(final Resource entity, final Resource member) {
		 return resourceDataService.validateAddChildResource(entity.getId(), member.getId());
	 }
	 
	 protected String getTaskName(final Resource entity, final boolean isDelete) {
		 if(isDelete) {
			 return String.format("Delete Resource %s", entity.getName());
		 } else {
			 if(StringUtils.isBlank(entity.getId())) {
				 return "Create New Resource";
			 } else {
				 return String.format("Edit Resoruce %s", entity.getName());
			 }
		 }
	 }
	 
	 protected String getTaskName(final Group entity, final boolean isDelete) {
		 if(isDelete) {
			 return String.format("Delete Group %s", entity.getName());
		 } else {
			 if(StringUtils.isBlank(entity.getId())) {
				 return "Create New Group";
			 } else {
				 return String.format("Edit Group %s", entity.getName());
			 }
		 }
	 }
	 
	 protected String getTaskName(final Role entity, final boolean isDelete) {
		 if(isDelete) {
			 return String.format("Delete Role %s", entity.getName());
		 } else {
			 if(StringUtils.isBlank(entity.getId())) {
				 return "Create New Role";
			 } else {
				 return String.format("Edit Role %s", entity.getName());
			 }
		 }
	 }
	 
	 protected String getTaskName(final Organization entity, final boolean isDelete) {
		 if(isDelete) {
			 return String.format("Delete Organization %s", entity.getName());
		 } else {
			 if(StringUtils.isBlank(entity.getId())) {
				 return "Create New Organization";
			 } else {
				 return String.format("Edit Organization %s", entity.getName());
			 }
		 }
	 }
	 
	 private String getTaskName(final Resource entity, final User member, final boolean isAddition) {
		 if(isAddition) {
			 return String.format("Entitle user %s to resource %s", member.getDisplayName(), entity.getName());
		 } else {
			 return String.format("Disentitle user %s from resource %s", member.getDisplayName(), entity.getName());
		 }
	 }
	 
	 private String getTaskName(final Role entity, final User member, final boolean isAddition) {
		 if(isAddition) {
			 return String.format("Make user %s a member of role %s", member.getDisplayName(), entity.getName());
		 } else {
			 return String.format("Remove user %s from role %s", member.getDisplayName(), entity.getName());
		 }
	 }
	 
	 private String getTaskName(final Group entity, final User member, final boolean isAddition) {
		 if(isAddition) {
			 return String.format("Make user %s a member of group %s", member.getDisplayName(), entity.getName());
		 } else {
			 return String.format("Remove user %s from group %s", member.getDisplayName(), entity.getName());
		 }
	 }
	 
	 private String getTaskName(final User entity, final User member, final boolean isAddition) {
		 if(isAddition) {
			 return String.format("Make %s a subordinate of %s", member.getDisplayName(), entity.getDisplayName());
		 } else {
			 return String.format("Remove %s as a subordinate of %s", member.getDisplayName(), entity.getDisplayName());
		 }
	 }
	 
	 private String getTaskName(final Organization entity, final User member, final boolean isAddition) {
		 if(isAddition) {
			 return String.format("Make user %s a member of organization %s", member.getDisplayName(), entity.getName());
		 } else {
			 return String.format("Remove user %s from organization %s", member.getDisplayName(), entity.getName());
		 }
	 }
	 
	 private String getTaskName(final Group entity, final Group member, final boolean isAddition) {
		 if(isAddition) {
			 return String.format("Make group %s a child of group %s", member.getName(), entity.getName());
		 } else {
			 return String.format("Remove group %s a child of group %s", member.getName(), entity.getName());
		 }
	 }
	 
	 private String getTaskName(final Group entity, final Role member, final boolean isAddition) {
		 if(isAddition) {
			 return String.format("Make role %s a child of group %s", member.getName(), entity.getName());
		 } else {
			 return String.format("Remove role %s from group %s", member.getName(), entity.getName());
		 }
	 }
	 
	 private String getTaskName(final Role entity, final Role member, final boolean isAddition) {
		 if(isAddition) {
			 return String.format("Make role %s a child of role %s", member.getName(), entity.getName());
		 } else {
			 return String.format("Remove role %s from role %s", member.getName(), entity.getName());
		 }
	 }
	 
	 private String getTaskName(final Resource entity, final Resource member, final boolean isAddition) {
		 if(isAddition) {
			 return String.format("Make resource %s a child of resource %s", member.getName(), entity.getName());
		 } else {
			 return String.format("Remove resource %s from resource %s", member.getName(), entity.getName());
		 }
	 }
	 
	 protected String getTaskName(final Resource entity, final Role member, final boolean isAddition) {
		 if(isAddition) {
			 return String.format("Entitle resource %s to role %s", entity.getName(), member.getName());
		 } else {
			 return String.format("Disentitle resource %s from role %s", entity.getName(), member.getName());
		 }
	 }
	 
	 protected String getTaskName(final Resource entity, final Group member, final boolean isAddition) {
		 if(isAddition) {
			 return String.format("Entitle resource %s to group %s", entity.getName(), member.getName());
		 } else {
			 return String.format("Disentitle resource %s from group %s", entity.getName(), member.getName());
		 }
	 }
	 
	 protected BasicAjaxResponse getResponseAfterEntity2EntityAddition(final Response wsResponse, final boolean isDelete) {
		 final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		 if (ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
			 ajaxResponse.setStatus(200);
			 ajaxResponse.setSuccessToken(new SuccessToken((isDelete) ? SuccessMessage.ENTITY_DELETED : SuccessMessage.ENTITY_ADDED));
		 } else {
			 if (wsResponse.getErrorCode() != null) {
				 switch (wsResponse.getErrorCode()) {
	                case CANT_ADD_YOURSELF_AS_CHILD:
	                    ajaxResponse.addError(new ErrorToken(Errors.CANT_MAKE_ENTITY_A_CHILD_OF_ITSELF));
	                    break;
	                case UNAUTHORIZED:
                        ajaxResponse.addError(new ErrorToken(Errors.UNAUTHORIZED));
                        break;
	                case CIRCULAR_DEPENDENCY:
	                    ajaxResponse.addError(new ErrorToken(Errors.CIRCULAR_DEPENDENCY));
	                    break;
	                case RELATIONSHIP_EXISTS:
	                    ajaxResponse.addError(new ErrorToken(Errors.RELATIONSHIP_EXISTS));
	                    break;
	                case OBJECT_NOT_FOUND:
	                    ajaxResponse.addError(new ErrorToken(Errors.OBJECT_DOES_NOT_EXIST));
	                    break;
					case PRINCIPAL_NOT_FOUND:
						ajaxResponse.addError(new ErrorToken(Errors.IDENTITY_DOES_NOT_EXIST));
						break;
	                case INVALID_ARGUMENTS:
	                    ajaxResponse.addError(new ErrorToken(Errors.INTERNAL_ERROR));
	                    break;
	                default:
	                    ajaxResponse.addError(new ErrorToken(Errors.INTERNAL_ERROR));
	                    break;
				 }
			 } else {
				 ajaxResponse.addError(new ErrorToken(Errors.INTERNAL_ERROR));
			 }
		 }
		 return ajaxResponse;
	 }
}
