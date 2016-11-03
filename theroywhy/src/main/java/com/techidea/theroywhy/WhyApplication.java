package com.techidea.theroywhy;

import android.app.Application;
import android.renderscript.ScriptGroup;

import com.techidea.theroywhy.net.Contast;
import com.techidea.theroywhy.net.OkHttpManager;

import java.io.InputStream;

import okio.Buffer;

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
            InputStream clientStream = getAssets().open("zchao_client.bks");
            String clientPwd = "123456";
            InputStream serverStream = getAssets().open("zchao_server.bks");
            String serverPwd = "123456";
//            OkHttpManager.getInstance().setContext(getApplicationContext()).
//                    setBothWayCertificates(getAssets().open("zchao_server.bks"));
            OkHttpManager.getInstance().setContext(getApplicationContext())
                    .setCertificates(clientStream, clientPwd, serverStream, serverPwd);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage().toString());
        }
    }
}
