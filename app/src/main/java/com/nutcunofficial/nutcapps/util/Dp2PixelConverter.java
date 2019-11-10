package com.nutcunofficial.nutcapps.util;

import android.util.DisplayMetrics;

import com.nutcunofficial.nutcapps.nutcApplication;

public class Dp2PixelConverter {

    public static int convertDpToPixel(int dp) {
        DisplayMetrics displayMetrics = nutcApplication.getResourses().getDisplayMetrics();
        return (int) (dp * displayMetrics.density);
    }

}
