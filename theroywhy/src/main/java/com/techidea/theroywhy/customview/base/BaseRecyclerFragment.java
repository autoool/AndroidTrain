package com.techidea.theroywhy.customview.base;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.techidea.theroywhy.R;
import com.techidea.theroywhy.customview.refreshloadmore.BaseRecyclerAdapter;

import java.util.List;

/**
 * Created by zchao on 2017/3/6.
 */

public abstract class BaseRecyclerFragment<Presenter extends BaseListPresenter, Model>
        extends BaseFragment
        implements BaseListView<Presenter, Model>,
        BaseRecyclerAdapter.OnItemClickListener,
        RecyclerRefreshLayout.SuperRefreshLayoutListener
       {

    protected RecyclerRefreshLayout recyclerRefreshLayout;
    protected RecyclerView recyclerView;
    protected BaseRecyclerAdapter<Model> adapter;
    protected Presenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_recycler;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        recyclerRefreshLayout = (RecyclerRefreshLayout) root.findViewById(R.id.refreshLayout);
        recyclerRefreshLayout.setSuperRefreshLayoutListener(this);
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerview);
        adapter = getAdapter();
        recyclerView.setLayoutManager(getLayoutManager());
        adapter.setOnItemClickListener(this);
        recyclerRefreshLayout.setColorSchemeResources(
                R.color.swiperefresh_color1, R.color.swiperefresh_color2,
                R.color.swiperefresh_color3, R.color.swiperefresh_color4);

    }

    @Override
    protected void initData() {
        super.initData();
        recyclerRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                recyclerRefreshLayout.setRefreshing(true);
                presenter.onRefreshing();
            }
        });
    }

    @Override
    public void onItemClick(int position, long itetmId) {
        Model model = adapter.getItem(position);
        if (model != null)
            onItemClick(model, position);
    }

    @Override
    public void onRefreshing() {
        presenter.onRefreshing();
    }

    @Override
    public void onLoadMore() {
        presenter.onLoadMore();
        adapter.setState(BaseRecyclerAdapter.STATE_LOADING, true);
    }

    @Override
    public void onRefreshSuccess(List<Model> data) {
        adapter.resetItem(data);
    }

    @Override
    public void onLoadMoreSuccess(List<Model> data) {
        adapter.addAll(data);
    }

    @Override
    public void showMoreMore() {
        adapter.setState(BaseRecyclerAdapter.STATE_NO_MORE, true);
    }

    @Override
    public void showNetworkError(int strId) {
        adapter.setState(BaseRecyclerAdapter.STATE_INVALID_NETWORK, true);
    }

    @Override
    public void onComplete() {
        recyclerRefreshLayout.onComplete();
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(context);
    }


    protected abstract BaseRecyclerAdapter<Model> getAdapter();

    protected abstract void onItemClick(Model model, int position);


}
