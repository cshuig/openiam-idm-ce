package org.openiam.ui.webconsole.web.model;

import org.openiam.idm.srvc.lang.dto.LanguageMapping;
import org.openiam.idm.srvc.meta.dto.MetadataElementPageTemplate;
import org.openiam.idm.srvc.meta.dto.MetadataValidValue;
import org.openiam.ui.web.model.AbstractBean;

import java.util.List;
import java.util.Map;

public class CustomFieldBean extends AbstractBean{
    private String resourceId;
    private String resourceName;
    private String typeId;
    private String fieldTypeDescription;
    private String name;
    private String staticDefaultValue;
    private boolean isRequired;
    private boolean isPublic;
    private boolean isEditable;
    private List<MetadataElementPageTemplate> pageTemplates;
    private Map<String, LanguageMapping> defaultValueLanguageMap;
    private List<MetadataValidValue> validValues;
    private Map<String, LanguageMapping> displayNameLanguageMap;


    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStaticDefaultValue() {
        return staticDefaultValue;
    }

    public void setStaticDefaultValue(String staticDefaultValue) {
        this.staticDefaultValue = staticDefaultValue;
    }

    public Map<String, LanguageMapping> getDefaultValueLanguageMap() {
        return defaultValueLanguageMap;
    }

    public void setDefaultValueLanguageMap(Map<String, LanguageMapping> defaultValueLanguageMap) {
        this.defaultValueLanguageMap = defaultValueLanguageMap;
    }

    public List<MetadataValidValue> getValidValues() {
        return validValues;
    }

    public void setValidValues(List<MetadataValidValue> validValues) {
        this.validValues = validValues;
    }

    public Map<String, LanguageMapping> getDisplayNameLanguageMap() {
        return displayNameLanguageMap;
    }

    public void setDisplayNameLanguageMap(Map<String, LanguageMapping> displayNameLanguageMap) {
        this.displayNameLanguageMap = displayNameLanguageMap;
    }

    public List<MetadataElementPageTemplate> getPageTemplates() {
        return pageTemplates;
    }

    public void setPageTemplates(List<MetadataElementPageTemplate> pageTemplates) {
        this.pageTemplates = pageTemplates;
    }

    public boolean getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(boolean required) {
        isRequired = required;
    }

    public boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public boolean getIsEditable() {
        return isEditable;
    }

    public void setIsEditable(boolean editable) {
        isEditable = editable;
    }

    public String getFieldTypeDescription() {
        return fieldTypeDescription;
    }

    public void setFieldTypeDescription(String fieldTypeDescription) {
        this.fieldTypeDescription = fieldTypeDescription;
    }
}
