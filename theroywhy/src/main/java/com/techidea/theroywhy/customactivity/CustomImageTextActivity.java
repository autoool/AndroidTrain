package com.techidea.theroywhy.customactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.techidea.theroywhy.R;

/**
 * Created by zchao on 2016/9/30.
 */
public class CustomImageTextActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_imagetext);
    }
}
