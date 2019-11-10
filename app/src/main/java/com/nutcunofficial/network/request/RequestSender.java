package com.nutcunofficial.network.request;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nutcunofficial.okHttp.ChromeHeaderBuilder;

import java.io.File;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class RequestSender {
    private static final String TAG = RequestSender.class.getSimpleName();

    public static Request createGetRequest(@NonNull String url, @Nullable RequestParams params){
        String result = url;
        StringBuilder urlBuilder = new StringBuilder(url);
        if(params!=null){
            urlBuilder.append("?");
            for(Map.Entry<String,String> entry : params.getParams().entrySet()){
                urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            result = urlBuilder.substring(0,urlBuilder.length() - 1);
        }
        return new ChromeHeaderBuilder(result).get().build();
    }

    public static Request createPostRequest(@NonNull String url, @NonNull RequestParams params){
        FormBody.Builder formBody = new FormBody.Builder();
        for(Map.Entry<String,String> entry : params.getParams().entrySet()) {
            formBody.add(entry.getKey(),entry.getValue());
        }
        return new ChromeHeaderBuilder(url).post(formBody.build()).build();
    }

    public static Request createMultipartBodyRequest(@NonNull String url, @NonNull RequestParams params, @NonNull File file, @NonNull String MediafileldName){
        MultipartBody.Builder partBody = new MultipartBody.Builder().setType(MultipartBody.FORM);

        for(Map.Entry<String,String> entry : params.getParams().entrySet()) {
            partBody.addFormDataPart(entry.getKey(),entry.getValue());
        }

        RequestBody requestBody = RequestBody.create(file,MediaType.parse("application/octet-stream"));
        partBody.addFormDataPart(MediafileldName,file.getName(),requestBody);

        return new ChromeHeaderBuilder(url).post(partBody.build()).build();
    }
}
