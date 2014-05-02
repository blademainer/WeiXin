package com.kingray.weixin.vo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.javafx.beans.annotations.Default;
import org.junit.Test;

import java.util.Random;

/**
 * type:1
 * content:asdfasfd
 * tofakeid:390155360
 * imgcode:
 * token:378103717
 * lang:zh_CN
 * random:0.9107416851911694
 * f:json
 * ajax:1
 * t:ajax-response
 * Created by qi on 2014/5/2 0002.
 */
public class SendMessageVo {
    private int type;
    private String content;
    private long tofakeid;
    private String imgcode;
    private String token;
    private String lang;
    private String random;
    private String f;
    private String ajax;
    private String t;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTofakeid() {
        return tofakeid;
    }

    public void setTofakeid(long tofakeid) {
        this.tofakeid = tofakeid;
    }

    public String getImgcode() {
        return imgcode;
    }

    public void setImgcode(String imgcode) {
        this.imgcode = imgcode;
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

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
    }

    public String getF() {
        return f;
    }

    public void setF(String f) {
        this.f = f;
    }

    public String getAjax() {
        return ajax;
    }

    public void setAjax(String ajax) {
        this.ajax = ajax;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    /**
     * type:1
     * content:asdfasfd
     * tofakeid:390155360
     * imgcode:
     * token:378103717
     * lang:zh_CN
     * random:0.9107416851911694
     * f:json
     * ajax:1
     * t:ajax-response
     * @return
     */
    public static SendMessageVo buildBaseSendMessageVo(){
        SendMessageVo sendMessageVo = new SendMessageVo();
        sendMessageVo.setType(1);
        sendMessageVo.setImgcode("");
        sendMessageVo.setLang("zh_CN");
        sendMessageVo.setRandom(Math.abs(new Random().nextGaussian()) + "");
        sendMessageVo.setF("json");
        sendMessageVo.setAjax("1");
        sendMessageVo.setT("ajax-response");
        return sendMessageVo;
    }

}
