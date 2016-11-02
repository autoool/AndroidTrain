package com.techidea.theroywhy.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.techidea.theroywhy.R;

/**
 * Created by zchao on 2016/10/8.
 */
public class CustomVolumControlBar extends View {

    private int firstColor;
    private int secondColor;
    private int circleWidth;
    private Paint paint;
    private int currentCount;
    private Bitmap bitmapCenter;
    private int splitSize;
    private int count;
    private Rect rect;

    public CustomVolumControlBar(Context context) {
        this(context, null);
    }

    public CustomVolumControlBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomVolumControlBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomVolumControlBar, defStyleAttr, 0);
        int n = typedArray.getIndexCount();

    }
}
