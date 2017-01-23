package com.techidea.demand.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.techidea.commonlibrary.adapter.BaseRecyclerAdapter;
import com.techidea.commonlibrary.adapter.BaseRecyclerHolder;
import com.techidea.demand.R;
import com.techidea.demand.entity.UploadImage;
import com.techidea.demand.util.DeviceUtils;

import java.util.Collection;

/**
 * Created by Administrator on 2016/11/25.
 */

public class ImagesAdapter extends BaseRecyclerAdapter<UploadImage> {

    public ImagesAdapter(RecyclerView v, Collection<UploadImage> datas, int itemLayoutId) {
        super(v, datas, itemLayoutId);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, UploadImage item, int position, boolean isScrolling) {
        if (item.getTag() == -1) {
            holder.setImageResource(R.id.imageview_item, R.mipmap.ic_launcher);
        } else if (item.getTag() == 1) {
            holder.setImageResource(R.id.imageview_item, item.getResId());
        } else {
            Glide.with(context).load(item.getFilePath()).into((ImageView) holder.getView(R.id.imageview_item));
        }
        View imageView = holder.getView(R.id.imageview_item);
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) imageView
                .getLayoutParams(); // 取控件mGrid当前的布局参数
        linearParams.height = (DeviceUtils.getScreen(context)[1] - DeviceUtils.dip2px(context, 50)) / 4;// 当控件的高强制设成height
        imageView.setLayoutParams(linearParams);
    }

    @Override
    public BaseRecyclerAdapter<UploadImage> refresh(Collection<UploadImage> datas) {
        return super.refresh(datas);
    }
}
