package com.techidea.theroywhy.customview.base;

/**
 * Created by zchao on 2017/3/6.
 */

public interface BaseListPresenter extends BasePresenter {
    void onRefreshing();

    void onLoadMore();
}
