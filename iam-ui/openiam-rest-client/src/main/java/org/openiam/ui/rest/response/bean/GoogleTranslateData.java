package org.openiam.ui.rest.response.bean;

import java.io.Serializable;
import java.util.List;

public class GoogleTranslateData implements Serializable {
    private List<GoogleTranslation> translations;

    public List<GoogleTranslation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<GoogleTranslation> translations) {
        this.translations = translations;
    }
}
