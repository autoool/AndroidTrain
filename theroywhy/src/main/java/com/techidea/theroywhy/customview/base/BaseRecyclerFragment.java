package com.techidea.theroywhy.customview.base;

import com.techidea.theroywhy.customview.refreshloadmore.BaseRecyclerAdapter;

/**
 * Created by zchao on 2017/3/6.
 */

public abstract class BaseRecyclerFragment<Presenter extends BaseListPresenter, Model>
        extends BaseFragment
        implements BaseListView<Presenter, Model>,
        BaseRecyclerAdapter.OnItemClickListener,
        RecyclerRefreshLayout.SuperRefreshLayoutListener {
}
