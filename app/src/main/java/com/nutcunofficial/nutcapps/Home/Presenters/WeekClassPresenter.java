package com.nutcunofficial.nutcapps.Home.Presenters;

import com.alamkanak.weekview.WeekViewDisplayable;
import com.nutcunofficial.nutcapps.data.memberDataSource;
import com.nutcunofficial.nutcapps.data.memberRepository;
import com.nutcunofficial.nutcapps.Home.Contracts.WeekTimeContract;
import com.nutcunofficial.nutcapps.Home.Modules.WeekTimeModule;
import com.nutcunofficial.nutcapps.R;
import com.nutcunofficial.nutcapps.nutcApplication;
import com.nutcunofficial.nutcapps.util.RegexParser;
import com.nutcunofficial.nutcapps.util.nutcStatusCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeekClassPresenter implements WeekTimeContract.Presenter, memberDataSource.GetWeekTimeCallBack,memberDataSource.fetchUserImageCallBack {

    private WeekTimeContract.View mView;

    public WeekClassPresenter( WeekTimeContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void requestWeekClass() {
        mView.showProgress(true);
        memberRepository.getInstance().GetWeekClassList(this);
    }

    @Override
    public void requestUserImage() {
        memberRepository.getInstance().fetchUserImage(this);
    }

    @Override
    public void cancelRequest() {
        mView.showProgress(false);
        memberRepository.getInstance().cancelRequest();
    }

    @Override
    public void onRequestSuccessful(String html) {
        try{

            int[] colorArray = new int[]{
                    nutcApplication.getResourses().getColor(R.color.event_color_02)
            };

            List<WeekViewDisplayable<WeekTimeModule>> result = new ArrayList<>();

            HashMap<String,String> parsed = RegexParser.JsonParser(html);

            int idx = 0;
            for ( Map.Entry<String,String> data : parsed.entrySet()){

                String[] classTime = data.getKey().split("-");
                // array 0 is Class , array 1 is ClassName , array 2 is teacher , array 3 is classNumber
                String[] classData = data.getValue().split("\t");

                if(classData.length!=5||classTime.length!=2) continue;

                long id = Long.valueOf(classData[4]);
                String ClassName = classData[1];
                String TeacherName = classData[2];
                Integer ClassDay = Integer.valueOf(classTime[0]);
                Integer ClassTime = Integer.valueOf(classTime[1]);
                String ClassRoom = classData[3];

                result.add(new WeekTimeModule(
                        id,ClassName,TeacherName,ClassDay,ClassTime,ClassRoom,colorArray[idx++%colorArray.length]
                ));

            }
            mView.showWeekClass(result);
        }
        catch(Exception e){
            mView.showRequestError(nutcStatusCode.FETCH_ERROR);
            e.printStackTrace();
        }
        finally {
            mView.showProgress(false);
        }
    }

    @Override
    public void onRequestFailure(int StatusCode) {
        mView.showRequestError(StatusCode);
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
