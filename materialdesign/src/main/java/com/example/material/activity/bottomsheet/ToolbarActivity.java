package com.example.material.activity.bottomsheet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.material.R;
import com.example.material.activity.BaseActivity;
import com.example.material.adapter.ImageItem;
import com.example.material.adapter.ImageItemAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/9.
 */
public class ToolbarActivity extends BaseActivity {

    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private List<ImageItem> mImageItemList;
    private ImageItemAdapter mImageItemAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        toolbar.setTitle("测试沉浸状态栏效果");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        mImageItemList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            mImageItemList.add(new ImageItem("", "", 0));
        }
        mImageItemAdapter = new ImageItemAdapter(mRecyclerView, mImageItemList, R.layout.view_imageitem);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(mImageItemAdapter);
    }
}
