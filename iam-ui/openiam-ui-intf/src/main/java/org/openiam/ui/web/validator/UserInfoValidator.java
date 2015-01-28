package org.openiam.ui.web.validator;

import org.openiam.idm.srvc.auth.ws.LoginDataWebService;
import org.openiam.idm.srvc.auth.ws.LoginResponse;
import org.openiam.ui.rest.api.model.EditUserModel;
import org.openiam.ui.rest.api.model.ResetPasswordBean;
import org.openiam.ui.web.util.DateFormatStr;
import org.openiam.ui.web.util.OpeniamCookieLocaleResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserInfoValidator extends AbstractUserValidator implements Validator {

    @Autowired
    protected MessageSource messageSource;

    @Autowired
    @Qualifier("localeResolver")
    protected OpeniamCookieLocaleResolver localeResolver;

    @Value("${org.openiam.email.validation.regexp}")
    private String emailPattern;

    @Value("${org.openiam.provision.service.flag}")
    private  Boolean provisionServiceFlag;

    @Resource(name = "loginServiceClient")
    private LoginDataWebService loginService;
    @Value("${org.openiam.defaultManagedSysId}")
    private String defaultManagedSysId;

    public boolean supports(@SuppressWarnings("rawtypes") Class cls) {
        return EditUserModel.class.equals(cls) || ResetPasswordBean.class.equals(cls);
    }

    public void validate(Object model, Errors err) {
        final ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        final HttpServletRequest request = sra.getRequest();

        if(model instanceof ResetPasswordBean) {
            ResetPasswordBean pwd = (ResetPasswordBean) model;

            if(!pwd.getAutoGeneratePassword()) {
                if(!StringUtils.hasText(pwd.getPassword())) {
                    err.rejectValue("password", "required", org.openiam.ui.util.messages.Errors.RESET_PASSWORD_PASSWORD_NOT_SET.getMessageName());

                } else {
                    if(!pwd.getPassword().equals(pwd.getConfPassword())) {
                        err.rejectValue("password", "required", org.openiam.ui.util.messages.Errors.RESET_PASSWORD_PASSWORD_NOT_MATCH.getMessageName());
                    }
                }
            }

        } else if(model instanceof EditUserModel) {
            ArrayList<String> dateFormatPattern = new ArrayList<>();
            dateFormatPattern.add(DateFormatStr.getSdfDate());

            SimpleDateFormat sdf = new SimpleDateFormat(DateFormatStr.getSdfDate());
            EditUserModel editUserModel = (EditUserModel) model;

            if(!StringUtils.hasText(editUserModel.getFirstName())) {
                err.rejectValue("firstName", "required", org.openiam.ui.util.messages.Errors.REQUIRED_USER_FIRST_NAME.getMessageName());
            }
            if(!StringUtils.hasText(editUserModel.getLastName())) {
                err.rejectValue("lastName", "required", org.openiam.ui.util.messages.Errors.REQUIRED_USER_LAST_NAME.getMessageName());
            }
            if(editUserModel.getBirthdateAsStr()!=null) {
                try {
                    editUserModel.setBirthdate(sdf.parse(editUserModel.getBirthdateAsStr()));
                } catch (ParseException e){
                    err.rejectValue("birthdate", "required", messageSource.getMessage(org.openiam.ui.util.messages.Errors.INVALID_BIRTHDAY_FORMAT.getMessageName(), dateFormatPattern.toArray(), localeResolver.resolveLocale(request)));
                }
            }

            //if(editUserModel.getStartDateAsStr()!=null && editUserModel.getLastDateAsStr()!=null) {
                boolean error=false;
                if(editUserModel.getStartDateAsStr() != null) {
                	try {
                		editUserModel.setStartDate(sdf.parse(editUserModel.getStartDateAsStr()));
                	} catch (ParseException e){
                		err.rejectValue("startDate", "required", messageSource.getMessage(org.openiam.ui.util.messages.Errors.INVALID_START_DATE_FORMAT.getMessageName(), dateFormatPattern.toArray(), localeResolver.resolveLocale(request)));
                		error=true;
                	}
                } else {
                	editUserModel.setStartDate(null);
                }
                
                if(editUserModel.getLastDateAsStr() != null) {
                	try {
                		editUserModel.setLastDate(sdf.parse(editUserModel.getLastDateAsStr()));
                	} catch (ParseException e) {
                		err.rejectValue("lastDate", "required", messageSource.getMessage(org.openiam.ui.util.messages.Errors.INVALID_LAST_DATE_FORMAT.getMessageName(), dateFormatPattern.toArray(), localeResolver.resolveLocale(request)));
                		error=true;
                	}
                } else {
                	editUserModel.setLastDate(null);
                }

            if(editUserModel.getClaimDateAsStr() != null) {
                try {
                    editUserModel.setClaimDate(sdf.parse(editUserModel.getClaimDateAsStr()));
                } catch (ParseException e) {
                    err.rejectValue("claimDate", "required", messageSource.getMessage(org.openiam.ui.util.messages.Errors.INVALID_CLAIM_DATE_FORMAT.getMessageName(), dateFormatPattern.toArray(), localeResolver.resolveLocale(request)));
                    error=true;
                }
            } else {
                editUserModel.setClaimDate(null);
            }

            if(!error){
                if(editUserModel.getStartDate() != null && editUserModel.getLastDate() != null && editUserModel.getStartDate().after(editUserModel.getLastDate()))
                //if(editUserModel.getStartDate().getTime()>editUserModel.getLastDate().getTime()){
                err.rejectValue("lastDate", "required", org.openiam.ui.util.messages.Errors.START_DATE_GREATER_LAST_DATE.getMessageName());
                //}
            }
            //}

            if(editUserModel.getId()==null || editUserModel.getId().isEmpty()){
                if (!StringUtils.hasText(editUserModel.getMetadataTypeId())) {
                    err.rejectValue("metadataTypeId", "required", org.openiam.ui.util.messages.Errors.REQUIRED_USER_METADATA_TYPE.getMessageName());
                }
            }

            if((editUserModel.getId()==null || editUserModel.getId().isEmpty())){
                validateLogin(err, editUserModel);
              //  doOrganizationChecks(err, editUserModel.getOrganizationIds());

                if(!provisionServiceFlag){
                    // check login, password fields
                    if(!StringUtils.hasText(editUserModel.getPassword())) {
                        err.rejectValue("password", "required", org.openiam.ui.util.messages.Errors.REQUIRED_USER_PASSWORD.getMessageName());
                    } else {
                        if (!StringUtils.hasText(editUserModel.getConfirmPassword()) || !editUserModel.getPassword().equals(editUserModel.getConfirmPassword())) {
                            err.rejectValue("confirmPassword", "required", org.openiam.ui.util.messages.Errors.PASSWORD_USER_DOES_NOT_MATCH.getMessageName());
                        }
                    }
                }

                if(editUserModel.getNotifyUserViaEmail()) {
                    if(editUserModel.getEmail()==null){
                        err.rejectValue("email", "required", org.openiam.ui.util.messages.Errors.USER_EMAIL_ADDRESS_REQUIRED.getMessageName());
                    }
                }
                if(editUserModel.getNotifySupervisorViaEmail()) {
                    if(!StringUtils.hasText(editUserModel.getSupervisorId())){
                        err.rejectValue("supervisorId", "required", org.openiam.ui.util.messages.Errors.USER_SUPERVISOR_REQUIRED.getMessageName());
                    }
                }

                if(editUserModel.getEmail()!=null) {
                    Pattern pattern = Pattern.compile(emailPattern);
                    Matcher matcher = pattern.matcher(editUserModel.getEmail().getEmail());
                    if (!matcher.matches()) {
                        err.rejectValue("email", "invalid", org.openiam.ui.util.messages.Errors.INVALID_EMAIL_ADDRESS.getMessageName());
                    }

                    if(!StringUtils.hasText(editUserModel.getEmail().getTypeId())) {
                        err.rejectValue("email", "required", org.openiam.ui.util.messages.Errors.USER_EMAIL_TYPE_REQUIRED.getMessageName());
                    }
                }

                if(editUserModel.getAddress()!=null) {
                    if(!StringUtils.hasText(editUserModel.getAddress().getCity())) {
                        err.rejectValue("address", "invalid", org.openiam.ui.util.messages.Errors.USER_ADDRESS_CITY_REQUIRED.getMessageName());
                    }
                    if(!StringUtils.hasText(editUserModel.getAddress().getAddress1())) {
                        err.rejectValue("address", "invalid", org.openiam.ui.util.messages.Errors.USER_ADDRESS_ADDRESS1_REQUIRED.getMessageName());
                    }

                    if (!StringUtils.hasText(editUserModel.getAddress().getTypeId())) {
                        err.rejectValue("address", "required", org.openiam.ui.util.messages.Errors.USER_ADDRESS_TYPE_REQUIRED.getMessageName());
                    }
                }

                if(editUserModel.getPhone()!=null) {
                    if(!StringUtils.hasText(editUserModel.getPhone().getAreaCd())) {
                        err.rejectValue("phone", "required", org.openiam.ui.util.messages.Errors.USER_PHONE_AREA_CD_REQUIRED.getMessageName());
                    }
                    if(!StringUtils.hasText(editUserModel.getPhone().getPhoneNbr())) {
                        err.rejectValue("phone", "required", org.openiam.ui.util.messages.Errors.USER_PHONE_NUMBER_REQUIRED.getMessageName());
                    }

                    if(!StringUtils.hasText(editUserModel.getPhone().getTypeId())) {
                        err.rejectValue("phone", "required", org.openiam.ui.util.messages.Errors.USER_PHONE_TYPE_REQUIRED.getMessageName());
                    }
                }


//            if (!StringUtils.hasText(editUserModel.getRoleId())) {
//                err.rejectValue("roleId", "required", org.openiam.ui.util.messages.Errors.NEW_USER_ROLE_REQUIRED.getMessageName());
//            }
            }
        }
    }

    private void validateLogin(Errors err, EditUserModel editUserModel) {
        if(!provisionServiceFlag){
            if (!StringUtils.hasText(editUserModel.getLogin())) {
                err.rejectValue("login", "required", org.openiam.ui.util.messages.Errors.REQUIRED_USER_LOGIN.getMessageName());
            }
            checkLoginForDuplication(err, editUserModel.getLogin());
        } else if (StringUtils.hasText(editUserModel.getLogin())) {
            checkLoginForDuplication(err, editUserModel.getLogin());
        }
    }

    private void checkLoginForDuplication(Errors err,String login){
        LoginResponse dupPrincipal = loginService.getLoginByManagedSys(login, defaultManagedSysId);
        if (dupPrincipal != null && dupPrincipal.getPrincipal()!=null) {
            err.rejectValue("login", "required", org.openiam.ui.util.messages.Errors.USER_LOGIN_EXISTS.getMessageName());
        }
    }
}