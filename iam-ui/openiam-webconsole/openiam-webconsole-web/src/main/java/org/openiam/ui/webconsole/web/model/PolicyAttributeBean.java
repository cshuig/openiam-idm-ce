package org.openiam.ui.webconsole.web.model;

import org.openiam.ui.rest.api.model.KeyNameBean;

/**
 * Created by zaporozhec on 10/1/14.
 */
public class PolicyAttributeBean extends KeyNameBean {
    protected String policyId;
    protected String defParamId;
    protected String operation;
    protected String value1;
    protected String value2;
    protected String rule;
    protected String description;
    protected String grouping;
    protected boolean required = true;

    public String getGrouping() {
        return grouping;
    }

    public void setGrouping(String grouping) {
        this.grouping = grouping;
    }

    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    public String getDefParamId() {
        return defParamId;
    }

    public void setDefParamId(String defParamId) {
        this.defParamId = defParamId;
    }


    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "PolicyAttributeBean{" +
                "policyId='" + policyId + '\'' +
                ", defParamId='" + defParamId + '\'' +
                ", name='" + getName() + '\'' +
                ", operation='" + operation + '\'' +
                ", value1='" + value1 + '\'' +
                ", value2='" + value2 + '\'' +
                ", rule='" + rule + '\'' +
                ", required=" + required +
                '}';
    }
}
