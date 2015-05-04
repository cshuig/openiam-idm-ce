package org.openiam.ui.idp.web.mvc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openiam.base.ws.MatchType;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.base.ws.SearchParam;
import org.openiam.idm.searchbeans.*;
import org.openiam.idm.srvc.audit.constant.AuditAttributeName;
import org.openiam.idm.srvc.audit.dto.IdmAuditLog;
import org.openiam.idm.srvc.auth.dto.Login;
import org.openiam.idm.srvc.auth.ws.LoginDataWebService;
import org.openiam.idm.srvc.auth.ws.LoginResponse;
import org.openiam.idm.srvc.continfo.dto.EmailAddress;
import org.openiam.idm.srvc.msg.dto.NotificationParam;
import org.openiam.idm.srvc.msg.dto.NotificationRequest;
import org.openiam.idm.srvc.msg.service.MailService;
import org.openiam.idm.srvc.policy.dto.Policy;
import org.openiam.idm.srvc.pswd.dto.*;
import org.openiam.idm.srvc.pswd.service.ChallengeResponseWebService;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.idm.srvc.user.ws.UserDataWebService;
import org.openiam.provision.service.AsynchUserProvisionService;
import org.openiam.provision.service.ProvisionService;
import org.openiam.ui.exception.ErrorMessageException;
import org.openiam.ui.exception.ErrorTokenException;
import org.openiam.ui.exception.HttpErrorCodeException;
import org.openiam.ui.idp.web.model.*;
import org.openiam.ui.util.URIUtils;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.model.SetPasswordToken;
import org.openiam.ui.web.mvc.AbstractPasswordController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

@Controller
public class UnlockUserController extends AbstractUserStatusController {

    private static final String REQUEST_PASSWORD_RESET_NOTIFICATION = "REQUEST_PASSWORD_RESET";
    private static final String REQUEST_LOGIN_REMINDER_NOTIFICATION = "REQUEST_LOGIN_REMINDER";

    @Value("${org.openiam.password.unlock.secure.enabled}")
    private boolean isUnlockSecure;

    @Value("${org.openiam.idp.unlock.password.email.enabled}")
    private boolean isEmailUnlockEnabled;

    @Resource(name = "challengeResponseServiceClient")
    private ChallengeResponseWebService challengeResponseService;

    @Autowired
    @Resource(name = "loginServiceClient")
    private LoginDataWebService loginServiceClient;

    @Resource(name = "provisionServiceClient")
    private ProvisionService provisionService;

    @Resource(name = "asynchProvisionServiceClient")
    private AsynchUserProvisionService asynchUserProvisionService;

    @Resource(name = "userServiceClient")
    private UserDataWebService userDataWebService;

    @Resource(name = "mailServiceClient")
    private MailService mailService;

    @Value("${org.openiam.ui.password.unlock.questions.url}")
    private String unlockQuestionsURL;

    @Value("${org.openiam.ui.challenge.answers.secured}")
    private Boolean secureAnswers;

    private static Logger LOG = Logger.getLogger(UnlockUserController.class);


    @RequestMapping(value = "/unlockUserResetPasswordForm", method = RequestMethod.GET)
    public String unlockPasswordForm(final HttpServletRequest request,
                                     final HttpServletResponse response,
                                     final @RequestParam(required = true, value = "token") String token) {
        request.setAttribute("token", token);
        return passwordResetFormView;
    }

    @RequestMapping(value = "/unlockUserResetPasswordForm", method = RequestMethod.POST)
    public String unlockPasswordForm(final HttpServletRequest request,
                                     final HttpServletResponse response,
                                     final UnlockPasswordFormRequest formRequest) throws IOException {
        final String referer = request.getHeader("referer");
        final String redirectTo = (StringUtils.containsIgnoreCase(referer, "webconsole")) ? "/webconsole" : "/selfservice";

        List<ErrorToken> errorList = null;
        String view = null;
        SetPasswordToken token = null;
        try {
            Policy policy = this.getAuthentificationPolicy();
            final Login login =attemptToResetPassword(request, formRequest,policy);

            final Response unlockResponse = loginServiceClient.unLockLogin(login.getLogin(), this.getAuthentificationManagedSystem(policy));
            if (unlockResponse == null || unlockResponse.getStatus() != ResponseStatus.SUCCESS) {
                throw new ErrorMessageException(Errors.CHANGE_PASSWORD_FAILED);
            }
        } catch (ErrorTokenException e) {
            errorList = e.getTokenList();
        } catch (ErrorMessageException e) {
            errorList = new LinkedList<>();
            errorList.add(new ErrorToken(e.getError()));
        } catch (Throwable e) {
            LOG.error("Can't unlock user", e);
            errorList = new LinkedList<>();
            errorList.add(new ErrorToken(Errors.INTERNAL_ERROR));
        } finally {
            if (CollectionUtils.isNotEmpty(errorList)) {
                final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
                ajaxResponse.addErrors(errorList);
                if (token != null) {
                    ajaxResponse.setPossibleErrors(token.getRules());
                }
                ajaxResponse.setStatus(500);
                ajaxResponse.process(localeResolver, messageSource, request);
                request.setAttribute("ajaxResponse", jacksonMapper.writeValueAsString(ajaxResponse));
                view = unlockPasswordForm(request, response, formRequest.getToken());
            } else {
                request.setAttribute("message", SuccessMessage.PASSWORD_CHANGED.getMessageName());
                request.setAttribute("loginTo", redirectTo);
                view = "common/message";
            }
        }
        return view;
    }

    @RequestMapping(value = "/unlockPasswordQuestions", method = RequestMethod.POST)
    public
    @ResponseBody
    BasicAjaxResponse unlockPasswordQuestionsPOST(final HttpServletRequest request,
                                                  final HttpServletResponse response,
                                                  @RequestBody UnlockUserRequestModel requestModel) throws IOException {
        UnlockUserToken unlockUserToken = null;
        BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        ajaxResponse.setStatus(200);
        try {
            unlockUserToken = getToken(request, requestModel.getUserId(), requestModel.getToken());
            final List<QuestionAnswerBean> answerMap = requestModel.getAnswers();
            if (CollectionUtils.isEmpty(answerMap)) {
                throw new HttpErrorCodeException(HttpServletResponse.SC_NOT_FOUND);
            }

            final String userId = unlockUserToken.getUser().getId();
            final List<UserIdentityAnswer> answerList = new LinkedList<UserIdentityAnswer>();
            for (final QuestionAnswerBean bean : answerMap) {
                final String questionAnswer = StringUtils.trimToNull(bean.getAnswerValue());
                final IdentityAnswerSearchBean searchBean = new IdentityAnswerSearchBean();
                searchBean.setQuestionId(bean.getQuestionId());
                searchBean.setUserId(userId);
                final List<UserIdentityAnswer> searchResult = challengeResponseService.findAnswerBeans(searchBean, getRequesterId(request), 0, 1);
                if (CollectionUtils.isNotEmpty(searchResult)) {
                    final UserIdentityAnswer answer = searchResult.get(0);
                    answer.setQuestionAnswer(questionAnswer);
                    answerList.add(answer);
                }
            }

            if (!challengeResponseService.isResponseValid(unlockUserToken.getUser().getId(), answerList)) {
                throw new ErrorMessageException(Errors.IDENTITY_QUESTIONS_INCORRECT);
            }
            Policy policy = this.getAuthentificationPolicy();
            String managedSysId = this.getAuthentificationManagedSystem(policy);
            final Login login = getLoginByUserId(userId, managedSysId);
            if (login == null) {
                throw new HttpErrorCodeException(HttpServletResponse.SC_NOT_FOUND);
            }

            final String passwordResetToken = getPasswordResetToken(login.getLogin(), managedSysId);
            if (StringUtils.isBlank(passwordResetToken)) {
                throw new HttpErrorCodeException(HttpServletResponse.SC_UNAUTHORIZED);
            }
            ajaxResponse.setRedirectURL("unlockUserResetPasswordForm.html?token=" + passwordResetToken);
        } catch (ErrorMessageException e) {
            ajaxResponse.setStatus(500);
            ErrorToken tokenError = new ErrorToken();
            tokenError.setMessage(e.getError().getMessageName());
            ajaxResponse.addError(tokenError);
        } catch (HttpErrorCodeException e) {
            ajaxResponse.setStatus(e.getErrorCode());
        } catch (Throwable e) {
            ajaxResponse.setStatus(500);
            LOG.error("Can't unlock user", e);
        }
        return ajaxResponse;
    }

    @RequestMapping(value = "/unlockPasswordQuestions", method = RequestMethod.GET)
    public String unlockPasswordQuestions(final HttpServletRequest request,
                                          final HttpServletResponse response,
                                          final @RequestParam(required = false, value = "userId") String unsecureUserId,
                                          final @RequestParam(required = false, value = "token") String token) throws IOException {
        String view = null;
        try {
            final UnlockUserToken unlockUserToken = getToken(request, unsecureUserId, token);
            setUnlockPasswordQuestionsParams(request, unlockUserToken);
            view = "core/user/unlockPasswordQuestions";
        } catch (HttpErrorCodeException e) {
            response.sendError(e.getErrorCode(), e.getErrorMessage());
            return null;
        } catch (Throwable e) {
            LOG.error("Can't unlock user", e);
        }
        return view;
    }

    private void setUnlockPasswordQuestionsParams(final HttpServletRequest request, final UnlockUserToken unlockUserToken) {
        request.setAttribute("unlockUserToken", unlockUserToken);
        request.setAttribute("currentURI", URIUtils.getRequestURL(request));
        request.setAttribute("secureAnswers", secureAnswers);
    }

    @RequestMapping(value = "/unlockPassword", method = RequestMethod.POST)
    public String resetPasswordPOST(final @Valid @ModelAttribute("passwordBean") UnlockPasswordBean passwordBean,
                                    BindingResult result,
                                    final HttpServletRequest request,
                                    final HttpServletResponse response) throws IOException {
        final IdmAuditLog auditLog = new IdmAuditLog();
        auditLog.setAction("Unlock password");
        auditLog.setPrincipal(passwordBean.getPrincipal());
        auditLog.addAttribute(AuditAttributeName.EMAIL, passwordBean.getEmail());
        if (!passwordUnlockEnabled) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        final String referer = request.getHeader("referer");
        final String redirectTo = (StringUtils.containsIgnoreCase(referer, "webconsole")) ? "/webconsole" : "/selfservice";

        Errors error = null;
        String view = "core/user/unlockUser";
        try {
            Policy policy = this.getAuthentificationPolicy();
            String internalLogin = this.buildLoginAccordingAuthPolicy(policy, passwordBean.getPrincipal());
            String managedSysId = this.getAuthentificationManagedSystem(policy);
            if (StringUtils.isNotBlank(passwordBean.getPrincipal())) {
                final User user = getUserByPrincipal(internalLogin, managedSysId);
                if (user == null) {
                    throw new ErrorMessageException(Errors.USER_NOT_FOUND);
                }
                if (CollectionUtils.isEmpty(getQuestionsForUser(request, user.getId()))) {
                    throw new ErrorMessageException(Errors.IDENTITY_QUESTIONS_NOT_ANSWERED);
                }
                if (isUnlockSecure) {
                    if (!sendTokenEmail(request, user, internalLogin, managedSysId)) {
                        throw new ErrorMessageException(Errors.COULD_NOT_SEND_EMAIL);
                    }
                    request.setAttribute("message", SuccessMessage.UNLOCK_EMAIL_SENT.getMessageName());
                    request.setAttribute("loginTo", redirectTo);
                    view = "common/message";
                } else {
                    view = unlockPasswordQuestions(request, response, user.getId(), null);
                }

            } else if (isUnlockSecure && StringUtils.isNotBlank(passwordBean.getEmail())) {

                if (result.hasErrors()) {
                    return view;
                }

                final Set<User> users = getUserListByEmail(passwordBean.getEmail());
                if (CollectionUtils.isEmpty(users)) {
                    throw new ErrorMessageException(Errors.USER_NOT_FOUND);
                }
                for (final User user : users) {
                    final Login login = getLoginByUserId(user.getId(), managedSysId);
                    if (login != null) {
                        if (!sendTokenEmail(request, user, login.getLogin(), managedSysId)) {
                            throw new ErrorMessageException(Errors.COULD_NOT_SEND_EMAIL);
                        }
                        request.setAttribute("message", SuccessMessage.UNLOCK_EMAIL_SENT.getMessageName());
                        request.setAttribute("loginTo", redirectTo);
                        view = "common/message";
                    }
                    /*
                    if(!sendLoginReminderEmail(u)) {
                        throw new ErrorMessageException(Errors.COULD_NOT_SEND_EMAIL);
                    }
                    */
                }
                request.setAttribute("message", SuccessMessage.LOGIN_REMINDER_SENT.getMessageName());
                view = "common/message";

            } else {
                throw new ErrorMessageException(Errors.INVALID_REQUEST);
            }
            auditLog.succeed();
        } catch (ErrorMessageException e) {
            error = e.getError();
            auditLog.fail();
            auditLog.setFailureReason(getMessage(request, e.getError()));
            auditLog.setException(e);
            view = "core/user/unlockUser";
        } catch (Throwable e) {
            error = Errors.INTERNAL_ERROR;
            auditLog.fail();
            auditLog.setFailureReason(getMessage(request, error));
            auditLog.setException(e);
            LOG.error("Can't unlock user", e);
        } finally {
            if (error != null) {
                request.setAttribute("error", new ErrorToken(error));
            }
            auditLogService.addLog(auditLog);
        }
        return view;
    }

    @RequestMapping(value = "/unlockPassword", method = RequestMethod.GET)
    public String resetPassword(final HttpServletResponse response, final Model model) throws IOException {
        if (!passwordUnlockEnabled) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        model.addAttribute("passwordBean", new UnlockPasswordBean());
        return "core/user/unlockUser";
    }

    @ModelAttribute("isUnlockSecure")
    public boolean isUnlockSecure() {
        return isUnlockSecure;
    }

    @ModelAttribute("isEmailUnlockEnabled")
    public boolean isEmailUnlockEnabled() {
        return isEmailUnlockEnabled;
    }

    private UnlockUserToken getToken(final HttpServletRequest request, final String unsecureUserId, final String token) throws HttpErrorCodeException {
        if ((isUnlockSecure && StringUtils.isBlank(token)) || (!isUnlockSecure && StringUtils.isBlank(unsecureUserId))) {
            throw new HttpErrorCodeException(HttpServletResponse.SC_NOT_FOUND);
        }

        final Map<String, String> hiddenAttributes = new HashMap<String, String>();
        String userId = null;
        if (isUnlockSecure) {
            final ValidatePasswordResetTokenResponse validateResponse = passwordService.validatePasswordResetToken(token);
            if (validateResponse == null || !validateResponse.isSuccess() || validateResponse.getPrincipal() == null) {
                throw new HttpErrorCodeException(HttpServletResponse.SC_UNAUTHORIZED, "Token is either invalid, has expired, or can no longer be used");
            }
            userId = validateResponse.getPrincipal().getUserId();
            hiddenAttributes.put("token", token);
        } else {
            userId = unsecureUserId;
            hiddenAttributes.put("userId", userId);
        }

        final User user = getUserById(userId);
        if (user == null) {
            throw new HttpErrorCodeException(HttpServletResponse.SC_NOT_FOUND);
        }

        final List<UserIdentityAnswer> answerList = getQuestionsForUser(request, userId);
        if (CollectionUtils.isEmpty(answerList)) {
            LOG.warn(String.format("No security answers foun for user %s - returning 404", userId));
            throw new HttpErrorCodeException(HttpServletResponse.SC_NOT_FOUND);
        }

        final IdentityQuestionSearchBean searchBean = new IdentityQuestionSearchBean();
        searchBean.setActive(true);
        final List<IdentityQuestion> allQuestionList = challengeResponseService.findQuestionBeans(searchBean, 0, Integer.MAX_VALUE, getCurrentLanguage());
        if (CollectionUtils.isEmpty(allQuestionList)) {
            throw new HttpErrorCodeException(HttpServletResponse.SC_NOT_FOUND);
        }

        final List<IdentityQuestion> questionList = new LinkedList<IdentityQuestion>();
        for (final Iterator<IdentityQuestion> it = allQuestionList.iterator(); it.hasNext(); ) {
            final IdentityQuestion question = it.next();
            for (final UserIdentityAnswer answer : answerList) {
                if (StringUtils.equals(question.getId(), answer.getQuestionId())) {
                    questionList.add(question);
                    it.remove();
                    break;
                }
            }
        }

        if (CollectionUtils.isEmpty(questionList)) {
            throw new HttpErrorCodeException(HttpServletResponse.SC_NOT_FOUND);
        }

        return new UnlockUserToken(user, hiddenAttributes, questionList);
    }

    private User getUserByPrincipal(final String principal, String managedSysId) {
        User user = null;
        final LoginResponse loginResponse = loginServiceClient.getLoginByManagedSys(principal, managedSysId);
        if (loginResponse != null && loginResponse.getStatus() == ResponseStatus.SUCCESS && loginResponse.getPrincipal() != null) {
            final UserSearchBean searchBean = new UserSearchBean();
            searchBean.setKey(loginResponse.getPrincipal().getUserId());
            searchBean.setDeepCopy(false);
            final List<User> userList = userDataWebService.findBeans(searchBean, 0, 1);
            user = (CollectionUtils.isNotEmpty(userList)) ? userList.get(0) : null;
        }
        return user;
    }

    private Set<User> getUserListByEmail(final String email) {
        final EmailSearchBean emailSearchBean = new EmailSearchBean();
        emailSearchBean.setEmailMatchToken(new SearchParam(email, MatchType.EXACT));
        emailSearchBean.setDeepCopy(false);
        List<EmailAddress> emailAddresses = userDataWebService.findEmailBeans(emailSearchBean, -1, -1);
        emailAddresses = (emailAddresses != null) ? emailAddresses : new LinkedList<EmailAddress>();

        final Set<User> userList = new HashSet<>();
        if (CollectionUtils.isNotEmpty(emailAddresses)) {
            for (final EmailAddress address : emailAddresses) {
                final User user = userDataWebService.getUserWithDependent(address.getParentId(), null, false);
                if (user != null) {
                    userList.add(user);
                }
            }
        }
        return userList;
    }

    private User getUserById(final String userId) {
        final UserSearchBean searchBean = new UserSearchBean();
        searchBean.setKey(userId);
        searchBean.setDeepCopy(false);
        final List<User> userList = userDataWebService.findBeans(searchBean, 0, 1);
        return (CollectionUtils.isNotEmpty(userList)) ? userList.get(0) : null;
    }

    private List<UserIdentityAnswer> getQuestionsForUser(final HttpServletRequest request, final String userId) {
        List<UserIdentityAnswer> retVal = null;
        if (StringUtils.isNotBlank(userId)) {
            final IdentityAnswerSearchBean searchBean = new IdentityAnswerSearchBean();
            searchBean.setDeepCopy(false);
            searchBean.setUserId(userId);
            try {
                retVal = challengeResponseService.findAnswerBeans(searchBean, getRequesterId(request), 0, Integer.MAX_VALUE);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return retVal;
    }

    private boolean sendTokenEmail(final HttpServletRequest request,
                                   final User user, final String principal, String managedSysId) throws URISyntaxException {
        boolean success = false;
        final String passwordResetToken = getPasswordResetToken(principal, managedSysId);
        if (StringUtils.isNotBlank(passwordResetToken)) {
            final NotificationRequest notificationRequest = new NotificationRequest();
            notificationRequest.setNotificationType(REQUEST_PASSWORD_RESET_NOTIFICATION);
            notificationRequest.setUserId(user.getId());

            final String baseURL = new StringBuilder(URIUtils.getBaseURI(request)).append(unlockQuestionsURL).toString();

            final List<NotificationParam> paramList = new LinkedList<NotificationParam>();
            paramList.add(new NotificationParam("TARGET_URL", baseURL));
            paramList.add(new NotificationParam("USER_ID", user.getId()));
            paramList.add(new NotificationParam("TOKEN", passwordResetToken));
            notificationRequest.setParamList(paramList);
            success = mailService.sendNotification(notificationRequest);
        } else {
            log.error(String.format("Password reset token was invalid (blank) for user: %s:%s", principal, (user != null) ? user.getId() : null));
        }
        return success;
    }

    private boolean sendLoginReminderEmail(final User user)
            throws URISyntaxException {
        boolean success = false;
        final LoginResponse response = loginServiceClient.getPrimaryIdentity(user.getId());
        if (response.isSuccess() && response.getPrincipal() != null) {
            final NotificationRequest notificationRequest = new NotificationRequest();
            notificationRequest.setNotificationType(REQUEST_LOGIN_REMINDER_NOTIFICATION);
            notificationRequest.setUserId(user.getId());

            final List<NotificationParam> paramList = new LinkedList<NotificationParam>();
            paramList.add(new NotificationParam("USER_ID", user.getId()));
            paramList.add(new NotificationParam("LOGIN", response.getPrincipal().getLogin()));
            notificationRequest.setParamList(paramList);
            success = mailService.sendNotification(notificationRequest);
        }

        return success;
    }

    private String getPasswordResetToken(final String principal, String managedSysId) {
        String retVal = null;
        final PasswordResetTokenRequest tokenRequest = new PasswordResetTokenRequest(principal, managedSysId);
        final PasswordResetTokenResponse tokenResponse = passwordService.generatePasswordResetToken(tokenRequest);
        if (tokenResponse != null && tokenResponse.isSuccess() && tokenResponse.getPasswordResetToken() != null) {
            retVal = tokenResponse.getPasswordResetToken();
        }
        return retVal;
    }

    private Login getLoginByUserId(final String userId, final String managedSysId) {
        LoginSearchBean lsb = new LoginSearchBean();
        lsb.setManagedSysId(managedSysId);
        lsb.setUserId(userId);
        List<Login> logins = loginServiceClient.findBeans(lsb, 0, 1);
        if (CollectionUtils.isNotEmpty(logins)) {
            return logins.get(0);
        } else return null;
    }
}
