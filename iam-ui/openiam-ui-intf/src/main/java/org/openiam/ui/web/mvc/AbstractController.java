package org.openiam.ui.web.mvc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.openiam.am.srvc.ws.ContentProviderWebService;
import org.openiam.authmanager.common.model.AuthorizationMenu;
import org.openiam.authmanager.service.AuthorizationManagerMenuWebService;
import org.openiam.authmanager.service.AuthorizationManagerWebService;
import org.openiam.authmanager.ws.request.MenuRequest;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseCode;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.connector.type.constant.StatusCodeType;
import org.openiam.idm.searchbeans.*;
import org.openiam.idm.srvc.audit.constant.AuditAction;
import org.openiam.idm.srvc.audit.constant.AuditTarget;
import org.openiam.idm.srvc.audit.ws.IdmAuditLogWebDataService;
import org.openiam.idm.srvc.auth.service.AuthenticationService;
import org.openiam.idm.srvc.auth.ws.IdentityWebService;
import org.openiam.idm.srvc.auth.ws.LoginDataWebService;
import org.openiam.idm.srvc.grp.ws.GroupDataWebService;
import org.openiam.idm.srvc.lang.dto.Language;
import org.openiam.idm.srvc.lang.service.LanguageWebService;
import org.openiam.idm.srvc.meta.domain.MetadataTypeGrouping;
import org.openiam.idm.srvc.meta.dto.MetadataElement;
import org.openiam.idm.srvc.meta.dto.MetadataType;
import org.openiam.idm.srvc.meta.ws.MetadataWebService;
import org.openiam.idm.srvc.mngsys.dto.ManagedSysDto;
import org.openiam.idm.srvc.mngsys.ws.ManagedSystemWebService;
import org.openiam.idm.srvc.org.dto.Organization;
import org.openiam.idm.srvc.org.dto.OrganizationType;
import org.openiam.idm.srvc.org.service.OrganizationDataService;
import org.openiam.idm.srvc.org.service.OrganizationTypeDataService;
import org.openiam.idm.srvc.pswd.service.ChallengeResponseWebService;
import org.openiam.idm.srvc.recon.ws.ReconciliationWebService;
import org.openiam.idm.srvc.res.dto.ResourceRisk;
import org.openiam.idm.srvc.res.dto.ResourceType;
import org.openiam.idm.srvc.res.service.ResourceDataService;
import org.openiam.idm.srvc.role.ws.RoleDataWebService;
import org.openiam.idm.srvc.ui.theme.UIThemeWebService;
import org.openiam.idm.srvc.ui.theme.dto.UITheme;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.idm.srvc.user.ws.UserDataWebService;
import org.openiam.provision.dto.ProvisionGroup;
import org.openiam.provision.dto.ProvisionUser;
import org.openiam.provision.service.ObjectProvisionService;
import org.openiam.provision.service.ProvisionService;
import org.openiam.script.ScriptIntegration;
import org.openiam.ui.audit.AuditLogProvider;
import org.openiam.ui.dozer.DozerBeanMapper;
import org.openiam.ui.rest.api.model.KeyNameBean;
import org.openiam.ui.rest.api.model.LanguageBean;
import org.openiam.ui.rest.api.model.MetadataElementBean;
import org.openiam.ui.rest.api.model.OrganizationBean;
import org.openiam.ui.security.OpenIAMCookieProvider;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.web.filter.OpeniamFilter;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.util.OpeniamCookieLocaleResolver;
import org.openiam.ui.web.util.OrganizationTypeStructureParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public abstract class AbstractController {
    protected final Logger log = Logger.getLogger(this.getClass());


	@Resource(name="authServiceClient")
	protected AuthenticationService authServiceClient;

	@Resource(name="challengeResponseServiceClient")
	protected ChallengeResponseWebService challengeResponseService;

	@Resource(name="auditServiceClient")
	protected IdmAuditLogWebDataService auditLogService;
    
    @Value("${org.openiam.defaultManagedSysId}")
    protected String defaultManagedSysId;
    
    @Resource(name="contentProviderServiceClient")
    protected ContentProviderWebService contentProviderServiceClient;
    
    @Autowired
    @Resource(name="loginServiceClient")
    protected LoginDataWebService loginServiceClient;

    @Resource(name="languageServiceClient")
    protected LanguageWebService languageWebService;

    @Autowired
    @Qualifier("localeResolver")
    protected OpeniamCookieLocaleResolver localeResolver;

    @Resource(name = "metadataServiceClient")
    protected MetadataWebService metadataWebService;

    @Resource(name = "authorizationManagerMenuServiceClient")
    protected AuthorizationManagerMenuWebService authManagerMenuService;
    
	@Resource(name="authorizationManagerServiceClient")
	protected AuthorizationManagerWebService authorizationManager;

    @Value("${org.openiam.provision.service.flag}")
    protected Boolean provisionServiceFlag;

    @Autowired
    protected MessageSource messageSource;

    @Resource(name = "provisionServiceClient")
    protected ProvisionService provisionService;

    @Autowired
    @Qualifier("configurableGroovyScriptEngine")
    protected ScriptIntegration scriptRunner;

    @Autowired
    protected OpenIAMCookieProvider cookieProvider;

    @Autowired
    @Qualifier("jacksonMapper")
    protected ObjectMapper jacksonMapper;

    @Autowired
    private OrganizationTypeStructureParser orgTypeParser;

    @Autowired
    @Qualifier("dozerBeanMapper")
    protected DozerBeanMapper mapper;

    @Resource(name="auditServiceClient")
    protected IdmAuditLogWebDataService auditWS;

    @Resource(name = "groupServiceClient")
    protected GroupDataWebService groupServiceClient;

    @Resource(name = "roleServiceClient")
    protected RoleDataWebService roleServiceClient;

    @Resource(name = "organizationServiceClient")
    protected OrganizationDataService organizationDataService;

    @Resource(name = "metadataServiceClient")
    protected MetadataWebService metadataServiceClient;

    @Resource(name = "resourceServiceClient")
    protected ResourceDataService resourceDataService;

    @Resource(name = "managedSysServiceClient")
    protected ManagedSystemWebService managedSysServiceClient;

    @Resource(name = "userServiceClient")
    protected UserDataWebService userDataWebService;

    @Resource(name = "organizationTypeClient")
    protected OrganizationTypeDataService organizationTypeClient;

    @Resource(name = "uiThemeClient")
    protected UIThemeWebService uiThemeClient;

    protected List<KeyNameBean> actionsKeyNames;

    protected List<KeyNameBean> auditTargetTypesKeyNames;

    protected List<KeyNameBean> statusKeyNames;

    @Resource(name = "reconciliationServiceClient")
    protected ReconciliationWebService reconciliationWebService;

    @Resource(name = "identityServiceClient")
    protected IdentityWebService identityWebService;

	protected static final MetadataTypeGrouping UI_WIDGET = MetadataTypeGrouping.UI_WIDGET;
    protected static final MetadataTypeGrouping EMAIL_GROUPING = MetadataTypeGrouping.EMAIL;
    protected static final MetadataTypeGrouping ADDRESS_GROUPING = MetadataTypeGrouping.ADDRESS;
    protected static final MetadataTypeGrouping PHONE_GROUPING = MetadataTypeGrouping.PHONE;
    
    protected HttpServletRequest getCurrentHttpServletRequest() {
    	final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    	return request;
    }

    protected Language getCurrentLanguage() {
    	return OpeniamFilter.getCurrentLangauge(getCurrentHttpServletRequest());
    }

    protected String getRequesterId(final HttpServletRequest httpRequest) {
        return cookieProvider.getUserId(httpRequest);
    }

    protected String getRequesterIdFromCookie(
            final HttpServletRequest httpRequest) {
        return cookieProvider.getUserIdFromCookie(httpRequest);
    }

    protected String getRequesterPrincipal(final HttpServletRequest httpRequest) {
        return cookieProvider.getPrincipal(httpRequest);
    }
    
    protected List<KeyNameBean> getMetadataTypesByGroupingAsKeyNameBeans(final MetadataTypeGrouping grouping) {
        List<KeyNameBean> res = mapper.mapToList(getMetadataTypesByGrouping(grouping), KeyNameBean.class);
        Collections.sort(res);
         return res;
    }

    protected MetadataType getMetadataTypeById(final String id) {
    	final MetadataTypeSearchBean searchBean = new MetadataTypeSearchBean();
		searchBean.setDeepCopy(false);
		searchBean.setKey(id);
		final List<MetadataType> metadataTypes = metadataServiceClient.findTypeBeans(searchBean, 0, 1, getCurrentLanguage());
		return (CollectionUtils.isNotEmpty(metadataTypes)) ? metadataTypes.get(0) : null;
    }
    
    protected List<LanguageBean> getUsedLanguages() {
    	final List<Language> resultsList = languageWebService.getUsedLanguages(getCurrentLanguage());
    	return mapper.mapToList(resultsList, LanguageBean.class);
    }
    
    protected MetadataElement getMetadataElement(final String id) {
    	final MetadataElementSearchBean searchBean = new MetadataElementSearchBean();
        searchBean.setKey(id);
        searchBean.setDeepCopy(true);
        final List<MetadataElement> dtoList = metadataServiceClient.findElementBeans(searchBean, 0, 1, getCurrentLanguage());
        return (CollectionUtils.isNotEmpty(dtoList)) ? dtoList.get(0) : null;
    }
    
    protected List<MetadataElementBean> getMetdataElementsByType(final String typeId) {
    	final MetadataElementSearchBean searchBean = new MetadataElementSearchBean();
    	searchBean.setDeepCopy(true);
    	searchBean.addTypeId(typeId);
    	final List<MetadataElement> dtoList = metadataServiceClient.findElementBeans(searchBean, 0, Integer.MAX_VALUE, getCurrentLanguage());
    	return mapper.mapToList(dtoList, MetadataElementBean.class);
    }

    protected List<KeyNameBean> getMetaTypeListAsKeyNameBeans() {
		final MetadataTypeSearchBean searchBean = new MetadataTypeSearchBean();
		searchBean.setDeepCopy(false);
		final List<MetadataType> metaTypes = metadataServiceClient.findTypeBeans(searchBean, 0, Integer.MAX_VALUE, getCurrentLanguage());
		return mapper.mapToList(metaTypes, KeyNameBean.class);
    }
    
    protected List<MetadataType> getMetadataTypesByGrouping(final MetadataTypeGrouping grouping) {
    	final MetadataTypeSearchBean searchBean = new MetadataTypeSearchBean();
    	searchBean.setGrouping(grouping);
    	searchBean.setActive(true);
        final List<MetadataType> types = metadataWebService.findTypeBeans(searchBean, 0, Integer.MAX_VALUE, getCurrentLanguage());
        return types;
    }

    protected List<KeyNameBean> getFieldTypeListAsKeyNameBeans() {
    	final List<MetadataType> results = getFieldTypeList();
    	return mapper.mapToList(results, KeyNameBean.class);
    }

    protected List<MetadataType> getFieldTypeList(){
        final MetadataTypeSearchBean typeSearchBean = new MetadataTypeSearchBean();
        typeSearchBean.setGrouping(UI_WIDGET);
        typeSearchBean.setActive(true);
        return metadataWebService.findTypeBeans(typeSearchBean, 0, Integer.MAX_VALUE, getCurrentLanguage());
    }
    
    protected HashMap<String, MetadataType> getFieldTypeMap(){
        return  getFieldTypeMap(getFieldTypeList());
    }

    protected HashMap<String, MetadataType> getFieldTypeMap(List<MetadataType> typeList){
        HashMap<String, MetadataType> typeMap =new HashMap<String, MetadataType>();
        if(typeList!=null && !typeList.isEmpty()) {
            for (MetadataType e: typeList){
                typeMap.put(e.getId(),  e);
            }
        }
        return  typeMap;
    }

    protected Response provisionUsers(HttpServletRequest request,
            String resourceId, UserSearchBean userSearchBean, boolean isAddition) {
        Response wsResponse = new Response(ResponseStatus.SUCCESS);

        if (provisionServiceFlag) {
            userSearchBean.setDeepCopy(true);
            final List<User> users = userDataWebService.findBeans(userSearchBean, -1, -1);
            if(CollectionUtils.isNotEmpty(users)) {
            	for (User user : users) {
            		final org.openiam.idm.srvc.res.dto.Resource resource = resourceDataService.getResource(resourceId, getCurrentLanguage());
            		if (user != null && resource != null) {
            			final ProvisionUser pUser = new ProvisionUser(user);
                        pUser.setRequestorUserId(getRequesterId(request));
            			if (isAddition) {
            				pUser.addResource(resource);
            			} else {
            				pUser.markResourceAsDeleted(resource.getId());
            			}
            			wsResponse = provisionService.modifyUser(pUser);
            		} else {
            			wsResponse.setStatus(ResponseStatus.FAILURE);
            			wsResponse.setErrorCode(ResponseCode.UNAUTHORIZED);
            			break;
            		}
            	}
            }
        }

        return wsResponse;
    }

    protected List<KeyNameBean> getUIThemesAsKeyNameBeans() {
        final UIThemeSearchBean searchBean = new UIThemeSearchBean();
        searchBean.setDeepCopy(false);
        final List<UITheme> uiThemeList = uiThemeClient.findBeans(searchBean,
                0, Integer.MAX_VALUE);
        return mapper.mapToList(uiThemeList, KeyNameBean.class);
    }

    protected List<KeyNameBean> getOrgTypeListAsKeyNameBeans() {
        final OrganizationTypeSearchBean organizationTypeSearchBean = new OrganizationTypeSearchBean();
        organizationTypeSearchBean.setDeepCopy(false);
        final List<OrganizationType> organizationTypes = organizationTypeClient.findBeans(organizationTypeSearchBean, 0, Integer.MAX_VALUE, getCurrentLanguage());
        return mapper.mapToList(organizationTypes, KeyNameBean.class);
    }

    protected List<List<OrganizationType>> getOrgTypeList() {
        return orgTypeParser.getOrgTypeList(getCurrentHttpServletRequest());
    }

    protected AuthorizationMenu getMenuTree(final HttpServletRequest request,
                               final String menuName) {
        final String userId = (String) SecurityContextHolder.getContext()
                .getAuthentication().getCredentials();
        final MenuRequest menuRequest = new MenuRequest();
        menuRequest.setUserId(userId);
        menuRequest.setMenuName(menuName);
        final AuthorizationMenu root = authManagerMenuService.getMenuTreeForUserId(menuRequest, getCurrentLanguage());
        return root;
    }

    protected void setMenuTree(final HttpServletRequest request,
            final String menuName) {
        final String userId = (String) SecurityContextHolder.getContext()
                .getAuthentication().getCredentials();
        final MenuRequest menuRequest = new MenuRequest();
        menuRequest.setUserId(userId);
        menuRequest.setMenuName(menuName);
        final AuthorizationMenu root = authManagerMenuService.getMenuTreeForUserId(menuRequest, getCurrentLanguage());
        String menuTreeString = null;
        try {
            menuTreeString = (root != null) ? jacksonMapper.writeValueAsString(root) : null;
        } catch (Throwable e) {
            log.error("Can't serialize menu tree", e);
        }
        request.setAttribute("menuTree", menuTreeString);
    }

    protected String getInitialMenuAsJson(final HttpServletRequest request, final String menuName) {
        final String queryString = StringUtils.trimToNull(request.getQueryString());

        final String userId = cookieProvider.getUserId(request);
        final MenuRequest menuRequest = new MenuRequest();
        menuRequest.setMenuName(menuName);
        menuRequest.setUserId(userId);
        final AuthorizationMenu menu = authManagerMenuService.getMenuTreeForUserId(menuRequest,
                OpeniamFilter.getCurrentLangauge(request));
        if(menu != null) {
            menu.setUrlParams(queryString);
        }

        String menuTreeString = null;
        try {
            menuTreeString = (menu != null) ? jacksonMapper.writeValueAsString(menu) : null;
        } catch (Throwable e) {
            log.error("Can't serialize menu tree", e);
        }
        return menuTreeString;
    }

//    @ModelAttribute("titleOrganizatioName")
//    public String getTitleOrganizatioName() {
//        return titleOrganizatioName;
//    }
//
//    @ModelAttribute("footerCopyright")
//    public String getFooterCopyright() {
//        return footerCopyright;
//    }
//
//    @ModelAttribute("supportedLanguageList")
//    public List<Language> getUsedLanguages(){
//        List<Language> languageList = languageWebService.getUsedLanguages();
//        return languageList;
//    }
//    @ModelAttribute("selectedLanguage")
//    public String getSelectedLanguage(HttpServletRequest request){
//        return localeResolver.resolveLocale(request).getLanguage();
//    }
//    @ModelAttribute("selectedCountry")
//    public String getSelectedCountry(HttpServletRequest request){
//        return localeResolver.resolveLocale(request).getCountry();
//    }

//    @ModelAttribute
//    public void populateModel(HttpServletRequest request){
//        request.setAttribute("titleOrganizatioName", titleOrganizatioName);
//        request.setAttribute("footerCopyright", footerCopyright);
//        request.setAttribute("supportedLanguageList", languageWebService.getUsedLanguages());
//        request.setAttribute("selectedLanguage", localeResolver.resolveLocale(request).toString());
//    }

    protected String getLocalizedMessage(final String key, final Object[] args) {
    	return messageSource.getMessage(key, args, localeResolver.resolveLocale(getCurrentHttpServletRequest()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public @ResponseBody
    BasicAjaxResponse handleAjaxBeanValidationError(
            MethodArgumentNotValidException e, HttpServletRequest request,
            HttpServletResponse response) {

        BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        ajaxResponse.setStatus(500);
        if (e.getBindingResult().hasErrors()) {
            for (FieldError error : e.getBindingResult().getFieldErrors()) {
                ErrorToken errorToken = new ErrorToken(
                        Errors.getByMessage(error.getDefaultMessage()),
                        error.getArguments());
                errorToken.setMessage(messageSource.getMessage(errorToken
                        .getError().getMessageName(), errorToken.getParams(),
                        localeResolver.resolveLocale(request)));
                ajaxResponse.addError(errorToken);
                log.info("Validation error: " + error.toString());
            }
        } else {
            ajaxResponse.addError(new ErrorToken(Errors.INTERNAL_ERROR));
        }

        return ajaxResponse;
    }
    
    protected List<KeyNameBean> getAuthLevelsAsKeyNameBeans() {
    	return mapper.mapToList(contentProviderServiceClient.getAuthLevelList(), KeyNameBean.class);
    }

    protected List<KeyNameBean> getCompleteMetadataElementList() {
        final ArrayList<KeyNameBean> newCodeList = new ArrayList<>();
        final MetadataElementSearchBean searchBean = new MetadataElementSearchBean();
        searchBean.setDeepCopy(false);
        searchBean.addGrouping(MetadataTypeGrouping.USER_OBJECT_TYPE);
        
        final List<MetadataElement> elementAry = metadataServiceClient.findElementBeans(searchBean, 0, Integer.MAX_VALUE, getCurrentLanguage());
        if (CollectionUtils.isNotEmpty(elementAry)) {
            for (final MetadataElement elm : elementAry) {
                newCodeList.add(new KeyNameBean(elm.getId(),  String.format("%s->%s",elm.getMetadataTypeName(), elm.getDisplayName())));
            }
        }
        Collections.sort(newCodeList);
        return newCodeList;
    }

    protected List<KeyNameBean> getMetadataTypes(final String typeName) {
    	final MetadataTypeSearchBean searchBean = new MetadataTypeSearchBean();
    	searchBean.addCategoryId(typeName);
    	searchBean.setDeepCopy(true);
    	
        final List<MetadataType> metaDataTypes = metadataServiceClient.findTypeBeans(searchBean, 0, Integer.MAX_VALUE, getCurrentLanguage());
        return mapper.mapToList(metaDataTypes, KeyNameBean.class);
    }

    /*
    protected List<KeyNameBean> getGroupTypes() {
        return getMetadataTypes("GROUP_TYPE");
    }

    protected List<KeyNameBean> getOrganizationMetadataTypes() {
        return getMetadataTypes("ORG_TYPE");
    }
    */

    protected List<KeyNameBean> getUserStatusList() {
        return getMetadataTypesByGroupingAsKeyNameBeans(MetadataTypeGrouping.USER);
    }

    protected List<KeyNameBean> getUserSecondaryStatusList() {
        return getMetadataTypesByGroupingAsKeyNameBeans(MetadataTypeGrouping.USER_2ND_STATUS);
    }
    
    protected List<KeyNameBean> getUserTypes() {
    	//return getMetadataTypes("USER_TYPE");
    	return getMetadataTypesByGroupingAsKeyNameBeans(MetadataTypeGrouping.USER_OBJECT_TYPE);
    }

    protected List<KeyNameBean> getJobCodeList(){
        return getMetadataTypesByGroupingAsKeyNameBeans(MetadataTypeGrouping.JOB_CODE);
    }
    
    protected List<KeyNameBean> getEmailTypes() {
    	return getMetadataTypesByGroupingAsKeyNameBeans(EMAIL_GROUPING);
    }
    
    protected List<KeyNameBean> getPhoneTypes() {
    	return getMetadataTypesByGroupingAsKeyNameBeans(PHONE_GROUPING);
    }
    
    protected List<KeyNameBean> getAddressTypes() {
    	return getMetadataTypesByGroupingAsKeyNameBeans(ADDRESS_GROUPING);
    }

    protected List<KeyNameBean> getUserTypeList() {
        return getMetadataTypesByGroupingAsKeyNameBeans(MetadataTypeGrouping.USER_TYPE);
    }

    protected List<OrganizationBean> getOrganizationBeanList(String orgTypeId, String requesterId) {
        return mapper.mapToList(getOrganizationList(orgTypeId, requesterId),
                OrganizationBean.class);
    }

    private List<Organization> getOrganizationList(String orgTypeId, String requesterId) {
        final OrganizationSearchBean searchBean = new OrganizationSearchBean();
        searchBean.setDeepCopy(false);
        searchBean.setOrganizationTypeId(orgTypeId);
        return getOrganizationList(searchBean, requesterId);
    }
    protected List<Organization> getOrganizationList(OrganizationSearchBean searchBean, String requesterId) {
        return organizationDataService.findBeansLocalized(searchBean, requesterId, 0, Integer.MAX_VALUE, getCurrentLanguage());
    }

    protected List<KeyNameBean> getResourceTypesAsKeyNameBeans() {
        final List<ResourceType> resourceTypes = resourceDataService
                .getAllResourceTypes(getCurrentLanguage());
        return mapper.mapToList(resourceTypes, KeyNameBean.class);
    }

    protected List<KeyNameBean> getResourceTypesAsKeyNameBeans(
            final boolean isSearchable) {
        final ResourceTypeSearchBean searchBean = new ResourceTypeSearchBean();
        searchBean.setSearchable(isSearchable);
        final List<ResourceType> resourceTypes = resourceDataService
                .findResourceTypes(searchBean, 0, Integer.MAX_VALUE, getCurrentLanguage());
        return mapper.mapToList(resourceTypes, KeyNameBean.class);
    }

    protected List<KeyNameBean> getManagedSystemsAsKeyNameBeans() {
        final List<ManagedSysDto> managedSystems = managedSysServiceClient
                .getAllManagedSys();
        return mapper.mapToList(managedSystems, KeyNameBean.class);
    }

    protected List<KeyNameBean> getActionsKeyNames() {
        if(actionsKeyNames == null) {
            actionsKeyNames = new LinkedList<>();
            for(AuditAction auditAction : AuditAction.values()) {
                actionsKeyNames.add(new KeyNameBean(auditAction.name(), auditAction.value()));
            }
        }
        Collections.sort(actionsKeyNames);
        return actionsKeyNames;
    }

    protected List<KeyNameBean> getAuditTargetTypesKeyNames() {
        if(auditTargetTypesKeyNames == null) {
            auditTargetTypesKeyNames = new LinkedList<>();
            for(AuditTarget auditTarget : AuditTarget.values()) {
                auditTargetTypesKeyNames.add(new KeyNameBean(auditTarget.name(), auditTarget.value()));
            }
        }
        Collections.sort(auditTargetTypesKeyNames);
        return auditTargetTypesKeyNames;
    }

    protected List<KeyNameBean> getStatusCodeTypeKeyNames() {
        if(statusKeyNames == null) {
            statusKeyNames = new LinkedList<>();
            for(StatusCodeType statusCodeType : StatusCodeType.values()) {
                statusKeyNames.add(new KeyNameBean(statusCodeType.name(), statusCodeType.name()));
            }
        }
        Collections.sort(statusKeyNames);
        return statusKeyNames;
    }

	protected List<KeyNameBean> getResourceRiskAsKeyNameBeans() {
		List<KeyNameBean> result = new ArrayList<KeyNameBean>();
		for(ResourceRisk risk: ResourceRisk.values()){
			result.add(new KeyNameBean(risk.name(), risk.name()));
		}
		return result;
	}

    protected String getMessage(HttpServletRequest request, Errors key) {
        return this.getMessage(request, key, null);
    }

    protected String getMessage(HttpServletRequest request, Errors key,
            Object[] arguments) {
        return this.getMessage(request, key, null, null);
    }

    protected String getMessage(HttpServletRequest request, Errors key,
            Object[] arguments, String defaultMsg) {
        RequestContext ctx = new RequestContext(request);
        return ctx.getMessage(key.getMessageName(), arguments, defaultMsg);
    }
}
