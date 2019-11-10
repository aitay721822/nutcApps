package com.nutcunofficial.nutcapps.Base;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nutcunofficial.nutcapps.R;

public class nutcSwipeRefreshLayout extends SwipeRefreshLayout {


    public nutcSwipeRefreshLayout(@NonNull Context context) {
        super(context);
        this.setColorSchemeResources(R.color.colorAccent);
    }

    public nutcSwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setColorSchemeResources(R.color.colorAccent);
    }



}
