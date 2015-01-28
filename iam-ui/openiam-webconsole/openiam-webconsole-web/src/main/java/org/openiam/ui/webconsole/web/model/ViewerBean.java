package org.openiam.ui.webconsole.web.model;

import java.io.Serializable;

public class ViewerBean implements Serializable {

    private String attributeName;
    private String idmAttribute;
    private String mngSysAttribute;
    private boolean readOnly;
    private boolean changed;

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getIdmAttribute() {
        return idmAttribute;
    }

    public void setIdmAttribute(String idmAttribute) {
        this.idmAttribute = idmAttribute;
    }

    public String getMngSysAttribute() {
        return mngSysAttribute;
    }

    public void setMngSysAttribute(String mngSysAttribute) {
        this.mngSysAttribute = mngSysAttribute;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }
}
