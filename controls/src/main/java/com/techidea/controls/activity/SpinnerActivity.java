package com.techidea.controls.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.Menu;

import com.techidea.controls.R;
import com.techidea.controls.adapter.MySpinnerAdapter;
import com.techidea.controls.adapter.SpinnerItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/9.
 */
public class SpinnerActivity extends AppCompatActivity {

    @Bind(R.id.spinner1)
    AppCompatSpinner mAppCompatSpinner1;
    @Bind(R.id.spinner2)
    AppCompatSpinner mAppCompatSpinner2;

    private List<SpinnerItem> mSpinnerItems;
    private MySpinnerAdapter mMySpinnerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiyt_spinner);
        ButterKnife.bind(this);
        initialize();
        mAppCompatSpinner1.setDropDownVerticalOffset(60);
        mAppCompatSpinner2.setDropDownVerticalOffset(60);
        mMySpinnerAdapter = new MySpinnerAdapter(this, mSpinnerItems);
        mAppCompatSpinner2.setAdapter(mMySpinnerAdapter);
    }

    private void initialize() {
        mSpinnerItems = new ArrayList<>();
        mSpinnerItems.add(new SpinnerItem("1", "111111", "111"));
        mSpinnerItems.add(new SpinnerItem("1", "111111", "111"));
        mSpinnerItems.add(new SpinnerItem("1", "111111", "111"));
        mSpinnerItems.add(new SpinnerItem("1", "111111", "111"));
        mSpinnerItems.add(new SpinnerItem("1", "111111", "111"));
        mSpinnerItems.add(new SpinnerItem("1", "111111", "111"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_spinner, menu);
        return true;
    }
}
