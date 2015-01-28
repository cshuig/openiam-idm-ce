package org.openiam.ui.webconsole.web.model;

import org.openiam.am.srvc.dto.URIPattern;
import org.openiam.idm.srvc.meta.dto.MetadataElementPageTemplateXref;
import org.openiam.idm.srvc.meta.dto.MetadataFieldTemplateXref;
import org.openiam.ui.web.model.AbstractBean;

import java.util.List;

public class PageTemplateBean extends AbstractBean {
    private String resourceId;
    private String resourceName;
    private String name;
    private boolean isPublic;
    private String templateTypeId;

    private List<URIPattern> patternList;
    private List<MetadataElementPageTemplateXref> customFieldList;
    private List<MetadataFieldTemplateXref> templateFieldList;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public List<URIPattern> getPatternList() {
        return patternList;
    }

    public void setPatternList(List<URIPattern> patternList) {
        this.patternList = patternList;
    }

    public List<MetadataElementPageTemplateXref> getCustomFieldList() {
        return customFieldList;
    }

    public void setCustomFieldList(List<MetadataElementPageTemplateXref> customFieldList) {
        this.customFieldList = customFieldList;
    }

    public String getTemplateTypeId() {
        return templateTypeId;
    }

    public void setTemplateTypeId(String templateTypeId) {
        this.templateTypeId = templateTypeId;
    }

    public List<MetadataFieldTemplateXref> getTemplateFieldList() {
        return templateFieldList;
    }

    public void setTemplateFieldList(List<MetadataFieldTemplateXref> templateFieldList) {
        this.templateFieldList = templateFieldList;
    }
}
