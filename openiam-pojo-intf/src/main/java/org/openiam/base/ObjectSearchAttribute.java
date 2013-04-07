package org.openiam.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Object Search attribute is used when you want to search for an object based on their attributes. These are
 * attributes that are defined as name value pairs
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ObjectSearchAttribute", propOrder = {
        "attributeName",
        "attributeValue"
})
public class ObjectSearchAttribute {
    String attributeName;
    String attributeValue;

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    @Override
    public String toString() {
        return "ObjectSearchAttribute{" +
                "attributeName='" + attributeName + '\'' +
                ", attributeValue='" + attributeValue + '\'' +
                '}';
    }
}
