package org.openiam.ui.selfservice.web.mvc;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.bpm.activiti.ActivitiService;
import org.openiam.bpm.util.ActivitiRequestType;
import org.openiam.exception.EsbErrorToken;
import org.openiam.idm.srvc.auth.dto.Login;
import org.openiam.idm.srvc.loc.dto.Location;
import org.openiam.idm.srvc.meta.dto.SaveTemplateProfileResponse;
import org.openiam.idm.srvc.user.dto.NewUserProfileRequestModel;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.ui.rest.api.model.LocationBean;
import org.openiam.ui.selfservice.web.model.NewUserBean;
import org.openiam.ui.selfservice.web.model.ProfieEditMode;
import org.openiam.ui.selfservice.web.mvc.validator.UserProfileValidator;
import org.openiam.ui.util.URIUtils;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.model.BeanResponse;
import org.openiam.ui.web.util.DateFormatStr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@Controller
public class AnonymousUserProfileController extends AbstractProfileController {

    private static Logger LOG = Logger.getLogger(AnonymousUserProfileController.class);

    @Value("${org.openiam.defaultManagedSysId}")
    protected String managedSysId;

    @Resource(name = "activitiClient")
    private ActivitiService activitiService;

    @Value("${org.openiam.self.register.enabled}")
    private boolean isSelfRegistrationEnabled;

    @Value("${org.openiam.provision.service.flag}")
    protected Boolean provisionServiceFlag;

    @Autowired
    @Qualifier("beanMapper")
    private Mapper mapper;

    @Autowired
    private UserProfileValidator profileValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(profileValidator);
    }

    @RequestMapping(value = "/selfRegistration", method = RequestMethod.GET)
    public String selfRegistrationGET(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        if (!isSelfRegistrationEnabled) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        request.setAttribute("currentURI", URIUtils.getRequestURL(request));
        request.setAttribute("addBranding", true);
        request.setAttribute("pageType", ProfieEditMode.SELF_REGISTRATION);
        return processProfileScreenGetRequest(request, new User(), true, null);
    }

    @RequestMapping(value = "/selfRegistration", method = RequestMethod.POST)
    public String selfRegistrationPOST(final HttpServletRequest request, final HttpServletResponse response,
                                       @Valid @RequestBody final NewUserBean requestBean) throws IOException {
        if (!isSelfRegistrationEnabled) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        final BasicAjaxResponse ajaxResponse = createNewUser(request, response, requestBean, ActivitiRequestType.SELF_REGISTRATION);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/getLocationsForOrg", method = RequestMethod.GET)
    public @ResponseBody
    BeanResponse getLocationsForOrg(final @RequestParam(required = true, value = "id") String  orgId,
                                    final @RequestParam(required = true, value = "from") Integer from,
                                    final @RequestParam(required = true, value = "size") Integer size) {

        final List<LocationBean> beanList = new LinkedList<LocationBean>();
        int cnt = 0;

        List<Location> locations = organizationDataService.getLocationListByPage(orgId, from, size);

        if (locations != null && locations.size() > 0) {
            cnt = locations.size();
            for (final Location locationEl : locations) {
                beanList.add(new LocationBean(locationEl));
            }
        }

        return new BeanResponse(beanList, cnt);
    }

    @RequestMapping(value = "/newUser", method = RequestMethod.GET)
    public String newUserGET(final HttpServletRequest request) throws Exception {
        request.setAttribute("pageType", ProfieEditMode.NEW_USER_NO_APPROVER);
        return processProfileScreenGetRequest(request, new User(), true, null);
    }

    @RequestMapping(value = "/newUser", method = RequestMethod.POST)
    public String newUserPOST(final HttpServletRequest request, final HttpServletResponse response, @Valid @RequestBody final NewUserBean requestBean) {
        final BasicAjaxResponse ajaxResponse = createNewUser(request, response, requestBean, ActivitiRequestType.NEW_HIRE_NO_APPROVAL);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    @RequestMapping(value = "/newUserWithApprover", method = RequestMethod.GET)
    public String newUserWithApprover(final HttpServletRequest request) throws Exception {
        request.setAttribute("pageType", ProfieEditMode.NEW_USER_WITH_APPROVER);
        return processProfileScreenGetRequest(request, new User(), true, null);
    }

    @RequestMapping(value = "/newUserWithApprover", method = RequestMethod.POST)
    public String newUserWithApprover(final HttpServletRequest request, final HttpServletResponse response,
                                      @Valid @RequestBody final NewUserBean requestBean) {
        final BasicAjaxResponse ajaxResponse = createNewUser(request, response, requestBean, ActivitiRequestType.NEW_HIRE_WITH_APPROVAL);
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }

    private BasicAjaxResponse createNewUser(final HttpServletRequest request, final HttpServletResponse response, final NewUserBean requestBean,
                                            final ActivitiRequestType requestType) {
        Errors error = null;
        Object[] params = null;
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        boolean isValidationError = false;
        if (!ajaxResponse.isError()) {
            try {
                //Add flag that this user is creating under selfservice
                //IDMAPPS-2470 - Provide flag to provision: is user created from selfservice or not
                requestBean.getUser().setIsFromActivitiCreation(true);
                //-------------------
                final NewUserProfileRequestModel requestModel = convert(requestBean, request);
                requestModel.setPageTemplate(requestBean.getPageTemplate());
                requestModel.setLanguageId(getCurrentLanguage().getId());
                requestModel.setActivitiRequestType(requestType);
                requestModel.setRequestorUserId(getRequesterId(request));
                requestModel.setRequestClientIP(request.getRemoteAddr());

                if (!provisionServiceFlag) {
                    final List<Login> loginList = new LinkedList<Login>();
                    final Login login = new Login();
                    login.setManagedSysId(managedSysId);
                    login.setLogin(requestBean.getLogin());
                    loginList.add(login);
                    requestModel.setLoginList(loginList);
                }

                final SaveTemplateProfileResponse wsResponse = activitiService.initiateNewHireRequest(requestModel);
                if (wsResponse.getStatus() != ResponseStatus.SUCCESS) {
                    error = Errors.INTERNAL_ERROR;
                    if (wsResponse.getErrorCode() != null) {
                        switch (wsResponse.getErrorCode()) {
                            case FIRST_NAME_REQUIRED:
                                error = Errors.REQUIRED_USER_FIRST_NAME;
                                break;
                            case LAST_NAME_REQUIRED:
                                error = Errors.REQUIRED_USER_LAST_NAME;
                                break;
                            case EMAIL_REQUIRED:
                                error = Errors.REQUIRED_USER_EMAIL;
                                break;
                            case LOGIN_REQUIRED:
                                error = Errors.REQUIRED_USER_LOGIN;
                                break;
                            case NO_REQUEST_APPROVERS:
                                error = Errors.NO_REQUEST_APPROVERS;
                                break;
                            case UNAUTHORIZED:
                                error = Errors.UNAUTHORIZED;
                                break;
                            case INVALID_VALUE:
                                error = Errors.INVALID_META_VALUE;
                                params = new Object[]{wsResponse.getCurrentValue(), wsResponse.getElementName()};
                                break;
                            case REQUIRED:
                                error = Errors.META_VALUE_REQUIRED;
                                params = new Object[]{wsResponse.getElementName()};
                                break;
                            case SEND_EMAIL_FAILED:
                                error = Errors.USER_CREATED_BUT_EMAIL_NOT_SENT;
                                break;
                            case LOGIN_EXISTS:
                                error = Errors.LOGIN_TAKEN;
                                break;
                            case VALIDATION_ERROR:
                                isValidationError = true;
                                for (EsbErrorToken e : wsResponse.getErrorTokenList()) {
                                    ErrorToken errorToken = new ErrorToken();
                                    errorToken.setValidationError(e.getMessage());
                                    if (e.getLengthConstraint() != null) {
                                        errorToken.setParams(new Object[]{e.getLengthConstraint()});
                                    }

                                    ajaxResponse.addError(errorToken);
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }
            } catch (ParseException e) {
                List<String> prm = new ArrayList<>();
                prm.add(DateFormatStr.getSdfDate());
                params = prm.toArray();
                error = Errors.INVALID_BIRTHDAY_FORMAT;
            } catch (Throwable e) {
                error = Errors.INTERNAL_ERROR;
                LOG.error("Unknown error while saving new user", e);
            } finally {
                if (isValidationError) {
                    ajaxResponse.setStatus(500);
                } else if (error != null) {
                    final ErrorToken token = new ErrorToken(error, params);
                    ajaxResponse.addError(token);
                    ajaxResponse.setStatus(500);
                } else {
                    SuccessMessage successMessage = null;
                    ajaxResponse.setStatus(200);
                    switch (requestType) {
                        case NEW_HIRE_NO_APPROVAL:
                            ajaxResponse.setRedirectURL(redirectUrlAfterUserWorkflowInitialization);
                            successMessage = SuccessMessage.USER_PROFILE_SAVED;
                            break;
                        case NEW_HIRE_WITH_APPROVAL:
                            ajaxResponse.setRedirectURL(redirectUrlAfterUserWorkflowInitialization);
                            successMessage = SuccessMessage.NEW_HIRE_PROCESS_CREATED;
                            break;
                        case SELF_REGISTRATION:
                            ajaxResponse.setRedirectURL("/selfservice");
                            successMessage = SuccessMessage.SELF_REGISTRATION_SUCCESSFUL;
                            break;
                        default:
                            break;
                    }
                    ajaxResponse.setSuccessToken(new SuccessToken(successMessage));
                }
            }
        }
        return ajaxResponse;
    }

    private NewUserProfileRequestModel convert(final NewUserBean requestBean, final HttpServletRequest request) throws ParseException {
        requestBean.formatDates();
        final NewUserProfileRequestModel profileRequest = mapper.map(requestBean, NewUserProfileRequestModel.class);
        if (StringUtils.isNotBlank(requestBean.getLogin())) {
            final Login login = new Login();
            login.setManagedSysId(defaultManagedSysId);
            login.setLogin(requestBean.getLogin());
            profileRequest.addLogin(login);
        }
        return profileRequest;
    }
}
