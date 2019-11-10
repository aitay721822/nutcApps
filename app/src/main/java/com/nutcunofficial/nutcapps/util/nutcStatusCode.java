package com.nutcunofficial.nutcapps.util;

import com.nutcunofficial.nutcapps.R;
import com.nutcunofficial.nutcapps.nutcApplication;

public class nutcStatusCode {
    public static final int NO_INTERNET_ERROR_CODE = -1;
    public static final int SIGN_IN_SUCCESS = 0;
    public static final int SIGN_IN_FAILURE = 1;
    public static final int NEED_CHANGE_PASS = 2;
    public static final int NOT_SIGN_IN = 3;
    public static final int IS_CHANGED_PASSWORD = 4;
    public static final int ACTIVED_ERROR = 5;
    public static final int ACTIVED_SUCCESSFUL = 6;
    public static final int NO_FETCH_IMAGE = 7;
    public static final int CANT_FETCH_NOTICE = 8;
    public static final int FETCH_ERROR = 9;

    public static String FIND_ERROR_CODE(int ERROR_CODE){
        switch (ERROR_CODE){
            case NO_INTERNET_ERROR_CODE:
                return nutcApplication.getResourses().getString(R.string.noInternetConnection);
            case NEED_CHANGE_PASS:
                return nutcApplication.getResourses().getString(R.string.needChangePass);
            case SIGN_IN_SUCCESS:
                return nutcApplication.getResourses().getString(R.string.loginSuccessful);
            case SIGN_IN_FAILURE:
                return nutcApplication.getResourses().getString(R.string.loginError);
            case IS_CHANGED_PASSWORD:
                return nutcApplication.getResourses().getString(R.string.IS_CHANGED_PASSWORD);
            case ACTIVED_ERROR:
                return nutcApplication.getResourses().getString(R.string.ACTIVED_ERROR);
            case ACTIVED_SUCCESSFUL:
                return nutcApplication.getResourses().getString(R.string.ACTIVED_SUCCESSFUL);
            case NO_FETCH_IMAGE:
                return nutcApplication.getResourses().getString(R.string.FETCH_IMAGE_ERROR);
            case CANT_FETCH_NOTICE:
                return nutcApplication.getResourses().getString(R.string.CANT_FETCH_NOTICE);
            case NOT_SIGN_IN:
                return nutcApplication.getResourses().getString(R.string.NOT_LOG_IN);
            case FETCH_ERROR:
                return nutcApplication.ResourcesString(R.string.FETCH_ERROR);
            default:
                return "";
        }
    }
}
