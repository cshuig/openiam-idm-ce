package org.openiam.ui.selfservice.web.mvc.validator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.srvc.auth.dto.Login;
import org.openiam.idm.srvc.auth.ws.LoginDataWebService;
import org.openiam.idm.srvc.auth.ws.LoginResponse;
import org.openiam.idm.srvc.continfo.dto.Address;
import org.openiam.idm.srvc.continfo.dto.EmailAddress;
import org.openiam.idm.srvc.continfo.dto.Phone;
import org.openiam.idm.srvc.meta.dto.PageTempate;
import org.openiam.idm.srvc.meta.dto.TemplateUIField;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.idm.srvc.user.ws.UserDataWebService;
import org.openiam.script.ScriptIntegration;
import org.openiam.ui.security.OpenIAMCookieProvider;
import org.openiam.ui.selfservice.web.groovy.ContactInfoPostprocessor;
import org.openiam.ui.selfservice.web.groovy.UnauthenticatedContactInfoPostprocessor;
import org.openiam.ui.selfservice.web.model.NewUserBean;
import org.openiam.ui.selfservice.web.mvc.provider.TemplateProvider;
import org.openiam.ui.web.util.DateFormatStr;
import org.openiam.ui.web.util.OpeniamCookieLocaleResolver;
import org.openiam.ui.web.validator.AbstractUserValidator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component
public class UserProfileValidator extends AbstractUserValidator implements Validator, ApplicationContextAware {

    private static Logger LOG = Logger.getLogger(UserProfileValidator.class);

    private ApplicationContext ctx;

    @Autowired
    private TemplateProvider templateProvider;

    @Autowired
    private OpenIAMCookieProvider cookieProvider;

    @Autowired
    protected MessageSource messageSource;

    @Autowired
    @Qualifier("localeResolver")
    protected OpeniamCookieLocaleResolver localeResolver;

    @Autowired
    @Qualifier("configurableGroovyScriptEngine")
    private ScriptIntegration scriptRunner;

    @Value("${org.openiam.profile.edit.contact.info.groovy.new.user.approver.postprocessor}")
    private String newUserWithApproverPostProcessor;

    @Value("${org.openiam.profile.edit.contact.info.groovy.new.user.postprocessor}")
    private String newUserPostProcessor;

    @Value("${org.openiam.profile.edit.contact.info.groovy.self.register.postprocessor}")
    private String selfRegisterPostProcessor;

    @Value("${org.openiam.profile.edit.contact.info.groovy.postprocessor}")
    private String contactInfoPostProcessorScript;

    @Resource(name = "userServiceClient")
    protected UserDataWebService userDataWebService;

    @Autowired
    @Resource(name = "loginServiceClient")
    private LoginDataWebService loginServiceClient;

    @Value("${org.openiam.defaultManagedSysId}")
    protected String defaultManagedSysId;

    private Map<String, String> postProcessors = new HashMap<String, String>();
    @Value("${org.openiam.provision.service.flag}")
    protected Boolean provisionServiceFlag;

    @Override
    public boolean supports(Class<?> clazz) {
        return NewUserBean.class.equals(clazz);
    }

    @PostConstruct
    public void init() {
        if (selfRegisterPostProcessor != null) {
            postProcessors.put("selfRegistration.html", selfRegisterPostProcessor);
        }
        if (newUserWithApproverPostProcessor != null) {
            postProcessors.put("newUserWithApprover.html", newUserWithApproverPostProcessor);
        }
        if (newUserPostProcessor != null) {
            postProcessors.put("newUser.html", newUserPostProcessor);
        }
        if (contactInfoPostProcessorScript != null) {
            postProcessors.put("editProfile.html", contactInfoPostProcessorScript);
            postProcessors.put("editUser.html", contactInfoPostProcessorScript);
        }
    }

    // TODO: document this awesomeness.
    @Override
    public void validate(Object target, Errors err) {
        final ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        final HttpServletRequest request = sra.getRequest();
        final String requestorId = cookieProvider.getUserId(request);
        final PageTempate template = templateProvider.getPageTemplate(requestorId, request);
        ArrayList<String> dateFormatPattern = new ArrayList<>();
        dateFormatPattern.add(DateFormatStr.getSdfDate());

        if (template == null) {
            err.rejectValue("pageTemplate", "required", org.openiam.ui.util.messages.Errors.MISCONFIGURED_PAGE.getMessageName());
        } else {
            final NewUserBean userBean = (NewUserBean) target;
            final User user = userBean.getUser();
            final boolean isNewUser = StringUtils.isBlank(user.getId());
            final User oldUser = (!isNewUser) ? userDataWebService.getUserWithDependent(user.getId(), null, true) : null;
            if (!isNewUser && oldUser == null) {
                err.rejectValue("pageTemplate", "required", org.openiam.ui.util.messages.Errors.INVALID_REQUEST.getMessageName());
                return;
            }
            userBean.formatDates();

            TemplateUIField field = template.getUIField("USER_FIRST_NAME");
            if (field != null) {
                if (field.isRequired() && StringUtils.isBlank(user.getFirstName())) {
                    err.rejectValue("user.firstName", "required", org.openiam.ui.util.messages.Errors.REQUIRED_USER_FIRST_NAME.getMessageName());
                } else if (oldUser != null && !field.isEditable()) {
                    user.setFirstName(oldUser.getFirstName());
                }
            }

            field = template.getUIField("USER_LAST_NAME");
            if (field != null) {
                if (field.isRequired() && StringUtils.isBlank(user.getLastName())) {
                    err.rejectValue("user.lastName", "required", org.openiam.ui.util.messages.Errors.REQUIRED_USER_LAST_NAME.getMessageName());
                } else if (oldUser != null && !field.isEditable()) {
                    user.setLastName(oldUser.getLastName());
                }
            }

            field = template.getUIField("USER_MIDDLE_INIT");
            if (field != null) {
                if (field.isRequired() && StringUtils.isBlank(user.getMiddleInit())) {
                    err.rejectValue("user.middleInit", "required", org.openiam.ui.util.messages.Errors.USER_MIDDLE_INIT_REQUIRED.getMessageName());
                } else if (oldUser != null && !field.isEditable()) {
                    user.setMiddleInit(oldUser.getMiddleInit());
                }
            }

            field = template.getUIField("USER_MAIDEN_NAME");
            if (field != null) {
                if (field.isRequired() && StringUtils.isBlank(user.getMaidenName())) {
                    err.rejectValue("user.maidenName", "required", org.openiam.ui.util.messages.Errors.USER_MAIDEN_NAME_REQUIRED.getMessageName());
                } else if (oldUser != null && !field.isEditable()) {
                    user.setMaidenName(oldUser.getMaidenName());
                }
            }

            field = template.getUIField("USER_NICKNAME");
            if (field != null) {
                if (field.isRequired() && StringUtils.isBlank(user.getNickname())) {
                    err.rejectValue("user.nickname", "required", org.openiam.ui.util.messages.Errors.USER_NICKNAME_REQUIRED.getMessageName());
                } else if (oldUser != null && !field.isEditable()) {
                    user.setNickname(oldUser.getNickname());
                }
            }

            field = template.getUIField("USER_DOB");
            if (field != null) {
                if (field.isRequired() && user.getBirthdate() == null) {
                    err.rejectValue("user.birthdate", "required", org.openiam.ui.util.messages.Errors.REQUIRED_USER_BIRTHDAY.getMessageName());
                } else if (oldUser != null && !field.isEditable()) {
                    user.setBirthdate(oldUser.getBirthdate());
                }
            }

            field = template.getUIField("USER_TITLE");
            if (field != null) {
                if (field.isRequired() && StringUtils.isBlank(user.getTitle())) {
                    err.rejectValue("user.title", "required", org.openiam.ui.util.messages.Errors.USER_TITLE_REQUIRED.getMessageName());
                } else if (oldUser != null && !field.isEditable()) {
                    user.setTitle(oldUser.getTitle());
                }
            }

            field = template.getUIField("USER_GENDER");
            if (field != null) {
                if (field.isRequired() && StringUtils.isBlank(user.getSex())) {
                    err.rejectValue("user.sex", "required", org.openiam.ui.util.messages.Errors.REQUIRED_USER_SEX.getMessageName());
                } else if (oldUser != null && !field.isEditable()) {
                    user.setSex(oldUser.getSex());
                }
            }

            field = template.getUIField("USER_EMPLOYEE_ID");
            if (field != null) {
                if (field.isRequired() && StringUtils.isBlank(user.getEmployeeId())) {
                    err.rejectValue("user.employeeId", "required", org.openiam.ui.util.messages.Errors.USER_EMPLOYEE_ID_REQUIRED.getMessageName());
                } else if (oldUser != null && !field.isEditable()) {
                    user.setEmployeeId(oldUser.getEmployeeId());
                }
            }

            field = template.getUIField("USER_EMPLOYEE_TYPE");
            if (field != null) {
                if (field.isRequired() && StringUtils.isBlank(user.getEmployeeTypeId())) {
                    err.rejectValue("user.employeeTypeId", "required", org.openiam.ui.util.messages.Errors.REQUIRED_USER_EMPLOYEE_TYPE.getMessageName());
                } else if (oldUser != null && !field.isEditable()) {
                    user.setEmployeeTypeId(oldUser.getEmployeeTypeId());
                }
            }

            field = template.getUIField("USER_LOCATION_CODE");
            if (field != null) {
                if (field.isRequired() && StringUtils.isBlank(user.getLocationCd())) {
                    err.rejectValue("user.locationCd", "required", org.openiam.ui.util.messages.Errors.USER_LOCATION_CODE_REQUIRED.getMessageName());
                } else if (oldUser != null && !field.isEditable()) {
                    user.setLocationCd(oldUser.getLocationCd());
                }
            }

            field = template.getUIField("USER_LOCATION_NAME");
            if (field != null) {
                if (field.isRequired() && StringUtils.isBlank(user.getLocationName())) {
                    err.rejectValue("user.locationName", "required", org.openiam.ui.util.messages.Errors.USER_LOCATION_NAME_REQUIRED.getMessageName());
                } else if (oldUser != null && !field.isEditable()) {
                    user.setLocationName(oldUser.getLocationName());
                }
            }

            field = template.getUIField("USER_CLASSICIATION");
            if (field != null) {
                if (field.isRequired() && StringUtils.isBlank(user.getClassification())) {
                    err.rejectValue("user.classification", "required",
                            org.openiam.ui.util.messages.Errors.USER_CLASSIFICATION_REQUIRED.getMessageName());
                } else if (oldUser != null && !field.isEditable()) {
                    user.setClassification(oldUser.getClassification());
                }
            }

            field = template.getUIField("USER_PREFIX");
            if (field != null) {
                if (field.isRequired() && StringUtils.isBlank(user.getPrefix())) {
                    err.rejectValue("user.prefix", "required", org.openiam.ui.util.messages.Errors.USER_PREFIX_REQUIRED.getMessageName());
                } else if (oldUser != null && !field.isEditable()) {
                    user.setPrefix(oldUser.getPrefix());
                }
            }

            field = template.getUIField("USER_STATUS");
            if (field != null) {
                if (field.isRequired() && user.getStatus() == null) {
                    err.rejectValue("user.status", "required", org.openiam.ui.util.messages.Errors.USER_STATUS_REQUIRED.getMessageName());
                } else if (oldUser != null && !field.isEditable()) {
                    user.setStatus(oldUser.getStatus());
                }
            }

            field = template.getUIField("USER_SECONDARY_STATUS");
            if (field != null) {
                if (field.isRequired() && user.getSecondaryStatus() == null) {
                    err.rejectValue("user.secondaryStatus", "required",
                            org.openiam.ui.util.messages.Errors.USER_SECONDARY_STATUS_REQUIRED.getMessageName());
                } else if (oldUser != null && !field.isEditable()) {
                    user.setSecondaryStatus(oldUser.getSecondaryStatus());
                }
            }

            field = template.getUIField("USER_SUFFIX");
            if (field != null) {
                if (field.isRequired() && StringUtils.isBlank(user.getSuffix())) {
                    err.rejectValue("user.suffix", "required", org.openiam.ui.util.messages.Errors.USER_SUFFIX_REQUIRED.getMessageName());
                } else if (oldUser != null && !field.isEditable()) {
                    user.setSuffix(oldUser.getSuffix());
                }
            }

            field = template.getUIField("USER_MAILCODE");
            if (field != null) {
                if (field.isRequired() && StringUtils.isBlank(user.getMailCode())) {
                    err.rejectValue("user.mailCode", "required", org.openiam.ui.util.messages.Errors.USER_MAILCODE_REQUIRED.getMessageName());
                } else if (oldUser != null && !field.isEditable()) {
                    user.setMailCode(oldUser.getMailCode());
                }
            }

            field = template.getUIField("USER_COST_CENTER");
            if (field != null) {
                if (field.isRequired() && StringUtils.isBlank(user.getCostCenter())) {
                    err.rejectValue("user.costCenter", "required", org.openiam.ui.util.messages.Errors.USER_COST_CENTER_REQUIRED.getMessageName());
                } else if (oldUser != null && !field.isEditable()) {
                    user.setCostCenter(oldUser.getCostCenter());
                }
            }

            field = template.getUIField("USER_JOB_CODE");
            if (field != null) {
                if (field.isRequired() && StringUtils.isBlank(user.getJobCodeId())) {
                    err.rejectValue("user.jobCodeId", "required", org.openiam.ui.util.messages.Errors.USER_JOB_CODE_REQUIRED.getMessageName());
                } else if (oldUser != null && !field.isEditable()) {
                    user.setJobCodeId(oldUser.getJobCodeId());
                }
            }

            field = template.getUIField("USER_USER_TYPE");
            if (field != null) {
                if (field.isRequired() && StringUtils.isBlank(user.getUserTypeInd())) {
                    err.rejectValue("user.userTypeInd", "required", org.openiam.ui.util.messages.Errors.USER_USER_TYPE_REQUIRED.getMessageName());
                } else if (oldUser != null && !field.isEditable()) {
                    user.setUserTypeInd(oldUser.getUserTypeInd());
                }
            }

            field = template.getUIField("USER_OBJECT_TYPE");
            if (field != null) {
                if (field.isRequired() && StringUtils.isBlank(user.getMdTypeId())) {
                    err.rejectValue("user.mdTypeId", "required",
                            org.openiam.ui.util.messages.Errors.REQUIRED_USER_METADATA_TYPE.getMessageName());
                } else if (oldUser != null && !field.isEditable()) {
                    user.setMdTypeId(oldUser.getMdTypeId());
                }
            }

            /*
             * you can only select one of these if the person creating the
             * profile is authenticated
             */
            if (!StringUtils.isBlank(requestorId)) {
                field = template.getUIField("USER_ALTERNATE_CONTACT");
                if (field != null) {
                    if (field.isRequired() && StringUtils.isBlank(user.getAlternateContactId())) {
                        err.rejectValue("user.alternateContactId", "required",
                                org.openiam.ui.util.messages.Errors.USER_ALTERNATE_CONTACT_REQUIRED.getMessageName());
                    } else if (oldUser != null && !field.isEditable()) {

                    }
                }

                if (isNewUser) {
                    field = template.getUIField("USER_SELECT_SUPERVISOR");
                    if (field != null) {
                        if (field.isRequired() && CollectionUtils.isEmpty(userBean.getSupervisorIds())) {
                            err.rejectValue("supervisorIds", "required",
                                    org.openiam.ui.util.messages.Errors.USER_SELECT_SUPERVISOR_REQUIRED.getMessageName());
                        }
                    }
                }
            }

            /* new user only */
            if (isNewUser) {
                field = template.getUIField("USER_CREATE_LOGIN");
                if (field != null) {
                    if (field.isRequired() && StringUtils.isBlank(userBean.getLogin())) {
                        err.rejectValue("login", "required", org.openiam.ui.util.messages.Errors.REQUIRED_USER_LOGIN.getMessageName());
                    } else {
                        LoginResponse response = loginServiceClient.getLoginByManagedSys(userBean.getLogin(), defaultManagedSysId);
                        Login lg = response.getPrincipal();
                        if (lg != null) {
                            err.rejectValue("login", "required", org.openiam.ui.util.messages.Errors.LOGIN_TAKEN.getMessageName());
                        }
                    }

                }

                field = template.getUIField("USER_SHOW_ROLES");
                if (field != null) {
                    if (field.isRequired() && CollectionUtils.isEmpty(userBean.getRoleIds())) {
                        err.rejectValue("roleIds", "required", org.openiam.ui.util.messages.Errors.USER_ROLES_REQUIRED.getMessageName());
                    }
                }

                field = template.getUIField("USER_SHOW_GROUPS");
                if (field != null) {
                    if (field.isRequired() && CollectionUtils.isEmpty(userBean.getGroupIds())) {
                        err.rejectValue("groupIds", "required", org.openiam.ui.util.messages.Errors.USER_GROUPS_REQUIRED.getMessageName());
                    }
                }

                field = template.getUIField("USER_SHOW_ORGANIZATIONS");
                if (field != null) {
                    if (field.isRequired()) {
                        doOrganizationChecks(err, userBean.getOrganizationIds());
                    }
                }
            } else {
                /* existing user - dont' set collections */
            }
            /* end new user */

            field = template.getUIField("USER_START_DATE");
            if (field != null) {
                if (field.isRequired() && user.getStartDate() == null) {
                    err.rejectValue("user.startDate", "required", messageSource.getMessage(org.openiam.ui.util.messages.Errors.INVALID_START_DATE_FORMAT.getMessageName(), dateFormatPattern.toArray(), localeResolver.resolveLocale(request)));
                } else if (oldUser != null && !field.isEditable()) {
                    user.setStartDate(oldUser.getStartDate());
                }
            }

            field = template.getUIField("USER_END_DATE");
            if (field != null) {
                if (field.isRequired() && user.getLastDate() == null) {
                    err.rejectValue("user.lastDate", "required", messageSource.getMessage(org.openiam.ui.util.messages.Errors.INVALID_LAST_DATE_FORMAT.getMessageName(), dateFormatPattern.toArray(), localeResolver.resolveLocale(request)));
                } else if (oldUser != null && !field.isEditable()) {
                    user.setLastDate(oldUser.getLastDate());
                }
            }

            ContactInfoPostprocessor contactInfoPostProcessor = null;
            if (isNewUser) {
                final UnauthenticatedContactInfoPostprocessor postProcessor = getUnauthenticatedContactInfoPostProcessor(userBean,
                        request,
                        getCurrentPreProcessor(request));
                contactInfoPostProcessor = postProcessor;
                final String loginError = postProcessor.validateLogin(userBean.getLogin());
                if (loginError != null) {
                    err.rejectValue("login", "required", loginError);
                } else {
                    if (StringUtils.isBlank(userBean.getLogin()) && !provisionServiceFlag) {
                        userBean.setLogin(postProcessor.getProcessedLogin());
                    }
                }
            } else {
                contactInfoPostProcessor = getContactInfoPostProcessor(user, request, getCurrentPreProcessor(request));
            }

            validateEmails(template, err, userBean.getEmails(), contactInfoPostProcessor);
            validatePhones(template, err, userBean.getPhones(), contactInfoPostProcessor);
            validateAddresses(template, err, userBean.getAddresses(), contactInfoPostProcessor);
        }
    }

    private String getCurrentPreProcessor(final HttpServletRequest request) {
        String contextPath = request.getContextPath();
        contextPath = new StringBuilder(contextPath).append("/").toString();
        final String url = request.getRequestURI().substring(contextPath.length());
        return postProcessors.get(url);
    }

    private void validateEmails(final PageTempate template, final Errors err, final List<EmailAddress> emails,
                                final ContactInfoPostprocessor postProcessor) {
        if (CollectionUtils.isEmpty(emails)) {
            /* IDMAPPS-1247 */
            if (template.isFieldRequired("USER_EMAIL_CREATABLE")) {
                err.rejectValue("emails", "required", org.openiam.ui.util.messages.Errors.USER_EMAIL_REQUIRED.getMessageName());
            }
        } else {
            final Set<String> typeSet = new HashSet<String>();
            for (final EmailAddress address : emails) {
                final String message = postProcessor.validate(address);
                if (message != null) {
                    err.rejectValue("emails", "required", new Object[]{address.getName()}, message);
                }

                if (StringUtils.isBlank(address.getMetadataTypeId())) {
                    err.rejectValue("emails", "required", org.openiam.ui.util.messages.Errors.EMAIL_TYPE_REQUIRED.getMessageName());
                } else {
                    if (typeSet.contains(address.getMetadataTypeId())) {
                        err.rejectValue("emails", "required", org.openiam.ui.util.messages.Errors.EMAIL_ADDRESS_TYPE_DUPLICATED.getMessageName());
                    }
                    typeSet.add(address.getMetadataTypeId());
                }
            }
        }
    }

    private void validatePhones(final PageTempate template, final Errors err, final List<Phone> phones, final ContactInfoPostprocessor postProcessor) {
        if (CollectionUtils.isEmpty(phones) && template.isFieldRequired("USER_PHONES_CREATABLE")) {
            err.rejectValue("phones", "required", org.openiam.ui.util.messages.Errors.USER_PHONE_REQUIRED.getMessageName());
        } else {
            final Set<String> typeSet = new HashSet<String>();
            for (final Phone phone : phones) {
                final String message = postProcessor.validate(phone);
                if (message != null) {
                    err.rejectValue("phones", "required", new Object[]{phone.getName()}, message);
                }

                if (StringUtils.isBlank(phone.getMetadataTypeId())) {
                    err.rejectValue("phones", "required", org.openiam.ui.util.messages.Errors.PHONE_TYPE_REQUIRED.getMessageName());
                } else {
                    if (typeSet.contains(phone.getMetadataTypeId())) {
                        err.rejectValue("phones", "required", org.openiam.ui.util.messages.Errors.PHONE_TYPE_DUPLICATED.getMessageName());
                    }
                    typeSet.add(phone.getMetadataTypeId());
                }
                if (!org.springframework.util.StringUtils.hasText(phone.getAreaCd())) {
                    err.rejectValue("phones", "required", org.openiam.ui.util.messages.Errors.PROFILE_PHONE_AREA_CD_REQUIRED.getMessageName());
                }
                if (!org.springframework.util.StringUtils.hasText(phone.getPhoneNbr())) {
                    err.rejectValue("phones", "required", org.openiam.ui.util.messages.Errors.PROFILE_PHONE_NUMBER_REQUIRED.getMessageName());
                }
            }
        }
    }

    private void validateAddresses(final PageTempate template, final Errors err, final List<Address> addresses,
                                   final ContactInfoPostprocessor postProcessor) {
        if (CollectionUtils.isEmpty(addresses) && template.isFieldRequired("USER_ADDRESSES_CREATABLE")) {
            err.rejectValue("addresses", "required", org.openiam.ui.util.messages.Errors.USER_ADDRESS_REQUIRED.getMessageName());
        } else {
            final Set<String> typeSet = new HashSet<String>();
            for (final Address address : addresses) {
                final String message = postProcessor.validate(address);
                if (message != null) {
                    err.rejectValue("addresses", "required", new Object[]{address.getName()}, message);
                }

                if (StringUtils.isBlank(address.getMetadataTypeId())) {
                    err.rejectValue("addresses", "required", org.openiam.ui.util.messages.Errors.ADDRESS_TYPE_REQUIRED.getMessageName());
                } else {
                    if (typeSet.contains(address.getMetadataTypeId())) {
                        err.rejectValue("addresses", "required", org.openiam.ui.util.messages.Errors.ADDRESS_TYPE_DUPLICATED.getMessageName());
                    }
                    typeSet.add(address.getMetadataTypeId());
                }
            }
        }
    }

    private UnauthenticatedContactInfoPostprocessor getUnauthenticatedContactInfoPostProcessor(final NewUserBean userRequest,
                                                                                               final HttpServletRequest request, final String script) {
        UnauthenticatedContactInfoPostprocessor postProcessor = null;
        try {
            postProcessor = (UnauthenticatedContactInfoPostprocessor) scriptRunner.instantiateClass(null, script);
        } catch (Throwable e) {
            LOG.info(String.format("Can't inialize script %s.  This is not an error", script), e);
        }
        postProcessor = (postProcessor != null) ? new UnauthenticatedContactInfoPostprocessor() : null;

        final Map<String, Object> bindingMap = new HashMap<String, Object>();
        bindingMap.put("context", ctx);
        bindingMap.put("request", request);
        bindingMap.put("user", userRequest.getUser());
        bindingMap.put("login", userRequest.getLogin());
        bindingMap.put("emails", userRequest.getEmails());
        postProcessor.init(bindingMap);
        return postProcessor;
    }

    private ContactInfoPostprocessor getContactInfoPostProcessor(final User user, final HttpServletRequest request, final String script) {
        ContactInfoPostprocessor postProcessor = null;
        try {
            postProcessor = (ContactInfoPostprocessor) scriptRunner.instantiateClass(null, script);
        } catch (Throwable e) {
            LOG.info(String.format("Can't inialize script %s.  This is not an error", script), e);
        }
        postProcessor = (postProcessor != null) ? new ContactInfoPostprocessor() : null;
        final Map<String, Object> bindingMap = new HashMap<String, Object>();
        bindingMap.put("context", ctx);
        bindingMap.put("user", user);
        bindingMap.put("request", request);
        postProcessor.init(bindingMap);
        return postProcessor;
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.ctx = ctx;
    }
}
