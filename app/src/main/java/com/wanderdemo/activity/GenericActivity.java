package com.wanderdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.wanderdemo.R;
import com.wanderdemo.application.AppManager;
import com.wanderdemo.callback.SwitchFragmentCallback;
import com.wanderdemo.fragment.BaseFragment;
import com.wanderdemo.fragment.FragmentFactory;
import com.wanderdemo.fragment.HomeFragment;
import com.wanderdemo.fragment.SplashFragment;
import com.wanderdemo.utils.LogUtil;

import java.util.ArrayList;


public class GenericActivity extends BaseActivity implements SwitchFragmentCallback {

    private BaseFragment currentFragment;
    private String TAG = getClass().getSimpleName();
    private String targetFragment = null;
    private String fragmentTag;
    private ArrayList<BaseFragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_generic;
    }

    @Override
    protected void initData(Intent intent) {
        fragmentList = new ArrayList<BaseFragment>();
    }

    @Override
    protected void initAllMembersView() {

        initFragment(targetFragment);

    }

    /**
     * Inits & displays the fragment on the Activity
     *
     * @param targetFragment
     */
    private void initFragment(String targetFragment) {

        if (targetFragment != null) {
            switchFragment(targetFragment, null, false);
        } else {
            /* default behaviour */
            switchFragment(SplashFragment.FRAGMENT_TAG, null, false);
            LogUtil.d("Et", "fragment:LoadingLauncherFragment");
        }
    }

    @Override
    public void switchFragment(String tag, Bundle bundle, boolean addToBackStack) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            fragment = FragmentFactory.getFragmentByTag(tag);
            if (bundle != null) {
                fragment.setArguments(bundle);
            }
            if (fragment != null) {

                ft.add(R.id.launcher_content, fragment, tag);
                if (currentFragment != null) {
                    ft.hide(currentFragment);
                }
                // ft.commit();
                ft.commitAllowingStateLoss();
                currentFragment = fragment;
                fragmentList.add(fragment);
//                targetFragment = fragment.getTag();
            }
        } else {
            if (currentFragment == fragment) {
                return;
            }
            if (!fragment.isAdded() && bundle != null) {
                fragment.setArguments(bundle);
            }
            if (!fragment.isAdded()) {
                ft.add(R.id.launcher_content, fragment, tag);

            } else {
                ft.show(fragment);
            }

            if (currentFragment != null) {
                ft.hide(currentFragment);
            }
            ft.commit();
            currentFragment = fragment;
            fragmentList.add(fragment);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK)) {

            if (currentFragment.getTag().equals(HomeFragment.FRAGMENT_TAG)) {
                finish();

            }

        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        AppManager.getAppManager().finishActivity(GenericActivity.class);
    }
}
