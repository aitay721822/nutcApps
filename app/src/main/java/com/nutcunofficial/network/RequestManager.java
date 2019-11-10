package com.nutcunofficial.network;

import android.content.Context;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.nutcunofficial.network.listener.DisposeResponseHandler;
import com.nutcunofficial.network.request.RequestParams;
import com.nutcunofficial.network.request.RequestSender;
import com.nutcunofficial.network.response.GeneralResponseHandler;
import com.nutcunofficial.nutcapps.R;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RequestManager {
    private static volatile RequestManager requestManager;
    private OkHttpClient mClient;
    private Call mCall;

    private RequestManager(Context context){

        ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(),
                new SharedPrefsCookiePersistor(context));

        mClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(networkConstants.HTTP_RETRY_ON_FAILURE)
                .followSslRedirects(networkConstants.HTTP_FOLLOW_REDIRECTS)
                .followRedirects(networkConstants.HTTP_FOLLOW_REDIRECTS)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .connectTimeout(networkConstants.HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(networkConstants.HTTP_CONNECT_READ_TIMEOUT,TimeUnit.SECONDS)
                .writeTimeout(networkConstants.HTTP_CONNECT_WRITE_TIMEOUT,TimeUnit.SECONDS)
                .cookieJar(cookieJar)
                .build();
    }

    public void RequestServerData(int method, String url, HashMap<String,String> paramsMap, DisposeResponseHandler handler){
        RequestParams params = new RequestParams(paramsMap);
        Request request = null;
        if (method == networkConstants.HTTP_METHOD_GET){
            request = RequestSender.createGetRequest(url,params);
        }
        else if (method == networkConstants.HTTP_METHOD_POST){
            request = RequestSender.createPostRequest(url,params);
        }
        if(request!=null) {
            mCall = mClient.newCall(request);
            mCall.enqueue(new GeneralResponseHandler(handler));
        }
    }


    public void RequestServerData(int method, String url, HashMap<String,String> paramsMap, File file, String MediaFieldName, DisposeResponseHandler handler){
        RequestParams params = new RequestParams(paramsMap);
        Request request = null;
        if (method == networkConstants.HTTP_METHOD_GET){
            request = RequestSender.createGetRequest(url,params);
        }
        else if (method == networkConstants.HTTP_METHOD_POST){
            request = RequestSender.createPostRequest(url,params);
        }
        else if(method == networkConstants.HTTP_METHOD_POST_UPLOAD){
            request = RequestSender.createMultipartBodyRequest(url,params,file,MediaFieldName);
        }
        if(request!=null) {
            mCall = mClient.newCall(request);
            mCall.enqueue(new GeneralResponseHandler(handler));
        }
    }

    public void CancelRequest(){
        mClient.dispatcher().cancelAll();
    }

    public static RequestManager getInstance(Context mContext){
        if(requestManager==null){
            synchronized (RequestManager.class){
                requestManager = new RequestManager(mContext);
            }
        }
        return requestManager;
    }

    public OkHttpClient getClient(){
        return mClient;
    }
}
