package com.nutcunofficial.network.request;

import android.text.TextUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RequestParams {

    private ConcurrentHashMap<String,String> params=  new ConcurrentHashMap<>();

    public RequestParams(){}

    public RequestParams(String key,String value){
        put(key, value);
    }

    public RequestParams(Map<String,String> source){
        if(source!=null){
            for(Map.Entry<String,String> entry : source.entrySet()){
                put(entry.getKey(),entry.getValue());
            }
        }
    }

    private void put(String key,String value){
        if(!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)){
            this.params.put(key, value);
        }
    }

    public ConcurrentHashMap<String,String> getParams(){
        return params;
    }

}
