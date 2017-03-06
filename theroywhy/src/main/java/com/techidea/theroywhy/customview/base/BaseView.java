package com.techidea.theroywhy.customview.base;

/**
 * Created by zchao on 2017/3/6.
 */

public interface BaseView<Presenter extends BasePresenter> {
    void setPresenter(Presenter presenter);

    void showNetworkError(int strId);
}
