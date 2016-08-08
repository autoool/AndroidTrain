package com.example.material.adapter;

import android.support.v7.widget.RecyclerView;

import com.example.material.R;
import com.techidea.commonlibrary.adapter.BaseRecyclerAdapter;
import com.techidea.commonlibrary.adapter.BaseRecyclerHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2016/7/10.
 */
public class ImageItemAdapter extends BaseRecyclerAdapter<ImageItem> {

    private List<ImageItem> mImageItemList;

    public ImageItemAdapter(RecyclerView v, Collection<ImageItem> datas, int itemLayoutId) {
        super(v, datas, itemLayoutId);
        this.mImageItemList = new ArrayList<>(datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, ImageItem item, int position, boolean isScrolling) {
        holder.setImageByUrl(R.id.imageview_item, item.getUrl(), R.drawable.ic_launcher);
    }
}
