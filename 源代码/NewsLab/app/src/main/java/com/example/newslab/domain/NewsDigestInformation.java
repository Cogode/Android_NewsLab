package com.example.newslab.domain;

import java.io.Serializable;
import java.util.List;

public class NewsDigestInformation implements Serializable {
    private String code;
    private String msg;
    private List<NewsDigest> newslist;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<NewsDigest> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<NewsDigest> newslist) {
        this.newslist = newslist;
    }
}
