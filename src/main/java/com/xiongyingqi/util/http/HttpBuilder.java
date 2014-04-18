package com.xiongyingqi.util.http;

import com.xiongyingqi.util.MD5Helper;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by 瑛琪<a href="http://xiongyingqi.com">xiongyingqi.com</a> on 2014/4/18 0018.
 */
public class HttpBuilder {
    public static String basePath = "https://mp.weixin.qq.com";
    public static void test(){
        try {
            HttpClientBuilder client = HttpClientBuilder.create();


            String loginUri = "/cgi-bin/login?lang=zh_CN";
            URL url = new URL(basePath);
            System.out.println(url.getPath());
            URI uri = new URI(loginUri);
            System.out.println(uri.getPath());
            CloseableHttpClient httpClient = client.build();
            URIBuilder uriBuilder = new URIBuilder().setPath(basePath + loginUri);

            String userName = "blademainer.root@gmail.com";
            String password = "";
            String encryptPassword = MD5Helper.encrypt(password);
            HttpPost post = new HttpPost();
//            post.setEntity();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        test();
    }

}
