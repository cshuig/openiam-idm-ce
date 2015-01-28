package org.openiam.ui.idp.web.model;

import java.util.List;

public class UnlockUserRequestModel {

    private String userId;
    private String token;

    private  List<QuestionAnswerBean> answers;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<QuestionAnswerBean> getAnswers() {
        return answers;
    }

    public void setAnswers(List<QuestionAnswerBean> answers) {
        this.answers = answers;
    }
}
