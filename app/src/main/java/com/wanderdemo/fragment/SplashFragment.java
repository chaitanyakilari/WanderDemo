package com.wanderdemo.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.wanderdemo.R;
import com.wanderdemo.callback.SwitchFragmentCallback;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SplashFragment extends BaseFragment {
    public static final String FRAGMENT_TAG = SplashFragment.class.getSimpleName();
    private int SPLASH_TIME_OUT = 2000;
    private SwitchFragmentCallback switchFragmentCallback;

    @Override
    public int getContentViewId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {


        Observable.timer(SPLASH_TIME_OUT, TimeUnit.MILLISECONDS) //handle after 2000ms, no order id and detail return.
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {

                        switchFragmentCallback.switchFragment(HomeFragment.FRAGMENT_TAG, null, false);
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        BaseFragment fragment = (BaseFragment) getActivity().getSupportFragmentManager().findFragmentByTag(SplashFragment.FRAGMENT_TAG);
                        ft.remove(fragment);
                        ft.commit();


                    }
                });


    }

    @Override
    protected void initData(Bundle bundle) {
        switchFragmentCallback = (SwitchFragmentCallback) context;
    }
}
