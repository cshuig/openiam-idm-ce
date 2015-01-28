package org.openiam.ui.rest.api.mvc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openiam.base.AttributeOperationEnum;
import org.openiam.base.BaseConstants;
import org.openiam.base.ExtendController;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseCode;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.exception.EsbErrorToken;
import org.openiam.idm.searchbeans.OrganizationSearchBean;
import org.openiam.idm.searchbeans.UserSearchBean;
import org.openiam.idm.srvc.audit.constant.AuditAction;
import org.openiam.idm.srvc.audit.dto.IdmAuditLog;
import org.openiam.idm.srvc.auth.dto.Login;
import org.openiam.idm.srvc.auth.dto.ProvLoginStatusEnum;
import org.openiam.idm.srvc.auth.ws.LoginDataWebService;
import org.openiam.idm.srvc.auth.ws.LoginListResponse;
import org.openiam.idm.srvc.continfo.dto.Address;
import org.openiam.idm.srvc.continfo.dto.EmailAddress;
import org.openiam.idm.srvc.continfo.dto.Phone;
import org.openiam.idm.srvc.org.dto.Organization;
import org.openiam.idm.srvc.policy.dto.Policy;
import org.openiam.idm.srvc.policy.service.PolicyDataService;
import org.openiam.idm.srvc.pswd.dto.PasswordValidationResponse;
import org.openiam.idm.srvc.pswd.service.PasswordGenerator;
import org.openiam.idm.srvc.pswd.ws.PasswordWebService;
import org.openiam.idm.srvc.role.dto.Role;
import org.openiam.idm.srvc.user.dto.ProfilePicture;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.idm.srvc.user.dto.UserStatusEnum;
import org.openiam.idm.srvc.user.ws.UserResponse;
import org.openiam.provision.dto.*;
import org.openiam.provision.resp.ProvisionUserResponse;
import org.openiam.provision.service.ActionEventBuilder;
import org.openiam.provision.service.ProvisionServiceEventProcessor;
import org.openiam.ui.exception.ErrorMessageException;
import org.openiam.ui.rest.api.model.EditUserModel;
import org.openiam.ui.rest.api.model.ResetPasswordBean;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.model.SetPasswordToken;
import org.openiam.ui.web.mvc.AbstractPasswordController;
import org.openiam.ui.web.validator.UserInfoValidator;
import org.openiam.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

@Controller
public class ProvisioningRestController extends AbstractPasswordController {


    @Value("${org.openiam.ui.user.profile.picture.formats}")
    private String formats;
    @Value("${org.openiam.ui.user.profile.picture.maxWidth}")
    private int maxWidth;
    @Value("${org.openiam.ui.user.profile.picture.maxHeight}")
    private int maxHeight;
    @Value("${org.openiam.ui.user.profile.picture.maxSize}")
    private long maxSize;

    @Autowired
    private UserInfoValidator userInfoValidator;

    @Resource(name = "passwordServiceClient")
    private PasswordWebService passwordService;

    @Resource(name = "loginServiceClient")
    private LoginDataWebService loginDataWebService;


    @Resource(name = "policyServiceClient")
    protected PolicyDataService policyServiceClient;

    private static final String extendController = "/webconsole/EditUserController.groovy";

    private Map<ResponseCode, PasswordResponseHandler> passwordErrormap = new HashMap<ResponseCode, PasswordResponseHandler>();

    /**
     * Deletes user by userId
     *
     * @param request the current HttpServletRequest
     * @param userId  userId of the user to be deleted
     * @return ajax response
     * @see BasicAjaxResponse
     */
    @RequestMapping(value = "/prov/deleteUser", method = RequestMethod.POST)
    public
    @ResponseBody
    BasicAjaxResponse deleteUser(final HttpServletRequest request, final @RequestParam(value = "id", required = true) String userId) {
        return handleUserOperation(request, userId, UserOperation.DELETE);
    }

    /**
     * Removes user by userId
     *
     * @param request the current HttpServletRequest
     * @param userId  userId of the user to be removed
     * @return ajax response
     * @see BasicAjaxResponse
     */
    @RequestMapping(value = "/prov/removeUser", method = RequestMethod.POST)
    public
    @ResponseBody
    BasicAjaxResponse removeUser(final HttpServletRequest request, final @RequestParam(value = "id", required = true) String userId) {
        return handleUserOperation(request, userId, UserOperation.REMOVE);
    }

    /**
     * Enables user by userId
     *
     * @param request the current HttpServletRequest
     * @param userId  userId of the user to be enabled
     * @return ajax response
     * @see BasicAjaxResponse
     */
    @RequestMapping(value = "/prov/enableUser", method = RequestMethod.POST)
    public
    @ResponseBody
    BasicAjaxResponse enableUser(final HttpServletRequest request, final @RequestParam(value = "id", required = true) String userId) {
        return handleUserOperation(request, userId, UserOperation.ENABLE);
    }

    /**
     * Disables user by userId
     *
     * @param request the current HttpServletRequest
     * @param userId  userId of the user to be disabled
     * @return ajax response
     * @see BasicAjaxResponse
     */
    @RequestMapping(value = "/prov/disableUser", method = RequestMethod.POST)
    public
    @ResponseBody
    BasicAjaxResponse disableUser(final HttpServletRequest request, final @RequestParam(value = "id", required = true) String userId) {
        return handleUserOperation(request, userId, UserOperation.DISABLE);
    }

    /**
     * Activates user by userId
     *
     * @param request the current HttpServletRequest
     * @param userId  userId of the user to be activated
     * @return ajax response
     * @see BasicAjaxResponse
     */
    @RequestMapping(value = "/prov/activateUser", method = RequestMethod.POST)
    public
    @ResponseBody
    BasicAjaxResponse activateUser(final HttpServletRequest request, final @RequestParam(value = "id", required = true) String userId) {
        ErrorToken errorToken = null;
        SuccessToken successToken = null;
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        Response wsResponse = null;

        ActionEventBuilder eventBuilder = new ActionEventBuilder(
                userId,
                getRequesterId(request),
                ProvisionActionEnum.ACTIVATE);
        ProvisionActionEvent actionEvent = eventBuilder.build();
        Response response = provisionService.addEvent(actionEvent, ProvisionActionTypeEnum.PRE);
        if (ProvisionServiceEventProcessor.BREAK.equalsIgnoreCase((String) response.getResponseValue())) {
            return genAbortAjaxResponse(response, localeResolver.resolveLocale(request));
        }

        try {
            if (provisionServiceFlag) {
                final User user = userDataWebService.getUserWithDependent(userId, null, true);
                final ProvisionUser pUser = new ProvisionUser(user);
                pUser.setRequestorUserId(getRequesterId(request));
                pUser.setStatus(UserStatusEnum.ACTIVE);
                wsResponse = provisionService.modifyUser(pUser);

                IdmAuditLog idmAuditLog = new IdmAuditLog();
                idmAuditLog.setRequestorUserId(getRequesterId(request));
                idmAuditLog.setTargetUser(user.getId(), user.getLogin());
                idmAuditLog.setAction(AuditAction.USER_ACTIVE.value());
                idmAuditLog.setAuditDescription("User activation");
                auditLogService.addLog(idmAuditLog);
            } else {
                wsResponse = userDataWebService.activateUser(userId);
            }

            if (wsResponse.getStatus() == ResponseStatus.SUCCESS) {
                successToken = new SuccessToken(SuccessMessage.USER_ACTIVATED);
            } else {
                errorToken = new ErrorToken(Errors.CANNOT_ACTIVATE_USER);
            }

        } catch (Exception e) {
            errorToken = new ErrorToken(Errors.INTERNAL_ERROR);
            log.error("Exception while saving custom field", e);

        } finally {
            if (errorToken != null) {
                ajaxResponse.setStatus(500);
                errorToken.setMessage(messageSource.getMessage(
                        errorToken.getError().getMessageName(),
                        errorToken.getParams(),
                        localeResolver.resolveLocale(request)));
                ajaxResponse.addError(errorToken);
            } else if (successToken != null) {
                provisionService.addEvent(actionEvent, ProvisionActionTypeEnum.POST);
                ajaxResponse.setSuccessToken(successToken);
                ajaxResponse.setSuccessMessage(messageSource.getMessage(
                        successToken.getMessage().getMessageName(),
                        null,
                        localeResolver.resolveLocale(request)));
                ajaxResponse.setStatus(200);
            }
        }
        return ajaxResponse;
    }

    /**
     * Creates new or updates existing user
     *
     * @param request   the current HttpServletRequest
     * @param userModel Ajax object representing user
     * @return ajax response
     * @see EditUserModel
     * @see BasicAjaxResponse
     */
    @RequestMapping(value = "/prov/saveUser", method = RequestMethod.POST)
    public
    @ResponseBody
    BasicAjaxResponse saveUser(final HttpServletRequest request, @RequestBody @Valid final EditUserModel userModel) {
        ErrorToken errorToken = null;
        SuccessToken successToken = null;
        BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        Response wsResponse = null;

        ActionEventBuilder eventBuilder = new ActionEventBuilder(
                userModel.getId(),
                getRequesterId(request),
                ProvisionActionEnum.SAVE);
        ProvisionActionEvent actionEvent = eventBuilder.build();
        Response response = provisionService.addEvent(actionEvent, ProvisionActionTypeEnum.PRE);
        if (ProvisionServiceEventProcessor.BREAK.equalsIgnoreCase((String) response.getResponseValue())) {
            return genAbortAjaxResponse(response, localeResolver.resolveLocale(request));
        }

        try {
            wsResponse = saveUserInfo(request, userModel);
            if (wsResponse.getStatus() == ResponseStatus.SUCCESS) {
                successToken = new SuccessToken(SuccessMessage.USER_INFO_SAVED);
            } else {
                Errors error = Errors.CANNOT_SAVE_USER_INFO;
                if (wsResponse.getErrorCode() != null) {
                    switch (wsResponse.getErrorCode()) {
                        case SEND_EMAIL_FAILED:
                            error = Errors.USER_CREATED_BUT_EMAIL_NOT_SENT;
                            break;
                        case FAIL_PREPROCESSOR_CUSTOM_ERROR_1:
                            error = Errors.FAIL_PREPROCESSOR_CUSTOM_ERROR_1;
                            break;
                        case FAIL_PREPROCESSOR_CUSTOM_ERROR_2:
                            error = Errors.FAIL_PREPROCESSOR_CUSTOM_ERROR_2;
                            break;
                        case FAIL_PREPROCESSOR_CUSTOM_ERROR_3:
                            error = Errors.FAIL_PREPROCESSOR_CUSTOM_ERROR_3;
                            break;
                        case FAIL_PREPROCESSOR_CUSTOM_ERROR_4:
                            error = Errors.FAIL_PREPROCESSOR_CUSTOM_ERROR_4;
                            break;
                        case FAIL_PREPROCESSOR_CUSTOM_ERROR_5:
                            error = Errors.FAIL_PREPROCESSOR_CUSTOM_ERROR_5;
                            break;
                        case FAIL_PREPROCESSOR_CUSTOM_ERROR_6:
                            error = Errors.FAIL_PREPROCESSOR_CUSTOM_ERROR_6;
                            break;
                        case FAIL_PREPROCESSOR_CUSTOM_ERROR_7:
                            error = Errors.FAIL_PREPROCESSOR_CUSTOM_ERROR_7;
                            break;
                        case FAIL_PREPROCESSOR_CUSTOM_ERROR_8:
                            error = Errors.FAIL_PREPROCESSOR_CUSTOM_ERROR_8;
                            break;
                        case FAIL_PREPROCESSOR_CUSTOM_ERROR_9:
                            error = Errors.FAIL_PREPROCESSOR_CUSTOM_ERROR_9;
                            break;
                        case FAIL_PREPROCESSOR_CUSTOM_ERROR_10:
                            error = Errors.FAIL_PREPROCESSOR_CUSTOM_ERROR_10;
                            break;
                        case FAIL_POSTPROCESSOR_CUSTOM_ERROR_1:
                            error = Errors.FAIL_POSTPROCESSOR_CUSTOM_ERROR_1;
                            break;
                        case FAIL_POSTPROCESSOR_CUSTOM_ERROR_2:
                            error = Errors.FAIL_POSTPROCESSOR_CUSTOM_ERROR_2;
                            break;
                        case FAIL_POSTPROCESSOR_CUSTOM_ERROR_3:
                            error = Errors.FAIL_POSTPROCESSOR_CUSTOM_ERROR_3;
                            break;
                        case FAIL_POSTPROCESSOR_CUSTOM_ERROR_4:
                            error = Errors.FAIL_POSTPROCESSOR_CUSTOM_ERROR_4;
                            break;
                        case FAIL_POSTPROCESSOR_CUSTOM_ERROR_5:
                            error = Errors.FAIL_POSTPROCESSOR_CUSTOM_ERROR_5;
                            break;
                        case FAIL_POSTPROCESSOR_CUSTOM_ERROR_6:
                            error = Errors.FAIL_POSTPROCESSOR_CUSTOM_ERROR_6;
                            break;
                        case FAIL_POSTPROCESSOR_CUSTOM_ERROR_7:
                            error = Errors.FAIL_POSTPROCESSOR_CUSTOM_ERROR_7;
                            break;
                        case FAIL_POSTPROCESSOR_CUSTOM_ERROR_8:
                            error = Errors.FAIL_POSTPROCESSOR_CUSTOM_ERROR_8;
                            break;
                        case FAIL_POSTPROCESSOR_CUSTOM_ERROR_9:
                            error = Errors.FAIL_POSTPROCESSOR_CUSTOM_ERROR_9;
                            break;
                        case FAIL_POSTPROCESSOR_CUSTOM_ERROR_10:
                            error = Errors.FAIL_POSTPROCESSOR_CUSTOM_ERROR_10;
                            break;
                        default:
                            error = Errors.INTERNAL_ERROR;
                            break;
                    }
                }
                errorToken = new ErrorToken(error);
            }

        } catch (Exception e) {
            errorToken = new ErrorToken(Errors.INTERNAL_ERROR);
            log.error("Exception while saving custom field", e);

        } finally {
            if (errorToken != null) {
                ajaxResponse.setStatus(500);
                errorToken.setMessage(messageSource.getMessage(
                        errorToken.getError().getMessageName(),
                        errorToken.getParams(),
                        localeResolver.resolveLocale(request)));
                ajaxResponse.addError(errorToken);
            } else {
                if (wsResponse != null && wsResponse.getResponseValue() != null) {
                    if (wsResponse.getResponseValue() instanceof String) {
                        final String userId = (String) wsResponse.getResponseValue();
                        if (StringUtils.isBlank(userModel.getId())) {
                            ajaxResponse.setRedirectURL(new StringBuilder("editUser.html?id=").append(userId).toString());
                        }
                    }
                }
                if (successToken != null) {
                    provisionService.addEvent(actionEvent, ProvisionActionTypeEnum.POST);
                    ajaxResponse.setSuccessToken(successToken);
                    final String userId = (String) wsResponse.getResponseValue();
                    ajaxResponse = setUserStatusInfoBuilder(request, ajaxResponse, userId, successToken);
                }
                ajaxResponse.addContextValues("checkStatusInProgress", true);
                ajaxResponse.setStatus(200);
            }
        }

        return ajaxResponse;
    }

    @RequestMapping(value = "/prov/resyncPassword", method = RequestMethod.POST)
    public
    @ResponseBody
    BasicAjaxResponse resyncPassword(final HttpServletRequest request, @RequestBody ResetPasswordBean passwordBean) throws IOException {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        List<ErrorToken> errorTokenList = new LinkedList<>();
        Response wsResponse = null;
        SetPasswordToken token = null;
        ActionEventBuilder eventBuilder = new ActionEventBuilder(
                passwordBean.getUserId(),
                getRequesterId(request),
                ProvisionActionEnum.RESYNC_PASSWORD);
        ProvisionActionEvent actionEvent = eventBuilder.build();
        Response response = provisionService.addEvent(actionEvent, ProvisionActionTypeEnum.PRE);
        if (ProvisionServiceEventProcessor.BREAK.equalsIgnoreCase((String) response.getResponseValue())) {
            return genAbortAjaxResponse(response, localeResolver.resolveLocale(request));
        }

        try {
            if (provisionServiceFlag) {
                UserSearchBean usb = new UserSearchBean();
                usb.setKey(passwordBean.getUserId());
                List<User> u = userDataWebService.findBeans(usb, -1, -1);
                String oiamPassword = null;
                if (CollectionUtils.isNotEmpty(u) && CollectionUtils.isNotEmpty(u.get(0).getPrincipalList())) {
                    for (Login l : u.get(0).getPrincipalList()) {
                        if (defaultManagedSysId.equals(l.getManagedSysId())) {
                            Response res = loginDataWebService.decryptPassword(l.getUserId(), l.getPassword());
                            if (res.isSuccess()) {
                                oiamPassword = (String) res.getResponseValue();
                            } else {
                                errorTokenList.add(new ErrorToken(Errors.INVALID_LOGIN));
                            }
                            break;
                        }
                    }

                    if (StringUtils.isNotBlank(oiamPassword)) {
                        final PasswordSync pswdSync = new PasswordSync();
                        pswdSync.setManagedSystemId(passwordBean.getManagedSystemId());
                        pswdSync.setPassword(oiamPassword);
                        pswdSync.setUserId(passwordBean.getUserId());
                        pswdSync.setRequestClientIP(request.getRemoteHost());
                        pswdSync.setRequestorLogin(cookieProvider.getPrincipal(request));
                        pswdSync.setRequestorId(getRequesterId(request));
                        pswdSync.setSendPasswordToUser(passwordBean.getNotifyUserViaEmail());
                        final Response setPasswordResponse = provisionService.resetPassword(pswdSync);
                        if (ResponseStatus.SUCCESS != setPasswordResponse.getStatus()) {
                            errorTokenList.add(new ErrorToken(Errors.CHANGE_PASSWORD_FAILED));
                        }
                    }
                }
            }
        } catch (Throwable e) {
            errorTokenList.add(new ErrorToken(Errors.INTERNAL_ERROR));
            log.error("Exception while resyncing the password", e);
        } finally {
            if (CollectionUtils.isNotEmpty(errorTokenList)) {
                ajaxResponse.setStatus(500);
                ajaxResponse.addErrors(errorTokenList);
            } else {
                provisionService.addEvent(eventBuilder.build(), ProvisionActionTypeEnum.POST);
                ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.PASSWORD_RESYNC));
                ajaxResponse.setStatus(200);
                ajaxResponse.setRedirectURL(String.format("editUser.html?id=%s", passwordBean.getUserId()));
            }
            ajaxResponse.process(localeResolver, messageSource, request);
        }
        return ajaxResponse;
    }

    /**
     * Resets password for user
     *
     * @param request      - the current HttpServletRequest
     * @param passwordBean - Ajax password object
     * @return ajax response
     * @throws IOException
     * @see ResetPasswordBean
     * @see BasicAjaxResponse
     */
    @RequestMapping(value = "/prov/resetPassword", method = RequestMethod.POST)
    public
    @ResponseBody
    BasicAjaxResponse resetPassword(final HttpServletRequest request, @RequestBody @Valid ResetPasswordBean passwordBean) throws IOException {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();

        List<ErrorToken> errorTokenList = new LinkedList<>();
        Response wsResponse = null;
        SetPasswordToken token = null;

        ActionEventBuilder eventBuilder = new ActionEventBuilder(
                passwordBean.getUserId(),
                getRequesterId(request),
                ProvisionActionEnum.RESET_PASSWORD);
        ProvisionActionEvent actionEvent = eventBuilder.build();
        Response response = provisionService.addEvent(actionEvent, ProvisionActionTypeEnum.PRE);
        if (ProvisionServiceEventProcessor.BREAK.equalsIgnoreCase((String) response.getResponseValue())) {
            return genAbortAjaxResponse(response, localeResolver.resolveLocale(request));
        }

        try {
            if (passwordBean.getAutoGeneratePassword()) {
                Policy policy = passwordService.getPasswordPolicy(passwordBean.getPrincipal(), defaultManagedSysId);
                passwordBean.setPassword(PasswordGenerator.generatePassword(policy));
            }
            Policy policy = this.getAuthentificationPolicy();
            String internalLogin = this.buildLoginAccordingAuthPolicy(policy, passwordBean.getPrincipal());
            String managedSystemId = this.getAuthentificationManagedSystem(policy);

            token = validatePassword(internalLogin, managedSystemId, passwordBean.getPassword(), false);
            if (token.hasErrors()) {
                errorTokenList.addAll(token.getErrorList());
            } else {
                // try to reset
                if (provisionServiceFlag) {
                    final PasswordSync pswdSync = new PasswordSync();
                    pswdSync.setManagedSystemId(passwordBean.getManagedSystemId());
                    pswdSync.setPassword(passwordBean.getPassword());
                    pswdSync.setUserId(passwordBean.getUserId());
                    pswdSync.setRequestClientIP(request.getRemoteHost());
                    pswdSync.setRequestorLogin(cookieProvider.getPrincipal(request));
                    pswdSync.setRequestorId(getRequesterId(request));
                    pswdSync.setSendPasswordToUser(passwordBean.getNotifyUserViaEmail());

                    final Response setPasswordResponse = provisionService.resetPassword(pswdSync);

                    if (ResponseStatus.SUCCESS != setPasswordResponse.getStatus()) {
                        errorTokenList.add(new ErrorToken(Errors.CHANGE_PASSWORD_FAILED));
                    }

                } else {

                    String encPassword = null;
                    wsResponse = loginDataWebService.encryptPassword(passwordBean.getUserId(), passwordBean.getPassword());

                    if (wsResponse.getStatus() != ResponseStatus.SUCCESS) {
                        errorTokenList.add(new ErrorToken(Errors.PASSWORD_ENCRYPTION_FAIL));
                    }
                    encPassword = (String) wsResponse.getResponseValue();

                    wsResponse = loginDataWebService.resetPasswordAndNotifyUser(passwordBean.getPrincipal(), defaultManagedSysId,
                            encPassword, passwordBean.getNotifyUserViaEmail());

                    IdmAuditLog idmAuditLog = new IdmAuditLog();
                    idmAuditLog.setRequestorUserId(getRequesterId(request));
                    idmAuditLog.setTargetUser(passwordBean.getUserId(), passwordBean.getPrincipal());
                    idmAuditLog.setAction(AuditAction.USER_RESETPASSWORD.value());
                    idmAuditLog.setAuditDescription("User reset password");
                    if (wsResponse.getStatus() == ResponseStatus.SUCCESS) {
                        idmAuditLog.succeed();
                    } else {
                        errorTokenList.add(new ErrorToken(Errors.CHANGE_PASSWORD_FAILED));
                        idmAuditLog.fail();
                        idmAuditLog.setFailureReason(wsResponse.getErrorText());
                    }
                    auditLogService.addLog(idmAuditLog);

                }
            }

        } catch (Throwable e) {
            errorTokenList.add(new ErrorToken(Errors.INTERNAL_ERROR));
            log.error("Exception while resetting the password", e);

        } finally {
            if (CollectionUtils.isNotEmpty(errorTokenList)) {
                ajaxResponse.setStatus(500);
                ajaxResponse.addErrors(errorTokenList);
                if (token != null) {
                    ajaxResponse.setPossibleErrors(token.getRules());
                }
            } else {
                provisionService.addEvent(actionEvent, ProvisionActionTypeEnum.PRE);
                ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.PASSWORD_RESETED));
                ajaxResponse.setStatus(200);
                ajaxResponse.setRedirectURL(String.format("editUser.html?id=%s", passwordBean.getUserId()));
            }
            ajaxResponse.process(localeResolver, messageSource, request);
        }

        return ajaxResponse;
    }

    @RequestMapping(value = "/prov/checkStatus", method = RequestMethod.POST)
    public
    @ResponseBody
    BasicAjaxResponse checkStatus(final HttpServletRequest request, final @RequestParam(value = "id", required = true) String userId) {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        SuccessToken successToken = new SuccessToken(SuccessMessage.USER_INFO_SAVED);
        ajaxResponse.setSuccessToken(successToken);
        setUserStatusInfoBuilder(request, ajaxResponse, userId, successToken);

        ajaxResponse.setStatus(200);
        return ajaxResponse;
    }

    private BasicAjaxResponse setUserStatusInfoBuilder(HttpServletRequest request, BasicAjaxResponse ajaxResponse, String userId, SuccessToken successToken) {
        String saveUserLabel = messageSource.getMessage(
                successToken.getMessage().getMessageName(),
                null,
                localeResolver.resolveLocale(request));
        String provMessage = messageSource.getMessage(
                SuccessMessage.USER_INFOPROV_SAVED.getMessageName(),
                null,
                localeResolver.resolveLocale(request));

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(saveUserLabel);
        stringBuilder.append("<br/>");
        stringBuilder.append(provMessage);
        stringBuilder.append(":");
        LoginListResponse loginListResponse = loginServiceClient.getLoginByUser(userId);
        int successCounter = 0;
        for (Login login : loginListResponse.getPrincipalList()) {
            String mngSysName = managedSysServiceClient.getManagedSys(login.getManagedSysId()).getName();

            stringBuilder.append("<br/>").append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;").append(mngSysName).append(": ").append(login.getProvStatus() != null ? login.getProvStatus().getValue() : "NONE");
            if (login.getProvStatus() != ProvLoginStatusEnum.PENDING_CREATE
                    && login.getProvStatus() != ProvLoginStatusEnum.PENDING_UPDATE
                    && login.getProvStatus() != ProvLoginStatusEnum.PENDING_DELETE
                    && login.getProvStatus() != ProvLoginStatusEnum.PENDING_DISABLE
                    && login.getProvStatus() != ProvLoginStatusEnum.PENDING_ENABLE
                    && login.getProvStatus() != null) {
                successCounter++;
            }
        }
        ajaxResponse.setSuccessMessage(stringBuilder.toString());
        // We have to define the stop behaviour
        //  ajaxResponse.addContextValues("checkStatusInProgress", successCounter==loginListResponse.getPrincipalList().size());
        ajaxResponse.addContextValues("checkStatusInProgress", true);
        ajaxResponse.addContextValues("userId", userId);
        return ajaxResponse;
    }

    /**
     * Resets user account by userId
     *
     * @param request - the current HttpServletRequest
     * @param userId  userId of the user to be reset
     * @return ajax response
     * @see ResetPasswordBean
     * @see BasicAjaxResponse
     */
    @RequestMapping(value = "/prov/resetUser", method = RequestMethod.POST)
    public
    @ResponseBody
    BasicAjaxResponse resetUser(final HttpServletRequest request, final @RequestParam(value = "id", required = true) String userId) {
        ErrorToken errorToken = null;
        SuccessToken successToken = null;
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        Response wsResponse = null;

        ActionEventBuilder eventBuilder = new ActionEventBuilder(
                userId,
                getRequesterId(request),
                ProvisionActionEnum.RESET_ACCOUNT);

        ProvisionActionEvent actionEvent = eventBuilder.build();
        Response response = provisionService.addEvent(actionEvent, ProvisionActionTypeEnum.PRE);
        if (ProvisionServiceEventProcessor.BREAK.equalsIgnoreCase((String) response.getResponseValue())) {
            return genAbortAjaxResponse(response, localeResolver.resolveLocale(request));
        }

        try {
            if (provisionServiceFlag) {
                final User user = userDataWebService.getUserWithDependent(userId, null, true);
                final ProvisionUser pUser = new ProvisionUser(user);
                pUser.setRequestorUserId(getRequesterId(request));
                pUser.setDateITPolicyApproved(BaseConstants.NULL_DATE);
                pUser.setClaimDate(BaseConstants.NULL_DATE);
                pUser.setStatus(UserStatusEnum.PENDING_INITIAL_LOGIN);
                pUser.setSecondaryStatus(null);
                wsResponse = provisionService.modifyUser(pUser);

                if (wsResponse.isSuccess()) {
                    final PasswordSync pswdSync = new PasswordSync();
                    pswdSync.setManagedSystemId(null);
                    pswdSync.setPassword(PasswordGenerator.generatePassword(16));
                    pswdSync.setUserId(userId);
                    pswdSync.setRequestClientIP(request.getRemoteHost());
                    pswdSync.setRequestorLogin(cookieProvider.getPrincipal(request));
                    pswdSync.setRequestorId(getRequesterId(request));
                    final Response setPasswordResponse = provisionService.resetPassword(pswdSync);
                    final Response resetQuestionsResponse = challengeResponseService.resetQuestionsForUser(userId);
                }
            } else {

                String login = loginDataWebService.getPrimaryIdentity(userId).getPrincipal().getLogin();
                // Generate random password
                wsResponse = loginDataWebService.encryptPassword(userId, PasswordGenerator.generatePassword(16));
                if (wsResponse.getStatus() == ResponseStatus.SUCCESS) {
                    wsResponse = userDataWebService.resetUser(userId);
                    String encPassword = (String) wsResponse.getResponseValue();
                    if (wsResponse.getStatus() == ResponseStatus.SUCCESS) {
                        wsResponse = loginDataWebService.resetPassword(login, defaultManagedSysId, encPassword);
                    }
                }
            }

            if (wsResponse.getStatus() == ResponseStatus.SUCCESS) {
                successToken = new SuccessToken(SuccessMessage.USER_ACCOUNT_RESET);
            } else {
                errorToken = new ErrorToken(Errors.CANNOT_RESET_USER);
            }

        } catch (Exception e) {
            errorToken = new ErrorToken(Errors.INTERNAL_ERROR);
            log.error("Exception while resetting user account", e);

        } finally {
            if (errorToken != null) {
                ajaxResponse.setStatus(500);
                errorToken.setMessage(messageSource.getMessage(
                        errorToken.getError().getMessageName(),
                        errorToken.getParams(),
                        localeResolver.resolveLocale(request)));
                ajaxResponse.addError(errorToken);
            } else if (successToken != null) {
                provisionService.addEvent(actionEvent, ProvisionActionTypeEnum.POST);
                ajaxResponse.setSuccessToken(successToken);
                ajaxResponse.setSuccessMessage(messageSource.getMessage(
                        successToken.getMessage().getMessageName(),
                        null,
                        localeResolver.resolveLocale(request)));
                ajaxResponse.setStatus(200);
            }
        }
        return ajaxResponse;
    }

    private Response saveUserInfo(HttpServletRequest request, EditUserModel userModel) throws Exception {
        String requesterId = getRequesterId(request);
        Response wsResult = new Response();
        boolean isNew = StringUtils.isBlank(userModel.getId());
        User supervisorUser = null;
        User user = mapper.mapToObject(userModel, User.class);
        setMetaDataTypes(user, userModel);
        setOrganizationIds(user, userModel, requesterId, isNew);
        setContactInfo(user, userModel, isNew);
        setUserRoles(user, userModel, requesterId, isNew);
        if (isNew) {
            if (StringUtils.isNotBlank(userModel.getSupervisorId())) {
                supervisorUser = new User(userModel.getSupervisorId());
                supervisorUser.setOperation(AttributeOperationEnum.ADD);
            }
            user.setId(null);
            user.setCreateDate(new Date());
            user.setCreatedBy(requesterId);
            user.setStatus(UserStatusEnum.PENDING_INITIAL_LOGIN);

        } else {

        }

        wsResult.setStatus(ResponseStatus.FAILURE);
        User returnedUser;
        if (provisionServiceFlag) {
            ExtendController extCmd = (ExtendController) scriptRunner.instantiateClass(null, extendController);
            Map<String, Object> controllerObj = new HashMap<String, Object>();

            ProvisionUser pUser = new ProvisionUser(user);
            pUser.setRequestorUserId(getRequesterId(request));
            if (isNew && supervisorUser != null) {
                pUser.addSuperior(supervisorUser);
            }
            setAuditInfo(pUser, request);
            controllerObj.put("user", pUser);
            if (isNew) {
                pUser.setProvisionOnStartDate(userModel.getProvisionOnStartDate());
                pUser.setEmailCredentialsToNewUsers(userModel.getNotifyUserViaEmail());
                pUser.setEmailCredentialsToSupervisor(userModel.getNotifySupervisorViaEmail());
                // provision user
                if (extCmd != null) {
                    extCmd.pre("ADD", controllerObj, null);
                }
                wsResult = provisionService.addUser(pUser);
                returnedUser = ((ProvisionUserResponse) wsResult).getUser();
            } else {
                if (extCmd != null) {
                    extCmd.pre("MODIFY", controllerObj, null);
                }

                wsResult = provisionService.modifyUser(pUser);

                //wsResult.setStatus(ResponseStatus.SUCCESS);
                returnedUser = pUser;
            }
        } else {

            if (supervisorUser != null) {

            }

            wsResult = userDataWebService.saveUserInfo(user, (supervisorUser != null) ? supervisorUser.getId() : null);
            returnedUser = ((UserResponse) wsResult).getUser();
        }
        if (returnedUser != null && wsResult.getStatus() == ResponseStatus.SUCCESS) {
            wsResult.setResponseValue(returnedUser.getId());
        }
        return wsResult;
    }

    @RequestMapping("/images/{id}")
    public void getImage(HttpServletRequest request,
                         final HttpServletResponse response,
                         @PathVariable String id) throws IOException {
        if (StringUtils.isNotBlank(id)) {
            ProfilePicture profilePicture = userDataWebService.getProfilePictureByUserId(id, getRequesterId(request));
            if (profilePicture != null) {
                ImageInputStream iis = ImageIO.createImageInputStream(new ByteArrayInputStream(profilePicture.getPicture()));
                Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
                if (iter.hasNext()) {
                    ImageReader ir = iter.next();
                    String format = ir.getFormatName();
                    List<String> contentTypes = Arrays.asList("jpeg", "jpg", "png", "gif");
                    if (StringUtils.isNotBlank(formats)) {
                        contentTypes = Arrays.asList(formats.split("\\s*,\\s*"));
                    }
                    if (contentTypes.contains(format.toLowerCase())) {
                        response.setContentType("image/" + format.toLowerCase());
                    }
                    BufferedImage bufferedImage = ImageIO.read(iis);
                    ImageIO.write(bufferedImage, format, response.getOutputStream());
                }
            }
        }
    }

    @RequestMapping(value = "/prov/deleteProfilePic", method = RequestMethod.POST)
    public
    @ResponseBody
    BasicAjaxResponse deleteProfilePic(HttpServletRequest request, HttpServletResponse response,
                                       @RequestParam(value = "id", required = true) final String userId) throws IOException {

        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();

        Response res = userDataWebService.deleteProfilePictureByUserId(userId, getRequesterId(request));
        if (res.isSuccess()) {
            ajaxResponse.setStatus(200);
            SuccessToken successToken = new SuccessToken(SuccessMessage.IMAGE_DELETE_SUCCESS);
            ajaxResponse.setSuccessToken(successToken);
            ajaxResponse.setSuccessMessage(messageSource.getMessage(
                    successToken.getMessage().getMessageName(),
                    null,
                    localeResolver.resolveLocale(request)));
        } else {
            ajaxResponse.setStatus(500);
            if (CollectionUtils.isNotEmpty(res.getErrorTokenList())) {
                for (EsbErrorToken e : res.getErrorTokenList()) {
                    ErrorToken errorToken = new ErrorToken();
                    errorToken.setValidationError(e.getMessage());
                    if (e.getLengthConstraint() != null) {
                        errorToken.setParams(new Object[]{e.getLengthConstraint()});
                    }
                    ajaxResponse.addError(errorToken);
                }
            } else {
                ErrorToken errorToken = new ErrorToken(Errors.INTERNAL_ERROR);
                errorToken.setMessage(messageSource.getMessage(
                        errorToken.getError().getMessageName(),
                        errorToken.getParams(),
                        localeResolver.resolveLocale(request)));
                ajaxResponse.addError(errorToken);
            }
        }

        return ajaxResponse;
    }

    @RequestMapping(value = "/prov/addProfilePic", method = RequestMethod.POST)
    public
    @ResponseBody
    BasicAjaxResponse addProfilePic(MultipartHttpServletRequest request, HttpServletResponse response,
                                    @RequestParam(value = "id", required = true) final String userId,
                                    @RequestPart(value = "pic", required = false) final MultipartFile pic) throws IOException {

        String requesterId = getRequesterId(request);

        final User user = userDataWebService.getUserWithDependent(userId, requesterId, true);

        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse(500);

        if (user != null) {
            List<ErrorToken> errors = validateProfilePic(pic, request);
            if (CollectionUtils.isNotEmpty(errors)) {
                ajaxResponse.setStatus(500);
                ajaxResponse.setErrorList(errors);

            } else {
                ProfilePicture profilePic = userDataWebService.getProfilePictureByUserId(user.getId(), requesterId);
                if (profilePic == null) {
                    profilePic = new ProfilePicture();
                }
                profilePic.setUser(user);
                profilePic.setName(pic.getName());
                profilePic.setPicture(pic.getBytes());
                Response res = userDataWebService.saveProfilePicture(profilePic, requesterId);
                if (res.isSuccess()) {
                    ajaxResponse.setStatus(200);
                    SuccessToken successToken = new SuccessToken(SuccessMessage.IMAGE_UPLOAD_SUCCESS);
                    ajaxResponse.setSuccessToken(successToken);
                    ajaxResponse.setSuccessMessage(messageSource.getMessage(
                            successToken.getMessage().getMessageName(),
                            null,
                            localeResolver.resolveLocale(request)));
                } else {
                    ajaxResponse.setStatus(500);
                    if (CollectionUtils.isNotEmpty(res.getErrorTokenList())) {
                        for (EsbErrorToken e : res.getErrorTokenList()) {
                            ErrorToken errorToken = new ErrorToken();
                            errorToken.setValidationError(e.getMessage());
                            if (e.getLengthConstraint() != null) {
                                errorToken.setParams(new Object[]{e.getLengthConstraint()});
                            }
                            ajaxResponse.addError(errorToken);
                        }
                    } else {
                        ErrorToken errorToken = new ErrorToken(Errors.INTERNAL_ERROR);
                        errorToken.setMessage(messageSource.getMessage(
                                errorToken.getError().getMessageName(),
                                errorToken.getParams(),
                                localeResolver.resolveLocale(request)));
                        ajaxResponse.addError(errorToken);
                    }
                }
            }
        }

        return ajaxResponse;
    }

    private List<ErrorToken> validateProfilePic(MultipartFile pic, HttpServletRequest request) throws IOException {

        List<ErrorToken> errors = new ArrayList<ErrorToken>();
        ImageInputStream iis = ImageIO.createImageInputStream(new ByteArrayInputStream(pic.getBytes()));
        Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
        ErrorToken errorToken;
        if (iter.hasNext()) {
            ImageReader ir = iter.next();
            String format = ir.getFormatName();
            BufferedImage bufferedImage = ImageIO.read(iis);

            List<String> contentTypes = Arrays.asList("jpeg", "jpg", "png", "gif");
            if (StringUtils.isNotBlank(formats)) {
                contentTypes = Arrays.asList(formats.split("\\s*,\\s*"));
            }
            if (!contentTypes.contains(format.toLowerCase())) {
                errorToken = new ErrorToken(Errors.IMAGE_INVALID_TYPE);
                errorToken.setMessage(messageSource.getMessage(
                        errorToken.getError().getMessageName(),
                        errorToken.getParams(),
                        localeResolver.resolveLocale(request)));
                errors.add(errorToken);
            }
            if ((bufferedImage.getWidth() > maxWidth) || (bufferedImage.getHeight() > maxHeight)) {
                errorToken = new ErrorToken(Errors.IMAGE_INVALID_DIMENSIONS);
                errorToken.setParams(new Object[]{maxWidth, maxHeight});
                errorToken.setMessage(messageSource.getMessage(
                        errorToken.getError().getMessageName(),
                        errorToken.getParams(),
                        localeResolver.resolveLocale(request)));
                errors.add(errorToken);
            }
            if ((pic.getSize() > maxSize * 1024)) {
                errorToken = new ErrorToken(Errors.IMAGE_INVALID_SIZE);
                errorToken.setParams(new Object[]{maxWidth, maxHeight});
                errorToken.setMessage(messageSource.getMessage(
                        errorToken.getError().getMessageName(),
                        errorToken.getParams(),
                        localeResolver.resolveLocale(request)));
                errors.add(errorToken);
            }

        } else {
            errorToken = new ErrorToken(Errors.IMAGE_INVALID);
            errorToken.setMessage(messageSource.getMessage(
                    errorToken.getError().getMessageName(),
                    errorToken.getParams(),
                    localeResolver.resolveLocale(request)));
            errors.add(errorToken);
        }
        return errors;
    }

    private void setMetaDataTypes(User user, EditUserModel userModel) {
        if (StringUtils.isNotBlank(userModel.getMetadataTypeId())) {
            user.setMdTypeId(userModel.getMetadataTypeId());
        }
//        if(StringUtils.isNotBlank(userModel.getJobCodeId())){
//            MetadataType jc = new MetadataType();
//            jc.setId(userModel.getJobCodeId());
//            user.setJobCode(jc);
//        }
    }

    private void setOrganizationIds(User user, EditUserModel userModel, String requesterId, boolean isNewUser) {
        List<Organization> orgs = new ArrayList<Organization>();
        if (CollectionUtils.isNotEmpty(userModel.getOrganizationIds())) {
            OrganizationSearchBean searchBean = new OrganizationSearchBean();
            searchBean.setKeys(userModel.getOrganizationIds());
            orgs = organizationDataService.findBeansLocalized(searchBean, requesterId, -1, -1, getCurrentLanguage());
        }
        for (Organization o : orgs) {
            if (isNewUser || !user.getAffiliations().contains(o)) {
                o.setOperation(AttributeOperationEnum.ADD);
                user.getAffiliations().add(o);
            }
        }
        if (!isNewUser) {
            for (Organization o : user.getAffiliations()) {
                if (!orgs.contains(o)) {
                    o.setOperation(AttributeOperationEnum.DELETE);
                }
            }
        }
    }

    private void setContactInfo(User user, EditUserModel userModel, boolean isNew) {
        if (userModel.getEmail() != null) {
            Set<EmailAddress> emailAddressList = new HashSet<EmailAddress>();
            EmailAddress ea = new EmailAddress();
            ea.setEmailAddress(userModel.getEmail().getEmail());
            ea.setIsDefault(true);
            ea.setIsActive(true);
            ea.setMetadataTypeId(userModel.getEmail().getTypeId());
            if (isNew) {
                ea.setOperation(AttributeOperationEnum.ADD);
            }
            emailAddressList.add(ea);
            user.setEmailAddresses(emailAddressList);
        }

        if (userModel.getAddress() != null) {
            Set<Address> addressList = new HashSet<Address>();
            Address a = new Address();

            a.setAddress1(userModel.getAddress().getAddress1());
            a.setAddress2(userModel.getAddress().getAddress2());
            a.setCity(userModel.getAddress().getCity());
            a.setState(userModel.getAddress().getState());
            a.setPostalCd(userModel.getAddress().getPostalCd());
            a.setBldgNumber(userModel.getAddress().getBldgNumber());
            a.setIsDefault(true);
            a.setIsActive(true);
            a.setMetadataTypeId(userModel.getAddress().getTypeId());
            if (isNew) {
                a.setOperation(AttributeOperationEnum.ADD);
            }
            addressList.add(a);
            user.setAddresses(addressList);
        }

        if (userModel.getPhone() != null) {
            Set<Phone> phoneList = new HashSet<Phone>();
            Phone ph = new Phone();
            ph.setAreaCd(userModel.getPhone().getAreaCd());
            ph.setPhoneNbr(userModel.getPhone().getPhoneNbr());
            ph.setPhoneExt(userModel.getPhone().getPhoneExt());
            ph.setIsDefault(true);
            ph.setIsActive(true);
            ph.setMetadataTypeId(userModel.getPhone().getTypeId());
            if (isNew) {
                ph.setOperation(AttributeOperationEnum.ADD);
            }
            phoneList.add(ph);
            user.setPhones(phoneList);
        }
    }

    private void setUserRoles(User user, EditUserModel userModel, String requesterId, boolean isNew) {
        if (StringUtils.isNotEmpty(userModel.getRoleId())) {
            Role role = roleServiceClient.getRoleLocalized(userModel.getRoleId(), requesterId, getCurrentLanguage());
            if (isNew) {
                role.setOperation(AttributeOperationEnum.ADD);
            }
            user.getRoles().add(role);
        }
    }

    private void setAuditInfo(ProvisionUser pUser, HttpServletRequest request) {
        pUser.setRequestClientIP(request.getRemoteHost());
        pUser.setRequestorLogin(cookieProvider.getPrincipal(request));
    }

    private BasicAjaxResponse handleUserOperation(HttpServletRequest request, String userId, UserOperation operation) {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        ErrorToken errorToken = null;
        SuccessToken successToken = null;
        ActionEventBuilder eventBuilder = new ActionEventBuilder(userId, getRequesterId(request), null);
        switch (operation) {
            case DELETE:
                eventBuilder.setActionType(ProvisionActionEnum.DEACTIVATE);
                successToken = new SuccessToken(SuccessMessage.USER_DELETED);
                break;
            case REMOVE:
                eventBuilder.setActionType(ProvisionActionEnum.DELETE);
                successToken = new SuccessToken(SuccessMessage.USER_REMOVED);
                break;
            case ENABLE:
                eventBuilder.setActionType(ProvisionActionEnum.ENABLE);
                successToken = new SuccessToken(SuccessMessage.USER_ENABLED);
                break;
            case DISABLE:
                eventBuilder.setActionType(ProvisionActionEnum.DISABLE);
                successToken = new SuccessToken(SuccessMessage.USER_DISABLED);
                break;
        }
        ProvisionActionEvent actionEvent = eventBuilder.build();
        Response response = provisionService.addEvent(actionEvent, ProvisionActionTypeEnum.PRE);
        if (ProvisionServiceEventProcessor.BREAK.equalsIgnoreCase((String) response.getResponseValue())) {
            return genAbortAjaxResponse(response, localeResolver.resolveLocale(request));
        }

        try {
            Errors error = performUserOperation(request, userId, operation);
            if (error != null) {
                throw new ErrorMessageException(error);
            }
        } catch (ErrorMessageException e) {
            errorToken = new ErrorToken(e.getError());
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            errorToken = new ErrorToken(Errors.INTERNAL_ERROR);
            log.error(e.getMessage(), e);

        } finally {
            if (errorToken == null) {
                ajaxResponse.setStatus(200);
                ajaxResponse.setSuccessToken(successToken);
                ajaxResponse.setSuccessMessage(messageSource.getMessage(
                        successToken.getMessage().getMessageName(),
                        null,
                        localeResolver.resolveLocale(request)));
                if (operation == UserOperation.DELETE || operation == UserOperation.REMOVE) {
                    ajaxResponse.setRedirectURL("users.html");
                }
            } else {
                provisionService.addEvent(actionEvent, ProvisionActionTypeEnum.POST);
                ajaxResponse.setStatus(500);
                errorToken.setMessage(messageSource.getMessage(
                        errorToken.getError().getMessageName(),
                        errorToken.getParams(),
                        localeResolver.resolveLocale(request)));
                ajaxResponse.addError(errorToken);
            }
        }
        return ajaxResponse;
    }

    private Errors performUserOperation(HttpServletRequest request, String userId, UserOperation operation) throws Exception {
        Errors retVal = null;
        Response wsResponse = null;
        String requesterId = getRequesterId(request);
        final User user = userDataWebService.getUserWithDependent(userId, requesterId, true);
        final ProvisionUser pUser = new ProvisionUser(user);
        IdmAuditLog idmAuditLog = new IdmAuditLog();
        idmAuditLog.setRequestorUserId(requesterId);
        idmAuditLog.setTargetUser(pUser.getId(), pUser.getLogin());

        if (provisionServiceFlag) {
            pUser.setRequestorUserId(requesterId);
            setAuditInfo(pUser, request);

            Map<String, Object> controllerObj = new HashMap<String, Object>();
            controllerObj.put("user", pUser);

            ExtendController extCmd = (ExtendController) scriptRunner.instantiateClass(null, extendController);

            if (extCmd.pre(operation.name(), controllerObj, null) == ExtendController.SUCCESS_CONTINUE) {
                switch (operation) {
                    case DELETE:
                        wsResponse = provisionService.deleteByUserId(userId, UserStatusEnum.DELETED, requesterId);
                        break;
                    case REMOVE:
                        wsResponse = provisionService.deleteByUserId(userId, UserStatusEnum.REMOVE, requesterId);
                        break;
                    case ENABLE:
                        wsResponse = provisionService.disableUser(userId, false, requesterId);
                        idmAuditLog.setAction(AuditAction.USER_ENABLE.value());
                        idmAuditLog.setAuditDescription("User enable");
                        auditLogService.addLog(idmAuditLog);
                        break;
                    case DISABLE:
                        wsResponse = provisionService.disableUser(userId, true, requesterId);
                        idmAuditLog.setAction(AuditAction.USER_DISABLE.value());
                        idmAuditLog.setAuditDescription("User disable");
                        auditLogService.addLog(idmAuditLog);
                        break;
                }
            }
        } else {

            switch (operation) {
                case DELETE:
                    wsResponse = userDataWebService.deleteUser(userId);
                    break;
                case REMOVE:
                    wsResponse = userDataWebService.removeUser(userId);
                    break;
                case ENABLE:
                    wsResponse = userDataWebService.setSecondaryStatus(userId, null);
                    idmAuditLog.setAction(AuditAction.USER_ENABLE.value());
                    idmAuditLog.setAuditDescription("User enable");
                    auditLogService.addLog(idmAuditLog);
                    break;
                case DISABLE:
                    wsResponse = userDataWebService.setSecondaryStatus(userId, UserStatusEnum.DISABLED);
                    idmAuditLog.setAction(AuditAction.USER_DISABLE.value());
                    idmAuditLog.setAuditDescription("User disable");
                    auditLogService.addLog(idmAuditLog);
                    break;
            }
        }
        if (!ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
            switch (operation) {
                case DELETE:
                    retVal = Errors.CANNOT_DELETE_USER;
                    break;
                case REMOVE:
                    retVal = Errors.CANNOT_REMOVE_USER;
                    break;
                case ENABLE:
                    retVal = Errors.CANNOT_ENABLE_USER;
                    break;
                case DISABLE:
                    retVal = Errors.CANNOT_DISABLE_USER;
                    break;
            }

        }
        return retVal;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(userInfoValidator);
    }

    private enum UserOperation {
        DELETE, REMOVE, DISABLE, ENABLE;
    }

    private interface PasswordResponseHandler {
        public ErrorToken handle(final PasswordValidationResponse response);
    }

    private BasicAjaxResponse genAbortAjaxResponse(Response response, Locale locale) {
        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        if (response.isSuccess()) {
            SuccessToken successToken = new SuccessToken(SuccessMessage.OPERATION_ABORTED);
            ajaxResponse.setStatus(200);
            ajaxResponse.setSuccessToken(successToken);
            ajaxResponse.setSuccessMessage(messageSource.getMessage(
                    successToken.getMessage().getMessageName(),
                    null, locale));
        } else {
            ajaxResponse.setStatus(500);
            ErrorToken errorToken = new ErrorToken(Errors.OPERATION_ABORTED);
            errorToken.setMessage(messageSource.getMessage(
                    errorToken.getError().getMessageName(),
                    errorToken.getParams(), locale));
            ajaxResponse.addError(errorToken);
        }
        return ajaxResponse;
    }

}
