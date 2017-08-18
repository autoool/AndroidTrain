package com.techidea.controls;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Administrator on 2017/7/4.
 */

public class ControlsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG)
            LeakCanary.install(this);
    }
}
