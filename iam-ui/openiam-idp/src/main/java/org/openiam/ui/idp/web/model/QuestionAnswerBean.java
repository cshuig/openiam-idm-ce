package org.openiam.ui.idp.web.model;

/**
 * Created by zaporozhec on 11/27/14.
 */
public class QuestionAnswerBean {
    private String questionId;
    private String answerValue;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getAnswerValue() {
        return answerValue;
    }

    public void setAnswerValue(String answerValue) {
        this.answerValue = answerValue;
    }
}
