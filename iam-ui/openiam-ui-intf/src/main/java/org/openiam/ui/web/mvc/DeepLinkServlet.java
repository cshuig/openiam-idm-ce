package org.openiam.ui.web.mvc;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.openiam.authmanager.common.model.AuthorizationMenu;
import org.openiam.authmanager.service.AuthorizationManagerMenuWebService;
import org.openiam.authmanager.ws.request.MenuRequest;
import org.openiam.ui.security.OpenIAMCookieProvider;
import org.openiam.ui.security.OpenIAMPreAuthFilter;
import org.openiam.ui.util.SpringContextProvider;
import org.openiam.ui.web.filter.OpeniamFilter;
import org.openiam.ui.web.util.LandingPageRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

public class DeepLinkServlet extends HttpServlet {
	
	private static final Log log = LogFactory.getLog(DeepLinkServlet.class);

	@Autowired
	private LandingPageRequestHandler requestHandler;
	
	@Autowired
	@Qualifier("authorizationManagerMenuServiceClient")
	private AuthorizationManagerMenuWebService authManagerMenuService;
	
	@Autowired
	@Qualifier("jacksonMapper")
	private ObjectMapper jacksonMapper;
	
	@Autowired
	private OpenIAMCookieProvider cookieProvider;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringContextProvider.autowire(this);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		requestHandler.handleRequest(request, response);
		String menuName = request.getRequestURI().substring(request.getContextPath().length()).substring("/menu".length());
		if(StringUtils.isNotBlank(menuName)) {
			menuName = menuName.substring(1);
			menuName = StringUtils.trimToNull(menuName);
		}
		
		final String queryString = StringUtils.trimToNull(request.getQueryString());
		
		final String userId = cookieProvider.getUserId(request);
		final MenuRequest menuRequest = new MenuRequest();
		menuRequest.setMenuName(menuName);
		menuRequest.setUserId(userId);
		final AuthorizationMenu menu = authManagerMenuService.getMenuTreeForUserId(menuRequest, OpeniamFilter.getCurrentLangauge(request));
		if(menu != null) {
			menu.setUrlParams(queryString);
		}
		
		String menuTreeString = null;
		try {
			menuTreeString = (menu != null) ? jacksonMapper.writeValueAsString(menu) : null;
		} catch (Throwable e) {
			log.error("Can't serialize menu tree", e);
		}
		
		request.setAttribute("initialMenu", menuTreeString);
		request.getRequestDispatcher("/WEB-INF/jsp/landingPage.jsp").forward(request, response);
	}
}
