package com.deepwisdom.config;

import com.deepwisdom.prop.TaskThreadPoolProp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName: CustomizeThreadPoolConfig
 * @Description: 自定义线程池
 * @Author: justin(zhanglei @ fuzhi.ai)
 * @Date: 2021/12/3 11:48
 * @Version: 1.0
 */
@Configuration
@EnableAsync
public class CustomizeThreadPoolConfig {

    @Autowired
    private TaskThreadPoolProp config;

    @Bean("customizeThreadPool")
    public Executor doConfigCustomizeThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程池大小
        executor.setCorePoolSize(config.getCorePoolSize());
        //最大线程数
        executor.setMaxPoolSize(config.getMaxPoolSize());
        //队列容量
        executor.setQueueCapacity(config.getQueueCapacity());
        //活跃时间
        executor.setKeepAliveSeconds(config.getKeepAliveSeconds());
        //线程名字前缀
        executor.setThreadNamePrefix("customize-thread-");
     /*
      当poolSize已达到maxPoolSize，如何处理新任务（是拒绝还是交由其它线程处理）
      CallerRunsPolicy：不在新线程中执行任务，而是由调用者所在的线程来执行
     */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
