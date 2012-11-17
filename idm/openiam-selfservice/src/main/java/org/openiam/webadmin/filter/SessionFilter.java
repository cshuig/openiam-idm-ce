package org.openiam.webadmin.filter;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.openiam.idm.srvc.menu.service.NavigatorDataService;
import org.openiam.base.ws.BooleanResponse;
import org.openiam.idm.srvc.auth.service.AuthenticationService;
import org.openiam.idm.srvc.menu.ws.NavigatorDataWebService;
import org.openiam.selfsrvc.AppConfiguration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * <p>
 * <code>SessionFilter</code> <font face="arial"> is a Filter that aids with session management.  
 * If it has expired, then it gracefully displays a session expiration message.
 *
 * </font>
 * </p>
 */

public class SessionFilter implements javax.servlet.Filter {

	private FilterConfig filterConfig = null;
	private String expirePage = null;
	private String excludePath = null;
    private boolean checkStoredToken = false;
	private static final Log log = LogFactory.getLog(SessionFilter.class);

	private NavigatorDataWebService navigationDataService;
    protected AuthenticationService authService = null;
	 
	//private org.openiam.idm.srvc.menu.service.NavigatorDataService navigationDataService;
	private AppConfiguration appConfiguration;
	


	public void init(FilterConfig filterConfig) throws ServletException {

		this.filterConfig = filterConfig;

		// the expire page is the url of the page to display if the session has expired.
		this.expirePage = filterConfig.getInitParameter("expirePage");
		excludePath = filterConfig.getInitParameter("excludePath");

        // determine if we should check if the user logged out or not.
        // this is expensive and should only be used if its possible for a user to logout elsewhere
        String checkDBToken =filterConfig.getInitParameter("checkDBToken");
        if ("Y".equalsIgnoreCase(checkDBToken)) {
            checkStoredToken = true;
        }else {
            checkStoredToken = false;
        }




	}

	public void destroy() {
		this.filterConfig = null;
	}

	public void doFilter(
		ServletRequest servletRequest,
		ServletResponse servletResponse,
		FilterChain chain)
		throws IOException, ServletException {
		
        System.out.println("SessionFilter:doFilter");

		ServletContext context = getFilterConfig().getServletContext();

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession();
		boolean loginPage = false;
		

		
		String url = request.getRequestURI();

		
		if ( url == null || url.equals("/") || url.endsWith("index.do") || url.endsWith("login.selfserve")   ) {
			log.debug("login page=true");
			loginPage = true; 
		}
		
		if (!loginPage && isCode(url) && !isExcludePath(url, context, session) ) 		{
		/* There is no User attribute so redirect to login page */
            String userId =  (String)session.getAttribute("userId");

			if(userId == null )	{

				response.sendRedirect(request.getContextPath() + expirePage);
				return;
			}
            // userId is not null
            if (checkStoredToken) {

                WebApplicationContext webContext = WebApplicationContextUtils.getWebApplicationContext(context);
                authService =  (AuthenticationService)webContext.getBean("authServiceClient");

                BooleanResponse resp = authService.isUserLoggedin(userId);
                // if not logged in then show the login page
                if (resp == null || !resp.getValue().booleanValue()) {
                    session.invalidate();
                    response.sendRedirect(request.getContextPath() + expirePage);
                    return;
                }


            }


		}
		chain.doFilter(servletRequest, servletResponse);

		

		


	}

	public javax.servlet.FilterConfig getFilterConfig() {
		return filterConfig;
	}

	// in older version instead of init() setFilterConfig is being called
	public void setFilterConfig(javax.servlet.FilterConfig f) {
		this.filterConfig = f;
		try {
			this.init(this.filterConfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isCode(String url) {

		if (url.contains(".jsp") || url.contains(".do") || url.contains(".report") || url.contains(".selfserve")) {
			return true;
		}
		return false;
	}

	public boolean isExcludePath(String url, ServletContext context, HttpSession session) {
		
		if (url == null) {
			return false;
		}

		if (url.contains(excludePath) ) {
			// may be calling a public url - make sure that static data is loaded.
			loadStaticData(session,context);
			return true;
		}
		return false;
	}
	private void loadStaticData(HttpSession session, ServletContext servletContext)  {

		// check to see if this is still in session
		if (session.getAttribute("publicRightMenuGroup1") == null) {
			log.debug("Reloading menus into session.");
			WebApplicationContext webContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		// load public menus
			navigationDataService = (NavigatorDataWebService)webContext.getBean("navServiceClient");
			appConfiguration  =(AppConfiguration)webContext.getBean("appConfiguration");
			session.setAttribute("publicLeftMenuGroup",
					navigationDataService.menuGroup(appConfiguration.getPublicLeftMenuGroup(), appConfiguration.getDefaultLang()).getMenuList());
			session.setAttribute("publicRightMenuGroup1",
					navigationDataService.menuGroup(appConfiguration.getPublicRightMenuGroup1(), appConfiguration.getDefaultLang()).getMenuList());
			session.setAttribute("publicRightMenuGroup2",
					navigationDataService.menuGroup(appConfiguration.getPublicRightMenuGroup2(), appConfiguration.getDefaultLang()).getMenuList());
		}
	}
	
}