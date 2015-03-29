package org.openiam.ui.web.mvc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.searchbeans.IdentityAnswerSearchBean;
import org.openiam.idm.searchbeans.IdentityQuestionSearchBean;
import org.openiam.idm.srvc.pswd.dto.IdentityQuestion;
import org.openiam.idm.srvc.pswd.dto.UserIdentityAnswer;
import org.openiam.idm.srvc.pswd.service.PasswordGenerator;
import org.openiam.ui.security.OpeniamWebAuthenticationDetails;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.model.ChallengeResponseModel;
import org.openiam.ui.web.model.ChallengeResponseRequest;
import org.openiam.ui.web.model.IdentityAnswerBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractChallengeResponseController extends AbstractController {

	@Value("${org.openiam.selfservice.challenge.response.group}")
	private String challengeResponseGroup;

    @Value("${org.openiam.ui.challenge.answers.secured}")
    private Boolean secureAnswers;

    @RequestMapping(value="/challengeResponse",method= RequestMethod.GET)
    public String challengeResponse(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    @RequestParam(value = "postbackUrl", required = false) String postbackUrl) throws Exception {

        if (StringUtils.isNotBlank(postbackUrl)) {
            request.setAttribute("postbackUrl", postbackUrl);
        }
        request.setAttribute("readOnly", false);
        return challengeResponse(request, cookieProvider.getUserId(request));
    }

    protected String challengeResponse(final HttpServletRequest request, String userId) throws Exception {

		final IdentityQuestionSearchBean questionSearchBean = new IdentityQuestionSearchBean();
		questionSearchBean.setDeepCopy(false);
		questionSearchBean.setGroupId(challengeResponseGroup);
		questionSearchBean.setActive(true);
		
		final List<IdentityQuestion> questionList = challengeResponseService.findQuestionBeans(questionSearchBean, 0, Integer.MAX_VALUE, getCurrentLanguage());
		
		final IdentityAnswerSearchBean answerSearchBean = new IdentityAnswerSearchBean();
		answerSearchBean.setDeepCopy(true);
		answerSearchBean.setUserId(userId);
		
		final List<UserIdentityAnswer> answerList = challengeResponseService.findAnswerBeans(answerSearchBean, userId, 0, Integer.MAX_VALUE);
		
		Integer numOfRequiredQuestions = challengeResponseService.getNumOfRequiredQuestions(userId);

		numOfRequiredQuestions = (numOfRequiredQuestions == null) ? Integer.valueOf(3) : numOfRequiredQuestions;
		
		
		final List<ChallengeResponseModel> modelList = new ArrayList<ChallengeResponseModel>(numOfRequiredQuestions.intValue());
		for(int i = 0; i < numOfRequiredQuestions.intValue(); i++) {
			UserIdentityAnswer finalAnswer = null;
			if(CollectionUtils.isNotEmpty(answerList)) {
				for(final IdentityQuestion question : questionList) {
					if(finalAnswer == null) {
						for(final Iterator<UserIdentityAnswer> it = answerList.iterator(); it.hasNext();) {
							final UserIdentityAnswer answer = it.next();
							if(StringUtils.equals(question.getId(), answer.getQuestionId())) {
								finalAnswer = answer;
								it.remove();
								break;
							}
						}
					}
				}
			}
			final ChallengeResponseModel model = new ChallengeResponseModel(questionList, finalAnswer);
			modelList.add(model);
		}
		
		if(CollectionUtils.isNotEmpty(answerList)) {
			for(final UserIdentityAnswer answer : answerList) {
				final ChallengeResponseModel model = new ChallengeResponseModel(questionList, answer);
				model.setMarkedAsDeleted(true);
				modelList.add(model);
			}
		}

		request.setAttribute("modelList", modelList);
        request.setAttribute("secureAnswers", secureAnswers);
        request.setAttribute("unchangedValue", PasswordGenerator.generatePassword(16));
		//request.getRequestDispatcher("users/challengeResponse.jsp").forward(request, response);
		return "jar:users/challengeResponse";
	}

    @RequestMapping(value="/challengeResponse",method=RequestMethod.POST)
    public String challengeResponsePost(final HttpServletRequest request, final @RequestBody ChallengeResponseRequest challengeRequest) {
        final String userId = cookieProvider.getUserId(request);

        Errors error = null;
        final List<UserIdentityAnswer> answerList = new ArrayList<UserIdentityAnswer>();
        if(CollectionUtils.isNotEmpty(challengeRequest.getAnswerList())) {
            for(final IdentityAnswerBean answer : challengeRequest.getAnswerList()) {
                if (secureAnswers) {
                    if (!StringUtils.equals(answer.getQuestionAnswer(), answer.getConfirmAnswer())) {
                        error = Errors.ANSWERS_NOT_EQUAL;
                        break;
                    }
                    if (StringUtils.equals(answer.getQuestionAnswer(), challengeRequest.getUnchangedValue())) {
                        continue;
                    }
                }
                UserIdentityAnswer answerDTO = new UserIdentityAnswer();
                answerDTO.setUserId(userId);
                answerDTO.setId(StringUtils.trimToNull(answer.getId()));
                answerDTO.setQuestionId(answer.getQuestionId());
                answerDTO.setQuestionAnswer(answer.getQuestionAnswer());
                answerList.add(answerDTO);
            }
        }

        final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
        if(CollectionUtils.isNotEmpty(challengeRequest.getDeleteList())) {
            for(final String answerId : challengeRequest.getDeleteList()) {
                challengeResponseService.deleteAnswer(answerId);
            }
        }

        if (error == null && CollectionUtils.isNotEmpty(answerList)) {
            final Response wsResponse = challengeResponseService.saveAnswers(answerList);
            if(wsResponse.getStatus() != ResponseStatus.SUCCESS) {
                error = Errors.INTERNAL_ERROR;
                if(wsResponse.getErrorCode() != null) {
                    switch(wsResponse.getErrorCode()) {
                        case NO_ANSWER_TO_QUESTION:
                            error = Errors.NO_QUESTION_TO_ANSWER;
                            break;
                        case IDENTICAL_QUESTIONS:
                            error = Errors.IDENTICAL_QUESTION;
                            break;

                        case QUEST_NOT_SELECTED:
                            error = Errors.QUESTION_NOT_TAKEN;
                            break;

                        case ANSWER_NOT_TAKEN:
                            error = Errors.ANSWER_NOT_TAKEN;
                            break;


                        default:
                            error = Errors.CHALLENGE_RESPONSES_NOT_SAVED;
                    }
                }
            }
        }

        if (error == null) {
            final SecurityContext ctx = SecurityContextHolder.getContext();
            final Authentication authentication = (ctx != null) ? ctx.getAuthentication() : null;
            if(authentication != null && authentication.getDetails() != null) {
                ((OpeniamWebAuthenticationDetails)authentication.getDetails()).setIdentityQuestionsAnswered(true);
            }
            if (StringUtils.isNotBlank(challengeRequest.getPostbackUrl())) {
                ajaxResponse.setRedirectURL(challengeRequest.getPostbackUrl());
            }
            ajaxResponse.setStatus(200);
            ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.CHALLENGE_RESPONSE_SAVED));
        } else {
            ajaxResponse.addError(new ErrorToken(error));
            ajaxResponse.setStatus(500);
        }
        request.setAttribute("response", ajaxResponse);
        return "common/basic.ajax.response";
    }
	
}
