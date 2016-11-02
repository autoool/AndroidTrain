package com.techidea.theroywhy.net;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.techidea.theroywhy.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zchao on 2016/11/2.
 */

public class NetActivity extends Activity {

    private class NetHandle extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            textViewLog.setText(bundle.getString("msg"));
        }
    }

    @Bind(R.id.textviewlog)
    TextView textViewLog;

    HttpClientManager httpClientManager;
    NetHandle netHandle;
    OkHttpManager okHttpManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net);
        ButterKnife.bind(this);
        httpClientManager = new HttpClientManager();
        netHandle = new NetHandle();
        okHttpManager = OkHttpManager.getInstance();
    }

    @OnClick(R.id.buttonclean)
    void clean() {
        textViewLog.setText("");
    }

    @OnClick(R.id.buttonhttpclient)
    void buttonhttpclient() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = httpClientManager.httpClientGet(Contast.URL_LOCALHOST_HTTPS_ONE);
                sendMessage(result);
            }
        }).start();

    }

    @OnClick(R.id.buttonhttpsclient)
    void buttonhttpsclient() {

    }

    @OnClick(R.id.buttonhttpsclientone)
    void buttonhttpsclientone() {

    }

    @OnClick(R.id.buttonokhttp)
    void buttonokhttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = okHttpManager.runHttp(Contast.URL_LOCALHOST);
                sendMessage(result);
            }
        }).start();

    }

    @OnClick(R.id.buttonokhttps)
    void buttonokhttps() {

    }

    @OnClick(R.id.buttonokhttpsone)
    void buttonokhttpsone() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = okHttpManager.runHttpOneWay(Contast.URL_LOCALHOST_HTTPS_ONE);
                sendMessage(result);
            }
        }).start();
    }

    @OnClick(R.id.buttonhttpsurl)
    void buttonhttpsurl() {

    }

    @OnClick(R.id.buttonhttpurl)
    void buttonhttpurl() {

    }

    @OnClick(R.id.buttonhttpsurlone)
    void buttonhttpsurlone() {

    }

    private void sendMessage(String msg) {
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("msg", msg);
        message.setData(bundle);
        netHandle.sendMessage(message);
    }
}
