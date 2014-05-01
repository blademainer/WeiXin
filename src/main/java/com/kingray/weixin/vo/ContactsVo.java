package com.kingray.weixin.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kingray.weixin.vo.deserializer.ContactsVoDeserializer;
import com.xiongyingqi.util.EntityHelper;

import java.util.Collection;

/**
 * wx.cgiData={
 * isVerifyOn: "0"*1,
 * pageIdx : 0,
 * pageCount : 1,
 * pageSize : 10,
 * groupsList : ({"groups":[{"id":0,"name":"未分组","cnt":7},{"id":1,"name":"黑名单","cnt":0},{"id":2,"name":"星标组","cnt":0}]}).groups,
 * friendsList : ({"contacts":[{"id":2151498040,"nick_name":"Fighting！","remark_name":"","group_id":0},{"id":321385200,"nick_name":"蜡笔小心","remark_name":"","group_id":0},{"id":155707355,"nick_name":"张佳","remark_name":"","group_id":0},{"id":2446328183,"nick_name":"丹丹","remark_name":"","group_id":0},{"id":1212231962,"nick_name":"Kenny","remark_name":"","group_id":0},{"id":559874680,"nick_name":"黄蒙蒙","remark_name":"","group_id":0},{"id":390155360,"nick_name":"kafee","remark_name":"","group_id":0}]}).contacts,
 * currentGroupId : '',
 * type : "0" * 1 || 0,
 * userRole : '1' * 1,
 * verifyMsgCount : '0' * 1,
 * totalCount : '7' * 1
 * };
 * Created by qi on 2014/5/1 0001.
 */
@JsonDeserialize(using = ContactsVoDeserializer.class)
public class ContactsVo extends EntityHelper {
    private int isVerifyOn;
    private int pageIdx;
    private int pageCount;
    private int pageSize;
    private String currentGroupId;
    private int type;
    private int userRole;
    private int verifyMsgCount;
    private int totalCount;
    private Collection<ContactGroupVo> groupsList;
    private Collection<ContactVo> friendsList;

    public ContactsVo() {
    }

    public ContactsVo(int isVerifyOn, int pageIdx, int pageCount, int pageSize, String currentGroupId, int type, int userRole, int verifyMsgCount, int totalCount, Collection<ContactGroupVo> groupsList, Collection<ContactVo> friendsList) {
        this.isVerifyOn = isVerifyOn;
        this.pageIdx = pageIdx;
        this.pageCount = pageCount;
        this.pageSize = pageSize;
        this.currentGroupId = currentGroupId;
        this.type = type;
        this.userRole = userRole;
        this.verifyMsgCount = verifyMsgCount;
        this.totalCount = totalCount;
        this.groupsList = groupsList;
        this.friendsList = friendsList;
    }

    public int getIsVerifyOn() {
        return isVerifyOn;
    }

    public void setIsVerifyOn(int isVerifyOn) {
        this.isVerifyOn = isVerifyOn;
    }

    public int getPageIdx() {
        return pageIdx;
    }

    public void setPageIdx(int pageIdx) {
        this.pageIdx = pageIdx;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getCurrentGroupId() {
        return currentGroupId;
    }

    public void setCurrentGroupId(String currentGroupId) {
        this.currentGroupId = currentGroupId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    public int getVerifyMsgCount() {
        return verifyMsgCount;
    }

    public void setVerifyMsgCount(int verifyMsgCount) {
        this.verifyMsgCount = verifyMsgCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public Collection<ContactGroupVo> getGroupsList() {
        return groupsList;
    }

    public void setGroupsList(Collection<ContactGroupVo> groupsList) {
        this.groupsList = groupsList;
    }

    public Collection<ContactVo> getFriendsList() {
        return friendsList;
    }

    public void setFriendsList(Collection<ContactVo> friendsList) {
        this.friendsList = friendsList;
    }
}
