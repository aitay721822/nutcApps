package com.nutcunofficial.nutcapps.Home.Contracts;

import android.graphics.Bitmap;

import com.nutcunofficial.nutcapps.data.Base.IBasePresenter;
import com.nutcunofficial.nutcapps.data.Base.IBaseView;

public interface SignInContract {

    interface View extends IBaseView{

        void showSignInStatus(int SignInStatus);

        void showValidationCode(Bitmap bitmap);

        void showValidationError(int errorCode);

        void showResetPasswordSend(String msg);

    }

    interface Presenter extends IBasePresenter {

        void requestSignIn(String account,String password,String validation);

        void requestValidationCode();

        void requestResetPassword(String schoolNumber, String idnumber, String QuestionHash, String QuestionAnswer);

        void checkSignIn();
    }
}
