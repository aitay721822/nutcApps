package com.nutcunofficial.nutcapps.util;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.nutcunofficial.network.RequestManager;

import com.bumptech.glide.annotation.GlideModule;

import java.io.InputStream;

import okhttp3.OkHttpClient;

@GlideModule
public class nutcGlideModule extends AppGlideModule {

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        builder.setDefaultRequestOptions(RequestOptions.formatOf(DecodeFormat.PREFER_ARGB_8888));
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        OkHttpClient okHttpClient = RequestManager.getInstance(context).getClient();
        OkHttpUrlLoader.Factory factory = new OkHttpUrlLoader.Factory(okHttpClient);
        registry.replace(GlideUrl.class, InputStream.class,factory);
    }
}
