package com.github.zhtouchs.Utils;

import android.util.Log;

public class ZHLog {
    private static final String TAG = "ZHLog_";

    private ZHLog() {
    }

    public static <T> void d(String tag, T t) {
        Log.d(TAG + tag, String.valueOf(t));
    }

    public static <T> void e(String tag, T t) {
        Log.e(TAG + tag, String.valueOf(t));
    }

    public static <T> void w(String tag, T t) {
        Log.w(TAG + tag, String.valueOf(t));
    }

    public static <T> void v(String tag, T t) {
        Log.v(TAG + tag, String.valueOf(t));
    }

    public static <T> void i(String tag, T t) {
        Log.i(TAG + tag, String.valueOf(t));
    }

}
