import org.apache.commons.collections.CollectionUtils
import org.apache.commons.lang.ArrayUtils
import org.apache.commons.lang.StringUtils
import org.apache.commons.lang.time.DateUtils
import org.openiam.base.ws.MatchType
import org.openiam.base.ws.ResponseCode
import org.openiam.base.ws.SearchParam
import org.openiam.idm.searchbeans.IdentityQuestionSearchBean
import org.openiam.idm.searchbeans.UserSearchBean
import org.openiam.idm.srvc.auth.dto.Login
import org.openiam.idm.srvc.lang.dto.Language
import org.openiam.idm.srvc.policy.dto.ITPolicy
import org.openiam.idm.srvc.policy.dto.ITPolicyApproveType
import org.openiam.idm.srvc.policy.service.PolicyDataService
import org.openiam.idm.srvc.pswd.dto.IdentityQuestion
import org.openiam.idm.srvc.pswd.dto.Password
import org.openiam.idm.srvc.pswd.dto.PasswordValidationResponse
import org.openiam.idm.srvc.pswd.dto.UserIdentityAnswer
import org.openiam.idm.srvc.pswd.service.ChallengeResponseWebService
import org.openiam.idm.srvc.pswd.ws.PasswordWebService
import org.openiam.idm.srvc.user.dto.User
import org.openiam.idm.srvc.user.ws.UserDataWebService
import org.openiam.provision.dto.PasswordSync
import org.openiam.provision.dto.ProvisionUser
import org.openiam.provision.service.ProvisionService
import org.openiam.ui.util.messages.ErrorToken
import org.openiam.ui.util.messages.Errors
import org.openiam.ui.web.filter.OpeniamFilter
import org.springframework.binding.message.MessageBuilder
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.webflow.execution.RequestContext

import javax.annotation.PostConstruct
import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest
import java.text.ParseException
import java.text.SimpleDateFormat

public class EmployeeService  {

    private Map<ResponseCode, PasswordResponseHandler> passwordErrormap = new HashMap<ResponseCode, PasswordResponseHandler>()

    private String defaultManagedSysId
    private String challengeResponseGroup
    private Boolean secureAnswers

    @Resource(name = "userServiceClient")
    private UserDataWebService userDataWebService

    @Resource(name="challengeResponseServiceClient")
    private ChallengeResponseWebService challengeResponseService

    @Resource(name="passwordServiceClient")
    protected PasswordWebService passwordService

    @Resource(name = "provisionServiceClient")
    protected ProvisionService provisionService

    @Resource(name = "policyServiceClient")
    private PolicyDataService policyDataService

    private df = new SimpleDateFormat("MM/dd/yyyy")

    public boolean validateEmployee(RequestContext context) {
        def ret = true
        def messageContext = context.messageContext
        def attrs = context.currentEvent.attributes
        if (!attrs.contains('employeeID') || StringUtils.isBlank((String)attrs.get('employeeID'))) {
            messageContext.addMessage(new MessageBuilder().error()
                    .source('employeeID')
                    .defaultText('Employee ID can not be blank')
                    .code("accountClaim.employee.employeeID.blank").build())
            ret = false
        }
        if (!attrs.contains('nationalID') || StringUtils.isBlank((String)attrs.get('nationalID'))) {
            messageContext.addMessage(new MessageBuilder().error()
                    .source('nationalID')
                    .defaultText('Last 4 digits of SSN can not be blank')
                    .code("accountClaim.employee.nationalID.blank").build())
            ret = false
        } else {
            def nationalID = attrs.get('nationalID') as String
            if (!(nationalID ==~ /\d{4}/)) {
                messageContext.addMessage(new MessageBuilder().error()
                        .source('nationalID')
                        .defaultText("Specify last 4 digits of SSN")
                        .code("accountClaim.employee.nationalID.invalidFormat").build())
                ret = false
            }
        }
        if (!attrs.contains('dateOfBirth') || StringUtils.isBlank((String)attrs.get('dateOfBirth'))) {
            messageContext.addMessage(new MessageBuilder().error()
                    .source('dateOfBirth')
                    .defaultText('Date of Birth can not be blank')
                    .code("accountClaim.employee.dateOfBirth.blank").build())
            ret = false
        } else {
            try {
                df.parse(attrs.get('dateOfBirth') as String)
            } catch (ParseException e) {
                messageContext.addMessage(new MessageBuilder().error()
                        .source('dateOfBirth')
                        .defaultText('Date of Birth has invalid date format (MM/dd/yyyy)')
                        .code("accountClaim.employee.dateOfBirth.invalidFormat").build())
                ret = false
            }
        }
        if (!ret) {
            context.viewScope.putAll(attrs)
        }
        return ret
    }

    public boolean reviewEmployee(RequestContext context) {
        def messageContext = context.messageContext
        def attrs = context.currentEvent.attributes

        def employeeID = attrs.get('employeeID') as String
        def nationalID = attrs.get('nationalID')  as String
        def dateOfBirth = df.parse(attrs.get('dateOfBirth') as String)

        def searchBean = new UserSearchBean()
        searchBean.setEmployeeIdMatchToken(new SearchParam(value: employeeID, matchType: MatchType.EXACT))
        def users = userDataWebService.findBeans(searchBean, 0, Integer.MAX_VALUE)
        if (users?.size() == 1) {
            def user = users.get(0)
            if (user.birthdate && user.birthdate.equals(dateOfBirth)) {
                if (user.userAttributes?.containsKey('SSN')) {
                    def ssn = user.userAttributes.get('SSN').value
                    if (ssn.endsWith(nationalID)) {
                        //TODO: check here if user has already activated his account
                        context.flowScope.put('user', user)
                        return true
                    }
                }
            }
        }
        messageContext.addMessage(new MessageBuilder().error()
                .defaultText('Error: invalid information')
                .code("accountClaim.error.invalidInfo").build())
        return false
    }

    public int getChallengeQuestionsNum(RequestContext context) {
        def user = context.flowScope.get('user') as User
        return challengeResponseService.getNumOfRequiredQuestions(user.id)?: 3
    }

    public Map<String, String> getChallengeQuestions(RequestContext context) {
        def questionSearchBean = new IdentityQuestionSearchBean()
        questionSearchBean.deepCopy = false
        questionSearchBean.groupId = challengeResponseGroup
        questionSearchBean.active = true

        def challengeQuestions = challengeResponseService.findQuestionBeans(questionSearchBean, 0, Integer.MAX_VALUE, getCurrentLanguage())
        def ret = new HashMap()
        if (challengeQuestions) {
            challengeQuestions.each { IdentityQuestion iq->
                ret.put(iq.id, iq.displayName)
            }
        }
        return ret
    }

    public boolean validateAndSaveChallengeQuestions(RequestContext context) {
        def ret = false
        def attrs = context.currentEvent.attributes
        if (context.flowScope.contains('user')) {
            def user = context.flowScope.get('user') as User
            def messageContext = context.messageContext

            if (attrs.contains('identityQuestion') && attrs.contains('identityAnswer')) {
                def questions = (attrs.get('identityQuestion') instanceof String[])? attrs.get('identityQuestion') as String[] : [attrs.get('identityQuestion')] as String[]
                def answers = (attrs.get('identityAnswer') instanceof String[])? attrs.get('identityAnswer') as String[] : [attrs.get('identityAnswer')] as String[]
                if (ArrayUtils.isNotEmpty(questions) && ArrayUtils.isNotEmpty(answers)) {
                    def answerList = new ArrayList<UserIdentityAnswer>()
                    for (int i=0; i<questions.size(); i++) {
                        def userIdentityAnswer = new UserIdentityAnswer()
                        userIdentityAnswer.setQuestionId(questions[i])
                        if (i < answers.size()) {
                            userIdentityAnswer.setQuestionAnswer(answers[i])
                        }
                        userIdentityAnswer.setUserId(user.id)
                        answerList.add(userIdentityAnswer)
                    }

                    def wsResponse = challengeResponseService.validateAnswers(answerList)
                    if (wsResponse.success) {
                        context.flowScope.put('answerList', answerList)
                        ret = true
                    } else {
                        def error = Errors.INTERNAL_ERROR
                        if (wsResponse.errorCode) {
                            switch(wsResponse.errorCode) {
                                case ResponseCode.NO_ANSWER_TO_QUESTION:
                                    error = Errors.NO_QUESTION_TO_ANSWER
                                    break
                                case ResponseCode.IDENTICAL_QUESTIONS:
                                    error = Errors.IDENTICAL_QUESTION
                                    break
                                case ResponseCode.QUEST_NOT_SELECTED:
                                    error = Errors.QUESTION_NOT_TAKEN
                                    break
                                case ResponseCode.ANSWER_NOT_TAKEN:
                                    error = Errors.ANSWER_NOT_TAKEN
                                    break
                                default:
                                    error = Errors.CHALLENGE_RESPONSES_NOT_SAVED
                            }
                        }
                        messageContext.addMessage(new MessageBuilder().error().code(error.messageName).build())
                    }
                }
            }
        }
        if (!ret) {
            context.viewScope.putAll(attrs)
        }
        return ret
    }

    public String getLoginID(RequestContext context) {
        if (context.flowScope.contains('user')) {
            def user = context.flowScope.get('user') as User
            return getPrimaryIdentity(user)
        }
        return ''
    }

    private String getPrimaryIdentity(User user) {
        if (CollectionUtils.isNotEmpty(user.principalList)) {
            def primaryPrincipal = user.principalList.find {Login l-> l.managedSysId == defaultManagedSysId}
            if (primaryPrincipal) {
                return primaryPrincipal.login
            }
        }
        return ''
    }

    public boolean validateAndSavePassword(RequestContext context) {
        def ret = true
        if (context.flowScope.contains('user')) {
            def user = context.flowScope.get('user') as User
            def messageContext = context.messageContext
            def attrs = context.currentEvent.attributes

            def pass = attrs.get('password') as String
            def confirmPass = attrs.get('confirmPassword') as String
            if (!pass) {
                messageContext.addMessage(new MessageBuilder().error()
                        .source('password')
                        .defaultText('Password field can not be blank')
                        .code("accountClaim.password.password.blank").build())
                ret = false
            }
            if (!confirmPass) {
                messageContext.addMessage(new MessageBuilder().error()
                        .source('confirmPassword')
                        .defaultText('Confirm Password field can not be blank')
                        .code("accountClaim.password.confirmPassword.blank").build())
                ret = false
            }
            if (!pass.equals(confirmPass)) {
                messageContext.addMessage(new MessageBuilder().error()
                        .defaultText("Entered passwords don't match")
                        .code("accountClaim.password.not.match").build())
                ret = false
            }
            if (ret) {
                def principal = getPrimaryIdentity(user)

                def password = new Password()
                password.password = pass
                password.principal = principal
                password.managedSysId = defaultManagedSysId
                password.srcApplicationId = defaultManagedSysId
                password.skipPasswordFrequencyCheck = true

                def response = passwordService.isPasswordValid(password)
                if (response.failure) {
                    ErrorToken retVal
                    if(response.errorCode && passwordErrormap.containsKey(response.errorCode)) {
                        retVal = passwordErrormap.get(response.errorCode).handle(response)
                    } else {
                        retVal = new ErrorToken(Errors.CHANGE_PASSWORD_FAILED)
                    }
                    messageContext.addMessage(new MessageBuilder().error()
                            .resolvableArgs(retVal.params)
                            .code(retVal.error.messageName).build())
                    ret = false

                } else {
                    def passwordSync = new PasswordSync()
                    passwordSync.managedSystemId = null // set password for all target systems
                    passwordSync.password = pass
                    passwordSync.principal = principal
                    passwordSync.passThruAttributes = false
                    passwordSync.requestClientIP = getCurrentIPAddress()
                    passwordSync.preventChangeCountIncrement = true
                    passwordSync.userId = user.id

                    context.flowScope.put('passwordSync', passwordSync)
                }
            }
        }
        return ret
    }

    public ITPolicy getITPolicy(RequestContext context) {
        return policyDataService.findITPolicy()
    }

    public boolean reviewITPolicy(RequestContext context) {
        if (context.flowScope.contains('user')) {
            def user = context.flowScope.get('user') as User
            def itPolicy = policyDataService.findITPolicy()
            return !getUsePolicyStatus(itPolicy, user)
        }
        return false
    }

    public boolean validateAndConfirmITPolicy(RequestContext context) {
        if (context.flowScope.contains('user')) {
            def messageContext = context.messageContext
            def attrs = context.currentEvent.attributes
            def confirmation = Boolean.parseBoolean(attrs.get('confirmation') as String)
            if (confirmation) {
                context.flowScope.put('policyAccepted', true)
                return true

            } else {
                messageContext.addMessage(new MessageBuilder().error()
                        .defaultText("Please read and confirm")
                        .code("accountClaim.itPolicy.confirmationRequired").build())
            }
        }
        return false
    }

    public void claimAccount(RequestContext context) {
        if (context.flowScope.contains('user')) {
            def user = context.flowScope.get('user') as User
            def error = false

            def policyAccepted = context.flowScope.getBoolean('policyAccepted')
            if (policyAccepted) {
                def ret = userDataWebService.acceptITPolicy(user.id)
                if (ret.isFailure()) {
                    error = true
                }
            }

            def answerList = context.flowScope.get('answerList') as List
            if (answerList) {
                if (challengeResponseService.isUserAnsweredSecurityQuestions(user.id)) {
                    def ret = challengeResponseService.resetQuestionsForUser(user.id)
                    if (ret.isFailure()) {
                        error = true
                    }
                }
                def ret = challengeResponseService.saveAnswers(answerList)
                if (ret.isFailure()) {
                    error = true
                }
            }

            def passwordSync = context.flowScope.get('passwordSync') as PasswordSync
            if (passwordSync) {
                def ret = provisionService.setPassword(passwordSync)
                if (ret.isFailure()) {
                    error = true
                }
            }

            user.claimDate = new Date()
            def ret = provisionService.modifyUser(new ProvisionUser(user))
            if (ret.isFailure()) {
                error = true
            }

            if (error) {
                context.flowScope.put('claimError', true)
                def messageContext = context.messageContext
                messageContext.addMessage(new MessageBuilder().error()
                        .defaultText('Error: please call Service Desk')
                        .code("accountClaim.error.callServiceDesk").build())
            } else {
                context.flowScope.remove('claimError')
            }
        }
    }

    void setChallengeResponseGroup(String challengeResponseGroup) {
        this.challengeResponseGroup = challengeResponseGroup
    }

    void setDefaultManagedSysId(String defaultManagedSysId) {
        this.defaultManagedSysId = defaultManagedSysId
    }

    @PostConstruct
    public void afterPropertiesSet() throws Exception {
        passwordErrormap.put(ResponseCode.PASSWORD_POLICY_NOT_FOUND, new PasswordResponseHandler() {

            @Override
            public ErrorToken handle(PasswordValidationResponse response) {
                return new ErrorToken(Errors.CHANGE_PWD_POLICY_NOT_FOUND)
            }
        })
        passwordErrormap.put(ResponseCode.FAIL_ALPHA_CHAR_RULE, new PasswordResponseHandler() {

            @Override
            public ErrorToken handle(PasswordValidationResponse response) {
                if(response.hasMaxBound() && response.hasMinBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_ALPHA_CHAR_RULE_RANGE, [response.getMinBound(), response.getMaxBound()] as Object[])
                } else if(response.hasMaxBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_ALPHA_CHAR_RULE_MAX, [response.getMaxBound()] as Object[])
                } else if(response.hasMinBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_ALPHA_CHAR_RULE_MIN, [response.getMinBound()] as Object[])
                } else {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_ALPHA_CHAR_RULE)
                }
            }
        })
        passwordErrormap.put(ResponseCode.FAIL_LOWER_CASE_RULE, new PasswordResponseHandler() {

            @Override
            public ErrorToken handle(PasswordValidationResponse response) {
                if(response.hasMaxBound() && response.hasMinBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_LOWER_CASE_RULE_RANGE, [response.getMinBound(), response.getMaxBound()] as Object[])
                } else if(response.hasMaxBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_LOWER_CASE_RULE_MAX, [response.getMaxBound()] as Object[])
                } else if(response.hasMinBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_LOWER_CASE_RULE_MIN, [response.getMinBound()] as Object[])
                } else {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_LOWER_CASE_RULE)
                }
            }
        })
        passwordErrormap.put(ResponseCode.FAIL_UPPER_CASE_RULE, new PasswordResponseHandler() {

            @Override
            public ErrorToken handle(PasswordValidationResponse response) {
                if(response.hasMaxBound() && response.hasMinBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_UPPER_CASE_RULE_RANGE, [response.getMinBound(), response.getMaxBound()] as Object[])
                } else if(response.hasMaxBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_UPPER_CASE_RULE_MAX, [response.getMaxBound()] as Object[])
                } else if(response.hasMinBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_UPPER_CASE_RULE_MIN, [response.getMinBound()] as Object[])
                } else {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_UPPER_CASE_RULE)
                }
            }
        })
        passwordErrormap.put(ResponseCode.FAIL_NON_APHANUMERIC_RULE, new PasswordResponseHandler() {

            @Override
            public ErrorToken handle(PasswordValidationResponse response) {
                if(response.hasMaxBound() && response.hasMinBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_NON_APHANUMERIC_RULE_RANGE, [response.getMinBound(), response.getMaxBound()] as Object[])
                } else if(response.hasMaxBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_NON_APHANUMERIC_RULE_MAX, [response.getMaxBound()] as Object[])
                } else if(response.hasMinBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_NON_APHANUMERIC_RULE_MIN, [response.getMinBound()] as Object[])
                } else {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_NON_APHANUMERIC_RULE)
                }
            }
        })
        passwordErrormap.put(ResponseCode.FAIL_NUMERIC_CHAR_RULE, new PasswordResponseHandler() {

            @Override
            public ErrorToken handle(PasswordValidationResponse response) {
                if(response.hasMaxBound() && response.hasMinBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_NUMERIC_CHAR_RULE_RANGE, [response.getMinBound(), response.getMaxBound()] as Object[])
                } else if(response.hasMaxBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_NUMERIC_CHAR_RULE_MAX, [response.getMaxBound()] as Object[])
                } else if(response.hasMinBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_NUMERIC_CHAR_RULE_MIN, [response.getMinBound()] as Object[])
                } else {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_NUMERIC_CHAR_RULE)
                }
            }
        })
        passwordErrormap.put(ResponseCode.FAIL_HISTORY_RULE, new PasswordResponseHandler() {

            @Override
            public ErrorToken handle(PasswordValidationResponse response) {
                return new ErrorToken(Errors.CHANGE_PWD_FAIL_HISTORY_RULE)
            }
        })
        passwordErrormap.put(ResponseCode.FAIL_LENGTH_RULE, new PasswordResponseHandler() {

            @Override
            public ErrorToken handle(PasswordValidationResponse response) {
                if(response.hasMaxBound() && response.hasMinBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_LENGTH_RULE_RANGE, [response.getMinBound(), response.getMaxBound()] as Object[])
                } else if(response.hasMaxBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_LENGTH_RULE_MAX, [response.getMaxBound()] as Object[])
                } else if(response.hasMinBound()) {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_LENGTH_RULE_MIN, [response.getMinBound()] as Object[])
                } else {
                    return new ErrorToken(Errors.CHANGE_PWD_FAIL_LENGTH_RULE)
                }
            }
        })
        passwordErrormap.put(ResponseCode.FAIL_NEQ_NAME, new PasswordResponseHandler() {

            @Override
            public ErrorToken handle(PasswordValidationResponse response) {
                return new ErrorToken(Errors.CHANGE_PWD_FAIL_NEQ_NAME)
            }
        })
        passwordErrormap.put(ResponseCode.FAIL_NEQ_PASSWORD, new PasswordResponseHandler() {

            @Override
            public ErrorToken handle(PasswordValidationResponse response) {
                return new ErrorToken(Errors.CHANGE_PWD_FAIL_NEQ_PASSWORD)
            }
        })
        passwordErrormap.put(ResponseCode.FAIL_NEQ_PRINCIPAL, new PasswordResponseHandler() {

            @Override
            public ErrorToken handle(PasswordValidationResponse response) {
                return new ErrorToken(Errors.CHANGE_PWD_FAIL_NEQ_PRINCIPAL)
            }
        })
        passwordErrormap.put(ResponseCode.FAIL_PASSWORD_CHANGE_FREQUENCY, new PasswordResponseHandler() {

            @Override
            public ErrorToken handle(PasswordValidationResponse response) {
                return new ErrorToken(Errors.CHANGE_PWD_FAIL_PASSWORD_CHANGE_FREQUENCY)
            }
        })
        passwordErrormap.put(ResponseCode.FAIL_PASSWORD_CHANGE_ALLOW, new PasswordResponseHandler() {

            @Override
            public ErrorToken handle(PasswordValidationResponse response) {
                return new ErrorToken(Errors.CHANGE_PWD_FAIL_PASSWORD_CHANGE_ALLOW)
            }
        })
        passwordErrormap.put(ResponseCode.FAIL_REJECT_CHARS_IN_PSWD, new PasswordResponseHandler() {

            @Override
            public ErrorToken handle(PasswordValidationResponse response) {
                return new ErrorToken(Errors.CHANGE_PWD_FAIL_REJECT_CHARS_IN_PSWD, response.getResponseValueAsArray())
            }
        })
        passwordErrormap.put(ResponseCode.FAIL_MIN_WORDS_PASSPHRASE_RULE, new PasswordResponseHandler() {

            @Override
            public ErrorToken handle(PasswordValidationResponse response) {
                return new ErrorToken(Errors.FAIL_MIN_WORDS_PASSPHRASE_RULE)
            }
        })
        passwordErrormap.put(ResponseCode.FAIL_REPEAT_SAME_WORD_PASSPHRASE_RULE, new PasswordResponseHandler() {

            @Override
            public ErrorToken handle(PasswordValidationResponse response) {
                return new ErrorToken(Errors.FAIL_REPEAT_SAME_WORD_PASSPHRASE_RULE)
            }
        })
        passwordErrormap.put(ResponseCode.FAIL_OTHER, new PasswordResponseHandler() {

            @Override
            public ErrorToken handle(PasswordValidationResponse response) {
                return new ErrorToken(Errors.CHANGE_PASSWORD_FAILED)
            }
        })
        passwordErrormap.put(ResponseCode.FAIL_LIMIT_NUM_REPEAT_CHAR, new PasswordResponseHandler() {

            @Override
            public ErrorToken handle(PasswordValidationResponse response) {
                return new ErrorToken(Errors.CHANGE_PWD_FAIL_LIMIT_NUM_REPEAT_CHAR, response.getResponseValueAsArray())
            }
        })
    }

    private interface PasswordResponseHandler {
        public ErrorToken handle(final PasswordValidationResponse response)
    }

    private boolean getUsePolicyStatus(final ITPolicy itPolicy, final User user) {
        def status = true
        if (itPolicy?.active) {
            status = false
            def date = user.dateITPolicyApproved
            if (date?.after(itPolicy.createDate)) {
                if (itPolicy.approveType == ITPolicyApproveType.ANNUALLY) {
//                    date = itPolicy.createDate
                    status = new Date().before(DateUtils.addYears(date, 1))
                } else if (itPolicy.approveType == ITPolicyApproveType.ONCE) {
                    status = true
                }
            }
        }
        return status
    }

    private Language getCurrentLanguage() {
        return OpeniamFilter.getCurrentLangauge(getCurrentRequest())
    }

    private HttpServletRequest getCurrentRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.requestAttributes)?.request
    }

    private String getCurrentIPAddress() {
        def request = getCurrentRequest()
        def ip = request.getHeader("X-Forwarded-For")
        if (!ip || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP")
        }
        if (!ip || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP")
        }
        if (!ip || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP")
        }
        if (!ip || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR")
        }
        if (!ip || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr()
        }
        return request?.getHeader('X-FORWARDED-FOR')?: request?.remoteAddr
    }

    Boolean isSecureAnswers() {
        return secureAnswers
    }

    void setSecureAnswers(Boolean secureAnswers) {
        this.secureAnswers = secureAnswers
    }

}