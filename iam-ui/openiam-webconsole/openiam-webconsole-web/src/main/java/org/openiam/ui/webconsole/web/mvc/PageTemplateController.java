package org.openiam.ui.webconsole.web.mvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openiam.am.srvc.dto.URIPattern;
import org.openiam.am.srvc.searchbeans.URIPatternSearchBean;
import org.openiam.am.srvc.ws.ContentProviderWebService;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.searchbeans.MetadataElementPageTemplateSearchBean;
import org.openiam.idm.searchbeans.MetadataElementSearchBean;
import org.openiam.idm.searchbeans.MetadataTemplateTypeFieldSearchBean;
import org.openiam.idm.srvc.lang.dto.Language;
import org.openiam.idm.srvc.meta.dto.MetadataElement;
import org.openiam.idm.srvc.meta.dto.MetadataElementPageTemplate;
import org.openiam.idm.srvc.meta.dto.MetadataElementPageTemplateXref;
import org.openiam.idm.srvc.meta.dto.MetadataFieldTemplateXref;
import org.openiam.idm.srvc.meta.dto.MetadataTemplateTypeField;
import org.openiam.idm.srvc.meta.dto.MetadataType;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.model.BeanResponse;
import org.openiam.ui.webconsole.validator.PageTemplateValidator;
import org.openiam.ui.webconsole.web.model.CustomFieldBean;
import org.openiam.ui.webconsole.web.model.CustomFieldSortableBean;
import org.openiam.ui.webconsole.web.model.PageTemplateBean;
import org.openiam.ui.webconsole.web.model.PageTemplatePatternBean;
import org.openiam.ui.webconsole.web.model.TemplateFieldBean;
import org.openiam.ui.webconsole.web.model.URIPatternSearchableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PageTemplateController extends BaseTemplateController {

    @Value("${org.openiam.ui.page.template.root.menu.id}")
    private String templateRootMenuName;
    @Value("${org.openiam.ui.page.template.edit.menu.id}")
    private String templateEditMenuName;
    @Resource(name = "contentProviderServiceClient")
    private ContentProviderWebService contentProviderServiceClient;

    @Autowired
    private PageTemplateValidator pageTemplateValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(pageTemplateValidator);
    }

    @RequestMapping(value = "/pageTemplates", method = RequestMethod.GET)
    public String getPageTemplateList(final HttpServletRequest request, final HttpServletResponse response,
                                      @RequestParam(required = false, value = "templateName") String templateName,
                                      @RequestParam(value = "page", required = false) Integer page,
                                      @RequestParam(value = "size", required = false) Integer size) {

        page = (page != null) ? page : 0;
        page = (page.intValue() > 0) ? page : 0;
        size = (size != null) ? size : 10;
        size = (size.intValue() > 0) ? size : 10;

        final String originalName = templateName;
        if (StringUtils.isNotBlank(templateName)) {
            if (templateName.charAt(0) != '*') {
                templateName = "*" + templateName;
            }
            if (templateName.charAt(templateName.length() - 1) != '*') {
                templateName = templateName + "*";
            }
        }

        final MetadataElementPageTemplateSearchBean searchBean = new MetadataElementPageTemplateSearchBean();
        searchBean.setName(templateName);
        searchBean.setDeepCopy(false);
        final List<MetadataElementPageTemplate> resultList = templateService
                        .findBeans(searchBean, page.intValue() * size.intValue(), size.intValue());
        final int count = templateService.count(searchBean);

        int totalPages = 0;
        if (count <= size.intValue()) {
            totalPages = 0;
        } else if ((count % size.intValue()) == 0) {
            totalPages = count / size.intValue();
        } else {
            totalPages = (count / size.intValue()) + 1;
        }

        request.setAttribute("templateName", originalName);
        request.setAttribute("resultList", resultList);
        request.setAttribute("size", size);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("page", page);
        request.setAttribute("count", count);

        setMenuTree(request, templateRootMenuName);
        return "pageTemplate/search";
    }

    @RequestMapping(value = "/newPageTemplate", method = RequestMethod.GET)
    public String newPageTemplate(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        populatePage(request, new MetadataElementPageTemplate());
        return "pageTemplate/edit";
    }

    @RequestMapping(value = "/editPageTemplate", method = RequestMethod.GET)
    public String editPageTemplate(final HttpServletRequest request, final HttpServletResponse response,
                                   @RequestParam(value = "id", required = true) String templateId) throws Exception {
        final MetadataElementPageTemplateSearchBean searchBean = new MetadataElementPageTemplateSearchBean();
        searchBean.setKey(templateId);
        searchBean.setDeepCopy(true);
        final List<MetadataElementPageTemplate> templateList = templateService.findBeans(searchBean, 0, 1);
        if (templateList == null || templateList.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, String.format("Page Template with ID: '%' - not found", templateId));
            return null;
        }
        populatePage(request, templateList.get(0));
        return "pageTemplate/edit";
    }

    @RequestMapping(value = "/savePageTemplate", method = RequestMethod.POST)
    public String savePageTemplate(final HttpServletRequest request, final HttpServletResponse response,
                                   @RequestBody @Valid PageTemplateBean templateBean) {
        MetadataElementPageTemplate template = convertFromModel(templateBean);
        ErrorToken errorToken = null;
        SuccessToken successToken = null;
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        Response wsResponse = null;
        try {
            if (errorToken == null) {
                wsResponse = templateService.save(template);

                if (wsResponse.getStatus() == ResponseStatus.SUCCESS) {
                    successToken = new SuccessToken(SuccessMessage.PAGE_TEMPLATE_SAVED);
                } else {
                    errorToken = new ErrorToken(Errors.CANNOT_SAVE_PAGE_TEMPLATE);
                    if (wsResponse.getErrorCode() != null) {
                        switch (wsResponse.getErrorCode()) {
                            case TEMPLATE_TYPE_REQUIRED:
                                errorToken = new ErrorToken(Errors.TEMPLATE_TYPE_REQUIRED);
                                break;
                            default:
                                errorToken = new ErrorToken(Errors.INTERNAL_ERROR);
                                break;
                        }
                    }
                }
            }
        } catch (Throwable e) {
            errorToken = new ErrorToken(Errors.INTERNAL_ERROR);
            log.error("Exception while saving custom field", e);
        } finally {
            if (errorToken != null) {
                ajaxResponse.setStatus(500);
                ajaxResponse.addError(errorToken);
            } else {
                if (wsResponse != null && wsResponse.getResponseValue() != null) {
                    if (wsResponse.getResponseValue() instanceof String) {
                        final String templateId = (String) wsResponse.getResponseValue();
                        ajaxResponse.setRedirectURL(String.format("editPageTemplate.html?id=%s", templateId));
                    }
                }
                ajaxResponse.setSuccessToken(successToken);
                ajaxResponse.setStatus(200);
            }
            request.setAttribute("response", ajaxResponse);
        }
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/deletePageTemplate", method = RequestMethod.POST)
    public String deletePageTemplate(final HttpServletRequest request, final @RequestParam(value = "id", required = true) String templateId) {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        try {
            final Response wsResponse = templateService.delete(templateId);
            if (ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
                ajaxResponse.setStatus(200);
                ajaxResponse.setRedirectURL("pageTemplates.html");
                ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.PAGE_TEMPLATE_DELETED));
            } else {
                ajaxResponse.addError(new ErrorToken(Errors.PAGE_TEMPLATE_CANNOT_DELETE));
                ajaxResponse.setStatus(500);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ajaxResponse.addError(new ErrorToken(Errors.INTERNAL_ERROR));
        }
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/getFieldsForTemplate", method = RequestMethod.GET)
    public @ResponseBody
    BeanResponse getFieldsForTemplate(final @RequestParam(required = true, value = "id") String templateId) {
        final MetadataElementPageTemplateSearchBean searchBean = new MetadataElementPageTemplateSearchBean();
        searchBean.setKey(templateId);
        searchBean.setDeepCopy(true);
        final List<MetadataElementPageTemplate> templateList = templateService.findBeans(searchBean, 0, 1);

        final HashMap<String, MetadataType> typeMap = getFieldTypeMap();

        final List<CustomFieldSortableBean> customFieldList = new ArrayList<CustomFieldSortableBean>();

        if (CollectionUtils.isNotEmpty(templateList)) {
        	final MetadataElementPageTemplate template = templateList.get(0);

            if (CollectionUtils.isNotEmpty(template.getMetadataElements())) {

            	final MetadataElementSearchBean searchElementBean = new MetadataElementSearchBean();
            	searchElementBean.setDeepCopy(false);

                for (MetadataElementPageTemplateXref t : template.getMetadataElements()) {
                	searchElementBean.addKey(t.getId().getMetadataElementId());
                }
                final List<MetadataElement> fieldList = metadataWebService.findElementBeans(searchElementBean, 0, Integer.MAX_VALUE, getCurrentLanguage());
                if (CollectionUtils.isNotEmpty(fieldList)) {
                    final HashMap<String, MetadataElement> fieldMap = new HashMap<String, MetadataElement>();
                    for (final MetadataElement e : fieldList) {
                        fieldMap.put(e.getId(), e);
                    }

                    for (final MetadataElementPageTemplateXref t : template.getMetadataElements()) {
                    	final MetadataElement element = fieldMap.get(t.getId().getMetadataElementId());

                        if (element != null) {
                        	final MetadataType type = typeMap.get(element.getMetadataTypeId());

                        	final CustomFieldSortableBean f = new CustomFieldSortableBean();
                            f.setId(element.getId());
                            f.setDisplayOrder(t.getDisplayOrder());
                            f.setName(element.getAttributeName());
                            f.setTypeId(element.getMetadataTypeId());

                            if (type != null) {
                                f.setTypeId(type.getId());
                                f.setFieldTypeDescription(type.getDescription());
                            }

                            customFieldList.add(f);
                        }
                    }
                }
                Collections.sort(customFieldList);
            }
        }
        return new BeanResponse(customFieldList, customFieldList.size());
    }

    @RequestMapping(value = "/getPatternsForTemplates", method = RequestMethod.GET)
    public @ResponseBody
    BeanResponse getPatternsForTemplates(final @RequestParam(required = true, value = "id") String templateId) {
        final MetadataElementPageTemplateSearchBean searchBean = new MetadataElementPageTemplateSearchBean();
        searchBean.setKey(templateId);
        searchBean.setDeepCopy(true);
        final List<MetadataElementPageTemplate> templateList = templateService.findBeans(searchBean, 0, 1);

        List<PageTemplatePatternBean> patternBeansList = new ArrayList<PageTemplatePatternBean>();

        if (templateList != null && !templateList.isEmpty()) {
            MetadataElementPageTemplate template = templateList.get(0);

            if (template.getUriPatterns() != null && !template.getUriPatterns().isEmpty()) {

                for (URIPattern p : template.getUriPatterns()) {
                    PageTemplatePatternBean bean = new PageTemplatePatternBean();
                    bean.setId(p.getId());
                    bean.setProviderId(p.getContentProviderId());
                    bean.setProviderName(p.getContentProviderName());
                    bean.setPattern(p.getPattern());

                    patternBeansList.add(bean);
                }

            }
        }
        return new BeanResponse(patternBeansList, patternBeansList.size());
    }

    @RequestMapping(value = "/getTemplateFields", method = RequestMethod.GET)
    public @ResponseBody
    BeanResponse getTemplateFields(final @RequestParam(required = true, value = "id") String templateId) {
        final MetadataElementPageTemplateSearchBean searchBean = new MetadataElementPageTemplateSearchBean();
        searchBean.setKey(templateId);
        searchBean.setDeepCopy(true);
        final List<MetadataElementPageTemplate> templateList = templateService.findBeans(searchBean, 0, 1);

        List<TemplateFieldBean> resultList = new ArrayList<TemplateFieldBean>();

        if (templateList != null && !templateList.isEmpty()) {
            MetadataElementPageTemplate template = templateList.get(0);

            if (template.getFieldXrefs() != null && !template.getFieldXrefs().isEmpty()) {

                final MetadataTemplateTypeFieldSearchBean searchFieldBean = new MetadataTemplateTypeFieldSearchBean();
                searchFieldBean.setTemplateId(templateId);
                searchFieldBean.setDeepCopy(false);
                final List<MetadataTemplateTypeField> fieldList = templateService.findUIFIelds(searchFieldBean, 0, Integer.MAX_VALUE);

                if (fieldList != null && !fieldList.isEmpty()) {
                    HashMap<String, MetadataTemplateTypeField> fieldMap = new HashMap<String, MetadataTemplateTypeField>();
                    for (MetadataTemplateTypeField f : fieldList) {
                        fieldMap.put(f.getId(), f);
                    }

                    for (final MetadataFieldTemplateXref xref : template.getFieldXrefs()) {
                    	final MetadataTemplateTypeField element = fieldMap.get(xref.getField().getId());

                        if (element != null) {

                            final TemplateFieldBean bean = new TemplateFieldBean();
                            bean.setXrefId(xref.getId());
                            bean.setId(element.getId());
                            bean.setFieldId(element.getId());
                            bean.setName(element.getName());
                            bean.setIsRequired(xref.isRequired());
                            bean.setEditable(xref.isEditable());
                            bean.setDisplayOrder(xref.getDisplayOrder());
                            bean.setLanguageMap(xref.getLanguageMap());
                            resultList.add(bean);

                        }
                    }
                }
                Collections.sort(resultList);
            }
        }
        return new BeanResponse(resultList, resultList.size());
    }

    @RequestMapping(value = "/searchTemplateFields", method = RequestMethod.GET)
    public @ResponseBody
    BeanResponse searchTemplateFields(@RequestParam(required = true, value = "templateTypeId") String templateTypeId) {
        final List<TemplateFieldBean> fieldList = new ArrayList<TemplateFieldBean>();
        int count = 0;
        if (StringUtils.isNotEmpty(templateTypeId)) {

            final MetadataTemplateTypeFieldSearchBean searchBean = new MetadataTemplateTypeFieldSearchBean();
            searchBean.setTemplateTypeId(templateTypeId);
            searchBean.setDeepCopy(false);
            final List<MetadataTemplateTypeField> resultList = templateService.findUIFIelds(searchBean, 0, Integer.MAX_VALUE);
            count = templateService.countUIFields(searchBean);

            if (resultList != null && !resultList.isEmpty()) {
                for (final MetadataTemplateTypeField dto : resultList) {
                	final TemplateFieldBean bean = new TemplateFieldBean();
                    bean.setId(dto.getId());
                    bean.setFieldId(dto.getId());
                    bean.setName(dto.getName());
                    bean.setIsRequired(dto.isRequired());
                    fieldList.add(bean);
                }
            }
        }
        return new BeanResponse(fieldList, count);
    }

    @RequestMapping(value = "/searchPattens", method = RequestMethod.GET)
    public @ResponseBody
    BeanResponse searchPattens(@RequestParam(required = false, value = "name") String pattern,
                               final @RequestParam(required = true, value = "size") Integer size,
                               final @RequestParam(required = true, value = "from") Integer from) {
        URIPatternSearchBean searchBean = new URIPatternSearchBean();
        searchBean.setPattern(pattern);
        searchBean.setDeepCopy(false);
        final List<URIPattern> patternList = contentProviderServiceClient.findUriPatterns(searchBean, from, size);
        final Integer count = contentProviderServiceClient.getNumOfUriPatterns(searchBean);
        final List<URIPatternSearchableBean> beanList = new LinkedList<URIPatternSearchableBean>();
        if (CollectionUtils.isNotEmpty(patternList)) {
            for (final URIPattern p : patternList) {
                beanList.add(new URIPatternSearchableBean(p.getId(), p.getContentProviderId(), p.getContentProviderName(), p.getPattern(), p.getIsPublic(), p.getResourceName()));
            }
        }
        return new BeanResponse(beanList, count);
    }

    private MetadataElementPageTemplate convertFromModel(PageTemplateBean templateBean) {
        MetadataElementPageTemplate dto = new MetadataElementPageTemplate();

        dto.setId(templateBean.getId());
        dto.setIsPublic(templateBean.getIsPublic());
        dto.setName(templateBean.getName());
        dto.setResourceId(templateBean.getResourceId());
        dto.setMetadataTemplateTypeId(templateBean.getTemplateTypeId());

        if (templateBean.getPatternList() != null && !templateBean.getPatternList().isEmpty()) {
            dto.setUriPatterns(new HashSet<URIPattern>(templateBean.getPatternList()));
        }
        if (templateBean.getCustomFieldList() != null && !templateBean.getCustomFieldList().isEmpty()) {
            dto.setMetadataElements(new HashSet<MetadataElementPageTemplateXref>(templateBean.getCustomFieldList()));
        }
        if (templateBean.getTemplateFieldList() != null && !templateBean.getTemplateFieldList().isEmpty()) {
            dto.setFieldXrefs(new HashSet<MetadataFieldTemplateXref>(templateBean.getTemplateFieldList()));
        }
        return dto;
    }

    private void populatePage(HttpServletRequest request, MetadataElementPageTemplate template) throws Exception {

        final List<Language> languageList = languageWebService.getUsedLanguages(getCurrentLanguage());
        request.setAttribute("languageList", languageList);
    	
        PageTemplateBean bean = new PageTemplateBean();

        bean.setId(template.getId());
        bean.setName(template.getName());
        bean.setIsPublic(template.getIsPublic());
        bean.setTemplateTypeId(template.getMetadataTemplateTypeId());
        // bean.setPatternList(new
        // ArrayList<URIPattern>(template.getUriPatterns()));

        if (template.getResourceId() != null && !template.getResourceId().isEmpty()) {
            org.openiam.idm.srvc.res.dto.Resource res = resourceDataService.getResource(template.getResourceId(), getCurrentLanguage());
            if (res != null) {
                bean.setResourceId(template.getResourceId());
                if(StringUtils.isNotBlank(res.getCoorelatedName())) {
                	bean.setResourceName(res.getCoorelatedName());
                } else {
                	bean.setResourceName(template.getName());
                }
            }
        }

        request.setAttribute("templateTypeList", getTemplateTypeList());
        request.setAttribute("typeList", getFieldTypeList());
        request.setAttribute("template", bean);

        if (template.getId() == null || template.getId().trim().isEmpty()) {
            setMenuTree(request, templateRootMenuName);
        } else {
            setMenuTree(request, templateEditMenuName);
        }
    }

}
