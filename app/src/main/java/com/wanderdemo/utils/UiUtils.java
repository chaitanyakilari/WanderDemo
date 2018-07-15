package com.wanderdemo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.wanderdemo.activity.BaseActivity;
import com.wanderdemo.application.WanderDemoApplication;


public class UiUtils {
    //public static String langPreference;
    /* contains public static methods */
    public static Context getContext() {
        return WanderDemoApplication.getApplication();
    }


    public static long getMainThreadId() {
        return WanderDemoApplication.getMainThreadId();
    }


    /**
     * Gets the handler for the main thread
     */
    public static Handler getHandler() {
        return WanderDemoApplication.getMainThreadHandler();
    }

    /**
     * The delay in the main thread executes runnable
     */
    public static boolean postDelayed(Runnable runnable, long delayMillis) {
        return getHandler().postDelayed(runnable, delayMillis);
    }

    /**
     * The main thread executes runnable
     */
    public static boolean post(Runnable runnable) {
        return getHandler().post(runnable);
    }


    /**
     * get resurce
     */
    public static Resources getResources() {

        return getContext().getResources();
    }

    /**
     * get String
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    //Judge the current thread is not in the main thread
    public static boolean isRunInMainThread() {
        return android.os.Process.myTid() == getMainThreadId();
    }

    public static void runInMainThread(Runnable runnable) {
        if (isRunInMainThread()) {
            runnable.run();
        } else {
            post(runnable);
        }
    }

    /**
     * A simple package for toast. Thread-safe, can be invoked on non-UI threads.
     */
    public static void showToastSafe(final int resId) {
        showToastSafe(getString(resId));
    }

    /**
     * A simple package for toast. Thread-safe, can be invoked on non-UI threads.
     */
    public static void showToastSafe(final String str) {
        if (str == null || str.length() == 0) {
            return;
        }

        if (isRunInMainThread()) {
            showToast(str);
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    showToast(str);
                }
            });
        }
    }

    private static void showToast(String str) {
        if (str.length() == 0) {
            return;
        }
        BaseActivity frontActivity = BaseActivity.getForegroundActivity();
        if (frontActivity != null) {
            Toast toast = Toast.makeText(frontActivity, str, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    /**
     * Displays safe toast, checks if fragment is attached and activity is alive
     *
     * @param fragment Fragment context
     * @param message  toast message
     */
    public static void showToast(Fragment fragment, String message) {
        Activity activity = fragment.getActivity();
        if (fragment.isAdded() && activity != null) {
            showToastSafe(message);
        }
    }

    public static boolean isEmpty(String str) {
        if (TextUtils.isEmpty(str))
            return true;
        else
            return false;
    }

    public static void closeSoftKeys(Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if(((Activity) mContext).getWindow()
                .getCurrentFocus() != null){
            imm.hideSoftInputFromWindow(((Activity) mContext).getWindow()
                    .getCurrentFocus().getWindowToken(), 0);
        }
    }

}

