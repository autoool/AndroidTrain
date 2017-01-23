package com.techidea.demand.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.techidea.demand.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zchao on 2016/11/28.
 */

public class ImageDetailActivity extends AppCompatActivity {

    @Bind(R.id.imageview_detail)
    ImageView imageViewDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagedetail);
        ButterKnife.bind(this);
    }
}
