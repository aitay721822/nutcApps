package com.nutcunofficial.nutcapps.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nutcunofficial.network.RequestManager;
import com.nutcunofficial.network.exception.networkException;
import com.nutcunofficial.network.listener.DisposeResponseHandler;
import com.nutcunofficial.network.listener.DisposeResponseListener;
import com.nutcunofficial.network.networkConstants;
import com.nutcunofficial.nutcapps.R;
import com.nutcunofficial.nutcapps.nutcApplication;
import com.nutcunofficial.nutcapps.util.RegexParser;
import com.nutcunofficial.nutcapps.util.nutcStatusCode;
import com.nutcunofficial.nutcapps.util.nutcUrl;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Response;
import okhttp3.ResponseBody;

public class SignInRepository implements SignInDataSource {

    private static final String TAG = SignInRepository.class.getSimpleName();

    private volatile static SignInRepository signInRepository;

    private Context mContext;

    private SignInRepository() {
        this.mContext = nutcApplication.getInstance().getApplicationContext();
    }

    public static SignInRepository getInstance() {
        if (signInRepository == null) {
            synchronized (SignInRepository.class) {
                if(signInRepository == null) signInRepository = new SignInRepository();
            }
        }
        return signInRepository;
    }


    @Override
    public void SignIn(String account, String password, String validationCode, final SignInRepository.SignInCallBack callBack) {
        final HashMap<String,String> params = new HashMap<>();
        params.put("ctl00$ContentPlaceHolder1$Account",account);
        params.put("ctl00$ContentPlaceHolder1$Password",password);
        params.put("ctl00$ContentPlaceHolder1$ValidationCode",validationCode);
        params.put("ctl00$ContentPlaceHolder1$Login.x","0");
        params.put("ctl00$ContentPlaceHolder1$Login.y","0");

        aspxRepository.getInstance().UpdateAspNetHiddenValue(nutcUrl.LOGIN_URL, new aspxDataSource.UpdateAspNetHiddenValueCallBack() {
            @Override
            public void onLoadedHTML(HashMap<String, String> update) {
                params.putAll(update);
                RequestManager.getInstance(mContext).RequestServerData(networkConstants.HTTP_METHOD_POST, nutcUrl.LOGIN_URL,params,new DisposeResponseHandler(new DisposeResponseListener() {
                    @Override
                    public void onSuccess(Response resposeObject) {
                        try{
                            String data = resposeObject.body().string();
                            String url = resposeObject.networkResponse().request().url().toString().toUpperCase();
                            if(url.equals(nutcUrl.MYAREA_URL.toUpperCase())){
                                fetchEportalLink(new SignInRepository.ActivedCallBack() {
                                    @Override
                                    public void onActiveStatus(int status) {
                                        switch(status){
                                            case nutcStatusCode.ACTIVED_SUCCESSFUL:
                                                callBack.onSignInStatus(nutcStatusCode.SIGN_IN_SUCCESS);
                                                break;
                                            case nutcStatusCode.ACTIVED_ERROR:
                                                callBack.onSignInStatus(nutcStatusCode.SIGN_IN_FAILURE);
                                                break;
                                            case nutcStatusCode.NO_INTERNET_ERROR_CODE:
                                                callBack.onSignInStatus(nutcStatusCode.NO_INTERNET_ERROR_CODE);
                                                break;
                                        }
                                    }
                                });
                            }
                            else if (url.equals(nutcUrl.CHANGE_URL.toUpperCase())){
                                callBack.onSignInStatus(nutcStatusCode.NEED_CHANGE_PASS);
                            }
                            else{
                                Log.i(TAG, RegexParser.AlertSource(data));
                                callBack.onSignInStatus(nutcStatusCode.SIGN_IN_FAILURE);
                            }
                        }
                        catch(Exception e){
                            Log.i(TAG,">> SignIn Got Exception.");
                            e.printStackTrace();
                        }
                        resposeObject.close();
                    }

                    @Override
                    public void onFailure(networkException e) {
                        callBack.onSignInStatus(nutcStatusCode.NO_INTERNET_ERROR_CODE);
                    }
                }));
            }

            @Override
            public void onLoadedFailure() {
                callBack.onSignInStatus(nutcStatusCode.SIGN_IN_FAILURE);
            }
        });
    }

    @Override
    public void RefreshValidationCode(final SignInRepository.ValidationCodeCallBack callBack) {
        RequestManager.getInstance(mContext).RequestServerData(networkConstants.HTTP_METHOD_GET, nutcUrl.LOG_IN_VALIDATION_CODE, null, new DisposeResponseHandler(new DisposeResponseListener() {
            @Override
            public void onSuccess(Response resposeObject) {
                Bitmap bitmap = BitmapFactory.decodeStream(resposeObject.body().byteStream());
                callBack.onBitmapLoaded(bitmap);
                resposeObject.close();
            }

            @Override
            public void onFailure(networkException e) {
                callBack.onLoadError(nutcStatusCode.NO_INTERNET_ERROR_CODE);
            }
        }));
    }

    @Override
    public void resetPassword(String schoolNumber, String idNumber, String Hash, String QuestionAnswer, final SignInRepository.resetPasswordCallBack callBack) {
        final HashMap<String,String> params = new HashMap<>();
        params.put("ctl00$ContentPlaceHolder1$Account",schoolNumber);
        params.put("ctl00$ContentPlaceHolder1$SN",idNumber);
        params.put("ctl00$ContentPlaceHolder1$QuestDropDownList",Hash);
        params.put("ctl00$ContentPlaceHolder1$Answer",QuestionAnswer);
        params.put("ctl00$ContentPlaceHolder1$Save","送出");

        aspxRepository.getInstance().UpdateAspNetHiddenValue(nutcUrl.FORGOT_URL, new aspxDataSource.UpdateAspNetHiddenValueCallBack() {
            @Override
            public void onLoadedHTML(HashMap<String, String> update) {
                params.putAll(update);
                RequestManager.getInstance(mContext).RequestServerData(networkConstants.HTTP_METHOD_POST, nutcUrl.FORGOT_URL, params, new DisposeResponseHandler(new DisposeResponseListener() {
                    @Override
                    public void onSuccess(Response resposeObject) {
                        try {
                            String HTML = resposeObject.body().string();
                            callBack.onSendStatus(RegexParser.AlertSource(HTML));
                        } catch (Exception e) {
                            callBack.onSendStatus(nutcApplication.getResourses().getString(R.string.sendError));
                            e.printStackTrace();
                        }
                        resposeObject.close();
                    }

                    @Override
                    public void onFailure(networkException e) {
                        callBack.onSendStatus(nutcApplication.getResourses().getString(R.string.sendError));
                    }
                }));
            }

            @Override
            public void onLoadedFailure() {
                callBack.onSendStatus(nutcApplication.getResourses().getString(R.string.sendError));
            }
        });
    }

    @Override
    public void checkSignIn(final SignInRepository.SignInCallBack callBack) {
        RequestManager.getInstance(mContext).RequestServerData(networkConstants.HTTP_METHOD_GET,nutcUrl.MYAREA_URL,null,new DisposeResponseHandler(new DisposeResponseListener() {
            @Override
            public void onSuccess(Response resposeObject) {
                try {
                    String body = resposeObject.body().string();
                    if (body == null || body.isEmpty()) throw new NullPointerException("Exception >> ResponseBody.string is null");
                    String url = resposeObject.networkResponse().request().url().toString().toUpperCase();
                    if(url.equals(nutcUrl.MYAREA_URL.toUpperCase())){
                        fetchEportalLink(body,new ActivedCallBack() {
                            @Override
                            public void onActiveStatus(int status) {
                                switch(status){
                                    case nutcStatusCode.ACTIVED_SUCCESSFUL:
                                        callBack.onSignInStatus(nutcStatusCode.SIGN_IN_SUCCESS);
                                        break;
                                    case nutcStatusCode.ACTIVED_ERROR:
                                        callBack.onSignInStatus(nutcStatusCode.SIGN_IN_FAILURE);
                                        break;
                                    case nutcStatusCode.NO_INTERNET_ERROR_CODE:
                                        callBack.onSignInStatus(nutcStatusCode.NO_INTERNET_ERROR_CODE);
                                        break;
                                }
                            }
                        });
                    }
                    else if (url.equals(nutcUrl.CHANGE_URL.toUpperCase())){
                        callBack.onSignInStatus(nutcStatusCode.NEED_CHANGE_PASS);
                    }
                    else{
                        callBack.onSignInStatus(nutcStatusCode.NOT_SIGN_IN);
                    }

                } catch (IOException e) {
                    callBack.onSignInStatus(nutcStatusCode.NOT_SIGN_IN);
                    e.printStackTrace();
                } catch (NullPointerException e){
                    callBack.onSignInStatus(nutcStatusCode.NOT_SIGN_IN);
                    e.printStackTrace();
                }
                resposeObject.close();
            }

            @Override
            public void onFailure(networkException e) {
                callBack.onSignInStatus(nutcStatusCode.NO_INTERNET_ERROR_CODE);
            }
        }));
    }

    @Override
    public void fetchEportalLink(final SignInRepository.ActivedCallBack callBack) {
        RequestManager.getInstance(mContext).RequestServerData(networkConstants.HTTP_METHOD_GET,nutcUrl.MYAREA_URL,null,new DisposeResponseHandler(new DisposeResponseListener() {
            @Override
            public void onSuccess(Response resposeObject) {
                try{
                    String responseHTML = resposeObject.body().string();
                    String responseURL = resposeObject.networkResponse().request().url().toString().toUpperCase();
                    if(responseURL.equals(nutcUrl.MYAREA_URL.toUpperCase())){
                        String url = RegexParser.urlFilter(responseHTML);
                        ActiveEportal(url,callBack);
                    }
                    else{
                        callBack.onActiveStatus(nutcStatusCode.ACTIVED_ERROR);
                    }
                }
                catch (Exception e){
                    callBack.onActiveStatus(nutcStatusCode.ACTIVED_ERROR);
                }
                resposeObject.close();
            }

            @Override
            public void onFailure(networkException e) {
                callBack.onActiveStatus(nutcStatusCode.NO_INTERNET_ERROR_CODE);
            }
        }));
    }

    @Override
    public void fetchEportalLink(String source, ActivedCallBack callBack) {
        if (source == null || source.isEmpty()) callBack.onActiveStatus(nutcStatusCode.ACTIVED_ERROR);
        String url = RegexParser.urlFilter(source);
        Log.i(TAG,">> have been Login Process Active...");
        ActiveEportal(url,callBack);
    }

    @Override
    public void ActiveEportal(String url, final SignInRepository.ActivedCallBack callBack) {
        RequestManager.getInstance(mContext).RequestServerData(networkConstants.HTTP_METHOD_GET,url,null,new DisposeResponseHandler(new DisposeResponseListener() {
            @Override
            public void onSuccess(Response resposeObject) {
                String responseURL = resposeObject.networkResponse().request().url().toString().toUpperCase();
                if(responseURL.equals(nutcUrl.EPORTAL_URL.toUpperCase())){
                    Log.i(TAG,">> eportal active successful.");
                    callBack.onActiveStatus(nutcStatusCode.ACTIVED_SUCCESSFUL);
                }
                else{
                    callBack.onActiveStatus(nutcStatusCode.ACTIVED_ERROR);
                }
                resposeObject.close();
            }

            @Override
            public void onFailure(networkException e) {
                callBack.onActiveStatus(nutcStatusCode.NO_INTERNET_ERROR_CODE);
            }
        }));
    }


    @Override
    public void cancelRequest() {
        RequestManager.getInstance(mContext).CancelRequest();
    }
}
