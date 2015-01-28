package org.openiam.ui.webconsole.web.mvc;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.openiam.idm.searchbeans.IdentityQuestionSearchBean;
import org.openiam.idm.searchbeans.PolicySearchBean;
import org.openiam.idm.srvc.policy.dto.Policy;
import org.openiam.idm.srvc.policy.service.PolicyDataService;
import org.openiam.idm.srvc.pswd.dto.IdentityQuestion;
import org.openiam.idm.srvc.pswd.service.ChallengeResponseWebService;
import org.openiam.ui.dozer.DozerBeanMapper;
import org.openiam.ui.rest.api.model.IdentityQuestionBean;
import org.openiam.ui.web.mvc.AbstractController;
import org.openiam.ui.webconsole.web.model.IdentityQuestionSearchResultBean;
import org.openiam.ui.webconsole.web.model.PolicyBean;
import org.openiam.ui.webconsole.web.model.PolicySearchResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ChallengeResponseQuestionRestfulController extends AbstractController {

	@Resource(name = "challengeResponseServiceClient")
	private ChallengeResponseWebService challengeResponseServiceClient;
	
	@RequestMapping(value="/searchChallengeResponseQuestions", method=RequestMethod.GET)
	public @ResponseBody IdentityQuestionSearchResultBean searchChallengeResponseQuestions(final HttpServletRequest request,
												   final @RequestParam(required=true, value="from") int from,
												   final @RequestParam(required=true, value="size") int size) {
		final IdentityQuestionSearchBean searchBean = new IdentityQuestionSearchBean();
		searchBean.setDeepCopy(false);
		final int count = challengeResponseServiceClient.count(searchBean);
		final List<IdentityQuestion> identityQuestionList = challengeResponseServiceClient.findQuestionBeans(searchBean, from, size, getCurrentLanguage());
		final List<IdentityQuestionBean> identityQuestionBeanList = mapper.mapToList(identityQuestionList, IdentityQuestionBean.class);
		return new IdentityQuestionSearchResultBean(count, identityQuestionBeanList);
	}
}
