

package com.deepwisdom.common.aspect;

import com.deepwisdom.common.enums.ExceptionCodeEnum;
import com.deepwisdom.common.exception.RRException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
* @ClassName: RedisAspect.java
* @Author: justin(zhanglei@fuzhi.ai)
* @Date: 2021/8/2 16:27
* @Description: Redis切面处理类
* @Version: 1.0
*/
@Aspect
@Configuration
public class RedisAspect {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 是否开启redis缓存  true开启   false关闭
     */
    @Value("${spring.redis.open: false}")
    private boolean open;

    @Around("execution(* com.deepwisdom.common.utils.RedisUtils.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        if(open){
            try{
                result = point.proceed();
            }catch (Exception e){
                logger.error("redis error", e);
                throw new RRException(ExceptionCodeEnum.REDIS_SERVICE_EXCEPTION.getMsg(), ExceptionCodeEnum.REDIS_SERVICE_EXCEPTION.getCode());
            }
        }
        return result;
    }
}
