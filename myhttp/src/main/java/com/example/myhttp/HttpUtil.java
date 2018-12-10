package com.example.myhttp;

import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {
    private static OkHttpClient httpClient = null;

    //    private static final String baseurl = "http://api.qiaoxin.hapn.cc";
    public static void cancelTag(Object tag) {
        OkHttpClient httpClient = getHttpClient();
        for (Call call : httpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }

        for (Call call : httpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }


    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return ssfFactory;
    }

    private static OkHttpClient getHttpClient() {
        if (httpClient == null) {
            httpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .cookieJar(new SimpleCookieJar())
                    .sslSocketFactory(createSSLSocketFactory())
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession sslSession) {
                            return true;
                        }
                    })
                    .build();

        }

        return httpClient;
    }



    public static void excute(final HttpBean bean, Object ctx, final IHttp task, Object tag) {
//        cancelTag(tag);
        OkHttpClient httpClient = getHttpClient();
        try {
            String url;
            if (bean.isAddBaseUrl()) {
                url = Settings.BASE_URL + bean.getBaseUrl();
            } else {
                url = bean.getBaseUrl();
            }
            Request.Builder builder = new Request.Builder();

            for (String key : bean.getmHeaderData().keySet()) {
                logger.v("getPhpResult json --> header key = " + key + ",value = " + bean.getmHeaderData().get(key));
                builder.addHeader(key, (bean.getmHeaderData().get(key)) + "");
            }

            if (bean.isPost()) {
                Map<String, Object> map = RequestMapUtil.getSignParams("POST", bean.getmPostData(), bean.getBaseUrl());
                MultipartBody.Builder mBuilder = new MultipartBody.Builder();
                mBuilder.setType(MultipartBody.FORM);
                for (String key : map.keySet()) {
                    logger.e("HTTP Request BODY: " + key + "=" + bean.getmPostData().get(key));
                    mBuilder.addFormDataPart(key, (bean.getmPostData().get(key)) + "");
                }

                if (bean.getFile() != null) {
                    logger.e("file -->" + bean.getFile().getAbsolutePath());
                    String key = bean.getFileKey() == null ? "upload" : bean.getFileKey();
                    mBuilder.addFormDataPart(key, "img", RequestBody.create(MediaType.parse("image/png"), bean.getFile()));
//                        mBuilder.addPart(RequestBody.create(MediaType.parse("file"), bean.getFile()));
                }

                logger.d("http request [post] url : " + url);
                builder.url(url);
                builder.post(mBuilder.build());
            } else {
                Map<String, Object> map = RequestMapUtil.getSignParams("GET", bean.getmPostData(), bean.getBaseUrl());

                if (bean.get_etag() != null) {
                    map.put("_etag", bean.get_etag());
                }
                if (map.size() > 0) {
                    String temp = "";
                    boolean flag = false;

                    for (String key : map.keySet()) {
                        logger.v("getPhpResult json --->post key=" + key + ", value=" + bean.getmPostData().get(key));
                        if (flag) {
                            temp = temp + "&";
                        } else {
                            flag = true;
                        }

                        temp = temp + key + "=" + map.get(key);
                    }

                    if (!StringUtils.isNull(temp)) {
                        if (!url.contains("?")) {
                            url = url + "?" + temp;
                        } else {
                            url = url + temp;
                        }
                    }

                    logger.d("http request [get] url : " + url);
                    builder.url(url);
                    if (bean.getFile() != null && bean.getFile().exists() && StringUtils.notNull(bean.getFileKey())) {
                        builder.post(RequestBody.create(MediaType.parse("file"), bean.getFile()));
                    }
                }
            }
            builder.tag(tag);
            Request request = builder.build();
            httpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    logger.e("expection - " + e);
                    task.doHttpInBackground(null);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String data = response.body().string();
                    task.doHttpInBackground(data);

                }
            });

        } catch (Exception e) {
            task.doHttpInBackground(null);
            logger.e("expection - " + e);
        }
    }

    public static void excute_url(final HttpBean bean, Object ctx, final IHttp task, Object tag) {
//        cancelTag(tag);
        OkHttpClient httpClient = getHttpClient();
        try {
            String url = bean.getUrl();
            Request.Builder builder = new Request.Builder();

            for (String key : bean.getmHeaderData().keySet()) {
                logger.v("getPhpResult json --> header key = " + key + ",value = " + bean.getmHeaderData().get(key));
                builder.addHeader(key, (bean.getmHeaderData().get(key)) + "");
            }

            if (bean.isPost()) {
                Map<String, Object> map = bean.getmPostData();
                MultipartBody.Builder mBuilder = new MultipartBody.Builder();
                mBuilder.setType(MultipartBody.FORM);
                for (String key : map.keySet()) {
                    logger.e("HTTP Request BODY: " + key + "=" + bean.getmPostData().get(key));
                    mBuilder.addFormDataPart(key, (bean.getmPostData().get(key)) + "");
                }

                if (bean.getFile() != null) {
                    logger.e("file -->" + bean.getFile().getAbsolutePath());
                    mBuilder.addFormDataPart("upload", "img", RequestBody.create(MediaType.parse("image/png"), bean.getFile()));
//                        mBuilder.addPart(RequestBody.create(MediaType.parse("file"), bean.getFile()));
                }

                logger.d("http request [post] url : " + url);
                builder.url(url);
                builder.post(mBuilder.build());
            } else {
                Map<String, Object> map = bean.getmPostData();

                if (bean.get_etag() != null) {
//                    map.put("_etag",bean.get_etag());
                }
                if (map.size() > 0) {
                    String temp = "";
                    boolean flag = false;

                    for (String key : map.keySet()) {
                        logger.v("getPhpResult json --->post key=" + key + ", value=" + bean.getmPostData().get(key));
                        if (flag) {
                            temp = temp + "&";
                        } else {
                            flag = true;
                        }

                        temp = temp + key + "=" + map.get(key);
                    }

                    if (!StringUtils.isNull(temp)) {
                        if (!url.contains("?")) {
                            url = url + "?" + temp;
                        } else {
                            url = url + temp;
                        }
                    }

                    logger.d("http request [get] url : " + url);
                    builder.url(url);
                    if (bean.getFile() != null && bean.getFile().exists() && StringUtils.notNull(bean.getFileKey())) {
                        builder.post(RequestBody.create(MediaType.parse("file"), bean.getFile()));
                    }
                }
            }
            builder.tag(tag);
            Request request = builder.build();
            httpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    logger.e("expection - " + e);
                    task.doHttpInBackground(null);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String data = response.body().string();
                    task.doHttpInBackground(data);

                }
            });

        } catch (Exception e) {
            task.doHttpInBackground(null);
            logger.e("expection - " + e);
        }
    }


    public static void downFile(final HttpBean bean, Object ctx, final IHttp task, Object tag) {
        OkHttpClient httpClient = getHttpClient();
        try {
            String url = bean.getUrl();
            Request.Builder builder = new Request.Builder();

            for (String key : bean.getmHeaderData().keySet()) {
                logger.v("getPhpResult json --> header key = " + key + ",value = " + bean.getmHeaderData().get(key));
                builder.addHeader(key, (bean.getmHeaderData().get(key)) + "");
            }

            if (bean.isPost()) {
                Map<String, Object> map = bean.getmPostData();
                MultipartBody.Builder mBuilder = new MultipartBody.Builder();
                mBuilder.setType(MultipartBody.FORM);
                for (String key : map.keySet()) {
                    logger.e("HTTP Request BODY: " + key + "=" + bean.getmPostData().get(key));
                    mBuilder.addFormDataPart(key, (bean.getmPostData().get(key)) + "");
                }

                if (bean.getFile() != null) {
                    logger.e("file -->" + bean.getFile().getAbsolutePath());
                    mBuilder.addFormDataPart("upload", "img", RequestBody.create(MediaType.parse("image/png"), bean.getFile()));
//                        mBuilder.addPart(RequestBody.create(MediaType.parse("file"), bean.getFile()));
                }

                logger.d("http request [post] url : " + url);
                builder.url(url);
                builder.post(mBuilder.build());
            } else {
                Map<String, Object> map = bean.getmPostData();

                if (bean.get_etag() != null) {
//                    map.put("_etag",bean.get_etag());
                }
                if (map.size() > 0) {
                    String temp = "";
                    boolean flag = false;

                    for (String key : map.keySet()) {
                        logger.v("getPhpResult json --->post key=" + key + ", value=" + bean.getmPostData().get(key));
                        if (flag) {
                            temp = temp + "&";
                        } else {
                            flag = true;
                        }

                        temp = temp + key + "=" + map.get(key);
                    }

                    if (!StringUtils.isNull(temp)) {
                        if (!url.contains("?")) {
                            url = url + "?" + temp;
                        } else {
                            url = url + temp;
                        }
                    }

                    if (bean.getFile() != null && bean.getFile().exists() && StringUtils.notNull(bean.getFileKey())) {
                        builder.post(RequestBody.create(MediaType.parse("file"), bean.getFile()));
                    }
                }
            }
            logger.d("http request [get] url : " + url);
            builder.url(url);
            builder.tag(tag);
            Request request = builder.build();
            httpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    logger.e("expection - " + e);
                    task.doHttpInBackground(null);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
//                    String data = response.body().string();
//                    task.doHttpInBackground(data);
                    saveFile(task, response, bean.getDownfilepath(), bean.getDownfilename());
                }
            });

        } catch (Exception e) {
            task.doHttpInBackground(null);
            logger.e("expection - " + e);
        }
    }

    static class SimpleCookieJar implements CookieJar {

        private final List<Cookie> allCookies = new ArrayList<Cookie>();

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            allCookies.addAll(cookies);
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> result = new ArrayList<Cookie>();
            for (Cookie cookie : allCookies) {
                if (cookie != null && cookie.matches(url)) {
                    result.add(cookie);
                }
            }
            return result;
        }
    }


    private static File saveFile(final IHttp task, Response response, String dirpath, String filename) throws IOException {
        File dir = new File(dirpath == null ? Settings.TEMP_PATH : dirpath);
        if (!dir.exists()) dir.mkdirs();
        File file = new File(dir, filename == null ? "defaltname" : filename);
        if (file.exists()) file.delete();

        long lastRefreshUiTime = 0;  //最后一次刷新的时间
        long lastWriteBytes = 0;     //最后一次写入字节数据

        InputStream is = null;
        byte[] buf = new byte[2048];
        FileOutputStream fos = null;
        try {
            is = response.body().byteStream();
            final long total = response.body().contentLength();
            long sum = 0;
            int len;
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                sum += len;
                fos.write(buf, 0, len);
                final long finalSum = sum;

                long curTime = System.currentTimeMillis();
                //每200毫秒刷新一次数据
                if (curTime - lastRefreshUiTime >= 200 || finalSum == total) {
                    //计算下载速度
                    long diffTime = (curTime - lastRefreshUiTime) / 1000;
                    if (diffTime == 0) diffTime += 1;
                    long diffBytes = finalSum - lastWriteBytes;
                    final long networkSpeed = diffBytes / diffTime;
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            task.downloadProgress(finalSum, total, finalSum * 1.0f / total, networkSpeed);   //进度回调的方法
                            if (finalSum == total && total != 0)
                                task.downSuccess();
                        }
                    });

                    lastRefreshUiTime = System.currentTimeMillis();
                    lastWriteBytes = finalSum;
                }
            }
            fos.flush();
            return file;
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
                task.downFailed();
            }
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
                task.downFailed();
            }
        }
    }
}


