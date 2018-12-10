package com.example.myhttp;

import android.app.Activity;
import android.app.Fragment;
import android.app.Service;
import android.content.Context;
import android.widget.Toast;

public class HttpTask {
    private HttpBean bean;
    private IHttp task;
    private boolean showLogin = true;
    private boolean showBufferDialog = true;
    private boolean isDialogCanCancle = false; // 加载dialog是否点击返回能取消
    private Object cxt;
//    private LoadingDialog dialog;

    private OnHttpFinish onHttpFinish = new OnHttpFinish() {

        @Override
        public void onHttpFinish() {

            onPostExecute();
        }
    };

    public HttpTask(HttpBean bean, IHttp task, Activity cxt) {
        this(bean, task, cxt, false);
    }

    public HttpTask(HttpBean bean, IHttp task, Fragment cxt) {
        this(bean, task, cxt, false);
    }

    public HttpTask(HttpBean bean, IHttp task, Service cxt) {
        this(bean, task, cxt, false);
    }
    public HttpTask(HttpBean bean, IHttp task, Context cxt) {
        this(bean, task, cxt, false);
    }

    public HttpTask(HttpBean bean, IHttp task, Activity cxt, boolean flag) {
        this(bean, task, cxt, flag, true);
    }

    public HttpTask(HttpBean bean, IHttp task, Activity cxt, boolean flag, boolean isDialogCanCancle) {
        this.bean = bean;
        this.task = task;
        this.cxt = cxt;
        this.showBufferDialog = flag;
        this.isDialogCanCancle = isDialogCanCancle;
//        dialog = new LoadingDialog(cxt, isDialogCanCancle);
        execute(bean);
    }

    public HttpTask(HttpBean bean, IHttp task, Fragment cxt, boolean flag) {
        this(bean, task, cxt, flag, true);
    }

    public HttpTask(HttpBean bean, IHttp task, Fragment cxt, boolean flag, boolean isDialogCanCancle) {
        this.bean = bean;
        this.task = task;
        this.cxt = cxt;
        this.showBufferDialog = flag;
        this.isDialogCanCancle = isDialogCanCancle;
//        dialog = new LoadingDialog(cxt.mBaseActivity, isDialogCanCancle);
        execute(bean);
    }

    public HttpTask(HttpBean bean, IHttp task, Service cxt, boolean flag) {
        this.bean = bean;
        this.task = task;
        this.cxt = cxt;
        this.showBufferDialog = flag;
        execute(bean);
    }

    public HttpTask(HttpBean bean, IHttp task, Context cxt, boolean flag) {
        this.bean = bean;
        this.task = task;
        this.cxt = cxt;
        this.showBufferDialog = flag;
        execute(bean);
    }

    public HttpTask(HttpBean bean, IHttp task, Service cxt, boolean flag, boolean showLogin) {
        this.bean = bean;
        this.task = task;
        this.cxt = cxt;
        this.showLogin = showLogin;
        this.showBufferDialog = flag;
        execute(bean);
    }

    private void execute(HttpBean bean) {
        if (bean.getHttp_tag() == null) {
            logger.e("-- bean -- 中没有http_tag");
        } else {
            HttpUtil.cancelTag(bean.getHttp_tag());
            onPreExecute();
            doInBackground(bean);

        }
    }

    protected void onPreExecute() {
        if (cxt != null && showBufferDialog) {
            if (cxt instanceof Activity) {
                ((BaseActivity) cxt).showLoadingDialog(isDialogCanCancle, bean.getHttp_tag());
            } /*else if (cxt instanceof Fragment) {
                ((Fragment) cxt).showLoadingDialog(isDialogCanCancle, bean.getHttp_tag());
            }*/
        }
        task.onHttpPreExecute();
        task.onHttpPostExecute(onHttpFinish);
    }

    protected void doInBackground(HttpBean bean) {
        boolean flag = false;
        if (HttpConfig.getIS_CONNECTED()) {
            flag = true;
        } else {
            HttpConfig.getNetworkInfo((Context) cxt);
            flag = HttpConfig.getIS_CONNECTED();
        }

        if (flag) {
            if (bean.getBaseUrl() != null && !"".equals(bean.getBaseUrl()))
                HttpUtil.excute(bean, cxt, task, bean.getHttp_tag());
            else if (!bean.isDownfile())
                HttpUtil.excute_url(bean, cxt, task, bean.getHttp_tag());
            else
                HttpUtil.downFile(bean, cxt, task, bean.getHttp_tag());
        } else {
            task.onNetFaild();
            showToast();
            onPostExecute();
        }
    }

    private void showToast() {
        if (cxt != null) {
            boolean needRun = false;
            if (cxt instanceof Fragment) {
                Toast.makeText(((Fragment) cxt).getActivity(), "网络不好，请检查网络", Toast.LENGTH_SHORT).show();
            } else if (cxt instanceof Activity) {
                needRun = !((Activity) cxt).isFinishing();
                Toast.makeText((BaseActivity) cxt, "网络不好，请检查网络", Toast.LENGTH_SHORT).show();

            }
        }
    }

    protected void onPostExecute() {
        logger.e("onpostexcute");
        if (cxt != null) {
            boolean needRun = false;
            if (cxt instanceof Fragment) {
                needRun = ((Fragment) cxt).isVisible();
            } else if (cxt instanceof Activity) {
                needRun = !((Activity) cxt).isFinishing();
            } else if (cxt instanceof Service) {
                needRun = true;
            }

            if (needRun) {
                if (showBufferDialog) {
                    if (cxt instanceof BaseActivity) {
                        ((BaseActivity) cxt).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((BaseActivity) cxt).dissMissLoadingDialog();
                            }
                        });

                    } /*else if (cxt instanceof BaseFragment) {
                        ((BaseFragment) cxt).getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((BaseFragment) cxt).dissMissLoadingDialog();
                            }
                        });


                    }*/
                }
            } else {
                logger.e("------ 条件不允许，可以不用执行onPostExecute方法了...");
            }
        } else {
            logger.e("-----cxt---------is------null-----");
        }

    }

    public interface OnHttpFinish {
        void onHttpFinish();
    }
}
