package org.openiam.ui.rest.api.model;

import org.openiam.ui.web.model.AbstractBean;

public class TranslateBean extends AbstractBean {
    private String text;
    private String sourceLang;
    private String targetLang;

    public TranslateBean(){}
    public TranslateBean(TranslateBean bean){
        this.text = bean.text;
        this.sourceLang=bean.sourceLang;
        this.targetLang=bean.targetLang;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSourceLang() {
        return sourceLang;
    }

    public void setSourceLang(String sourceLang) {
        this.sourceLang = sourceLang;
    }

    public String getTargetLang() {
        return targetLang;
    }

    public void setTargetLang(String targetLang) {
        this.targetLang = targetLang;
    }
}
