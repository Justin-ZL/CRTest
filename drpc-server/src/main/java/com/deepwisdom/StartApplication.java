package com.deepwisdom;

import com.deepwisdom.common.annotation.GrpcService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.IOException;
import java.util.Map;

/**
 * @author zhouhangeng
 */
@SpringBootApplication
@EnableAsync
public class StartApplication {

    public static void main(String[] args) throws IOException, InterruptedException {
        // 启动SpringBoot web
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(StartApplication.class, args);
        Map<String, Object> grpcServiceBeanMap = configurableApplicationContext.getBeansWithAnnotation(GrpcService.class);
        ServiceManager serviceManager = configurableApplicationContext.getBean(ServiceManager.class);
        serviceManager.loadService(grpcServiceBeanMap);
    }
}
