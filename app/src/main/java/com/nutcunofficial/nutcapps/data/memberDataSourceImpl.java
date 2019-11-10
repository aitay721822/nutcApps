package com.nutcunofficial.nutcapps.data;

import android.content.Context;

import com.nutcunofficial.network.RequestManager;
import com.nutcunofficial.network.exception.networkException;
import com.nutcunofficial.network.listener.DisposeResponseHandler;
import com.nutcunofficial.network.listener.DisposeResponseListener;
import com.nutcunofficial.network.networkConstants;
import com.nutcunofficial.nutcapps.nutcApplication;
import com.nutcunofficial.nutcapps.util.RegexParser;
import com.nutcunofficial.nutcapps.util.nutcStatusCode;
import com.nutcunofficial.nutcapps.util.nutcUrl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import okhttp3.Response;

public abstract class memberDataSourceImpl implements memberDataSource {

    private static final String TAG = memberDataSourceImpl.class.getSimpleName();

    private Context mContext;

    memberDataSourceImpl() {
        this.mContext = nutcApplication.getInstance().getApplicationContext();
    }

    @Override
    public void checkSignIn(final checkSignInCallback callback) {
        RequestManager.getInstance(mContext).RequestServerData(networkConstants.HTTP_METHOD_GET,nutcUrl.MYAREA_URL,null,new DisposeResponseHandler(new DisposeResponseListener() {
            @Override
            public void onSuccess(Response resposeObject) {
                String url = resposeObject.networkResponse().request().url().toString().toUpperCase();
                if(url.equals(nutcUrl.MYAREA_URL.toUpperCase())){
                    callback.SignInStatus(nutcStatusCode.SIGN_IN_SUCCESS);
                }
                else if (url.equals(nutcUrl.CHANGE_URL.toUpperCase())){
                    callback.SignInStatus(nutcStatusCode.NEED_CHANGE_PASS);
                }
                else{
                    callback.SignInStatus(nutcStatusCode.NOT_SIGN_IN);
                }
                resposeObject.close();
            }

            @Override
            public void onFailure(networkException e) {
                callback.SignInStatus(nutcStatusCode.NO_INTERNET_ERROR_CODE);
            }

        }));;
    }

    @Override
    public void fetchUserImage(final memberDataSource.fetchUserImageCallBack callBack) {
        RequestManager.getInstance(mContext).RequestServerData(networkConstants.HTTP_METHOD_GET, nutcUrl.EPORTAL_URL,null,new DisposeResponseHandler(new DisposeResponseListener() {
            @Override
            public void onSuccess(Response resposeObject) {
                try{
                    String HTML = resposeObject.body().string();
                    Document doc = Jsoup.parse(HTML);
                    Elements imgSources = doc.select("img[src=\"/student/images/space-png.png\"]");
                    String imagePath = RegexParser.backgroundGetter(imgSources.attr("style"));
                    if(imagePath!=null){
                        callBack.onFetchSuccess(nutcUrl.AIS_DOMAIN + imagePath);
                    }
                    else{
                        callBack.onFetchError(nutcStatusCode.NO_FETCH_IMAGE);
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                    callBack.onFetchError(nutcStatusCode.NO_FETCH_IMAGE);
                }
                resposeObject.close();
            }

            @Override
            public void onFailure(networkException e) {
                callBack.onFetchError(nutcStatusCode.NO_INTERNET_ERROR_CODE);
            }
        }));
    }
}
