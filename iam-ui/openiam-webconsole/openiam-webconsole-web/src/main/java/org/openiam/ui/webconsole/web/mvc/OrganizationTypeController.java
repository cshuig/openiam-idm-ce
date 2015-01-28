package org.openiam.ui.webconsole.web.mvc;

import org.apache.commons.lang.StringUtils;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.searchbeans.OrganizationTypeSearchBean;
import org.openiam.idm.srvc.org.dto.OrganizationType;
import org.openiam.idm.srvc.org.service.OrganizationTypeDataService;
import org.openiam.ui.rest.api.model.KeyNameDescriptionBean;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.model.BeanResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class OrganizationTypeController extends AbstractWebconsoleController {
	
	@Value("${org.openiam.ui.organization.type.root.menu.id}")
	private String rootMenu;
	
	@Value("${org.openiam.ui.organization.type.edit.menu.id}")
	private String editMenu;

	@Resource(name="organizationTypeClient")
	private OrganizationTypeDataService organizationTypeClient;
	
	@RequestMapping(value="/organizationTypeSearch", method=RequestMethod.GET)
	public String organizationTypeSearch(final HttpServletRequest request,
			   							 final HttpServletResponse response) {
		setMenuTree(request, rootMenu);
		return "organization/type/search";
	}
	
	@RequestMapping(value="/organizationTypeMembership", method=RequestMethod.GET)
	public String organizationTypeEntitlements(final HttpServletRequest request,
											   final HttpServletResponse response,
			   					   			   final @RequestParam(required=false, value="id") String id,
			   					   			   @RequestParam(required=false, value="type") String type) throws IOException {
		final OrganizationType organizationType = organizationTypeClient.findByIdLocalized(id, getCurrentLanguage());
		if(organizationType == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		type = StringUtils.isBlank(type) ? "children" : type;
		setMenuTree(request, editMenu);
		request.setAttribute("organizationType", organizationType);
		request.setAttribute("type", type);
		return "organization/type/membership";
	}
	
	@RequestMapping(value="/organizationTypeEdit", method=RequestMethod.GET)
	public String organizationType(final HttpServletRequest request,
								   final @RequestParam(required=false, value="id") String id) throws Exception {
		OrganizationType type = null;
		if(StringUtils.isNotBlank(id)) {
			setMenuTree(request, editMenu);
			type = organizationTypeClient.findByIdLocalized(id, getCurrentLanguage());
		} else {
			setMenuTree(request, rootMenu);
			type = new OrganizationType();
		}
		request.setAttribute("organizationType", type);
		request.setAttribute("typeAsJSON", jacksonMapper.writeValueAsString(type));
		return "organization/type/edit";
	}
	
	@RequestMapping(value="/organization/type/rest/search", method=RequestMethod.GET)
	public @ResponseBody BeanResponse findOrganizationTypes(final HttpServletRequest request,
															final @RequestParam(required=false, value="parentId") String parentId,
															final @RequestParam(required=false, value="childId") String childId,
            												final @RequestParam(required=false, value="id") String typeId,
            												final @RequestParam(required=true, value="size") Integer size,
            												final @RequestParam(required=true, value="from") Integer from) {
		final OrganizationTypeSearchBean searchBean = new OrganizationTypeSearchBean();
		searchBean.setKey(StringUtils.trimToNull(typeId));
		searchBean.setDeepCopy(false);
		searchBean.addChildId(childId);
		searchBean.addParentId(parentId);
		
		final Integer count = (from.intValue() == 0) ? organizationTypeClient.count(searchBean) : null;
        final List<OrganizationType> results = organizationTypeClient.findBeans(searchBean, from, size, getCurrentLanguage());
        return new BeanResponse(mapper.mapToList(results, KeyNameDescriptionBean.class),count);
	}

	@RequestMapping(value="/organizationTypeEdit", method=RequestMethod.POST)
	public String save(final HttpServletRequest request, 
					   final HttpServletResponse response,
					   @RequestBody OrganizationType type) {
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		
		if(!ajaxResponse.isError()) {
			final Response wsResponse = organizationTypeClient.save(type);
			if(ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
				ajaxResponse.setStatus(HttpServletResponse.SC_OK);
				ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.ORGANIZATION_TYPE_SAVED));
				ajaxResponse.setRedirectURL(String.format("organizationTypeEdit.html?id=%s", (String)wsResponse.getResponseValue()));
			} else {
				Errors error = Errors.SAVE_ORGANIZATION_TYPE_FAILED;
				if(wsResponse.getErrorCode() != null) {
					switch(wsResponse.getErrorCode()) {
						case NO_NAME:
							error = Errors.ORGANIZAION_TYPE_NAME_REQUIRED;
							break;
						case NAME_TAKEN:
							error = Errors.ORGANIZAION_TYPE_NAME_TAKEN;
							break;
						default:
							break;
					}
				} 
				ajaxResponse.addError(new ErrorToken(error));
				ajaxResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		}
		
		request.setAttribute("response", ajaxResponse);
		return "common/basic.ajax.response";
	}
	
	@RequestMapping(value="/organizationTypeDelete", method=RequestMethod.POST)
	public String delete(final HttpServletRequest request, 
					   	 final HttpServletResponse response,
					   	 final @RequestParam(required=true, value="id") String typeId) {
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		
		if(!ajaxResponse.isError()) {
			final Response wsResponse = organizationTypeClient.delete(typeId);
			if(ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
				ajaxResponse.setStatus(HttpServletResponse.SC_OK);
				ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.ORGANIZATION_TYPE_DELETED));
				ajaxResponse.addContextValues("organizationTypeId", (String)wsResponse.getResponseValue());
			} else {
				Errors error = Errors.DELETE_ORGANIZATION_TYPE_FAILED;
				if(wsResponse.getErrorCode() != null) {
					switch(wsResponse.getErrorCode()) {
						case ORGANIZATION_TYPE_CHILDREN_EXIST:
							error = Errors.ORGANIZAION_TYPE_HAS_CHILDREN;
							break;
						case ORGANIZATION_TYPE_PARENTS_EXIST:
							error = Errors.ORGANIZAION_TYPE_HAS_PARENTS;
							break;
						case ORGANIZATION_TYPE_TIED_TO_ORGANIZATION:
							error = Errors.ORGANIZAION_TYPE_HAS_ORGS;
							break;
						default:
							break;
					}
				} 
				ajaxResponse.addError(new ErrorToken(error));
				ajaxResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		}
		
		request.setAttribute("response", ajaxResponse);
		return "common/basic.ajax.response";
	}
	
	private BasicAjaxResponse getResponseAfterChildTypeManipulation(final Response wsResponse, final boolean isDelete) {
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		if(ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
			ajaxResponse.setStatus(200);
			ajaxResponse.setSuccessToken(new SuccessToken((isDelete) ? SuccessMessage.CHILD_RESOURCE_DELETED : SuccessMessage.CHILD_RESOURCE_ADDED));
		} else {
			Errors error = Errors.INTERNAL_ERROR;
			if(wsResponse.getErrorCode() != null) {
				switch(wsResponse.getErrorCode()) {
					case RELATIONSHIP_EXISTS:
						error = Errors.RELATIONSHIP_EXISTS;
						break;
				 	case CIRCULAR_DEPENDENCY:
				 		error = Errors.CIRCULAR_DEPENDENCY;
				 		break;
					default:
						break;
				}
			}
			ajaxResponse.addError(new ErrorToken(error));
		}
		return ajaxResponse;
	}
}
