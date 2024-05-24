package com.deepwisdom.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName: MinioProp
 * @Description: minio 属性值
 * @Author: justin(zhanglei @ fuzhi.ai)
 * @Date: 2021/12/1 13:59
 * @Version: 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "boto3")
public class MinioProp {
    /**
     * 连接url
     */
    private String endpoint;
    /**
     * 用户名
     */
    private String accessKey;
    /**
     * 密码
     */
    private String secretKey;

    /**
     * 桶
     */
    private String bucketName;
}
