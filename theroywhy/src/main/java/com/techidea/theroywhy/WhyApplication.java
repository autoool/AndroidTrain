package com.techidea.theroywhy;

import android.app.Application;

import com.techidea.theroywhy.net.OkHttpManager;

import java.io.InputStream;

/**
 * Created by zchao on 2016/11/2.
 */

public class WhyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initHttps();
    }

    private void initHttps() {

        try {

            InputStream clientStream = getResources().openRawResource(R.raw.zchao_clientbks);
            String clientPwd = "123456";
            InputStream serverStream = getResources().openRawResource(R.raw.zchao_serverbks);
            String serverPwd = "123456";
            OkHttpManager.getInstance().setContext(getApplicationContext()).
                    setONECertificates(getResources().openRawResource(R.raw.zchao_servercer));

            OkHttpManager.getInstance().setContext(getApplicationContext())
                    .setBothCertificates(clientStream, clientPwd, serverStream, serverPwd);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage().toString());
        }
    }
}
