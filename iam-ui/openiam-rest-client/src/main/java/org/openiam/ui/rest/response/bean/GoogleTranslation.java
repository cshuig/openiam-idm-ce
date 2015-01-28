package org.openiam.ui.rest.response.bean;

import java.io.Serializable;

public class GoogleTranslation implements Serializable {
    private String translatedText;
    private String detectedSourceLanguage;

    public String getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }

    public String getDetectedSourceLanguage() {
        return detectedSourceLanguage;
    }

    public void setDetectedSourceLanguage(String detectedSourceLanguage) {
        this.detectedSourceLanguage = detectedSourceLanguage;
    }
}