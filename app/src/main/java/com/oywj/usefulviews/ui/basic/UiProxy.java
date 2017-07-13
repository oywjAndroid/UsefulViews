package com.oywj.usefulviews.ui.basic;

import android.content.Context;

/**
 * UiProxy可用于UI层对Presenter层提供的UI操作。
 */
public interface UiProxy {
    // create uiProxy that the subclass implement uiProxy .
    Context getProxyContext();

}
