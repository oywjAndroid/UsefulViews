package com.oywj.usefulviews.utils;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

/**
 * 关于Android4.4实现浸入式的工具类
 */
public class StatusHintUtils {
    private static StatusHintUtils mInstance;

    private StatusHintUtils() {

    }

    public static StatusHintUtils getInstance() {
        if (mInstance == null) {
            synchronized (StatusHintUtils.class) {
                if (mInstance == null) {
                    mInstance = new StatusHintUtils();
                }
            }
        }
        return mInstance;
    }

    public void translucentWindow(Context context, View statusBar) {
        // 获取状态栏的高度
        int statusHeight = getStatusBarHeight(context);
        // 填充伪状态栏
        ViewGroup.LayoutParams layoutParams = statusBar.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = statusHeight;
        statusBar.setLayoutParams(layoutParams);
        statusBar.setVisibility(View.VISIBLE);
    }

    public int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        try {
            Class<?> dimen = Class.forName("com.android.internal.R$dimen");
            Object dimenObj = dimen.newInstance();

            int height = Integer.parseInt(
                    dimen.getField("status_bar_height").get(dimenObj).toString()
            );

            statusBarHeight = context.getApplicationContext().getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

}
