package com.nutcunofficial.nutcapps.util;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogUtil {

    private static final String TAG = LogUtil.class.getSimpleName();

    private enum LOG_TYPE
    {
        INFORMATION,
        EXCEPTION,
        DEBUG
    }

    private final static int NEW_LOG = 1;
    private final static int ORIGIN_LOG = 0;
    private Application application;
    private static volatile LogUtil instance;

    private int USE_LOG = ORIGIN_LOG;

    public static LogUtil newInstance(int requestCode, Activity activity, Application application){
        if(instance == null){
            synchronized (LogUtil.class){
                if(instance==null){
                    instance = new LogUtil(requestCode,activity,application);
                }
            }
        }
        return instance;
    }

    private LogUtil(int requestCode, @NonNull Activity activity,Application application) {
        if(ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE},requestCode);
            USE_LOG = ORIGIN_LOG;
        }
        else{
            USE_LOG = NEW_LOG;
        }
        this.application=application;
    }

    /*
     SEND 紀錄執行資訊
     params: description 描述該執行資訊
     */
    public void send(LOG_TYPE Type,String description){
        if(USE_LOG == NEW_LOG){
            try {
                appendLog(Type,description);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else Log.i(TAG,description);
    }

    private synchronized void appendLog(LOG_TYPE Type , String body) throws IOException {
        File file =  new File(application.getApplicationContext().getFilesDir(),"nutc.log");
        if(!file.exists()) file.createNewFile();
        else{
            FileWriter fw = new FileWriter(file);
            fw.append(String.format("%s <%s> : %s\n",TAG,Type.name(),body));
            fw.flush();
            fw.close();
        }
    }

}
