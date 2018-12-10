package com.example.myhttp;


public abstract class DownHttp<T extends BaseResult> implements IHttp<T> {

    @Override
    public void onHttpPreExecute() {

    }

    @Override
    public void doHttpInBackground(String json) {

    }

    @Override
    public void onHttpPostExecute(HttpTask.OnHttpFinish onHttpFinish) {

    }

    @Override
    public void onNetFaild() {
        doNetFailed();
    }

    @Override
    public void downSuccess() {
        doDownSuccess();
    }

    @Override
    public void downFailed() {
        doDownFailed();
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

    protected abstract void doNetFailed();

    protected abstract void doDownFailed();

    protected abstract void doDownSuccess();

}
