package com.kingray.weixin.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xiongyingqi.util.EntityHelper;

/**
 * 联系人实体
 * "id":2151498040,
 * "nick_name":"Fighting！",
 * "remark_name":"",
 * "group_id":0
 * Created by qi on 2014/5/1 0001.
 */
public class ContactVo extends EntityHelper {
    @JsonProperty("id")
    private long id;

    @JsonProperty("nick_name")
    private String nickName;

    @JsonProperty("remark_name")
    private String remarkName;

    @JsonProperty("group_id")
    private int groupId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRemarkName() {
        return remarkName;
    }

    public void setRemarkName(String remarkName) {
        this.remarkName = remarkName;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
