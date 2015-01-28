package org.openiam.ui.selfservice.web.mvc;


import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.util.ArrayUtil;
import org.openiam.am.srvc.dto.AuthLevel;
import org.openiam.am.srvc.uriauth.dto.URIFederationResponse;
import org.openiam.am.srvc.ws.ContentProviderWebService;
import org.openiam.am.srvc.ws.URIFederationWebService;
import org.openiam.authmanager.service.AuthorizationManagerWebService;
import org.openiam.idm.searchbeans.GroupSearchBean;
import org.openiam.idm.searchbeans.OrganizationTypeSearchBean;
import org.openiam.idm.searchbeans.RoleSearchBean;
import org.openiam.idm.srvc.auth.ws.LoginDataWebService;
import org.openiam.idm.srvc.continfo.dto.Address;
import org.openiam.idm.srvc.continfo.dto.EmailAddress;
import org.openiam.idm.srvc.continfo.dto.Phone;
import org.openiam.idm.srvc.grp.dto.Group;
import org.openiam.idm.srvc.grp.ws.GroupDataWebService;
import org.openiam.idm.srvc.meta.dto.MetadataType;
import org.openiam.idm.srvc.meta.dto.PageTempate;
import org.openiam.idm.srvc.meta.dto.TemplateRequest;
import org.openiam.idm.srvc.meta.ws.MetadataElementTemplateWebService;
import org.openiam.idm.srvc.meta.ws.MetadataWebService;
import org.openiam.idm.srvc.mngsys.ws.ManagedSystemWebService;
import org.openiam.idm.srvc.org.dto.OrganizationType;
import org.openiam.idm.srvc.org.service.OrganizationTypeDataService;
import org.openiam.idm.srvc.role.dto.Role;
import org.openiam.idm.srvc.role.ws.RoleDataWebService;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.idm.srvc.user.dto.UserStatusEnum;
import org.openiam.idm.srvc.user.ws.UserDataWebService;
import org.openiam.script.ScriptIntegration;
import org.openiam.ui.rest.api.model.KeyNameBean;
import org.openiam.ui.selfservice.web.groovy.AuthenticatedContactInfoPreprocessor;
import org.openiam.ui.selfservice.web.groovy.ContactInfoPostprocessor;
import org.openiam.ui.selfservice.web.groovy.UnauthenticatedContactInfoPostprocessor;
import org.openiam.ui.selfservice.web.groovy.UnauthenticatedContactInfoPreprocessor;
import org.openiam.ui.selfservice.web.model.NewUserBean;
import org.openiam.ui.selfservice.web.mvc.provider.TemplateProvider;
import org.openiam.ui.util.OpenIAMConstants;
import org.openiam.ui.util.URIUtils;
import org.openiam.ui.web.filter.ProxyFilter;
import org.openiam.ui.web.util.DateFormatStr;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public abstract class AbstractProfileController extends AbstractSelfServiceController implements ApplicationContextAware {
	
	@Autowired
	@Resource(name="authorizationManagerServiceClient")
	protected AuthorizationManagerWebService authorizationManager;
	
    @Resource(name = "userServiceClient")
    protected UserDataWebService userDataWebService;
    
	@Resource(name="managedSysServiceClient")
	protected ManagedSystemWebService managedSysServiceClient;

    @Autowired
    @Qualifier("configurableGroovyScriptEngine")
    private ScriptIntegration scriptRunner;
	
	@Value("${openiam.ui.loginurl}")
	protected String loginURL;
	
    private ApplicationContext applicationContext;
    
    @Resource(name="roleServiceClient")
    private RoleDataWebService roleDataService;
    
	@Resource(name="groupServiceClient")
	private GroupDataWebService groupServiceClient;
    
    @Autowired
    protected TemplateProvider templateProvider;
    
    @Value("${org.openiam.workflow.new.user.redirect.url}")
    protected String redirectUrlAfterUserWorkflowInitialization;
    
    protected String processProfileScreenGetRequest(final HttpServletRequest request, final User user, final boolean newUser, final String targetUserId) throws Exception {
    	final List<List<OrganizationType>> orgTypes = getOrgTypeList();
    	
    	/*
    	 * IDMAPPS-1062
		 * Self Registration - we need an enhancement where we can pass an orgid or 
		 * orgname on the URL and the drop down that shows the org list will only 
		 * show the org that is passed in.
    	 */
    	String[] orgArray = request.getParameterValues("orgList");
    	orgArray = (orgArray != null) ? orgArray : new String[0];
    	final List<String> orgList = new LinkedList<String>();
    	for(final String org : orgArray) {
    		orgList.add(org);
    	}
    	request.setAttribute("orgList", jacksonMapper.writeValueAsString(orgList));
    	
    	//final String userId = cookieProvider.getUserId(request);
		request.setAttribute("target", request.getRequestURI());
		request.setAttribute("orgHierarchy", (orgTypes != null) ? jacksonMapper.writeValueAsString(orgTypes) : null);
		request.setAttribute("user", user);
		request.setAttribute("userStatuses", UserStatusEnum.values());
		request.setAttribute("objClassList", getUserTypes());
		
		PageTempate template = templateProvider.getPageTemplate(targetUserId, request);
		request.setAttribute("template", (template != null) ? jacksonMapper.writeValueAsString(template) : null);
		
		request.setAttribute("currentUserId", getRequesterId(request));
		request.setAttribute("pageTemplate", (template != null) ? template : new PageTempate());
		request.setAttribute("employeeTypeList", getUserTypeList());
		request.setAttribute("jobList", getJobCodeList());
		request.setAttribute("newUser", newUser);
		
		final List<KeyNameBean> emailTypes = getEmailTypes();
		final List<KeyNameBean> phoneTypes = getPhoneTypes();
		final List<KeyNameBean> addressTypes = getAddressTypes();
		final Map<String, KeyNameBean> emailTypeMap = new HashMap<String, KeyNameBean>();
		if(CollectionUtils.isNotEmpty(emailTypes)) {
			for(final KeyNameBean bean : emailTypes) {
				emailTypeMap.put(bean.getId(), bean);
			}
		}
		final Map<String, KeyNameBean> phoneTypeMap = new HashMap<String, KeyNameBean>();
		if(CollectionUtils.isNotEmpty(phoneTypes)) {
			for(final KeyNameBean bean : phoneTypes) {
				phoneTypeMap.put(bean.getId(), bean);
			}
		}
		
		final Map<String, KeyNameBean> addressTypeMap = new HashMap<String, KeyNameBean>();
		if(CollectionUtils.isNotEmpty(addressTypes)) {
			for(final KeyNameBean bean : addressTypes) {
				addressTypeMap.put(bean.getId(), bean);
			}
		}
		
		request.setAttribute("emailTypes", (emailTypes != null) ? jacksonMapper.writeValueAsString(emailTypes) : null);
		request.setAttribute("phoneTypes", (phoneTypes != null) ? jacksonMapper.writeValueAsString(phoneTypes) : null);
		request.setAttribute("addressTypes", (addressTypes != null) ? jacksonMapper.writeValueAsString(addressTypes) : null);
		request.setAttribute("emailTypeMap", jacksonMapper.writeValueAsString(emailTypeMap));
		request.setAttribute("phoneTypeMap", jacksonMapper.writeValueAsString(phoneTypeMap));
		request.setAttribute("addressTypeMap", jacksonMapper.writeValueAsString(addressTypeMap));
        request.setAttribute("dateFormatDP", DateFormatStr.getDpDate());
		
		return "user/profileScreen";
    }
    
    protected AuthenticatedContactInfoPreprocessor getAuthenticatedContactInfoPreProcessor(final User user, final HttpServletRequest request, final String script) {
    	AuthenticatedContactInfoPreprocessor preProcessor = null;
    	try {
    		preProcessor = (AuthenticatedContactInfoPreprocessor) scriptRunner.instantiateClass(null, script);
    	} catch(Throwable e) {
            log.info(String.format("Can't inialize script %s.  This is not an error", script), e);
    		preProcessor = new AuthenticatedContactInfoPreprocessor();
    	}
    	
    	final Map<String, Object> bindingMap = new HashMap<String, Object>();
    	bindingMap.put("context", applicationContext);
    	bindingMap.put("user", user);
    	bindingMap.put("request", request);
    	preProcessor.init(bindingMap);
        return preProcessor;
    }


	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
}
