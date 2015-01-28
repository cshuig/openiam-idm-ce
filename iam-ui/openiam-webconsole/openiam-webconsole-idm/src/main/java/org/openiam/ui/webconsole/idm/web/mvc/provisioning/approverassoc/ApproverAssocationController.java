package org.openiam.ui.webconsole.idm.web.mvc.provisioning.approverassoc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.dozer.DozerBeanMapper;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.srvc.grp.dto.Group;
import org.openiam.idm.srvc.grp.ws.GroupDataWebService;
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
		final List<ApproverAssociation> approverAssociationList = new LinkedList<ApproverAssociation>();
		if(bean != null && CollectionUtils.isNotEmpty(bean.getBeans())) {
			for(final ApproverAssociationModel model : bean.getBeans()) {
				approverAssociationList.add(model);
			}
		}
		
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		final Response wsResponse = managedSysServiceClient.saveApproverAssociations(approverAssociationList, bean.getType(), bean.getEntityId());
		Errors error = null;
		try {
			if(wsResponse == null) {
				error = Errors.APPROVER_ASSOC_SAVE_ERR;
			} else if(wsResponse.getStatus() == ResponseStatus.FAILURE) {
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
