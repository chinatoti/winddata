package com.sumscope.optimus;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by wenshuai.li on 2016/6/28.
 */
@SpringBootApplication
@ImportResource("classpath:datasource-config.xml")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ApplicationInitializer implements CommandLineRunner {

    public static void main(String[] args) throws Exception {
        SpringApplication springApplication =
                new SpringApplicationBuilder()
                        .sources(ApplicationInitializer.class)
                        .web(false)
                        .build();
        springApplication.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("hello world");
    }
}
