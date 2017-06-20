package com.oywj.usefulviews.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Activities jump utils.
 */
public class JumpUtils {


    public static void jumpAnotherActivity(Activity activity,
                                           Class<? extends Activity> cls) {
        jumpAnotherActivity(activity, cls, false);
    }

    /**
     * 跳转到其他Activity。
     *
     * @param activity 当前Activity
     * @param cls      需要跳转到的Activity
     * @param isFinish 是否finish当前Activity
     */
    public static void jumpAnotherActivity(Activity activity,
                                           Class<? extends Activity> cls,
                                           boolean isFinish) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);
        if (isFinish) activity.finish();
    }

    /**
     * 携带一定的参数跳转到其他Activity。
     *
     * @param activity 当前Activity
     * @param cls      需要跳转到的Activity
     * @param hashMap  数据集
     */
    public static void jumpAnotherActivity(Activity activity,
                                           Class<? extends Activity> cls,
                                           HashMap<String, Object> hashMap) {
        Intent intent = new Intent(activity, cls);
        Iterator<?> iterator = hashMap.entrySet().iterator();
        for (; iterator.hasNext(); ) {
            @SuppressWarnings("unchecked")
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                intent.putExtra(key, (String) value);
            } else if (value instanceof Boolean) {
                intent.putExtra(key, (boolean) value);
            } else if (value instanceof Integer) {
                intent.putExtra(key, (int) value);
            } else if (value instanceof Float) {
                intent.putExtra(key, (float) value);
            } else if (value instanceof Double) {
                intent.putExtra(key, (double) value);
            } else if (value instanceof Parcelable) {
                intent.putExtra(key, (Parcelable) value);
            }
        }
        activity.startActivity(intent);
    }
}
