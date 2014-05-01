package com.kingray.weixin.vo;

/**
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/5/1 0001.
 */
public class GetContactRequestVo {
    private String t;
    private int pagesize;
    private int pageidx;
    private int type;
    private String token;
    private String lang;

    public GetContactRequestVo() {
    }

    public GetContactRequestVo(String t, int pagesize, int pageidx, int type, String token, String lang) {
        this.t = t;
        this.pagesize = pagesize;
        this.pageidx = pageidx;
        this.type = type;
        this.token = token;
        this.lang = lang;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public int getPageidx() {
        return pageidx;
    }

    public void setPageidx(int pageidx) {
        this.pageidx = pageidx;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
