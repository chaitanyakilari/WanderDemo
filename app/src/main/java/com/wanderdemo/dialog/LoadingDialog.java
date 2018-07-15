package com.wanderdemo.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import com.wanderdemo.R;


/**
 * Created by Chaitanya kumar.
 */
public class LoadingDialog extends ProgressDialog {
    private String title = null;
    ImageView mImageView;
    private AnimationDrawable animation;


    public LoadingDialog(Context context, int theme) {

        super(context, theme);
//        this.title = title;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init(getContext());
    }

    private void init(Context context) {

        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.load_dialog);
        setProgressStyle(R.style.LoadingDialog);
        WindowManager.LayoutParams params = getWindow().getAttributes();

        getWindow().setAttributes(params);

//        Window window = this.getWindow();
//        window.setGravity(Gravity.CENTER);
//        window.setWindowAnimations(R.style.DialogBottom);
        mImageView = (ImageView) findViewById(R.id.iv_gif);
        mImageView.setBackgroundResource(R.drawable.custom_progress_dialog);
        animation = (AnimationDrawable) mImageView.getBackground();

    }


    @Override
    public void show() {
        super.show();
       /* if (owLoadingView != null) {
            newtonCradleLoading.start();
            owLoadingView.startAnim();
        }*/
        animation.start();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        /*if (owLoadingView != null) {
            newtonCradleLoading.stop();
            owLoadingView.stopAnim();
        }*/
        animation.start();
    }
}