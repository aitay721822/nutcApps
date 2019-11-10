package com.nutcunofficial.nutcapps.Base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nutcunofficial.nutcapps.R;

public abstract class TitleBaseFragment extends BaseFragment {
    protected TextView title;
    protected ImageView back;
    protected ImageView userImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(resourceViewId(),container,false);
        back = (ImageView)view.findViewById(R.id.iv_back);
        if(back!=null) {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    backClick(v);
                }
            });
        }
        title = (TextView) view.findViewById(R.id.tv_title);
        if (title != null) {
            title.setText(getTitleName());
        }
        userImage = (ImageView)view.findViewById(R.id.student_icon);
        setStudentIcon();
        initView(view);
        view.post(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        });
        return view;
    }

    protected void backClick(View v) {

    }

    protected abstract void setStudentIcon();

    protected abstract String getTitleName();

    protected abstract @LayoutRes int resourceViewId();

    /**
         * 初始化View物件
         * @param view
         */
    protected abstract void initView(View view);

    /**
         * 載入初始資料
         */
    protected abstract void initData();

}
