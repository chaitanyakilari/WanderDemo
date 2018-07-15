package com.wanderdemo.application;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDexApplication;

public class WanderDemoApplication extends MultiDexApplication {

    private static WanderDemoApplication sInstance;
    //Get to the main thread context
    private static WanderDemoApplication mContext = null;
    //Get to the main thread handler
    private static Handler mMainThreadHandler = null;
    //Get to the main thread looper
    private static Looper mMainThreadLooper = null;
    //Get to the main thread
    private static Thread mMainThead = null;
    //Get to the main thread id
    private static int mMainTheadId;

    public static WanderDemoApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        this.mContext = this;
//        UrlConstants.SOURCE_ID = UiUtils.getString(R.string.source_id);

        this.mMainThreadHandler = new Handler();
        this.mMainThreadLooper = getMainLooper();
        this.mMainThead = Thread.currentThread();
        this.mMainTheadId = android.os.Process.myTid();
        /* Put initializations here */


    }


    public static Context getApplication() {
        return mContext;
    }

    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    public static Looper getMainThreadLooper() {
        return mMainThreadLooper;
    }

    public static Thread getMainThread() {
        return mMainThead;
    }

    public static int getMainThreadId() {
        return mMainTheadId;
    }
}
