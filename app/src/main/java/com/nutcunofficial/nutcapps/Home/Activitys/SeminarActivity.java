package com.nutcunofficial.nutcapps.Home.Activitys;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.nutcunofficial.nutcapps.Base.TitleBaseActivity;
import com.nutcunofficial.nutcapps.R;
import com.nutcunofficial.nutcapps.Home.Adapters.SeminarAdapter;
import com.nutcunofficial.nutcapps.Home.Modules.SeminarModules;
import com.nutcunofficial.nutcapps.Home.Contracts.SeminarContract;
import com.nutcunofficial.nutcapps.Home.Presenters.SeminarPresenter;
import com.nutcunofficial.nutcapps.util.GlideApp;
import com.nutcunofficial.nutcapps.util.nutcSharedPreferences;
import com.nutcunofficial.nutcapps.util.nutcStatusCode;
import com.nutcunofficial.nutcapps.util.SharedPreferenceManager;

import java.util.List;

import static com.nutcunofficial.nutcapps.util.ToastUtil.showToast;

public class SeminarActivity extends TitleBaseActivity implements SeminarContract.View, SearchView.OnQueryTextListener {

    private SearchView mSearchView;
    private RecyclerView seminarRecyclerView;
    private Handler mDelivery = new Handler(Looper.getMainLooper());
    private SeminarPresenter presenter;
    private SeminarAdapter seminarAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SeminarPresenter(this);
        setContentView(R.layout.activity_seminar_view);
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
                    GlideApp.with(SeminarActivity.this).load(url).apply(requestOptions).into(userImage);
                }
            });
        }
        else{
            presenter.requestUserImage();
        }
    }

    @Override
    protected String getTitleName() {
        return getString(R.string.SeminarTitle);
    }

    @Override
    protected void initView() {
        this.back.setVisibility(View.VISIBLE);

        seminarRecyclerView = findViewById(R.id.seminarView);
        seminarRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        seminarAdapter = new SeminarAdapter(this);
        seminarRecyclerView.setAdapter(seminarAdapter);

        mSearchView = findViewById(R.id.searchView);
        mSearchView.setOnQueryTextListener(this);
    }

    @Override
    protected void initData() {
        presenter.requestSeminarList();
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
                showToast(nutcStatusCode.FIND_ERROR_CODE(StatusCode), Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    public void showSeminarList(final List<SeminarModules> seminarList) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if(seminarAdapter!=null){
                    seminarAdapter.add(seminarList);
                }
            }
        });
    }

    @Override
    public void showProgress(boolean isShow) {
        if(isShow)
            showProgressDialog();
        else
            hideProgressDialog();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        seminarAdapter.getFilter().filter(newText);
        return true;
    }
}
