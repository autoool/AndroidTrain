package com.techidea.demand.pay.scanpay;

/**
 * Created by zchao on 2017/5/31.
 */

public class ScanPayResult {

    private boolean isSuccess;
    private String errorCode;//渠道不可用code 7777

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
