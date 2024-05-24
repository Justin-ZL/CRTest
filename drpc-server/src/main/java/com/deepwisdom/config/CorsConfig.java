package com.deepwisdom.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
* @ClassName: CorsConfig.java
* @Author: justin(zhanglei@fuzhi.ai)
* @Date: 2021/8/2 16:15
* @Description: 后端处理跨域的配置文件，主要是实现WebMvcConfigurer的addCorsMappings，如果跨域由网关处理，
                则可以将此文件的@Configuration注解注释掉即可
* @Version: 1.0
*/
@Configuration
public class CorsConfig {

    @Bean
    public FilterRegistrationBean corsFilter() {
        //1.添加CORS配置信息
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        //2) 是否发送Cookie信息
        config.setAllowCredentials(true);
        //3) 允许的请求方式
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        config.setMaxAge(3600L);
        // 4）允许的头信息
        config.addAllowedHeader("*");

        //2.添加映射路径，我们拦截一切请求
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);

        //3.返回新的CorsFilter.
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(configSource));
        bean.setOrder(0);
        return bean;
    }
}