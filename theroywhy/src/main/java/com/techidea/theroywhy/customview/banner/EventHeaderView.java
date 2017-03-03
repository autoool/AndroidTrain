package com.techidea.theroywhy.customview.banner;

import android.content.Context;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.techidea.theroywhy.R;

/**
 * Created by zchao on 2017/3/3.
 */

public class EventHeaderView extends HeaderView {

    public EventHeaderView(Context context, RequestManager requestManager) {
        super(context, requestManager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_event_header_view;
    }

    @Override
    protected Object instantiateItem(ViewGroup container, int position) {
        ViewEventBanner view = new ViewEventBanner(getContext());
        container.addView(view);
        if (bannersList.size() != 0) {
            int p = position % bannersList.size();
            if (p > 0 && p < bannersList.size())
                view.initData(requestManager, bannersList.get(p));
        }
        return view;
    }

    @Override
    protected void destoryItem(ViewGroup container, int position, Object object) {
        if (object instanceof ViewEventBanner)
            container.removeView((ViewEventBanner) object);
    }
}
