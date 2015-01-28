package org.openiam.ui.webconsole.web.mvc;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.openiam.authmanager.common.model.AuthorizationMenu;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.searchbeans.MetadataElementSearchBean;
import org.openiam.idm.searchbeans.MetadataTypeSearchBean;
import org.openiam.idm.searchbeans.ResourceSearchBean;
import org.openiam.idm.srvc.meta.domain.MetadataTypeGrouping;
import org.openiam.idm.srvc.meta.dto.MetadataElement;
import org.openiam.idm.srvc.meta.dto.MetadataType;
import org.openiam.idm.srvc.org.dto.Organization;
import org.openiam.idm.srvc.res.dto.Resource;
import org.openiam.ui.rest.api.model.KeyNameBean;
import org.openiam.ui.rest.api.model.KeyNameDescriptionBean;
import org.openiam.ui.rest.api.model.MetadataElementBean;
import org.openiam.ui.rest.api.model.MetadataTypeBean;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.model.BeanResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MetadataController extends AbstractWebconsoleController {

    @Value("${org.openiam.ui.metadata.root.menu.id}")
    private String rootMetaMenu;
    @Value("${org.openiam.ui.metadata.type.root.menu.id}")
    private String rootMetaTypeMenu;

    @RequestMapping(value = "/metaDataTypeSearch", method = RequestMethod.GET)
    public String metadataTypeSearch(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		setMenuTree(request, rootMetaTypeMenu);
		request.setAttribute("groupingsAsJSON", jacksonMapper.writeValueAsString(MetadataTypeGrouping.values()));
		return "metadata/type/search";
    }

    @RequestMapping(value = "/metaDataSearch", method = RequestMethod.GET)
    public String metadataSearch(final HttpServletRequest request, final HttpServletResponse response)
	    throws JsonGenerationException, JsonMappingException, IOException {
		final List<KeyNameBean> metadataTypes = getMetaTypeListAsKeyNameBeans();
		setMenuTree(request, rootMetaMenu);
		request.setAttribute("metadataTypes", (metadataTypes != null) ? jacksonMapper.writeValueAsString(metadataTypes) : null);
		return "metadata/search";
    }

    @RequestMapping(value = "/metaDataTypeEdit", method = RequestMethod.GET)
    public String editMetadataType(final HttpServletRequest request, 
    							   final HttpServletResponse response,
    							   final @RequestParam(required = false, value = "id") String id) throws IOException {
        final List<KeyNameBean> metadataTypes = getMetaTypeListAsKeyNameBeans();
		MetadataType metadataType = new MetadataType();
		if (StringUtils.isNotBlank(id)) {
		    metadataType = getMetadataTypeById(id);
		    if (metadataType == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return null;
		    }
		}
		
		
		request.setAttribute("groupings", MetadataTypeGrouping.values());
		request.setAttribute("metadataTypeAsJSON", jacksonMapper.writeValueAsString(metadataType));
		request.setAttribute("metadataType", metadataType);
        request.setAttribute("metadataTypeId", metadataType.getId());
		setMenuTree(request, rootMetaTypeMenu);
        request.setAttribute("metadataTypes", (metadataTypes != null) ? jacksonMapper.writeValueAsString(metadataTypes) : null);
		return "metadata/type/editMetatype";
    }

    @RequestMapping(value = "/metaDataEdit", method = RequestMethod.GET)
    public String editMetadata(final HttpServletRequest request, 
    						   final HttpServletResponse response,
    						   final @RequestParam(required = false, value = "id") String id,
                               final @RequestParam(required = false, value = "typeid") String typeid) throws IOException {
		MetadataElement element = new MetadataElement();
		if (StringUtils.isNotBlank(id)) {
			element = getMetadataElement(id);
		    if (element == null) {
		    	response.sendError(HttpServletResponse.SC_NOT_FOUND);
		    	return null;
		    }
		} else if (StringUtils.isNotBlank(typeid)){
            final List<KeyNameBean> metadataTypes = getMetaTypeListAsKeyNameBeans();
            MetadataType metadataType = new MetadataType();
                metadataType = getMetadataTypeById(typeid);
                if (metadataType != null) {
                    element.setMetadataTypeId(metadataType.getId());
                    element.setMetadataTypeName(metadataType.getDisplayName());
                }
        }

	
		request.setAttribute("metadataElement", element);
		request.setAttribute("elementAsJSON", jacksonMapper.writeValueAsString(element));
		request.setAttribute("metaTypes", getMetaTypeListAsKeyNameBeans());
	
		if (element.getResourceId() != null) {
			final Resource res = resourceDataService.getResource(element.getResourceId(), getCurrentLanguage());
		    request.setAttribute("resource", res);
		}
		if (element.getMetadataTypeId() != null) {
		    final MetadataType metaT = getMetadataTypeById(element.getMetadataTypeId()); //metadataServiceClient.findTypeById(metadata.getMetadataTypeId());
		    request.setAttribute("metaType", metaT);
		}
        AuthorizationMenu root = getMenuTree(request, rootMetaMenu);
        root.getFirstChild().setUrlParams("id=" + element.getMetadataTypeId());
        String menuTreeString = null;
        try {
            menuTreeString = (root != null) ? jacksonMapper.writeValueAsString(root) : null;
        } catch (Throwable e) {
            log.error("Can't serialize menu tree", e);
        }
        request.setAttribute("menuTree", menuTreeString);

		//setMenuTree(request, rootMetaMenu);
		return "metadata/editMetadata";
    }

    @RequestMapping(value = "/metaDataEdit", method = RequestMethod.POST)
    public String saveMetadata(final HttpServletRequest request, 
    						   final HttpServletResponse response,
    						   @RequestBody MetadataElement element) {
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
	    final Response wsResponse = metadataServiceClient.saveMetadataEntity(element);
        String typeId = element.getMetadataTypeId();
	    if (ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
			ajaxResponse.setStatus(HttpServletResponse.SC_OK);
			ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.METADATA_ELEMENT_SAVED));
			ajaxResponse.setRedirectURL(String.format("metaDataTypeEdit.html?id=%s", typeId));
	    } else {
			Errors error = Errors.SAVE_METADATA_ELEMENT_FAILED;
			if (wsResponse.getErrorCode() != null) {
			    switch (wsResponse.getErrorCode()) {
			    	case METADATA_TYPE_MISSING:
			    		error = Errors.CUSTOM_FIELD_TYPE_NOT_SET;
			    		break;
			    	case ATTRIBUTE_NAME_MISSING:
			    		error = Errors.CUSTOM_FIELD_NAME_NOT_SET;
			    		break;
			    	default:
			    		break;
			    }
			}
			ajaxResponse.addError(new ErrorToken(error));
			ajaxResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    }
	
		request.setAttribute("response", ajaxResponse);
		return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/metaDataTypeEdit", method = RequestMethod.POST)
    public String saveMetadataType(final HttpServletRequest request, 
    							   final HttpServletResponse response,
    							   @RequestBody MetadataType type) {
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
	    final Response wsResponse = metadataServiceClient.saveMetadataType(type);
	    if (ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
			ajaxResponse.setStatus(HttpServletResponse.SC_OK);
			ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.METADATA_TYPE_SAVED));
			ajaxResponse.setRedirectURL(String.format("metaDataTypeEdit.html?id=%s", (String) wsResponse.getResponseValue()));
	    } else {
			Errors error = Errors.SAVE_METADATA_TYPE_FAILED;
			if (wsResponse.getErrorCode() != null) {
			    switch (wsResponse.getErrorCode()) {
			    	case NO_NAME:
			    		error = Errors.METADATA_TYPE_NAME_REQUIRED;
			    		break;
			    	case DISPLAY_NAME_REQUIRED:
			    		error = Errors.DISPLAY_NAME_REQUIRED;
			    		break;
			    	default:
			    		break;
			    }
			}
			ajaxResponse.addError(new ErrorToken(error));
			ajaxResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    }
	
		request.setAttribute("response", ajaxResponse);
		return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/metadataTypeDelete", method = RequestMethod.POST)
    public String delete(final HttpServletRequest request, 
    					 final HttpServletResponse response,
    					 final @RequestParam(required = true, value = "id") String typeId) {
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();

	    final Response wsResponse = metadataServiceClient.deleteMetadataType(typeId);
	    if (ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
			ajaxResponse.setStatus(HttpServletResponse.SC_OK);
			ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.METADATA_TYPE_DELETED));
			ajaxResponse.addContextValues("metadataTypeId", (String) wsResponse.getResponseValue());
	    } else {
			Errors error = Errors.DELETE_METADATA_TYPE_FAILED;
			if (wsResponse.getErrorCode() != null) {
			    switch (wsResponse.getErrorCode()) {
			    	case METATYPE_LINKED_WITH_METAELEMENT:
			    		error = Errors.METADATA_TYPE_HAS_LINKS;
			    		break;
			    	default:
			    		break;
			    }
			}
			ajaxResponse.addError(new ErrorToken(error));
			ajaxResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    }
	
		request.setAttribute("response", ajaxResponse);
		return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/metadataDelete", method = RequestMethod.POST)
    public String deleteMetadataElement(final HttpServletRequest request, 
    									final HttpServletResponse response,
		    							final @RequestParam(required = true, value = "id") String id) {
        MetadataElement element = new MetadataElement();
        String mdTypeId = null;
        if (StringUtils.isNotBlank(id)) {
            element = getMetadataElement(id);
            if (element!= null) {
                mdTypeId = element.getMetadataTypeId();
            }
        }

		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
	
	    final Response wsResponse = metadataServiceClient.deleteMetadataElement(id);
	    if (ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
			ajaxResponse.setStatus(HttpServletResponse.SC_OK);
			ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.METADATA_ELEMENT_DELETED));
			ajaxResponse.addContextValues("metadataTypeId", (String) wsResponse.getResponseValue());
            ajaxResponse.addContextValues("mdTypeId", mdTypeId);
	    } else {
			Errors error = Errors.DELETE_METADATA_ELEMENT_FAILED;
			ajaxResponse.addError(new ErrorToken(error));
			ajaxResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    }
	
		request.setAttribute("response", ajaxResponse);
		return "common/basic.ajax.response";
    }
}
