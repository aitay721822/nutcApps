package com.nutcunofficial.network.listener;

import com.nutcunofficial.network.exception.networkException;

import okhttp3.Response;

public interface DisposeResponseListener {

    void onSuccess(Response resposeObject);

    void onFailure(networkException e);
}
