package com.example.myhttp;

public interface IHttp <T> {
    void onHttpPreExecute();
    void doHttpInBackground(String json);
//    void onHttpCancelled(T a);
    void onHttpPostExecute(final HttpTask.OnHttpFinish onHttpFinish);
    void onNetFaild();

    void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed);
    void downSuccess();
    void downFailed();
//    void onHttpExecuteSuccess(T t);
//    void onHttpExecuteFailed(T t);
//    void onHttpProgressUpdate(T t, Integer percent);
}
