package org.openiam.ui.web.model;

public class IdentityAnswerBean {

    protected String id;
    protected String questionId;
    protected String userId;
    protected String questionAnswer;
    protected String confirmAnswer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    public String getConfirmAnswer() {
        return confirmAnswer;
    }

    public void setConfirmAnswer(String confirmAnswer) {
        this.confirmAnswer = confirmAnswer;
    }

}
