package org.openiam.ui.idp.saml.util;

import java.io.UnsupportedEncodingException;

import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.commons.codec.binary.Base64;
import org.opensaml.saml2.core.Response;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallerFactory;
import org.opensaml.xml.io.MarshallingException;
import org.w3c.dom.Element;

public class SAMLEncoder {

	public static String encodeResponse(final Response response) throws MarshallingException, TransformerFactoryConfigurationError, TransformerException, UnsupportedEncodingException {
		final MarshallerFactory marshallerFactory = Configuration.getMarshallerFactory();
		final Marshaller marshaller = marshallerFactory.getMarshaller(response);
		final Element responseElmt = marshaller.marshall(response);
		final String xml = SAMLUtils.toString(responseElmt);
		final String encodedXML = new Base64().encodeAsString(xml.getBytes("UTF-8"));
		//System.out.println(xml);
		return encodedXML;
	}
}
