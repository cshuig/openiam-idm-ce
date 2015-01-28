package org.openiam.ui.idp.openid.model;


import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;


public class XRDCDocument {

	private static final Logger LOG = Logger.getLogger(XRDCDocument.class);
	
	private static final String MASTER_XRDS_NAMESPACE = "xri://$xrd*($v*2.0)";
	
	private Element baseElement;

	public XRDCDocument() {
		final Namespace xrdNS = Namespace.getNamespace(MASTER_XRDS_NAMESPACE);
	    baseElement = new Element("XRD", xrdNS);
	}
	

	public void addServiceElement(String[] type, String uri, String priority) {
		addServiceElement(type, uri, priority, null);
	}
	  
	private void addServiceElement(String[] typeArray, String uri, String priority, String delegate) {
	    final Namespace xrdNS = Namespace.getNamespace(MASTER_XRDS_NAMESPACE);
	    final Namespace openidNS = Namespace.getNamespace("openid", "http://openid.net/xmlns/2.0");
	    final Element serviceElement = new Element("Service", xrdNS);
	    if(typeArray != null) {
	    	for(int idx = 0; idx < typeArray.length; idx++) {
	    		final Element typeElement = new Element("Type", xrdNS);
	    		typeElement.addContent(typeArray[idx]);
	    		serviceElement.addContent(typeElement);
	    	}
	    }
	    
	    final Element uriElement = new Element("URI", xrdNS);
	    uriElement.addContent(uri);
	    serviceElement.addContent(uriElement);
	    if (StringUtils.isNotEmpty(delegate)) {
	    	final Element delegateElement = new Element("Delegate", openidNS);
	    	delegateElement.addContent(delegate);
	    	serviceElement.addContent(delegateElement);
	    }
	    
	    if (StringUtils.isNotEmpty(priority)) {
	    	serviceElement.setAttribute("priority", priority);
	    }
	    baseElement.addContent(serviceElement);
	}
	  
	public String toXmlString() {
		final Namespace xrdsNS = Namespace.getNamespace("xrds", "xri://$xrds");
		final Element rootElement = new Element("XRDS", xrdsNS);
		rootElement.addContent(baseElement);
	  
		final Document doc = new Document(rootElement);
		final StringWriter w = new StringWriter();
		final XMLOutputter o = new XMLOutputter(Format.getPrettyFormat());
		try {
			o.output(doc, w);
			w.close();
		} catch(IOException e) {
			LOG.warn("Caught an IOException while writing to StringWriter! This can't be happening!", e);
		}
		LOG.debug("XRD Response = " + w.toString());
		return w.toString();
	}
}
