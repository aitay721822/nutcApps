package com.nutcunofficial.nutcapps.Home.Contracts;

import com.nutcunofficial.nutcapps.data.Base.memberBasePresenter;
import com.nutcunofficial.nutcapps.data.Base.memberBaseView;
import com.nutcunofficial.nutcapps.Home.Modules.noticeModule;

import java.util.List;

public interface HomeContract {

    interface View extends memberBaseView {
        void showNotice(List<noticeModule> moduleList);
        void showError(int statusCode);
    }

    interface Presenter extends memberBasePresenter {
        void requestNotice();
    }
}
