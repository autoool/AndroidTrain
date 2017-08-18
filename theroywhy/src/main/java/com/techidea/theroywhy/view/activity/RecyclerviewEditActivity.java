package com.techidea.theroywhy.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.techidea.theroywhy.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sam.zhang on 2017/8/10.
 */

public class RecyclerviewEditActivity extends AppCompatActivity {

    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_edit);
        ButterKnife.bind(this);
    }
}
