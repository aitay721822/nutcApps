package com.nutcunofficial.nutcapps.Home.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.nutcunofficial.nutcapps.Base.BaseFragment;
import com.nutcunofficial.nutcapps.Base.TitleBaseFragment;
import com.nutcunofficial.nutcapps.Home.Adapters.ButtonAdapter;
import com.nutcunofficial.nutcapps.Home.Adapters.ClassViewAdapter;
import com.nutcunofficial.nutcapps.Home.Adapters.KeyValueAdapter;
import com.nutcunofficial.nutcapps.Home.Contracts.AbsentContract;
import com.nutcunofficial.nutcapps.Home.Dialogs.AbsentDetailViewDialog;
import com.nutcunofficial.nutcapps.Home.Modules.AbsentModule;
import com.nutcunofficial.nutcapps.Home.Modules.GradeModule;
import com.nutcunofficial.nutcapps.Home.Modules.KeyValueModule;
import com.nutcunofficial.nutcapps.Home.Presenters.AbsentPresenter;
import com.nutcunofficial.nutcapps.R;
import com.nutcunofficial.nutcapps.util.GlideApp;
import com.nutcunofficial.nutcapps.util.SharedPreferenceManager;
import com.nutcunofficial.nutcapps.util.ToastUtil;
import com.nutcunofficial.nutcapps.util.nutcSharedPreferences;
import com.nutcunofficial.nutcapps.util.nutcStatusCode;

import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AbsentFragment extends TitleBaseFragment implements AbsentContract.View,ButtonAdapter.btnOnClickListener, ClassViewAdapter.onDetailSelectedListener {

    private static final String TAG = AbsentFragment.class.getSimpleName();

    public static AbsentFragment newInstance() {
        return new AbsentFragment();
    }

    private Handler mHandler = new Handler(Looper.getMainLooper());
    private AbsentPresenter presenter;

    private RecyclerView button_select_absent;
    private RecyclerView absent_status;
    private RecyclerView absent_detail;

    private ButtonAdapter btnAdapter;
    private ClassViewAdapter classViewAdapter;
    private KeyValueAdapter DayOffAdapter;

    private HashMap<String,String> AbsentData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"AbsentFragment has been created!");

        presenter = new AbsentPresenter(this);
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
                    GlideApp.with(AbsentFragment.this).load(url).apply(requestOptions).into(userImage);
                }
            });
        }
        else{
            presenter.requestUserImage();
        }
    }

    @Override
    protected String getTitleName() {
        return getString(R.string.Absent_Title);
    }

    @Override
    protected int resourceViewId() {
        return R.layout.fragment_absent;
    }

    @Override
    protected void initView(View view) {
        super.back.setVisibility(View.GONE);

        button_select_absent = view.findViewById(R.id.button_select_absent);
        absent_detail = view.findViewById(R.id.absent_detail);
        absent_status = view.findViewById(R.id.absent_status);

        absent_status.setItemAnimator(new DefaultItemAnimator());
        absent_detail.setItemAnimator(new DefaultItemAnimator());

        btnAdapter = new ButtonAdapter(mActivity.getApplicationContext(),this);
        classViewAdapter = new ClassViewAdapter(mActivity.getApplicationContext(),this);
        DayOffAdapter = new KeyValueAdapter(mActivity.getApplicationContext());

        button_select_absent.setLayoutManager(new LinearLayoutManager(mActivity.getApplicationContext(), LinearLayoutManager.HORIZONTAL,false));
        absent_detail.setLayoutManager(new LinearLayoutManager(mActivity.getApplicationContext(), RecyclerView.VERTICAL,false));
        absent_status.setLayoutManager(new LinearLayoutManager(mActivity.getApplicationContext(), RecyclerView.VERTICAL,false));

        button_select_absent.setAdapter(btnAdapter);
        absent_detail.setAdapter(classViewAdapter);
        absent_status.setAdapter(DayOffAdapter);
    }

    @Override
    protected void initData() {
        presenter.requestOptions();
    }

    @Override
    public void showAbsentOptions(final HashMap<String, String> yysem) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                AbsentData = yysem;

                String[] sorting = new String[AbsentData.size()];

                // first add button
                int index = 0;
                for(Map.Entry<String, String> g : AbsentData.entrySet()){
                    sorting[index++]=g.getValue();
                }

                Arrays.sort(sorting);

                for(String i : sorting) btnAdapter.addButton(i);

                if(btnAdapter.getItemCount() > 0){
                    displayAbsent(0);
                }
            }
        });
    }

    private void displayAbsent(int position) {
        String yysem = null;

        // get yysem
        for(Map.Entry<String, String> entry : AbsentData.entrySet())
            if(entry.getValue().equals(btnAdapter.getText(position))) yysem = entry.getKey();

         if (yysem!=null){
             presenter.requestAbsentData(yysem);
         }
    }

    @Override
    public void showAbsentList(final AbsentModule module) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {

                if ( module == null || module.getTotalModule() == null || module.getClassModules() == null) return;

                DayOffAdapter.clear();
                classViewAdapter.clear();

                List<KeyValueModule<String>> kVMap =  module.getTotalModule().toList();

                List<AbsentModule.AbsentClassModule> claz = module.getClassModules();

                if(kVMap == null) return;

                for(KeyValueModule<String> k : kVMap){
                    DayOffAdapter.add(k.getKey(),k.getValue());
                }

                for(AbsentModule.AbsentClassModule c : claz){
                    if(c.getStatus().length > 0) {
                        classViewAdapter.addClass(c);
                    }
                }

            }
        });
    }

    @Override
    public void showError(final int StatusCode) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showToast(nutcStatusCode.FIND_ERROR_CODE(StatusCode), Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    public void setUserImage(String url) {
        SharedPreferenceManager manager = new SharedPreferenceManager(this.mActivity.getApplicationContext(), nutcSharedPreferences.UserInfoSP,Context.MODE_PRIVATE);
        manager.write("userIcon_image",url);
        setStudentIcon();
    }

    @Override
    public void showProgress(boolean isShow) {
        if(isShow) {
            showProgressDialog();
        }
        else {
            hideProgressDialog();
        }
    }

    @Override
    public void onBtnRequestSwitchListener(int position) {
        displayAbsent(position);
    }

    @Override
    public void onShowDetail(int position) {
        if(classViewAdapter == null) return;
        if(classViewAdapter.getClassType(position) instanceof AbsentModule.AbsentClassModule){
            AbsentModule.AbsentClassModule clazModels = (AbsentModule.AbsentClassModule) classViewAdapter.getClassType(position);

            String detail = "";
            for(int i = 0 ; i < clazModels.getDetailStat().length; i++)
                detail += (i != clazModels.getDetailStat().length - 1 ) ? clazModels.getDetailStat()[i] + "\n" : clazModels.getDetailStat()[i];
            if(detail.isEmpty()) detail= getString(R.string.isEMPTY);

            AbsentDetailViewDialog dialog = AbsentDetailViewDialog.newInstance(
                    clazModels.getClass_Name(),
                    clazModels.getClassRoom_Name(),
                    clazModels.getTeacher(),
                    detail
            );

            dialog.show(mActivity.getSupportFragmentManager(),AbsentDetailViewDialog.class.getSimpleName());
        }
    }
}
