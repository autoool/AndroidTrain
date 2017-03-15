package com.techidea.theroywhy.net.base;

import android.os.Build;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by zchao on 2017/3/14.
 */

public class Platform {

//    private static final Platform PLATFORM;

    public static Platform get() {
        return null;
    }

    private static Platform findPlatform() {
        try {

            Class.forName("android.os.Build");
            if (Build.VERSION.SDK_INT != 0) {
                return null;
            }
        } catch (ClassNotFoundException e) {

        }
        return new Platform();
    }

    public Executor defaultCallbackExecutor() {
        return Executors.newCachedThreadPool();
    }

    public void execute(Runnable runnable) {
        defaultCallbackExecutor().execute(runnable);
    }

    static class Android extends Platform {

    }


}
