package com.nutcunofficial.nutcapps.Home.Activitys;

import android.content.Context;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.alamkanak.weekview.OnEventLongClickListener;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewDisplayable;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.nutcunofficial.nutcapps.Base.TitleBaseActivity;
import com.nutcunofficial.nutcapps.Home.Contracts.WeekTimeContract;
import com.nutcunofficial.nutcapps.Home.Modules.WeekTimeModule;
import com.nutcunofficial.nutcapps.Home.Presenters.WeekClassPresenter;
import com.nutcunofficial.nutcapps.R;
import com.nutcunofficial.nutcapps.util.CalendarUtils;
import com.nutcunofficial.nutcapps.util.GlideApp;
import com.nutcunofficial.nutcapps.util.ToastUtil;
import com.nutcunofficial.nutcapps.util.nutcSharedPreferences;
import com.nutcunofficial.nutcapps.util.nutcStatusCode;
import com.nutcunofficial.nutcapps.util.SharedPreferenceManager;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.List;

import static com.nutcunofficial.nutcapps.util.ToastUtil.showToast;

public class WeekClassActivity extends TitleBaseActivity implements WeekTimeContract.View, OnEventLongClickListener<WeekTimeModule> {
    private static final String TAG = WeekClassActivity.class.getSimpleName();
    private Handler mDelivery = new Handler(Looper.getMainLooper());
    private WeekView mWeekView;
    private WeekClassPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new WeekClassPresenter(this);
        setContentView(R.layout.activity_week_class);
    }

    @Override
    protected void backClick(View v) {
        finish();
    }

    @Override
    protected void setStudentIcon() {
        SharedPreferenceManager manager = new SharedPreferenceManager(getApplicationContext(), nutcSharedPreferences.UserInfoSP, Context.MODE_PRIVATE);
        final String url = manager.read("userIcon_image",null);
        if(url!=null){
            final RequestOptions requestOptions = new RequestOptions().transform(new FitCenter(),new RoundedCorners(250));
            mDelivery.post(new Runnable() {
                @Override
                public void run() {
                    GlideApp.with(WeekClassActivity.this).load(url).apply(requestOptions).into(userImage);
                }
            });
        }
        else{
            presenter.requestUserImage();
        }
    }

    @Override
    protected String getTitleName() {
        return getString(R.string.WeekClassTitle);
    }

    @Override
    protected void initView() {
        this.back.setVisibility(View.VISIBLE);

        mWeekView = (WeekView)findViewById(R.id.weekView);

        Calendar startTime = CalendarUtils.getInstance().getCurrentWeekFirstDay();
        Calendar endTime = CalendarUtils.getInstance().getCurrentWeekLastDay();

        Log.e(TAG,startTime.getTime().toString());
        Log.e(TAG,endTime.getTime().toString());

        // Click Show Details.
        mWeekView.setNumberOfVisibleDays(7);

        mWeekView.setMinDate(startTime);
        mWeekView.setMaxDate(endTime);

        mWeekView.setMinHour(7);
        mWeekView.setMaxHour(23);
        mWeekView.setOnEventLongClickListener(this);

        mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
        mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));

    }

    @Override
    protected void initData() {
        presenter.requestWeekClass();
    }

    @Override
    public void setUserImage(String url) {
        SharedPreferenceManager manager = new SharedPreferenceManager(getApplicationContext(),nutcSharedPreferences.UserInfoSP,Context.MODE_PRIVATE);
        manager.write("userIcon_image",url);
        setStudentIcon();
    }

    @Override
    public void showError(final int StatusCode) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showToast(nutcStatusCode.FIND_ERROR_CODE(StatusCode),Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    public void showWeekClass(final List<WeekViewDisplayable<WeekTimeModule>> data) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                mWeekView.submit(data);
            }
        });
    }

    @Override
    public void showRequestError(final int StatusCode) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                showToast(nutcStatusCode.FIND_ERROR_CODE(StatusCode), Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    public void showProgress(boolean isShow) {
        if(isShow)showProgressDialog();
        else hideProgressDialog();
    }

    @Override
    public void onEventLongClick(WeekTimeModule weekTimeModule, @NotNull RectF rectF) {
        Log.i(TAG,">> Press Long Click");
    }
}
