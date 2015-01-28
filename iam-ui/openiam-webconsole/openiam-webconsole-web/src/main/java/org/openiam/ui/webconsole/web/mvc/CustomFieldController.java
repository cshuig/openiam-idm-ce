package org.openiam.ui.webconsole.web.mvc;

import org.apache.commons.lang.StringUtils;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.searchbeans.MetadataElementPageTemplateSearchBean;
import org.openiam.idm.searchbeans.MetadataElementSearchBean;
import org.openiam.idm.srvc.lang.dto.Language;
import org.openiam.idm.srvc.meta.dto.MetadataElement;
import org.openiam.idm.srvc.meta.dto.MetadataElementPageTemplate;
import org.openiam.idm.srvc.meta.dto.MetadataElementPageTemplateXref;
import org.openiam.idm.srvc.meta.dto.MetadataType;
import org.openiam.idm.srvc.meta.dto.MetadataValidValue;
import org.openiam.ui.rest.api.model.TranslateBean;
import org.openiam.ui.rest.client.factory.TranslateApiClientFactory;
import org.openiam.ui.rest.constant.ClientProvider;
import org.openiam.ui.rest.response.GoogleTranslateResponse;
import org.openiam.ui.rest.response.bean.GoogleTranslation;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.webconsole.validator.CustomFieldValidator;
import org.openiam.ui.webconsole.web.model.CustomFieldBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Controller
public class CustomFieldController extends BaseTemplateController {


    @Value("${org.openiam.ui.custom.field.root.menu.id}")
    private String fieldRootMenuName;

    @Value("${org.openiam.ui.custom.field.edit.menu.id}")
    private String fieldEditMenuName;

    @Value("${org.openiam.ui.api.translate.provider}")
    private ClientProvider translateApiProvider;

    @Autowired
    private CustomFieldValidator customFieldValidator;

    @Autowired
    private TranslateApiClientFactory translateApiClientFactory;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(customFieldValidator);
    }

    @RequestMapping(value="/customFields", method= RequestMethod.GET)
    public String getCustomFieldList(final HttpServletRequest request, 
    								 final HttpServletResponse response) throws Exception {
    	request.setAttribute("typeListAsJSON", jacksonMapper.writeValueAsString(getFieldTypeListAsKeyNameBeans()));
        setMenuTree(request, fieldRootMenuName);
        return "customFields/search";
    }

    @RequestMapping(value="/newCustomField", method= RequestMethod.GET)
    public String newtCustomField(final HttpServletRequest request, final HttpServletResponse response){
        populatePage(request, new MetadataElement());
        return "customFields/edit";
    }

    @RequestMapping(value="/editCustomField", method= RequestMethod.GET)
    public String editCustomField(final HttpServletRequest request, final HttpServletResponse response,
                                  @RequestParam(value="id", required=true) String fieldId) throws IOException {
        final MetadataElementSearchBean searchBean = new MetadataElementSearchBean();
        searchBean.setKey(fieldId);
        searchBean.setDeepCopy(true);
        final List<MetadataElement> fieldList = metadataWebService.findElementBeans(searchBean, 0, 1, getCurrentLanguage());
        if(fieldList == null || fieldList.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, String.format("Custom Field with ID: '%' - not found", fieldId));
            return null;
        }
        populatePage(request, fieldList.get(0));
        return "customFields/edit";
    }

    @RequestMapping(value="/saveCustomField", method= RequestMethod.POST)
    public String saveCustomField(final HttpServletRequest request, final HttpServletResponse response,
                                  @RequestBody @Valid CustomFieldBean customField){
        MetadataElement element = convertFromModel(customField);

        ErrorToken errorToken = null;
        SuccessToken successToken = null;
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        Response wsResponse = null;
        try {
            if(errorToken == null) {
                wsResponse = metadataWebService.saveMetadataEntity(element);

                if(wsResponse.getStatus() == ResponseStatus.SUCCESS) {
                    successToken = new SuccessToken(SuccessMessage.CUSTOM_FIELD_SAVED);
                } else {
                    Errors error = Errors.CANNOT_SAVE_CUSTOM_FIELD;
                    if(wsResponse.getErrorCode() != null) {
                        switch(wsResponse.getErrorCode()) {
                            case ATTRIBUTE_NAME_MISSING:
                                error = Errors.CUSTOM_FIELD_NAME_NOT_SET;
                                break;
                            case METADATA_TYPE_MISSING:
                                error = Errors.CUSTOM_FIELD_TYPE_NOT_SET;
                                break;
                            case NAME_TAKEN:
                            	error = Errors.FIELD_NAME_TAKEN;
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
            log.error("Exception while saving custom field", e);
        } finally {
            if(errorToken != null) {
                ajaxResponse.setStatus(500);
                ajaxResponse.addError(errorToken);
            } else {
                if(wsResponse != null && wsResponse.getResponseValue() != null) {
                    if(wsResponse.getResponseValue() instanceof String) {
                        final String fieldId = (String)wsResponse.getResponseValue();
                        ajaxResponse.setRedirectURL(String.format("editCustomField.html?id=%s", fieldId));
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

    @RequestMapping(value="/deleteCustomField", method=RequestMethod.POST)
    public String deleteCustomField(final HttpServletRequest request,
                                    final @RequestParam(value="id", required=true) String fieldId) {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        try {
            final Response wsResponse = metadataWebService.deleteMetadataElement(fieldId);
            if(ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
                ajaxResponse.setStatus(200);
                ajaxResponse.setRedirectURL("customFields.html");
                ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.CUSTOM_FIELD_DELETED));
            } else {
                ajaxResponse.addError(new ErrorToken(Errors.CUSTOM_FIELD_CANNOT_DELETE));
                ajaxResponse.setStatus(500);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ajaxResponse.addError(new ErrorToken(Errors.INTERNAL_ERROR));
        }
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value="/translateLabels", method=RequestMethod.POST)
    public @ResponseBody
    TranslateBean translateLabels(final HttpServletRequest request,
                                 final @RequestBody TranslateBean translateBean) {
        TranslateBean beanResponse = new TranslateBean();
        try {

            if(translateBean.getText()!=null && !translateBean.getText().trim().isEmpty()){

                   GoogleTranslateResponse translateResponse =  translateApiClientFactory.getApiClient(translateApiProvider).translate(translateBean.getText(),
                                                                                                                                       translateBean.getSourceLang(),
                                                                                                                                       translateBean.getTargetLang());

                   if(translateResponse.getData()!=null && translateResponse.getData().getTranslations()!=null && !translateResponse.getData().getTranslations().isEmpty()){
                       // TODO: need to think how to make it more common ( independent from api provider)
                        for(GoogleTranslation translation: translateResponse.getData().getTranslations()){
                            beanResponse.setText(translation.getTranslatedText());
                        }
                   }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return beanResponse;
    }

    private String getLanguageFromLocale(String locale) {
        String result = "en";

        if(locale!=null && !locale.trim().isEmpty()){

            String[] strArray = locale.split("_");

            if(strArray!=null && strArray.length>0)
                result=strArray[0];
        }
        return result;
    }

    private void populatePage(HttpServletRequest request, MetadataElement metadataElement) {
        CustomFieldBean field = new CustomFieldBean();
        field.setId(metadataElement.getId());
        field.setName(metadataElement.getAttributeName());
        field.setStaticDefaultValue(metadataElement.getStaticDefaultValue());
        field.setTypeId(metadataElement.getMetadataTypeId());
        field.setDisplayNameLanguageMap(metadataElement.getLanguageMap());
        field.setDefaultValueLanguageMap(metadataElement.getDefaultValueLanguageMap());
        field.setIsRequired(metadataElement.getRequired());
        field.setIsPublic(metadataElement.getIsPublic());
        field.setIsEditable(metadataElement.getSelfEditable());

        if(metadataElement.getValidValues()!=null){
            List<MetadataValidValue> values = new ArrayList<MetadataValidValue>(metadataElement.getValidValues());
            Collections.sort(values);
            field.setValidValues(values);
        }

        if(metadataElement.getResourceId()!=null && !metadataElement.getResourceId().isEmpty()){
            org.openiam.idm.srvc.res.dto.Resource res = resourceDataService.getResource(metadataElement.getResourceId(), getCurrentLanguage());
            if(res!=null){
                field.setResourceId(metadataElement.getResourceId());
                if(StringUtils.isNotBlank(res.getCoorelatedName())) {
                	field.setResourceName(res.getCoorelatedName());
                } else {
                	field.setResourceName(res.getName());
                }
            }
        }

        if(metadataElement.getTemplateSet()!=null && !metadataElement.getTemplateSet().isEmpty()){
            MetadataElementPageTemplateSearchBean searchBean = new MetadataElementPageTemplateSearchBean();
            for (MetadataElementPageTemplateXref t: metadataElement.getTemplateSet()){
                searchBean.addKey(t.getId().getMetadataElementPageTemplateId());
            }
            List<MetadataElementPageTemplate> templateList = templateService.findBeans(searchBean, 0, Integer.MAX_VALUE);
            field.setPageTemplates(templateList);
        }
        
        final List<MetadataType> fieldTypeList = getFieldTypeList();
        request.setAttribute("field", field);
        request.setAttribute("typeList", fieldTypeList);

        List<Language> languageList = languageWebService.getUsedLanguages(getCurrentLanguage());
        request.setAttribute("languageList", languageList);

        if(metadataElement.getId() == null || metadataElement.getId().trim().isEmpty()) {
            setMenuTree(request, fieldRootMenuName);
        } else {
            setMenuTree(request, fieldEditMenuName);
        }
    }

    private MetadataElement convertFromModel(CustomFieldBean customField) {
        MetadataElement element = new MetadataElement();
        element.setId(customField.getId());
        element.setAttributeName(customField.getName());
        element.setMetadataTypeId(customField.getTypeId());
        element.setResourceId(customField.getResourceId());
        element.setRequired(customField.getIsRequired());
        element.setIsPublic(customField.getIsPublic());
        element.setSelfEditable(customField.getIsEditable());

        element.setStaticDefaultValue(customField.getStaticDefaultValue());
        if(customField.getValidValues()!=null && !customField.getValidValues().isEmpty()){
            element.setValidValues(new HashSet<MetadataValidValue>(customField.getValidValues()));
        }

        if(customField.getDefaultValueLanguageMap()!=null && !customField.getDefaultValueLanguageMap().isEmpty()){
            element.setDefaultValueLanguageMap(customField.getDefaultValueLanguageMap());
        }
        if(customField.getDisplayNameLanguageMap()!=null && !customField.getDisplayNameLanguageMap().isEmpty()){
            element.setLanguageMap(customField.getDisplayNameLanguageMap());
        }

        return element;
    }
}
