package com.wanderdemo.callback;

import android.os.Bundle;


public interface SwitchFragmentCallback {

    /**
     * @param tag : Fragment Name
     * @param bundle  : Bundle data passing between fragments
     * @param addToBackStack : Add fragment to backstack
     */
    void switchFragment(String tag, Bundle bundle, boolean addToBackStack);
}
