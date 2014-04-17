package com.kingray.weixin;

import com.xiongyingqi.bot.XiaoIArtificialIntelligent;
import org.marker.weixin.DefaultSession;
import org.marker.weixin.HandleMessageAdapter;
import org.marker.weixin.MySecurity;
import org.marker.weixin.msg.Data4Item;
import org.marker.weixin.msg.Msg4ImageText;
import org.marker.weixin.msg.Msg4Text;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 处理微信服务器请求的Servlet URL地址：http://xxx/weixin/dealwith.do
 * <p/>
 * 注意：官方文档限制使用80端口哦！
 *
 * @author marker
 * @blog www.yl-blog.com
 * @weibo http://t.qq.com/wuweiit
 */
public class WeixinServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    //TOKEN 是你在微信平台开发模式中设置的哦
    public static final String TOKEN = "111";

    public static final int SECOND = 1000;

    /**
     * 处理微信服务器验证
     *
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        String signature = request.getParameter("signature");// 微信加密签名
        String timestamp = request.getParameter("timestamp");// 时间戳
        String nonce = request.getParameter("nonce");// 随机数
        String echostr = request.getParameter("echostr");// 随机字符串

        System.out.println("signature ============ " + signature);
        System.out.println("timestamp ============ " + timestamp);
        System.out.println("nonce ============ " + nonce);
        System.out.println("echostr ============ " + echostr);

        // 重写totring方法，得到三个参数的拼接字符串
        List<String> list = new ArrayList<String>(3) {
            private static final long serialVersionUID = 2621444383666420433L;

            public String toString() {
                return this.get(0) + this.get(1) + this.get(2);
            }
        };
        list.add(TOKEN);
        list.add(timestamp);
        list.add(nonce);
        Collections.sort(list);// 排序
        String tmpStr = new MySecurity().encode(list.toString(),
                MySecurity.SHA_1);// SHA-1加密
        Writer out = response.getWriter();
        if (signature.equals(tmpStr)) {
            out.write(echostr);// 请求验证成功，返回随机码
        } else {
            out.write("");
        }
        out.flush();
        out.close();
    }


    /**
     * 处理微信服务器发过来的各种消息，包括：文本、图片、地理位置、音乐等等
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        InputStream is = request.getInputStream();
        OutputStream os = response.getOutputStream();


        final DefaultSession session = DefaultSession.newInstance();

        session.addOnHandleMessageListener(new HandleMessageAdapter() {

            @Override
            public void onTextMsg(Msg4Text msg) {
//                System.out.println("收到微信消息：" + msg.getContent());
//                if ("我是唐小怪".equals(msg.getContent())) {
//                    Msg4Text rmsg = new Msg4Text();
//                    rmsg.setFromUserName(msg.getToUserName());
//                    rmsg.setToUserName(msg.getFromUserName());
//                    rmsg.setContent("你是小乖乖，哈哈!");
//                    session.callback(rmsg);
//                    return;
//                } else if(msg.getContent().startsWith("呵呵")) {
//                    Msg4Text rmsg = new Msg4Text();
//                    rmsg.setFromUserName(msg.getToUserName());
//                    rmsg.setToUserName(msg.getFromUserName());
//                    rmsg.setContent("呵呵你妹。。。");
//                    session.callback(rmsg);
//                    return;
//                }

                XiaoIArtificialIntelligent xiaoIArtificialIntelligent = new XiaoIArtificialIntelligent();//使用xiaoi回复
                String answer = xiaoIArtificialIntelligent.simpleAsk(msg.getFromUserName(), msg.getContent());
                if (answer != null && !answer.startsWith("主人还没给我设置这类话题的回复")) {
                    if (answer.startsWith("你的话题涉及敏感词汇")) {
                        answer = "哎呀~小心查水表哦~";
                    } else if (answer.startsWith("文明中国")) {
                        answer = "素质一词本是生理学概念，指人的先天生理解剖特点，主要指神经系统、脑的特性及感觉器官和运动器官的特点，素质是心理活动发展的前提，离开这个物质基础谈不上心理发展。各门学科对素质的解释不同，但都有一点是共同的，即素质是以人的生理和心理实际作基础，以其自然属性为基本前提的。也就是说，个体生理的、心理的成熟水平的不同决定着个体素质的差异，因此，对人的素质的理解要以人的身心组织结构及其质量水平为前提。";
                    }
                    Msg4Text rmsg = new Msg4Text();
                    rmsg.setFromUserName(msg.getToUserName());
                    rmsg.setToUserName(msg.getFromUserName());
                    rmsg.setContent(answer);
                    session.callback(rmsg);
                    return;
                }

                //回复一条消息
                Data4Item d1 = new Data4Item("blademainer的博客", "测试描述", "http://xiongyingqi.com/themes/violet/images/favicon.ico", "xiongyingqi.com");

                Msg4ImageText mit = new Msg4ImageText();
                mit.setFromUserName(msg.getToUserName());
                mit.setToUserName(msg.getFromUserName());
                mit.setCreateTime(msg.getCreateTime());
                mit.addItem(d1);
                session.callback(mit);
                try {
                    Thread.sleep(SECOND * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                session.callback(mit);
                try {
                    Thread.sleep(SECOND * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                session.callback(mit);
            }
        });

        //必须调用这两个方法
        //如果不调用close方法，将会出现响应数据串到其它Servlet中。
        session.process(is, os);//处理微信消息
        session.close();//关闭Session
    }

}