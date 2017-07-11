package com.oywj.usefulviews.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 一个获取当前网络状态的工具类
 */
public class NetworkUtil {


    /**
     * The primary responsibilities of this class are to:
     * <p/>
     * 1 . Monitor network connections (Wi-Fi, GPRS, UMTS, etc.)
     * 可以用来管理网络连接
     * <p/>
     * 2 . Send broadcast intents when network connectivity changes
     * 当网络连接发生改变的时候，将会发送广播
     * <p/>
     * 3 . Attempt to "fail over" to another network when connectivity
     * to a network is lost
     * 当一个连接失败的时候，会自动使用其他的连接
     * <p/>
     * 4 . Provide an API that allows applications to query the
     * coarse-grained or fine-grained state of the available networks
     * 提供了api，可以用来查询可用的网络
     * <p/>
     * 5 . Provide an API that allows applications to request and
     * select networks for their data traffic
     * 提供了api ，可以用来选择网络连接
     */

    //需要提供的方法：
    // 1 . 判断 网络是否连接
    // 2 . 判断 wifi 是否可用
    // 3 . 判断 手机网络 是否可用

    //返回值，决定了网络是否可用
    public static boolean isNetConnected(Context context) {
        //通过一个系统服务，可以获取到一个连接的ConnectivityManager， 用来
        //判断网络是否可用
        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        //获取到当前 可用的网络示例， 可能是wifi ，
        // 可能是手机网络，也可能是 null
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        //当获取到的网络不为null ， 且可以连接到网络的时候
        if (networkInfo != null && networkInfo.isConnected()) {
            //网络可用，返回true
            return true;
        }

        //其他， 返回false
        return false;
    }

    //判断 wifi 网络是否连接
    public static boolean isWifiConnected(Context context) {
        //获取到管理网络的一个管理器
        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        //获取到wifi网络的信息
        NetworkInfo networkInfo = manager.getNetworkInfo
                (ConnectivityManager.TYPE_WIFI);

        //当连接不为null ， 且 连接上网络的时候
        if (networkInfo != null && networkInfo.isConnected()) {
            //返回true
            return true;
        }
        //其他返回false
        return false;
    }

    //判断 手机状态下的网络 是否可用
    public static boolean isPhoneNetConnected(Context context) {

        //获取到管理网络的一个管理器
        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);


        //获取到 手机网络的信息
        NetworkInfo networkInfo = manager.getNetworkInfo
                (ConnectivityManager.TYPE_MOBILE);

        //判断网络是否可用
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }

        return false;
    }


}
