package com.kingray.weixin.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xiongyingqi.util.EntityHelper;

/**
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/5/1 0001.
 */
public class BaseResp extends EntityHelper {
    private String ret;
    @JsonProperty("err_msg")
    private String errMsg;

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
