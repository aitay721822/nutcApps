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
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Response;

public class memberRepository extends memberDataSourceImpl{

    private static final String TAG = memberRepository.class.getSimpleName();

    private volatile static memberRepository repository;

    private Context mContext;

    private memberRepository() {
        super();
        this.mContext = nutcApplication.getInstance().getApplicationContext();
    }

    public static memberRepository getInstance() {
        if (repository == null) {
            synchronized (memberRepository.class) {
                if(repository == null) repository = new memberRepository();
            }
        }
        return repository;
    }

    @Override
    public void changePassword(String oldPassword, String newPassword, final memberDataSource.changePasswordCallBack callBack) {
        final HashMap<String,String> params = new HashMap<>();
        params.put("ctl00$ContentPlaceHolder1$OldPassword",oldPassword);
        params.put("ctl00$ContentPlaceHolder1$Password",newPassword);
        params.put("ctl00$ContentPlaceHolder1$Password1",newPassword);
        params.put("ctl00$ContentPlaceHolder1$Save","變更");
        aspxRepository.getInstance().UpdateAspNetHiddenValue(nutcUrl.CHANGE_URL, new aspxDataSource.UpdateAspNetHiddenValueCallBack() {
            @Override
            public void onLoadedHTML(HashMap<String, String> update) {
                params.putAll(update);
                RequestManager.getInstance(mContext).RequestServerData(networkConstants.HTTP_METHOD_POST,nutcUrl.CHANGE_URL,params,new DisposeResponseHandler(new DisposeResponseListener() {
                    @Override
                    public void onSuccess(Response resposeObject) {
                        try{
                            String body = resposeObject.body().string();
                            String alert = RegexParser.AlertSource(body);
                            callBack.onSendStatus(alert);
                        }catch (Exception e){
                            callBack.onSendStatus(nutcStatusCode.FIND_ERROR_CODE(nutcStatusCode.FETCH_ERROR));
                        }
                        resposeObject.close();
                    }

                    @Override
                    public void onFailure(networkException e) {
                        callBack.onSendStatus(nutcStatusCode.FIND_ERROR_CODE(nutcStatusCode.NO_INTERNET_ERROR_CODE));
                    }
                }));
            }

            @Override
            public void onLoadedFailure() {
                callBack.onSendStatus(nutcStatusCode.FIND_ERROR_CODE(nutcStatusCode.NO_INTERNET_ERROR_CODE));
            }
        });
    }

    @Override
    public void getNotices(final memberDataSource.getNoticesCallBack callBack) {
        RequestManager.getInstance(mContext).RequestServerData(networkConstants.HTTP_METHOD_GET, nutcUrl.EPORTAL_URL,null,new DisposeResponseHandler(new DisposeResponseListener() {
            @Override
            public void onSuccess(Response resposeObject) {
                try{
                    String HTML = resposeObject.body().string();
                    callBack.onNoticeContent(HTML);
                    resposeObject.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                    callBack.onNoticeGetterFail(nutcStatusCode.CANT_FETCH_NOTICE);
                }
            }

            @Override
            public void onFailure(networkException e) {
                callBack.onNoticeGetterFail(nutcStatusCode.NO_INTERNET_ERROR_CODE);
            }
        }));
    }

    @Override
    public void GetSeminarList(final memberDataSource.GetSeminarListCallBack callBack) {
        RequestManager.getInstance(mContext).RequestServerData(networkConstants.HTTP_METHOD_GET,nutcUrl.EPORTAL_CADRES,null,new DisposeResponseHandler(new DisposeResponseListener() {
            @Override
            public void onSuccess(Response resposeObject) {
                try{
                    String html = resposeObject.body().string();
                    callBack.onRequestSuccessful(html);
                }
                catch(Exception e){
                    callBack.onRequestFailure(nutcStatusCode.FETCH_ERROR);
                    e.printStackTrace();
                }
                resposeObject.close();
            }

            @Override
            public void onFailure(networkException e) {
                callBack.onRequestFailure(nutcStatusCode.NO_INTERNET_ERROR_CODE);
            }
        }));
    }

    @Override
    public void GetClassmateList(final memberDataSource.GetClassmateCallback callback) {
        RequestManager.getInstance(mContext).RequestServerData(networkConstants.HTTP_METHOD_GET,nutcUrl.EPORTAL_CLASSMATES,null,new DisposeResponseHandler(new DisposeResponseListener() {
            @Override
            public void onSuccess(Response resposeObject) {
                try{
                    callback.onRequestClassmateSuccessful(resposeObject.body().string());
                    resposeObject.close();
                }
                catch (Exception e){
                    callback.onRequestFailure(nutcStatusCode.FETCH_ERROR);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(networkException e) {
                callback.onRequestFailure(nutcStatusCode.NO_INTERNET_ERROR_CODE);
            }
        }));
    }

    @Override
    public void GetWeekClassList(final memberDataSource.GetWeekTimeCallBack callback) {
        RequestManager.getInstance(mContext).RequestServerData(networkConstants.HTTP_METHOD_GET,nutcUrl.EPORTAL_WEEK_TIME,null,new DisposeResponseHandler(new DisposeResponseListener() {
            @Override
            public void onSuccess(Response resposeObject) {
                try{
                    callback.onRequestSuccessful(resposeObject.body().string());
                }
                catch(Exception e){
                    callback.onRequestFailure(nutcStatusCode.FETCH_ERROR);
                    e.printStackTrace();
                }
                resposeObject.close();
            }

            @Override
            public void onFailure(networkException e) {
                callback.onRequestFailure(nutcStatusCode.NO_INTERNET_ERROR_CODE);
            }
        }));
    }

    @Override
    public void GetUserData(final memberDataSource.GetUserDataCallback callBack) {
        RequestManager.getInstance(mContext).RequestServerData(networkConstants.HTTP_METHOD_POST,nutcUrl.EPORTAL_STU_INFO,null,new DisposeResponseHandler(new DisposeResponseListener() {
            @Override
            public void onSuccess(Response resposeObject) {
                try {
                    callBack.onGettingSuccess(resposeObject.body().string());
                } catch (IOException e) {
                    callBack.onGettingFailure(nutcStatusCode.FETCH_ERROR);
                    e.printStackTrace();
                }
                resposeObject.close();
            }

            @Override
            public void onFailure(networkException e) {
                callBack.onGettingFailure(nutcStatusCode.NO_INTERNET_ERROR_CODE);
            }
        }));
    }

    @Override
    public void GetAbsentData(String sem, final GetAbsentDataCallback callback) {
        HashMap<String,String> params = new HashMap<>();
        params.put("sem",sem);
        RequestManager.getInstance(mContext).RequestServerData(networkConstants.HTTP_METHOD_GET,nutcUrl.EPORTAL_ABSENSE_LIST,params,new DisposeResponseHandler(new DisposeResponseListener() {
            @Override
            public void onSuccess(Response resposeObject) {
                try{
                    String responseHTML = resposeObject.body().string();
                    callback.GettingSuccess(responseHTML);
                    resposeObject.body().close();
                }
                catch (Exception e){
                    callback.GettingFailure(nutcStatusCode.NO_INTERNET_ERROR_CODE);
                }
                resposeObject.close();
            }

            @Override
            public void onFailure(networkException e) {
                callback.GettingFailure(e.getErrorCode());
            }
        }));
    }

    @Override
    public void GetOptions(final GetOptionsCallback callback) {
        RequestManager.getInstance(mContext).RequestServerData(networkConstants.HTTP_METHOD_GET,nutcUrl.EPORTAL_ABSENSE_LIST,null ,new DisposeResponseHandler(new DisposeResponseListener() {
            @Override
            public void onSuccess(Response resposeObject) {
                try{
                    String responseHTML = resposeObject.body().string();

                    Document doc = Jsoup.parse(responseHTML);

                    Elements options = doc.select("option");
                    HashMap<String,String> Opt = new HashMap<>();
                    for(Element e : options){
                        if(e.hasText() && e.hasAttr("value")){
                            Opt.put(e.attr("value"),e.text());
                        }
                    }

                    callback.GetOptionsData(Opt);

                    resposeObject.body().close();
                }
                catch (Exception e){
                    callback.GetOptionsFail(nutcStatusCode.NO_INTERNET_ERROR_CODE);
                }
                resposeObject.close();
            }

            @Override
            public void onFailure(networkException e) {
                callback.GetOptionsFail(e.getErrorCode());
            }
        }));
    }

    @Override
    public void GetGradeData(final GetGradeDataCallback callback) {
        RequestManager.getInstance(mContext).RequestServerData(networkConstants.HTTP_METHOD_GET,nutcUrl.EPORTAL_GRADE_VIEW,null,new DisposeResponseHandler(new DisposeResponseListener() {
            @Override
            public void onSuccess(Response resposeObject) {
                try{
                    String html = resposeObject.body().string();
                    callback.GetSuccess(html);
                }
                catch (IOException e){
                    callback.GetError(nutcStatusCode.FETCH_ERROR);
                    e.printStackTrace();
                }
                resposeObject.close();
            }

            @Override
            public void onFailure(networkException e) {
                callback.GetError(e.getErrorCode());

            }
        }));
    }

    @Override
    public void cancelRequest() {
        RequestManager.getInstance(mContext).CancelRequest();
    }
}
