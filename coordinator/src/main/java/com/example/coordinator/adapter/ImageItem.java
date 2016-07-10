package com.example.coordinator.adapter;

/**
 * Created by Administrator on 2016/7/9.
 */
public class ImageItem {

    private String name;
    private String url;

    public ImageItem(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
