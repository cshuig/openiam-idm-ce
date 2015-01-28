package org.openiam.ui.web.mvc.entitlements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.openiam.idm.searchbeans.GroupSearchBean;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.openiam.base.ws.Response;
import org.openiam.exception.EsbErrorToken;
import org.openiam.idm.srvc.grp.dto.Group;
import org.openiam.idm.srvc.grp.ws.GroupDataWebService;
import org.openiam.ui.util.ErrorUtils;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.mvc.AbstractController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public abstract class AbstractGroupController extends AbstractEntityEntitlementsController<Group> {

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
						    final @RequestParam(required=false,value="id") String groupId) throws IOException {
        final String requesterId = getRequesterId(request);

        Group group = new Group();
		if(StringUtils.isNotBlank(groupId)) {
            group = groupServiceClient.getGroupLocalize(groupId,requesterId, getCurrentLanguage());
            if(group == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, String.format("Group '%s' does not exist", groupId));
                return null;
            }
		}
		
		request.setAttribute("isNew", StringUtils.isBlank(group.getId()));
		request.setAttribute("groupAsJSON", jacksonMapper.writeValueAsString(group));
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
	
	protected abstract BasicAjaxResponse addGroup2Group(final HttpServletRequest request, final String groupId, final String childGroupId);
	protected abstract BasicAjaxResponse removeGroupFromGroup(final HttpServletRequest request, final String groupId, final String childGroupId);
}
