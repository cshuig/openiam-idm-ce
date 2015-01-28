package org.openiam.ui.idp.saml.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openiam.ui.idp.saml.decoder.OpenIAMHTTPPostDecoder;
import org.openiam.ui.idp.saml.decoder.OpenIAMHTTPRedirectDeflateDecoder;
import org.openiam.ui.idp.saml.model.SAMLResponseToken;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.BasicSAMLMessageContext;
import org.opensaml.saml2.binding.decoding.BaseSAML2MessageDecoder;
import org.opensaml.saml2.binding.decoding.HTTPPostDecoder;
import org.opensaml.saml2.binding.decoding.HTTPRedirectDeflateDecoder;
import org.opensaml.saml2.binding.encoding.BaseSAML2MessageEncoder;
import org.opensaml.saml2.binding.encoding.HTTPRedirectDeflateEncoder;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.LogoutResponse;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.ecp.Request;
import org.opensaml.ws.message.decoder.MessageDecodingException;
import org.opensaml.ws.transport.http.HttpServletRequestAdapter;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallerFactory;
import org.opensaml.xml.io.UnmarshallingException;
import org.opensaml.xml.parse.BasicParserPool;
import org.opensaml.xml.parse.XMLParserException;
import org.opensaml.xml.security.SecurityException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SAMLDigestUtils {
	
	private static Logger LOG = Logger.getLogger(SAMLDigestUtils.class);
	
	public static AuthnRequest getAuthNRequest(final HttpServletRequest request, final boolean skipcheckEndpointURI) throws MessageDecodingException, SecurityException {
		final BasicSAMLMessageContext<AuthnRequest, Response, SAMLObject> msgContext = new BasicSAMLMessageContext<AuthnRequest, Response, SAMLObject>();
		msgContext.setInboundMessageTransport(new HttpServletRequestAdapter(request));
		BaseSAML2MessageDecoder decoder = null;
		
		try {
			decoder = new OpenIAMHTTPPostDecoder(skipcheckEndpointURI);
			decoder.decode(msgContext);
		} catch(Throwable e) {
			LOG.error(String.format("Could not decode message using HTTPPostDecoder Deflater - using HTTPRedirectDeflateDecoder.  Printing Exception", e));
			decoder = new OpenIAMHTTPRedirectDeflateDecoder(skipcheckEndpointURI);
			decoder.decode(msgContext);
		}
		final AuthnRequest authnRequest = msgContext.getInboundSAMLMessage();
		return authnRequest;
	}
	
	public static Response getSAMLResponse(final HttpServletRequest request, final boolean skipcheckEndpointURI) {
		final BasicSAMLMessageContext<Response, Response, SAMLObject> msgContext = new BasicSAMLMessageContext<Response, Response, SAMLObject>();
		msgContext.setInboundMessageTransport(new HttpServletRequestAdapter(request));
		BaseSAML2MessageDecoder decoder = null;
		
		try {
			decoder = new OpenIAMHTTPPostDecoder(skipcheckEndpointURI);
			decoder.decode(msgContext);
		} catch(Throwable e) {
			LOG.error(String.format("Could not decode message using HTTPPostDecoder Deflater - using HTTPRedirectDeflateDecoder.  Printing Exception", e));
		}
		final Response response = msgContext.getInboundSAMLMessage();
		return response;
	}
	
	/*
	public static AuthnRequest getAuthNRequest(final String raw) throws IOException, XMLParserException, UnmarshallingException {
		final byte[] xml = decompress(raw);
		final BasicParserPool ppMgr = new BasicParserPool();
		ppMgr.setNamespaceAware(true);
		
		final InputStream in = new ByteArrayInputStream(xml);
		final Document inCommonMDDoc = ppMgr.parse(in);
		final Element metadataRoot = inCommonMDDoc.getDocumentElement();
		
		final UnmarshallerFactory unmarshallerFactory = Configuration.getUnmarshallerFactory();
		final Unmarshaller unmarshaller = unmarshallerFactory.getUnmarshaller(metadataRoot);
		
		final AuthnRequest authnRequest = (AuthnRequest)unmarshaller.unmarshall(metadataRoot);
		return authnRequest;
	}

	public static byte[] decompress(final String raw) throws IOException {
		byte[] uncompressed = null;
		
		final Base64 base64Decoder = new Base64();
		final byte[] xmlBytes = raw.getBytes("UTF-8");
		final byte[] base64DecodedByteArray = base64Decoder.decode(xmlBytes);
		try {
			uncompressed = inflate(base64DecodedByteArray, true); 
		} catch (ZipException e) {
			uncompressed = inflate(base64DecodedByteArray, false);
		}
		return uncompressed;
	}
	
	private static byte[] inflate(byte[] bytes, boolean nowrap)  throws IOException { 
		  Inflater decompressor = null; 
		  ByteArrayOutputStream out = null;
		  try {
			  decompressor = new Inflater(nowrap);
			  decompressor.setInput(bytes);
			  out = new ByteArrayOutputStream(bytes.length);
			  final byte[] buf = new byte[1024];
			  while (!decompressor.finished()) {
				  try {
					  final int count = decompressor.inflate(buf); // PROBLEM
					  out.write(buf, 0, count);
					  // added check to avoid loops
					  if (count == 0) {
						  return altInflate(bytes);
					  }
				  } catch (DataFormatException e) {
					  return altInflate(bytes);
				  } catch (Exception e) {
					  return altInflate(bytes);
				  } catch (Throwable e) {
					  return altInflate(bytes);
				  }
			  }
			  return out.toByteArray();
		  } finally {
			  if (decompressor != null) decompressor.end(); 
			  try {
				  if (out != null) out.close();
			  } catch (IOException ioe) {
			  } 
		  }  
	  } 
	
	protected static byte[] altInflate(byte[] bytes) throws IOException {
		  ByteArrayInputStream bais = null;
		  ByteArrayOutputStream baos = new ByteArrayOutputStream();
		  InflaterInputStream iis = null;
		  byte[] buf = new byte[1024];
		  try {
			  //	if DEFLATE fails, then attempt to unzip the byte array according to
			  //zlib (rfc 1950)      
			  bais = new ByteArrayInputStream(bytes);
			  iis = new InflaterInputStream(bais);
			  buf = new byte[1024];
			  int count = iis.read(buf); // PROBLEM
			  while (count != -1) {
		 		 baos.write(buf, 0, count);
		 		 count = iis.read(buf);
			  }
			  return baos.toByteArray();   
		  }  catch (IOException ex) {
			  throw ex;
		  } finally {
			  if (iis != null) try {
				  iis.close();
			  } catch (IOException ex2) {}
			  if (baos != null) {
				  try { baos.close();
				  } catch (IOException ex2) {}
			  }
		  }
	  } 
	  */
}
