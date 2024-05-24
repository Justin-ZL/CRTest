package com.deepwisdom.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName: TaskThreadPoolProp
 * @Description: 线程池配置属性类
 * @Author: justin(zhanglei @ fuzhi.ai)
 * @Date: 2021/12/3 11:46
 * @Version: 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "task.pool")
public class TaskThreadPoolProp {

    /**
     * 核心线程数
     */
    private int corePoolSize;

    /**
     * 最大线程数
     */
    private int maxPoolSize;

    /**
     * 线程空闲时间
     */
    private int keepAliveSeconds;

    /**
     * 任务队列容量（阻塞队列）
     */
    private int queueCapacity;
}
