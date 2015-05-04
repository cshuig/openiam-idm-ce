package org.openiam.ui.idp.web.mvc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.openiam.ui.idp.saml.model.SAMLResponseToken;
import org.openiam.ui.idp.saml.service.SAMLService;
import org.openiam.ui.web.mvc.AbstractController;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallerFactory;
import org.opensaml.xml.util.Base64;
import org.opensaml.xml.util.XMLHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

@Controller
public class SAMLDebugController extends AbstractController {
	
	@Autowired
	private SAMLService samlProvider;

	@Autowired
	private SAMLService samlService;
	
	@RequestMapping(value="/debugSAMLRequest", method=RequestMethod.POST)
	public @ResponseBody SAMLResponseToken debugSAMLResponsePOST(final HttpServletRequest request) {
		return samlProvider.samlLogin(request);
	}
	
	@RequestMapping(value="/debugSAMLRequest", method=RequestMethod.GET)
	public String debugSAMLRequestGET() {
		return "debug/samlRequestDebug";
	}
	

	@RequestMapping(value="/debugSAMLResponse", method=RequestMethod.GET)
	public String debugSAMLResponseGET() {
		return "debug/samlResponseDebug";
	}
	
	@RequestMapping(value="/debugSAMLResponse", method=RequestMethod.POST)
	public @ResponseBody SAMLResponseToken debugSAMLRequestPOST(final HttpServletRequest request, final HttpServletResponse response) {
		return samlService.processSAMLResponse(request, response, true);
	}
}
