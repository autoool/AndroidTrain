package com.example.material.activity.bottomsheet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.material.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zchao on 2016/7/14.
 */
public class SheetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_scroll)
    void buttonScroll() {
        startActivity(new Intent(this, ScrollingActivity.class));
    }

    @OnClick(R.id.buttton_toolbar)
    void toolbar() {
        startActivity(new Intent(this, ToolbarActivity.class));
    }

    @OnClick(R.id.buttton_bottom_sheet)
    void BottomSheet() {
        startActivity(new Intent(this, NestedScrollViewActivity.class));
    }

    @OnClick(R.id.buttton_recyclerview)
    void BottomRecyclerview() {
        startActivity(new Intent(this, SheetRecyclerviewActivity.class));
    }

    @OnClick(R.id.buttton_bottom_sheet_dialog)
    void bottomsheetDialog() {
        startActivity(new Intent(this, SheetDialogActivity.class));
    }
}
