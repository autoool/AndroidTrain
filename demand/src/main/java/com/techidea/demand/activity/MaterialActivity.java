package com.techidea.demand.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.techidea.demand.R;

import butterknife.ButterKnife;

/**
 * Created by zchao on 2016/9/8.
 */
public class MaterialActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);
    }

    @Override
    public void setToolBar() {
    }

    @Override
    public void init() {

    }
}
