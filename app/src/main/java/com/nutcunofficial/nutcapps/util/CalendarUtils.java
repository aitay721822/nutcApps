package com.nutcunofficial.nutcapps.util;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class CalendarUtils {
    private static CalendarUtils calendarUtils;
    private Calendar now;

    public static CalendarUtils getInstance(){
        if(calendarUtils==null){
            synchronized (CalendarUtils.class){
                calendarUtils = new CalendarUtils();
            }
        }
        return calendarUtils;
    }

    private CalendarUtils(){
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Taipei"));
        now = Calendar.getInstance(Locale.TAIWAN);
        now.setTimeInMillis(System.currentTimeMillis());
        now.clear(Calendar.HOUR_OF_DAY);
        now.clear(Calendar.MINUTE);
        now.clear(Calendar.SECOND);
        now.clear(Calendar.MILLISECOND);
        now.setFirstDayOfWeek(Calendar.MONDAY);
    }

    public Calendar getNow(){
        return now;
    }

    public Calendar getCurrentWeekFirstDay(){
        Calendar startTime = (Calendar)now.clone();
        startTime.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
        return (Calendar)startTime.clone();
    }

    public Calendar getCurrentWeekLastDay(){
        Calendar firstDay = getCurrentWeekFirstDay();
        Calendar lastDay = (Calendar)firstDay.clone();
        lastDay.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
        return (Calendar)lastDay.clone();
    }

    public Calendar getCurrentWeekWithOffset(int days){
        Calendar firstDay = getCurrentWeekFirstDay();
        firstDay.add(Calendar.DAY_OF_WEEK,days);
        return (Calendar)firstDay.clone();
    }

}
