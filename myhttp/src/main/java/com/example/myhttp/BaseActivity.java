package com.example.myhttp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity {
    public BaseActivity mBaseActivity;

    public LoadingDialog mLoadingDialog;// 普通加载对话框


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBaseActivity = this;
        mLoadingDialog = new LoadingDialog(this, true);

    }


    public void showLoadingDialog(boolean isCancle, final Object http_tag) {
        if (mLoadingDialog != null) {
            if (mLoadingDialog.isShowing()) {
                mLoadingDialog.dismiss();
            }
            mLoadingDialog = null;

        }
        mLoadingDialog = new LoadingDialog(mBaseActivity, isCancle);

        if (!mBaseActivity.isFinishing())
            mLoadingDialog.show();

    }

    public void dissMissLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

}
