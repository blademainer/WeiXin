package com.xiongyingqi.bot;

import com.xiongyingqi.util.*;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * XiaoI机器人自动应答
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/4/17 0017.
 */
public class XiaoIArtificialIntelligent {
    public static final File propertiesFile = new File(XiaoIArtificialIntelligent.class.getClassLoader().getResource("xiaoi.properties").getFile());
    private static String appKey;
    private static String appSecret;

    private static String nonce;
    private static String signature;

    private static String realm;
    private static String method;
    private static String uri;


    private static String url;
    private static String platform;
    private static String type;

    private static String encoding;

    private static String header;


    static {
        Map<String, String> properties = PropertiesHelper.readProperties(propertiesFile);
        appKey = properties.get("key");
        appSecret = properties.get("secret");
        realm = properties.get("realm");
        method = properties.get("method");
        uri = properties.get("uri");
        url = properties.get("url");
        platform = properties.get("platform");
        type = properties.get("type");
        encoding = properties.get("encoding");


        if (!url.startsWith("http://")) {
            url = "http://" + url;
        }
        sign();
    }

    private static void sign() {
        Assert.notNull(appKey);
        Assert.notNull(appSecret);
        Assert.notNull(realm);
        Assert.notNull(method);
        Assert.notNull(uri);

        byte[] b = new byte[20];
        new Random().nextBytes(b);
        nonce = new String(Hex.encodeHex(b));

        Collection<String> strings1 = new ArrayList<String>();
        strings1.add(appKey);
        strings1.add(realm);
        strings1.add(appSecret);

        Collection<String> strings2 = new ArrayList<String>();
        strings2.add(method);
        strings2.add(uri);

        String HA1 = DigestUtils.sha1Hex(StringUtils.join(strings1, ":"));
        String HA2 = DigestUtils.sha1Hex(StringUtils.join(strings2, ":"));

        Collection<String> strings3 = new ArrayList<String>();
        strings3.add(HA1);
        strings3.add(nonce);
        strings3.add(HA2);
        signature = DigestUtils.sha1Hex(StringUtils.join(strings3, ":"));

    }

    public String simpleAsk(String question){
        return simpleAsk(StringUtil.randomString(8), question);
    }

    public String simpleAsk(String userId, String question) {
        return sendRequest(userId, question);
    }

    public String sendRequest(String userId, String question) {
        HttpClientBuilder client = HttpClientBuilder.create();
        client.setUserAgent("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.116 Safari/537.36");
        final CloseableHttpClient closeableHttpClient = client.build();
        URIBuilder uriBuilder = new URIBuilder().setPath(url);
        uriBuilder.addParameter("platform", platform);

        try {
            URI uri = uriBuilder.build();
            HttpPost httpPost = new HttpPost(uri);
            /**
             * X-Auth:app_key="y601p5UoGtbU", nonce="97576fe76a668ae55818b0b19f376fa29d6c", signature="87dc9c5f8b1b1907837eeb9ae5724fa375ef7d96"
             */
            httpPost.addHeader("X-Auth", getHeader());

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("userId", userId));
            nameValuePairs.add(new BasicNameValuePair("question", question));
            nameValuePairs.add(new BasicNameValuePair("type", type));

            HttpEntity requestEntity = new UrlEncodedFormEntity(nameValuePairs, Charset.forName(encoding));
            httpPost.setEntity(requestEntity);

//            EntityHelper.print(httpPost.getURI());
//            EntityHelper.print(httpPost.getRequestLine());
//            String request = FileHelper.readInputStreamToString(httpPost.getEntity().getContent(), "UTF-8");
//            EntityHelper.print(request);

            Header[] headers = httpPost.getAllHeaders();
            for (Header header : headers) {
                EntityHelper.print(header);
            }



            CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            ContentType contentType = ContentType.getOrDefault(entity);// 获取编码
            Charset charset = contentType.getCharset();
            InputStream inputStream = entity.getContent();
            String result = FileHelper.readInputStreamToString(inputStream, charset);
            return result;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getHeader() {
        if (header == null) {
            String header = "";
            header += "app_key=\"" + appKey + "\", ";
            header += "nonce=\"" + nonce + "\", ";
            header += "signature=\"" + signature + "\"";
            XiaoIArtificialIntelligent.header = header;
        }
        return header;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        //app_key="y601p5UoGtbU", nonce="97576fe76a668ae55818b0b19f376fa29d6c", signature="87dc9c5f8b1b1907837eeb9ae5724fa375ef7d96"
        XiaoIArtificialIntelligent artificialIntelligent = new XiaoIArtificialIntelligent();
        System.out.println(artificialIntelligent.simpleAsk("你是谁"));
    }
}
