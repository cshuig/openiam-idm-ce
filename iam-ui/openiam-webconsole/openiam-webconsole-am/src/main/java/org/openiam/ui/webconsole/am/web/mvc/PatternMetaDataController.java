package org.openiam.ui.webconsole.am.web.mvc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openiam.am.srvc.dto.*;
import org.openiam.am.srvc.ws.AuthResourceAttributeWebService;
import org.openiam.am.srvc.ws.ContentProviderWebService;
import org.openiam.base.ws.*;
import org.openiam.ui.rest.api.model.KeyNameBean;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.model.BeanResponse;
import org.openiam.ui.web.mvc.AbstractController;
import org.openiam.ui.webconsole.am.web.model.MetaDataFormRequest;
import org.openiam.ui.webconsole.am.web.model.MetaValueBean;
import org.openiam.ui.webconsole.am.web.model.URIPatternMetaBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Controller
public class PatternMetaDataController extends AbstractController {
    private static Logger log = Logger.getLogger(PatternMetaDataController.class);
    @Value("${org.openiam.ui.content.provider.meta.name}")
    private String metaMenuName;

    @Resource(name="contentProviderServiceClient")
    private ContentProviderWebService contentProviderServiceClient;

    @Resource(name="authAttributeServiceClient")
    private AuthResourceAttributeWebService authAttributeServiceClient;

    @RequestMapping(value="/getMetaDataForPattern", method = RequestMethod.GET)
    public @ResponseBody
    BeanResponse getMetaDataForPattern(final @RequestParam(required=true, value="patternId") String patternId,
                                        final @RequestParam(required=true, value="size") Integer size,
                                        final @RequestParam(required=true, value="from") Integer from) {
        final List<URIPatternMeta> metaList = contentProviderServiceClient.getMetaDataForPattern(patternId, from,
                                                                                                    size);
        final Integer count = contentProviderServiceClient.getNumOfMetaDataForPattern(patternId);
        final List<URIPatternMetaBean> beanList = new LinkedList<URIPatternMetaBean>();
        if(CollectionUtils.isNotEmpty(metaList)) {
            for(final URIPatternMeta m : metaList) {
                beanList.add(new URIPatternMetaBean(m.getId(), m.getMetaType().getName()));
            }
        }
        return new BeanResponse(beanList, count);
    }

    @RequestMapping(value="/newPatternMetaData", method=RequestMethod.GET)
    public String newPatternMetaData(final HttpServletRequest request,
                                      final HttpServletResponse response,
                                      final @RequestParam(required=true, value="patternId") String patternId) throws IOException {
        return pupulateForm(request, response, patternId, null);
    }

    @RequestMapping(value="/editPatternMetaData", method=RequestMethod.GET)
    public String editPatternMetaData(final HttpServletRequest request,
                                      final HttpServletResponse response,
                                      final @RequestParam(required=true, value="id") String metaId,
                                      final @RequestParam(required=true, value="patternId") String patternId) throws IOException {
        return pupulateForm(request, response, patternId, metaId);
    }

    @RequestMapping(value="/saveMetaData", method=RequestMethod.POST)
    public @ResponseBody BasicAjaxResponse saveMetaData(final HttpServletRequest request, @RequestBody URIPatternMeta uriPatternMeta) {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        try{
            SuccessMessage succsessMessage = SuccessMessage.URI_PATTERN_META_SAVED;

            final Response wsResponse = contentProviderServiceClient.saveMetaDataForPattern(uriPatternMeta);

            if(wsResponse.getStatus() == org.openiam.base.ws.ResponseStatus.SUCCESS) {
                final String metaId = (String)wsResponse.getResponseValue();
                SuccessToken successToken = new SuccessToken(succsessMessage);
                ajaxResponse.setStatus(200);
                ajaxResponse.setSuccessToken(successToken);
                ajaxResponse.setRedirectURL(String.format("editPatternMetaData.html?id=%s&patternId=%s", metaId, uriPatternMeta.getUriPatternId()));
            } else {
                Errors error = Errors.CANNOT_SAVE_URI_PATTERN_META;
                if(wsResponse.getErrorCode() != null) {
                    switch(wsResponse.getErrorCode()) {
                        case URI_PATTERN_NOT_SET:
                            error = Errors.URI_PATTERN_NOT_SET;
                            break;
                        case URI_PATTERN_META_TYPE_NOT_SET:
                            error = Errors.URI_PATTERN_META_TYPE_NOT_SET;
                            break;
//                        case URI_PATTERN_META_EXISTS:
//                            error = Errors.URI_PATTERN_META_EXISTS;
//                            break;
                        case URI_PATTERN_META_NAME_NOT_SET:
                            error = Errors.URI_PATTERN_META_NAME_NOT_SET;
                            break;
                        case URL_PATTERN_META_VALUE_NAME_NOT_SET:
                            error = Errors.URL_PATTERN_META_VALUE_NAME_NOT_SET;
                            break;
                        case URL_PATTERN_META_VALUE_MAP_NOT_SET:
                            error = Errors.URL_PATTERN_META_VALUE_MAP_NOT_SET;
                            break;
                        case META_NAME_MISSING:
                        	error = Errors.PATTERN_META_NAME_MISSING;
                        	break;
                        case META_VALUE_MISSING:
                        	error = Errors.PATTERN_META_VALUE_MISSING;
                        	break;
                        default:
                            error = Errors.INTERNAL_ERROR;
                            break;
                    }
                }
                ErrorToken errorToken = new ErrorToken(error);
                ajaxResponse.addError(errorToken);
            }
        }  catch (Exception e){
            log.error(e.getMessage(), e);
            ajaxResponse.addError(new ErrorToken(Errors.INTERNAL_ERROR));
        } finally {
        	ajaxResponse.process(localeResolver, messageSource, request);
        }
        return ajaxResponse;
    }
    
    @RequestMapping(value="/deletePatternMetaData", method=RequestMethod.POST)
    public String deletePatternMetaData(final HttpServletRequest request, 
    									final @RequestParam(value="id", required=true) String id) {
    	 return deleteMetaData(request, id, null);
    }

    @RequestMapping(value="/deleteMetaData", method=RequestMethod.POST)
    public String deleteMetaData(final HttpServletRequest request, @RequestParam(value="id", required=true) String id, @RequestParam(value="patternId", required=true) String patternId) {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        final Response wsResponse = contentProviderServiceClient.deleteMetaDataForPattern(id);
        if(org.openiam.base.ws.ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
            ajaxResponse.setStatus(200);
            if(patternId != null) {
            	ajaxResponse.setRedirectURL("patternMetadata.html?id="+patternId);
            }
            ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.URI_PATTERN_META_DELETED));
        } else {
            Errors error = Errors.URI_PATTERN_META_DELETE_ERROR;
            if(wsResponse.getErrorCode() != null) {
                //TODO: error handler
                switch(wsResponse.getErrorCode()) {
                    default:
                        break;
                }
            }
            ajaxResponse.addError(new ErrorToken(error));
            ajaxResponse.setStatus(500);
        }
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value="/getPatternMeta", method=RequestMethod.GET)
    public @ResponseBody URIPatternMeta getPatternMeta(final HttpServletRequest request, final @RequestParam(required=false, value="id") String id) {
    	return (StringUtils.isNotBlank(id)) ? contentProviderServiceClient.getURIPatternMeta(id) : new URIPatternMeta();
    }
    
    @RequestMapping(value="/getAMAttributes", method=RequestMethod.GET)
    public  @ResponseBody BeanResponse getAMAttributes(final HttpServletRequest request) {
    	final List<AuthResourceAMAttribute> attributeList = authAttributeServiceClient.getAmAttributeList();
    	final List<KeyNameBean> keyNameBeanList = mapper.mapToList(attributeList, KeyNameBean.class);
    	return new BeanResponse(keyNameBeanList, keyNameBeanList.size());
    }
    

    private String pupulateForm(HttpServletRequest request, HttpServletResponse response, String uriPatternId, String uriPatternMetaId) throws IOException{
        final List<URIPatternMetaType> metaTypeList = contentProviderServiceClient.getAllMetaType();


        final URIPattern uriPattern = contentProviderServiceClient.getURIPattern(uriPatternId);
        if(uriPattern == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        setMenuTree(request, metaMenuName);
        request.setAttribute("uriPattern", uriPattern);
        request.setAttribute("uriPatternMetaId", uriPatternMetaId);
        request.setAttribute("metaTypeList", metaTypeList);
        request.setAttribute("amAttributes", authAttributeServiceClient.getAmAttributeList());
        return "content/metaEdit";
    }
}
