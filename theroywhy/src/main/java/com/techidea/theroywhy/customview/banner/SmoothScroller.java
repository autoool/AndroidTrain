package com.techidea.theroywhy.customview.banner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * Created by zchao on 2017/3/3.
 */

public class SmoothScroller extends Scroller {

    private int mDuration = 1200;

    public SmoothScroller(Context context) {
        super(context);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    void bindViewPager(ViewPager viewPager) {
        try {
            Field scroller;
            scroller = ViewPager.class.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            scroller.set(viewPager, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
