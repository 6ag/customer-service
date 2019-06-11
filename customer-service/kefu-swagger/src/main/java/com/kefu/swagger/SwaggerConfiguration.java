package com.kefu.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger配置
 *
 * @author feng
 * @date 2019-05-19
 */
@Configuration
@EnableSwagger2
//@ComponentScan("com.kefu.swagger")
public class SwaggerConfiguration {

    @Bean
    public Docket docket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName("后台管理系统接口")
                .apiInfo(new ApiInfoBuilder()
                        .title("接口文档")
                        .description("接口文档描述")
                        .termsOfServiceUrl("http://springfox.io")
                        .license("Apache License Version 2.0")
                        .version("1.0")
                        .build());
        ApiSelectorBuilder builder = docket.select();
        builder.apis(RequestHandlerSelectors.basePackage("com.kefu.admin.handler"));
        return docket;
    }

}
