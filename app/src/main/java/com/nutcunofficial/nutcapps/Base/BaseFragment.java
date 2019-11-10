package com.nutcunofficial.nutcapps.Base;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

    protected BaseActivity mActivity;

    public void showProgressDialog() {
        mActivity.showProgressDialog();
    }

    public void hideProgressDialog() {
        mActivity.hideProgressDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity.destroyProgressDialog();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mActivity = (BaseActivity) context;
    }

    protected BaseActivity getHoldingActivity() {
        return mActivity;
    }

    public void start(Class<?> claz){
        start(claz,true);
    }

    public void start(Class<?> claz,boolean Clear){
        Intent intent = new Intent(getActivity(),claz);
        startActivity(intent);
        if(Clear) getActivity().finish();
    }
}
