package com.techidea.theroywhy;

import android.app.Application;

import com.techidea.theroywhy.net.OkHttpManager;

/**
 * Created by zchao on 2016/11/2.
 */

public class WhyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            OkHttpManager.getInstance().setContext(getApplicationContext())
                    .setCertificates(getAssets().open("zchao_server.cer"));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage().toString());
        }

    }
}
