package org.openiam.ui.web.model;

import java.util.List;

import org.openiam.idm.srvc.pswd.dto.UserIdentityAnswer;

public class ChallengeResponseRequest {

    private String unchangedValue;
    private List<String> deleteList;
	private List<IdentityAnswerBean> answerList;
    private String postbackUrl;

	public List<IdentityAnswerBean> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(List<IdentityAnswerBean> answerList) {
		this.answerList = answerList;
	}

	public List<String> getDeleteList() {
		return deleteList;
	}

	public void setDeleteList(List<String> deleteList) {
		this.deleteList = deleteList;
	}

    public String getUnchangedValue() {
        return unchangedValue;
    }

    public void setUnchangedValue(String unchangedValue) {
        this.unchangedValue = unchangedValue;
    }

    public String getPostbackUrl() {
        return postbackUrl;
    }

    public void setPostbackUrl(String postbackUrl) {
        this.postbackUrl = postbackUrl;
    }
}
