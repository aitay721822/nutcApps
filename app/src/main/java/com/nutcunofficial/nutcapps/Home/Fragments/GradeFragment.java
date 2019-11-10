package com.nutcunofficial.nutcapps.Home.Fragments;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.nutcunofficial.nutcapps.Base.BaseFragment;
import com.nutcunofficial.nutcapps.Base.TitleBaseFragment;
import com.nutcunofficial.nutcapps.Home.Adapters.ButtonAdapter;
import com.nutcunofficial.nutcapps.Home.Adapters.ClassViewAdapter;
import com.nutcunofficial.nutcapps.Home.Contracts.GradeContract;
import com.nutcunofficial.nutcapps.Home.Dialogs.GradeDetailViewDialog;
import com.nutcunofficial.nutcapps.Home.Modules.GradeModule;
import com.nutcunofficial.nutcapps.Home.Presenters.GradePresenter;
import com.nutcunofficial.nutcapps.R;
import com.nutcunofficial.nutcapps.util.GlideApp;
import com.nutcunofficial.nutcapps.util.SharedPreferenceManager;
import com.nutcunofficial.nutcapps.util.ToastUtil;
import com.nutcunofficial.nutcapps.util.nutcSharedPreferences;
import com.nutcunofficial.nutcapps.util.nutcStatusCode;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GradeFragment extends TitleBaseFragment implements GradeContract.View,ButtonAdapter.btnOnClickListener,ClassViewAdapter.onDetailSelectedListener {

    private static final String TAG = GradeFragment.class.getSimpleName();

    private GradePresenter presenter;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private RecyclerView buttonSwitch;
    private RecyclerView detailView;

    private TextView credit;
    private TextView accept_credit;
    private TextView not_accept_credit;
    private TextView avg_grade;
    private TextView conduct;

    private ButtonAdapter BtnAdapter;
    private ClassViewAdapter ClassAdapter;
    private GradeModule gradeData;

    public static GradeFragment newInstance() {
        return new GradeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"GradeFragment has been created!");
        presenter = new GradePresenter(this);
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
                    GlideApp.with(GradeFragment.this).load(url).apply(requestOptions).into(userImage);
                }
            });
        }
        else{
            presenter.requestUserImage();
        }
    }

    @Override
    protected String getTitleName() {
        return getString(R.string.title_grades);
    }

    @Override
    protected int resourceViewId() {
        return R.layout.grade_fragment;
    }

    @Override
    protected void initView(View view) {
        super.back.setVisibility(View.GONE);

        buttonSwitch = view.findViewById(R.id.button_select_grade);
        detailView = view.findViewById(R.id.grade_status);
        credit = view.findViewById(R.id.credit);
        accept_credit = view.findViewById(R.id.accept_credit);
        not_accept_credit = view.findViewById(R.id.not_accept_credit);
        avg_grade = view.findViewById(R.id.average_grade);
        conduct = view.findViewById(R.id.absent_grade);

        BtnAdapter = new ButtonAdapter(getContext(),this);
        ClassAdapter = new ClassViewAdapter(getContext(),this);


        buttonSwitch.setLayoutManager(new LinearLayoutManager(mActivity.getApplicationContext(), LinearLayoutManager.HORIZONTAL,false));
        buttonSwitch.setHasFixedSize(true);
        buttonSwitch.setAdapter(BtnAdapter);

        detailView.setLayoutManager(new LinearLayoutManager(mActivity.getApplicationContext()));
        detailView.setItemAnimator(new DefaultItemAnimator());
        detailView.setHasFixedSize(false);
        ///////////////////
        detailView.setAdapter(ClassAdapter);
        ///////////////////

    }

    @Override
    protected void initData() {
        presenter.requestGradeList();
    }

    @Override
    public void showGradeList(final GradeModule data) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {

                gradeData = data;

                String[] sorting = new String[gradeData.getGrade().size()];

                // first add button
                int index = 0;
                for(Map.Entry<String, GradeModule.GradeInnerModule> g : gradeData.getGrade().entrySet()){
                    sorting[index++]=gradeData.getTag(g.getKey());
                }
                Arrays.sort(sorting);
                for(String i : sorting) BtnAdapter.addButton(i);

                if(BtnAdapter.getItemCount() > 0) displayGrade(0);

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
        SharedPreferenceManager manager = new SharedPreferenceManager(this.mActivity.getApplicationContext(),nutcSharedPreferences.UserInfoSP,Context.MODE_PRIVATE);
        manager.write("userIcon_image",url);
        setStudentIcon();
    }

    @Override
    public void showProgress(boolean isShow) {
        if(isShow) showProgressDialog();
        else hideProgressDialog();
    }

    private void displayGrade(int position){
        if(gradeData == null) return;

        String TAG = BtnAdapter.getText(position);
        String yysem = null;

        // get yysem
        for(Map.Entry<String, GradeModule.GradeInnerModule> entry : gradeData.getGrade().entrySet())
            if(TAG.equals(entry.getValue().getTAG())) yysem = entry.getKey();

        if(yysem!=null){
            // get Total Grade //
            GradeModule.GradeTotalModule gradeTotalModule = gradeData.getTotalGrade(yysem);

            // get <List> Class Grade //
            List<GradeModule.GradeClassModule> gradeClassModule = gradeData.getClassGrade(yysem);

            // set Text //
            this.credit.setText(gradeTotalModule.getAll_credit());
            this.accept_credit.setText(gradeTotalModule.getAccept_credit());
            this.not_accept_credit.setText(gradeTotalModule.getNot_accept_credit());
            this.avg_grade.setText(gradeTotalModule.getAvg_Grade());
            this.conduct.setText(gradeTotalModule.getConduct());

            ClassAdapter.clear();
            for (GradeModule.GradeClassModule i : gradeClassModule){
                ClassAdapter.addClass(i);
            }
        }
    }

    @Override
    public void onBtnRequestSwitchListener(int position) {
        displayGrade(position);
    }

    @Override
    public void onShowDetail(int position) {
        if(gradeData == null) return;
        if(ClassAdapter.getClassType(position) instanceof GradeModule.GradeClassModule){
            GradeModule.GradeClassModule data = (GradeModule.GradeClassModule)ClassAdapter.getClassType(position);
            GradeDetailViewDialog dialog = GradeDetailViewDialog.newInstance(
                    data.getClass_Name(),
                    data.getClassRoom_Name(),
                    data.getGroup(),
                    data.getType(),
                    data.getCredit(),
                    data.getGrade()
            );
            dialog.show(mActivity.getSupportFragmentManager(),GradeDetailViewDialog.class.getSimpleName());
        }
    }
}
