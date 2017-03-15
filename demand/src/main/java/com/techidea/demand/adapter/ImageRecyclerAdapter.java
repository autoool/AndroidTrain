package com.techidea.demand.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.techidea.commonlibrary.adapter.BaseRecyclerAdapter;
import com.techidea.commonlibrary.adapter.BaseRecyclerHolder;
import com.techidea.demand.R;
import com.techidea.demand.entity.UploadImage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by zchao on 2017/3/14.
 */

public class ImageRecyclerAdapter extends BaseRecyclerAdapter<UploadImage> {

    private List<UploadImage> imageList;
    private Context context;

    public ImageRecyclerAdapter(RecyclerView v, Collection<UploadImage> datas, int itemLayoutId) {
        super(v, datas, itemLayoutId);
        this.context = v.getContext();
        this.imageList = new ArrayList<>(datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, UploadImage item, int position, boolean isScrolling) {
        ImageView imageView = (ImageView) holder.getView(R.id.imageview_item);
        Glide.with(this.context)
                .load(item.getUrl())
                .placeholder(R.mipmap.facebook_blue)
                .error(R.mipmap.facebook_blue)
                .into(imageView);
    }

    public List<UploadImage> getImageList() {
        return imageList;
    }
}
