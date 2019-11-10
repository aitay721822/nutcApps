package com.nutcunofficial.nutcapps.Home.Contracts;

import com.nutcunofficial.nutcapps.data.Base.memberBasePresenter;
import com.nutcunofficial.nutcapps.data.Base.memberBaseView;
import com.nutcunofficial.nutcapps.Home.Modules.StudentModule;

public interface UserContract {
    interface View extends memberBaseView {
        void showError(int Status);
        void setStuData(StudentModule stuData);
    }
    interface Presenter extends memberBasePresenter {
        void requestStuData();
    }
}
