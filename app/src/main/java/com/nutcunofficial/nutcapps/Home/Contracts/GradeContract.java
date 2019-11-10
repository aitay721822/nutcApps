package com.nutcunofficial.nutcapps.Home.Contracts;

import com.nutcunofficial.nutcapps.data.Base.memberBasePresenter;
import com.nutcunofficial.nutcapps.data.Base.memberBaseView;
import com.nutcunofficial.nutcapps.Home.Modules.GradeModule;

public interface GradeContract {

    interface View extends memberBaseView {
        void showGradeList(GradeModule data);
        void showError(int StatusCode);
    }

    interface Presenter extends memberBasePresenter{
        void requestGradeList();
    }
}
