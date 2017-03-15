package com.techidea.demand.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.techidea.demand.R;
import com.techidea.demand.entity.UploadImage;

import java.util.List;

/**
 * Created by zchao on 2017/3/14.
 */

public class ImageListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<UploadImage> imageList;
    private Context context;
    private int layoutId;

    public ImageListAdapter(Context context, List<UploadImage> imageList, int layoutId) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.imageList = imageList;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    public List<UploadImage> getImageList() {
        return imageList;
    }

    @Override
    public Object getItem(int position) {
        return imageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolderItem = null;
        if (convertView == null) {
            viewHolderItem = new ViewHolderItem();
            convertView = inflater.inflate(layoutId, null);
            viewHolderItem.imageView = (ImageView) convertView.findViewById(R.id.imageview_item);
            convertView.setTag(viewHolderItem);
        } else {
            viewHolderItem = (ViewHolderItem) convertView.getTag();
        }
        Glide.with(context)
                .load(imageList.get(position).getUrl())
                .centerCrop()
                .error(R.mipmap.facebook_blue)
                .placeholder(R.mipmap.facebook_blue)
                .crossFade()
                .into(viewHolderItem.imageView);
        return convertView;
    }

    private static class ViewHolderItem {
        private ImageView imageView;
    }
}
