package com.techidea.theroywhy.customview.banner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.RequestManager;
import com.techidea.theroywhy.R;

/**
 * Created by zchao on 2017/3/3.
 */

public class ViewEventBanner extends RelativeLayout implements View.OnClickListener {

    private Banner banner;
    private ImageView imageView;

    public ViewEventBanner(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_event_banner, this, true);
        imageView = (ImageView) findViewById(R.id.imageview_event);
        setOnClickListener(this);
    }

    public void initData(RequestManager manager, Banner banner) {
        this.banner = banner;
        manager.load(banner.getImg())
                .placeholder(R.mipmap.event_cover_default)
                .error(R.mipmap.event_cover_default)
                .into(imageView);
    }

    @Override
    public void onClick(View v) {
        if (banner != null) {
            // TODO: 2017/3/3 跳转到不同也页面
        }
    }
}
