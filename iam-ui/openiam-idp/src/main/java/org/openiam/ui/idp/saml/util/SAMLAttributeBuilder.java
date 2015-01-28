package org.openiam.ui.idp.saml.util;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openiam.am.srvc.dto.SSOAttribute;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeValue;
import org.opensaml.saml2.core.impl.AttributeBuilder;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.schema.XSAny;
import org.opensaml.xml.schema.XSInteger;
import org.opensaml.xml.schema.XSString;
import org.opensaml.xml.schema.XSURI;
import org.opensaml.xml.schema.impl.XSAnyBuilder;
import org.opensaml.xml.schema.impl.XSIntegerBuilder;
import org.opensaml.xml.schema.impl.XSStringBuilder;
import org.opensaml.xml.schema.impl.XSURIBuilder;

public class SAMLAttributeBuilder {
	
	private static Logger LOG = Logger.getLogger(SAMLAttributeBuilder.class);
	
	public static Attribute buildMetadataAttribute(final SSOAttribute attribute, final XMLObjectBuilderFactory builderFactory) {
		final Attribute attr = ((AttributeBuilder)builderFactory.getBuilder(Attribute.DEFAULT_ELEMENT_NAME)).buildObject();
		attr.setName(attribute.getTargetAttributeName());
		return attr;
	}

	public static  Attribute buildAttribute(final SSOAttribute attribute, final XMLObjectBuilderFactory builderFactory) {
		try {
			final Attribute attr = ((AttributeBuilder)builderFactory.getBuilder(Attribute.DEFAULT_ELEMENT_NAME)).buildObject();
			attr.setName(attribute.getTargetAttributeName());
			attr.setNameFormat(Attribute.UNSPECIFIED);

			XMLObject attrValue = null;
			switch(attribute.getAttributeType()) {
			 	case Any:
			 		attrValue = getXSAny(attribute.getAttributeValue(), builderFactory);
			 		attr.getAttributeValues().add(attrValue);
			 		break;
			 	case Integer:
			 		attrValue = getXSInteger(attribute.getAttributeValue(), builderFactory);
			 		attr.getAttributeValues().add(attrValue);
			 		break;
			 	case String:
			 		attrValue = getXSString(attribute.getAttributeValue(), builderFactory);
			 		attr.getAttributeValues().add(attrValue);
			 		break;
			 	case URI:
			 		attrValue = getXSURI(attribute.getAttributeValue(), builderFactory);
			 		attr.getAttributeValues().add(attrValue);
			 		break;
			 	case LIST_OF_ANY:
			 		attr.getAttributeValues().addAll(getXSAnyList(attribute.getAttributeValue(), builderFactory));
			 		break;
			 	case LIST_OF_INTEGERS:
			 		attr.getAttributeValues().addAll(getXSIntegerList(attribute.getAttributeValue(), builderFactory));
			 		break;
			 	case LIST_OF_STRINGS:
			 		attr.getAttributeValues().addAll(getXSStringList(attribute.getAttributeValue(), builderFactory));
			 		break;
			 	default:
			 		attrValue = getXSAny(attribute.getAttributeValue(), builderFactory);
			 		attr.getAttributeValues().add(attrValue);
			 		break;
			}
			return attr;
		} catch(Throwable e) {
			LOG.warn("Can't create attribute", e);
			return null;
		}
	}
	
	private static XSInteger getXSInteger(final String value, final XMLObjectBuilderFactory builderFactory) {
		final XSInteger attrValue = ((XSIntegerBuilder)builderFactory.getBuilder(XSInteger.TYPE_NAME)).buildObject(AttributeValue.DEFAULT_ELEMENT_NAME, XSInteger.TYPE_NAME);
		try {
			attrValue.setValue(Integer.valueOf(value));
		} catch(Throwable e) {
			
		}
		return attrValue;
	}
	
	private static List<XSInteger> getXSIntegerList(final String value, final XMLObjectBuilderFactory builderFactory) {
		final List<XSInteger> retVal = new LinkedList<XSInteger>();
		final List<String> valueList = getValueFromString(value);
		for(final String parsedValue : valueList) {
			retVal.add(getXSInteger(parsedValue, builderFactory));
		}
		return retVal;
	}
	
	private static XSString getXSString(final String value, final XMLObjectBuilderFactory builderFactory) {
		final XSString attrValue = ((XSStringBuilder)builderFactory.getBuilder(XSString.TYPE_NAME)).buildObject(AttributeValue.DEFAULT_ELEMENT_NAME, XSString.TYPE_NAME);
		if(StringUtils.isNotBlank(value)) {
			attrValue.setValue(value);
		}
		return attrValue;
	}
	
	private static List<XSString> getXSStringList(final String value, final XMLObjectBuilderFactory builderFactory) {
		final List<XSString> retVal = new LinkedList<XSString>();
		final List<String> valueList = getValueFromString(value);
		for(final String parsedValue : valueList) {
			retVal.add(getXSString(parsedValue, builderFactory));
		}
		return retVal;
	}
	
	private static XSURI getXSURI(final String value, final XMLObjectBuilderFactory builderFactory) {
		final XSURI attrValue = ((XSURIBuilder)builderFactory.getBuilder(XSURI.TYPE_NAME)).buildObject(AttributeValue.DEFAULT_ELEMENT_NAME, XSURI.TYPE_NAME);
		if(StringUtils.isNotBlank(value)) {
			attrValue.setValue(value);
		}
		return attrValue;
	}
	
	private static XSAny getXSAny(final String value, final XMLObjectBuilderFactory builderFactory) {
		final XSAny attrValue = ((XSAnyBuilder)builderFactory.getBuilder(XSAny.TYPE_NAME)).buildObject(AttributeValue.DEFAULT_ELEMENT_NAME, XSAny.TYPE_NAME);
		if(StringUtils.isNotBlank(value)) {
			attrValue.setTextContent(value);
		}
		return attrValue;
	}
	
	private static List<XSAny> getXSAnyList(final String value, final XMLObjectBuilderFactory builderFactory) {
		final List<XSAny> retVal = new LinkedList<XSAny>();
		final List<String> valueList = getValueFromString(value);
		for(final String parsedValue : valueList) {
			retVal.add(getXSAny(parsedValue, builderFactory));
		}
		return retVal;
	}
	
	private static List<String> getValueFromString(final String str) {
		final List<String> listOfStrings = new LinkedList<String>();
		final String[] parsedList = StringUtils.split(str, "|");
		if(parsedList != null) {
			for(final String parsedValue : parsedList) {
				if(StringUtils.isNotBlank(parsedValue)) {
					listOfStrings.add(parsedValue);
				}
			}
		}
		
		return listOfStrings;
	}
}
