package com.techidea.theroywhy.customview.base;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.techidea.theroywhy.R;

/**
 * 下拉刷新上拉加载控件，目前适用于RecyclerView
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
        if (getChildCount() > 0) {
            View childView = getChildAt(0);
            if (!(childView instanceof RecyclerView)) {
                childView = findViewById(R.id.recyclerview);
            }
            if (childView != null && childView instanceof RecyclerView) {
                recyclerView = (RecyclerView) childView;
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        if (canLoad() && canLoadMore) {
                            loadData();
                        }
                    }
                });
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                yDown = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                lastY = (int) ev.getRawY();
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 是否可以加载更多, 条件是到了最底部
     *
     * @return isCanLoad
     */
    private boolean canLoad() {
        return isScrollBottom() && !isOnLoading && isPullUp() && hasMore;
    }

    /**
     * 如果到了最底部,而且是上拉操作.那么执行onLoad方法
     */
    private void loadData() {
        if (listener != null) {
            setOnLoading(true);
            listener.onLoadMore();
        }
    }

    /**
     * 是否是上拉操作
     *
     * @return isPullUp
     */
    private boolean isPullUp() {
        return (yDown - lastY) >= touchSlop;
    }

    public void setOnLoading(boolean loading) {
        isOnLoading = loading;
        if (!isOnLoading) {
            yDown = 0;
            lastY = 0;
        }
    }

    /**
     * 判断是否到了最底部
     *
     * @return
     */
    private boolean isScrollBottom() {
        return (recyclerView != null && recyclerView.getAdapter() != null)
                && getLastVisiblePosition() == (recyclerView.getAdapter().getItemCount() - 1);
    }

    /**
     * 加载结束记得调用
     */
    public void onComplete() {
        setOnLoading(false);
        setRefreshing(false);
        hasMore = true;
    }

    public void setCanLoadMore(boolean canLoadMore) {
        this.canLoadMore = canLoadMore;
    }

    public int getLastVisiblePosition() {
        int position;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
        } else if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            position = ((GridLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
        } else if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
            int[] lastPositions = layoutManager.findLastVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            position = getMaxPosition(lastPositions);
        } else {
            position = recyclerView.getLayoutManager().getItemCount() - 1;
        }
        return position;
    }

    public void setSuperRefreshLayoutListener(SuperRefreshLayoutListener listener) {
        this.listener = listener;
    }

    /**
     * 获得最大的位置
     *
     * @param positions 获得最大的位置
     * @return 获得最大的位置
     */
    private int getMaxPosition(int[] positions) {
        int maxPosition = Integer.MIN_VALUE;
        for (int position : positions) {
            maxPosition = Math.max(maxPosition, position);
        }
        return maxPosition;
    }

    public interface SuperRefreshLayoutListener {
        void onRefreshing();

        void onLoadMore();
    }
}
