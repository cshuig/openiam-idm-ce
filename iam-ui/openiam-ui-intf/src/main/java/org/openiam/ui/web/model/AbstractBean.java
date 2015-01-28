package org.openiam.ui.web.model;

import java.io.Serializable;

public class AbstractBean implements Serializable {
    private String id;
    private Boolean hasChild=false;
    private String beanType = this.getClass().getSimpleName();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public boolean getHasChild() {
        return hasChild;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }

    public String getBeanType() {
        return beanType;
    }
    public void setBeanType(String beanType){
        this.beanType=beanType;
    }

}
