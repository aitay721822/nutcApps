package com.nutcunofficial.network.listener;

import android.util.Log;

import com.nutcunofficial.network.exception.networkException;

import java.io.IOException;

import okhttp3.Response;

public class DisposeResponseHandler {

    public DisposeResponseListener mListener;
    public Class<?> mClass;

    public DisposeResponseHandler(DisposeResponseListener listener){
        mListener=listener;
    }
    public DisposeResponseHandler(DisposeResponseListener listener,Class<?> clazz){
        mListener = listener;
        mClass = clazz;
    }

    public void onSuccess(Response responseObj) {
        mListener.onSuccess(responseObj);
    }

    public void onFailure(networkException exception) {
        mListener.onFailure(exception);
    }

    public Class<?> getClassType() {
        return mClass;
    }

}
