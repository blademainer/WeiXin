package com.kingray.weixin;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingray.weixin.vo.ContactsVo;
import com.kingray.weixin.vo.GetContactRequestVo;
import com.kingray.weixin.vo.LoginResultVo;
import com.kingray.weixin.vo.LoginVo;
import com.xiongyingqi.util.EntityHelper;
import com.xiongyingqi.util.FileHelper;
import com.xiongyingqi.util.MD5Helper;
import com.xiongyingqi.util.http.BuildNameValuePairsHelper;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/5/1 0001.
 */
public class WeiXinApi {
    private HttpClientBuilder client;
    private CloseableHttpClient closeableHttpClient;
    private String loginUrl = "https://mp.weixin.qq.com/cgi-bin/login?lang=zh_CN";
    private String token;

    private ThreadLocal<ObjectMapper> _mapper = new ThreadLocal<ObjectMapper>() {
        @Override
        protected ObjectMapper initialValue() {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
            objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            return objectMapper;
        }
    };

    private Pattern PATTERN_TOKEN = Pattern.compile("token=\\w+");
    /**
     * 查找wx.cgiData = {};的类似内容
     */
    private Pattern PATTERN_CGI_DATA = Pattern.compile("wx.cgiData\\s*=\\s*(.|\\s)*?\\}\\;");
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
     */
    private Pattern PATTERN_PARAMETER = Pattern.compile("\\w+\\s*:\\s*\\((.|\\s)+?\\)");
    private String encoding = "UTF-8";

    public WeiXinApi() {
        client = HttpClientBuilder.create();
        closeableHttpClient = client.build();
    }


    public LoginResultVo doLogin(LoginVo loginVo) {
        try {

//            URL url = new URL(basePath);
//            System.out.println(url.getPath());
//            URI uri = new URI(loginUri);
//            System.out.println(uri.getPath());


//            URIBuilder uriBuilder = new URIBuilder().setPath(basePath + loginUri);
//            EntityHelper.print(uriBuilder.build());

            String encryptPassword = MD5Helper.encrypt(loginVo.getPassword());

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("username", loginVo.getUserName()));
            nameValuePairs.add(new BasicNameValuePair("pwd", encryptPassword));
            nameValuePairs.add(new BasicNameValuePair("f", "json"));
            if (loginVo.getCaptcha() != null) {
                nameValuePairs.add(new BasicNameValuePair("imgcode", loginVo.getCaptcha()));
            }

            HttpPost httpPost = new HttpPost(loginUrl);
            httpPost.addHeader("Referer", "https://mp.weixin.qq.com/");
            HttpEntity requestEntity = new UrlEncodedFormEntity(nameValuePairs, Charset.forName(encoding));
            httpPost.setEntity(requestEntity);


            CloseableHttpResponse response = closeableHttpClient.execute(httpPost);

            Header[] headers = response.getAllHeaders();
//            for (Header header : headers) {
//                EntityHelper.print(header);
//            }
            HttpEntity entity = response.getEntity();
            ContentType contentType = ContentType.getOrDefault(entity);// 获取编码
//            EntityHelper.print(contentType);
            Charset charset = contentType.getCharset();
            InputStream inputStream = entity.getContent();
            String result = FileHelper.readInputStreamToString(inputStream, charset);
            EntityHelper.print(result);
            LoginResultVo loginResultVo = _mapper.get().readValue(result, LoginResultVo.class);
            EntityHelper.print(loginResultVo);
            String token = parseToken(loginResultVo);
            this.token = token;
            return loginResultVo;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取token
     *
     * @return
     */
    private String parseToken(LoginResultVo loginResultVo) {
        /**
         * /cgi-bin/home?t=home/index&lang=zh_CN&token=1560285654
         */
        String redirectUrl = loginResultVo.getRedirectUrl();
        Matcher matcher = PATTERN_TOKEN.matcher(redirectUrl);
        if (matcher.find()) {
            String tokenPair = matcher.group();
            String token = tokenPair.replace("token", "").replace("=", "");
            EntityHelper.print(token);
            return token;
        }
        return null;
    }


    /**
     * t:user/index
     * pagesize:10
     * pageidx:0
     * type:0
     * token:1560285654
     * lang:zh_CN
     */
    public void getContacts(GetContactRequestVo getContactRequestVo) {
        String url = "https://mp.weixin.qq.com/cgi-bin/contactmanage";
        String nameValues = BuildNameValuePairsHelper.buildObjectToGet(getContactRequestVo);
        if(nameValues != null){
            url += "?" + nameValues;
        }
        try {

//            URL url = new URL(basePath);
//            System.out.println(url.getPath());
//            URI uri = new URI(loginUri);
//            System.out.println(uri.getPath());


//            URIBuilder uriBuilder = new URIBuilder().setPath(basePath + loginUri);
//            EntityHelper.print(uriBuilder.build());


            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("Referer", "https://mp.weixin.qq.com/advanced/advanced?action=index&t=advanced/index&token=1560285654&lang=zh_CN");
//            HttpEntity requestEntity = new UrlEncodedFormEntity(BuildNameValuePairsHelper.build(getContactRequestVo), Charset.forName(encoding));
//            httpGet.setEntity(requestEntity);


            CloseableHttpResponse response = closeableHttpClient.execute(httpGet);

//            for (Header header : headers) {
//                EntityHelper.print(header);
//            }
            HttpEntity entity = response.getEntity();
            ContentType contentType = ContentType.getOrDefault(entity);// 获取编码
//            EntityHelper.print(contentType);
            Charset charset = contentType.getCharset();
            InputStream inputStream = entity.getContent();
            String result = FileHelper.readInputStreamToString(inputStream, charset);
            EntityHelper.print(result);
            Matcher matcher = PATTERN_CGI_DATA.matcher(result);// 查找cgiData
            if(matcher.find()){
                String cgiData = matcher.group();
                EntityHelper.print(cgiData);

                ContactsVo contactsVo = handleContacts(cgiData);
                EntityHelper.print(contactsVo);

            } else { // 如果返回的结果包括，则应该是登录错误

            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ContactsVo handleContacts(String cgiData) throws IOException {
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
        ContactsVo contactsVo = _mapper.get().readValue(cgiData, ContactsVo.class);
        return contactsVo;
    }

    public static void main(String[] args) {
        WeiXinApi api = new WeiXinApi();
        LoginVo loginVo = new LoginVo();
        loginVo.setUserName("blademainer@gmail.com");
        loginVo.setPassword("xl606017");
        api.doLogin(loginVo);

        /**
         * t:user/index
         * pagesize:10
         * pageidx:0
         * type:0
         * token:1560285654
         * lang:zh_CN
         */
        GetContactRequestVo getContactRequestVo = new GetContactRequestVo("user/index", 10, 0, 0, api.token, "zh_CN");
        api.getContacts(getContactRequestVo);

    }

}
