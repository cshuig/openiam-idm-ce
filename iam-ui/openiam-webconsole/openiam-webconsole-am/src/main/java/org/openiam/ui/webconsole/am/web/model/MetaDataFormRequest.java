package org.openiam.ui.webconsole.am.web.model;

import java.io.Serializable;
import java.util.List;

public class MetaDataFormRequest implements Serializable {
    private String metaDataId;
    private String metaTypeId;
    private String metaDataName;
    private String uriPatternId;
    private List<MetaValueBean> valueList;

    public String getMetaDataId() {
        return metaDataId;
    }

    public void setMetaDataId(String metaDataId) {
        this.metaDataId = metaDataId;
    }

    public String getMetaTypeId() {
        return metaTypeId;
    }

    public void setMetaTypeId(String metaTypeId) {
        this.metaTypeId = metaTypeId;
    }

    public List<MetaValueBean> getValueList() {
        return valueList;
    }

    public void setValueList(List<MetaValueBean> valueList) {
        this.valueList = valueList;
    }

    public String getUriPatternId() {
        return uriPatternId;
    }

    public void setUriPatternId(String uriPatternId) {
        this.uriPatternId = uriPatternId;
    }

    public String getMetaDataName() {
        return metaDataName;
    }

    public void setMetaDataName(String metaDataName) {
        this.metaDataName = metaDataName;
    }
}
