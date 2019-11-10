package com.nutcunofficial.network.response;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.nutcunofficial.network.exception.networkException;
import com.nutcunofficial.network.listener.DisposeResponseHandler;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class GeneralResponseHandler implements Callback {

    private static final String TAG = "Response Handler";

    private DisposeResponseHandler mHandler;

    private static final String NETWORK_ERROR_MSG = "Request not accepted.";
    private static final int NETWORK_ERROR = -1;

    public GeneralResponseHandler(DisposeResponseHandler handler){
        this.mHandler = handler;
    }

    @Override
    public void onFailure(@NotNull Call call, @NotNull IOException e) {
        mHandler.onFailure(new networkException(NETWORK_ERROR,NETWORK_ERROR_MSG));
    }

    @Override
    public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
        mHandler.onSuccess(response);
    }
}
