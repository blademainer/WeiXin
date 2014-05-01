package com.kingray.weixin.vo.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.kingray.weixin.vo.ContactGroupVo;
import com.kingray.weixin.vo.ContactVo;
import com.kingray.weixin.vo.ContactsVo;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMessages_it;
import com.xiongyingqi.util.EntityHelper;
import org.junit.Test;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by qi on 2014/5/1 0001.
 */
public class ContactsVoDeserializer extends JsonDeserializer<ContactsVo> {
    private ThreadLocal<ObjectMapper> _mapper = new ThreadLocal<ObjectMapper>() {
        @Override
        protected ObjectMapper initialValue() {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
            objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            return objectMapper;
        }
    };

    @Override
    public ContactsVo deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        int isVerifyOn = 0;
        int pageIdx = 0;
        int pageCount = 0;
        int pageSize = 0;
        String currentGroupId = null;
        int type = 0;
        int userRole = 0;
        int verifyMsgCount = 0;
        int totalCount = 0;
        Collection<ContactGroupVo> groupsList = null;
        Collection<ContactVo> friendsList = null;

        JsonNode isVerifyOnNode = node.get("isVerifyOn");
        JsonNode pageIdxNode = node.get("pageIdx");
        JsonNode pageCountNode = node.get("pageCount");
        JsonNode pageSizeNode = node.get("pageSize");
        JsonNode currentGroupIdNode = node.get("currentGroupId");
        JsonNode typeNode = node.get("type");
        JsonNode userRoleNode = node.get("userRole");
        JsonNode verifyMsgCountNode = node.get("verifyMsgCount");
        JsonNode totalCountNode = node.get("totalCount");
        JsonNode groupsListNode = node.get("groupsList");
        JsonNode friendsListNode = node.get("friendsList");

        EntityHelper.print(isVerifyOnNode);
        EntityHelper.print(pageIdxNode);
        EntityHelper.print(pageCountNode);
        EntityHelper.print(pageSizeNode);
        EntityHelper.print(currentGroupIdNode);

        /**
         * {        isVerifyOn: "0"*1,
         * pageIdx : 0,
         * pageCount : 1,
         * pageSize : 10,
         * groupsList : [{"id":0,"name":"未分组","cnt":7},{"id":1,"name":"黑名单","cnt":0},{"id":2,"name":"星标组","cnt":0}],
         * friendsList : [{"id":2151498040,"nick_name":"Fighting！","remark_name":"","group_id":0},{"id":321385200,"nick_name":"蜡笔小心","remark_name":"","group_id":0},{"id":155707355,"nick_name":"张佳","remark_name":"","group_id":0},{"id":2446328183,"nick_name":"丹丹","remark_name":"","group_id":0},{"id":1212231962,"nick_name":"Kenny","remark_name":"","group_id":0},{"id":559874680,"nick_name":"黄蒙蒙","remark_name":"","group_id":0},{"id":390155360,"nick_name":"kafee","remark_name":"","group_id":0}],
         * currentGroupId : '',
         * type : "0" * 1 || 0,
         * userRole : '1' * 1,
         * verifyMsgCount : '0' * 1,
         * totalCount : '7' * 1
         * }
         */

        if (isVerifyOnNode != null) {
            isVerifyOn = Integer.parseInt(isVerifyOnNode.asText().replaceAll("\\\\\\\"", ""));
            EntityHelper.print(isVerifyOn);
        }
        if (pageIdxNode != null) {
            pageIdx = pageIdxNode.asInt();
        }
        if (pageCountNode != null) {
            pageCount = pageCountNode.asInt();
        }
        if (pageSizeNode != null) {
            pageSize = pageSizeNode.asInt();
        }
        if (currentGroupIdNode != null) {
            currentGroupId = currentGroupIdNode.asText();
        }
        if (typeNode != null) {
            type = typeNode.asInt();
        }
        if (userRoleNode != null) {
            userRole = userRoleNode.asInt();
        }
        if (verifyMsgCountNode != null) {
            verifyMsgCount = verifyMsgCountNode.asInt();
        }
        if (totalCountNode != null) {
            totalCount = totalCountNode.asInt();
        }
        if (groupsListNode != null) {
            String groupsListString = groupsListNode.toString();
            EntityHelper.print(groupsListString);
            groupsList = _mapper.get().readValue(groupsListString, new TypeReference<List<ContactGroupVo>>() {
            });
        }
        if (friendsListNode != null) {
            String friendsListString = friendsListNode.toString();
            EntityHelper.print(friendsListString);
            friendsList = _mapper.get().readValue(friendsListString, new TypeReference<List<ContactVo>>() {
            });
        }

        ContactsVo contactsVo = new ContactsVo(isVerifyOn,
                pageIdx,
                pageCount,
                pageSize,
                currentGroupId,
                type,
                userRole,
                verifyMsgCount,
                totalCount,
                groupsList,
                friendsList);
//        EntityHelper.print(contactsVo);

//		System.out.println("content ========= " + content);
        return contactsVo;
    }

    @Test
    public void test() throws JsonProcessingException {

        String cgiData = "wx.cgiData={        isVerifyOn: \"0\"*1,        pageIdx : 0,            pageCount : 1,            pageSize : 10,            groupsList : ({\"groups\":[{\"id\":0,\"name\":\"未分组\",\"cnt\":7},{\"id\":1,\"name\":\"黑名单\",\"cnt\":0},{\"id\":2,\"name\":\"星标组\",\"cnt\":0}]}).groups,                        friendsList : ({\"contacts\":[{\"id\":2151498040,\"nick_name\":\"Fighting！\",\"remark_name\":\"\",\"group_id\":0},{\"id\":321385200,\"nick_name\":\"蜡笔小心\",\"remark_name\":\"\",\"group_id\":0},{\"id\":155707355,\"nick_name\":\"张佳\",\"remark_name\":\"\",\"group_id\":0},{\"id\":2446328183,\"nick_name\":\"丹丹\",\"remark_name\":\"\",\"group_id\":0},{\"id\":1212231962,\"nick_name\":\"Kenny\",\"remark_name\":\"\",\"group_id\":0},{\"id\":559874680,\"nick_name\":\"黄蒙蒙\",\"remark_name\":\"\",\"group_id\":0},{\"id\":390155360,\"nick_name\":\"kafee\",\"remark_name\":\"\",\"group_id\":0}]}).contacts,                                    currentGroupId : '',                        type : \"0\" * 1 || 0,            userRole : '1' * 1,            verifyMsgCount : '0' * 1,            totalCount : '7' * 1    };";
        cgiData = cgiData.replaceAll("wx.cgiData\\s*=", "")
                .replaceAll(";", "")
                .replaceAll("\\s*\\}\\s*\\)\\s*\\.\\w+\\s*", "")
                .replaceAll("\\(\\{\\s*\\\"\\s*\\w+\\s*\\\"\\s*:\\s*", "")
                .replaceAll("\\s*\\*\\s*\\w+\\s*(\\|\\|\\s*\\w+){0,1}", "");
        Pattern pattern = Pattern.compile("(\\\"\\d\\\")|(\\'\\d\\')");
        Matcher matcher = pattern.matcher(cgiData);
        while (matcher.find()) {
            String number = matcher.group();
            number = number.replaceAll("\"", "").replaceAll("\'", "");
            cgiData = matcher.replaceAll(number);
        }

//        Pattern pattern2 = Pattern.compile("\\w+\\s*:");
//        Matcher matcher2 = pattern2.matcher(cgiData);
//        while (matcher2.find()) {
//            String number = matcher2.group();
//
//        }

        EntityHelper.print(cgiData);
        ContactsVo contactsVo = null;
        try {
            contactsVo = _mapper.get().readValue(cgiData, ContactsVo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        EntityHelper.print(contactsVo);
    }
}
