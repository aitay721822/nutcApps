package com.nutcunofficial.nutcapps.Home.Presenters;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.nutcunofficial.nutcapps.data.SignInDataSource;
import com.nutcunofficial.nutcapps.data.SignInRepository;
import com.nutcunofficial.nutcapps.Home.Contracts.SignInContract;

public class SignInPresenter implements SignInContract.Presenter,
        SignInDataSource.ValidationCodeCallBack,
        SignInDataSource.SignInCallBack,
        SignInDataSource.resetPasswordCallBack{

    private static final String TAG = "SignInPresenter";

    @NonNull
    private final SignInContract.View mView;


    public SignInPresenter( @NonNull SignInContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void requestSignIn(String account, String password, String validation) {
        mView.showProgress(true);
        SignInRepository.getInstance().SignIn(account,password,validation,this);
    }

    @Override
    public void requestValidationCode() {
        mView.showProgress(true);
        SignInRepository.getInstance().RefreshValidationCode(this);
    }

    @Override
    public void requestResetPassword(String schoolNumber, String idnumber, String QuestionHash, String QuestionAnswer) {
        mView.showProgress(true);
        SignInRepository.getInstance().resetPassword(schoolNumber,idnumber,QuestionHash,QuestionAnswer,this);
    }

    @Override
    public void checkSignIn() {
        mView.showProgress(true);
        SignInRepository.getInstance().checkSignIn(this);
    }

    @Override
    public void cancelRequest() {
        SignInRepository.getInstance().cancelRequest();
        mView.showProgress(false);
    }


    @Override
    public void onBitmapLoaded(Bitmap bitmap) {
        mView.showValidationCode(bitmap);
        mView.showProgress(false);
    }

    @Override
    public void onLoadError(int errorCode) {
        mView.showValidationError(errorCode);
        mView.showProgress(false);
    }

    @Override
    public void onSignInStatus(int SignInStatus) {
        mView.showSignInStatus(SignInStatus);
        mView.showProgress(false);
    }

    @Override
    public void onSendStatus(String response) {
        mView.showResetPasswordSend(response);
        mView.showProgress(false);
    }
}
