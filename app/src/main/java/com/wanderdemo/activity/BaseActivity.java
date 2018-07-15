package com.wanderdemo.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.support.v7.app.AppCompatActivity;

import com.hwangjr.rxbus.RxBus;
import com.wanderdemo.R;
import com.wanderdemo.application.AppManager;
import com.wanderdemo.constants.KeyConstants;
import com.wanderdemo.utils.LogUtil;
import com.wanderdemo.utils.NetUtil;
import com.wanderdemo.utils.UiUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {
    private static BaseActivity mForegroundActivity = null;
    public Unbinder mUnbinder;
    private MainBroadcastReceiver mainReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getContentViewId() == R.layout.activity_splash) {
            if (!this.isTaskRoot()) {
                Intent intent1 = getIntent();
                if (intent1 != null) {
                    String action = intent1.getAction();
                    if (intent1.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                        finish();
                        return;
                    }
                }
            }
        }

        RxBus.get().register(this);
        setContentView(getContentViewId());
        mUnbinder = ButterKnife.bind(this);
        mainReceiver = new MainBroadcastReceiver();/*add IntentFilter*/
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mainReceiver, filter);

        initData(getIntent());
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                LogUtil.i("IdleHandler", "queueIdle");
                initAllMembersView();
                return false;
            }
        });
        AppManager.getAppManager().addActivity(this);

    }

    public abstract int getContentViewId();

    protected abstract void initData(Intent intent);

    protected abstract void initAllMembersView();


    /**
     * Gets the activity that is currently in the foreground
     */
    public static BaseActivity getForegroundActivity() {
        return mForegroundActivity;
    }

    class MainBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                if (!NetUtil.isConnected(BaseActivity.this)) {
                    UiUtils.showToastSafe(R.string.noNetwork);
                    boolean val = NetUtil.isNetworkAvailable(context);
                    RxBus.get().post(KeyConstants.KEY_INTERNET_STATUS_CHANGE, val);
                } else {
                    boolean val = NetUtil.isNetworkAvailable(context);
                    RxBus.get().post(KeyConstants.KEY_INTERNET_STATUS_CHANGE, val);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        mForegroundActivity = this;
        super.onResume();

        LogUtil.d("Et", "onResume : " + this.getLocalClassName());
    }

    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d("Et", "onDestroy : " + this.getLocalClassName());
        System.gc();
        exitAction();
    }

    private void exitAction() {

        RxBus.get().unregister(this);
        if (mainReceiver != null) unregisterReceiver(mainReceiver);
        if (mUnbinder != null) mUnbinder.unbind();
    }
}
