package com.techidea.theroywhy.handler;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2017/6/18.
 */

public class MessageQueue {


    //互斥锁和条件变量
    Lock lock;
    Condition notEmpty;
    Condition notFull;


    Message[] items;
    int putIndex;
    int takeIndex;
    int count;

    public MessageQueue(){
        this.items = new Message[50];
        this.lock = new ReentrantLock();
        this.notEmpty = lock.newCondition();
        this.notFull = lock.newCondition();
    }

    //当消息队列已经满,停止生产，阻塞
    public void enqueueMessage(Message msg){
        try {
            lock.lock();
            while (count==items.length){
                try {
                    notFull.await();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            items[putIndex] = msg;
            putIndex=(++putIndex== items.length)?0:putIndex;
            count++;
            notEmpty.signalAll();
        }finally {
            lock.unlock();
        }

    }

    //当消息队列为空，停止消费，主线程阻塞
    public Message next(){
        Message msg = null;
        try {
            lock.lock();
            while (count==0){
                try {
                    notEmpty.wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            msg = items[takeIndex];
            takeIndex = (++takeIndex==items.length)?0:takeIndex;
            count--;

            notFull.signalAll();//通知生产者可以生产
        }finally {
            lock.unlock();
        }
        return msg;
    }


}
