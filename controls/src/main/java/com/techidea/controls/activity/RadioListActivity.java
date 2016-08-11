package com.techidea.controls.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.techidea.commonlibrary.adapter.BaseRecyclerAdapter;
import com.techidea.commonlibrary.adapter.DividerListItemDecoration;
import com.techidea.controls.R;
import com.techidea.controls.adapter.RadioItem;
import com.techidea.controls.adapter.RadioListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/11.
 */
public class RadioListActivity extends AppCompatActivity {

    @Bind(R.id.coordinatorlayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.recyclerview_radio)
    RecyclerView mRecyclerView;

    private List<RadioItem> mRadioItems;
    private RadioListAdapter mRadioListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radiolist);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        mRadioItems = new ArrayList<>();
        mRadioItems.add(new RadioItem(0, "Apple", true));
        mRadioItems.add(new RadioItem(1, "HUAWEI", false));
        mRadioItems.add(new RadioItem(2, "MI 小米", false));
        mRadioItems.add(new RadioItem(3, "MEIZU", false));
        mRadioItems.add(new RadioItem(4, "ZTE 中兴", false));
        mRadioItems.add(new RadioItem(5, "SAMSUNG", false));
        mRadioItems.add(new RadioItem(6, "SONY", false));
        mRadioListAdapter = new RadioListAdapter(mRecyclerView, mRadioItems, R.layout.view_radiolist_item);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerListItemDecoration(this, DividerListItemDecoration.VERTICAL_LIST));
        mRecyclerView.setAdapter(mRadioListAdapter);
        mRadioListAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object data, int position) {
                mRadioListAdapter.changeItemChecked(position);
                RadioItem radioItem = (RadioItem) data;
                showMessage("选择:" + radioItem.getContent());

            }
        });
    }

    private void showMessage(String msg) {
        Snackbar.make(coordinatorLayout, msg, Snackbar.LENGTH_SHORT).show();
    }
}
