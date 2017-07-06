package com.oywj.usefulviews.utils;

import android.content.Context;
import android.content.res.Resources;

import com.oywj.usefulviews.ui.basic.BasicApplication;


/**
 * 创建者     伍碧林
 * 创建时间   2015/12/27 11:05
 * 描述	      和ui相关的一些工具类
 * 更新者     $Author: admin $
 * 更新时间   $Date: 2016-01-02 14:33:24 +0800 (星期六, 02 一月 2016) $
 * 更新描述   ${}
 */
public class UIUtils {
    /**
     * 得到上下文
     */
    public static Context getContext() {
        return BasicApplication.getInstance();
    }

    /**
     * 得到Resource对象
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 得到string.xml中的字符
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 得到string.xml中的字符,带占位符
     */
    public static String getString(int resId, Object... formatArgs) {
        return getResources().getString(resId, formatArgs);
    }

    /**
     * 得到string.xml中的字符数组
     */
    public static String[] getStringArr(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 得到color.xml中的颜色值
     */
    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    /**
     * 得到应用程序的包名
     */
    public static String getPackageName() {
        return getContext().getPackageName();
    }




    /**
     * dip-->px
     *
     * @param
     * @return
     */
    public static int dip2Px(int dip) {
        // px/dp = density
        // px/(ppi/160) = px
        float density = getResources().getDisplayMetrics().density;//1.5
        int ppi = getResources().getDisplayMetrics().densityDpi;//240
        int px = (int) (dip * density + .5f);
        return px;
    }

    /**
     * px-->dip
     *
     * @param px
     * @return
     */
    public static int px2Dip(int px) {
        // px/dp = density
        float density = getResources().getDisplayMetrics().density;//1.5
        int dp = (int) (px / density + .5f);
        return dp;
    }
}
