package org.openiam.ui.webconsole.am.web.mvc;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openiam.am.srvc.dto.AuthLevel;
import org.openiam.am.srvc.dto.AuthLevelGrouping;
import org.openiam.am.srvc.dto.ContentProvider;
import org.openiam.am.srvc.searchbeans.ContentProviderSearchBean;
import org.openiam.am.srvc.ws.ContentProviderWebService;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.srvc.mngsys.dto.ManagedSysDto;
import org.openiam.idm.srvc.mngsys.service.ManagedSysDAO;
import org.openiam.idm.srvc.mngsys.ws.ManagedSystemWebService;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.model.BeanResponse;
import org.openiam.ui.web.mvc.AbstractController;
import org.openiam.ui.webconsole.am.web.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class ContentProviderController extends AbstractController {
    private static Logger log = Logger.getLogger(AuthenticationProviderController.class);

    @Value("${org.openiam.ui.content.provider.root.name}")
    private String rootMenuName;

    @Value("${org.openiam.ui.content.provider.edit.name}")
    private String editMenuName;


    @RequestMapping("/contentProviders")
    public String contentProviders(final HttpServletRequest request) {
        setMenuTree(request, rootMenuName);
        return "content/search";
    }
    
    @RequestMapping(value="/createDefaultURIPatterns", method=RequestMethod.POST)
    public @ResponseBody BasicAjaxResponse createDefaultURIPatterns(final HttpServletRequest request,
    																final HttpServletResponse response,
    																final @RequestParam(required=true, value="id") String contentProviderId) {
    	final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
    	Errors error = null;
    	try {
    		final Response wsResponse = contentProviderServiceClient.createDefaultURIPatterns(contentProviderId);
    		if(wsResponse.isFailure()) {
    			error = Errors.COULD_NOT_SAVE_CONTENT_PROVIDER;
    		}
    	} catch(Throwable e) {
    		error = Errors.COULD_NOT_SAVE_CONTENT_PROVIDER;
    		log.error("Unknown Error", e);
    	} finally {
    		if(error != null) {
    			ajaxResponse.addError(new ErrorToken(error));
    			ajaxResponse.setStatus(500);
    		} else {
    			ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.CONTECNT_PROVIDER_SAVED));
    			ajaxResponse.setStatus(200);
    		}
    		ajaxResponse.process(localeResolver, messageSource, request);
    	}
    	return ajaxResponse;
    }
    
    @RequestMapping("/searchContentProviders")
    public @ResponseBody BeanResponse searchContentProviders(@RequestParam(value="name", required=false) String name,
                                          	   				final @RequestParam(value="from", required=true) int from,
                                          	   				final @RequestParam(value="size", required=true) int size) {
    	 if (StringUtils.isNotBlank(name)) {
             if (name.charAt(0) != '*') {
                 name = "*" + name;
             }

             if (name.charAt(name.length() - 1) != '*') {
                 name = name + "*";
             }
         }
    	
    	final ContentProviderSearchBean searchBean = new ContentProviderSearchBean();
        searchBean.setDeepCopy(false);
        searchBean.setProviderName(StringUtils.trimToNull(name));
        final List<ContentProvider> resultList = contentProviderServiceClient.findBeans(searchBean, from, size);
        final int count = contentProviderServiceClient.getNumOfContentProviders(searchBean);
        return new BeanResponse(mapper.mapToList(resultList, ContentProviderBean.class), count);
    }

    @RequestMapping(value="/newContentProvider", method= RequestMethod.GET)
    public String newAuthProvider(final HttpServletRequest request) throws Exception {
        populateContentProviderPage(request, new ContentProvider());
        //return "content/edit";
        return "content/contentprovider";
    }

    @RequestMapping(value="/editContentProvider", method=RequestMethod.GET)
    public String editAuthProvider(final HttpServletRequest request,
                                   final HttpServletResponse response,
                                   final @RequestParam(value="providerId", required=true) String providerId) throws Exception {
        final ContentProvider provider = contentProviderServiceClient.getContentProvider(providerId);
        if(provider == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, String.format("Content provider with ID: '%' - not found", providerId));
            return null;
        }
        populateContentProviderPage(request, provider);
        return "content/contentprovider";
    }

    @RequestMapping(value="/editContentProvider", method=RequestMethod.POST)
    public String editAuthProviderPOST(final HttpServletRequest request, @RequestBody ContentProvider provider) {
        ErrorToken errorToken = null;
        SuccessToken successToken = null;
        
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        //TODO: validate
        Response response = null;
        try {
            if(errorToken == null) {
                response = contentProviderServiceClient.saveContentProvider(provider);

                if(response.getStatus() == ResponseStatus.SUCCESS) {
                    successToken = new SuccessToken(SuccessMessage.CONTECNT_PROVIDER_SAVED);
                } else {
                    Errors error = Errors.COULD_NOT_SAVE_CONTENT_PROVIDER;
                    if(response.getErrorCode() != null) {
                        switch(response.getErrorCode()) {
                            case CONTENT_PROVIDER_NAME_NOT_SET:
                                error = Errors.CONTENT_PROVIDER_NAME_NOT_SET;
                                break;
                            case CONTENT_PROVIDER_AUTH_LEVEL_NOT_SET:
                                error = Errors.CONTENT_PROVIDER_AUTH_LEVEL_NOT_SET;
                                break;
                            case CONTENT_PROVIDER_DOMAIN_PATERN_NOT_SET:
                                error = Errors.CONTENT_PROVIDER_DOMAIN_PATERN_NOT_SET;
                                break;
                            case CONTENT_PROVIDER_WITH_NAME_EXISTS:
                                error = Errors.CONTENT_PROVIDER_WITH_NAME_EXISTS;
                                break;
                            case CONTENT_PROVIDER_DOMAIN_PATTERN_EXISTS:
                                error = Errors.CONTENT_PROVIDER_DOMAIN_PATTERN_EXISTS;
                                break;
                            case MANAGED_SYSTEM_NOT_SET:
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
        				final String contentProviderId = ((String)response.getResponseValue());
        				ajaxResponse.setRedirectURL(String.format("editContentProvider.html?providerId=%s", contentProviderId));
        			}
        		}
        		ajaxResponse.setSuccessToken(successToken);
        		ajaxResponse.setStatus(200);
        	}
            request.setAttribute("response", ajaxResponse);
        }
        return "common/basic.ajax.response";
    }

    @RequestMapping(value="/deleteContentProvider", method=RequestMethod.POST)
    public String deleteAuthProvider(final HttpServletRequest request, @RequestParam(value="id", required=true) String id) {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        final Response wsResponse = contentProviderServiceClient.deleteContentProvider(id);
        if(ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
            ajaxResponse.setStatus(200);
            ajaxResponse.setRedirectURL("contentProviders.html");
            ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.CONTENT_PROVIDER_DELETED));
        } else {
            Errors error = Errors.CONTENT_PROVIDER_DELETE_ERROR;
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

    private void populateContentProviderPage(final HttpServletRequest request, final ContentProvider provider) throws Exception {
    	if(provider != null && StringUtils.isBlank(provider.getId())) {
    		provider.setIsPublic(true);
    	}
    	
    	if(StringUtils.isBlank(provider.getManagedSysId())) {
    		provider.setManagedSysId(defaultManagedSysId);
    	}
        request.setAttribute("provider", provider);
        request.setAttribute("providerAsJSON", jacksonMapper.writeValueAsString(provider));
        
        request.setAttribute("managedSystems", getManagedSystemsAsKeyNameBeans());

        if(provider.getId() == null || provider.getId().trim().isEmpty()) {
            setMenuTree(request, rootMenuName);
        } else {
            setMenuTree(request, editMenuName);
        }
        
        request.setAttribute("uiThemes", getUIThemesAsKeyNameBeans());
    }
}
