package com.techidea.theroywhy.handler;

/**
 * Created by Administrator on 2017/6/18.
 */

public class Message {

    //目标
    Handler target;
    private int what;
    private Object obj;

    @Override
    public String toString() {
        return obj.toString();
    }
}
