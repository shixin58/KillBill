package com.bride.widget;

import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * <p>Created by shixin on 2018/10/27.
 */
public class AndroidToJS {
    @JavascriptInterface
    public void hello(String msg) {
        Log.i("AndroidToJS", "hello "+msg);
    }
}
