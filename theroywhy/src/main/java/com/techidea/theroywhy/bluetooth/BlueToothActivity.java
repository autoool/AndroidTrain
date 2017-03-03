package com.techidea.theroywhy.bluetooth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.techidea.theroywhy.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zchao on 2017/2/13.
 */

public class BlueToothActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_1)
    void button1Click() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("1", "1");
        bundle.putString("2", "2");
        bundle.putString("3", "3");
        bundle.putString("4", "4");
        bundle.putString("5", "5");
        intent.putExtras(bundle);
        intent.setClass(this, BlueToothActivityy.class);
        startActivity(intent);
    }
}
