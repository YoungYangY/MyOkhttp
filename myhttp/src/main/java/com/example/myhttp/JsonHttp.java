package com.example.myhttp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

public abstract class JsonHttp implements IHttp<String> {
    private Handler handler = new Handler(Looper.getMainLooper());
    private HttpTask.OnHttpFinish mOnHttpFinish;

    private Context mContext;

    public JsonHttp(Context context) {
        this.mContext = context;
    }
    @Override
    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {

    }

    @Override
    public void downSuccess() {

    }

    @Override
    public void downFailed() {

    }

    @Override
    public void onHttpPreExecute() {

    }

    @Override
    public void doHttpInBackground(final String json) {
        logger.e("json -- > " + json);
        final boolean flag = (json != null);
        if (flag) doExecuteBackground(json);
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (mOnHttpFinish != null) {
                    mOnHttpFinish.onHttpFinish();
                }

                if (flag) {
                    doExecuteSuccess(json);
                } else {
                    doExecuteFailed(json);

                }
            }
        });

    }

    @Override
    public void onHttpPostExecute(HttpTask.OnHttpFinish onHttpFinish) {
        mOnHttpFinish = onHttpFinish;
    }

    @Override
    public void onNetFaild() {

    }

    protected abstract void doExecuteBackground(String json);

    protected abstract void doExecuteSuccess(String json);

    protected abstract void doExecuteFailed(String json);

}
