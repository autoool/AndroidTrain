package com.example.material.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sam.zhang on 2017/8/18.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN| View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }
//
//    public static void addStatusBarBehind(Activity activity, int color, int statusBarAlpha) {
//        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
//        int       count     = decorView.getChildCount();
//        if (count > 0 && decorView.getChildAt(count - 1) instanceof StatusBarView) {
//            decorView.getChildAt(count - 1).setBackgroundColor(calculateStatusColor(color, statusBarAlpha));
//        } else {
//            StatusBarView statusView = createStatusBarView(activity, color, statusBarAlpha);
//            decorView.addView(statusView);
//        }
//        setRootView(activity);
//    }
}
