package org.openiam.ui.web.util;

public class DateFormatStr {

    public static String sdfDate;
    public static String sdfDateTime;
    public static String dpDate; //datepicker date format


    public static void setSDFDate(String sdfDate) {
        DateFormatStr.sdfDate = sdfDate;
    }
    public static void setSDFDateTime(String sdfDateTime) {
        DateFormatStr.sdfDateTime = sdfDateTime;
    }
    public static void setDPDate(String dpDate) {
        DateFormatStr.dpDate = dpDate;
    }

    public static String getSdfDate() {
        return sdfDate;
    }

    public static String getSdfDateTime() {
        return sdfDateTime;
    }

    public static String getDpDate() {
        return dpDate;
    }

}
