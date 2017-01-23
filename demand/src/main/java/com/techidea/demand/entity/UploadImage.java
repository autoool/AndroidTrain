package com.techidea.demand.entity;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2016/11/25.
 */

public class UploadImage {

    private String url;
    private int tag;
    private String fileName;
    private String filePath;
    private Bitmap bitmap;
    private int resId;

    public UploadImage() {
    }

    public UploadImage(int tag, int resId) {
        this.tag = tag;
        this.resId = resId;
        this.filePath = "";
        this.url = "";
        this.fileName = "";
        this.bitmap = null;
    }

    public UploadImage(String url, int tag, String fileName,
                       String filePath, Bitmap bitmap, int resId) {
        this.tag = tag;
        this.url = url;
        this.fileName = fileName;
        this.filePath = filePath;
        this.bitmap = bitmap;
        this.resId = resId;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public int getResId() {
        return resId;
    }
}
