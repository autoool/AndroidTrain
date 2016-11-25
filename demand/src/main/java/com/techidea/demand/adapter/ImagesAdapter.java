package com.techidea.demand.adapter;

import android.support.v7.widget.RecyclerView;

import com.techidea.commonlibrary.adapter.BaseRecyclerAdapter;
import com.techidea.commonlibrary.adapter.BaseRecyclerHolder;
import com.techidea.demand.R;
import com.techidea.demand.entity.UploadImages;

import java.util.Collection;

/**
 * Created by Administrator on 2016/11/25.
 */

public class ImagesAdapter extends BaseRecyclerAdapter<UploadImages> {

    public ImagesAdapter(RecyclerView v, Collection<UploadImages> datas, int itemLayoutId) {
        super(v, datas, itemLayoutId);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, UploadImages item, int position, boolean isScrolling) {
        if (item.getTag() == -1) {
            holder.setImageResource(R.id.imageview_item, R.mipmap.ic_launcher);
        } else {
            holder.setImageBitmap(R.id.imageview_item, item.getBitmap());
        }
    }
}
