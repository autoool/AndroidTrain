package com.techidea.theroywhy.customview.base;

import java.util.List;

/**
 * Created by zchao on 2017/3/6.
 */

public interface BaseListView<Presenter extends BaseListPresenter, M>
        extends BaseView<Presenter> {
    /**
     * 刷新成功
     */
    void onRefreshSuccess(List<M> data);

    /**
     * 加载成功
     */
    void onLoadMoreSuccess(List<M> data);

    /**
     * 没有更多数据
     */
    void showMoreMore();

    /**
     * 加载完成
     */
    void onComplete();

}
