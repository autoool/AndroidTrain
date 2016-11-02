package com.techidea.theroywhy.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zchao on 2016/10/11.
 */

public class FlowLayout extends ViewGroup {

    private final static String TAG = "FlowLayout";

    private List<List<View>> allViews = new ArrayList<>();
    private List<Integer> lineHeights = new ArrayList<>();

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        Log.e(TAG, sizeWidth + "," + sizeHeight);

        /**
         *如果是wrap_content 情况，记录宽高
         */
        int width = 0;
        int height = 0;
        /**
         * 记录每一行的宽度，width不断取最大值
         */
        int lineWidth = 0;
        /**
         * 每一行的高度 累加至height
         */
        int lineHeight = 0;

        int cCount = getChildCount();

        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            //当前子空间实际占据宽度
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            //如果加入当前child 超出最大宽度，目前最大宽度给width
            // 累加height,然后开始新行
            if (lineWidth + childWidth > sizeWidth) {
                width = Math.max(lineWidth, childWidth);
                lineWidth = childWidth;//重新开启新行
                height += lineHeight;
                lineHeight = childHeight;
            } else {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
            if (i == cCount - 1) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }
        }
        setMeasuredDimension((modeWidth == MeasureSpec.EXACTLY) ? sizeWidth : width,
                (modeHeight == MeasureSpec.EXACTLY) ? sizeHeight : height);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        allViews.clear();
        lineHeights.clear();

        int width = getWidth();

        int lineWidth = getPaddingLeft();
        int lineHeight = getPaddingTop();
        //存储每一行所有的childView

        List<View> lineViews = new ArrayList<>();
        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            //如果需要换行
            if (childWidth + lp.leftMargin + lp.rightMargin + lineWidth > width) {
                //记录这一行的所有view,以及最大高度
                lineHeights.add(lineHeight);
                allViews.add(lineViews);
                lineWidth = 0;//重置行宽
                lineViews = new ArrayList<>();
            }
            //不需要换行，则累加
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.bottomMargin);
            lineViews.add(child);
        }
        // 记录最后一行
        lineHeights.add(lineHeight);
        allViews.add(lineViews);

        int left = getPaddingLeft();
        int top = 0;
        int lineNums = allViews.size();
        for (int i = 0; i < lineNums; i++) {
            lineViews = allViews.get(i);
            lineHeight = lineHeights.get(i);
            Log.i(TAG, "第" + i + "行 ：" + lineViews.size() + " , " + lineViews);
            Log.i(TAG, "第" + i + "行， ：" + lineHeight);

            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                if (child.getVisibility() == View.GONE)
                    continue;
                MarginLayoutParams lp = (MarginLayoutParams) child
                        .getLayoutParams();
                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                Log.i(TAG, child + " , l = " + lc + " , t = " + t + " , r ="
                        + rc + " , b = " + bc);
                child.layout(lc, tc, rc, bc);
                left += child.getMeasuredWidth() + lp.rightMargin + lp.leftMargin;
            }
            left = getPaddingLeft();
            top += lineHeight;
        }

    }
}
