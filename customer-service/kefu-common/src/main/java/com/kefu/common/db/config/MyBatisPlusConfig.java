package com.kefu.common.db.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Properties;

/**
 * @author feng
 * @date 2019-05-23
 */
@Configuration
@MapperScan("com.kefu.*.mapper")
public class MyBatisPlusConfig {

    /**
     * MyBatis Plus分页插件
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 打印SQL/SQL执行效率插件 设置 dev 环境开启
     */
    @Bean
    @Profile({"dev"})
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        // 格式化sql语句
//        Properties properties = new Properties();
//        properties.setProperty("format", "true");
//        performanceInterceptor.setProperties(properties);
        return performanceInterceptor;
    }
}
