package com.nutcunofficial.nutcapps.Home.Activitys;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.nutcunofficial.nutcapps.Base.BaseActivity;
import com.nutcunofficial.nutcapps.R;

import com.nutcunofficial.nutcapps.Home.Dialogs.ForgotDialog;
import com.nutcunofficial.nutcapps.Home.Contracts.SignInContract;
import com.nutcunofficial.nutcapps.Home.Presenters.SignInPresenter;
import com.nutcunofficial.nutcapps.util.nutcStatusCode;
import com.nutcunofficial.nutcapps.util.nutcSharedPreferences;
import com.nutcunofficial.nutcapps.util.EditTextUtil;
import com.nutcunofficial.nutcapps.util.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.nutcunofficial.nutcapps.util.ToastUtil.showToast;

public class SignInActivity extends BaseActivity implements SignInContract.View, ForgotDialog.onForgotListener {

    private String account;
    private String password;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private Button LogIn;
    private ImageView ValidationCodeView;
    private EditText Inputaccount;
    private EditText Inputpassword;
    private EditText validationCode;
    private Button ForgotPassword;

    private SharedPreferenceManager manager;

    private SignInPresenter SignInpresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        manager = new SharedPreferenceManager(this,nutcSharedPreferences.UserInfoSP,MODE_PRIVATE);

        LogIn = (Button)findViewById(R.id.LogInButton);
        ValidationCodeView = (ImageView) findViewById(R.id.ValidationCode);

        Inputaccount = (EditText)findViewById(R.id.account) ;
        Inputpassword = (EditText)findViewById(R.id.password) ;
        validationCode = (EditText)findViewById(R.id.inputValidationCode) ;
        ForgotPassword = (Button)findViewById(R.id.ForgotPassword);

        SignInpresenter = new SignInPresenter(this);

        clearPrivatePersonalData();

        if(loadFromSharedPreferences()){
            Inputpassword.setText(this.password);
            Inputaccount.setText(this.account);
        }

        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<EditText> text = new ArrayList<>();
                text.add(Inputaccount);
                text.add(Inputpassword);
                text.add(validationCode);

                EditTextUtil util = new EditTextUtil(text).
                        filter(EditTextUtil.CHECK_IF_NON_EMPTY);

                 //Validation
                if(util.getResult()){
                    HashMap<String,String> params = new HashMap<>();
                    params.put("ctl00$ContentPlaceHolder1$Account",Inputaccount.getText().toString());
                    params.put("ctl00$ContentPlaceHolder1$Password",Inputpassword.getText().toString());
                    params.put("ctl00$ContentPlaceHolder1$ValidationCode",validationCode.getText().toString());
                    params.put("ctl00$ContentPlaceHolder1$Login.x","0");
                    params.put("ctl00$ContentPlaceHolder1$Login.y","0");
                    SignInpresenter.requestSignIn(Inputaccount.getText().toString(),Inputpassword.getText().toString(),validationCode.getText().toString());
                }

            }
        });

        ValidationCodeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInpresenter.requestValidationCode();
            }
        });

        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgotDialog forgot = new ForgotDialog();
                forgot.show(getSupportFragmentManager(),ForgotDialog.class.getSimpleName());
            }
        });
    }

    private void clearPrivatePersonalData(){
        if(manager.read("userIcon_image",null)!=null){
            manager.write("userIcon_image",null);
        }
    }

    private boolean loadFromSharedPreferences() {
        account = manager.read("account",null);
        password = manager.read("password",null);
        if(account!=null && password != null)
            return true;
        else
            return false;
    }


    private void saveToSharedPreferences(String account,String password) {
        if(account==null||password==null) return;
        if(account==""||password=="") return;
        manager.write("account",account);
        manager.write("password",password);
    }

    @Override
    public void showSignInStatus(final int SignInStatus) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                switch(SignInStatus){
                    case nutcStatusCode.SIGN_IN_SUCCESS:
                        showToast(nutcStatusCode.FIND_ERROR_CODE(SignInStatus), Toast.LENGTH_SHORT);
                        saveToSharedPreferences(Inputaccount.getText().toString(),Inputpassword.getText().toString());
                        start(HomeActivity.class);
                        break;
                    case nutcStatusCode.NEED_CHANGE_PASS:
                        showToast(nutcStatusCode.FIND_ERROR_CODE(SignInStatus), Toast.LENGTH_SHORT);
                        start(ChangePassword.class);
                        break;
                    case nutcStatusCode.SIGN_IN_FAILURE:
                        showToast(nutcStatusCode.FIND_ERROR_CODE(SignInStatus), Toast.LENGTH_SHORT);
                        ValidationCodeView.performClick();
                        break;
                    case nutcStatusCode.NOT_SIGN_IN:
                        showToast(nutcStatusCode.FIND_ERROR_CODE(SignInStatus), Toast.LENGTH_SHORT);
                        break;
                }
            }
        });
    }

    @Override
    public void showValidationCode(final Bitmap bitmap) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                ValidationCodeView.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    public void showValidationError(final int errorCode) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(nutcStatusCode.FIND_ERROR_CODE(errorCode), Toast.LENGTH_SHORT);
                ValidationCodeView.setImageResource(R.drawable.vc_ex);
            }
        });
    }

    @Override
    public void showResetPasswordSend(final String msg) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                showToast(msg, Toast.LENGTH_SHORT);
            }
        });
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
    protected void onResume() {
        if(SignInpresenter!=null){
            SignInpresenter.checkSignIn();
            SignInpresenter.requestValidationCode();
        }
        super.onResume();
    }

    @Override
    public void doForgotTask(String schoolNumber, String idnumber, String QuestionHash, String QuestionAnswer) {
        SignInpresenter.requestResetPassword(schoolNumber,idnumber,QuestionHash,QuestionAnswer);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
