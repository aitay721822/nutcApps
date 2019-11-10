package com.nutcunofficial.nutcapps.Home.Contracts;

import com.nutcunofficial.nutcapps.data.Base.memberBasePresenter;
import com.nutcunofficial.nutcapps.data.Base.memberBaseView;
import com.nutcunofficial.nutcapps.Home.Modules.SeminarModules;

import java.util.List;

public interface SeminarContract {

    interface View extends memberBaseView {
        void showError(int StatusCode);
        void showSeminarList(List<SeminarModules> seminarList);
    }

    interface Presenter extends memberBasePresenter {
        void requestSeminarList();
    }
}
