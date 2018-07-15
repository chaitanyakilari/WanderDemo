package com.wanderdemo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    public abstract int getContentViewId();
    protected Context context;
    protected View mRootView;
    public Unbinder mUnbinder;
    protected Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView =inflater.inflate(getContentViewId(), container, false);
        mUnbinder= ButterKnife.bind(this, mRootView);
        this.context = getActivity();
        bundle = this.getArguments();
        initData(bundle);
        initAllMembersView(savedInstanceState);
        return mRootView;
    }

    protected abstract void initAllMembersView(Bundle savedInstanceState);
    protected abstract void initData(Bundle bundle);
    @Override
    public void onDestroyView() {
        super.onDestroyView();
     if(mUnbinder!=null)   mUnbinder.unbind();

    }
}
