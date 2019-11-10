package com.nutcunofficial.nutcapps.Home.Contracts;

import com.nutcunofficial.nutcapps.Home.Modules.AbsentModule;
import com.nutcunofficial.nutcapps.data.Base.memberBasePresenter;
import com.nutcunofficial.nutcapps.data.Base.memberBaseView;

import java.util.HashMap;

public interface AbsentContract {

    interface View extends memberBaseView {
        void showAbsentOptions(HashMap<String,String> yysem);
        void showAbsentList(AbsentModule module);
        void showError(int StatusCode);
    }

    interface Presenter extends memberBasePresenter {
        void requestOptions();
        void requestAbsentData(String yysem);
    }
}
