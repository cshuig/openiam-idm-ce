package org.openiam.ui.web.mvc.entitlements;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.openiam.base.ws.Response;
import org.openiam.idm.searchbeans.RoleSearchBean;
import org.openiam.idm.srvc.role.dto.Role;
import org.openiam.idm.srvc.role.ws.RoleDataWebService;
import org.openiam.ui.util.ErrorUtils;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractRoleController extends AbstractEntityEntitlementsController<Role> {
	
	@Resource(name="roleServiceClient")
	protected RoleDataWebService roleServiceClient;
	
	
	@RequestMapping(value="/roles", method=RequestMethod.GET)
	public String searchRoles(final HttpServletRequest request, 
							  final HttpServletResponse response) throws Exception {
		setMenuTree(request, getRootMenu());
		request.setAttribute("managedSystems", jacksonMapper.writeValueAsString(getManagedSystemsAsKeyNameBeans()));
		return "jar:roles/search";
	}
	
	@RequestMapping(value="/editRole", method=RequestMethod.GET)
	public String editRole(final HttpServletRequest request,
						   final HttpServletResponse response,
						   final @RequestParam(required=false,value="id") String roleId) throws IOException {
        String requesterId = getRequesterId(request);
        Role role = new Role();
		if(StringUtils.isNotBlank(roleId)) {
			role = roleServiceClient.getRoleLocalized(roleId, requesterId, getCurrentLanguage());
            if(role==null){
                response.sendError(HttpServletResponse.SC_NOT_FOUND, String.format("Role with ID: '%s' not found", roleId));
                return null;
            }
		}
		request.setAttribute("roleAsJSON", jacksonMapper.writeValueAsString(role));
		request.setAttribute("role", role);
		request.setAttribute("managedSystems", getManagedSystemsAsKeyNameBeans());
		if(StringUtils.isNotBlank(roleId)) {
			setMenuTree(request, getEditMenu());
		} else {
			setMenuTree(request, getRootMenu());
		}
		return "jar:roles/editRole";
	}
	
	@RequestMapping(value="/deleteRole", method=RequestMethod.POST)
	public String deleteRole(final HttpServletRequest request,
							 final HttpServletResponse response,
							 final @RequestParam(value="id", required=true) String roleId) throws Exception {
		final Role role = getEntity(roleId, request);
		final BasicAjaxResponse ajaxResponse = doDelete(request, response, role);
		request.setAttribute("response", ajaxResponse);
		return "common/basic.ajax.response";
	}
	
	@RequestMapping(value="/saveRole", method=RequestMethod.POST)
	public String saveRole(final HttpServletRequest request,
						   final HttpServletResponse response,
						   @RequestBody final Role role) throws Exception {

		final BasicAjaxResponse ajaxResponse = doEdit(request, response, role);
		
		request.setAttribute("response", ajaxResponse);
		return "common/basic.ajax.response";
	}
	
	@Override
	protected List<ErrorToken> getEditErrors(final Response wsResponse, final HttpServletRequest request, final Role entity) {
		final List<ErrorToken> errorList = new LinkedList<ErrorToken>();
		if(wsResponse == null) {
			errorList.add(new ErrorToken(Errors.INTERNAL_ERROR));
		} else if(wsResponse.isFailure()) {
			if(wsResponse.getErrorCode() == null) {
				errorList.add(new ErrorToken(Errors.INTERNAL_ERROR));
			} else {
				switch(wsResponse.getErrorCode()) {
					case NO_NAME:
						errorList.add(new ErrorToken(Errors.INVALID_ROLE_NAME));
						break;
					case NAME_TAKEN:
						errorList.add(new ErrorToken(Errors.ROLE_NAME_TAKEN));
						break;
					case INVALID_ROLE_DOMAIN:
						errorList.add(new ErrorToken(Errors.INVALID_ROLE_DOMAIN));
						break;
	                case VALIDATION_ERROR:
	                	errorList.addAll(ErrorUtils.getESBErrorTokens(wsResponse));
	                    break;
					default:
						errorList.add(new ErrorToken(Errors.INTERNAL_ERROR));
						break;
				}
			}
		}
		return errorList;
	}
	
	@RequestMapping(value="/roleEntitlements", method=RequestMethod.GET)
	public String roleEntitlements(final HttpServletRequest request,
								   final HttpServletResponse response,
								   final @RequestParam(value="id", required=true) String roleId,
								   @RequestParam(value="type", required=false) String type) throws IOException {
        String requesterId = getRequesterId(request);

		if(StringUtils.isBlank(type)) {
			type = "childroles";
		}
		
		final RoleSearchBean searchBean = new RoleSearchBean();
		searchBean.setKey(roleId);
		searchBean.setDeepCopy(false);
		List<Role> resultList = roleServiceClient.findBeans(searchBean,requesterId, 0, 1);
		if(CollectionUtils.isEmpty(resultList)) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, String.format("Role with ID: '%s' not found", roleId));
			return null;
		}
		
		final Role role = resultList.get(0);
		request.setAttribute("role", role);
		request.setAttribute("type", type);
		setMenuTree(request, getEditMenu());
		return "jar:roles/entitlements";
	}
	
    @RequestMapping(value = "/addGroupToRole", method = RequestMethod.POST)
    public String addGroupToRole(final HttpServletRequest request, final @RequestParam(required = true, value = "roleId") String roleId,
                                 final @RequestParam(required = true, value = "groupId") String groupId) {
        final BasicAjaxResponse ajaxResponse = doAddRole2Group(request, roleId, groupId);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/removeGroupFromRole", method = RequestMethod.POST)
    public String removeGroupFromRole(final HttpServletRequest request, final @RequestParam(required = true, value = "roleId") String roleId,
                                      final @RequestParam(required = true, value = "groupId") String groupId) {
        final BasicAjaxResponse ajaxResponse = doRemoveRoleFromGroup(request, roleId, groupId);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/addChildRole", method = RequestMethod.POST)
    public String addChildRole(final HttpServletRequest request, final @RequestParam(required = true, value = "roleId") String roleId,
                               final @RequestParam(required = true, value = "childRoleId") String childRoleId) {
        final BasicAjaxResponse ajaxResponse = doAddRole2Role(request, roleId, childRoleId);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/removeChildRole", method = RequestMethod.POST)
    public String removeChildRole(final HttpServletRequest request, final @RequestParam(required = true, value = "roleId") String roleId,
                                  final @RequestParam(required = true, value = "childRoleId") String childRoleId) {
        final BasicAjaxResponse ajaxResponse = doRemoveRoleFromRole(request, roleId, childRoleId);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }
    
    @Override
    protected Role getEntity(final String id, final HttpServletRequest request) {
    	return roleServiceClient.getRoleLocalized(id, getRequesterId(request), getCurrentLanguage());
    }
	
	protected abstract BasicAjaxResponse doAddRole2Role(final HttpServletRequest request, final String roleId, final String childRoleId);
	protected abstract BasicAjaxResponse doRemoveRoleFromRole(final HttpServletRequest request, final String roleId, final String childRoleId);
	protected abstract BasicAjaxResponse doAddRole2Group(final HttpServletRequest request, final String roleId, final String groupId);
	protected abstract BasicAjaxResponse doRemoveRoleFromGroup(final HttpServletRequest request, final String roleId, final String groupId);
}
