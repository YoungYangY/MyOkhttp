package com.example.yang.myokhttp;

import android.os.Bundle;

import com.example.myhttp.BaseActivity;
import com.example.myhttp.BaseResult;
import com.example.myhttp.DownHttp;
import com.example.myhttp.HttpBean;
import com.example.myhttp.HttpTask;
import com.example.myhttp.MyHttp;
import com.example.myhttp.Settings;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doRequest();
    }


    private void doRequest() {
        MyHttp<BaseResult> myHttp = new MyHttp<BaseResult>(mBaseActivity, BaseResult.class) {
            @Override
            protected void doExecuteBackground(BaseResult baseResult) {

            }

            @Override
            protected void doExecuteSuccess(BaseResult baseResult) {

            }

            @Override
            protected void doExecuteFailed(BaseResult baseResult) {

            }
        };
        HttpBean bean = new HttpBean();
        bean.setPost(true);
        bean.getmPostData().put("id", "id");
        bean.setBaseUrl(Settings.BASE_URL);

        new HttpTask(bean, myHttp, mBaseActivity);
    }

    void dodownFile() {
        DownHttp<BaseResult> downfile = new DownHttp<BaseResult>() {
            @Override
            protected void doDownFailed() {

            }

            @Override
            protected void doDownSuccess() {

            }

            @Override
            protected void doNetFailed() {

            }

            @Override
            public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                super.downloadProgress(currentSize, totalSize, progress, networkSpeed);
//                logger.e("totalSize: "+totalSize+" currentSize:"+currentSize+" progress:"+progress+" networkSpeed:"+networkSpeed);
//                Log.i("downfile当前下载的进度", progress + "");
                int pb = (int) (progress * 100);

            }
        };
        HttpBean bean = new HttpBean();
        bean.setUrl("");
        bean.setDownfile(true);
        bean.setDownfilepath(Settings.BASE_URL);
        bean.setDownfilename("myokhttp.apk");
        new HttpTask(bean, downfile, getApplicationContext());

    }

}
