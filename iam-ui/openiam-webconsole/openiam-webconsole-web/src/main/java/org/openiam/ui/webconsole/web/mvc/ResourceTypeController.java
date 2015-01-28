package org.openiam.ui.webconsole.web.mvc;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openiam.base.ws.Response;
import org.openiam.idm.searchbeans.ResourceSearchBean;
import org.openiam.idm.searchbeans.ResourceTypeSearchBean;
import org.openiam.idm.srvc.res.dto.ResourceType;
import org.openiam.ui.rest.api.model.ResourceTypeBean;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.mvc.AbstractController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class ResourceTypeController extends AbstractController {

    @Value("${org.openiam.ui.landingpage.resource.type.root.name}")
    private String rootTypeMenu;

    @RequestMapping("/resourceTypes")
    public String getResourceTypesPage(final HttpServletRequest request) throws Exception {
        setMenuTree(request, this.rootTypeMenu);
        return "resources/type/search";
    }

    @RequestMapping(value = "/editResourceType", method = RequestMethod.GET)
    public String editResourceType(final HttpServletRequest request, final HttpServletResponse response,
            final @RequestParam(required = false, value = "id") String resourceTypeId) throws Exception {
        org.openiam.idm.srvc.res.dto.ResourceType resourceType = new org.openiam.idm.srvc.res.dto.ResourceType();
        ResourceTypeBean result = new ResourceTypeBean();
        if (StringUtils.isEmpty(resourceTypeId)) {
            setMenuTree(request, this.rootTypeMenu);
        } else {
            final ResourceTypeSearchBean searchBean = new ResourceTypeSearchBean();
            searchBean.setKey(resourceTypeId);
            searchBean.setDeepCopy(true);
            List<org.openiam.idm.srvc.res.dto.ResourceType> resultList = resourceDataService.findResourceTypes(searchBean, 0, 1, getCurrentLanguage());
            if (CollectionUtils.isEmpty(resultList)) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND,
                        String.format("Resource Type with ID: '%s' not found", resourceTypeId));
                return null;
            }

            resourceType = resultList.get(0);
            result.setDescription(resourceType.getDescription());
            result.setId(resourceType.getId());
            result.setProcessName(resourceType.getProcessName());
            result.setProvisionResource(resourceType.getProvisionResource());
            result.setSearchable(resourceType.isSearchable());
            result.setSupportsHierarchy(resourceType.isSupportsHierarchy());
            result.setImageType(resourceType.getImageType());
            result.setUrl(resourceType.getUrl());
            result.setDisplayNameMap(resourceType.getDisplayNameMap());
            resourceType.setImageType(resourceType.getImageType());
            setMenuTree(request, this.rootTypeMenu);
        }
        request.setAttribute("resourceTypeAsJSON", jacksonMapper.writeValueAsString(resourceType));
        request.setAttribute("resourceType", result);
        request.setAttribute("isNew", StringUtils.isBlank(resourceTypeId));
        return "resources/editResourceType";
    }

    @RequestMapping(value = "/uploadIcon", method = RequestMethod.POST)
    public String upload(MultipartHttpServletRequest request, HttpServletResponse response) throws IOException {
        BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        // 1. get the files from the request object
        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf = request.getFile(itr.next());
        if (mpf.getSize() > 24576) {
            ajaxResponse.setErrorList(Arrays.asList(new ErrorToken(Errors.FILE_TOO_BIG, 24)));
        } else {
            String resourceTypeId = request.getParameter("resourceTypeId");
            ResourceTypeSearchBean sb = new ResourceTypeSearchBean();
            sb.setKey(resourceTypeId);
            List<ResourceType> list = resourceDataService.findResourceTypes(sb, 0, 1, getCurrentLanguage());
            if (CollectionUtils.isEmpty(list)) {
                ajaxResponse.setErrorList(Arrays.asList(new ErrorToken(Errors.RESOURCE_TYPE_IS_NOT_EXIST)));
            } else {
                ResourceType rt = list.get(0);
                if (mpf == null || mpf.isEmpty()) {
                    log.info("file is empty");
                    ajaxResponse.setErrorList(Arrays.asList(new ErrorToken(Errors.NOTHING_TO_UPLOAD)));
                } else {
                    try {
                        String[] fileNameParts = StringUtils.split(mpf.getOriginalFilename(), '.');
                        if (fileNameParts != null && fileNameParts.length > 0) {
                            String extension = fileNameParts[fileNameParts.length - 1];
                            if ("png".equals(extension) || "jpg".equals(extension) || "bmp".equals(extension)
                                    || "gif".equals(extension)) {
                                rt.setUrl(Base64.encodeBase64String(mpf.getBytes()));
                                rt.setImageType(fileNameParts[fileNameParts.length - 1]);
                                resourceDataService.saveResourceType(rt, this.getRequesterId(request));
                                ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.RESOURCE_TYPE_SAVED));
                                ajaxResponse.setStatus(200);
                                ajaxResponse.setRedirectURL(String
                                        .format("editResourceType.html?id=%s", resourceTypeId));
                                log.info(mpf.getOriginalFilename() + " uploaded!");
                            } else {
                                ajaxResponse
                                        .setErrorList(Arrays.asList(new ErrorToken(Errors.EXTENSION_NOT_SUPPORTED)));
                            }
                        }
                    } catch (Exception e) {
                        log.error(mpf.getOriginalFilename() + "not uploaded!");
                        ajaxResponse.setErrorList(Arrays.asList(new ErrorToken(Errors.INTERNAL_ERROR)));
                    }
                }
            }
        }
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/editResourceType", method = RequestMethod.POST)
    public String saveResourceType(final HttpServletRequest request, final HttpServletResponse response,
            final @RequestBody ResourceTypeBean bean) throws Exception {
        BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        if (StringUtils.isEmpty(bean.getDescription())) {
            ajaxResponse.setErrorList(Arrays.asList(new ErrorToken(Errors.DESCRIPTION_IS_EMPTY)));
        } else {
            final ResourceType resourceType = new ResourceType();
            resourceType.setDescription(bean.getDescription());
            resourceType.setId(bean.getId());
            resourceType.setProcessName(bean.getProcessName());
            resourceType.setProvisionResource(bean.getProvisionResource());
            resourceType.setSearchable(bean.getSearchable());
            resourceType.setSupportsHierarchy(bean.getSupportsHierarchy());
            resourceType.setUrl(bean.getUrl());
            resourceType.setImageType(bean.getImageType());
            resourceType.setDisplayNameMap(bean.getDisplayNameMap());
            Response resp = resourceDataService.saveResourceType(resourceType, this.getRequesterId(request));
            if (resp.isSuccess()) {
                String resourceTypeId = (String) resp.getResponseValue();
                ajaxResponse.setStatus(200);
                ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.RESOURCE_TYPE_SAVED));
                ajaxResponse.setRedirectURL(String.format("editResourceType.html?id=%s", resourceTypeId));
            } else {
                ajaxResponse.setErrorList(Arrays.asList(new ErrorToken(Errors.INTERNAL_ERROR)));
            }
        }
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/deleteResourceType", method = RequestMethod.POST)
    public String deleteResourceType(final @RequestParam(value = "id", required = true) String resourceTypeId,
            final HttpServletResponse response, final HttpServletRequest request) throws Exception {
        BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();

        if (StringUtils.isNotEmpty(resourceTypeId)) {
            ResourceSearchBean searchBean = new ResourceSearchBean();
            searchBean.setResourceTypeId(resourceTypeId);
            List res = resourceDataService.findBeans(searchBean, 0, 1, getCurrentLanguage());
            if (CollectionUtils.isNotEmpty(res)) {
                ajaxResponse.setErrorList(Arrays.asList(new ErrorToken(Errors.RES_TYPE_HAS_DEPENDENCIES)));
            } else {
                final String callerId = getRequesterId(request);
                Response resp = resourceDataService.deleteResourceType(resourceTypeId, callerId);
                if (resp.isSuccess()) {
                    ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.RESOURCE_TYPE_DELETED));
                    ajaxResponse.setStatus(200);
                } else {
                    ajaxResponse.setErrorList(Arrays.asList(new ErrorToken(Errors.INTERNAL_ERROR)));
                }
            }
        }
        ajaxResponse.setRedirectURL("resourceTypes.html");
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }
}
