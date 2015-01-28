package org.openiam.ui.webconsole.web.model;

import java.util.Map;

import org.openiam.idm.srvc.lang.dto.Language;
import org.openiam.idm.srvc.lang.dto.LanguageMapping;
import org.openiam.ui.web.model.AbstractBean;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 7/30/13
 * Time: 11:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class TemplateFieldBean extends AbstractBean implements Comparable<TemplateFieldBean>{
    private String name;
    private boolean isRequired;
    private boolean editable = true;
    private Integer displayOrder;
    private String fieldId;
    private String xrefId;
    private Map<String, LanguageMapping> languageMap;

    public String getName() {
        return name;
    }

    public void setName(String fieldName) {
        this.name = fieldName;
    }

    public boolean getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(boolean required) {
        isRequired = required;
    }

    public boolean getEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    @Override
    public int compareTo(TemplateFieldBean o) {
        return this.displayOrder.compareTo(o.displayOrder);
    }

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getXrefId() {
		return xrefId;
	}

	public void setXrefId(String xrefId) {
		this.xrefId = xrefId;
	}

	public Map<String, LanguageMapping> getLanguageMap() {
		return languageMap;
	}

	public void setLanguageMap(Map<String, LanguageMapping> languageMap) {
		this.languageMap = languageMap;
	}
}
