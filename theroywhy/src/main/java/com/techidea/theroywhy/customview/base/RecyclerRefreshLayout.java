package com.techidea.theroywhy.customview.base;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Created by zchao on 2017/3/6.
 */

public class RecyclerRefreshLayout extends SwipeRefreshLayout
        implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private int touchSlop;
    private SuperRefreshLayoutListener listener;
    private boolean isOnLoading = false;
    private boolean canLoadMore = true;
    private boolean hasMore = true;
    private int yDown;
    private int lastY;

    public RecyclerRefreshLayout(Context context) {
        this(context, null);
    }

    public RecyclerRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        if (listener != null && !isOnLoading) {
            listener.onRefreshing();
        } else
            setRefreshing(false);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (recyclerView == null)
            getRecycleView();
    }

    private void getRecycleView() {
        if (getChildCount()>0){
            View childView = getChildAt(0);
            if (!(childView instanceof RecyclerView)){

            }
        }
    }

    public interface SuperRefreshLayoutListener {
        void onRefreshing();

        void onLoadMore();
    }
}
