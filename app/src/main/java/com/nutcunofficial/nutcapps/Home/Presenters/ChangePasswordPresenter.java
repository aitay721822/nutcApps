package com.nutcunofficial.nutcapps.Home.Presenters;

import androidx.annotation.NonNull;

import com.nutcunofficial.nutcapps.data.SignInDataSource;
import com.nutcunofficial.nutcapps.data.SignInRepository;
import com.nutcunofficial.nutcapps.data.memberDataSource;
import com.nutcunofficial.nutcapps.data.memberRepository;
import com.nutcunofficial.nutcapps.Home.Contracts.ChangePasswordContract;

public class ChangePasswordPresenter implements ChangePasswordContract.Presenter, memberDataSource.changePasswordCallBack,memberDataSource.fetchUserImageCallBack, SignInDataSource.SignInCallBack{
    @NonNull
    private final ChangePasswordContract.View mView;


    public ChangePasswordPresenter(@NonNull ChangePasswordContract.View mView){
        this.mView=mView;
    }

    @Override
    public void requestChangePass(String oldpassword, String newPassword) {
        mView.showProgress(true);
        memberRepository.getInstance().changePassword(oldpassword,newPassword,this);
    }

    @Override
    public void requestUserImage() {
        mView.showProgress(true);
        memberRepository.getInstance().fetchUserImage(this);
    }

    @Override
    public void checkSuccessful() {
        mView.showProgress(true);
        SignInRepository.getInstance().checkSignIn(this);
    }

    @Override
    public void cancelRequest() {
        memberRepository.getInstance().cancelRequest();
        mView.showProgress(false);
    }

    @Override
    public void onSendStatus(String response) {
        mView.showChangePasswordStatus(response);
        mView.showProgress(false);
    }

    @Override
    public void onSignInStatus(int SignInStatus) {
        mView.showSuccessful(SignInStatus);
        mView.showProgress(false);
    }

    @Override
    public void onFetchSuccess(String url) {
        mView.setUserImage(url);
        mView.showProgress(false);
    }

    @Override
    public void onFetchError(int StatusCode) {
        mView.showError(StatusCode);
        mView.showProgress(false);
    }

}
