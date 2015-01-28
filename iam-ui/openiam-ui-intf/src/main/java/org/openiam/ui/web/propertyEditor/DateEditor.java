package org.openiam.ui.web.propertyEditor;

import org.apache.commons.lang.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateEditor extends PropertyEditorSupport {

    private SimpleDateFormat df;

    public DateEditor(String pattern) {
        this.df = new SimpleDateFormat(pattern);
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        Date d = null;
        if(StringUtils.isNotBlank(text)) {
            try {
                d = df.parse(text);
            } catch (ParseException pe) {
                throw new IllegalArgumentException(pe);
            }
        }
        this.setValue(d);
    }

    @Override
    public String getAsText() {
        if (this.getValue() != null) {
            return df.format((Date)this.getValue());
        } else {
            return "";
        }
    }
}