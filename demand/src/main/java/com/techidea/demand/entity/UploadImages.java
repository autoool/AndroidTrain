package com.techidea.demand.entity;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2016/11/25.
 */

public class UploadImages {

    private String url;
    private int tag;
    private String fileName;
    private String filePath;
    private Bitmap bitmap;

    public UploadImages(String url,int tag, String fileName, String filePath, Bitmap bitmap) {
        this.tag = tag;
        this.url = url;
        this.fileName = fileName;
        this.filePath = filePath;
        this.bitmap = bitmap;
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
}
