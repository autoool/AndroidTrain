package com.example.coordinator.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.coordinator.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_scroll)
    void buttonScroll(){
        startActivity(new Intent(this,ScrollingActivity.class));
    }

    @OnClick(R.id.buttton_toolbar)
    void toolbar(){
        startActivity(new Intent(this,ToolbarActivity.class));
    }

    @OnClick(R.id.buttton_bottom_sheet)
    void BottomSheet(){
        startActivity(new Intent(this,BootomSheetActivity.class));
    }

}
