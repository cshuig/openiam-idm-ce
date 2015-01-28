package org.openiam.ui.webconsole.web.mvc;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.srvc.pswd.dto.IdentityQuestion;
import org.openiam.idm.srvc.pswd.service.ChallengeResponseWebService;
import org.openiam.ui.util.messages.ErrorToken;
import org.openiam.ui.util.messages.Errors;
import org.openiam.ui.util.messages.SuccessMessage;
import org.openiam.ui.util.messages.SuccessToken;
import org.openiam.ui.web.model.BasicAjaxResponse;
import org.openiam.ui.web.mvc.AbstractController;
import org.openiam.ui.webconsole.web.model.IdentityQuestionRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class ChallengeResponseQuestionController extends AbstractController {

	private static final Log log = LogFactory
			.getLog(ChallengeResponseQuestionController.class);

	private static String GLOBAL = "GLOBAL";

	@Value("${org.openiam.ui.page.challenge.response.question.root.id}")
	private String challengeResponseQuestionRootMenuName;

	@RequestMapping("/challengeResponseQuestion")
	public String getChallengeResponseQuestions(
			final HttpServletRequest request, final HttpServletResponse response) {

		request.setAttribute("challengeResponseQuestionPageURL",
				"editChallengeResponseQuestion.html");
		setMenuTree(request, challengeResponseQuestionRootMenuName);
		return "challengeResponse/questionSearch";
	}

	@RequestMapping(value = "/editChallengeResponseQuestion", method = RequestMethod.GET)
	public String editChallengeResponseQuestion(
			final HttpServletRequest request,
			final HttpServletResponse response,
			final @RequestParam(required = false, value = "id") String id)
			throws IOException {
		IdentityQuestion identityQuestion = new IdentityQuestion();
		if (StringUtils.isNotBlank(id)) {
			identityQuestion = challengeResponseService.getQuestion(id, getCurrentLanguage());
			if (identityQuestion == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return null;
			}
		}
		request.setAttribute("identityQuestion", identityQuestion);
		request.setAttribute("questionAsJSON", jacksonMapper.writeValueAsString(identityQuestion));
		// setMenuTree(request, attributeEditPageRootMenuName);
		return "challengeResponse/edit";
	}

	@RequestMapping(value = "/saveChallengeResponseQuestion", method = RequestMethod.POST)
	public String saveChallengeResponseQuestion(final HttpServletRequest request, @RequestBody IdentityQuestion identityQuestion) {
		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		Response wsResponse = null;
		identityQuestion.setIdentityQuestGrpId(GLOBAL);
		wsResponse = challengeResponseService.saveQuestion(identityQuestion);
		if (ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {
			ajaxResponse.setRedirectURL(String.format("editChallengeResponseQuestion.html?id=%s",(String) wsResponse.getResponseValue()));
			ajaxResponse.setStatus(HttpServletResponse.SC_OK);
			ajaxResponse.setSuccessToken(new SuccessToken(SuccessMessage.CHALLENGE_RESPONSE_QUESTION_SAVED));
		} else {
			ajaxResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			Errors error = Errors.SAVE_CHALLENGE_RESPONSE_QUESTION_FAIL;
			if (wsResponse.getErrorCode() != null) {
				switch (wsResponse.getErrorCode()) {
				case NO_IDENTITY_QUESTION:
					error = Errors.IDENTITY_QUESTIONS_NOT_SET;
					break;
				case IDENTICAL_QUESTIONS:
					error = Errors.IDENTITY_QUESTIONS_DUPLICATE;
					break;
				default:
					break;
				}
			}

			ajaxResponse.addError(new ErrorToken(error));
		}

		request.setAttribute("response", ajaxResponse);
		return "common/basic.ajax.response";
	}

	@RequestMapping(value = "/deleteChallengeResponseQuestion", method = RequestMethod.POST)
	public String deleteChallengeResponseQuestion(
			final @RequestParam(value = "id") String id,
			final HttpServletResponse response, final HttpServletRequest request)
			throws IOException {

		final BasicAjaxResponse ajaxResponse = new BasicAjaxResponse();
		final Response wsResponse = challengeResponseService
				.deleteQuestion(id);

		if (ResponseStatus.SUCCESS.equals(wsResponse.getStatus())) {

			ajaxResponse.setStatus(200);
			ajaxResponse.setRedirectURL("challengeResponseQuestion.html");
			ajaxResponse.setSuccessToken(new SuccessToken(
					SuccessMessage.CHALLENGE_RESPONSE_QUESTION_DELETED));
		} else {
			// TODO check error for foreign key, and mention child exists
			ajaxResponse.addError(new ErrorToken(
					Errors.DELETE_CHALLENGE_RESPONSE_QUESTION_FAIL));
		}
		request.setAttribute("response", ajaxResponse);
		return "common/basic.ajax.response";
	}

}
