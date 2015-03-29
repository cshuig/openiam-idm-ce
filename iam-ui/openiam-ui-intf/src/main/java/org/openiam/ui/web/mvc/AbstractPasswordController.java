package org.openiam.ui.web.mvc;

import org.apache.commons.collections.CollectionUtils;

import org.apache.commons.lang.StringUtils;
import org.openiam.base.ws.ResponseCode;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.exception.EsbErrorToken;
import org.openiam.exception.ObjectNotFoundException;
import org.openiam.idm.srvc.pswd.dto.Password;
import org.openiam.idm.srvc.pswd.dto.PasswordRule;
import org.openiam.idm.srvc.pswd.dto.PasswordValidationResponse;
import org.openiam.idm.srvc.pswd.rule.PasswordRuleException;
import org.openiam.idm.srvc.pswd.rule.PasswordRuleViolation;
import org.openiam.idm.srvc.pswd.ws.PasswordWebService;
import org.openiam.provision.dto.PasswordSync;
import org.openiam.provision.resp.PasswordResponse;
import org.openiam.provision.service.ProvisionService;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.web.model.SetPasswordToken;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractPasswordController extends AbstractLoginController implements InitializingBean {

    private Map<ResponseCode, PasswordResponseHandler> passwordErrormap = new HashMap<ResponseCode, PasswordResponseHandler>();

    @Resource(name = "provisionServiceClient")
    protected ProvisionService provisionService;

    protected SetPasswordToken validatePassword(final String principal, final String managedSysId,
                                                final String newPassword,
                                                final boolean forgotPassword) throws ObjectNotFoundException {
        final SetPasswordToken token = new SetPasswordToken();

        final Password password = new Password();
        password.setPassword(newPassword);
        password.setPrincipal(principal);
        password.setManagedSysId(managedSysId);
        password.setSrcApplicationId(managedSysId);
        password.setSkipPasswordFrequencyCheck(!(forgotPassword));
        final PasswordValidationResponse response = passwordService.isPasswordValid(password);

        if (response.isFailure()) {
            if (CollectionUtils.isNotEmpty(response.getViolations())) {
                ErrorToken errorToken = null;
                for (final PasswordRuleViolation exception : response.getViolations()) {
                    if (exception.getCode() != null && passwordErrormap.containsKey(exception.getCode())) {
                        errorToken = passwordErrormap.get(exception.getCode()).handle(exception);
                        if (errorToken != null) {
                            token.addError(errorToken);
                        }
                    }
                }
            } else {
                token.addError(new ErrorToken(Errors.INTERNAL_ERROR));
            }
            if (CollectionUtils.isNotEmpty(response.getRules())) {
                for (final PasswordRule rule : response.getRules()) {
                    if (rule != null && passwordErrormap.containsKey(rule.getCode())) {
                        final ErrorToken ruleResult = getRule(rule);
                        if (ruleResult != null) {
                            if (!(forgotPassword)) {
                                if (Errors.CHANGE_PWD_FAIL_HISTORY_MESSAGE.equals(ruleResult.getError())) {
                                    continue;
                                }
                            }
                            token.addRule(ruleResult);
                        }
                    }
                }
            }
        }
        return token;
    }

    protected SetPasswordToken attemptResetPassword(final HttpServletRequest request,
                                                    final String password,
                                                    final String userId,
                                                    final boolean preventChangeCountIncrement,
                                                    final boolean forceReset) {
        final PasswordSync passwordSync = new PasswordSync();
        passwordSync.setManagedSystemId(null); // reset all identities for user
        passwordSync.setPassword(password);
        passwordSync.setUserId(userId);
        passwordSync.setPassThruAttributes(false);
        passwordSync.setRequestClientIP(request.getRemoteAddr());
        String requestorId = getRequesterId(request);
        passwordSync.setRequestorId(StringUtils.isEmpty(requestorId) ? "3000" : requestorId);
        passwordSync.setPreventChangeCountIncrement(preventChangeCountIncrement);
        if (forceReset) {
            return forceResetPassword(passwordSync);
        } else {
            return attemptResetPassword(passwordSync);
        }
    }

    protected SetPasswordToken attemptResetPassword(final PasswordSync passwordSync) {
        final SetPasswordToken token = new SetPasswordToken();
        final PasswordValidationResponse response = provisionService.setPassword(passwordSync);
        if (response.isFailure()) {
            if (CollectionUtils.isNotEmpty(response.getViolations())) {
                for (final PasswordRuleViolation violation : response.getViolations()) {
                    if (passwordErrormap.containsKey(violation.getCode())) {
                        final ErrorToken errorToken = getError(violation);
                        token.addError(errorToken);
                    }
                }
            }
            if (!token.hasErrors()) {
                token.addError(new ErrorToken(Errors.CHANGE_PASSWORD_FAILED));
            }

            if (CollectionUtils.isNotEmpty(response.getRules())) {
                for (final PasswordRule rule : response.getRules()) {
                    if (rule != null && passwordErrormap.containsKey(rule.getCode())) {
                        final ErrorToken ruleResult = getRule(rule);
                        token.addRule(ruleResult);
                    }
                }
            }
        }
        return token;
    }

    protected SetPasswordToken forceResetPassword(final PasswordSync passwordSync) {
        final SetPasswordToken token = new SetPasswordToken();
        if (passwordSync.getRequestorId() == null) {
            passwordSync.setRequestorId("3000");
        }
        final PasswordResponse setPasswordResponse = provisionService.resetPassword(passwordSync);
        if (ResponseStatus.SUCCESS != setPasswordResponse.getStatus()) {
            token.addError(new ErrorToken(Errors.CHANGE_PASSWORD_FAILED));
        }
        return token;
    }

    private ErrorToken getError(final PasswordRuleViolation response) {
        return passwordErrormap.get(response.getCode()).handle(response);
    }

    private ErrorToken getRule(final PasswordRule rule) {
        return passwordErrormap.get(rule.getCode()).getRule(rule);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        passwordErrormap.put(ResponseCode.PASSWORD_POLICY_NOT_FOUND, new PasswordResponseHandler() {

            @Override
            public ErrorToken handle(PasswordRuleViolation response) {
                return new ErrorToken(Errors.CHANGE_PWD_POLICY_NOT_FOUND);
            }

            @Override
            public ErrorToken getRule(PasswordRule rule) {
                /* not handled in password rule s*/
                return null;
            }
        });
        passwordErrormap.put(ResponseCode.FAIL_ALPHA_CHAR_RULE, new PasswordResponseHandler() {

            @Override
            public ErrorToken handle(PasswordRuleViolation response) {
                if (response.hasMaxBound() && response.hasMinBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_ALPHA_CHAR_RULE_RANGE, new Object[]{response.getMinBound(), response.getMaxBound()});
                } else if (response.hasMaxBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_ALPHA_CHAR_RULE_MAX, new Object[]{response.getMaxBound()});
                } else if (response.hasMinBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_ALPHA_CHAR_RULE_MIN, new Object[]{response.getMinBound()});
                } else {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_ALPHA_CHAR_RULE);
                }
            }

            @Override
            public ErrorToken getRule(PasswordRule rule) {
                if (rule.hasMaxBound() && rule.hasMinBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_ALPHA_CHAR_RULE_RANGE, new Object[]{rule.getMinBound(), rule.getMaxBound()});
                } else if (rule.hasMaxBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_ALPHA_CHAR_RULE_MAX, new Object[]{rule.getMaxBound()});
                } else if (rule.hasMinBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_ALPHA_CHAR_RULE_MIN, new Object[]{rule.getMinBound()});
                } else {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_ALPHA_CHAR_RULE);
                }
            }
        });
        passwordErrormap.put(ResponseCode.FAIL_LOWER_CASE_RULE, new PasswordResponseHandler() {

            @Override
            public ErrorToken handle(PasswordRuleViolation response) {
                if (response.hasMaxBound() && response.hasMinBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_LOWER_CASE_RULE_RANGE, new Object[]{response.getMinBound(), response.getMaxBound()});
                } else if (response.hasMaxBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_LOWER_CASE_RULE_MAX, new Object[]{response.getMaxBound()});
                } else if (response.hasMinBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_LOWER_CASE_RULE_MIN, new Object[]{response.getMinBound()});
                } else {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_LOWER_CASE_RULE);
                }
            }

            @Override
            public ErrorToken getRule(PasswordRule rule) {
                if (rule.hasMaxBound() && rule.hasMinBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_LOWER_CASE_RULE_RANGE, new Object[]{rule.getMinBound(), rule.getMaxBound()});
                } else if (rule.hasMaxBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_LOWER_CASE_RULE_MAX, new Object[]{rule.getMaxBound()});
                } else if (rule.hasMinBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_LOWER_CASE_RULE_MIN, new Object[]{rule.getMinBound()});
                } else {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_LOWER_CASE_RULE);
                }
            }
        });
        passwordErrormap.put(ResponseCode.FAIL_UPPER_CASE_RULE, new PasswordResponseHandler() {

            @Override
            public ErrorToken handle(PasswordRuleViolation response) {
                if (response.hasMaxBound() && response.hasMinBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_UPPER_CASE_RULE_RANGE, new Object[]{response.getMinBound(), response.getMaxBound()});
                } else if (response.hasMaxBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_UPPER_CASE_RULE_MAX, new Object[]{response.getMaxBound()});
                } else if (response.hasMinBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_UPPER_CASE_RULE_MIN, new Object[]{response.getMinBound()});
                } else {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_UPPER_CASE_RULE);
                }
            }

            @Override
            public ErrorToken getRule(PasswordRule rule) {
                if (rule.hasMaxBound() && rule.hasMinBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_UPPER_CASE_RULE_RANGE, new Object[]{rule.getMinBound(), rule.getMaxBound()});
                } else if (rule.hasMaxBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_UPPER_CASE_RULE_MAX, new Object[]{rule.getMaxBound()});
                } else if (rule.hasMinBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_UPPER_CASE_RULE_MIN, new Object[]{rule.getMinBound()});
                } else {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_UPPER_CASE_RULE);
                }
            }
        });
        passwordErrormap.put(ResponseCode.FAIL_NON_APHANUMERIC_RULE, new PasswordResponseHandler() {

            @Override
            public ErrorToken handle(PasswordRuleViolation response) {
                if (response.hasMaxBound() && response.hasMinBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_NON_APHANUMERIC_RULE_RANGE, new Object[]{response.getMinBound(), response.getMaxBound()});
                } else if (response.hasMaxBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_NON_APHANUMERIC_RULE_MAX, new Object[]{response.getMaxBound()});
                } else if (response.hasMinBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_NON_APHANUMERIC_RULE_MIN, new Object[]{response.getMinBound()});
                } else {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_NON_APHANUMERIC_RULE);
                }
            }

            @Override
            public ErrorToken getRule(PasswordRule rule) {
                if (rule.hasMaxBound() && rule.hasMinBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_NON_APHANUMERIC_RULE_RANGE, new Object[]{rule.getMinBound(), rule.getMaxBound()});
                } else if (rule.hasMaxBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_NON_APHANUMERIC_RULE_MAX, new Object[]{rule.getMaxBound()});
                } else if (rule.hasMinBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_NON_APHANUMERIC_RULE_MIN, new Object[]{rule.getMinBound()});
                } else {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_NON_APHANUMERIC_RULE);
                }
            }
        });
        passwordErrormap.put(ResponseCode.FAIL_NUMERIC_CHAR_RULE, new PasswordResponseHandler() {

            @Override
            public ErrorToken handle(PasswordRuleViolation response) {
                if (response.hasMaxBound() && response.hasMinBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_NUMERIC_CHAR_RULE_RANGE, new Object[]{response.getMinBound(), response.getMaxBound()});
                } else if (response.hasMaxBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_NUMERIC_CHAR_RULE_MAX, new Object[]{response.getMaxBound()});
                } else if (response.hasMinBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_NUMERIC_CHAR_RULE_MIN, new Object[]{response.getMinBound()});
                } else {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_NUMERIC_CHAR_RULE);
                }
            }

            @Override
            public ErrorToken getRule(PasswordRule rule) {
                if (rule.hasMaxBound() && rule.hasMinBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_NUMERIC_CHAR_RULE_RANGE, new Object[]{rule.getMinBound(), rule.getMaxBound()});
                } else if (rule.hasMaxBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_NUMERIC_CHAR_RULE_MAX, new Object[]{rule.getMaxBound()});
                } else if (rule.hasMinBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_NUMERIC_CHAR_RULE_MIN, new Object[]{rule.getMinBound()});
                } else {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_NUMERIC_CHAR_RULE);
                }
            }
        });
        passwordErrormap.put(ResponseCode.FAIL_HISTORY_RULE, new PasswordResponseHandler() {

            @Override
            public ErrorToken handle(PasswordRuleViolation response) {
                return new ErrorToken(Errors.CHANGE_PWD_FAIL_HISTORY_MESSAGE);
            }

            @Override
            public ErrorToken getRule(PasswordRule rule) {
                return new ErrorToken(Errors.CHANGE_PWD_FAIL_HISTORY_MESSAGE);
            }
        });
        passwordErrormap.put(ResponseCode.FAIL_LENGTH_RULE, new PasswordResponseHandler() {

            @Override
            public ErrorToken handle(PasswordRuleViolation response) {
                if (response.hasMaxBound() && response.hasMinBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_LENGTH_RULE_RANGE, new Object[]{response.getMinBound(), response.getMaxBound()});
                } else if (response.hasMaxBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_LENGTH_RULE_MAX, new Object[]{response.getMaxBound()});
                } else if (response.hasMinBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_LENGTH_RULE_MIN, new Object[]{response.getMinBound()});
                } else {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_LENGTH_RULE);
                }
            }

            @Override
            public ErrorToken getRule(PasswordRule rule) {
                if (rule.hasMaxBound() && rule.hasMinBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_LENGTH_RULE_RANGE, new Object[]{rule.getMinBound(), rule.getMaxBound()});
                } else if (rule.hasMaxBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_LENGTH_RULE_MAX, new Object[]{rule.getMaxBound()});
                } else if (rule.hasMinBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_LENGTH_RULE_MIN, new Object[]{rule.getMinBound()});
                } else {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_LENGTH_RULE);
                }
            }
        });
        passwordErrormap.put(ResponseCode.FAIL_NEQ_NAME, new PasswordResponseHandler() {

            @Override
            public ErrorToken handle(PasswordRuleViolation response) {
                return new ErrorToken(Errors.CHANGE_PWD_FAIL_NEQ_NAME);
            }

            @Override
            public ErrorToken getRule(PasswordRule rule) {
                return new ErrorToken(Errors.CHANGE_PWD_FAIL_NEQ_NAME);
            }
        });
        passwordErrormap.put(ResponseCode.FAIL_NEQ_PASSWORD, new PasswordResponseHandler() {

            @Override
            public ErrorToken handle(PasswordRuleViolation response) {
                return new ErrorToken(Errors.CHANGE_PWD_FAIL_NEQ_PASSWORD);
            }

            @Override
            public ErrorToken getRule(PasswordRule rule) {
                return new ErrorToken(Errors.CHANGE_PWD_FAIL_NEQ_PASSWORD);
            }
        });
        passwordErrormap.put(ResponseCode.FAIL_NEQ_PRINCIPAL, new PasswordResponseHandler() {

            @Override
            public ErrorToken handle(PasswordRuleViolation response) {
                return new ErrorToken(Errors.CHANGE_PWD_FAIL_NEQ_PRINCIPAL);
            }

            @Override
            public ErrorToken getRule(PasswordRule rule) {
                return new ErrorToken(Errors.CHANGE_PWD_FAIL_NEQ_PRINCIPAL);
            }
        });
        passwordErrormap.put(ResponseCode.FAIL_PASSWORD_CHANGE_FREQUENCY, new PasswordResponseHandler() {

            @Override
            public ErrorToken handle(PasswordRuleViolation response) {
                return new ErrorToken(Errors.CHANGE_PWD_FAIL_PASSWORD_CHANGE_FREQUENCY);
            }

            @Override
            public ErrorToken getRule(PasswordRule rule) {
                return new ErrorToken(Errors.CHANGE_PWD_FAIL_PASSWORD_CHANGE_FREQUENCY);
            }
        });
        passwordErrormap.put(ResponseCode.FAIL_PASSWORD_CHANGE_ALLOW, new PasswordResponseHandler() {

            @Override
            public ErrorToken handle(PasswordRuleViolation response) {
                return new ErrorToken(Errors.CHANGE_PWD_FAIL_PASSWORD_CHANGE_ALLOW);
            }

            @Override
            public ErrorToken getRule(PasswordRule rule) {
                return new ErrorToken(Errors.CHANGE_PWD_FAIL_PASSWORD_CHANGE_ALLOW);
            }
        });
        passwordErrormap.put(ResponseCode.FAIL_REJECT_CHARS_IN_PSWD, new PasswordResponseHandler() {

            @Override
            public ErrorToken handle(PasswordRuleViolation response) {
                return new ErrorToken(Errors.CHANGE_PWD_FAIL_REJECT_CHARS_IN_PSWD, response.getResponseValueAsArray());
            }

            @Override
            public ErrorToken getRule(PasswordRule rule) {
                return new ErrorToken(Errors.CHANGE_PWD_FAIL_REJECT_CHARS_IN_PSWD, rule.getResponseValueAsArray());
            }
        });

        passwordErrormap.put(ResponseCode.FAIL_REJECT_WORDS_IN_PSWD, new PasswordResponseHandler() {

            @Override
            public ErrorToken handle(PasswordRuleViolation response) {
                return new ErrorToken(Errors.CHANGE_PWD_FAIL_REJECT_WORDS_IN_PSWD, response.getResponseValueAsArray());
            }

            @Override
            public ErrorToken getRule(PasswordRule rule) {
                return new ErrorToken(Errors.CHANGE_PWD_FAIL_REJECT_WORDS_IN_PSWD, rule.getResponseValueAsArray());
            }
        });

        passwordErrormap.put(ResponseCode.PASSPHRASE_WORD_REPEAT_RULE, new PasswordResponseHandler() {

            @Override
            public ErrorToken handle(PasswordRuleViolation response) {
                return new ErrorToken(Errors.PASSPHRASE_WORD_REPEAT_RULE);
            }

            @Override
            public ErrorToken getRule(PasswordRule rule) {
                return new ErrorToken(Errors.PASSPHRASE_WORD_REPEAT_RULE);
            }
        });
        passwordErrormap.put(ResponseCode.FAIL_MIN_WORDS_PASSPHRASE_RULE, new PasswordResponseHandler() {

            @Override
            public ErrorToken handle(PasswordRuleViolation response) {
                return new ErrorToken(Errors.FAIL_MIN_WORDS_PASSPHRASE_RULE, response.getResponseValueAsArray());
            }

            @Override
            public ErrorToken getRule(PasswordRule rule) {
                return new ErrorToken(Errors.FAIL_MIN_WORDS_PASSPHRASE_RULE, rule.getResponseValueAsArray());
            }
        });
        passwordErrormap.put(ResponseCode.FAIL_REPEAT_SAME_WORD_PASSPHRASE_RULE, new PasswordResponseHandler() {

            @Override
            public ErrorToken handle(PasswordRuleViolation response) {
                return new ErrorToken(Errors.FAIL_REPEAT_SAME_WORD_PASSPHRASE_RULE);
            }

            @Override
            public ErrorToken getRule(PasswordRule rule) {
                return new ErrorToken(Errors.FAIL_REPEAT_SAME_WORD_PASSPHRASE_RULE);
            }
        });
        passwordErrormap.put(ResponseCode.FAIL_OTHER, new PasswordResponseHandler() {

            @Override
            public ErrorToken handle(PasswordRuleViolation response) {
                return new ErrorToken(Errors.CHANGE_PASSWORD_FAILED);
            }

            @Override
            public ErrorToken getRule(PasswordRule rule) {
                return new ErrorToken(Errors.CHANGE_PASSWORD_FAILED);
            }
        });
        passwordErrormap.put(ResponseCode.FAIL_LIMIT_NUM_REPEAT_CHAR, new PasswordResponseHandler() {

            @Override
            public ErrorToken handle(PasswordRuleViolation response) {
                return new ErrorToken(Errors.CHANGE_PWD_FAIL_LIMIT_NUM_REPEAT_CHAR, response.getResponseValueAsArray());
            }

            @Override
            public ErrorToken getRule(PasswordRule rule) {
                return new ErrorToken(Errors.CHANGE_PWD_FAIL_LIMIT_NUM_REPEAT_CHAR, rule.getResponseValueAsArray());
            }
        });
    }

    private interface PasswordResponseHandler {
        public ErrorToken handle(final PasswordRuleViolation response);

        public ErrorToken getRule(final PasswordRule rule);
    }

}
