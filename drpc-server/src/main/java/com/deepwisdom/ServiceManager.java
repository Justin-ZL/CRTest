package com.deepwisdom;

import com.deepwisdom.common.interceptor.TraceIdServerInterceptor;
import io.grpc.*;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Map;

/**
* @ClassName: ServiceManager.java
* @Author: justin(zhanglei@fuzhi.ai)
* @Date: 2021/8/2 16:14
* @Description: 服务管理
* @Version: 1.0
*/
@Component
public class ServiceManager {

    private Server server;

    /**
     * 端口，infra内部约定http端口使用8080，GRPC的端口使用8090
     */
    private int grpcServerPort = 8090;

    public void loadService(Map<String, Object> grpcServiceBeanMap) throws IOException, InterruptedException {
        ServerBuilder serverBuilder = ServerBuilder.forPort(grpcServerPort);
        // 采用注解扫描方式，添加服务
        for (Object bean : grpcServiceBeanMap.values()) {
            serverBuilder.addService(ServerInterceptors.intercept((BindableService) bean,new TraceIdServerInterceptor()));
            System.out.println(bean.getClass().getSimpleName() + " is registed！");

        }
        server = serverBuilder.build().start();

        System.out.println("grpc server is started at " + grpcServerPort);

        // 增加一个钩子，当JVM进程退出时，Server 关闭
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                if (server != null) {
                    server.shutdown();
                }
                System.err.println("*** server shut down！！！！");
            }
        });

        server.awaitTermination();
    }
}
