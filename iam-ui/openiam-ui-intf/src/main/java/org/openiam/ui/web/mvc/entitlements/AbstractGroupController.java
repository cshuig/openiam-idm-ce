package org.openiam.ui.web.mvc.entitlements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.searchbeans.GroupSearchBean;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.openiam.base.ws.Response;
import org.openiam.exception.EsbErrorToken;
import org.openiam.idm.searchbeans.MetadataTypeSearchBean;
import org.openiam.idm.srvc.audit.constant.AuditAction;
import org.openiam.idm.srvc.audit.dto.IdmAuditLog;
import org.openiam.idm.srvc.auth.dto.IdentityDto;
import org.openiam.idm.srvc.auth.ws.IdentityWebService;
import org.openiam.idm.srvc.grp.dto.Group;
import org.openiam.idm.srvc.grp.ws.GroupDataWebService;
import org.openiam.idm.srvc.meta.domain.MetadataTypeGrouping;
import org.openiam.idm.srvc.meta.dto.MetadataType;
import org.openiam.idm.srvc.org.dto.OrganizationType;
import org.openiam.provision.dto.ProvisionGroup;
import org.openiam.provision.service.ObjectProvisionService;
import org.openiam.ui.util.ErrorUtils;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.mvc.AbstractController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public abstract class AbstractGroupController extends AbstractEntityEntitlementsController<Group> {
	@Resource(name = "groupProvisionServiceClient")
	protected ObjectProvisionService<ProvisionGroup> groupProvisionService;

	@Resource(name = "identityServiceClient")
	protected IdentityWebService identityService;

	@Resource(name="groupServiceClient")
	protected GroupDataWebService groupServiceClient;
	
	@RequestMapping(value="/groupEntitlements", method=RequestMethod.GET)
	public String roleEntitlements(final HttpServletRequest request,
								   final HttpServletResponse response,
								   final @RequestParam(value="id", required=true) String groupId,
								   @RequestParam(value="type", required=false) String type) throws IOException {

        String requesterId = getRequesterId(request);

		if(StringUtils.isBlank(type)) {
			type = "childgroups";
		}
		
		final Group group = groupServiceClient.getGroup(groupId, requesterId);
		if(group == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, String.format("Group '%s' does not exist", groupId));
			return null;
		}
		
		request.setAttribute("group", group);
		request.setAttribute("type", type);
		setMenuTree(request, getEditMenu());
		return "jar:groups/entitlements";
	}
	
	@RequestMapping(value="/deleteGroup", method=RequestMethod.POST)
	public String deleteGroup(final HttpServletRequest request,
							  final HttpServletResponse response,
							  final @RequestParam(value="id", required=true) String groupId) throws Exception {
		final Group group = getEntity(groupId, request);
		final BasicAjaxResponse ajaxResponse = doDelete(request, response, group);
		request.setAttribute("response", ajaxResponse);
		return "common/basic.ajax.response";
	}
	
	@RequestMapping(value="/saveGroup", method=RequestMethod.POST)
	public String saveGroup(final HttpServletRequest request,
							final HttpServletResponse response,
						    @RequestBody final Group group) throws Exception {
		final BasicAjaxResponse ajaxResponse = doEdit(request, response, group);
		request.setAttribute("response", ajaxResponse);
		return "common/basic.ajax.response";
	}
	
	@RequestMapping(value="/editGroup", method=RequestMethod.GET)
	public String editGroup(final HttpServletRequest request,
						    final HttpServletResponse response,
						    final @RequestParam(required=false,value="id") String groupId,
							final @RequestParam(required=false,value="groupTypeId") String groupTypeId) throws IOException {
        final String requesterId = getRequesterId(request);
		final List<List<OrganizationType>> orgTypes = getOrgTypeList();
        Group group = new Group();
		if(StringUtils.isNotBlank(groupId)) {
			// edit group
            group = groupServiceClient.getGroupLocalize(groupId,requesterId, getCurrentLanguage());
            if(group == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, String.format("Group '%s' does not exist", groupId));
                return null;
            }
		} else if(StringUtils.isBlank(groupId) && StringUtils.isBlank(groupTypeId)){
			final List<MetadataType> groupTypes = this.getMetadataTypesByGrouping(MetadataTypeGrouping.GROUP_TYPE);
			request.setAttribute("groupTypes", groupTypes);
			setMenuTree(request, getRootMenu());
			return "jar:groups/selectGroupType";
		} else if(StringUtils.isBlank(groupId) && StringUtils.isNotBlank(groupTypeId)){
			MetadataType type = this.getMetadataTypeById(groupTypeId);
			group.setMdTypeId(type.getId());
			group.setMetadataTypeName(type.getDisplayName());

		}
		
		request.setAttribute("isNew", StringUtils.isBlank(group.getId()));
		request.setAttribute("groupAsJSON", jacksonMapper.writeValueAsString(group));
		request.setAttribute("orgHierarchy", (orgTypes != null) ? jacksonMapper.writeValueAsString(orgTypes) : null);
		request.setAttribute("group", group);
		
		//request.setAttribute("organizationList", getOrganizationBeanList(requesterId));
		request.setAttribute("managedSystems", getManagedSystemsAsKeyNameBeans());
		
		if(StringUtils.isNotBlank(groupId)) {
			setMenuTree(request, getEditMenu());
		} else {
			setMenuTree(request, getRootMenu());
		}
		return "jar:groups/editGroup";
	}
	
	@RequestMapping(value="/groups", method=RequestMethod.GET)
	public String searchGroups(final HttpServletRequest request, 
							   final HttpServletResponse response) throws Exception {
		setMenuTree(request, getRootMenu());
		request.setAttribute("managedSystems", jacksonMapper.writeValueAsString(getManagedSystemsAsKeyNameBeans()));
		return "jar:groups/search";
	}

    @RequestMapping(value = "/addChildGroup", method = RequestMethod.POST)
    public String addChildGroup(final HttpServletRequest request, final @RequestParam(required = true, value = "groupId") String groupId,
                                final @RequestParam(required = true, value = "childGroupId") String childGroupId) {
        final BasicAjaxResponse ajaxResponse = addGroup2Group(request, groupId, childGroupId);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/removeChildGroup", method = RequestMethod.POST)
    public String removeChildGroup(final HttpServletRequest request, final @RequestParam(required = true, value = "groupId") String groupId,
                                   final @RequestParam(required = true, value = "childGroupId") String childGroupId) {
        final BasicAjaxResponse ajaxResponse = removeGroupFromGroup(request, groupId, childGroupId);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }
    
    @Override
    protected Group getEntity(final String id, final HttpServletRequest request) {
    	return groupServiceClient.getGroup(id, getRequesterId(request));
    }
	
	@Override
	protected final List<ErrorToken> getEditErrors(final Response wsResponse, final HttpServletRequest request, final Group entity) {
		final List<ErrorToken> retVal = new LinkedList<ErrorToken>();
		if(wsResponse != null && wsResponse.isFailure()) {
			if(wsResponse.getErrorCode() != null) {
				switch(wsResponse.getErrorCode()) {
					case NO_NAME:
						retVal.add(new ErrorToken(Errors.INVALID_GROUP_NAME));
						break;
					case NAME_TAKEN:
						retVal.add(new ErrorToken(Errors.GROUP_NAME_TAKEN));
						break;
					case VALIDATION_ERROR:
						retVal.addAll(ErrorUtils.getESBErrorTokens(wsResponse));
						break;
					default:
						retVal.add(new ErrorToken(Errors.INTERNAL_ERROR));
						break;
				}
			} else {
				retVal.add(new ErrorToken(Errors.INTERNAL_ERROR));
			}
		}
		return retVal;
	}

	protected BasicAjaxResponse doEdit(final HttpServletRequest request, final HttpServletResponse response, final Group group, String redirectUrl) throws Exception {
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();

		String requesterId = getRequesterId(request);
		IdmAuditLog idmAuditLog = new IdmAuditLog();
		idmAuditLog.setRequestorUserId(getRequesterId(request));
		idmAuditLog.setRequestorPrincipal(getRequesterPrincipal(request));
		boolean isNew = group.getId() == null;
		if(isNew) {
			idmAuditLog.setAction(AuditAction.ADD_GROUP.value());
			idmAuditLog.setAuditDescription("Create new group");
		} else {
			idmAuditLog.setAction(AuditAction.EDIT_GROUP.value());
			idmAuditLog.setAuditDescription("Edit new group");
		}

		final Response wsResponse = groupServiceClient.saveGroup(group, requesterId);

		if(wsResponse.isSuccess()) {
			String groupId = (String) wsResponse.getResponseValue();
			Group createdGroup  = groupServiceClient.getGroup(groupId, requesterId);
			ProvisionGroup provisionGroup = new ProvisionGroup(createdGroup);
			Response groupResponse = isNew ? groupProvisionService.add(provisionGroup):
					groupProvisionService.modify(provisionGroup);
			//TODO group provisioning processing the result

			idmAuditLog.setTargetGroup(groupId, group.getName());
			ajaxResponse.setStatus(200);
			ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.GROUP_SAVED));
			if(StringUtils.isBlank(redirectUrl)){
				if(StringUtils.isBlank(group.getId())) {
					ajaxResponse.setRedirectURL(new StringBuilder("editGroup.html?id=").append(wsResponse.getResponseValue()).toString());
				}
			}else{
				ajaxResponse.setRedirectURL(redirectUrl);
			}

			idmAuditLog.succeed();
		} else {
			ajaxResponse.addErrors(getEditErrors(wsResponse, request, group));
			idmAuditLog.fail();
			idmAuditLog.setFailureReason(wsResponse.getErrorCode());
			idmAuditLog.setFailureReason(wsResponse.getErrorText());
			idmAuditLog.setTargetGroup(group.getId(), group.getName());
		}
		auditLogService.addLog(idmAuditLog);
		return ajaxResponse;
	}

	@Override
	protected BasicAjaxResponse doDelete(final HttpServletRequest request, final HttpServletResponse response, final Group entity) throws Exception {
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		final String callerId = getRequesterId(request);
		IdmAuditLog idmAuditLog = new IdmAuditLog();
		idmAuditLog.setRequestorUserId(callerId);
		idmAuditLog.setAction(AuditAction.DELETE_GROUP.value());
		idmAuditLog.setAuditDescription("Delete group");
		idmAuditLog.setTargetGroup(entity.getId(), entity.getName());

		List<IdentityDto> identityDtos = identityService.getIdentities(entity.getId());
		Response wsResponse = null;
		if(identityDtos != null && identityDtos.size() > 0) {
			wsResponse = groupProvisionService.remove(entity.getId(), callerId);
		}
        wsResponse = groupServiceClient.deleteGroup(entity.getId(), callerId);


		if(wsResponse.isSuccess()) {
			new Response(ResponseStatus.SUCCESS);
			ajaxResponse.setStatus(200);
			ajaxResponse.setRedirectURL("groups.html");
			ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.GROUP_DELETED));
			idmAuditLog.succeed();
		} else {
			ajaxResponse.addErrors(getDeleteErrors(wsResponse, request, entity));
			idmAuditLog.fail();
			idmAuditLog.setFailureReason(wsResponse.getErrorCode());
			idmAuditLog.setFailureReason(wsResponse.getErrorText());
		}
		auditLogService.addLog(idmAuditLog);

		return ajaxResponse;
	}

//	@Override
	protected BasicAjaxResponse addGroup2Group(HttpServletRequest request,
											   String groupId, String childGroupId) {
		final String callerId = getRequesterId(request);
		final Response wsResponse = groupServiceClient.addChildGroup(groupId, childGroupId, callerId);
		return getResponseAfterEntity2EntityAddition(wsResponse, false);
	}

//	@Override
	protected BasicAjaxResponse removeGroupFromGroup(
			HttpServletRequest request, String groupId, String childGroupId) {
		final String callerId = getRequesterId(request);
		final Response wsResponse = groupServiceClient.removeChildGroup(groupId, childGroupId.toLowerCase(), callerId);
		return getResponseAfterEntity2EntityAddition(wsResponse, true);
	}
	
//	protected abstract BasicAjaxResponse addGroup2Group(final HttpServletRequest request, final String groupId, final String childGroupId);
//	protected abstract BasicAjaxResponse removeGroupFromGroup(final HttpServletRequest request, final String groupId, final String childGroupId);
}
