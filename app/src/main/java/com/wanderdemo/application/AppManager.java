package com.wanderdemo.application;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.widget.Toast;


import com.wanderdemo.utils.LogUtil;

import java.util.Stack;

public class AppManager {
    private Stack<Activity> activityStack;
    private static AppManager instance;

    private AppManager() {
    }


    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
        LogUtil.d("Et", "activityStack : " + activityStack.size() + "  the new activity is : " + activity.getLocalClassName());
        StringBuffer str = new StringBuffer();
        for (Activity ignored : activityStack) {
            if (ignored.getLocalClassName().split("\\.").length > 0) {
                str.append("  " + ignored.getLocalClassName().split("\\.")[ignored.getLocalClassName().split("\\.").length - 1]);
            }
        }
        LogUtil.d("Et", "activityStack all: " + str.toString());
    }

    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }


    public void removeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity = null;
        }
    }

    public void finishActivity(Class<?> cls) {
        for (int i = 0; i < activityStack.size(); i++) {
            if (activityStack.get(i).getClass().equals(cls)) {
                finishActivity(activityStack.get(i));
            }
        }
    }

    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * finish all activity with out cls
     *
     * @param cls
     */
    public void finishAllWithOutThis(Class<?> cls) {
        for (int i = 0; i < activityStack.size(); i++) {
            if (!activityStack.get(i).getClass().equals(cls)) {
                finishActivity(activityStack.get(i).getClass());
            }
        }
        LogUtil.d("Et", "activityStack size : " + activityStack.size());
    }


    public boolean findActivity(Class<?> cls) {
        for (int i = 0; i < activityStack.size(); i++) {
            if (activityStack.get(i).getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }


    public boolean findActivityOnStack(Activity activity) {
        if (activityStack.contains(activity)) {
            return true;
        }
        return false;
    }

    /**
     * find specified acitivity count
     *
     * @param cls
     * @return
     */
    public int getAcivityCount(Class<?> cls) {
        int count = 0;
        for (int i = 0; i < activityStack.size(); i++) {
            if (activityStack.get(i).getClass().equals(cls)) {
                count++;
            }
        }

        return count;
    }

    public void AppExit(Context context, Boolean isBackground) {
        try {
            if (isBackground) {
                PackageManager pm = context.getPackageManager();
                ResolveInfo homeInfo =
                        pm.resolveActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME), 0);

                ActivityInfo ai = homeInfo.activityInfo;
                Intent startIntent = new Intent(Intent.ACTION_MAIN);
                startIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                startIntent.setComponent(new ComponentName(ai.packageName, ai.name));
                startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    context.startActivity(startIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(context, "null", Toast.LENGTH_SHORT).show();
                } catch (SecurityException e) {
                    Toast.makeText(context, "null", Toast.LENGTH_SHORT).show();
                }
            } else {
                finishAllActivity();

                ActivityManager activityMgr = (ActivityManager) context
                        .getSystemService(Context.ACTIVITY_SERVICE);
                //activityMgr.restartPackage(context.getPackageName());

                activityMgr.killBackgroundProcesses(context.getPackageName());


                System.exit(0);
            }

        } catch (Exception e) {

        }
    }
}
