package com.xiongyingqi.util.http;

import com.xiongyingqi.util.EntityHelper;
import com.xiongyingqi.util.FileHelper;
import com.xiongyingqi.util.MD5Helper;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
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

/**
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/4/18 0018.
 */
public class HttpBuilder {
    public static String basePath = "https://mp.weixin.qq.com";
    public static void test(){
        try {
            String encoding = "UTF-8";
            HttpClientBuilder client = HttpClientBuilder.create();


            String loginUri = "/cgi-bin/login?lang=zh_CN";

//            URL url = new URL(basePath);
//            System.out.println(url.getPath());
//            URI uri = new URI(loginUri);
//            System.out.println(uri.getPath());

            CloseableHttpClient closeableHttpClient = client.build();
//            URIBuilder uriBuilder = new URIBuilder().setPath(basePath + loginUri);
//            EntityHelper.print(uriBuilder.build());

            String userName = "blademainer.root@gmail.com";
            String password = "krit1979";
            String encryptPassword = MD5Helper.encrypt(password);

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("username", userName));
            nameValuePairs.add(new BasicNameValuePair("pwd", encryptPassword));
            nameValuePairs.add(new BasicNameValuePair("f", "json"));

            HttpPost httpPost = new HttpPost("https://mp.weixin.qq.com/cgi-bin/login?lang=zh_CN");
            httpPost.addHeader("Referer", "https://mp.weixin.qq.com/");
            HttpEntity requestEntity = new UrlEncodedFormEntity(nameValuePairs, Charset.forName(encoding));
            httpPost.setEntity(requestEntity);


            CloseableHttpResponse response = closeableHttpClient.execute(httpPost);

            Header[] headers = response.getAllHeaders();
            for (Header header : headers) {
                EntityHelper.print(header);
            }

            HttpEntity entity = response.getEntity();
            ContentType contentType = ContentType.getOrDefault(entity);// 获取编码
            EntityHelper.print(contentType);
            Charset charset = contentType.getCharset();
            InputStream inputStream = entity.getContent();
            String result = FileHelper.readInputStreamToString(inputStream, charset);
            EntityHelper.print(result);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        test();
    }

}
