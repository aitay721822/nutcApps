package com.nutcunofficial.nutcapps.Home.Fragments;

import android.content.Context;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.nutcunofficial.nutcapps.Base.TitleBaseFragment;
import com.nutcunofficial.nutcapps.Home.Adapters.StudentAdapter;
import com.nutcunofficial.nutcapps.Home.Composings.ItemDecoration;
import com.nutcunofficial.nutcapps.Home.Contracts.UserContract;
import com.nutcunofficial.nutcapps.Home.Modules.StudentModule;
import com.nutcunofficial.nutcapps.Home.Presenters.userPresenter;
import com.nutcunofficial.nutcapps.R;
import com.nutcunofficial.nutcapps.util.GlideApp;
import com.nutcunofficial.nutcapps.util.ToastUtil;
import com.nutcunofficial.nutcapps.util.nutcSharedPreferences;
import com.nutcunofficial.nutcapps.util.nutcStatusCode;
import com.nutcunofficial.nutcapps.util.SharedPreferenceManager;

public class userFragment extends TitleBaseFragment implements UserContract.View {

    private final String TAG = userFragment.class.getSimpleName();

    private userPresenter presenter;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private RecyclerView StudentData;

    private StudentAdapter adapter;

    private TextView StudentType;

    private TextView StudentClass;

    private TextView StudentSecType;

    private ImageView card_view_user_image;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"userFragment has been created!");
        presenter = new userPresenter(this);
    }

    @Override
    protected void setStudentIcon() {
        SharedPreferenceManager manager = new SharedPreferenceManager(this.mActivity.getApplicationContext(), nutcSharedPreferences.UserInfoSP, Context.MODE_PRIVATE);
        final String url = manager.read("userIcon_image",null);
        if(url!=null){
            final RequestOptions requestOptions = new RequestOptions().transform(new FitCenter(),new RoundedCorners(250));
            final RequestOptions cardView_requestOptions = new RequestOptions().transform(new FitCenter(),new RoundedCorners(8));

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    GlideApp.with(userFragment.this).load(url).apply(requestOptions).into(userImage);
                    GlideApp.with(userFragment.this).load(url).apply(cardView_requestOptions).into(card_view_user_image);
                }
            });
        }
        else{
            presenter.requestUserImage();
        }
    }

    @Override
    protected String getTitleName() {
        return getResources().getString(R.string.title_user);
    }

    @Override
    protected int resourceViewId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initView(View view) {
        this.back.setVisibility(View.GONE);

        StudentData = (RecyclerView)view.findViewById(R.id.studentData);
        StudentClass = (TextView)view.findViewById(R.id.stu_class);
        StudentType = (TextView)view.findViewById(R.id.stu_type);
        StudentSecType = (TextView)view.findViewById(R.id.stu_sec_type);

        StudentData.setHasFixedSize(false);
        adapter = new StudentAdapter(this.mActivity.getApplicationContext());
        StudentData.setAdapter(adapter);
        StudentData.addItemDecoration(new DividerItemDecoration(this.mActivity.getApplicationContext(),DividerItemDecoration.VERTICAL));
        StudentData.setLayoutManager(new LinearLayoutManager(this.mActivity.getApplicationContext()));

        userImage = (ImageView)view.findViewById(R.id.student_icon);
        card_view_user_image = (ImageView)view.findViewById(R.id.userImage_card);
        //userImage.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        presenter.requestStuData();
    }

    @Override
    public void showError(final int Status) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showToast(nutcStatusCode.FIND_ERROR_CODE(Status), Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    public void setStuData(final StudentModule stuData) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                StudentClass.setText(stuData.getClass_Type());
                StudentType.setText(stuData.getStudent_Type());
                StudentSecType.setText(stuData.getStudent_Sec_Type());

                adapter.add(stuData.toList());
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void setUserImage(final String url) {
        SharedPreferenceManager manager = new SharedPreferenceManager(this.mActivity.getApplicationContext(),nutcSharedPreferences.UserInfoSP,Context.MODE_PRIVATE);
        manager.write("userIcon_image",url);
        setStudentIcon();
    }


    @Override
    public void showProgress(boolean isShow) {
        if(isShow)
            showProgressDialog();
        else
            hideProgressDialog();
    }

    public static userFragment newInstance(){
        return new userFragment();
    }
}
