package org.openiam.ui.web.model;

import java.util.List;

import org.openiam.idm.srvc.pswd.dto.IdentityQuestion;
import org.openiam.idm.srvc.pswd.dto.UserIdentityAnswer;

public class ChallengeResponseModel {

	private List<IdentityQuestion> questionList;
	private UserIdentityAnswer answer;
	private boolean markedAsDeleted;
	
	public ChallengeResponseModel(final List<IdentityQuestion> questionList, final UserIdentityAnswer answer) {
		this.questionList = questionList;
		this.answer = answer;
	}
	
	public List<IdentityQuestion> getQuestionList() {
		return questionList;
	}
	
	public UserIdentityAnswer getAnswer() {
		return answer;
	}

	public boolean isMarkedAsDeleted() {
		return markedAsDeleted;
	}

	public void setMarkedAsDeleted(boolean markedAsDeleted) {
		this.markedAsDeleted = markedAsDeleted;
	}
}
