package com.deepwisdom.common.utils;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * 钉钉机器人工具类
 * @author zhouhangeng
 * @Description
 * @date 2022/7/6
 */
@Component("dingDingUtils")
@Slf4j
public class DingDingUtils {

    /**
     * 设置机器人的时候成成的webhook
     */
    private static final String WEBHOOK = "https://oapi.dingtalk.com/robot/send?access_token=37983e315aa6a353cf6c839b0f82a594c4584c955c398ba873e3ab6341fa102e";

    /**
     * 设置机器人加签的时候生成的密钥secret
     */
    private static final String SECRET = "SECc0a1ea3055b21d5af9bba169e6f3fa1eb4c170e0247c018a0c5d35305cd21c8d";

    /**
     * 钉钉推送消息
     * @param atMobiles
     * @param content
     * @throws ApiException
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static void sendDingDing(List<String> atMobiles, String content) throws Exception {
        // 如果创建机器人的时候选择了加密，就需要getSign(XXX)
        String url = WEBHOOK + getSign(SECRET) ;
        // 创建客户端
        DingTalkClient client = new DefaultDingTalkClient(url);
        // 创建请求
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        // 设置通知内容格式为文本
        request.setMsgtype("text");
        // 传入文本内容
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent(content);
        request.setText(text);
        // 设置被艾特人
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        // isAtAll类型如果不为Boolean，请升级至最新SDK
        // 设置为false表示不艾特所有人。就算不艾特某个人，也加上 setIsAtAll(false)
        at.setIsAtAll(false);
        // 设置手机号，通过手机号艾特
        at.setAtMobiles(atMobiles);
        request.setAt(at);
        // 执行，发送消息
        client.execute(request);
    }

    /**
     * 加密方法
     * @param secret
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     */
    public static String getSign(String secret) throws Exception {
        Long timestamp = System.currentTimeMillis();
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)),"UTF-8");
        //https://oapi.dingtalk.com/robot/send?access_token=XXXXXX&timestamp=XXX&sign=XXX
        String signUrl = "&timestamp="+timestamp+"&sign="+sign ;
        return signUrl ;
    }
}

