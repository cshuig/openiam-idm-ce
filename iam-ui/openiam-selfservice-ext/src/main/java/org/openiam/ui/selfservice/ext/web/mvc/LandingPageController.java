package org.openiam.ui.selfservice.ext.web.mvc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openiam.ui.web.util.LandingPageRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LandingPageController extends AbstractSelfServiceExtController{
	
	private static final Log log = LogFactory.getLog(LandingPageController.class);
	
	@Autowired
	private LandingPageRequestHandler requestHandler;
	
	@RequestMapping("/landingPage")
	public String landingPage(final HttpServletRequest request,
							  final HttpServletResponse response) throws Exception {
		requestHandler.handleRequest(request, response);
		return "/landingPage";
	}
}
