package org.openiam.ui.webconsole.am.web.model;

import org.openiam.base.AttributeOperationEnum;
import org.openiam.ui.web.model.AbstractBean;

public class MetaValueBean extends AbstractBean {
    private AttributeOperationEnum operation=AttributeOperationEnum.ADD;
    private String name;
    private String staticValue;
    private String amAttributeId;
    private String groovyScript;

    public AttributeOperationEnum getOperation() {
        return operation;
    }

    public void setOperation(AttributeOperationEnum operation) {
        this.operation = operation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStaticValue() {
        return staticValue;
    }

    public void setStaticValue(String staticValue) {
        this.staticValue = staticValue;
    }

    public String getAmAttributeId() {
        return amAttributeId;
    }

    public void setAmAttributeId(String amAttributeId) {
        this.amAttributeId = amAttributeId;
    }

	public String getGroovyScript() {
		return groovyScript;
	}

	public void setGroovyScript(String groovyScript) {
		this.groovyScript = groovyScript;
	}
    
}
