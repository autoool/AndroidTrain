package com.techidea.theroywhy.customview.banner;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bumptech.glide.RequestManager;
import com.techidea.theroywhy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zchao on 2017/3/3.
 */

public abstract class HeaderView extends RelativeLayout
        implements ViewPager.OnPageChangeListener, Runnable {

    protected ViewPager viewPager;
    protected CirclePagerIndicator indicator;
    protected List<Banner> bannersList;
    protected BannerAdapter bannerAdapter;
    protected Handler handler;
    protected int currentItem;
    protected RequestManager requestManager;
    protected String url;
    private boolean isScrolling;

    public HeaderView(Context context, RequestManager requestManager) {
        super(context);
        this.requestManager = requestManager;
        init(context);
    }

    protected void init(Context context) {
        bannersList = new ArrayList<>();
        LayoutInflater.from(context).inflate(getLayoutId(), this, true);
        viewPager = (ViewPager) findViewById(R.id.vp_banner);
        indicator = (CirclePagerIndicator) findViewById(R.id.indicator);
        bannerAdapter = new BannerAdapter();
        viewPager.addOnPageChangeListener(this);
        viewPager.setAdapter(bannerAdapter);
        indicator.bindViewPager(viewPager);
        indicator.setmCount(bannersList.size());

        new SmoothScroller(getContext()).bindViewPager(viewPager);
        viewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isScrolling = true;
                    case MotionEvent.ACTION_UP:
                        isScrolling = false;
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        isScrolling = false;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        isScrolling = true;
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void run() {
        handler.postDelayed(this, 5000);
        if (isScrolling)
            return;
        currentItem = currentItem + 1;
        viewPager.setCurrentItem(currentItem);
    }

    public void requestBanner() {
        if (handler == null)
            handler = new Handler();
        handler.removeCallbacks(this);
        // TODO: 2017/3/3 获取

    }

    public void setBanners(List<Banner> banners) {
        if (handler == null)
            handler = new Handler();
        if (banners != null) {
            handler.removeCallbacks(this);
            bannersList.clear();
            bannersList.addAll(banners);
            viewPager.getAdapter().notifyDataSetChanged();
            indicator.setmCount(bannersList.size());
            indicator.notifyDataSetChanged();
            if (currentItem == 0 && bannersList.size() != 1) {
                currentItem = bannersList.size() * 1000;
                viewPager.setCurrentItem(currentItem);
            }
            if (bannersList.size() > 1) {
                handler.postDelayed(this, 5000);
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        isScrolling = currentItem != position;
    }

    @Override
    public void onPageSelected(int position) {
        isScrolling = false;
        currentItem = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        isScrolling = state != ViewPager.SCROLL_STATE_IDLE;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (handler == null)
            handler = new Handler();
        handler.postDelayed(this, 5000);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (handler == null)
            return;
        handler.removeCallbacks(this);
        handler = null;
    }

    protected abstract int getLayoutId();

    protected abstract Object instantiateItem(ViewGroup container, int position);

    protected abstract void destoryItem(ViewGroup container, int position, Object object);

    private class BannerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return bannersList.size() == 1 ? 1 : Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return HeaderView.this.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            HeaderView.this.destoryItem(container, position, object);
        }
    }
}
