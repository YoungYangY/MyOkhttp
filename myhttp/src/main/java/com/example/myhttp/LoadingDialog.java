package com.example.myhttp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * @author qiuch
 * 
 */
public class LoadingDialog extends Dialog {
	private Context mContext;
	private boolean mCancelable;

	public LoadingDialog(Context context) {
		super(context, R.style.dialog);
		mContext = context;
	}

	public LoadingDialog(Context context, boolean cancelable) {
		super(context, R.style.dialog);
		mContext = context;
		mCancelable = cancelable;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading_dialog_layout);
		this.setCanceledOnTouchOutside(false);
		this.setCancelable(mCancelable);
	}

	@Override
	public void show() {
		super.show();
	}

	@Override
	public void dismiss() {
		super.dismiss();
	}

}
