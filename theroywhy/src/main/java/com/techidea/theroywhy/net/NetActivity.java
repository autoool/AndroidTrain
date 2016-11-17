package com.techidea.theroywhy.net;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.techidea.theroywhy.R;
import com.techidea.theroywhy.net.httpclient.HttpClientManager;
import com.techidea.theroywhy.net.httpurl.HttpUrlConnectManager;
import com.techidea.theroywhy.net.interf.ResponseCallBack;
import com.techidea.theroywhy.net.volley.VolleyManager;
import com.techidea.theroywhy.net.okhttp.OkHttpsManager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zchao on 2016/11/2.
 */

public class NetActivity extends AppCompatActivity {

    private class NetHandle extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            textViewLog.setText(bundle.getString("msg"));
        }
    }

    @Bind(R.id.textviewlog)
    TextView textViewLog;
    GestureDetector mGestureDetector;
    HttpClientManager httpClientManager;
    NetHandle netHandle;
    OkHttpsManager okHttpManager;
    VolleyManager volleyManager;
    HttpUrlConnectManager httpUrlConnectManager;
    View decorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net);
        ButterKnife.bind(this);
        getWindow().addFlags(5);
        decorView = getWindow().getDecorView();

        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);

        httpClientManager = new HttpClientManager(this);
        httpUrlConnectManager = HttpUrlConnectManager.getInstance();

        netHandle = new NetHandle();
        okHttpManager = OkHttpsManager.getInstance();
        volleyManager = new VolleyManager(getApplicationContext());

        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_IMMERSIVE;

        // This work only for android 4.4+
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().getDecorView().setSystemUiVisibility(flags);

            // Code below is to handle presses of Volume up or Volume down.
            // Without this, after pressing volume buttons, the navigation bar will
            // show up and won't hide
            final View decorView = getWindow().getDecorView();
            decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                @Override
                public void onSystemUiVisibilityChange(int visibility) {
                    if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                        decorView.setSystemUiVisibility(flags);
                    }
                }
            });
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                        decorView.setSystemUiVisibility(flags);
                    }
                });
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    @OnClick(R.id.buttonclean)
    void clean() {
        textViewLog.setText("");
    }

    @OnClick(R.id.buttonhttpclient)
    void buttonhttpclient() {
    }

    @OnClick(R.id.buttonhttpsclient)
    void buttonhttpsclient() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                httpClientManager.httpsBothClientGet(Contast.URL_ALI, new ResponseCallBack() {
                    @Override
                    public void callBack(String response) {
                        sendMessage(response);
                    }
                });
            }
        }).start();

    }

    @OnClick(R.id.buttonhttpscustom)
    void httpscustom() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new CustomTrust().run(new ResponseCallBack() {
                    @Override
                    public void callBack(String response) {
                        sendMessage(response);
                    }
                });
            }
        }).start();
    }

    @OnClick(R.id.buttonhttpsclientone)
    void buttonhttpsclientone() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                httpClientManager.httpsSingleClientGet(Contast.URL_ALI, new ResponseCallBack() {
                    @Override
                    public void callBack(String response) {
                        sendMessage(response);
                    }
                });
            }
        }).start();
    }

    @OnClick(R.id.buttonokhttp)
    void buttonokhttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = okHttpManager.runHttp(Contast.URL_ALI);
                sendMessage(result);
            }
        }).start();

    }

    @OnClick(R.id.buttonokhttps)
    void buttonokhttps() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = okHttpManager.runHttpBothWay(Contast.URL_ALI);
                sendMessage(result);
            }
        }).start();
    }

    @OnClick(R.id.buttonokhttpsone)
    void buttonokhttpsone() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = okHttpManager.runHttpOneWay(Contast.URL_ALI);
                sendMessage(result);
            }
        }).start();
    }

    @OnClick(R.id.buttonhttpsurl)
    void buttonhttpsurl() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                httpUrlConnectManager.get(Contast.URL_BING, new ResponseCallBack() {
                    @Override
                    public void callBack(String response) {
                        sendMessage(response);
                    }
                });
            }
        }).start();
    }

    @OnClick(R.id.buttonhttpurl)
    void buttonhttpurl() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                httpUrlConnectManager.httpsSingleGet(Contast.URL_ALI, new ResponseCallBack() {
                    @Override
                    public void callBack(String response) {
                        sendMessage(response);
                    }
                });
            }
        }).start();

    }

    @OnClick(R.id.buttonhttpsurlone)
    void buttonhttpsurlone() {

    }

    @OnClick(R.id.buttonvolley)
    void volley() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                volleyManager.requestGet(Contast.URL_GOOGLE);
            }
        }).start();
    }

    @OnClick(R.id.buttonhttpsvolley)
    void buttonhttpsvolley() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                volleyManager.requestHttpsGet(Contast.URL_GOOGLE);
            }
        }).start();
    }

    private void sendMessage(String msg) {
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("msg", msg);
        message.setData(bundle);
        netHandle.sendMessage(message);
    }

    public static void startAppByPackageName(Context context, String packageName) {
        PackageInfo pi = null;
        try {
            pi = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return;
        }

        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(pi.packageName);

        List<ResolveInfo> apps = context.getPackageManager().queryIntentActivities(resolveIntent, 0);

        ResolveInfo ri = apps.iterator().next();
        if (ri != null) {
            String packageName1 = ri.activityInfo.packageName;
            String className = ri.activityInfo.name;

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            ComponentName cn = new ComponentName(packageName1, className);

            intent.setComponent(cn);
            context.startActivity(intent);
        }
    }


    @Override
    public boolean onNavigateUp() {
        return super.onNavigateUp();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                || keyCode == KeyEvent.KEYCODE_HOME
                || keyCode == KeyEvent.KEYCODE_APP_SWITCH
                )
            return true;
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                || keyCode == KeyEvent.KEYCODE_HOME
                || keyCode == KeyEvent.KEYCODE_MENU)
            return true;
        return super.onKeyUp(keyCode, event);
    }
}
