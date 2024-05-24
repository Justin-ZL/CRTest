package com.deepwisdom.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
* @ClassName: MybatisPlusConfigs.java
* @Author: justin(zhanglei@fuzhi.ai)
* @Date: 2021/8/2 16:28
* @Description: mybatis-plus配置。包的扫描路径一定要写清楚，不然会出现boundNotFound
* @Version: 1.0
*/
@Configuration
@MapperScan("com.deepwisdom.modules.datasetcenter.mapper")
public class MybatisPlusConfigs {

   /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
