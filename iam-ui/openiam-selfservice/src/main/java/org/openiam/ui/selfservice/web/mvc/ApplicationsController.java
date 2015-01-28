package org.openiam.ui.selfservice.web.mvc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openiam.am.srvc.dto.ContentProvider;
import org.openiam.am.srvc.searchbeans.ContentProviderSearchBean;
import org.openiam.am.srvc.ws.AuthProviderWebService;
import org.openiam.am.srvc.ws.ContentProviderWebService;
import org.openiam.authmanager.common.model.AuthorizationResource;
import org.openiam.authmanager.service.AuthorizationManagerWebService;
import org.openiam.authmanager.ws.request.UserToResourceAccessRequest;
import org.openiam.idm.searchbeans.ResourceSearchBean;
import org.openiam.idm.srvc.res.service.ResourceDataService;
import org.openiam.ui.rest.api.model.Application;
import org.openiam.ui.security.OpenIAMCookieProvider;
import org.openiam.ui.web.util.ApplicationsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class ApplicationsController extends AbstractSelfServiceController {
	
	@Autowired
	private OpenIAMCookieProvider cookieProvider;
	
	@Autowired
	private ApplicationsProvider applicationProvider;

	@RequestMapping("/myApplications")
	public String myApplications(final HttpServletRequest request) {
		final List<Application> applications = applicationProvider.getAppliationsForUser(getRequesterId(request), request);
		
		request.setAttribute("applications", applications);
		return "sso/userApps";
	}
}
