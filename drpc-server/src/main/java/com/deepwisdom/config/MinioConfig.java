package com.deepwisdom.config;

import com.deepwisdom.prop.MinioProp;
import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: MinioConfig
 * @Description: minio 核心配置类
 * @Author: justin(zhanglei @ fuzhi.ai)
 * @Date: 2021/12/1 14:00
 * @Version: 1.0
 */
@Configuration
@EnableConfigurationProperties(MinioProp.class)
public class MinioConfig {
    @Autowired
    private MinioProp minioProp;

    /**
     * 获取 MinioClient
     * @return
     * @throws InvalidPortException
     * @throws InvalidEndpointException
     */
    @Bean
    public MinioClient minioClient() throws InvalidPortException, InvalidEndpointException {
        return new MinioClient(minioProp.getEndpoint(), minioProp.getAccessKey(), minioProp.getSecretKey());
    }
}
