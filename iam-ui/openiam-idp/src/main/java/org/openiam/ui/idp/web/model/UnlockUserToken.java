package org.openiam.ui.idp.web.model;

import java.util.List;
import java.util.Map;

import org.openiam.idm.srvc.pswd.dto.IdentityQuestion;
import org.openiam.idm.srvc.user.dto.User;

public class UnlockUserToken {

	private User user;
	private Map<String, String> hiddenAttributes;
	private List<IdentityQuestion> questionList;
	
	public UnlockUserToken(final User user, final Map<String, String> hiddenAttributes, final List<IdentityQuestion> questionList) {
		this.user = user;
		this.hiddenAttributes = hiddenAttributes;
		this.questionList = questionList;
	}

	public User getUser() {
		return user;
	}

	public Map<String, String> getHiddenAttributes() {
		return hiddenAttributes;
	}

	public List<IdentityQuestion> getQuestionList() {
		return questionList;
	}
}
