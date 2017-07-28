package com.oywj.usefulviews.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.oywj.usefulviews.ui.basic.BasicApplication;

/**
 * Created by Administrator on 2016/2/25.
 * 项目名:ZCSTMarket
 * 包名: com.zcstmarket.utils
 * 作者: 欧阳维骏
 * 描述: 尺寸的工具类
 */
public class DensityUtils {

    /**
     * dp转px
     *
     * @param dpVal
     * @return
     */
    public static int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, BasicApplication.getInstance().getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     *
     * @param spVal
     * @retur
     */
    public static int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, BasicApplication.getInstance().getApplicationContext().getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param pxVal
     * @return
     */
    public static float px2dp(float pxVal) {
        final float scale = BasicApplication.getInstance().getApplicationContext().getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     *
     * @param pxVal
     * @return
     */
    public static float px2sp(float pxVal) {
        return (pxVal / BasicApplication.getInstance().getResources().getDisplayMetrics().scaledDensity);
    }

    /**
     * 获取屏幕的宽高
     *
     * @return
     */
    public static int[] getDisplaySize() {
        int[] size = new int[2];
        Resources resources = BasicApplication.getInstance().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        size[0] = dm.widthPixels;
        size[1] = dm.heightPixels;
        return size;
    }


}
