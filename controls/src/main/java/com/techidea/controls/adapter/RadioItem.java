package com.techidea.controls.adapter;

/**
 * Created by Administrator on 2016/8/11.
 */
public class RadioItem {

    private int id;
    private String content;
    private boolean isSelect;

    public RadioItem() {
    }

    public RadioItem(int id, String content, boolean isSelect) {
        this.id = id;
        this.content = content;
        this.isSelect = isSelect;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
