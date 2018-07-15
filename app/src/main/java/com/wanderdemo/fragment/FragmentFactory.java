package com.wanderdemo.fragment;


public class FragmentFactory {

    public static BaseFragment getFragmentByTag(String tag) {

        if (tag.equals(SplashFragment.FRAGMENT_TAG)) {
            return new SplashFragment();
        } else if (tag.equals(HomeFragment.FRAGMENT_TAG)) {
            return new HomeFragment();
        }

        return null;
    }
}
