package com.example.material.adapter;

/**
 * Created by Administrator on 2016/7/9.
 */
public class ImageItem {

    private String name;
    private String url;
    private int id;

    public ImageItem(String name, String url,int id) {
        this.name = name;
        this.url = url;
        this.id = id;
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

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
