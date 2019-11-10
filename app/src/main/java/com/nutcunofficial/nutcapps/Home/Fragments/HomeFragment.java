package com.nutcunofficial.nutcapps.Home.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.nutcunofficial.nutcapps.Base.TitleBaseFragment;
import com.nutcunofficial.nutcapps.Base.nutcSwipeRefreshLayout;
import com.nutcunofficial.nutcapps.Home.Activitys.ChangePassword;
import com.nutcunofficial.nutcapps.Home.Activitys.ClassmatesActivity;
import com.nutcunofficial.nutcapps.Home.Activitys.WeekClassActivity;
import com.nutcunofficial.nutcapps.Home.Adapters.functionAdapter;
import com.nutcunofficial.nutcapps.Home.Adapters.noticeAdapter;
import com.nutcunofficial.nutcapps.Home.Composings.ItemDecoration;
import com.nutcunofficial.nutcapps.Home.Contracts.HomeContract;
import com.nutcunofficial.nutcapps.Home.Modules.functionModule;
import com.nutcunofficial.nutcapps.Home.Modules.noticeModule;
import com.nutcunofficial.nutcapps.Home.Presenters.HomePresenter;
import com.nutcunofficial.nutcapps.R;
import com.nutcunofficial.nutcapps.Home.Activitys.SeminarActivity;
import com.nutcunofficial.nutcapps.Home.Activitys.SignInActivity;
import com.nutcunofficial.nutcapps.util.GlideApp;
import com.nutcunofficial.nutcapps.util.nutcSharedPreferences;
import com.nutcunofficial.nutcapps.util.nutcStatusCode;
import com.nutcunofficial.nutcapps.util.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.nutcunofficial.nutcapps.util.ToastUtil.showToast;


public class HomeFragment extends TitleBaseFragment implements HomeContract.View,
        View.OnClickListener,SwipeRefreshLayout.OnRefreshListener,
        noticeAdapter.onNoticeClickListener,
        functionAdapter.FunctionListener{


    private final String TAG = HomeFragment.class.getSimpleName();

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private final int SPAN_COUNT = 4;
    private RecyclerView functionRecyclerView;
    private RecyclerView NoticeRecyclerView;
    private functionAdapter functionAdapter;
    private noticeAdapter noticeAdapter;
    private List<functionModule> functionModuleList;
    private List<noticeModule> noticeModules;

    private nutcSwipeRefreshLayout swipeRefreshLayout;

    private HomePresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG,"homeFragment has been created!");

        presenter = new HomePresenter(this);

        functionModuleList = new ArrayList<>();
        functionModuleList.add(new functionModule(R.drawable.timetable,"課表"));
        functionModuleList.add(new functionModule(R.drawable.progress,"修業進度"));
        functionModuleList.add(new functionModule(R.drawable.classmates,"班級成員"));
        functionModuleList.add(new functionModule(R.drawable.seminar,"班級幹部"));
        functionModuleList.add(new functionModule(R.drawable.mask,"請假"));
        functionModuleList.add(new functionModule(R.drawable.padlock,"更改密碼"));

        noticeModules = new ArrayList<>();
    }

    @Override
    protected void setStudentIcon() {
        SharedPreferenceManager manager = new SharedPreferenceManager(this.mActivity.getApplicationContext(), nutcSharedPreferences.UserInfoSP, Context.MODE_PRIVATE);
        final String url = manager.read("userIcon_image",null);
        if(url!=null){
            final RequestOptions requestOptions = new RequestOptions().transform(new FitCenter(),new RoundedCorners(250));
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    GlideApp.with(HomeFragment.this).load(url).apply(requestOptions).into(userImage);
                }
            });
        }
        else{
            presenter.requestUserImage();
        }
    }

    @Override
    protected String getTitleName() {
        return getResources().getString(R.string.app_name);
    }

    @Override
    protected int resourceViewId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        super.back.setVisibility(View.GONE);

        functionAdapter = new functionAdapter(this.mActivity.getApplicationContext(),functionModuleList,this);
        noticeAdapter = new noticeAdapter(this.mActivity.getApplicationContext(),noticeModules,this);

        functionRecyclerView = (RecyclerView)view.findViewById(R.id.AllFunction);
        functionRecyclerView.setHasFixedSize(true);
        functionRecyclerView.addItemDecoration(new ItemDecoration(SPAN_COUNT));
        functionRecyclerView.setLayoutManager(new GridLayoutManager(mActivity,SPAN_COUNT));
        functionRecyclerView.setAdapter(functionAdapter);

        NoticeRecyclerView = (RecyclerView)view.findViewById(R.id.announcement);
        NoticeRecyclerView.setLayoutManager(new LinearLayoutManager(this.mActivity.getApplicationContext()));
        NoticeRecyclerView.setAdapter(noticeAdapter);

        userImage = (ImageView)view.findViewById(R.id.student_icon);
        userImage.setOnClickListener(this);

        swipeRefreshLayout = (nutcSwipeRefreshLayout) view.findViewById(R.id.swipeView);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initData() {
        presenter.requestNotice();
    }

    @Override
    public void showNotice(final List<noticeModule> moduleList) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                if(noticeAdapter!=null){
                    noticeAdapter.clear();
                    Collections.sort(moduleList,noticeModule.sort);
                    noticeAdapter.add(moduleList);
                    noticeAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void showError(final int statusCode) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if(statusCode == nutcStatusCode.NO_INTERNET_ERROR_CODE || statusCode == nutcStatusCode.ACTIVED_ERROR){
                    start(SignInActivity.class);
                }
                else{
                    showToast(nutcStatusCode.FIND_ERROR_CODE(statusCode), Toast.LENGTH_SHORT);
                }
            }
        });
    }

    public void setUserImage(final String url) {
        SharedPreferenceManager manager = new SharedPreferenceManager(this.mActivity.getApplicationContext(),nutcSharedPreferences.UserInfoSP,Context.MODE_PRIVATE);
        manager.write("userIcon_image",url);
        setStudentIcon();
    }


    @Override
    public void showProgress(boolean isShow) {
        if(isShow){
            showProgressDialog();
        }
        else{
            hideProgressDialog();
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.student_icon:
                break;
        }
    }

    @Override
    protected void backClick(View v) {
        super.backClick(v);
    }

    @Override
    public void onRefresh() {
        presenter.requestNotice();
    }

    @Override
    public void onNoticeItemClick(int position) {
        noticeModule module = noticeAdapter.get(position);
        // do something ... //
    }

    @Override
    public void onFunctionListener(int position) {
        Log.d(TAG,">> is FunctionListener Click");
        functionModule module = functionAdapter.get(position);
        switch(module.getDrawbleId()){
            case R.drawable.padlock:
                start(ChangePassword.class,false);
                break;
            case R.drawable.seminar:
                start(SeminarActivity.class,false);
                break;
            case R.drawable.classmates:
                start(ClassmatesActivity.class,false);
                break;
            case R.drawable.timetable:
                start(WeekClassActivity.class,false);
                break;
        }
    }
    public static HomeFragment newInstance(){
        return new HomeFragment();
    }
}
