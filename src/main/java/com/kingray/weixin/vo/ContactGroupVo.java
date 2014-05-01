package com.kingray.weixin.vo;

import com.xiongyingqi.util.EntityHelper;

/**
 * 联系人分组编号
 * {"id":0,"name":"未分组","cnt":7}
 * Created by qi on 2014/5/1 0001.
 */
public class ContactGroupVo extends EntityHelper {
    private long id;
    private String name;
    private int cnt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }
}
