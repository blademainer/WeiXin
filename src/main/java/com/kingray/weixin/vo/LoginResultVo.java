package com.kingray.weixin.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xiongyingqi.util.EntityHelper;

/**
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/5/1 0001.
 */
public class LoginResultVo extends EntityHelper {
    @JsonProperty("base_resp")
    private BaseResp baseResp;

    @JsonProperty("redirect_url")
    private String redirectUrl;

    public BaseResp getBaseResp() {
        return baseResp;
    }

    public void setBaseResp(BaseResp baseResp) {
        this.baseResp = baseResp;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
