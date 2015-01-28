package org.openiam.ui.idp.saml.util;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

public class SAMLUtils {

	public static String urlEncode(final String url) {
		String encodedURL = null;
		try {
			encodedURL = URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			/* won't happen */
		}
		return encodedURL;
	}
	
	public static String toString(final Node node) throws TransformerFactoryConfigurationError, TransformerException {
		final StringWriter writer = new StringWriter();
		final Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.transform(new DOMSource(node), new StreamResult(writer));
		final String xml = writer.toString();
		return xml;
	}
}
