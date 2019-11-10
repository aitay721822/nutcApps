package com.nutcunofficial.nutcapps.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import java.util.Map;

public class SharedPreferenceManager {

    SharedPreferences preferences;

    public SharedPreferenceManager(Context context,String name, int mode){
        preferences = context.getSharedPreferences(name,mode);
    }

    public void write(String key,String value){
        preferences.edit().putString(key,value).apply();
    }

    public void write(String key,int value){
        preferences.edit().putInt(key,value).apply();
    }

    public void write(String key,float value){
        preferences.edit().putFloat(key,value).apply();
    }

    public void write(String key,boolean value){
        preferences.edit().putBoolean(key,value).apply();
    }

    public void write(String key,long value){
        preferences.edit().putLong(key,value).apply();
    }

    public String read(String key,@Nullable String default_String){
        return preferences.getString(key,default_String);
    }

    public int read(String key, int default_String){
        return preferences.getInt(key,default_String);
    }

    public float read(String key,@Nullable float default_String){
        return preferences.getFloat(key,default_String);
    }

    public boolean read(String key,@Nullable boolean default_String){
        return preferences.getBoolean(key,default_String);
    }

    public long read(String key,@Nullable long default_String){
        return preferences.getLong(key,default_String);
    }

    public Map<String,?> getAll(){
        return preferences.getAll();
    }
}
