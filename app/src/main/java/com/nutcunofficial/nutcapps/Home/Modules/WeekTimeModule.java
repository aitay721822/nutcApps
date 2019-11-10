package com.nutcunofficial.nutcapps.Home.Modules;

import android.util.Log;

import com.alamkanak.weekview.WeekViewDisplayable;
import com.alamkanak.weekview.WeekViewEvent;
import com.nutcunofficial.nutcapps.Home.Activitys.ClassmatesActivity;
import com.nutcunofficial.nutcapps.util.CalendarUtils;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class WeekTimeModule implements WeekViewDisplayable<WeekTimeModule> {

    public static final int[][] g_Classtime1 = {{810, 900}, {910, 1000}, {1010, 1100}, {1110, 1200}, {1325, 1415}, {1420, 1510}, {1520, 1610}, {1615, 1705}, {1710, 1800}, {1810, 1900}, {1910, 2000}, {2010, 2100}, {2110, 2200}};
    public static final int[][] g_Classtime1X = {{810, 900}, {910, 1000}, {1010, 1100}, {1110, 1200}, {1325, 1415}, {1420, 1510}, {1520, 1610}, {1615, 1705}, {1710, 1800}, {1810, 1900}, {1910, 2000}, {2010, 2100}, {2110, 2200}};
    public static final int[][] g_Classtime2 = {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {1340, 1425}, {1430, 1515}, {1520, 1605}, {1610, 1655}, {1800, 1845}, {1850, 1935}, {1940, 2025}, {2030, 2115}, {2120, 2205}};
    public static final int[][] g_Classtime2X = {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {1340, 1425}, {1430, 1515}, {1520, 1605}, {1610, 1655}, {1700, 1745}, {1825, 1910}, {1915, 2000}, {2005, 2050}, {2055, 2140}};
    public static final int[][] g_Classtime3 = {{810, 855}, {910, 955}, {1010, 1055}, {1110, 1155}, {1325, 1410}, {1420, 1505}, {1520, 1605}, {1615, 1700}, {1710, 1755}, {1800, 1845}, {1850, 1935}, {1940, 2025}, {2030, 2115}, {2120, 2205}};
    public static final int[][] g_Classtime3X = {{820, 905}, {910, 955}, {1005, 1050}, {1055, 1140}, {1250, 1335}, {1340, 1425}, {1430, 1515}, {1520, 1605}, {1610, 1655}, {1700, 1745}, {1825, 1910}, {1915, 2000}, {2005, 2050}, {2055, 2140}};

    private static final String TAG = WeekTimeModule.class.getSimpleName();

    private long id;
    private String classSit;
    private String className;
    private String teacherName;
    private int classDay;
    private int classTime;
    private int color;

    public WeekTimeModule(long id, String className, String teacherName, int classDay, int classTime,String classSit, int color) {
        this.id = id;
        this.className = className;
        this.teacherName = teacherName;
        this.classDay = classDay;
        this.classTime = classTime;
        this.color = color;
        this.classSit =classSit;
    }

    public long getId() {
        return id;
    }

    public String getClassName() {
        return className;
    }

    public String getClassSit() {
        return classSit;
    }

    public void setClassSit(String classSit) {
        this.classSit = classSit;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getStartTime() {
        int sd = Integer.valueOf(String.valueOf(String.valueOf(getId()).charAt(0)));
        return get_class_time(sd,classDay,classTime)[0];
    }

    public String getEndTime() {
        int sd = Integer.valueOf(String.valueOf(String.valueOf(getId()).charAt(0)));
        return get_class_time(sd,classDay,classTime)[1];
    }

    public int getColor() {
        return color;
    }

    public int getClassDay() {

        CalendarUtils utils = CalendarUtils.getInstance();
        Calendar ClassDay = utils.getCurrentWeekWithOffset(classDay - 1);

        Log.e(TAG,String.valueOf(ClassDay.get(Calendar.DAY_OF_MONTH)));

        return ClassDay.get(Calendar.DAY_OF_MONTH);
    }

    public String[] get_class_time(int sd,int wd,int sec){
        int[][] nowTimeResult = new int[13][2];
        if(wd>5){
            // SELECT "X"
            switch(sd){
                case 1:
                    nowTimeResult = g_Classtime1X;
                    break;
                case 2:
                    nowTimeResult = g_Classtime2X;
                    break;
                case 3:
                    nowTimeResult = g_Classtime3X;
                    break;
            }
        }
        else{
            // SELECT NO "X"
            switch(sd){
                case 1:
                    nowTimeResult = g_Classtime1;
                    break;
                case 2:
                    nowTimeResult = g_Classtime2;
                    break;
                case 3:
                    nowTimeResult = g_Classtime3;
                    break;
            }
        }
        // nowTime
        int[] table = nowTimeResult[sec-1];
        String fillZeroStartTime = String.format("%04d",table[0]);
        String fillZeroEndTime = String.format("%04d",table[1]);
        String startTime = fillZeroStartTime.substring(0,2) + ":" + fillZeroStartTime.substring(2);
        String endTime =  fillZeroEndTime.substring(0,2) + ":" + fillZeroEndTime.substring(2);
        return new String[]{startTime,endTime};
    }

    @NotNull
    @Override
    public WeekViewEvent<WeekTimeModule> toWeekViewEvent() {
        WeekViewEvent.Style style = new WeekViewEvent.Style.Builder()
                .setBackgroundColor(getColor())
                .setTextStrikeThrough(false)
                .build();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date start = new Date();
        Date end = new Date();
        try {
            start = sdf.parse(getStartTime());
            end = sdf.parse(getEndTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Set Event as DAY + Hours View
        CalendarUtils calendarUtils = CalendarUtils.getInstance();

        Calendar startTime = (Calendar) calendarUtils.getNow().clone();
        startTime.setTimeInMillis(start.getTime());
        startTime.set(Calendar.YEAR, calendarUtils.getNow().get(Calendar.YEAR));
        startTime.set(Calendar.MONTH, calendarUtils.getNow().get(Calendar.MONTH));
        startTime.set(Calendar.DAY_OF_MONTH, getClassDay());

        Calendar endTime = (Calendar) startTime.clone();
        endTime.setTimeInMillis(end.getTime());
        endTime.set(Calendar.YEAR, startTime.get(Calendar.YEAR));
        endTime.set(Calendar.MONTH, startTime.get(Calendar.MONTH));
        endTime.set(Calendar.DAY_OF_MONTH, startTime.get(Calendar.DAY_OF_MONTH));

        return new WeekViewEvent.Builder<WeekTimeModule>(this)
                .setId(id)
                .setTitle(getClassName())
                .setStartTime(startTime)
                .setEndTime(endTime)
                .setStyle(style)
                .build();
    }

}
