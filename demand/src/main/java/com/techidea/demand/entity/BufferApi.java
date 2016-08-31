package com.techidea.demand.entity;

/**
 * Created by Administrator on 2016/8/31.
 */
public class BufferApi {

    private String url;
    private String params;

    public BufferApi() {
    }

    public BufferApi(String url, String params) {
        this.url = url;
        this.params = params;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
