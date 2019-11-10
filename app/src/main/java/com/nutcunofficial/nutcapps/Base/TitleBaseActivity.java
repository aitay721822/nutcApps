package com.nutcunofficial.nutcapps.Base;

import android.media.Image;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;

import com.nutcunofficial.nutcapps.R;

public abstract class TitleBaseActivity extends BaseActivity {

    private Handler mHandler = new Handler(Looper.getMainLooper());
    protected ImageView userImage;
    protected ImageView back;
    protected TextView title;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        back = (ImageView)findViewById(R.id.iv_back);
        if(back!=null){
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    backClick(v);
                }
            });
        }
        userImage = (ImageView)findViewById(R.id.student_icon);
        setStudentIcon();
        title = (TextView)findViewById(R.id.tv_title);
        if(title!=null){
            title.setText(getTitleName());
        }
        initView();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        });
    }

    protected void backClick(View v) {finish();}

    protected abstract void setStudentIcon();

    public void setTitleName(String title) {
        if (title != null) {
            this.title.setText(title);
        }
    }

    protected abstract String getTitleName();

    /**
     * 初始化View物件
     */
    protected abstract void initView();

    /**
     * 載入初始資料
     */
    protected abstract void initData();
}
