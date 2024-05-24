package com.deepwisdom.modules.datasetcenter.config;

import com.deepwisdom.common.enums.GrpcHostEnum;
import com.deepwisdom.common.interceptor.TraceIdClientInterceptor;
import com.deepwisdom.grpc.user.UserServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
* @ClassName: UserGrpcService配置
* @Author: zhouhangeng
* @Date: 2022/7/6 15:38
* @Description: 调用tianji-admin项目的grpc接口
* @Version: 1.0
*/
@Configuration
public class GrpcServiceConfig {

    @Value("${grpc.port : 8090}")
    private Integer port;

    /**
     * 要GRPC调用的项目名
     */
    private static String project = "tianji_admin";

    /**
     * ENV
     * 研发环境 dev
     * 测试环境 test
     * 生产环境 prod
     */
    private static String env = System.getenv("ENV");

    @Bean
    public ManagedChannel getChannel() {
        String host = "";
        if (env != null) {
            // 获取到的env不为null，说明是dev、test或prod环境访问
            host = GrpcHostEnum.getHost(project, env);
        } else {
            // 获取到的env为null，说明是本地访问
            host = GrpcHostEnum.getHost("local", "dev");
        }

        // 根据要访问的项目和环境进行调整
        return ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .intercept(new TraceIdClientInterceptor())
                .build();
    }

    @Bean
    public UserServiceGrpc.UserServiceBlockingStub getUserServiceStub(ManagedChannel channel) {
        return UserServiceGrpc.newBlockingStub(channel);
    }
}
