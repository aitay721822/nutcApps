package com.nutcunofficial.network.exception;

public class networkException extends Exception {

    private int errorCode;
    private String errorMsg;

    public networkException(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

}
