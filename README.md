# MyOkhttp

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
            }
        };
        HttpBean bean = new HttpBean();
        bean.setUrl("");
        bean.setDownfile(true);
        bean.setDownfilepath(Settings.BASE_URL);
        bean.setDownfilename("myokhttp.apk");
        new HttpTask(bean, downfile, getApplicationContext());

    }
