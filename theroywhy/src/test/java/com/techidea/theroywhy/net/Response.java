package com.techidea.theroywhy.net;

/**
 * Created by zchao on 2016/11/9.
 */

public class Response {

    private String ReqTransDate;
    private String TransIndexCode;
    private String AppVersion;
    private String HasReversal;

    public Response() {
    }

    public String getReqTransDate() {
        return ReqTransDate;
    }

    public void setReqTransDate(String reqTransDate) {
        ReqTransDate = reqTransDate;
    }

    public String getTransIndexCode() {
        return TransIndexCode;
    }

    public void setTransIndexCode(String transIndexCode) {
        TransIndexCode = transIndexCode;
    }

    public String getAppVersion() {
        return AppVersion;
    }

    public void setAppVersion(String appVersion) {
        AppVersion = appVersion;
    }

    public String getHasReversal() {
        return HasReversal;
    }

    public void setHasReversal(String hasReversal) {
        HasReversal = hasReversal;
    }
}
