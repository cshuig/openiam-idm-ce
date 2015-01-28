package org.openiam.ui.web.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class DateFormatParser implements InitializingBean {

    @Value("${org.openiam.date.format}")
    private String dateFormatProp;
    @Value("${org.openiam.date.time.format}")
    private String dateTimeFormatProp;


    @Override
    public void afterPropertiesSet() throws Exception {
        if (dateFormatProp == null) {
            throw new Exception("org.openiam.date.format is null");
        }
        if (dateTimeFormatProp == null) {
            throw new Exception("org.openiam.date.time.format is null");
        }

        String sdfDateTime = new String(dateTimeFormatProp);
        String sdfDate = new String(dateFormatProp);
        String datepickerDate= "mm/dd/yy";
        int cnt = 0;

        datepickerDate = sdfDate.replaceAll("yy","y");
        Pattern pattern = Pattern.compile("([M]+)", Pattern.UNICODE_CASE);
        Matcher matcher = pattern.matcher(sdfDate);

        if (matcher.find()) {
            cnt = matcher.group(1).length();
        }
        switch(cnt) {
            case 1:
                datepickerDate = datepickerDate.replaceAll("M","m");
                break;
            case 2:
                datepickerDate = datepickerDate.replaceAll("MM","mm");
                break;
            case 3:
                datepickerDate = datepickerDate.replaceAll("MMM","M");
                break;
            case 4:
                datepickerDate = datepickerDate.replaceAll("MMMM","MM");
                break;

        }

        DateFormatStr.setSDFDateTime(sdfDateTime);
        DateFormatStr.setSDFDate(sdfDate);
        DateFormatStr.setDPDate(datepickerDate);

    }
}
