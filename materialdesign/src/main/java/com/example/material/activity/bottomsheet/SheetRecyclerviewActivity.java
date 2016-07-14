package com.example.material.activity.bottomsheet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.material.R;
import com.example.material.adapter.ImageItem;
import com.example.material.adapter.MenuSheetAdapter;
import com.techidea.commonlibrary.adapter.BaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zchao on 2016/7/14.
 */
public class SheetRecyclerviewActivity extends AppCompatActivity {

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout mCoordinatorLayout;
    @Bind(R.id.textview_bottom_sheet)
    TextView mTextViewBottomSheet;
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @Bind(R.id.view_black)
    View mViewBlack;

    private List<ImageItem> mImageItems;
    private MenuSheetAdapter mMenuSheetAdapter;
    private BottomSheetBehavior mBehavior;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_recyclerview);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initialize();
    }

    private void initialize() {
        mImageItems = new ArrayList<>();
        mImageItems.add(new ImageItem("", "", R.drawable.weixin));
        mImageItems.add(new ImageItem("", "", R.drawable.douban));
        mImageItems.add(new ImageItem("", "", R.drawable.facebook));
        mImageItems.add(new ImageItem("", "", R.drawable.google));
        mImageItems.add(new ImageItem("", "", R.drawable.hangouts));
        mImageItems.add(new ImageItem("", "", R.drawable.mail));
        mImageItems.add(new ImageItem("", "", R.drawable.phone));
        mImageItems.add(new ImageItem("", "", R.drawable.qq));
        mImageItems.add(new ImageItem("", "", R.drawable.message));
        mMenuSheetAdapter = new MenuSheetAdapter(mRecyclerView, mImageItems, R.layout.view_menusheet);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mMenuSheetAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mBehavior = BottomSheetBehavior.from(mRecyclerView);
        mMenuSheetAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object data, int position) {
                mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED ||
                        newState == BottomSheetBehavior.STATE_HIDDEN) {
                    mViewBlack.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                mViewBlack.setVisibility(View.VISIBLE);
                ViewCompat.setAlpha(mViewBlack, slideOffset);
            }
        });

        mTextViewBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        mViewBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });


    }
}
