package org.openiam.ui.rest.api.model;

public class MetadataElementBean extends KeyNameBean {

    public MetadataElementBean() {
    }

    private String defaultValue;
    private String defaultName;
    private String typeName;
    private String type;
    private String metaTypeIdSearch;
    private String metaNameSearh;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMetaTypeIdSearch() {
        return metaTypeIdSearch;
    }

    public void setMetaTypeIdSearch(String metaTypeIdSearch) {
        this.metaTypeIdSearch = metaTypeIdSearch;
    }

    public String getMetaNameSearh() {
        return metaNameSearh;
    }

    public void setMetaNameSearh(String metaNameSearh) {
        this.metaNameSearh = metaNameSearh;
    }

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

    public String getDefaultName() {
        return defaultName;
    }

    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }
}
