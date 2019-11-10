package com.nutcunofficial.nutcapps.Home.Activitys;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.nutcunofficial.nutcapps.Base.TitleBaseActivity;
import com.nutcunofficial.nutcapps.Home.Adapters.ClassmateAdapter;
import com.nutcunofficial.nutcapps.Home.Contracts.ClassmateContracts;
import com.nutcunofficial.nutcapps.Home.Modules.ClassmateModule;
import com.nutcunofficial.nutcapps.Home.Presenters.ClassmatePresenter;
import com.nutcunofficial.nutcapps.R;
import com.nutcunofficial.nutcapps.util.GlideApp;
import com.nutcunofficial.nutcapps.util.nutcSharedPreferences;
import com.nutcunofficial.nutcapps.util.nutcStatusCode;
import com.nutcunofficial.nutcapps.util.SharedPreferenceManager;

import java.util.List;

import static com.nutcunofficial.nutcapps.util.ToastUtil.showToast;

public class ClassmatesActivity extends TitleBaseActivity implements SearchView.OnQueryTextListener, ClassmateContracts.View {
    private ClassmateAdapter adapter;
    private SearchView mSearchView;
    private RecyclerView classmateRecyclerView;
    private Handler mDelivery = new Handler(Looper.getMainLooper());
    private ClassmatePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new ClassmatePresenter(this);

        setContentView(R.layout.activity_classmates);
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
                    GlideApp.with(ClassmatesActivity.this).load(url).apply(requestOptions).into(userImage);
                }
            });
        }
        else{
            presenter.requestUserImage();
        }
    }

    @Override
    protected String getTitleName() {
        return getString(R.string.classmateTitle);
    }

    @Override
    protected void initView() {
        this.back.setVisibility(View.VISIBLE);
        classmateRecyclerView = findViewById(R.id.classmateView);
        classmateRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        classmateRecyclerView.setHasFixedSize(false);
        adapter = new ClassmateAdapter(this);
        classmateRecyclerView.setAdapter(adapter);

        mSearchView = findViewById(R.id.searchView);
        mSearchView.setOnQueryTextListener(this);
    }

    @Override
    protected void initData() {
        presenter.requestClassmateList();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return true;
    }

    @Override
    public void setUserImage(String url) {
        SharedPreferenceManager manager = new SharedPreferenceManager(getApplicationContext(),nutcSharedPreferences.UserInfoSP,Context.MODE_PRIVATE);
        manager.write("userIcon_image",url);
        setStudentIcon();
    }

    @Override
    public void showClassmateList(final List<ClassmateModule> list) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if(adapter!=null){
                    adapter.add(list);
                }
            }
        });
    }

    @Override
    public void showError(final int StatusCode) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                showToast(nutcStatusCode.FIND_ERROR_CODE(StatusCode),Toast.LENGTH_SHORT);
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
}
