package com.kingray.weixin.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiongyingqi.util.EntityHelper;
import org.junit.Test;

import java.io.IOException;

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

    @Test
    public void test() throws IOException {
        String res = "{\"base_resp\":{\"ret\":0,\"err_msg\":\"ok\"}}";
        EntityHelper.print(res.lastIndexOf("}"));
        EntityHelper.print(res.length());
        res = res.replaceAll("\\{\\s*(\\\"|\\')base_resp(\\\"|\\')\\s*\\:", "");
        res = res.substring(0, res.lastIndexOf("}"));
        EntityHelper.print(res);
        ObjectMapper mapper = new ObjectMapper();
        BaseResp baseResp = mapper.readValue(res, BaseResp.class);
        EntityHelper.print(baseResp);
    }
}
