package com.nutcunofficial.nutcapps.Home.Contracts;

import com.nutcunofficial.nutcapps.data.Base.memberBasePresenter;
import com.nutcunofficial.nutcapps.data.Base.memberBaseView;

public interface ChangePasswordContract {

    interface View extends memberBaseView {
        void showError(int StatusCode);
        void showChangePasswordStatus(String msg);
        void showSuccessful(int Status);
    }

    interface Presenter extends memberBasePresenter {
        void requestChangePass(String oldpassword,String newPassword);
        void checkSuccessful();
    }
}
