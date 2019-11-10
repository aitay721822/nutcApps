package com.nutcunofficial.nutcapps.util;

import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.nutcunofficial.nutcapps.nutcApplication;

public class ToastUtil {

    public static void showToast(String msg,int TOAST_LENGTH){
        Toast.makeText(nutcApplication.getInstance().getApplicationContext(), msg, TOAST_LENGTH).show();
    }

    public static void showSnackBar(View view, String msg, int SNACK_LENGTH){
        Snackbar.make(view,msg,SNACK_LENGTH).show();
    }

    public static void showSnackBar(View view, String msg, int SNACK_LENGTH, String ActionMsg,int TextColor, View.OnClickListener listener){
        Snackbar.make(view,msg,SNACK_LENGTH).setAction(ActionMsg,listener).setActionTextColor(TextColor).show();
    }
}
