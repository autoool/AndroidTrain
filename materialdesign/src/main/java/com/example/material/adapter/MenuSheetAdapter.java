package com.example.material.adapter;

import android.support.v7.widget.RecyclerView;

import com.example.material.R;
import com.techidea.commonlibrary.adapter.BaseRecyclerAdapter;
import com.techidea.commonlibrary.adapter.BaseRecyclerHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by zchao on 2016/7/14.
 */
public class MenuSheetAdapter extends BaseRecyclerAdapter<ImageItem> {

    private List<ImageItem> mDatas;

    public MenuSheetAdapter(RecyclerView v, Collection<ImageItem> datas, int itemLayoutId) {
        super(v, datas, itemLayoutId);
        mDatas = new ArrayList<>(datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, ImageItem item, int position, boolean isScrolling) {
        holder.setImageResource(R.id.imageview_item, item.getId());
    }
}
