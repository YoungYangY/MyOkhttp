package com.example.myhttp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class MyHttp<T extends BaseResult> implements IHttp<T> {

    private Context mContext;
    private HttpTask.OnHttpFinish mOnHttpFinish;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Class<T> tClass;

    private CheckErrString checkErrString;
    private boolean isFailShowToast = false; // 解析失败后是否显示 错误提示

    public MyHttp(Context context, Class<T> tClass) {
        this(context, tClass, true);
    }

    public MyHttp(Context context, Class<T> tClass, boolean isFailShowToast) {
        this.mContext = context;
        this.tClass = tClass;
        this.isFailShowToast = isFailShowToast;
    }

    @Override
    public void onNetFaild() {

    }

    @Override
    public void onHttpPreExecute() {

    }

    /**
     * @param json 原json串
     */
    public void onGetJson(String json) {

    }

    @Override
    public void doHttpInBackground(final String json) {
        logger.e("json -- " + json);
        try {
            T a = null;
            try {
                Gson gson = new Gson();
                a = gson.fromJson(json, tClass);
            } catch (Exception e) { // 处理data[]情况
                logger.e(e);
                JSONObject object = new JSONObject(json);
                if (object != null) {
                    JSONArray dataArray = object.optJSONArray("data");
                    if (dataArray != null) {
                        a = tClass.newInstance();

                        a.setErr(object.optString("err"));
                        a.set_time(object.optString("_time"));
                    }
                }

            }
            final T t = a;
            final boolean flag = (t != null && t.getErr() != null && t.getErr().equals("hapn.ok"));

            if (flag) doExecuteBackground(t);

            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (mOnHttpFinish != null) {
                        mOnHttpFinish.onHttpFinish();
                    }

                    if (flag) {
                        doExecuteSuccess(t);
                    } else {
                        if (t != null && t.getErr() != null) {
                            if (isFailShowToast) {
                                checkErrString = new CheckErrString(mContext);
                                checkErrString.showErr(t.getErr());
                            }
                            doExecuteFailed(t);
                        } else {
                            try {
                                T oj = tClass.newInstance();
                                oj.setErr("null");
                                doExecuteFailed(oj);
                            } catch (Exception e) {
                                logger.e(e);
                            }
                            onHttpException();
//                            MyToast.makeText(mContext,"网络不好，请检查网络", Toast.LENGTH_SHORT).show();
                            onGetJson(json);
                            logger.e("restult.err -- error");
                        }
                    }

                }
            });


        } catch (Exception e) {
            if (mOnHttpFinish != null) {
                mOnHttpFinish.onHttpFinish();
            }
            logger.e(e);

            handler.post(new Runnable() {
                @Override
                public void run() {
                    onHttpException();
                }
            });
        }

    }

    public void onHttpException() {

    }


    @Override
    public void onHttpPostExecute(final HttpTask.OnHttpFinish onHttpFinish) {
        mOnHttpFinish = onHttpFinish;
    }

    /**
     * 执行下载过程中的进度回调，UI线程
     *
     * @param currentSize  当前下载的字节数
     * @param totalSize    总共需要下载的字节数
     * @param progress     当前下载的进度
     * @param networkSpeed 当前下载的速度   字节/秒
     */
    @Override
    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {

    }

    @Override
    public void downSuccess() {

    }

    @Override
    public void downFailed() {

    }

    protected abstract void doExecuteBackground(T t);

    protected abstract void doExecuteSuccess(T t);

    protected abstract void doExecuteFailed(T t);

}
