package com.nutcunofficial.nutcapps.Home.Activitys;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.nutcunofficial.nutcapps.Base.TitleBaseActivity;
import com.nutcunofficial.nutcapps.Home.Contracts.ChangePasswordContract;
import com.nutcunofficial.nutcapps.Home.Presenters.ChangePasswordPresenter;
import com.nutcunofficial.nutcapps.R;
import com.nutcunofficial.nutcapps.nutcApplication;
import com.nutcunofficial.nutcapps.util.GlideApp;
import com.nutcunofficial.nutcapps.util.ToastUtil;
import com.nutcunofficial.nutcapps.util.nutcSharedPreferences;
import com.nutcunofficial.nutcapps.util.nutcStatusCode;
import com.nutcunofficial.nutcapps.util.EditTextUtil;
import com.nutcunofficial.nutcapps.util.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.List;

import static com.nutcunofficial.nutcapps.util.ToastUtil.showToast;

public class ChangePassword extends TitleBaseActivity implements ChangePasswordContract.View,View.OnClickListener {

    private ChangePasswordPresenter presenter;
    private Handler mDelivery = new Handler(Looper.getMainLooper());

    private EditText OldPassword;
    private EditText NewPassword;
    private EditText NewPasswordAgain;
    private Button Send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ChangePasswordPresenter(this);
        setContentView(R.layout.activity_change_password);
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
                    GlideApp.with(ChangePassword.this).load(url).apply(requestOptions).into(userImage);
                }
            });
        }
        else{
            presenter.requestUserImage();
        }
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
    public void showChangePasswordStatus(final String msg) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                showToast(msg,Toast.LENGTH_SHORT);
                presenter.checkSuccessful();
            }
        });
    }

    @Override
    public void showSuccessful(final int Status) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                switch (Status){
                    case nutcStatusCode.NOT_SIGN_IN:
                        showToast(nutcStatusCode.FIND_ERROR_CODE(nutcStatusCode.NOT_SIGN_IN), Toast.LENGTH_SHORT);
                        start(SignInActivity.class,true);
                        break;
                    case nutcStatusCode.SIGN_IN_SUCCESS:
                        showToast(nutcStatusCode.FIND_ERROR_CODE(nutcStatusCode.SIGN_IN_SUCCESS), Toast.LENGTH_SHORT);
                        start(SignInActivity.class,true);
                        break;
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
    protected String getTitleName() {
        return nutcApplication.getResourses().getString(R.string.CHANGE_PASSWORD);
    }

    @Override
    protected void initView() {
        this.back.setVisibility(View.VISIBLE);
        OldPassword = (EditText)findViewById(R.id.oldPassword);
        NewPassword = (EditText)findViewById(R.id.newPassword);
        NewPasswordAgain = (EditText)findViewById(R.id.checkNewPassword);
        Send = (Button)findViewById(R.id.sendBtn);
        Send.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if(viewId == R.id.sendBtn){

            List<EditText> Password_EQUAL = new ArrayList<>();
            Password_EQUAL.add(NewPassword); Password_EQUAL.add(NewPasswordAgain);

            List<EditText> Password_NOT_EQUAL = new ArrayList<>();
            Password_NOT_EQUAL.add(OldPassword); Password_NOT_EQUAL.add(NewPassword);

            EditTextUtil newPassChecker = new EditTextUtil(Password_EQUAL).
                    filter(EditTextUtil.CHECK_IF_NON_EMPTY).
                    filter(EditTextUtil.CHECK_IF_EQUAL).
                    filter(EditTextUtil.CHECK_PASSWORD_VALID);

            EditTextUtil OldPassChecker = new EditTextUtil(Password_NOT_EQUAL).
                    filter(EditTextUtil.CHECK_IF_NON_EMPTY).
                    filter(EditTextUtil.CHECK_PASSWORD_VALID);

            if(newPassChecker.getResult() && OldPassChecker.getResult()){
                String oldPassword = OldPassword.getText().toString();
                String newPassword = NewPassword.getText().toString();
                presenter.requestChangePass(oldPassword,newPassword);
            }
        }
    }
}
