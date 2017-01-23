package com.techidea.demand.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by zchao on 2016/11/29.
 */

public class DeviceUtils {

    /**
     * 获取屏幕高度和宽带
     *
     * @param mContext
     * @return int[高，宽]
     */
    public static int[] getScreen(Context mContext) {
        DisplayMetrics dm = new DisplayMetrics();
        // 取得窗口属性
        // getWindowManager().getDefaultDisplay().getMetrics(dm);
        ((Activity) mContext).getWindowManager().getDefaultDisplay()
                .getMetrics(dm);

        // 窗口的宽度
        int screenWidth = dm.widthPixels;

        // 窗口高度
        int screenHeight = dm.heightPixels;
        int screen[] = { screenHeight, screenWidth };
        return screen;

    }

    /**
     *
     * @Title: dip2px
     * @Description: TODO(dp转px)
     * @param @param context
     * @param @param dpValue
     * @param @return 设定文件
     * @return int 返回类型
     * @throws
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
