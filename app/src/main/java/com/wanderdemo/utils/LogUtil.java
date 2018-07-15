package com.wanderdemo.utils;

import android.util.Log;

import com.wanderdemo.BuildConfig;


public class LogUtil {

    public static final int LOG_LEVEL_NONE = 0;     //  log  Do not output any log
    public static final int LOG_LEVEL_DEBUG = 1;    //  Debug blue
    public static final int LOG_LEVEL_INFO = 2;     //    Debug green
    public static final int LOG_LEVEL_WARN = 3;     //    Debug Orange
    public static final int LOG_LEVEL_ERROR = 4;    //     Debug red
    public static final int LOG_LEVEL_ALL = 5;      //  Debug all


    private static int mLogLevel = BuildConfig.LOG_DEBUG ? LOG_LEVEL_ALL : LOG_LEVEL_NONE;


    public static int getLogLevel() {
        return mLogLevel;
    }


    public static void setLogLevel(int level) {
        LogUtil.mLogLevel = level;
    }


    public static void d(String tag, String msg) {
        if (getLogLevel() >= LOG_LEVEL_DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (getLogLevel() >= LOG_LEVEL_INFO) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (getLogLevel() >= LOG_LEVEL_WARN) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable exception) {
        if (getLogLevel() >= LOG_LEVEL_ERROR) {
            Log.e(tag, msg, exception);
        }
    }

    public static void e(String tag, String msg) {
        if (getLogLevel() >= LOG_LEVEL_ERROR) {
            Log.e(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (getLogLevel() >= LOG_LEVEL_ALL) {
            Log.v(tag, msg);
        }
    }
}
