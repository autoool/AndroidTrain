package com.techidea.theroywhy.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.techidea.theroywhy.R;

/**
 * Created by zchao on 2016/9/30.
 */
public class CustomImageText extends View {

    private String text;
    private int textColor;
    private int textSize;
    private Bitmap bitmap;
    private int scaleType;


    private Rect rect;
    private Rect rect2;
    private Paint paint;
    private int width;
    private int height;

    public CustomImageText(Context context) {
        super(context);
    }

    public CustomImageText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.CustomImageText, defStyleAttr, 0);
        int n = typedArray.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.CustomImageText_src:
                    bitmap = BitmapFactory.decodeResource(getResources(), typedArray.getResourceId(attr, 0));
                    break;
                case R.styleable.CustomImageText_text:
                    text = typedArray.getString(attr);
                    break;
                case R.styleable.CustomImageText_textColor:
                    textColor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CustomImageText_ScaleType:
                    scaleType = typedArray.getInt(attr, 0);
                    break;
                case R.styleable.CustomImageText_textSize:
                    textSize = typedArray.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                    16, getResources().getDisplayMetrics()));
                    break;
            }
        }
        typedArray.recycle();
        rect = new Rect();
        rect2 = new Rect();
        paint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            width = specSize;
        } else {
            int desireImg = getPaddingLeft() + getPaddingRight() + bitmap.getWidth();
            int desireText = getPaddingLeft() + getPaddingRight() + rect.width();
            if (specMode == MeasureSpec.AT_MOST) {
                int desire = Math.max(desireImg, desireText);
                width = Math.min(desire, specSize);
            }
        }

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            height = specSize;
        } else {
            int desire = getPaddingTop() + getPaddingBottom() + rect.height() + bitmap.getHeight();
            if (specMode == MeasureSpec.AT_MOST) {
                height = Math.min(specSize, desire);
            }
        }
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint);

//        rect.left =
    }
}
