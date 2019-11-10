package com.nutcunofficial.nutcapps.Home.Contracts;

import com.nutcunofficial.nutcapps.data.Base.memberBasePresenter;
import com.nutcunofficial.nutcapps.data.Base.memberBaseView;
import com.nutcunofficial.nutcapps.Home.Modules.ClassmateModule;

import java.util.List;

public interface ClassmateContracts {
    interface View extends memberBaseView {

        void showClassmateList(List<ClassmateModule> list);

        void showError(int StatusCode);
    }

    interface Presenter extends memberBasePresenter {

        void requestClassmateList();
    }
}
