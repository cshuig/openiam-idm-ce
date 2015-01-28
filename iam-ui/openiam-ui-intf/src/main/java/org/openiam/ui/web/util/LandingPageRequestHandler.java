package org.openiam.ui.web.util;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.openiam.authmanager.common.model.AuthorizationMenu;
import org.openiam.authmanager.service.AuthorizationManagerMenuWebService;
import org.openiam.authmanager.ws.request.MenuRequest;
import org.openiam.idm.searchbeans.ResourceSearchBean;
import org.openiam.idm.srvc.res.service.ResourceDataService;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.idm.srvc.user.ws.UserDataWebService;
import org.openiam.ui.security.OpenIAMCookieProvider;
import org.openiam.ui.security.OpenIAMPreAuthFilter;
import org.openiam.ui.util.URIUtils;
import org.openiam.ui.web.filter.OpeniamFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

public abstract class LandingPageRequestHandler {
	
	private static final Log log = LogFactory.getLog(LandingPageRequestHandler.class);
	
	@Resource(name="authorizationManagerMenuServiceClient")
	private AuthorizationManagerMenuWebService authorizationManagerMenuServiceClient;
	
	@Autowired
	@Qualifier("jacksonMapper")
	private ObjectMapper jacksonMapper;
	
	@Value("${org.openiam.ui.landingpage.menu.name}")
	private String menuName;
	
	@Autowired
	private OpenIAMCookieProvider cookieProvider;
	
	public abstract void doCustom(final HttpServletRequest request, final HttpServletResponse response);

	public void handleRequest(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		final String userId = cookieProvider.getUserId(request);
		final String userName = cookieProvider.getUserName(request);
		final MenuRequest menuRequest = new MenuRequest();
		menuRequest.setUserId(userId);
		menuRequest.setMenuName(menuName);
		final AuthorizationMenu root = authorizationManagerMenuServiceClient.getMenuTreeForUserId(menuRequest, OpeniamFilter.getCurrentLangauge(request));
		String menuAsString = null;
		try {
			menuAsString = (root != null) ? jacksonMapper.writeValueAsString(root) : null;
		} catch(Throwable e) {
			log.error("Error serializing menu into JSON", e);
		}
		
		/* can't test for a 'starts with /', because we create an absolute URL from the postbackURL 
		 * relative param.  This happens in our Spring Security code
		 */
		String frameURL = request.getParameter("frameURL");
		frameURL = (StringUtils.isNotBlank(frameURL))/* && frameURL.startsWith("/"))*/ ? frameURL : null;
		if(!URIUtils.isValidPostbackURL(frameURL)) {
			log.warn(String.format("Postback URL '%s' not valid - doesn't start with a '/' - XSS detected.  Returning 401", frameURL));
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		request.setAttribute("frameURL", frameURL);
		request.setAttribute("userId", userId);
		request.setAttribute("userName", userName);
		request.setAttribute("menuTree", menuAsString);
		request.setAttribute("contextPath", request.getContextPath());
		doCustom(request, response);
	}
}
