package com.example.coordinator.activity;

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
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.coordinator.R;
import com.example.coordinator.adapter.ImageItem;
import com.example.coordinator.adapter.ImageItemAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/10.
 */
public class BootomSheetActivity extends AppCompatActivity {

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout mCoordinatorLayout;
    @Bind(R.id.swiperefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.design_bottom_sheet_bar)
    RelativeLayout bottomSheetBar;
    @Bind(R.id.design_bottom_sheet)
    RelativeLayout designBottomSheet;
    @Bind(R.id.textview_bottom_sheet)
    TextView mTextView;
    @Bind(R.id.imageview_bottom_sheet)
    ImageView mImageView;
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private BottomSheetBehavior mBehavior;
    private boolean isSetBottomSheetHeight;
    private ImageItemAdapter mImageItemAdapter;

    private List<ImageItem> mImageItemList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottomsheet);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mImageItemList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            mImageItemList.add(new ImageItem("", ""));
        }
        mImageItemAdapter = new ImageItemAdapter(mRecyclerView, mImageItemList, R.layout.view_imageitem);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(mImageItemAdapter);

        mBehavior = BottomSheetBehavior.from(designBottomSheet);
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState != BottomSheetBehavior.STATE_COLLAPSED && mTextView.getVisibility() == View.VISIBLE) {
                    mTextView.setVisibility(View.GONE);
                    mImageView.setVisibility(View.VISIBLE);
                    mImageItemAdapter.setOnItemClickListener(null);
                } else if (newState == BottomSheetBehavior.STATE_COLLAPSED && mTextView.getVisibility() == View.GONE) {
                    mTextView.setVisibility(View.VISIBLE);
                    mImageView.setVisibility(View.GONE);
                    mImageItemAdapter.setOnItemClickListener(null);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                if (bottomSheet.getTop() < 2 * bottomSheetBar.getHeight()) {
                    bottomSheetBar.setVisibility(View.VISIBLE);
                    bottomSheetBar.setAlpha(slideOffset);
                    bottomSheetBar.setTranslationY(bottomSheet.getTop() - 2 * bottomSheetBar.getHeight());
                } else {
                    bottomSheetBar.setVisibility(View.INVISIBLE);
                }
            }
        });

        bottomSheetBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!isSetBottomSheetHeight) {
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) designBottomSheet.getLayoutParams();
            params.height = mCoordinatorLayout.getHeight() - designBottomSheet.getHeight();
            designBottomSheet.setLayoutParams(params);
            isSetBottomSheetHeight = true;
        }
    }
}
