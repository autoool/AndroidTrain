package com.techidea.theroywhy.handler;

/**
 * Created by Administrator on 2017/6/18.
 */

public class Looper {

    static ThreadLocal<Looper> threadLocal = new ThreadLocal<>();


    public static void loop() {

    }

    public static Looper myLooper() {
        return threadLocal.get();
    }

    public static Looper prepare() {
        if (threadLocal.get() != null) {
            throw new RuntimeException();
        }
        threadLocal.set(new Looper());
        return null;
    }
}
