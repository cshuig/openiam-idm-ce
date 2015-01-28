package org.openiam.ui.webconsole.web.model;

import org.openiam.ui.web.util.DateFormatStr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HistorySearchModel {
    private static SimpleDateFormat sdf = new SimpleDateFormat(DateFormatStr.getSdfDate());
    private String userId;
    private String startDate;
    private String endDate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStartDate() {
        return startDate;
    }

    public Date getStartDateAsDate() {
        Date result = null;
        if(startDate!=null && !startDate.trim().isEmpty()){
            try {
                result= sdf.parse(startDate);
            } catch (ParseException e) {
                result=null;
            }
        }
        return result;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setStartDateFromDate(Date startDate) {
        this.startDate = sdf.format(startDate);
    }

    public String getEndDate() {
        return endDate;
    }

    public Date getEndDateAsDate() {
        Date result = null;
        if(endDate!=null && !endDate.trim().isEmpty()){
            try {
                result= sdf.parse(endDate);
            } catch (ParseException e) {
                result=null;
            }
        }
        return result;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public void setEndDateFromDate(Date endDate) {
        this.endDate = sdf.format(endDate);
    }
}
