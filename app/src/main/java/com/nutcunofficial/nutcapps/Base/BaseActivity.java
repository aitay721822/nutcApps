package com.nutcunofficial.nutcapps.Base;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.nutcunofficial.nutcapps.util.ProgressDialogUtil;

public abstract class BaseActivity extends AppCompatActivity {

    private static ProgressDialogUtil util = new ProgressDialogUtil();

    public void showProgressDialog() {
        util.showProgressDialog(BaseActivity.this);
    }

    public void hideProgressDialog() {
        util.hideProgressDialog();
    }

    public void destroyProgressDialog(){util.destroyProgressDialog();}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyProgressDialog();
    }

    public void start(Class<?> claz){
        start(claz,true);
    }

    public void start(Class<?> claz,boolean Clear){
        Intent intent = new Intent(this,claz);
        startActivity(intent);
        if(Clear) this.finish();
    }
}
