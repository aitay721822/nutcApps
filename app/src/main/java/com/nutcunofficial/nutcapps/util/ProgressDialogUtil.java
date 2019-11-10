package com.nutcunofficial.nutcapps.util;

/**
 *  簡單包裝 progressDialog 類
 */

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogUtil {

    private ProgressDialog mProgressDialog;

    public void showProgressDialog(Context context) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("載入中...");
        }
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void destroyProgressDialog(){
        if(mProgressDialog!=null){
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}
