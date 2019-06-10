package com.kefu.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = {"com.kefu"})
public class KefuAdminApplication extends SpringBootServletInitializer {

    /**
     * 部署war包需要重写此方法
     *
     * @param builder
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(KefuAdminApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(KefuAdminApplication.class, args);
    }

}
