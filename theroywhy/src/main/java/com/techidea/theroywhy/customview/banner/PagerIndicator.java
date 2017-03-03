package com.techidea.theroywhy.customview.banner;

import android.support.v4.view.ViewPager;

/**
 * Created by zchao on 2017/3/3.
 */

public interface PagerIndicator extends ViewPager.OnPageChangeListener {
    /**
     * bind the viewPager into indicator
     *
     * @param viewPager the ViewPager
     */
    void bindViewPager(ViewPager viewPager);


    /**
     * bind the viewPager into indicator
     *
     * @param viewPager       the ViewPager
     * @param initialPosition initialPosition
     */
    void bindViewPager(ViewPager viewPager, int initialPosition);


    /**
     * the ViewPager Current Item
     *
     * @param currentItem currentItem
     */
    void setCurrentItem(int currentItem);

    /**
     * the ViewPager ChangeListener
     *
     * @param listener listener
     */
    void setOnPageChangeListener(ViewPager.OnPageChangeListener listener);

    /**
     * update the DataSet,invalidate
     */
    void notifyDataSetChanged();

}
