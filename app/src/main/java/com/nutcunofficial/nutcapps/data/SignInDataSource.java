package com.nutcunofficial.nutcapps.data;

import android.graphics.Bitmap;

public interface SignInDataSource {

    void SignIn(String account, String password, String validationCode, final SignInDataSource.SignInCallBack callBack);

    void RefreshValidationCode(SignInDataSource.ValidationCodeCallBack callBack);

    void resetPassword(String schoolNumber,String idNumber,String Hash,String QuestionAnswer,final SignInDataSource.resetPasswordCallBack callBack);

    void checkSignIn(final SignInDataSource.SignInCallBack callBack);

    void fetchEportalLink(final SignInDataSource.ActivedCallBack callBack);

    void fetchEportalLink(String source,final SignInDataSource.ActivedCallBack callBack);

    void ActiveEportal(String url,final SignInDataSource.ActivedCallBack callBack);


    interface SignInCallBack{
        void onSignInStatus(int SignInStatus);
    }

    interface ValidationCodeCallBack{
        void onBitmapLoaded(Bitmap bitmap);
        void onLoadError(int errorCode);
    }

    interface resetPasswordCallBack{
        void onSendStatus(String response);
    }

    interface ActivedCallBack{
        void onActiveStatus(int status);
    }

    void cancelRequest();
}
