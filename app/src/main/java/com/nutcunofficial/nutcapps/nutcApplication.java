package com.nutcunofficial.nutcapps;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

public class nutcApplication extends Application {

    private static nutcApplication nutcApplication;
    private static Resources resources;

    @Override
    public void onCreate() {
        super.onCreate();
        nutcApplication = this;
        resources = getResources();
    }

    public static nutcApplication getInstance(){
        return nutcApplication;
    }

    public static Resources getResourses() {
        return resources;
    }

    public static String ResourcesString(int Id){
        if(resources!=null){
            return resources.getString(Id);
        }
        return "";
    }

}
