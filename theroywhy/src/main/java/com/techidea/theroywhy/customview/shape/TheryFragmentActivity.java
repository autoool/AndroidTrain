package com.techidea.theroywhy.customview.shape;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.techidea.theroywhy.R;

import java.util.List;

/**
 * Created by zchao on 2017/3/15.
 */

public abstract class TheryFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentcontainer);
        if (null == fragment) {
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragmentcontainer, fragment)
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!isAppOnForeground()) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isAppOnForeground()) {

        }
    }

    public boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcessInfos
                = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcessInfos) {
            if (appProcess.processName.equals(getApplicationContext().getPackageName())) {
                return true;
            }
        }
        return false;
    }
}
