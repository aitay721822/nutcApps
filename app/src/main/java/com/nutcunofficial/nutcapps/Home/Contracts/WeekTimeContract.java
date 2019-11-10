package com.nutcunofficial.nutcapps.Home.Contracts;

import com.alamkanak.weekview.WeekViewDisplayable;
import com.nutcunofficial.nutcapps.data.Base.memberBasePresenter;
import com.nutcunofficial.nutcapps.data.Base.memberBaseView;
import com.nutcunofficial.nutcapps.Home.Modules.WeekTimeModule;

import java.util.List;

public interface WeekTimeContract {

    interface View extends memberBaseView {
        void showError(int StatusCode);
        void showWeekClass(List<WeekViewDisplayable<WeekTimeModule>> data);
        void showRequestError(int StatusCode);
    }

    interface Presenter extends memberBasePresenter {
        void requestWeekClass();
    }
}
