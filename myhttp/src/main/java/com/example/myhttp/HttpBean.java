package com.example.myhttp;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class HttpBean implements Serializable {

    /**
     * 请求参数信息
     */
    private Map<String,Object> mPostData = new HashMap<String,Object>();

    /**
     * 请求头信息
     */
    private Map<String,Object> mHeaderData = new HashMap<String,Object>();

    /**
     * 请求记录信息
     */
    private Map<String,Object> otherData = new HashMap<String,Object>();

    /**
     * 基础url
     */
    private String baseUrl;
    /**
     * 不加base的url
     */
    private String Url;
    /**
     * 请求类型
     */
    private boolean post;


    private boolean downfile = false;
    private String downfilepath;
    private String downfilename;

    /**
     *
     */
    private File file;
    private String fileKey;
    /**
     * 网络请求tag
     */
    private Object http_tag;

    /**
     * 接口缓存 _etag
     */
    private String _etag;

    private boolean isAddBaseUrl = true;

    public boolean isAddBaseUrl() {
        return isAddBaseUrl;
    }

    public void setAddBaseUrl(boolean addBaseUrl) {
        isAddBaseUrl = addBaseUrl;
    }

    public String get_etag() {
        return _etag;
    }

    public void set_etag(String _etag) {
        this._etag = _etag;
    }

    public Object getHttp_tag() {
        return http_tag;
    }

    public void setHttp_tag(Object http_tag) {
        this.http_tag = http_tag;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isPost() {
        return post;
    }

    public void setPost(boolean post) {
        this.post = post;
    }

    public Map<String, Object> getmPostData() {
        return mPostData;
    }

    public void setmPostData(Map<String, Object> mPostData) {
        this.mPostData = mPostData;
    }

    public Map<String, Object> getmHeaderData() {
        return mHeaderData;
    }

    public void setmHeaderData(Map<String, Object> mHeaderData) {
        this.mHeaderData = mHeaderData;
    }

    public Map<String, Object> getOtherData() {
        return otherData;
    }

    public void setOtherData(Map<String, Object> otherData) {
        this.otherData = otherData;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public boolean isDownfile() {
        return downfile;
    }

    public void setDownfile(boolean downfile) {
        this.downfile = downfile;
    }

    public String getDownfilepath() {
        return downfilepath;
    }

    public void setDownfilepath(String downfilepath) {
        this.downfilepath = downfilepath;
    }

    public String getDownfilename() {
        return downfilename;
    }

    public void setDownfilename(String downfilename) {
        this.downfilename = downfilename;
    }
}
