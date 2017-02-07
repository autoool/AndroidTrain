package com.techidea.theroywhy.launchmode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.techidea.theroywhy.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zchao on 2017/2/7.
 */

public class SecondActivity extends AppCompatActivity {

    @Bind(R.id.textview_info)
    TextView textViewInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launchmode);
        ButterKnife.bind(this);
        textViewInfo.setText(this.toString() + " " + this.getTaskId());
    }

    @OnClick(R.id.button_jump)
    void jump() {
        startActivity(new Intent(this, FirstActivity.class));
    }
}
