package com.techidea.theroywhy.bluetooth;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.techidea.theroywhy.R;

/**
 * Created by zchao on 2017/2/13.
 */

public class BlueToothActivityy extends AppCompatActivity {

    private final static String TAG = "BlueToothActivityy";

    private Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetoothh);
        bundle = getIntent().getExtras();
        String one = bundle.getString("1", "0");
        Log.d(TAG, one);
    }
}
