package com.deepwisdom.common.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
* @ClassName: GrpcService.java
* @Author: justin(zhanglei@fuzhi.ai)
* @Date: 2021/8/2 16:21
* @Description: 自定义注解，用于获取Spring扫描到的类
* @Version: 1.0
*/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface GrpcService {
}
