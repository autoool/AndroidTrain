package com.techidea.theroywhy;

import android.app.Application;


import com.techidea.theroywhy.net.Contast;
import com.techidea.theroywhy.net.okhttp.OkHttpsManager;

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



//            OkHttpsManager.getInstance().setContext(getApplicationContext()).
//                    setONECertificates(getResources().openRawResource(R.raw.zc_server));


            OkHttpsManager.getInstance().setContext(getApplicationContext()).
                    setONECertificates(new Buffer().writeUtf8(Contast.CER_SERVER_ZC).inputStream());

            InputStream clientStream = getResources().openRawResource(R.raw.zc_clientbks);
            String clientPwd = "123456";
            InputStream serverStream = getResources().openRawResource(R.raw.zc_serverbks);
            String serverPwd = "123456";

            OkHttpsManager.getInstance().setContext(getApplicationContext())
                    .setBothCertificates(clientStream, clientPwd, serverStream, serverPwd);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage().toString());
        }
    }
}
