package org.openiam.ui.webconsole.idm.web.mvc.provisioning.approverassoc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.dozer.DozerBeanMapper;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.srvc.audit.constant.AuditAction;
import org.openiam.idm.srvc.audit.dto.IdmAuditLog;
import org.openiam.idm.srvc.auth.dto.IdentityDto;
import org.openiam.idm.srvc.grp.dto.Group;
import org.openiam.idm.srvc.grp.ws.GroupDataWebService;
import org.openiam.idm.srvc.lang.dto.Language;
import org.openiam.idm.srvc.mngsys.domain.AssociationType;
import org.openiam.idm.srvc.mngsys.dto.ApproverAssocationSearchBean;
import org.openiam.idm.srvc.mngsys.dto.ApproverAssociation;
import org.openiam.idm.srvc.mngsys.ws.ManagedSystemWebService;
import org.openiam.idm.srvc.org.dto.Organization;
import org.openiam.idm.srvc.org.service.OrganizationDataService;
import org.openiam.idm.srvc.res.service.ResourceDataService;
import org.openiam.idm.srvc.role.dto.Role;
import org.openiam.idm.srvc.role.ws.RoleDataWebService;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.idm.srvc.user.ws.UserDataWebService;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.mvc.AbstractController;
import org.openiam.ui.webconsole.idm.web.mvc.provisioning.common.IdNameBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Controller
public class ApproverAssocationController extends AbstractController {
	
	@Value("${org.openiam.ui.resource.edit.menu.name}")
	private String resourceEditPageRootMenuName;
	
	@Value("${org.openiam.ui.group.edit.menu.name}")
	private String groupEditMenuName;
	
	@Value("${org.openiam.ui.role.edit.menu.name}")
	private String roleEditMenuName;
	
	@Value("${org.openiam.ui.organization.edit.menu.id}")
	private String organizationEditMenuName;
	
    @Autowired
    private ManagedSystemWebService managedSysServiceClient;
    
	@Resource(name="groupServiceClient")
	private GroupDataWebService groupServiceClient;
	
	@Resource(name="resourceServiceClient")
	private ResourceDataService resourceDataService;
    
	@Resource(name="roleServiceClient")
	private RoleDataWebService roleServiceClient;
	
    @Resource(name = "userServiceClient")
    protected UserDataWebService userDataWebService;
    
    @Resource(name="organizationServiceClient")
    private OrganizationDataService organizationDataService;

    @Autowired
    @Qualifier("beanMapper")
    private DozerBeanMapper mapper;

	@RequestMapping("/provisioning/resourceApproverAssociations")
	public String resourceApproverAssociations(final HttpServletRequest request, 
											   final @RequestParam(required=true, value="id") String id) throws JsonGenerationException, JsonMappingException, IOException {
		setCommonAttributes(request, AssociationType.RESOURCE, id);
		return "provisioning/approverAssociation";
	}
	
	@RequestMapping("/provisioning/groupApproverAssociations")
	public String groupApproverAssociations(final HttpServletRequest request, 
											final @RequestParam(required=true, value="id") String id) throws JsonGenerationException, JsonMappingException, IOException {
		setCommonAttributes(request, AssociationType.GROUP, id);
		return "provisioning/approverAssociation";
	}
	
	@RequestMapping("/provisioning/roleApproverAssociations")
	public String roleApproverAssociations(final HttpServletRequest request, 
										   final @RequestParam(required=true, value="id") String id) throws JsonGenerationException, JsonMappingException, IOException {
		setCommonAttributes(request, AssociationType.ROLE, id);
		return "provisioning/approverAssociation";
	}
	
	@RequestMapping("/provisioning/organizationApproverAssociations")
	public String organizationApproverAssociations(final HttpServletRequest request, 
										   		   final @RequestParam(required=true, value="id") String id) throws JsonGenerationException, JsonMappingException, IOException {
		setCommonAttributes(request, AssociationType.ORGANIZATION, id);
		return "provisioning/approverAssociation";
	}
	
	@RequestMapping(value="/provisioning/saveApproverAssociations", method=RequestMethod.POST)
	public String saveApproverAssociation(final HttpServletRequest request, @RequestBody ApproverAssociationResponseBean bean) throws JsonParseException, JsonMappingException, IOException {

        final ApproverAssocationSearchBean searchBean = new ApproverAssocationSearchBean();
        searchBean.setAssociationEntityId(bean.getEntityId());
        searchBean.setAssociationType(bean.getType());
        final List<ApproverAssociation> currApproverAssociations = managedSysServiceClient.getApproverAssociations(searchBean, 0, Integer.MAX_VALUE);

		final List<ApproverAssociation> approverAssociationList = new LinkedList<ApproverAssociation>();
		if(bean != null && CollectionUtils.isNotEmpty(bean.getBeans())) {
			for(final ApproverAssociationModel model : bean.getBeans()) {
				approverAssociationList.add(model);
			}
		}

        String requesterId = getRequesterId(request);
        String requestorPrincipal = getRequesterPrincipal(request);
        Language curLang = getCurrentLanguage();

        IdmAuditLog idmAuditLog = new IdmAuditLog();
        idmAuditLog.setRequestorUserId(requesterId);
        idmAuditLog.setRequestorPrincipal(requestorPrincipal);
        idmAuditLog.setAction(AuditAction.EDIT_APPROVER_ASSOCIATIONS.value());
        setTargetForApproverAuditLog(idmAuditLog, bean.getType(), bean.getEntityId(), requesterId, curLang);

        if (CollectionUtils.isNotEmpty(currApproverAssociations)) {
            List<ApproverAssociation> permApproverAssociationList = new LinkedList<ApproverAssociation>();
            for (ApproverAssociation as : approverAssociationList) {
                boolean exists = false;
                for (ApproverAssociation cas : currApproverAssociations) {
                    if (StringUtils.equals(cas.getApproverEntityId(), as.getApproverEntityId()) &&
                            cas.getApproverEntityType() == as.getApproverEntityType() &&
                            StringUtils.equals(cas.getAssociationEntityId(), as.getAssociationEntityId()) &&
                            cas.getAssociationType() == as.getAssociationType() &&
                            StringUtils.equals(cas.getOnApproveEntityId(),as.getOnApproveEntityId()) &&
                            cas.getOnApproveEntityType() == as.getOnApproveEntityType() &&
                            StringUtils.equals(cas.getOnRejectEntityId(),as.getOnRejectEntityId()) &&
                            cas.getOnRejectEntityType() == as.getOnRejectEntityType()) {
                        permApproverAssociationList.add(cas);
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    IdmAuditLog childAuditLog = buildAuditLogForApprover(as, requesterId, requestorPrincipal, curLang, false);
                    idmAuditLog.addChild(childAuditLog);
                }
            }
            currApproverAssociations.removeAll(permApproverAssociationList);
            for (ApproverAssociation das : currApproverAssociations) {
                IdmAuditLog childAuditLog = buildAuditLogForApprover(das, requesterId, requestorPrincipal, curLang, true);
                idmAuditLog.addChild(childAuditLog);
            }
        } else if (CollectionUtils.isNotEmpty(approverAssociationList)) {
            for (ApproverAssociation as : approverAssociationList) {
                IdmAuditLog childAuditLog = buildAuditLogForApprover(as, requesterId, requestorPrincipal, curLang, false);
                idmAuditLog.addChild(childAuditLog);
            }
        }

		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		final Response wsResponse = managedSysServiceClient.saveApproverAssociations(approverAssociationList, bean.getType(), bean.getEntityId());
		Errors error = null;
		try {
			if(wsResponse == null) {
				error = Errors.APPROVER_ASSOC_SAVE_ERR;
                idmAuditLog.succeed();

			} else if(wsResponse.getStatus() == ResponseStatus.FAILURE) {
                idmAuditLog.fail();
                idmAuditLog.setFailureReason(wsResponse.getErrorCode());
				error = Errors.APPROVER_ASSOC_SAVE_ERR;
				if(wsResponse.getErrorCode() != null) {
					switch(wsResponse.getErrorCode()) {
						case REQUEST_APPROVERS_NOT_SET:
							error = Errors.REQUEST_APPROVERS_NOT_SET;
							break;
						default:
							break;
					}
				}
			}

            auditLogService.addLog(idmAuditLog);

		} finally {
			if(error != null) {
				ajaxResponse.addError(new ErrorToken(error));
				ajaxResponse.setStatus(500);
			} else {
				ajaxResponse.setStatus(200);
				ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.APPROVER_ASSOCIAITON_SAVE));
			}
		}
		request.setAttribute("response", ajaxResponse);
		return "common/basic.ajax.response";
	}

    private IdmAuditLog buildAuditLogForApprover(ApproverAssociation association, String requesterId,
                                                 String requestorPrincipal, Language curLang, boolean delete) {
        String approverEntityId = association.getApproverEntityId();
        String associationEntityId = association.getAssociationEntityId();
        IdmAuditLog idmAuditLog = new IdmAuditLog();
        idmAuditLog.setRequestorUserId(requesterId);
        idmAuditLog.setRequestorPrincipal(requestorPrincipal);

        switch (association.getAssociationType()) {
            case GROUP:
                idmAuditLog.setAction(delete? AuditAction.DELETE_APPROVER_FROM_GROUP.value(): AuditAction.ADD_APPROVER_TO_GROUP.value());
                break;
            case ORGANIZATION:
                idmAuditLog.setAction(delete? AuditAction.DELETE_APPROVER_FROM_ORGANIZATION.value(): AuditAction.ADD_APPROVER_TO_ORGANIZATION.value());
                break;
            case RESOURCE:
                idmAuditLog.setAction(delete? AuditAction.DELETE_APPROVER_FROM_RESOURCE.value(): AuditAction.ADD_APPROVER_TO_RESOURCE.value());
                break;
            case ROLE:
                idmAuditLog.setAction(delete? AuditAction.DELETE_APPROVER_FROM_ROLE.value(): AuditAction.ADD_APPROVER_TO_ROLE.value());
                break;
            case TARGET_USER:
                idmAuditLog.setAction(delete? AuditAction.DELETE_APPROVER_FROM_TARGET_USER.value(): AuditAction.ADD_APPROVER_TO_TARGET_USER.value());
                break;
            case USER:
                idmAuditLog.setAction(delete? AuditAction.DELETE_APPROVER_FROM_USER.value(): AuditAction.ADD_APPROVER_TO_USER.value());
                break;
            default:
                break;
        }
        setTargetForApproverAuditLog(idmAuditLog, association.getAssociationType(), associationEntityId, requesterId, curLang);
        setTargetForApproverAuditLog(idmAuditLog, association.getApproverEntityType(), approverEntityId, requesterId, curLang);

        return idmAuditLog;
    }

    private void setTargetForApproverAuditLog(IdmAuditLog idmAuditLog, AssociationType type, String entityId,
                                              String requesterId, Language curLang) {
        if (StringUtils.isNotBlank(entityId) && type != null) {
            switch (type) {
                case GROUP:
                    final Group group = groupServiceClient.getGroup(entityId, requesterId);
                    idmAuditLog.setTargetGroup(group.getId(), group.getName());
                    break;
                case ORGANIZATION:
                    final Organization org = organizationDataService.getOrganizationLocalized(entityId, requesterId, curLang);
                    idmAuditLog.setTargetOrg(org.getId(), org.getName());
                    break;
                case RESOURCE:
                    final org.openiam.idm.srvc.res.dto.Resource res = resourceDataService.getResource(entityId, curLang);
                    idmAuditLog.setTargetResource(res.getId(), res.getName());
                    break;
                case ROLE:
                    final Role role = roleServiceClient.getRole(entityId, requesterId);
                    idmAuditLog.setTargetRole(role.getId(), role.getName());
                    break;
                //case SUPERVISOR:
                case TARGET_USER:
                case USER:
                    final User user = userDataWebService.getUserWithDependent(entityId, requesterId, false);
                    String principal = user.getDisplayName();
                    IdentityDto identity = identityWebService.getIdentityByManagedSys(entityId, defaultManagedSysId);
                    if (identity != null) {
                        principal = identity.getIdentity();
                    }
                    idmAuditLog.setTargetUser(user.getId(), principal);
                    break;
                default:
                    break;
            }
        }
    }
	
	private List<ApproverAssociationModel> getApproverAssociationModal(final HttpServletRequest request, 
																	   final AssociationType type, 
																	   final String id) {
		final ApproverAssocationSearchBean searchBean = new ApproverAssocationSearchBean();
		searchBean.setAssociationEntityId(id);
		searchBean.setAssociationType(type);
		final List<ApproverAssociation> approverAssociations = managedSysServiceClient.getApproverAssociations(searchBean, 0, Integer.MAX_VALUE);
		
		//final ApproverAssociationResponseBean responseBean = new ApproverAssociationResponseBean();
		return convertToAssociationModel(request, approverAssociations);
	}
	
	private List<ApproverAssociationModel> convertToAssociationModel(final HttpServletRequest request,
																	 final List<ApproverAssociation> approverAssociations) {
		final List<ApproverAssociationModel> retVal = new LinkedList<ApproverAssociationModel>();
		if(CollectionUtils.isNotEmpty(approverAssociations)) {
			for(final ApproverAssociation association : approverAssociations) {
				final ApproverAssociationModel model = mapper.map(association, ApproverAssociationModel.class);
				model.setApproverDisplayName(getDisplayNameForAssociationModel(request, model.getApproverEntityType(), model.getApproverEntityId()));
				model.setOnApproveDisplayName(getDisplayNameForAssociationModel(request, model.getOnApproveEntityType(), model.getOnApproveEntityId()));
				model.setOnRejectDisplayName(getDisplayNameForAssociationModel(request, model.getOnRejectEntityType(), model.getOnRejectEntityId()));
				model.setAssociationTypeDispayName(getDisplayNameForAssociationModel(request, model.getAssociationType(), model.getAssociationEntityId()));
				retVal.add(model);
			}
		}
		return retVal;
	}
	
	private String getDisplayNameForAssociationModel(final HttpServletRequest request,
												   final AssociationType associationType, 
												   final String associationId) {
		String displayName = null;
		final String userId = cookieProvider.getUserId(request);
		if(associationType != null && StringUtils.isNotBlank(associationId)) {
			switch(associationType) {
				case GROUP:
					final Group group = groupServiceClient.getGroup(associationId, userId);
					displayName = (group != null) ? group.getName() : null;
					break;
				case RESOURCE:
					final org.openiam.idm.srvc.res.dto.Resource resoruce = resourceDataService.getResource(associationId, getCurrentLanguage());
					displayName = (resoruce != null) ? resoruce.getName() : null;
					break;
				case ROLE:
					final Role role = roleServiceClient.getRoleLocalized(associationId, userId, getCurrentLanguage());
					displayName = (role != null) ? role.getName() : null;
					break;
				//case SUPERVISOR:
					/*
					final User supervisor = userDataWebService.getUserWithDependent(associationId, userId, false);
					displayName = (supervisor != null) ? supervisor.getDisplayName() : null;
					*/
					//displayName = getLocalizedMessage("openiam.ui.user.supervisor", null);
					//break;
				case TARGET_USER:
					final User targetUser = userDataWebService.getUserWithDependent(associationId, userId, false);
					displayName = (targetUser != null) ? targetUser.getDisplayName() : null;
					break;
				case USER:
					final User user = userDataWebService.getUserWithDependent(associationId, userId, false);
					displayName = (user != null) ? user.getDisplayName() : null;
					break;
				default:
					break;
			}
			if(AssociationType.SUPERVISOR.equals(associationType)) {
				displayName = getLocalizedMessage("openiam.ui.user.supervisor", null);
			} else if(displayName != null) {
				displayName = String.format("%s - %s", associationType.getValue(), displayName);
			}
		}
		return displayName;
	}
	
	private void setCommonAttributes(final HttpServletRequest request, 
									 final AssociationType assocationType,
									 final String entityId) throws JsonGenerationException, JsonMappingException, IOException {
		
		final List<IdNameBean> approvables = new LinkedList<IdNameBean>();
		final List<IdNameBean> notifiables = new LinkedList<IdNameBean>();
		for(final AssociationType type : AssociationType.values()) {
			final IdNameBean bean = new IdNameBean(type.getValue(), type.getValue());
			if(type.getIsNotifiable()) {
				notifiables.add(bean);
			}
			if(type.getIsApprover()) {
				approvables.add(bean);
			}
		}
		request.setAttribute("notifiables", jacksonMapper.writeValueAsString(notifiables));
		request.setAttribute("approvables", jacksonMapper.writeValueAsString(approvables));
		request.setAttribute("entityId", entityId);
		request.setAttribute("assocationTypeEnum", assocationType);
		request.setAttribute("associationType", assocationType.getValue());
		switch (assocationType) {
			case GROUP:
				final Group group = groupServiceClient.getGroup(entityId, null);
				request.setAttribute("entityName", group.getName());
				setMenuTree(request, groupEditMenuName);
				break;
			case ROLE:
				final Role role = roleServiceClient.getRoleLocalized(entityId, null, getCurrentLanguage());
				request.setAttribute("entityName", role.getName());
				setMenuTree(request, roleEditMenuName);
				break;
			case RESOURCE:
				final org.openiam.idm.srvc.res.dto.Resource resource = resourceDataService.getResource(entityId, getCurrentLanguage());
				request.setAttribute("entityName", resource.getName());
				setMenuTree(request, resourceEditPageRootMenuName);
				break;
			case ORGANIZATION:
				final Organization organization = organizationDataService.getOrganizationLocalized(entityId, null, getCurrentLanguage());
				request.setAttribute("entityName", organization.getName());
				setMenuTree(request, organizationEditMenuName);
				break;
			default:
				break;
		}
		
		final List<ApproverAssociationModel> associationModels = getApproverAssociationModal(request, assocationType, entityId);
		request.setAttribute("associationModels", (associationModels != null) ? jacksonMapper.writeValueAsString(associationModels) : null);
	}
}
