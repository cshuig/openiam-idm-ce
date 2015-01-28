package org.openiam.ui.selfservice.web.model;

import org.openiam.ui.rest.api.model.KeyNameBean;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 11/8/13
 * Time: 12:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class EntitlementTypeBean extends KeyNameBean {
    private String entitlementType;
    private String parentId;
    private String parentBeanType;

    public EntitlementTypeBean(){}

    public EntitlementTypeBean(String id, String name, String parentId){
        this(id, name, name, parentId);
    }
    public EntitlementTypeBean(String id, String name, String type, String parentId){
        this(id, name, type, parentId, null);
    }

    public EntitlementTypeBean(String id, String name, String type, String parentId, String parentBeanType){
        super(id, name);
        this.parentId=parentId;
        this.parentBeanType=parentBeanType;
        this.entitlementType=type;
    }

    public String getEntitlementType() {
        return entitlementType;
    }

    public void setEntitlementType(String entitlementType) {
        this.entitlementType = entitlementType;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentBeanType() {
        return parentBeanType;
    }

    public void setParentBeanType(String parentBeanType) {
        this.parentBeanType = parentBeanType;
    }
}
