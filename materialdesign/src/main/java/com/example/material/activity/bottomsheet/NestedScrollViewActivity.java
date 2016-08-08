package com.example.material.activity.bottomsheet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.material.R;
import com.example.material.adapter.ImageItem;
import com.example.material.adapter.ImageItemAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/10.
 */
public class NestedScrollViewActivity extends AppCompatActivity {

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout mCoordinatorLayout;
    @Bind(R.id.swiperefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @Bind(R.id.bottom_sheet)
    View bottomSheet;
    @Bind(R.id.textview_bottom_sheet)
    TextView mTextView;

    private BottomSheetBehavior mBehavior;
    private boolean isSetBottomSheetHeight;
    private ImageItemAdapter mImageItemAdapter;

    private List<ImageItem> mImageItemList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nestedscroll);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {

        mImageItemList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            mImageItemList.add(new ImageItem("", "",0));
        }
        mImageItemAdapter = new ImageItemAdapter(mRecyclerView, mImageItemList, R.layout.view_imageitem);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(mImageItemAdapter);

        mBehavior = BottomSheetBehavior.from(bottomSheet);
        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                //这里是bottomSheet 状态的改变，根据slideOffset可以做一些动画
                Log.i("cjj", "newState--->" + newState);
//                ViewCompat.setScaleX(bottomSheet,1);
//                ViewCompat.setScaleY(bottomSheet,1);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //这里是拖拽中的回调，根据slideOffset可以做一些动画
                Log.i("cjj", "slideOffset=====》" + slideOffset);
//                ViewCompat.setScaleX(bottomSheet,slideOffset);
//                ViewCompat.setScaleY(bottomSheet,slideOffset);
            }
        });

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
    }
}
