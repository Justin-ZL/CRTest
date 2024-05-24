package com.deepwisdom.config;

import com.deepwisdom.prop.TaskThreadPoolProp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName: OverrideDefaultThreadPoolConfig
 * @Description: 重写默认线程池配置
 * @Author: justin(zhanglei @ fuzhi.ai)
 * @Date: 2021/12/3 11:44
 * @Version: 1.0
 */
@Slf4j
@Configuration
@EnableAsync
public class OverrideDefaultThreadPoolConfig implements AsyncConfigurer {

    @Autowired
    private TaskThreadPoolProp config;

    @Override
    public Executor getAsyncExecutor() {
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
        executor.setThreadNamePrefix("default-thread-");
    /*
      当poolSize已达到maxPoolSize，如何处理新任务（是拒绝还是交由其它线程处理）
      CallerRunsPolicy：不在新线程中执行任务，而是由调用者所在的线程来执行
     */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    /**
     * 异步任务中异常处理
     *
     * @return
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> {
            log.error("==========================" + ex.getMessage() + "=======================", ex);
            log.error("exception method:" + method.getName());
        };
    }
}
