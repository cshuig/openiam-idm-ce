package org.openiam.ui.rest.api.model;

import org.openiam.idm.srvc.org.dto.OrganizationAttribute;
import org.openiam.ui.web.model.AbstractBean;

import java.util.ArrayList;
import java.util.List;

public class OrganizationModel extends AbstractBean {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String name;
    private String organizationTypeId;
    private String description;
    private String abbreviation;
    private String internalOrgId;
    private String symbol;
    private boolean selectable;
    private String alias;
    private String domainName;
    private String ldapStr;
    private String mdTypeId;

    protected List<OrganizationAttribute> attributes = new ArrayList<OrganizationAttribute>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganizationTypeId() {
        return organizationTypeId;
    }

    public void setOrganizationTypeId(String organizationTypeId) {
        this.organizationTypeId = organizationTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getInternalOrgId() {
        return internalOrgId;
    }

    public void setInternalOrgId(String internalOrgId) {
        this.internalOrgId = internalOrgId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public boolean getSelectable() {
        return selectable;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getLdapStr() {
        return ldapStr;
    }

    public void setLdapStr(String ldapStr) {
        this.ldapStr = ldapStr;
    }

    public String getMdTypeId() {
        return mdTypeId;
    }

    public void setMdTypeId(String mdTypeId) {
        this.mdTypeId = mdTypeId;
    }

    public List<OrganizationAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<OrganizationAttribute> attributes) {
        this.attributes = attributes;
    }
}
