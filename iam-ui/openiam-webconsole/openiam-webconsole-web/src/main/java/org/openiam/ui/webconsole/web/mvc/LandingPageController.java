package org.openiam.ui.webconsole.web.mvc;

import java.io.IOException;
import java.util.Arrays;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.openiam.authmanager.common.model.AuthorizationMenu;
import org.openiam.authmanager.service.AuthorizationManagerMenuWebService;
import org.openiam.authmanager.ws.request.MenuRequest;
import org.openiam.ui.web.util.LandingPageRequestHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LandingPageController extends AbstractWebconsoleController{
	
	private static final Log log = LogFactory.getLog(LandingPageController.class);
	
	@Autowired
	private LandingPageRequestHandler requestHandler;
	
	@RequestMapping("/landingPage")
	public String landingPage(final HttpServletRequest request,
							  final HttpServletResponse response) throws Exception {
		requestHandler.handleRequest(request, response);
		return "landingPage";
	}
}
