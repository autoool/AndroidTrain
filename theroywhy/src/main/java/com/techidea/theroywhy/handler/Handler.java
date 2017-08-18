package com.techidea.theroywhy.handler;


/**
 * Created by Administrator on 2017/6/18.
 */

public class Handler {

    private MessageQueue mQueue;
    private Looper mLooper;

    public Handler(){

    }

    public  void sendMessage(Message msg){
        msg.target = this;
        mQueue.enqueueMessage(msg);
    }

    public void dispatchMessage(Message msg){
        handlerMessage(msg);
    }

    public void handlerMessage(Message msg){

    }
}
