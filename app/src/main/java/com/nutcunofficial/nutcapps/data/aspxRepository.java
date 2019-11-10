package com.nutcunofficial.nutcapps.data;

import android.content.Context;
import android.util.Log;

import com.nutcunofficial.network.RequestManager;
import com.nutcunofficial.network.exception.networkException;
import com.nutcunofficial.network.listener.DisposeResponseHandler;
import com.nutcunofficial.network.listener.DisposeResponseListener;
import com.nutcunofficial.network.networkConstants;
import com.nutcunofficial.nutcapps.nutcApplication;
import com.nutcunofficial.nutcapps.util.hiddenValueParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.HashMap;

import okhttp3.Response;

public class aspxRepository implements aspxDataSource{


    private static final String TAG = aspxRepository.class.getSimpleName();

    private volatile static aspxRepository aspxRepository;

    private Context mContext;

    private aspxRepository() {
        this.mContext = nutcApplication.getInstance().getApplicationContext();
    }

    public static aspxRepository getInstance() {
        if (aspxRepository == null) {
            synchronized (SignInRepository.class) {
                if(aspxRepository == null) aspxRepository = new aspxRepository();
            }
        }
        return aspxRepository;
    }

    @Override
    public void UpdateAspNetHiddenValue(String current_Url, final aspxDataSource.UpdateAspNetHiddenValueCallBack callBack) {
        RequestManager.getInstance(mContext).RequestServerData(networkConstants.HTTP_METHOD_GET, current_Url, null, new DisposeResponseHandler(new DisposeResponseListener() {
            @Override
            public void onSuccess(Response resposeObject) {
                try {
                    String responseStr = (resposeObject).body().string();
                    Document document = Jsoup.parse(responseStr);
                    HashMap<String, String> result = hiddenValueParser.hiddenValueParser(document);
                    callBack.onLoadedHTML(result);
                } catch (Exception e) {
                    Log.d(TAG, ">> Document Parse Error >> ERROR!");
                    e.printStackTrace();
                }
                resposeObject.close();
            }
            @Override
            public void onFailure(networkException e) {
                callBack.onLoadedFailure();
            }
        }));
    }

}
