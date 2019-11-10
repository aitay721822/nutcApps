package com.nutcunofficial.nutcapps.Home.Contracts;

import com.nutcunofficial.nutcapps.data.Base.memberBasePresenter;
import com.nutcunofficial.nutcapps.data.Base.memberBaseView;

public interface StudiesRecordContract {

    interface View extends memberBaseView {
        void showRecord();
        void showError(int StatusCode);
    }

    interface Presenter extends memberBasePresenter {
        void requestRecord();
    }
}
