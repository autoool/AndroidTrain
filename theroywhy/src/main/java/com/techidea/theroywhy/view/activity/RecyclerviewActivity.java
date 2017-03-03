package com.techidea.theroywhy.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.techidea.theroywhy.R;
import com.techidea.theroywhy.view.adapter.HeaderBottomAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zchao on 2017/2/28.
 */

public class RecyclerviewActivity extends AppCompatActivity {

    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;

    private HeaderBottomAdapter headerBottomAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        headerBottomAdapter = new HeaderBottomAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(headerBottomAdapter);
    }


}
