package org.openiam.ui.webconsole.am.web.mvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.openiam.am.srvc.dto.AuthLevelAttribute;
import org.openiam.am.srvc.dto.AuthLevelGrouping;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.srvc.meta.domain.MetadataTypeGrouping;
import org.openiam.idm.srvc.meta.dto.MetadataType;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.mvc.AbstractController;
import org.openiam.ui.webconsole.am.web.model.AuthLevelAttributeBean;
import org.openiam.ui.webconsole.am.web.model.AuthLevelAttributeFormRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthLevelGroupingController extends AbstractController {

	@Value("${org.openiam.auth.level.grouping.new.menu}")
	private String newAuthLevelGroupingMenu;
	
	@Value("${org.openiam.auth.level.grouping.edit.menu}")
	private String editAuthLevelGroupingMenu;

	@Value("${org.openiam.auth.level.grouping.attribute.menu}")
	private String authLevelAttributeMenu;
	
	@RequestMapping("/authLevelGroupings")
	public String authLevelGroupings(final HttpServletRequest request) {
		setMenuTree(request, newAuthLevelGroupingMenu);
		return "authlevel/search";
	}
	
	@RequestMapping("/deleteAuthLevelGrouping")
	public String deleteAuthLevelGrouping(final HttpServletRequest request,
										  final @RequestParam(required=true, value="id") String id) throws Exception {
		ErrorToken errorToken = null;
        SuccessToken successToken = null;
        
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        //TODO: validate
        Response response = null;
        try {
            response = contentProviderServiceClient.deleteAuthLevelGrouping(id);

            if(response.getStatus() == ResponseStatus.SUCCESS) {
                successToken = new SuccessToken(SuccessMessage.AUTH_LEVEL_GROUPING_DELETED);
            } else {
                Errors error = Errors.COULD_NOT_SAVE_CONTENT_PROVIDER;
                if(response.getErrorCode() != null) {
                    switch(response.getErrorCode()) {
                        case AUTH_LEVEL_GROUPING_HAS_CONTENT_PROVIDERS:
                        	error = Errors.AUTH_LEVEL_GROUPING_HAS_CONTENT_PROVIDERS;
                            break;
                        case AUTH_LEVEL_GROUPING_HAS_PATTERNS:
                            error = Errors.AUTH_LEVEL_GROUPING_HAS_PATTERNS;
                            break;
                        default:
                            error = Errors.INTERNAL_ERROR;
                            break;
                    }
                }
                errorToken = new ErrorToken(error);
            }
        } catch(Throwable e) {
            errorToken = new ErrorToken(Errors.INTERNAL_ERROR);
            log.error("Exception while saving auth level grouping", e);
        } finally {
        	if(errorToken != null) {
        		ajaxResponse.setStatus(500);
        		ajaxResponse.addError(errorToken);
        	} else {
        		ajaxResponse.setRedirectURL("authLevelGroupings.html");
        		ajaxResponse.setSuccessToken(successToken);
        		ajaxResponse.setStatus(200);
        	}
            request.setAttribute("response", ajaxResponse);
        }
        return "common/basic.ajax.response";
	}
	
	@RequestMapping(value="/editAuthLevelGrouping", method=RequestMethod.GET)
	public String editAuthLevelGrouping(final HttpServletRequest request,
										final HttpServletResponse response,
										final @RequestParam(required=false, value="id") String id) throws Exception {
		AuthLevelGrouping grouping = new AuthLevelGrouping();
		if(StringUtils.isNotBlank(id)) {
			grouping = contentProviderServiceClient.getAuthLevelGrouping(id);
			if(grouping == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return null;
			}
		}
		
		/* for UI */
		final List<AuthLevelAttributeBean> beanList = mapper.mapToList(grouping.getAttributes(), AuthLevelAttributeBean.class);
		grouping.setAttributes(null);
		
		request.setAttribute("attributes", jacksonMapper.writeValueAsString(beanList));
		request.setAttribute("authLevels", getAuthLevelsAsKeyNameBeans());
		request.setAttribute("grouping", grouping);
		request.setAttribute("groupingAsJSON", jacksonMapper.writeValueAsString(grouping));
		setMenuTree(request, editAuthLevelGroupingMenu);
		return "authlevel/edit";
	}
	
	@RequestMapping(value="/editAuthLevelGrouping", method=RequestMethod.POST)
	public String saveAuthLevelGrouping(final HttpServletRequest request,
										final @RequestBody AuthLevelGrouping grouping) {
		ErrorToken errorToken = null;
        SuccessToken successToken = null;
        
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        //TODO: validate
        Response response = null;
        try {
        	response = contentProviderServiceClient.saveAuthLevelGrouping(grouping);

        	if(response.getStatus() == ResponseStatus.SUCCESS) {
        		successToken = new SuccessToken(SuccessMessage.AUTH_LEVEL_GROUPING_SAVED);
        	} else {
        		Errors error = Errors.COULD_NOT_SAVE_CONTENT_PROVIDER;
        		if(response.getErrorCode() != null) {
        			switch(response.getErrorCode()) {
        				case NAME_TAKEN:
        					error = Errors.AUTH_LEVEL_GROUPING_NAME_TAKEN;
        					break;
        				default:
        					error = Errors.INTERNAL_ERROR;
        					break;
        			}
        		}
        		errorToken = new ErrorToken(error);
        	}
        } catch(Throwable e) {
            errorToken = new ErrorToken(Errors.INTERNAL_ERROR);
            log.error("Exception while saving auth level grouping", e);
        } finally {
        	if(errorToken != null) {
        		ajaxResponse.setStatus(500);
        		ajaxResponse.addError(errorToken);
        	} else {
        		if(response != null && response.getResponseValue() != null) {
        			final String id = ((String)response.getResponseValue());
        			ajaxResponse.setRedirectURL(String.format("editAuthLevelGrouping.html?id=%s", id));
        		}
        		ajaxResponse.setSuccessToken(successToken);
        		ajaxResponse.setStatus(200);
        	}
            request.setAttribute("response", ajaxResponse);
        }
        return "common/basic.ajax.response";
	}
	
	@RequestMapping(value="/deleteAuthLevelAttribute", method=RequestMethod.POST)
	public String deleteAuthLevelAttribute(final HttpServletRequest request,
										   final @RequestParam(required=true, value="id") String id,
										   final @RequestParam(required=true, value="groupId") String groupId) {
		ErrorToken errorToken = null;
        SuccessToken successToken = null;
        
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        //TODO: validate
        Response response = null;
        try {
            response = contentProviderServiceClient.deleteAuthLevelAttribute(id);

            if(response.getStatus() == ResponseStatus.SUCCESS) {
                successToken = new SuccessToken(SuccessMessage.ATTRIBUTE_DELETED);
            } else {
                Errors error = Errors.ATTRIBUTE_COULD_NOT_DELETE;
                if(response.getErrorCode() != null) {
                    switch(response.getErrorCode()) {
                        default:
                            error = Errors.INTERNAL_ERROR;
                            break;
                    }
                }
                errorToken = new ErrorToken(error);
            }
        } catch(Throwable e) {
            errorToken = new ErrorToken(Errors.INTERNAL_ERROR);
            log.error("Exception while saving auth level grouping", e);
        } finally {
        	if(errorToken != null) {
        		ajaxResponse.setStatus(500);
        		ajaxResponse.addError(errorToken);
        	} else {
        		ajaxResponse.setRedirectURL(String.format("editAuthLevelGrouping.html?id=%s", groupId));
        		ajaxResponse.setSuccessToken(successToken);
        		ajaxResponse.setStatus(200);
        	}
            request.setAttribute("response", ajaxResponse);
        }
        return "common/basic.ajax.response";
	}
	
	@RequestMapping(value="/editAuthLevelGroupingAttibute", method=RequestMethod.POST)
	public String saveAuthLevelAttribute(final HttpServletRequest request,
										 final HttpServletResponse response,
										 final AuthLevelAttributeFormRequest formRequest) throws Exception {
		ErrorToken errorToken = null;
        SuccessToken successToken = null;
		
        Response wsResponse = null;
        try {
        	final AuthLevelAttribute attribute = convert(formRequest);
        	if(StringUtils.isNotBlank(formRequest.getId())) {
        		if(!formRequest.isModifiedFile()) {
        			attribute.setValueAsByteArray(contentProviderServiceClient.getAuthLevelAttribute(formRequest.getId()).getValueAsByteArray());
        		}
        	}
        	
        	wsResponse = contentProviderServiceClient.saveAuthLevelAttribute(attribute);

        	if(wsResponse.isSuccess()) {
        		successToken = new SuccessToken(SuccessMessage.ATTRIBUTE_SAVED);
        	} else {
                Errors error = Errors.ATTRIBUTE_COULD_NOT_SAVE;
                if(wsResponse.getErrorCode()  != null) {
                	switch(wsResponse.getErrorCode()) {
                		case TYPE_REQUIRED:
                			error = Errors.TYPE_IS_REQUIRED;
                			break;
                		case GROUPING_REQUIRED:
                			break;
                		case VALUE_REQUIRED:
                			error = Errors.VALUE_IS_REQUIRED;
                			break;
                		case NO_NAME:
                			error = Errors.NAME_REQUIRED;
                			break;
                        default:
                            error = Errors.INTERNAL_ERROR;
                            break;
                    }
                }
                errorToken = new ErrorToken(error);
        	}
        } catch(Throwable e) {
            errorToken = new ErrorToken(Errors.INTERNAL_ERROR);
            log.error("Exception while saving auth level grouping", e);
        }
        final AuthLevelGrouping grouping = contentProviderServiceClient.getAuthLevelGrouping(formRequest.getGroupingId());	
        
        if(errorToken != null) {
        	request.setAttribute("errorToken", errorToken);
        } else {
        	final String id = ((String)wsResponse.getResponseValue());
        	formRequest.setId(id);
       		request.setAttribute("successToken", successToken);
        }
        return getAuthLevelAttributePage(request, grouping, formRequest);
	}
	
	@RequestMapping(value="/editAuthLevelGroupingAttibute", method=RequestMethod.GET)
	public String editAuthLevelGroupingAttibute(final HttpServletRequest request,
												final HttpServletResponse response,
												final @RequestParam(required=true, value="groupingId") String groupingId,
												final @RequestParam(required=false, value="id") String id) throws Exception {
		final AuthLevelGrouping grouping = contentProviderServiceClient.getAuthLevelGrouping(groupingId);
		AuthLevelAttribute attribute = new AuthLevelAttribute();
		if(id != null) {
			attribute = contentProviderServiceClient.getAuthLevelAttribute(id);
			if(attribute == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return null;
			}
		} else {
			attribute.setGrouping(grouping);
		}
		return getAuthLevelAttributePage(request, grouping, convert(attribute));
	}
	
	private String getAuthLevelAttributePage(final HttpServletRequest request,
											 final AuthLevelGrouping grouping,
											 final AuthLevelAttributeFormRequest formRequest) throws Exception {
		final List<MetadataType> typeList = getMetadataTypesByGrouping(MetadataTypeGrouping.AUTH_LEVEL);
		final Map<String, MetadataType> typeMap = new HashMap<String, MetadataType>();
		if(CollectionUtils.isNotEmpty(typeList)) {
			for(final MetadataType type : typeList) {
				typeMap.put(type.getId(), type);
			}
		}
		
		setMenuTree(request, authLevelAttributeMenu);
		request.setAttribute("typeMap", jacksonMapper.writeValueAsString(typeMap));
		request.setAttribute("grouping", grouping);
		request.setAttribute("attribute", formRequest);
		request.setAttribute("types", typeList);
		return "authlevel/attributs/edit";
	}
	
	private AuthLevelAttribute convert(final AuthLevelAttributeFormRequest request) {
		final AuthLevelAttribute attribute = mapper.mapToObject(request, AuthLevelAttribute.class);
		if(request.getBytes() != null && ArrayUtils.isNotEmpty(request.getBytes().getBytes())) {
			attribute.setValueAsByteArray(request.getBytes().getBytes());
		}
		return attribute;
	}
	
	private AuthLevelAttributeFormRequest convert(final AuthLevelAttribute attribute) {
		final AuthLevelAttributeFormRequest request = mapper.mapToObject(attribute, AuthLevelAttributeFormRequest.class);
		if(ArrayUtils.isNotEmpty(attribute.getValueAsByteArray())) {
			request.setHasFile(true);
		}
		return request;
	}
}
