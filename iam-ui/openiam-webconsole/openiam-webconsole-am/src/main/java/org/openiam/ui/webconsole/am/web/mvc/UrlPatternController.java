package org.openiam.ui.webconsole.am.web.mvc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openiam.am.srvc.dto.ContentProvider;
import org.openiam.am.srvc.dto.URIPattern;
import org.openiam.am.srvc.searchbeans.ContentProviderSearchBean;
import org.openiam.am.srvc.searchbeans.URIPatternSearchBean;
import org.openiam.am.srvc.ws.ContentProviderWebService;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.model.BeanResponse;
import org.openiam.ui.web.model.URIPatternBean;
import org.openiam.ui.web.mvc.AbstractController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;

@Controller
public class UrlPatternController extends AbstractController {
    private static Logger log = Logger.getLogger(UrlPatternController.class);
    @Resource(name="contentProviderServiceClient")
    private ContentProviderWebService contentProviderServiceClient;

    @Value("${org.openiam.ui.content.provider.pattern.name}")
    private String patternMenuName;

    @Value("${org.openiam.ui.content.provider.edit.name}")
    private String editMenuName;

    @RequestMapping(value="/getPatternsForProvider", method = RequestMethod.GET)
    public @ResponseBody
    BeanResponse getPatternsForProvider(final @RequestParam(required=true, value="providerId") String providerId,
                                       final @RequestParam(required=true, value="size") Integer size,
                                       final @RequestParam(required=true, value="from") Integer from) {
        URIPatternSearchBean searchBean = new URIPatternSearchBean();
        searchBean.setContentProviderId(providerId);
        searchBean.setDeepCopy(true);

        final List<URIPattern> patternList = contentProviderServiceClient.findUriPatterns(searchBean, from,
                                                                                                    size);
        final Integer count = contentProviderServiceClient.getNumOfUriPatterns(searchBean);
        final List<URIPatternBean> beanList = new LinkedList<URIPatternBean>();
        if(CollectionUtils.isNotEmpty(patternList)) {
            for(final URIPattern p : patternList) {
                beanList.add(new URIPatternBean(p.getId(), p.getContentProviderId(), p.getContentProviderName(), p.getPattern(), p.getIsPublic(), p.getResourceName()));
            }
        }
        return new BeanResponse(beanList, count);
    }

    @RequestMapping(value="/newProviderPattern", method= RequestMethod.GET)
    public String newProviderPattern(final HttpServletRequest request, final HttpServletResponse response,
                                     final @RequestParam(value="providerId", required=true) String providerId) throws Exception{

        final ContentProviderSearchBean searchBean = new ContentProviderSearchBean();
        searchBean.setKey(providerId);
        searchBean.setDeepCopy(false);
        final List<ContentProvider> list = contentProviderServiceClient.findBeans(searchBean, 0, 1);
        if(CollectionUtils.isEmpty(list)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, String.format("Content Provider with  id '%s' does not exist", providerId));
            return null;
        }

        URIPattern pattern = new URIPattern();
        pattern.setContentProviderId(providerId);
        populateContentProviderPatternPage(request, pattern, list.get(0));
        return "content/patternEdit";
    }

    @RequestMapping(value="/editProviderPattern", method=RequestMethod.GET)
    public String editProviderPattern(final HttpServletRequest request, final HttpServletResponse response,
                                      final @RequestParam(value="id", required=true) String patternId,
                                      final @RequestParam(value="providerId", required=true) String providerId) throws Exception {
        final ContentProviderSearchBean searchBean = new ContentProviderSearchBean();
        searchBean.setKey(providerId);
        searchBean.setDeepCopy(false);
        final List<ContentProvider> list = contentProviderServiceClient.findBeans(searchBean, 0, 1);
        if(CollectionUtils.isEmpty(list)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, String.format("Content Provider with  id '%s' does not exist", providerId));
            return null;
        }

        final URIPattern pattern = contentProviderServiceClient.getURIPattern(patternId);
        if(pattern == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, String.format("URI Pattern with ID: '%' - not found", patternId));
            return null;
        }
        populateContentProviderPatternPage(request, pattern, list.get(0));
        return "content/patternEdit";
    }


    @RequestMapping(value="/editProviderPattern", method=RequestMethod.POST)
    public String editProviderPatternPOST(final HttpServletRequest request, @RequestBody URIPattern pattern) {
        ErrorToken errorToken = null;
        SuccessToken successToken = null;

        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        Response response = null;
        //TODO: validate
        try {
            if(errorToken == null) {
                response = contentProviderServiceClient.saveURIPattern(pattern);

                if(response.getStatus() == ResponseStatus.SUCCESS) {
                    successToken = new SuccessToken(SuccessMessage.CONTENT_PROVIDER_URI_PATTERN_SAVED);
                } else {
                    Errors error = Errors.CANNOT_SAVE_CONTENT_PROVIDER_URI_PATTERN_PROIVDER;
                    if(response.getErrorCode() != null) {
                        switch(response.getErrorCode()) {
                            case CONTENT_PROVIDER_URI_PATTERN_NOT_SET:
                                error = Errors.URI_PATERN_NOT_SET;
                                break;
                            /*
                            case URI_PATTERN_AUTH_LEVEL_NOT_SET:
                                error = Errors.URI_PATERN_AUTH_LEVEL_NOT_SET;
                                break;
                            */
                            case CONTENT_PROVIDER_NOT_SET:
                                error = Errors.CONTENT_PROVIDER_NOT_SET;
                                break;
                            case URI_PATTERN_EXISTS:
                                error = Errors.URI_PATTERN_EXISTS;
                                break;
                            case URI_PATTERN_INVALID:
                                error = Errors.URI_PATTERN_INVALID;
                                break;
                            default:
                                error = Errors.INTERNAL_ERROR;
                                break;
                        }
                    }
                    errorToken = new ErrorToken(error);
                }
            }
        } catch(Throwable e) {
            errorToken = new ErrorToken(Errors.INTERNAL_ERROR);
            log.error("Exception while saving auth provider", e);
        } finally {
        	if(errorToken != null) {
        		ajaxResponse.setStatus(500);
        		ajaxResponse.addError(errorToken);
        	} else {
        		if(response != null && response.getResponseValue() != null) {
        			if(response.getResponseValue() instanceof String) {
        				final String uriPatternId = ((String)response.getResponseValue());
        				ajaxResponse.setRedirectURL(String.format("editProviderPattern.html?id=%s&providerId=%s", uriPatternId, pattern.getContentProviderId()));
        			}
        		}
        		ajaxResponse.setSuccessToken(successToken);
        		ajaxResponse.setStatus(200);
        	}
            request.setAttribute("response", ajaxResponse);
        }
        return "common/basic.ajax.response";
    }

    @RequestMapping(value="/deleteProviderPattern", method=RequestMethod.POST)
    public  @ResponseBody BasicAjaxResponse deleteProviderPattern(final HttpServletRequest request, @RequestBody  URIPatternBean pattern) {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        try{

            Response wsResponse = contentProviderServiceClient.deleteProviderPattern(pattern.getId());

            if(wsResponse.getStatus() == ResponseStatus.SUCCESS) {
                SuccessToken successToken = new SuccessToken(SuccessMessage.CONTENT_PROVIDER_URI_PATTERN_DELETED);
                ajaxResponse.setStatus(200);
                ajaxResponse.setSuccessToken(successToken);
            } else {
                Errors error = Errors.CANNOT_DELETE_CONTENT_PROVIDER_URI_PATTERN;
                if(wsResponse.getErrorCode() != null) {
                    switch(wsResponse.getErrorCode()) {
                        case INVALID_ARGUMENTS:
                            error = Errors.URI_PATERN_NOT_SELECTED;
                            break;
                        default:
                            error = Errors.INTERNAL_ERROR;
                            break;
                    }
                }
                ErrorToken errorToken = new ErrorToken(error);
                ajaxResponse.addError(errorToken);
            }
        }  catch (Throwable e){
            log.error(e.getMessage(), e);
            ajaxResponse.addError(new ErrorToken(Errors.INTERNAL_ERROR));
        } finally {
        	ajaxResponse.process(localeResolver, messageSource, request);
        }
        return ajaxResponse;
    }

    private void populateContentProviderPatternPage(final HttpServletRequest request, final URIPattern pattern, final ContentProvider provider) throws Exception {
    	if(pattern != null && StringUtils.isBlank(pattern.getId())) {
    		pattern.setIsPublic(true);
    	}
    	
        request.setAttribute("uriPattern", pattern);
        request.setAttribute("provider", provider);
        request.setAttribute("uriPatternAsJSON", jacksonMapper.writeValueAsString(pattern));
       
        if(pattern.getId() == null) {
            setMenuTree(request, editMenuName);
        } else {
            setMenuTree(request, patternMenuName);
        }
        request.setAttribute("uiThemes", getUIThemesAsKeyNameBeans());
    }


}
